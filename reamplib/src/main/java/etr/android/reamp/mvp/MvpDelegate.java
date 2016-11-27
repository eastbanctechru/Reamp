package etr.android.reamp.mvp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import java.util.List;
import java.util.UUID;

import etr.android.reamp.navigation.Navigation;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class MvpDelegate {

    private static final String KEY_PRESENTER_STATE = "KEY_PRESENTER_STATE";
    private static final String KEY_MVP_ID = "KEY_MVP_ID";
    private static final String TAG = "MvpDelegate";

    private final MvpView view;
    private Subscription subscription;
    private String mvpId;

    public MvpDelegate(MvpView view) {
        this.view = view;
    }

    public void onCreate(Bundle savedInstanceState) {

        Bundle presenterState = null;
        if (savedInstanceState != null) {
            presenterState = savedInstanceState.getBundle(KEY_PRESENTER_STATE);
            mvpId = savedInstanceState.getString(KEY_MVP_ID);
        } else {
            mvpId = view.getMvpId();
        }

        PresenterManager presenterManager = PresenterManager.getInstance();
        MvpPresenter presenter = presenterManager.getPresenter(mvpId);

        boolean newPresenter = presenter == null;

        if (presenter == null) {
            presenter = view.onCreatePresenter();
            presenterManager.setPresenter(mvpId, presenter);

            MvpStateModel stateModel = null;
            if (presenterState != null) {
                stateModel = presenter.deserializeState(presenterState);
            }

            if (stateModel == null) {
                stateModel = view.onCreateStateModel();
            }

            presenter.attachStateModel(stateModel);
        }

        Navigation navigation = new Navigation();
        //TODO: check if view is always an Activity
        navigation.setActivity((Activity) view.getContext());
        presenter.setNavigation(navigation);

        view.setPresenter(presenter);
        presenter.setView(view);

        if (newPresenter) {
            presenter.onPresenterCreated();
        }
    }


    public String generateId(MvpView view) {
        return UUID.randomUUID().toString();
    }

    public void connect() {

        view.getPresenter().onConnect();

        subscription = view.getPresenter().getStateUpdater()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<MvpStateModel>() {
                    @Override
                    public void call(MvpStateModel stateModel) {
                        view.onStateChanged(stateModel);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        view.onError(throwable);
                    }
                });
    }

    public void disconnect() {
        view.getPresenter().onDisconnect();
        subscription.unsubscribe();
    }

    public void onSaveInstanceState(Bundle outState) {
        outState.putString(KEY_MVP_ID, mvpId);
        outState.putBundle(KEY_PRESENTER_STATE, view.getPresenter().serializeState());
    }

    public void onDestroy() {
        MvpPresenter presenter = view.getPresenter();
        presenter.setView(null);
        view.setPresenter(null);

        Navigation navigation = presenter.getNavigation();
        navigation.setActivity(null);
        presenter.setNavigation(null);

        if (view instanceof Activity && ((Activity) view).isFinishing()) {
            presenter.onDestroyPresenter();
            PresenterManager.getInstance().destroyPresenter(view.getMvpId());

            if (view instanceof FragmentActivity) {
                FragmentManager fragmentManager = ((FragmentActivity) view).getSupportFragmentManager();
                List<Fragment> fragments = fragmentManager.getFragments();
                if (fragments != null) {
                    for (Fragment fragment : fragments) {
                        if (fragment instanceof MvpView) {
                            MvpView mvpFragment = (MvpView) fragment;
                            if (mvpFragment.getPresenter() != null) {
                                mvpFragment.getPresenter().onDestroyPresenter();
                            } else {
                                Log.w(TAG, "onDestroy: fragment presenter is null");
                            }
                            PresenterManager.getInstance().destroyPresenter(mvpFragment.getMvpId());
                        }
                    }
                }
            }

            //TODO: do the same for simple Activity from standard package
        }
    }

    public void onResult(int requestCode, int resultCode, Intent data) {
        if (view instanceof Activity) {

            MvpPresenter presenter = view.getPresenter();
            presenter.onResult(requestCode, resultCode, data);
            Activity activity = (Activity) view.getContext();
            if (activity instanceof FragmentActivity) {
                FragmentManager fragmentManager = ((FragmentActivity) activity).getSupportFragmentManager();
                List<Fragment> fragments = fragmentManager.getFragments();
                if (fragments != null) {
                    for (Fragment fragment : fragments) {
                        if (fragment instanceof MvpView) {
                            MvpView mvpFragment = (MvpView) fragment;
                            MvpPresenter fragmentPresenter = mvpFragment.getPresenter();
                            if (fragmentPresenter != null) {
                                fragmentPresenter.onResult(requestCode, resultCode, data);
                            } else {
                                Log.w(TAG, "onResult: fragment presenter is null");
                            }
                        }
                    }
                }
            }
        }
    }
}

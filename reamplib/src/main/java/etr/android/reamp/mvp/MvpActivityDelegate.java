package etr.android.reamp.mvp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import etr.android.reamp.navigation.Navigation;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class MvpActivityDelegate<P extends MvpPresenter<SM>, SM extends MvpStateModel> {

    private static final String KEY_PRESENTER_STATE = "KEY_PRESENTER_STATE";
    private static final String KEY_MVP_ID = "KEY_MVP_ID";

    private final IMvpActivity<P, SM> activity;
    private final List<Subscription> subscriptions = new ArrayList<>();

    public MvpActivityDelegate(IMvpActivity<P, SM> activity) {
        this.activity = activity;
    }

    public void onCreate(Bundle savedInstanceState) {

        Bundle presenterState = null;
        if (savedInstanceState != null) {
            activity.setMvpId(savedInstanceState.getString(KEY_MVP_ID));
            presenterState = savedInstanceState.getBundle(KEY_PRESENTER_STATE);
        }
        if (activity.getMvpId() == null) {
            activity.setMvpId(UUID.randomUUID().toString());
        }

        PresenterManager presenterManager = PresenterManager.getInstance();
        P presenter = (P) presenterManager.getPresenter(activity.getMvpId());

        boolean newPresenter = false;
        if (presenter == null) {
            newPresenter = true;
            presenter = activity.onCreatePresenter();
            presenterManager.setPresenter(activity.getMvpId(), presenter);

            SM stateModel = null;
            if (presenterState != null) {
                stateModel = presenter.deserializeState(presenterState);
            }

            if (stateModel == null) {
                stateModel = activity.onCreateStateModel();
            }

            presenter.attachStateModel(stateModel);
        }

        Navigation navigation = new Navigation();
        navigation.setActivity(activity.getActivity());
        presenter.setNavigation(navigation);

        activity.setPresenter(presenter);
        presenter.setView(activity);

        if (newPresenter) {
            presenter.onFirstCreate();
        } else {
            presenter.onRestore(presenterState);
        }
        presenter.onCreate();
    }

    public void onResume() {
        subscriptions.add(activity.getPresenter().getStateUpdater()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<SM>() {
                    @Override
                    public void call(SM stateModel) {
                        activity.onStateChanged(stateModel);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        activity.onError(throwable);
                    }
                }));

        activity.getPresenter().onResume();
    }

    public void onPause() {
        activity.getPresenter().onPause();
        for (Subscription subscription : subscriptions) {
            subscription.unsubscribe();
        }
        subscriptions.clear();
    }

    public void onSaveInstanceState(Bundle outState) {
        outState.putString(KEY_MVP_ID, activity.getMvpId());
        outState.putBundle(KEY_PRESENTER_STATE, activity.getPresenter().serializeState());
    }

    public void onResult(int requestCode, int resultCode, Intent data) {
        P presenter = activity.getPresenter();
        presenter.onResult(requestCode, resultCode, data);
        Activity activity = this.activity.getActivity();
        if (activity instanceof FragmentActivity) {
            FragmentManager fragmentManager = ((FragmentActivity) activity).getSupportFragmentManager();
            List<Fragment> fragments = fragmentManager.getFragments();
            if (fragments != null) {
                for (Fragment fragment : fragments) {
                    if (fragment instanceof IMvpFragment) {
                        IMvpFragment mvpFragment = (IMvpFragment) fragment;
                        MvpFragmentDelegate mvpFragmentDelegate = mvpFragment.getDelegate();
                        mvpFragmentDelegate.onResult(requestCode, resultCode, data);
                    }
                }
            }
        }
    }

    public void onDestroy(boolean finishing) {
        P presenter = activity.getPresenter();
        presenter.onDestroy();
        presenter.setView(null);
        activity.setPresenter(null);

        Navigation navigation = presenter.getNavigation();
        navigation.setActivity(null);
        presenter.setNavigation(null);

        if (finishing) {
            presenter.onDestroyPresenter();
            PresenterManager.getInstance().destroyPresenter(activity.getMvpId());

            if (activity instanceof FragmentActivity) {
                FragmentManager fragmentManager = ((FragmentActivity) activity).getSupportFragmentManager();
                List<Fragment> fragments = fragmentManager.getFragments();
                if (fragments != null) {
                    for (Fragment fragment : fragments) {
                        if (fragment instanceof IMvpFragment) {
                            IMvpFragment mvpFragment = (IMvpFragment) fragment;
                            mvpFragment.getPresenter().onDestroyPresenter();
                            PresenterManager.getInstance().destroyPresenter(mvpFragment.getMvpId());
                        }
                    }
                }
            }
        }
    }
}

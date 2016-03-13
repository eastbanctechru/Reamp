package etr.android.reamp.mvp;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import rx.Subscription;
import rx.functions.Action1;

public class MvpFragmentDelegate<P extends MvpPresenter<SM>, SM extends MvpStateModel> {

    public static final String KEY_PRESENTER_STATE = "KEY_PRESENTER_STATE";
    private static final String KEY_MVP_ID = "KEY_MVP_ID";

    private final IMvpFragment<P, SM> fragment;
    private final List<Subscription> subscriptions = new ArrayList<>();

    public MvpFragmentDelegate(IMvpFragment<P, SM> fragment) {
        this.fragment = fragment;
    }

    public void onCreate(Bundle savedInstanceState) {

        Bundle presenterState = null;
        if (savedInstanceState != null) {
            presenterState = savedInstanceState.getBundle(KEY_PRESENTER_STATE);
            fragment.setMvpId(savedInstanceState.getString(KEY_MVP_ID));
        }
        if (fragment.getMvpId() == null) {
            fragment.setMvpId(UUID.randomUUID().toString());
        }

        SM stateModel = fragment.onCreateStateModel();

        PresenterManager presenterManager = PresenterManager.getInstance();
        P presenter = (P) presenterManager.getPresenter(fragment.getMvpId());
        if (presenter == null){
            presenter = fragment.onCreatePresenter();
            presenterManager.setPresenter(fragment.getMvpId(), presenter);
        }

        MvpAppCompatActivity activity = (MvpAppCompatActivity) fragment.getActivity();
        presenter.setNavigation(activity.getPresenter().getNavigation());
        presenter.attachStateModel(stateModel);
        presenter.setView(fragment);
        fragment.setPresenter(presenter);
        if (presenterState == null) {
            presenter.onFirstCreate();
        } else {
            presenter.onRestore(presenterState);
        }
        presenter.onCreate();
    }

    public void onResume() {
        subscriptions.add(fragment.getPresenter().getStateUpdater()
                .subscribe(new Action1<SM>() {
                    @Override
                    public void call(SM stateModel) {
                        fragment.onStateChanged(stateModel);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        fragment.onError(throwable);
                    }
                }));

        fragment.getPresenter().onResume();
    }

    public void onPause() {
        for (Subscription subscription : subscriptions) {
            subscription.unsubscribe();
        }
        subscriptions.clear();
    }

    public void onSaveInstanceState(Bundle outState) {
        outState.putString(KEY_MVP_ID, fragment.getMvpId());
        outState.putBundle(KEY_PRESENTER_STATE, fragment.getPresenter().serializeState());
    }

    public void onDestroy() {
        P presenter = fragment.getPresenter();
        presenter.setView(null);
        presenter.setNavigation(null);
        fragment.setPresenter(null);
    }

    public void onResult(int requestCode, int resultCode, Intent data) {
        P presenter = fragment.getPresenter();
        presenter.onResult(requestCode, resultCode, data);
    }
}

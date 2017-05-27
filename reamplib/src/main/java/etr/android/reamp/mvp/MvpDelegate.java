package etr.android.reamp.mvp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.util.List;
import java.util.UUID;

/**
 * A proxy class between {@link MvpView} and {@link MvpPresenter} which should be used
 * while implementing a custom {@link MvpView}.
 */
public class MvpDelegate {

    private static final String KEY_PRESENTER_STATE = "KEY_PRESENTER_STATE";
    private static final String KEY_MVP_ID = "KEY_MVP_ID";
    private static final String TAG = "MvpDelegate";

    private final MvpView view;
    private String mvpId;
    private MvpPresenter presenter;
    private StateChanges stateChanges;

    public MvpDelegate(MvpView view) {
        this.view = view;
    }

    public <P extends MvpPresenter<SM>, SM extends MvpStateModel> P getPresenter() {
        return (P) presenter;
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

        presenter.addView(view);
        this.presenter = presenter;

        if (newPresenter) {
            presenter.onPresenterCreated();
        }

        presenterManager.registerView(view, view.getContext());
    }

    public String getId() {
        if (this.mvpId == null) {
            this.mvpId = UUID.randomUUID().toString();
        }
        return this.mvpId;
    }

    public void connect() {

        stateChanges = new StateChanges() {
            @Override
            public void onNewState(MvpStateModel state) {
                view.onStateChanged(state);
            }

            @Override
            public void onError(Throwable e) {
                view.onError(e);
            }
        };
        view.getPresenter().connect(stateChanges, view);
    }

    public void disconnect() {
        view.getPresenter().disconnect(stateChanges, view);
        stateChanges = null;
    }

    public void onSaveInstanceState(Bundle outState) {
        outState.putString(KEY_MVP_ID, mvpId);
        outState.putBundle(KEY_PRESENTER_STATE, view.getPresenter().serializeState());
    }

    public void onDestroy() {
        MvpPresenter presenter = view.getPresenter();
        presenter.removeView(view);
        this.presenter = null;
    }

    /**
     * Helper method that forwards the "onResult" call of Activity to all its' fragments
     */
    public void onResult(int requestCode, int resultCode, Intent data) {
        if (view instanceof Activity) {
            dispatchResult(requestCode, resultCode, data);
        }
    }

    private void dispatchResult(int requestCode, int resultCode, Intent data) {
        List<MvpView> views = PresenterManager.getInstance().getViewsOf(view.getContext());
        for (MvpView mvpView : views) {
            MvpPresenter presenter = mvpView.getPresenter();
            if (presenter != null) {
                presenter.onResult(requestCode, resultCode, data);
            }
        }
    }
}

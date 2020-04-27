package etr.android.reamp.mvp.screen_one;

import android.content.Context;
import androidx.annotation.NonNull;

import etr.android.reamp.mvp.ReampPresenter;
import etr.android.reamp.mvp.ReampStateModel;
import etr.android.reamp.mvp.ReampView;

public class TestReampView<P extends ReampPresenter<SM>, SM extends ReampStateModel> implements ReampView<SM> {

    private final P presenter;
    private final SM stateModel;

    public SM sm;
    public Throwable error;

    public TestReampView(@NonNull P presenter, @NonNull SM stateModel) {
        this.presenter = presenter;
        this.stateModel = stateModel;
    }

    @Override
    public Context getContext() {
        return null;
    }

    @Override
    public void onStateChanged(SM stateModel) {
        sm = stateModel;
    }

    @Override
    public void onError(Throwable throwable) {
        error = throwable;
    }

    @Override
    public String getMvpId() {
        return null;
    }

    @Override
    public SM onCreateStateModel() {
        return stateModel;
    }

    @Override
    public P onCreatePresenter() {
        return presenter;
    }

    @Override
    public P getPresenter() {
        return presenter;
    }
}

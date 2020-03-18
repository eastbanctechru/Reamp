package etr.android.reamp.mvp.screen_one;

import android.content.Context;

import etr.android.reamp.mvp.ReampPresenter;
import etr.android.reamp.mvp.ReampView;

public class OneView implements ReampView<OneModel> {
    @Override
    public Context getContext() {
        return null;
    }

    @Override
    public void onStateChanged(OneModel stateModel) {

    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public String getMvpId() {
        return null;
    }

    @Override
    public OneModel onCreateStateModel() {
        return null;
    }

    @Override
    public ReampPresenter<OneModel> onCreatePresenter() {
        return null;
    }

    @Override
    public ReampPresenter<OneModel> getPresenter() {
        return null;
    }
}

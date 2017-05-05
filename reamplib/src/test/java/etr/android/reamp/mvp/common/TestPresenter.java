package etr.android.reamp.mvp.common;

import etr.android.reamp.mvp.MvpPresenter;

public class TestPresenter extends MvpPresenter<TestState> {
    public void count() {
        getStateModel().counter++;
        sendStateModel();
    }
}

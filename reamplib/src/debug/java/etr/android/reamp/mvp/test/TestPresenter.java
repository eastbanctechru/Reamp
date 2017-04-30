package etr.android.reamp.mvp.test;

import etr.android.reamp.mvp.MvpPresenter;

public class TestPresenter extends MvpPresenter<TestState> {

    @Override
    public void onPresenterCreated() {
        super.onPresenterCreated();
    }

    public void onClick() {
        getStateModel().clicked = true;
        sendStateModel();
    }

    public void increment() {
        getStateModel().count++;
        sendStateModel();
    }
}

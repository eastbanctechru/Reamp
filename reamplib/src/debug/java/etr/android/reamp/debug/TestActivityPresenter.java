package etr.android.reamp.debug;

import etr.android.reamp.mvp.MvpPresenter;

public class TestActivityPresenter extends MvpPresenter<TestActivityState> {

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

    public void setObj(Object object) {
        getStateModel().object = object;
        sendStateModel();
    }

    public void sendError() {
        getView().onError(new RuntimeException());
    }
}

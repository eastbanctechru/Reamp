package etr.android.reamp.debug;

import etr.android.reamp.mvp.MvpPresenter;

public class TestFragmentPresenter extends MvpPresenter<TestFragmentState> {
    public void count() {
        getStateModel().counter++;
        sendStateModel();
    }

    public void sendError() {
        getView().onError(new RuntimeException());
    }
}

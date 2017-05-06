package etr.android.reamp.debug;

import etr.android.reamp.mvp.MvpPresenter;

public class CustomViewPresenter extends MvpPresenter<CustomViewState> {
    public void count() {
        getStateModel().counter++;
        sendStateModel();
    }
}

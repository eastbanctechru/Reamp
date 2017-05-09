package example.reamp.basic;

import etr.android.reamp.mvp.MvpPresenter;

public class BasicPresenter extends MvpPresenter<BasicState> {

    public void increment() {
        getStateModel().counter++;
        sendStateModel();
    }
}

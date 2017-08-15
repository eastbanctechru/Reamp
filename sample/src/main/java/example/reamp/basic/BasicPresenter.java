package example.reamp.basic;

import etr.android.reamp.mvp.ReampPresenter;

public class BasicPresenter extends ReampPresenter<BasicState> {

    public void increment() {
        getStateModel().counter++;
        sendStateModel();
    }
}

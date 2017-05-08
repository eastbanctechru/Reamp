package etr.android.reamp.mvp.internal;

import etr.android.reamp.mvp.MvpPresenter;

public class TesteePresenter extends MvpPresenter<TesteeState> {

    public int presenterCreated;
    public int destroyPresenter;
    public int onConnected;
    public int onDisconnected;

    @Override
    public void onPresenterCreated() {
        super.onPresenterCreated();
        presenterCreated++;
    }

    @Override
    public void onDestroyPresenter() {
        super.onDestroyPresenter();
        destroyPresenter++;
    }

    @Override
    public void onConnect() {
        super.onConnect();
        onConnected++;
    }

    @Override
    public void onDisconnect() {
        super.onDisconnect();
        onDisconnected++;
    }

    public void increment() {
        getStateModel().counter++;
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

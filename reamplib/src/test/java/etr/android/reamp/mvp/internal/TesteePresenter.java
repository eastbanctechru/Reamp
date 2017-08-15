package etr.android.reamp.mvp.internal;

import etr.android.reamp.mvp.ReampPresenter;
import etr.android.reamp.mvp.ReampView;

public class TesteePresenter extends ReampPresenter<TesteeState> {

    public int presenterCreated;
    public int destroyPresenter;
    public int onConnected;
    public int onDisconnected;
    public int onConnectedWithView;
    public int onDisconnectWithView;

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
    public void onConnect(ReampView view) {
        super.onConnect(view);
        onConnectedWithView++;
    }

    @Override
    public void onDisconnect(ReampView view) {
        super.onDisconnect(view);
        onDisconnectWithView++;
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

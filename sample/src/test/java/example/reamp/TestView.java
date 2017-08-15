package example.reamp;

import android.content.Context;

import etr.android.reamp.mvp.MvpDelegate;
import etr.android.reamp.mvp.ReampPresenter;
import etr.android.reamp.mvp.ReampStateModel;
import etr.android.reamp.mvp.ReampView;

public class TestView<SM extends ReampStateModel> implements ReampView<SM> {

    private MvpDelegate delegate = new MvpDelegate(this);

    public TestView() {
        delegate.onCreate(null);
        delegate.connect();
    }

    @Override
    public Context getContext() {
        return null;
    }

    @Override
    public void onStateChanged(SM stateModel) {

    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public String getMvpId() {
        return delegate.getId();
    }

    @Override
    public SM onCreateStateModel() {
        return null;
    }

    @Override
    public ReampPresenter<SM> onCreatePresenter() {
        return null;
    }

    @Override
    public ReampPresenter<SM> getPresenter() {
        return delegate.<ReampPresenter<SM>, SM>getPresenter();
    }
}

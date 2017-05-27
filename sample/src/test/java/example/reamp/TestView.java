package example.reamp;

import android.content.Context;

import etr.android.reamp.mvp.MvpDelegate;
import etr.android.reamp.mvp.MvpPresenter;
import etr.android.reamp.mvp.MvpStateModel;
import etr.android.reamp.mvp.MvpView;

public class TestView<SM extends MvpStateModel> implements MvpView<SM> {

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
    public MvpPresenter<SM> onCreatePresenter() {
        return null;
    }

    @Override
    public MvpPresenter<SM> getPresenter() {
        return delegate.<MvpPresenter<SM>, SM>getPresenter();
    }
}

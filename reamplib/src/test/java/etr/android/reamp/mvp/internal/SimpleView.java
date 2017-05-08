package etr.android.reamp.mvp.internal;

import android.content.Context;

import etr.android.reamp.mvp.MvpDelegate;
import etr.android.reamp.mvp.MvpPresenter;
import etr.android.reamp.mvp.MvpView;

public class SimpleView implements MvpView<TesteeState> {

    private MvpDelegate delegate = new MvpDelegate(this);

    @Override
    public Context getContext() {
        return null;
    }

    @Override
    public void onStateChanged(TesteeState stateModel) {

    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public String getMvpId() {
        return delegate.getId();
    }

    @Override
    public TesteeState onCreateStateModel() {
        return new TesteeState();
    }

    @Override
    public TesteePresenter onCreatePresenter() {
        return new TesteePresenter();
    }

    @Override
    public TesteePresenter getPresenter() {
        MvpPresenter presenter = delegate.getPresenter();
        return (TesteePresenter) presenter;
    }

    public void onCreate() {
        delegate.onCreate(null);
    }

    public void connect() {
        delegate.connect();
    }
}

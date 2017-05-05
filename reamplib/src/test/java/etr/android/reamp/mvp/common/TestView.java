package etr.android.reamp.mvp.common;

import android.content.Context;

import etr.android.reamp.mvp.MvpDelegate;
import etr.android.reamp.mvp.MvpPresenter;
import etr.android.reamp.mvp.MvpView;

public class TestView implements MvpView<TestState> {

    private MvpDelegate delegate = new MvpDelegate(this);

    @Override
    public Context getContext() {
        return null;
    }

    @Override
    public void onStateChanged(TestState stateModel) {

    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public String getMvpId() {
        return delegate.getId();
    }

    @Override
    public TestState onCreateStateModel() {
        return new TestState();
    }

    @Override
    public MvpPresenter<TestState> onCreatePresenter() {
        return new TestPresenter();
    }

    @Override
    public TestPresenter getPresenter() {
        MvpPresenter presenter = delegate.getPresenter();
        return (TestPresenter) presenter;
    }

    public void onCreate() {
        delegate.onCreate(null);
    }

    public void connect() {
        delegate.connect();
    }
}

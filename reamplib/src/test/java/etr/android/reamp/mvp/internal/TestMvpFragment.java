package etr.android.reamp.mvp.internal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import etr.android.reamp.mvp.MvpFragment;

public class TestMvpFragment extends MvpFragment<TesteePresenter, TesteeState> {

    public int counter;
    public Throwable throwable;

    @Override
    public void onStateChanged(TesteeState stateModel) {
        this.counter = stateModel.counter;
    }

    @Override
    public TesteeState onCreateStateModel() {
        return new TesteeState();
    }

    @Override
    public TesteePresenter onCreatePresenter() {
        return new TesteePresenter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return new View(getContext());
    }

    @Override
    public void onError(Throwable throwable) {
        super.onError(throwable);
        this.throwable = throwable;
    }
}

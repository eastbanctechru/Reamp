package etr.android.reamp.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TestMvpFragment extends MvpFragment<TestFragmentPresenter, TestFragmentState> {

    public int counter;

    @Override
    public void onStateChanged(TestFragmentState stateModel) {
        this.counter = stateModel.counter;
    }

    @Override
    public TestFragmentState onCreateStateModel() {
        return new TestFragmentState();
    }

    @Override
    public MvpPresenter<TestFragmentState> onCreatePresenter() {
        return new TestFragmentPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return new View(getContext());
    }
}

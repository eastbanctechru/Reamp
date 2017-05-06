package etr.android.reamp.debug.navigation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import etr.android.reamp.mvp.MvpFragment;
import etr.android.reamp.mvp.MvpPresenter;

public class NavFragment extends MvpFragment<NavFragmentPresenter, NavFragmentState> {

    private boolean spoilPresenter;

    @Override
    public void onStateChanged(NavFragmentState stateModel) {

    }

    @Override
    public NavFragmentState onCreateStateModel() {
        return new NavFragmentState();
    }

    @Override
    public MvpPresenter<NavFragmentState> onCreatePresenter() {
        return new NavFragmentPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return new View(getContext());
    }

    public void spoilPresenter() {
        spoilPresenter = true;
    }

    @Override
    public NavFragmentPresenter getPresenter() {
        if (spoilPresenter) {
            return null;
        }
        return super.getPresenter();
    }
}

package etr.android.reamp.mvp.internal.navigation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import etr.android.reamp.mvp.ReampFragment;
import etr.android.reamp.mvp.SerializableStateModel;

public class NavFragment extends ReampFragment<NavigationPresenter, SerializableStateModel> {

    private boolean spoilPresenter;

    @Override
    public void onStateChanged(SerializableStateModel stateModel) {

    }

    @Override
    public SerializableStateModel onCreateStateModel() {
        return new SerializableStateModel();
    }

    @Override
    public NavigationPresenter onCreatePresenter() {
        return new NavigationPresenter();
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
    public NavigationPresenter getPresenter() {
        if (spoilPresenter) {
            return null;
        }
        return super.getPresenter();
    }
}

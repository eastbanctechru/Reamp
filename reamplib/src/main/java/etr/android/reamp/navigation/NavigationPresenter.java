package etr.android.reamp.navigation;

import android.app.Activity;

import etr.android.reamp.mvp.MvpPresenter;
import etr.android.reamp.mvp.MvpStateModel;

public class NavigationPresenter<SM extends MvpStateModel> extends MvpPresenter<SM> {

    protected Navigation getNavigation() {
        return new Navigation((Activity) getView().getContext());
    }
}

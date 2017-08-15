package example.reamp.navigation;

import android.content.Intent;

import etr.android.reamp.mvp.ReampPresenter;
import example.reamp.navigation.details.DetailsWithData;
import example.reamp.navigation.details.DetailsWithResultUnit;

public class NavigationFragmentPresenter extends ReampPresenter<NavigationFragmentState> {

    // a basic way to use Navigation and NavigationUnit
    public void openBrowser() {
        getNavigation().open(new BrowserNavUnit());
    }

    public void openForResult() {
        DetailsWithResultUnit detailsNavigationUnit = new DetailsWithResultUnit();
        getNavigation().open(detailsNavigationUnit);
    }

    @Override
    public void onResult(int requestCode, int resultCode, Intent data) {
        super.onResult(requestCode, resultCode, data);
        getStateModel().resultText = getNavigation().getResult(new DetailsWithResultUnit(), requestCode, resultCode, data);
        sendStateModel();
    }

    public void openWithData() {
        getNavigation().open(new DetailsWithData().withData(getStateModel().mainText));
    }

    public void onMainTextChanged(String text) {
        getStateModel().mainText = text;
    }
}

package example.reamp.main;

import android.content.Intent;

import etr.android.reamp.mvp.MvpPresenter;
import example.reamp.navigation.DetailsNavigationUnit;

public class MainFragmentPresenter extends MvpPresenter<MainFragmentStateModel> {

    @Override
    public void onResult(int requestCode, int resultCode, Intent data) {
        super.onResult(requestCode, resultCode, data);
        getStateModel().textFromDetails = getNavigation().getResult(new DetailsNavigationUnit(), requestCode, resultCode, data);
        sendStateModel();
    }

    public void onOpenDetailsClicked() {
        DetailsNavigationUnit detailsNavigationUnit = new DetailsNavigationUnit(getStateModel().mainText);
        getNavigation().open(detailsNavigationUnit);
    }

    public void onMainTextChanged(String text) {
        getStateModel().mainText = text;
    }
}

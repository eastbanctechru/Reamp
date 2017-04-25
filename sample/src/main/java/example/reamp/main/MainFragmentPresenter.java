package example.reamp.main;

import android.content.Intent;

import etr.android.reamp.navigation.NavigationPresenter;
import example.reamp.navigation.DetailsNavigationUnit;
import example.reamp.navigation.NumberNavigationUnit;

public class MainFragmentPresenter extends NavigationPresenter<MainFragmentStateModel> {

    @Override
    public void onResult(int requestCode, int resultCode, Intent data) {
        super.onResult(requestCode, resultCode, data);
        getStateModel().resultText = getNavigation().getResult(new DetailsNavigationUnit(), requestCode, resultCode, data);
        if (getStateModel().resultText == null) {
            getStateModel().resultText = String.valueOf(getNavigation().getResult(new NumberNavigationUnit(), requestCode, resultCode, data));
        }
        sendStateModel();
    }

    public void onOpenDetailsClicked() {
        DetailsNavigationUnit detailsNavigationUnit = new DetailsNavigationUnit(getStateModel().mainText);
        getNavigation().open(detailsNavigationUnit);
    }

    public void onOpenNumberClicked() {
        NumberNavigationUnit numberNavigationUnit = new NumberNavigationUnit();
        numberNavigationUnit.setText(getStateModel().mainText);
        getNavigation().open(numberNavigationUnit);
    }

    public void onMainTextChanged(String text) {
        getStateModel().mainText = text;
    }
}

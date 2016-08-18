package example.reamp.number;

import etr.android.reamp.mvp.MvpPresenter;
import example.reamp.navigation.DetailsNavigationUnit;
import example.reamp.navigation.NumberNavigationUnit;

public class NumberFragmentPresenter extends MvpPresenter<NumberFragmentStateModel> {

    @Override
    public void onFirstCreate() {
        super.onFirstCreate();
        getStateModel().numberText = getNavigation().getData(new DetailsNavigationUnit());
        sendStateModel();
    }

    public void onSendBackClicked() {
        try {
            NumberNavigationUnit navigationUnit = new NumberNavigationUnit();
            navigationUnit.setNumber(Integer.parseInt(getStateModel().numberText));
            getNavigation().setResult(navigationUnit);
        } catch (Throwable e) {
            getStateModel().showErrorToast.set(e.getMessage());
            sendStateModel();
        }

    }

    public void onTextChanged(String text) {
        getStateModel().numberText = text;
    }
}

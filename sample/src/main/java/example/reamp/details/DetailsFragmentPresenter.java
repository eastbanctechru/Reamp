package example.reamp.details;

import etr.android.reamp.navigation.NavigationPresenter;
import example.reamp.navigation.DetailsNavigationUnit;

public class DetailsFragmentPresenter extends NavigationPresenter<DetailsFragmentStateModel> {

    @Override
    public void onPresenterCreated() {
        super.onPresenterCreated();
        getStateModel().text = getNavigation().getData(new DetailsNavigationUnit());
        sendStateModel();
    }

    public void onSendBackClicked() {
        getNavigation().setResult(new DetailsNavigationUnit(getStateModel().text));
    }

    public void onTextChanged(String text) {
        getStateModel().text = text;
    }
}

package example.reamp.details;

import etr.android.reamp.mvp.MvpPresenter;
import example.reamp.navigation.DetailsNavigationUnit;

public class DetailsFragmentPresenter extends MvpPresenter<DetailsFragmentStateModel> {

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

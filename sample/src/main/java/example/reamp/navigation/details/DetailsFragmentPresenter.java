package example.reamp.navigation.details;

import etr.android.reamp.mvp.ReampPresenter;

public class DetailsFragmentPresenter extends ReampPresenter<DetailsFragmentStateModel> {

    @Override
    public void onPresenterCreated() {
        super.onPresenterCreated();
        //check if we have a data
        String data = getNavigation().getData(new DetailsWithData());
        if (data != null) {
            getStateModel().showDataAction.set(data);
            sendStateModel();
        }
    }

    public void sendResultText() {
        getNavigation().setResult(new DetailsWithResultUnit().withTextResult(getStateModel().text));
    }

    public void onTextChanged(String text) {
        getStateModel().text = text;
    }
}

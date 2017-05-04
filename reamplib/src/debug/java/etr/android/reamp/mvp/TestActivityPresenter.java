package etr.android.reamp.mvp;

public class TestActivityPresenter extends MvpPresenter<TestActivityState> {

    @Override
    public void onPresenterCreated() {
        super.onPresenterCreated();
    }

    public void onClick() {
        getStateModel().clicked = true;
        sendStateModel();
    }

    public void increment() {
        getStateModel().count++;
        sendStateModel();
    }
}

package etr.android.reamp.mvp;

public class TestFragmentPresenter extends MvpPresenter<TestFragmentState> {
    public void count() {
        getStateModel().counter++;
        sendStateModel();
    }
}

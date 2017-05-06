package etr.android.reamp.debug.navigation;

import etr.android.reamp.mvp.MvpAppCompatActivity;

public class SecondActivity extends MvpAppCompatActivity<SecondPresenter, SecondState> {

    @Override
    public void onStateChanged(SecondState stateModel) {

    }

    @Override
    public SecondState onCreateStateModel() {
        return new SecondState();
    }

    @Override
    public SecondPresenter onCreatePresenter() {
        return new SecondPresenter();
    }
}

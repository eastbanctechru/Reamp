package etr.android.reamp.debug.navigation;

import android.os.Bundle;

import etr.android.reamp.R;
import etr.android.reamp.mvp.MvpAppCompatActivity;

public class FirstActivity extends MvpAppCompatActivity<FirstPresenter, FirstState> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
    }

    public void addFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.test_fragment, new NavFragment())
                .commitNow();
    }

    @Override
    public void onStateChanged(FirstState stateModel) {

    }

    @Override
    public FirstState onCreateStateModel() {
        return new FirstState();
    }

    @Override
    public FirstPresenter onCreatePresenter() {
        return new FirstPresenter();
    }
}

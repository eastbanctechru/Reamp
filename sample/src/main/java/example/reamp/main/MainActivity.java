package example.reamp.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import etr.android.reamp.mvp.MvpAppCompatActivity;
import example.reamp.R;

public class MainActivity extends MvpAppCompatActivity<MainActivityPresenter, MainActivityStateModel> {

    @Override
    public MainActivityStateModel onCreateStateModel() {
        return new MainActivityStateModel();
    }

    @Override
    public MainActivityPresenter onCreatePresenter() {
        return new MainActivityPresenter();
    }

    @Override
    public void onStateChanged(MainActivityStateModel stateModel) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.content);
        if (fragment == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content, new MainFragment())
                    .commit();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}

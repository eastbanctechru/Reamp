package etr.android.reamp.mvp.internal.navigation;

import android.os.Bundle;

import etr.android.reamp.R;
import etr.android.reamp.mvp.ReampAppCompatActivity;
import etr.android.reamp.mvp.SerializableStateModel;

public class FirstActivity extends ReampAppCompatActivity<NavigationPresenter, SerializableStateModel> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_AppCompat);
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
    public void onStateChanged(SerializableStateModel stateModel) {

    }

    @Override
    public SerializableStateModel onCreateStateModel() {
        return new SerializableStateModel();
    }

    @Override
    public NavigationPresenter onCreatePresenter() {
        return new NavigationPresenter();
    }
}

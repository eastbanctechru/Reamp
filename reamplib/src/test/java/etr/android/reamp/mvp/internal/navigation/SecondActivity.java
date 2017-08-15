package etr.android.reamp.mvp.internal.navigation;

import android.os.Bundle;

import etr.android.reamp.R;
import etr.android.reamp.mvp.ReampAppCompatActivity;
import etr.android.reamp.mvp.ReampPresenter;
import etr.android.reamp.mvp.SerializableStateModel;

public class SecondActivity extends ReampAppCompatActivity<ReampPresenter<SerializableStateModel>, SerializableStateModel> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_AppCompat);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStateChanged(SerializableStateModel stateModel) {

    }

    @Override
    public SerializableStateModel onCreateStateModel() {
        return new SerializableStateModel();
    }

    @Override
    public ReampPresenter<SerializableStateModel> onCreatePresenter() {
        return new ReampPresenter<>();
    }
}

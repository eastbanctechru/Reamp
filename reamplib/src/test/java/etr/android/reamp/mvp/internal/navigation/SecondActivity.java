package etr.android.reamp.mvp.internal.navigation;

import android.os.Bundle;

import etr.android.reamp.R;
import etr.android.reamp.mvp.MvpAppCompatActivity;
import etr.android.reamp.mvp.MvpPresenter;
import etr.android.reamp.mvp.SerializableStateModel;

public class SecondActivity extends MvpAppCompatActivity<MvpPresenter<SerializableStateModel>, SerializableStateModel> {

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
    public MvpPresenter<SerializableStateModel> onCreatePresenter() {
        return new MvpPresenter<>();
    }
}

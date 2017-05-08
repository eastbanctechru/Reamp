package etr.android.reamp.mvp.internal;

import android.os.Bundle;

import etr.android.reamp.R;
import etr.android.reamp.mvp.MvpAppCompatActivity;
import etr.android.reamp.mvp.MvpPresenter;
import etr.android.reamp.mvp.MvpStateModel;

public class NotSerializableActivity extends MvpAppCompatActivity<MvpPresenter<MvpStateModel>, MvpStateModel> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_AppCompat);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStateChanged(MvpStateModel stateModel) {

    }

    @Override
    public MvpStateModel onCreateStateModel() {
        return new MvpStateModel();
    }

    @Override
    public MvpPresenter<MvpStateModel> onCreatePresenter() {
        return new MvpPresenter<>();
    }
}

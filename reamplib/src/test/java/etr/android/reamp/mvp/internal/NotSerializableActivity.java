package etr.android.reamp.mvp.internal;

import android.os.Bundle;

import etr.android.reamp.R;
import etr.android.reamp.mvp.ReampAppCompatActivity;
import etr.android.reamp.mvp.ReampPresenter;
import etr.android.reamp.mvp.ReampStateModel;

public class NotSerializableActivity extends ReampAppCompatActivity<ReampPresenter<ReampStateModel>, ReampStateModel> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_AppCompat);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStateChanged(ReampStateModel stateModel) {

    }

    @Override
    public ReampStateModel onCreateStateModel() {
        return new ReampStateModel();
    }

    @Override
    public ReampPresenter<ReampStateModel> onCreatePresenter() {
        return new ReampPresenter<>();
    }
}

package example.reamp.navigation;

import android.os.Bundle;

import etr.android.reamp.mvp.ReampAppCompatActivity;
import etr.android.reamp.mvp.ReampPresenter;
import etr.android.reamp.mvp.SerializableStateModel;
import example.reamp.R;

public class NavigationActivity extends ReampAppCompatActivity<ReampPresenter<SerializableStateModel>, SerializableStateModel> {

    @Override
    public SerializableStateModel onCreateStateModel() {
        return new SerializableStateModel();
    }

    @Override
    public ReampPresenter<SerializableStateModel> onCreatePresenter() {
        return new ReampPresenter<>();
    }

    @Override
    public void onStateChanged(SerializableStateModel stateModel) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
    }
}

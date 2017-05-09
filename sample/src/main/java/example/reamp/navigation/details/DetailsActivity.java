package example.reamp.navigation.details;

import android.os.Bundle;

import etr.android.reamp.mvp.MvpAppCompatActivity;
import etr.android.reamp.mvp.MvpPresenter;
import etr.android.reamp.mvp.SerializableStateModel;
import example.reamp.R;

public class DetailsActivity extends MvpAppCompatActivity<MvpPresenter<SerializableStateModel>, SerializableStateModel> {

    @Override
    public SerializableStateModel onCreateStateModel() {
        return new SerializableStateModel();
    }

    @Override
    public MvpPresenter<SerializableStateModel> onCreatePresenter() {
        return new MvpPresenter<>();
    }

    @Override
    public void onStateChanged(SerializableStateModel stateModel) {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
    }
}

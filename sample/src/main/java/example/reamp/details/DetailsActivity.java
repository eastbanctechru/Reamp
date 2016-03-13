package example.reamp.details;

import android.os.Bundle;

import etr.android.reamp.mvp.MvpAppCompatActivity;
import example.reamp.R;

public class DetailsActivity extends MvpAppCompatActivity<DetailsActivityPresenter, DetailsActivityStateModel> {

    @Override
    public DetailsActivityStateModel onCreateStateModel() {
        return new DetailsActivityStateModel();
    }

    @Override
    public DetailsActivityPresenter onCreatePresenter() {
        return new DetailsActivityPresenter();
    }

    @Override
    public void onStateChanged(DetailsActivityStateModel stateModel) {
        if (getSupportFragmentManager().findFragmentById(R.id.content) == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content, new DetailsFragment())
                    .commit();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
    }
}

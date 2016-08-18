package example.reamp.number;

import android.os.Bundle;

import etr.android.reamp.mvp.MvpAppCompatActivity;
import example.reamp.R;

public class NumberActivity extends MvpAppCompatActivity<NumberActivityPresenter, NumberActivityStateModel> {

    @Override
    public NumberActivityStateModel onCreateStateModel() {
        return new NumberActivityStateModel();
    }

    @Override
    public NumberActivityPresenter onCreatePresenter() {
        return new NumberActivityPresenter();
    }

    @Override
    public void onStateChanged(NumberActivityStateModel stateModel) {
        if (getSupportFragmentManager().findFragmentById(R.id.content) == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content, new NumberFragment())
                    .commit();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number);
    }
}

package example.reamp.lifecycle;

import android.os.Bundle;
import android.widget.TextView;

import etr.android.reamp.mvp.MvpAppCompatActivity;
import example.reamp.R;

public class LongRunningActivity extends MvpAppCompatActivity<LongRunningPresenter, LongRunningState> {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_long_running_job);
        textView = (TextView) findViewById(R.id.job_text);
    }

    @Override
    public LongRunningState onCreateStateModel() {
        return new LongRunningState();
    }

    @Override
    public LongRunningPresenter onCreatePresenter() {
        return new LongRunningPresenter();
    }

    @Override
    public void onStateChanged(LongRunningState stateModel) {
        textView.setText(String.valueOf(stateModel.times));
    }
}

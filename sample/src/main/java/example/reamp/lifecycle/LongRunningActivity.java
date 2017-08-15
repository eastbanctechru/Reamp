package example.reamp.lifecycle;

import android.os.Bundle;
import android.widget.TextView;

import etr.android.reamp.mvp.ReampAppCompatActivity;
import example.reamp.R;

public class LongRunningActivity extends ReampAppCompatActivity<LongRunningPresenter, RunningState> {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_long_running_job);
        textView = (TextView) findViewById(R.id.job_text);
    }

    @Override
    public RunningState onCreateStateModel() {
        return new RunningState();
    }

    @Override
    public LongRunningPresenter onCreatePresenter() {
        return new LongRunningPresenter(1000, "LongRunningActivity");
    }

    @Override
    public void onStateChanged(RunningState stateModel) {
        textView.setText(getString(R.string.activity_timer, stateModel.times));
    }
}

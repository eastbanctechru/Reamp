package example.reamp.lifecycle;

import android.os.Bundle;
import android.widget.TextView;

import etr.android.reamp.mvp.MvpAppCompatActivity;
import example.reamp.R;

public class ShortRunningActivity extends MvpAppCompatActivity<ShortRunnintPresenter, RunningState> {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_short_running);
        textView = (TextView) findViewById(R.id.job_text);
    }

    @Override
    public RunningState onCreateStateModel() {
        return new RunningState();
    }

    @Override
    public ShortRunnintPresenter onCreatePresenter() {
        return new ShortRunnintPresenter(1000, "ShortRunningActivity");
    }

    @Override
    public void onStateChanged(RunningState stateModel) {
        textView.setText(getString(R.string.activity_timer, stateModel.times));
    }
}

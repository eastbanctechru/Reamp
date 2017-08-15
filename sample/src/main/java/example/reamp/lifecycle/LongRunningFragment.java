package example.reamp.lifecycle;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import etr.android.reamp.mvp.ReampFragment;
import example.reamp.R;

public class LongRunningFragment extends ReampFragment<LongRunningPresenter, RunningState> {


    private TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_long_running, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textView = (TextView) view.findViewById(R.id.test_text);
    }

    @Override
    public RunningState onCreateStateModel() {
        return new RunningState();
    }

    @Override
    public LongRunningPresenter onCreatePresenter() {
        return new LongRunningPresenter(1000, "LongRunningFragment");
    }

    @Override
    public void onStateChanged(RunningState stateModel) {
        textView.setText(getString(R.string.fragment_timer, stateModel.times));
    }
}

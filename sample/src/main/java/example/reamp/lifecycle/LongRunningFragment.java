package example.reamp.lifecycle;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import etr.android.reamp.mvp.MvpFragment;
import example.reamp.R;

public class LongRunningFragment extends MvpFragment<LongRunningFragmentPresenter, LongRunningFragmentState> {


    private TextView textView;

    public LongRunningFragment() {
        // Required empty public constructor
    }


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
    public LongRunningFragmentState onCreateStateModel() {
        return new LongRunningFragmentState();
    }

    @Override
    public LongRunningFragmentPresenter onCreatePresenter() {
        return new LongRunningFragmentPresenter();
    }

    @Override
    public void onStateChanged(LongRunningFragmentState stateModel) {
        textView.setText(String.valueOf(stateModel.times));
    }
}

package example.reamp.basic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import etr.android.reamp.mvp.ReampFragment;
import etr.android.reamp.mvp.ReampPresenter;
import example.reamp.R;

public class BasicFragment extends ReampFragment<BasicPresenter, BasicState> {

    private TextView counterView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_basic, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.increment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().increment();
            }
        });
        counterView = (TextView) view.findViewById(R.id.counter);
    }

    @Override
    public void onStateChanged(BasicState stateModel) {
        counterView.setText(String.valueOf(stateModel.counter));
    }

    @Override
    public BasicState onCreateStateModel() {
        return new BasicState();
    }

    @Override
    public ReampPresenter<BasicState> onCreatePresenter() {
        return new BasicPresenter();
    }
}

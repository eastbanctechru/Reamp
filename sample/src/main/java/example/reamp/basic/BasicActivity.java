package example.reamp.basic;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import etr.android.reamp.mvp.ReampAppCompatActivity;
import etr.android.reamp.mvp.ReampPresenter;
import example.reamp.R;

public class BasicActivity extends ReampAppCompatActivity<BasicPresenter, BasicState> {

    private TextView counterView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);
        findViewById(R.id.increment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().increment();
            }
        });
        counterView = (TextView) findViewById(R.id.counter);
    }

    //This method is called each time when the presenter sends you changes.
    //Setup your views according to the state passed as a parameter
    @Override
    public void onStateChanged(BasicState stateModel) {
        counterView.setText(String.valueOf(stateModel.counter));
    }

    // This method is called when Reamp asks you to create a new instance of BasicState
    @Override
    public BasicState onCreateStateModel() {
        return new BasicState();
    }

    // This method is called when Reamp asks you to create a new instance of BasicPresenter
    @Override
    public ReampPresenter<BasicState> onCreatePresenter() {
        return new BasicPresenter();
    }
}

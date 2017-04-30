package etr.android.reamp.mvp.test;

import android.os.Bundle;
import android.view.View;

import etr.android.reamp.R;
import etr.android.reamp.mvp.MvpAppCompatActivity;
import etr.android.reamp.mvp.MvpPresenter;

public class TestActivity extends MvpAppCompatActivity<TestPresenter, TestState> {

    boolean clicked;
    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().onClick();
            }
        });
    }

    @Override
    public void onStateChanged(TestState stateModel) {
        clicked = stateModel.clicked;
        count = stateModel.count;
    }

    @Override
    public TestState onCreateStateModel() {
        return new TestState();
    }

    @Override
    public MvpPresenter<TestState> onCreatePresenter() {
        return new TestPresenter();
    }
}

package etr.android.reamp.mvp;

import android.os.Bundle;
import android.view.View;

import etr.android.reamp.R;

public class TestMvpActivity extends MvpAppCompatActivity<TestActivityPresenter, TestActivityState> {

    public boolean clicked;
    public int count;

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
    public void onStateChanged(TestActivityState stateModel) {
        clicked = stateModel.clicked;
        count = stateModel.count;
    }

    @Override
    public TestActivityState onCreateStateModel() {
        return new TestActivityState();
    }

    @Override
    public MvpPresenter<TestActivityState> onCreatePresenter() {
        return new TestActivityPresenter();
    }
}

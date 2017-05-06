package etr.android.reamp.debug;

import android.os.Bundle;
import android.view.View;

import etr.android.reamp.R;
import etr.android.reamp.mvp.MvpAppCompatActivity;
import etr.android.reamp.mvp.MvpPresenter;

public class TestMvpActivity extends MvpAppCompatActivity<TestActivityPresenter, TestActivityState> {

    public boolean clicked;
    public int count;
    public Throwable throwable;
    private boolean throwOnSerializationError = true;

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
        TestActivityPresenter testActivityPresenter = new TestActivityPresenter();
        testActivityPresenter.setThrowOnSerializationError(throwOnSerializationError);
        return testActivityPresenter;
    }

    @Override
    public void onError(Throwable throwable) {
        super.onError(throwable);
        this.throwable = throwable;
    }

    public void setThrowOnSerializationError(boolean throwOnSerializationError) {
        this.throwOnSerializationError = throwOnSerializationError;
    }
}

package etr.android.reamp.mvp.internal;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import etr.android.reamp.R;
import etr.android.reamp.mvp.ReampAppCompatActivity;
import etr.android.reamp.mvp.ReampPresenter;

public class TestReampActivity extends ReampAppCompatActivity<TesteePresenter, TesteeState> {

    public int count;
    public Throwable throwable;
    private boolean throwOnSerializationError = true;
    public Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_AppCompat);
        super.onCreate(savedInstanceState);
        setContentView(makeContentView());
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().increment();
            }
        });
    }

    private View makeContentView() {
        FrameLayout frameLayout = new FrameLayout(this);
        btn = new Button(this);
        frameLayout.addView(btn);
        return frameLayout;
    }

    @Override
    public void onStateChanged(TesteeState stateModel) {
        count = stateModel.counter;
    }

    @Override
    public TesteeState onCreateStateModel() {
        return new TesteeState();
    }

    @Override
    public ReampPresenter<TesteeState> onCreatePresenter() {
        TesteePresenter testActivityPresenter = new TesteePresenter();
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

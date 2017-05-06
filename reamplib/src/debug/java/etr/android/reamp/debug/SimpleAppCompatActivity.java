package etr.android.reamp.debug;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

import etr.android.reamp.R;

public class SimpleAppCompatActivity extends AppCompatActivity {

    private TestMvpFragment dynamicFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);
        if (savedInstanceState != null) {
            dynamicFragment = getDynamicFragment();
        } else {
            dynamicFragment = new TestMvpFragment();
        }
    }

    public TestCustomView getCustomView() {
        return (TestCustomView) findViewById(R.id.custom_view);
    }

    public TestMvpFragment getEmbeddedFragment() {
        return (TestMvpFragment) getSupportFragmentManager().findFragmentById(R.id.test_fragment);
    }

    public void addFragmentProgrammatically() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.dynamic_container, dynamicFragment)
                .commitNow();
    }

    public TestMvpFragment getDynamicFragment() {
        return (TestMvpFragment) getSupportFragmentManager().findFragmentById(R.id.dynamic_container);
    }

    public void removeFragmentProgrammatically() {
        TestMvpFragment fragment = getDynamicFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .remove(fragment)
                .commitNow();
    }

    public void removeView() {
        ((ViewGroup) findViewById(R.id.root)).removeView(findViewById(R.id.custom_view));
    }
}

package etr.android.reamp.mvp.internal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

import etr.android.reamp.R;

public class RegularAppCompatActivity extends AppCompatActivity {

    private TestReampFragment dynamicFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.Theme_AppCompat);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);
        if (savedInstanceState != null) {
            dynamicFragment = getDynamicFragment();
        } else {
            dynamicFragment = new TestReampFragment();
        }
    }

    public TestReampCustomView getCustomView() {
        return (TestReampCustomView) findViewById(R.id.custom_view);
    }

    public TestReampFragment getEmbeddedFragment() {
        return (TestReampFragment) getSupportFragmentManager().findFragmentById(R.id.test_fragment);
    }

    public void addFragmentProgrammatically() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.dynamic_container, dynamicFragment)
                .commitNow();
    }

    public TestReampFragment getDynamicFragment() {
        return (TestReampFragment) getSupportFragmentManager().findFragmentById(R.id.dynamic_container);
    }

    public void removeFragmentProgrammatically() {
        TestReampFragment fragment = getDynamicFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .remove(fragment)
                .commitNow();
    }

    public void removeView() {
        ((ViewGroup) findViewById(R.id.root)).removeView(findViewById(R.id.custom_view));
    }
}

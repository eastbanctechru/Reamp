package etr.android.reamp.mvp;

import android.app.Activity;
import android.content.Context;

import org.junit.Test;

public class MvpDelegateTest extends BaseTest {

    @Test
    public void normalBehaviorWithRegularActivity() throws Exception {
        MockActivity activity = new MockActivity();
        MvpDelegate mvpDelegate = new MvpDelegate(activity);
        mvpDelegate.onResult(0, 0, null);

    }

    private class MockActivity extends Activity implements MvpView {

        @Override
        public Context getContext() {
            return null;
        }

        @Override
        public void onStateChanged(MvpStateModel stateModel) {

        }

        @Override
        public void onError(Throwable throwable) {

        }

        @Override
        public String getMvpId() {
            return null;
        }

        @Override
        public MvpStateModel onCreateStateModel() {
            return null;
        }

        @Override
        public MvpPresenter onCreatePresenter() {
            return null;
        }

        @Override
        public MvpPresenter getPresenter() {
            return new MvpPresenter();
        }
    }
}
package etr.android.reamp.mvp;

import android.os.Handler;
import android.os.Looper;

import org.junit.Assert;
import org.junit.Test;
import org.robolectric.Robolectric;
import org.robolectric.shadow.api.Shadow;

import java.util.concurrent.CountDownLatch;

import etr.android.reamp.mvp.common.TestPresenter;
import etr.android.reamp.mvp.common.TestState;
import etr.android.reamp.mvp.common.TestView;

public class AsyncTest extends BaseTest {

    @Test
    public void testEventsOnMainThread() throws Exception {

        final CountDownLatch cdl = new CountDownLatch(1);

        TestView testView = new TestView() {
            @Override
            public void onStateChanged(TestState stateModel) {
                super.onStateChanged(stateModel);
                System.out.println(stateModel.counter);
                Assert.assertTrue(Looper.myLooper() == Looper.getMainLooper());
                if (stateModel.counter == 1) {
                    cdl.countDown();
                }
            }
        };

        testView.onCreate();
        testView.connect();
        final TestPresenter presenter = testView.getPresenter();

        new Thread() {
            @Override
            public void run() {
                super.run();
                presenter.count();
            }
        }.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Robolectric.flushForegroundThreadScheduler();
        org.robolectric.shadows.ShadowLooper.runUiThreadTasks();
        org.robolectric.shadows.ShadowLooper.runUiThreadTasksIncludingDelayedTasks();

        cdl.await();
    }

}

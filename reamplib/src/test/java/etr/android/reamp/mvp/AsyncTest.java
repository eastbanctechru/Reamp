package etr.android.reamp.mvp;

import android.os.Looper;

import org.junit.Assert;
import org.junit.Test;
import org.robolectric.Robolectric;

import java.util.concurrent.CountDownLatch;

import etr.android.reamp.mvp.internal.SimpleView;
import etr.android.reamp.mvp.internal.TesteePresenter;
import etr.android.reamp.mvp.internal.TesteeState;

public class AsyncTest extends BaseTest {

    @Test
    public void testEventsOnMainThread() throws Exception {

        final CountDownLatch cdl = new CountDownLatch(1);

        SimpleView simpleView = new SimpleView() {
            @Override
            public void onStateChanged(TesteeState stateModel) {
                super.onStateChanged(stateModel);
                Assert.assertTrue(Looper.myLooper() == Looper.getMainLooper());
                if (stateModel.counter == 1) {
                    cdl.countDown();
                }
            }
        };

        simpleView.onCreate();
        simpleView.connect();
        final TesteePresenter presenter = simpleView.getPresenter();

        new Thread() {
            @Override
            public void run() {
                super.run();
                presenter.increment();
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

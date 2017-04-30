package etr.android.reamp.mvp.test;

import android.os.Bundle;
import android.widget.Button;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.robolectric.Robolectric;
import org.robolectric.android.controller.ActivityController;

import etr.android.reamp.R;
import etr.android.reamp.mvp.BaseTest;
import etr.android.reamp.mvp.MvpPresenter;
import etr.android.reamp.mvp.PresenterManager;
import etr.android.reamp.mvp.ReampProvider;

public class TestActivityTest extends BaseTest {

    @Before
    public void setUp() throws Exception {
        Robolectric.setupContentProvider(ReampProvider.class);
    }

    @Test
    public void simple() throws Exception {
        TestActivity activity = Robolectric.setupActivity(TestActivity.class);
        Assert.assertFalse(activity.clicked);
        TestPresenter presenter = activity.getPresenter();
        Assert.assertNotNull(presenter);
        Button button = (Button) activity.findViewById(R.id.btn);
        button.performClick();
        Assert.assertTrue(presenter.getStateModel().clicked);
        Assert.assertTrue(activity.clicked);
    }

    @Test
    public void keepState() throws Exception {
        ActivityController<TestActivity> controller = Robolectric.buildActivity(TestActivity.class);
        TestActivity activity = controller.setup().get();
        Assert.assertFalse(activity.clicked);
        Button button = (Button) activity.findViewById(R.id.btn);
        button.performClick();
        Bundle bundle = new Bundle();
        controller.saveInstanceState(bundle).pause().stop().destroy();
        activity = Robolectric.buildActivity(TestActivity.class).create(bundle).start().restoreInstanceState(bundle).resume().get();
        Assert.assertTrue(activity.clicked);
    }

    @Test
    public void keepRunning() throws Exception {
        ActivityController<TestActivity> controller = Robolectric.buildActivity(TestActivity.class);
        TestActivity activity = controller.setup().get();
        Assert.assertEquals(activity.count, 0);
        TestPresenter presenter = activity.getPresenter();
        Bundle bundle = new Bundle();
        controller.saveInstanceState(bundle).pause().stop().destroy();
        for (int i = 0; i < 10; i++) {
            presenter.increment();
        }
        activity = Robolectric.buildActivity(TestActivity.class).create(bundle).start().restoreInstanceState(bundle).resume().get();
        Assert.assertEquals(activity.count, 10);
    }

    @Test
    public void destroyProvider() throws Exception {
        ActivityController<TestActivity> controller = Robolectric.buildActivity(TestActivity.class);
        TestActivity testActivity = controller.get();
        controller.create().start().resume().visible();
        String mvpId = testActivity.getMvpId();
        controller.userLeaving();
        testActivity.finish(); //make activity to think that it is being finished
        controller.pause().stop().destroy();
        MvpPresenter presenter = PresenterManager.getInstance().getPresenter(mvpId);
        Assert.assertNull(presenter);
    }

    @Test
    public void doNotDestroyProvider() throws Exception {
        ActivityController<TestActivity> controller = Robolectric.buildActivity(TestActivity.class);
        TestActivity testActivity = controller.get();
        controller.create().start().resume().visible();
        String mvpId = testActivity.getMvpId();
        controller.pause().stop().destroy();
        MvpPresenter presenter = PresenterManager.getInstance().getPresenter(mvpId);
        Assert.assertNotNull(presenter);
    }

    @Test
    public void activityLeak() throws Exception {
        ActivityController<TestActivity> controller = Robolectric.buildActivity(TestActivity.class);
        TestActivity testActivity = controller.get();
        controller.create().start().resume().visible();
        TestPresenter presenter = testActivity.getPresenter();
        controller.pause().stop().destroy();
        Assert.assertNull(testActivity.getPresenter());
        Assert.assertNull(presenter.getView());
    }
}

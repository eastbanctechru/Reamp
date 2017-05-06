package etr.android.reamp.mvp.integrations;

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
import etr.android.reamp.debug.TestActivityPresenter;
import etr.android.reamp.debug.TestMvpActivity;

public class TestActivityTest extends BaseTest {

    @Before
    public void setUp() throws Exception {
        Robolectric.setupContentProvider(ReampProvider.class);
    }

    @Test
    public void simple() throws Exception {
        TestMvpActivity activity = Robolectric.setupActivity(TestMvpActivity.class);
        Assert.assertFalse(activity.clicked);
        TestActivityPresenter presenter = activity.getPresenter();
        Assert.assertNotNull(presenter);
        Button button = (Button) activity.findViewById(R.id.btn);
        button.performClick();
        Assert.assertTrue(presenter.getStateModel().clicked);
        Assert.assertTrue(activity.clicked);
    }

    @Test
    public void keepState() throws Exception {
        ActivityController<TestMvpActivity> controller = Robolectric.buildActivity(TestMvpActivity.class);
        TestMvpActivity activity = controller.setup().get();
        Assert.assertFalse(activity.clicked);
        Button button = (Button) activity.findViewById(R.id.btn);
        button.performClick();
        Bundle bundle = new Bundle();
        controller.saveInstanceState(bundle).pause().stop().destroy();
        activity = Robolectric.buildActivity(TestMvpActivity.class).create(bundle).start().restoreInstanceState(bundle).resume().get();
        Assert.assertTrue(activity.clicked);
    }

    @Test
    public void keepRunning() throws Exception {
        ActivityController<TestMvpActivity> controller = Robolectric.buildActivity(TestMvpActivity.class);
        TestMvpActivity activity = controller.setup().get();
        Assert.assertEquals(activity.count, 0);
        TestActivityPresenter presenter = activity.getPresenter();
        Bundle bundle = new Bundle();
        controller.saveInstanceState(bundle).pause().stop().destroy();
        for (int i = 0; i < 10; i++) {
            presenter.increment();
        }

        //activity should not receive updates while is detached
        Assert.assertEquals(activity.count, 0);

        activity = Robolectric.buildActivity(TestMvpActivity.class).create(bundle).start().restoreInstanceState(bundle).resume().get();
        Assert.assertEquals(activity.count, 10);
    }

    @Test
    public void destroyPresenter() throws Exception {
        ActivityController<TestMvpActivity> controller = Robolectric.buildActivity(TestMvpActivity.class);
        TestMvpActivity testMvpActivity = controller.get();
        controller.create().start().resume().visible();
        String mvpId = testMvpActivity.getMvpId();
        controller.userLeaving();
        testMvpActivity.finish(); //make activity to think that it is being finished
        controller.pause().stop().destroy();
        MvpPresenter presenter = PresenterManager.getInstance().getPresenter(mvpId);
        Assert.assertNull(presenter);
    }

    @Test
    public void doNotDestroyPresenter() throws Exception {
        ActivityController<TestMvpActivity> controller = Robolectric.buildActivity(TestMvpActivity.class);
        TestMvpActivity testMvpActivity = controller.get();
        controller.create().start().resume().visible();
        String mvpId = testMvpActivity.getMvpId();
        controller.pause().stop().destroy();
        MvpPresenter presenter = PresenterManager.getInstance().getPresenter(mvpId);
        Assert.assertNotNull(presenter);
    }

    @Test
    public void activityLeak() throws Exception {
        ActivityController<TestMvpActivity> controller = Robolectric.buildActivity(TestMvpActivity.class);
        TestMvpActivity testMvpActivity = controller.get();
        controller.create().start().resume().visible();
        TestActivityPresenter presenter = testMvpActivity.getPresenter();
        controller.pause().stop().destroy();
        Assert.assertNull(testMvpActivity.getPresenter());
        Assert.assertNull(presenter.getView());
    }

    @Test
    public void restoreState() throws Exception {
        ActivityController<TestMvpActivity> controller = Robolectric.buildActivity(TestMvpActivity.class);
        TestMvpActivity activity = controller.setup().get();
        Assert.assertEquals(activity.count, 0);
        activity.getPresenter().increment();
        String mvpId = activity.getMvpId();
        Bundle bundle = new Bundle();
        controller.saveInstanceState(bundle).pause().stop().destroy();
        PresenterManager.getInstance().destroyPresenter(mvpId); // kill the presenter's instance to force the state to beresored
        activity = Robolectric.buildActivity(TestMvpActivity.class).create(bundle).start().restoreInstanceState(bundle).resume().get();
        Assert.assertEquals(activity.count, 1);
    }
}

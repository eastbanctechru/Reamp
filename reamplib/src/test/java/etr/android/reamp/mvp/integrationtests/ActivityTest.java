package etr.android.reamp.mvp.integrationtests;

import android.content.Context;
import android.os.Bundle;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.robolectric.Robolectric;
import org.robolectric.android.controller.ActivityController;

import java.util.List;

import etr.android.reamp.mvp.BaseTest;
import etr.android.reamp.mvp.ReampPresenter;
import etr.android.reamp.mvp.ReampView;
import etr.android.reamp.mvp.PresenterManager;
import etr.android.reamp.mvp.ReampProvider;
import etr.android.reamp.mvp.internal.TesteePresenter;
import etr.android.reamp.mvp.internal.TestReampActivity;

public class ActivityTest extends BaseTest {

    @Before
    public void setUp() throws Exception {
        Robolectric.setupContentProvider(ReampProvider.class);
    }

    @Test
    public void simple() throws Exception {
        TestReampActivity activity = Robolectric.setupActivity(TestReampActivity.class);
        Assert.assertEquals(activity.count, 0);

        TesteePresenter presenter = activity.getPresenter();
        activity.btn.performClick();

        Assert.assertEquals(presenter.getStateModel().counter, 1);
        Assert.assertEquals(activity.count, 1);
    }

    @Test
    public void keepState() throws Exception {
        ActivityController<TestReampActivity> controller = Robolectric.buildActivity(TestReampActivity.class);
        TestReampActivity activity = controller.setup().get();
        TesteePresenter presenter = activity.getPresenter();
        Assert.assertEquals(activity.count, 0);
        activity.btn.performClick();
        Assert.assertEquals(activity.count, 1);

        Bundle bundle = new Bundle();
        controller.saveInstanceState(bundle).pause().stop().destroy();

        activity = Robolectric.buildActivity(TestReampActivity.class).create(bundle).start().restoreInstanceState(bundle).resume().get();
        Assert.assertEquals(activity.count, 1);
    }

    @Test
    public void testPresenterCallbacks() throws Exception {
        ActivityController<TestReampActivity> controller = Robolectric.buildActivity(TestReampActivity.class);
        TestReampActivity activity = controller.setup().get();
        TesteePresenter presenter = activity.getPresenter();

        assertPresenterClearInitState(presenter);

        Bundle bundle = new Bundle();
        controller.saveInstanceState(bundle).pause().stop().destroy();

        Assert.assertEquals(presenter.onDisconnected, 1);
        Assert.assertEquals(presenter.destroyPresenter, 0);

        controller = Robolectric.buildActivity(TestReampActivity.class).create(bundle).start().restoreInstanceState(bundle).resume().visible();
        activity = controller.get();

        Assert.assertEquals(presenter.presenterCreated, 1);
        Assert.assertEquals(presenter.onConnected, 2);

        activity.finish();
        controller.pause().stop().destroy();

        Assert.assertEquals(presenter.onDisconnected, 2);
        Assert.assertEquals(presenter.destroyPresenter, 1);
    }

    @Test
    public void keepRunning() throws Exception {
        ActivityController<TestReampActivity> controller = Robolectric.buildActivity(TestReampActivity.class);
        TestReampActivity activity = controller.setup().get();
        Assert.assertEquals(activity.count, 0);
        TesteePresenter presenter = activity.getPresenter();
        Bundle bundle = new Bundle();
        controller.saveInstanceState(bundle).pause().stop().destroy();
        for (int i = 0; i < 10; i++) {
            presenter.increment();
        }

        //activity should not receive updates while is detached
        Assert.assertEquals(activity.count, 0);

        activity = Robolectric.buildActivity(TestReampActivity.class).create(bundle).start().restoreInstanceState(bundle).resume().get();
        Assert.assertEquals(activity.count, 10);
    }

    @Test
    public void destroyPresenter() throws Exception {
        ActivityController<TestReampActivity> controller = Robolectric.buildActivity(TestReampActivity.class);
        TestReampActivity testMvpActivity = controller.get();
        controller.create().start().resume().visible();
        String mvpId = testMvpActivity.getMvpId();
        controller.userLeaving();
        testMvpActivity.finish(); //make activity to think that it is being finished
        controller.pause().stop().destroy();
        ReampPresenter presenter = PresenterManager.getInstance().getPresenter(mvpId);
        Assert.assertNull(presenter);
        List<ReampView> reampViews = PresenterManager.getInstance().getViewsOf(testMvpActivity);
        Assert.assertTrue(reampViews.isEmpty());
    }

    @Test
    public void doNotDestroyPresenter() throws Exception {
        ActivityController<TestReampActivity> controller = Robolectric.buildActivity(TestReampActivity.class);
        TestReampActivity testMvpActivity = controller.get();
        controller.create().start().resume().visible();
        String mvpId = testMvpActivity.getMvpId();
        controller.pause().stop().destroy();
        ReampPresenter presenter = PresenterManager.getInstance().getPresenter(mvpId);
        Assert.assertNotNull(presenter);
        List<ReampView> reampViews = PresenterManager.getInstance().getViewsOf(testMvpActivity);
        Assert.assertTrue(reampViews.isEmpty());
    }

    @Test
    public void activityLeak() throws Exception {
        ActivityController<TestReampActivity> controller = Robolectric.buildActivity(TestReampActivity.class);
        TestReampActivity testMvpActivity = controller.get();
        controller.create().start().resume().visible();
        TesteePresenter presenter = testMvpActivity.getPresenter();
        controller.pause().stop().destroy();
        Assert.assertNull(presenter.getView());
        List<ReampView> reampViews = PresenterManager.getInstance().getViewsOf(testMvpActivity);
        Assert.assertTrue(reampViews.isEmpty());
    }

    @Test
    public void restoreState() throws Exception {
        ActivityController<TestReampActivity> controller = Robolectric.buildActivity(TestReampActivity.class);
        TestReampActivity activity = controller.setup().get();
        Assert.assertEquals(activity.count, 0);
        activity.getPresenter().increment();
        String mvpId = activity.getMvpId();
        Bundle bundle = new Bundle();
        controller.saveInstanceState(bundle).pause().stop().destroy();
        PresenterManager.getInstance().destroyPresenter(mvpId); // kill the presenter's instance to force the state to beresored
        activity = Robolectric.buildActivity(TestReampActivity.class).create(bundle).start().restoreInstanceState(bundle).resume().get();
        Assert.assertEquals(activity.count, 1);
        Assert.assertEquals(activity.getPresenter().presenterCreated, 1);
    }

    @Test
    public void errorMessage() throws Exception {
        TestReampActivity activity = Robolectric.setupActivity(TestReampActivity.class);
        TesteePresenter presenter = activity.getPresenter();
        presenter.sendError();
        Assert.assertNotNull(activity.throwable);
    }

    @Test
    public void context() throws Exception {
        TestReampActivity activity = Robolectric.setupActivity(TestReampActivity.class);
        Context context = activity.getContext();
        Assert.assertNotNull(context);
    }

    private void assertPresenterClearInitState(TesteePresenter presenter) {
        Assert.assertEquals(presenter.getStateModel().counter, 0);
        Assert.assertEquals(presenter.presenterCreated, 1);
        Assert.assertEquals(presenter.onConnected, 1);
        Assert.assertEquals(presenter.destroyPresenter, 0);
        Assert.assertEquals(presenter.onDisconnected, 0);
    }


}

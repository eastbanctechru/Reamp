package etr.android.reamp.mvp.integrationtests;

import android.os.Bundle;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.robolectric.Robolectric;
import org.robolectric.android.controller.ActivityController;

import etr.android.reamp.mvp.internal.TesteePresenter;
import etr.android.reamp.mvp.internal.TesteeState;
import etr.android.reamp.mvp.BaseTest;
import etr.android.reamp.mvp.ReampPresenter;
import etr.android.reamp.mvp.PresenterManager;
import etr.android.reamp.mvp.ReampProvider;
import etr.android.reamp.mvp.internal.RegularAppCompatActivity;
import etr.android.reamp.mvp.internal.TestReampFragment;

public class SupportFragmentTest extends BaseTest {

    @Before
    public void setUp() throws Exception {
        Robolectric.setupContentProvider(ReampProvider.class);
    }

    @Test
    public void simple() throws Exception {
        RegularAppCompatActivity activity = Robolectric.setupActivity(RegularAppCompatActivity.class);
        TestReampFragment fragment = activity.getEmbeddedFragment();
        TesteePresenter presenter = fragment.getPresenter();
        Assert.assertNotNull(presenter);
        TesteeState stateModel = presenter.getStateModel();
        Assert.assertNotNull(stateModel);
        presenter.increment();
        Assert.assertEquals(fragment.counter, 1);
    }

    @Test
    public void keepState() throws Exception {
        ActivityController<RegularAppCompatActivity> controller = Robolectric.buildActivity(RegularAppCompatActivity.class);
        RegularAppCompatActivity activity = controller.setup().get();
        TestReampFragment fragment = activity.getEmbeddedFragment();
        fragment.getPresenter().increment();
        Bundle bundle = new Bundle();
        controller.saveInstanceState(bundle).pause().stop().destroy();
        activity = Robolectric.buildActivity(RegularAppCompatActivity.class).create(bundle).start().restoreInstanceState(bundle).resume().get();
        fragment = activity.getEmbeddedFragment();
        TesteePresenter presenter = fragment.getPresenter();
        Assert.assertNotNull(presenter);
        TesteeState stateModel = presenter.getStateModel();
        Assert.assertNotNull(stateModel);
        Assert.assertEquals(stateModel.counter, 1);
        Assert.assertEquals(fragment.counter, 1);
    }

    @Test
    public void keepRunning() throws Exception {
        ActivityController<RegularAppCompatActivity> controller = Robolectric.buildActivity(RegularAppCompatActivity.class);
        RegularAppCompatActivity activity = controller.setup().get();
        TestReampFragment fragment = activity.getEmbeddedFragment();
        Assert.assertEquals(fragment.counter, 0);
        TesteePresenter presenter = fragment.getPresenter();
        Bundle bundle = new Bundle();
        controller.saveInstanceState(bundle).pause().stop().destroy();
        for (int i = 0; i < 10; i++) {
            presenter.increment();
        }

        //fragment should not receive updates while is detached
        Assert.assertEquals(fragment.counter, 0);

        activity = Robolectric.buildActivity(RegularAppCompatActivity.class).create(bundle).start().restoreInstanceState(bundle).resume().get();
        fragment = activity.getEmbeddedFragment();
        Assert.assertEquals(fragment.counter, 10);
    }

    @Test
    public void destroyPresenter() throws Exception {
        ActivityController<RegularAppCompatActivity> controller = Robolectric.buildActivity(RegularAppCompatActivity.class);
        RegularAppCompatActivity activity = controller.get();
        controller.create().start().resume().visible();
        TestReampFragment fragment = activity.getEmbeddedFragment();
        String mvpId = fragment.getMvpId();
        controller.userLeaving();
        activity.finish(); //make activity to think that it is being finished
        controller.pause().stop().destroy();
        ReampPresenter presenter = PresenterManager.getInstance().getPresenter(mvpId);
        Assert.assertNull(presenter);
    }

    @Test
    public void doNotDestroyPresenter() throws Exception {
        ActivityController<RegularAppCompatActivity> controller = Robolectric.buildActivity(RegularAppCompatActivity.class);
        RegularAppCompatActivity activity = controller.get();
        controller.create().start().resume().visible();
        TestReampFragment fragment = activity.getEmbeddedFragment();
        String mvpId = fragment.getMvpId();
        controller.pause().stop().destroy();
        ReampPresenter presenter = PresenterManager.getInstance().getPresenter(mvpId);
        Assert.assertNotNull(presenter);
    }

    @Test
    public void fragmentLeak() throws Exception {
        ActivityController<RegularAppCompatActivity> controller = Robolectric.buildActivity(RegularAppCompatActivity.class);
        RegularAppCompatActivity activity = controller.get();
        controller.create().start().resume().visible();
        TestReampFragment fragment = activity.getEmbeddedFragment();
        TesteePresenter presenter = fragment.getPresenter();
        controller.pause().stop().destroy();
        Assert.assertNull(presenter.getView());
    }

    @Test
    public void simpleDynamicFragment() throws Exception {
        ActivityController<RegularAppCompatActivity> controller = Robolectric.buildActivity(RegularAppCompatActivity.class);
        RegularAppCompatActivity activity = controller.get();
        controller.create().start().resume().visible();
        activity.addFragmentProgrammatically();
        TestReampFragment fragment = activity.getDynamicFragment();
        TesteePresenter presenter = fragment.getPresenter();
        Assert.assertNotNull(presenter);
        TesteeState stateModel = presenter.getStateModel();
        Assert.assertNotNull(stateModel);
        presenter.increment();
        Assert.assertEquals(fragment.counter, 1);
    }

    @Test
    public void dynamicFragmentKeepState() throws Exception {
        ActivityController<RegularAppCompatActivity> controller = Robolectric.buildActivity(RegularAppCompatActivity.class);
        RegularAppCompatActivity activity = controller.setup().get();
        activity.addFragmentProgrammatically();
        TestReampFragment fragment = activity.getDynamicFragment();
        fragment.getPresenter().increment();
        Bundle bundle = new Bundle();
        controller.saveInstanceState(bundle).pause().stop().destroy();
        activity = Robolectric.buildActivity(RegularAppCompatActivity.class).create(bundle).start().restoreInstanceState(bundle).resume().get();
        fragment = activity.getDynamicFragment();
        TesteePresenter presenter = fragment.getPresenter();
        Assert.assertNotNull(presenter);
        TesteeState stateModel = presenter.getStateModel();
        Assert.assertNotNull(stateModel);
        Assert.assertEquals(stateModel.counter, 1);
        Assert.assertEquals(fragment.counter, 1);
    }

    @Test
    public void dynamicFragmentKeepRunning() throws Exception {
        ActivityController<RegularAppCompatActivity> controller = Robolectric.buildActivity(RegularAppCompatActivity.class);
        RegularAppCompatActivity activity = controller.setup().get();
        activity.addFragmentProgrammatically();
        TestReampFragment fragment = activity.getDynamicFragment();
        Assert.assertEquals(fragment.counter, 0);
        TesteePresenter presenter = fragment.getPresenter();
        activity.removeFragmentProgrammatically();
        for (int i = 0; i < 10; i++) {
            presenter.increment();
        }

        //fragment should not receive updates while is detached
        Assert.assertEquals(fragment.counter, 0);

        activity.addFragmentProgrammatically();
        Assert.assertEquals(fragment.counter, 10);
    }

    @Test
    public void dynamicFragmentLeak() throws Exception {
        ActivityController<RegularAppCompatActivity> controller = Robolectric.buildActivity(RegularAppCompatActivity.class);
        RegularAppCompatActivity activity = controller.setup().get();
        activity.addFragmentProgrammatically();
        TestReampFragment fragment = activity.getDynamicFragment();
        String mvpId = fragment.getMvpId();
        activity.removeFragmentProgrammatically();
        controller.get().finish();
        controller.pause().stop().destroy();
        ReampPresenter presenter = PresenterManager.getInstance().getPresenter(mvpId);
        Assert.assertNull(presenter);
    }

    @Test
    public void errorMessage() throws Exception {
        RegularAppCompatActivity activity = Robolectric.setupActivity(RegularAppCompatActivity.class);
        TestReampFragment fragment = activity.getEmbeddedFragment();
        TesteePresenter presenter = fragment.getPresenter();
        presenter.sendError();
        Assert.assertNotNull(fragment.throwable);
    }


    @Test
    public void context() throws Exception {
        RegularAppCompatActivity activity = Robolectric.setupActivity(RegularAppCompatActivity.class);
        TestReampFragment fragment = activity.getEmbeddedFragment();
        Assert.assertNotNull(fragment.getContext());
    }
}

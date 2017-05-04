package etr.android.reamp.mvp.integrations;

import android.os.Bundle;

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
import etr.android.reamp.mvp.SimpleAppCompatActivity;
import etr.android.reamp.mvp.TestFragmentPresenter;
import etr.android.reamp.mvp.TestFragmentState;
import etr.android.reamp.mvp.TestMvpFragment;

public class StandAloneFragmentTest extends BaseTest {

    @Before
    public void setUp() throws Exception {
        Robolectric.setupContentProvider(ReampProvider.class);
    }

    @Test
    public void simple() throws Exception {
        SimpleAppCompatActivity activity = Robolectric.setupActivity(SimpleAppCompatActivity.class);
        TestMvpFragment fragment = activity.getEmbeddedFragment();
        TestFragmentPresenter presenter = fragment.getPresenter();
        Assert.assertNotNull(presenter);
        TestFragmentState stateModel = presenter.getStateModel();
        Assert.assertNotNull(stateModel);
        presenter.count();
        Assert.assertEquals(fragment.counter, 1);
    }

    @Test
    public void keepState() throws Exception {
        ActivityController<SimpleAppCompatActivity> controller = Robolectric.buildActivity(SimpleAppCompatActivity.class);
        SimpleAppCompatActivity activity = controller.setup().get();
        TestMvpFragment fragment = activity.getEmbeddedFragment();
        fragment.getPresenter().count();
        Bundle bundle = new Bundle();
        controller.saveInstanceState(bundle).pause().stop().destroy();
        activity = Robolectric.buildActivity(SimpleAppCompatActivity.class).create(bundle).start().restoreInstanceState(bundle).resume().get();
        fragment = activity.getEmbeddedFragment();
        TestFragmentPresenter presenter = fragment.getPresenter();
        Assert.assertNotNull(presenter);
        TestFragmentState stateModel = presenter.getStateModel();
        Assert.assertNotNull(stateModel);
        Assert.assertEquals(stateModel.counter, 1);
        Assert.assertEquals(fragment.counter, 1);
    }

    @Test
    public void keepRunning() throws Exception {
        ActivityController<SimpleAppCompatActivity> controller = Robolectric.buildActivity(SimpleAppCompatActivity.class);
        SimpleAppCompatActivity activity = controller.setup().get();
        TestMvpFragment fragment = activity.getEmbeddedFragment();
        Assert.assertEquals(fragment.counter, 0);
        TestFragmentPresenter presenter = fragment.getPresenter();
        Bundle bundle = new Bundle();
        controller.saveInstanceState(bundle).pause().stop().destroy();
        for (int i = 0; i < 10; i++) {
            presenter.count();
        }

        //fragment should not receive updates while is detached
        Assert.assertEquals(fragment.counter, 0);

        activity = Robolectric.buildActivity(SimpleAppCompatActivity.class).create(bundle).start().restoreInstanceState(bundle).resume().get();
        fragment = activity.getEmbeddedFragment();
        Assert.assertEquals(fragment.counter, 10);
    }

    @Test
    public void destroyPresenter() throws Exception {
        ActivityController<SimpleAppCompatActivity> controller = Robolectric.buildActivity(SimpleAppCompatActivity.class);
        SimpleAppCompatActivity activity = controller.get();
        controller.create().start().resume().visible();
        TestMvpFragment fragment = activity.getEmbeddedFragment();
        String mvpId = fragment.getMvpId();
        controller.userLeaving();
        activity.finish(); //make activity to think that it is being finished
        controller.pause().stop().destroy();
        MvpPresenter presenter = PresenterManager.getInstance().getPresenter(mvpId);
        Assert.assertNull(presenter);
    }

    @Test
    public void doNotDestroyPresenter() throws Exception {
        ActivityController<SimpleAppCompatActivity> controller = Robolectric.buildActivity(SimpleAppCompatActivity.class);
        SimpleAppCompatActivity activity = controller.get();
        controller.create().start().resume().visible();
        TestMvpFragment fragment = activity.getEmbeddedFragment();
        String mvpId = fragment.getMvpId();
        controller.pause().stop().destroy();
        MvpPresenter presenter = PresenterManager.getInstance().getPresenter(mvpId);
        Assert.assertNotNull(presenter);
    }

    @Test
    public void fragmentLeak() throws Exception {
        ActivityController<SimpleAppCompatActivity> controller = Robolectric.buildActivity(SimpleAppCompatActivity.class);
        SimpleAppCompatActivity activity = controller.get();
        controller.create().start().resume().visible();
        TestMvpFragment fragment = activity.getEmbeddedFragment();
        TestFragmentPresenter presenter = fragment.getPresenter();
        controller.pause().stop().destroy();
        Assert.assertNull(fragment.getPresenter());
        Assert.assertNull(presenter.getView());
    }

    @Test
    public void simpleDynamicFragment() throws Exception {
        ActivityController<SimpleAppCompatActivity> controller = Robolectric.buildActivity(SimpleAppCompatActivity.class);
        SimpleAppCompatActivity activity = controller.get();
        controller.create().start().resume().visible();
        activity.addFragmentProgrammatically();
        TestMvpFragment fragment = activity.getDynamicFragment();
        TestFragmentPresenter presenter = fragment.getPresenter();
        Assert.assertNotNull(presenter);
        TestFragmentState stateModel = presenter.getStateModel();
        Assert.assertNotNull(stateModel);
        presenter.count();
        Assert.assertEquals(fragment.counter, 1);
    }

    @Test
    public void dynamicFragmentKeepState() throws Exception {
        ActivityController<SimpleAppCompatActivity> controller = Robolectric.buildActivity(SimpleAppCompatActivity.class);
        SimpleAppCompatActivity activity = controller.setup().get();
        activity.addFragmentProgrammatically();
        TestMvpFragment fragment = activity.getDynamicFragment();
        fragment.getPresenter().count();
        Bundle bundle = new Bundle();
        controller.saveInstanceState(bundle).pause().stop().destroy();
        activity = Robolectric.buildActivity(SimpleAppCompatActivity.class).create(bundle).start().restoreInstanceState(bundle).resume().get();
        fragment = activity.getDynamicFragment();
        TestFragmentPresenter presenter = fragment.getPresenter();
        Assert.assertNotNull(presenter);
        TestFragmentState stateModel = presenter.getStateModel();
        Assert.assertNotNull(stateModel);
        Assert.assertEquals(stateModel.counter, 1);
        Assert.assertEquals(fragment.counter, 1);
    }

    @Test
    public void dynamicFragmentKeepRunning() throws Exception {
        ActivityController<SimpleAppCompatActivity> controller = Robolectric.buildActivity(SimpleAppCompatActivity.class);
        SimpleAppCompatActivity activity = controller.setup().get();
        activity.addFragmentProgrammatically();
        TestMvpFragment fragment = activity.getDynamicFragment();
        Assert.assertEquals(fragment.counter, 0);
        TestFragmentPresenter presenter = fragment.getPresenter();
        activity.removeFragmentProgrammatically();
        for (int i = 0; i < 10; i++) {
            presenter.count();
        }

        //fragment should not receive updates while is detached
        Assert.assertEquals(fragment.counter, 0);

        activity.addFragmentProgrammatically();
        Assert.assertEquals(fragment.counter, 10);
    }

}

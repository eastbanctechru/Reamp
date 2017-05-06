package etr.android.reamp.mvp.integrations;

import android.os.Bundle;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.robolectric.Robolectric;
import org.robolectric.android.controller.ActivityController;

import etr.android.reamp.mvp.BaseTest;
import etr.android.reamp.debug.CustomViewPresenter;
import etr.android.reamp.debug.CustomViewState;
import etr.android.reamp.mvp.MvpPresenter;
import etr.android.reamp.mvp.PresenterManager;
import etr.android.reamp.mvp.ReampProvider;
import etr.android.reamp.debug.SimpleAppCompatActivity;
import etr.android.reamp.debug.TestCustomView;

public class StandAloneViewTest extends BaseTest {

    @Before
    public void setUp() throws Exception {
        Robolectric.setupContentProvider(ReampProvider.class);
    }

    @Test
    public void simple() throws Exception {
        SimpleAppCompatActivity activity = Robolectric.setupActivity(SimpleAppCompatActivity.class);
        TestCustomView testCustomView = activity.getCustomView();
        CustomViewPresenter presenter = testCustomView.getPresenter();
        Assert.assertNotNull(presenter);
        CustomViewState stateModel = presenter.getStateModel();
        Assert.assertNotNull(stateModel);
        presenter.count();
        Assert.assertEquals(testCustomView.counter, 1);
    }

    @Test
    public void keepState() throws Exception {
        ActivityController<SimpleAppCompatActivity> controller = Robolectric.buildActivity(SimpleAppCompatActivity.class);
        SimpleAppCompatActivity activity = controller.setup().get();
        TestCustomView view = activity.getCustomView();
        view.getPresenter().count();
        Bundle bundle = new Bundle();
        controller.saveInstanceState(bundle).pause().stop().destroy();
        activity = Robolectric.buildActivity(SimpleAppCompatActivity.class).create(bundle).start().restoreInstanceState(bundle).resume().visible().get();
        view = activity.getCustomView();
        CustomViewPresenter presenter = view.getPresenter();
        Assert.assertNotNull(presenter);
        CustomViewState stateModel = presenter.getStateModel();
        Assert.assertNotNull(stateModel);
        Assert.assertEquals(stateModel.counter, 1);
        Assert.assertEquals(view.counter, 1);
    }

    @Test
    public void destroyPresenter() throws Exception {
        ActivityController<SimpleAppCompatActivity> controller = Robolectric.buildActivity(SimpleAppCompatActivity.class);
        SimpleAppCompatActivity activity = controller.get();
        controller.create().start().resume().visible();
        TestCustomView view = activity.getCustomView();
        String mvpId = view.getMvpId();
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
        TestCustomView view = activity.getCustomView();
        String mvpId = view.getMvpId();
        controller.pause().stop().destroy();
        MvpPresenter presenter = PresenterManager.getInstance().getPresenter(mvpId);
        Assert.assertNotNull(presenter);
    }

    @Test
    public void viewLeak() throws Exception {
        ActivityController<SimpleAppCompatActivity> controller = Robolectric.buildActivity(SimpleAppCompatActivity.class);
        SimpleAppCompatActivity activity = controller.get();
        controller.create().start().resume().visible();
        TestCustomView view = activity.getCustomView();
        CustomViewPresenter presenter = view.getPresenter();
        activity.removeView();
        Assert.assertNull(view.getPresenter());
        Assert.assertNull(presenter.getView());
    }
}

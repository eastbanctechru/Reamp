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
import etr.android.reamp.mvp.MvpPresenter;
import etr.android.reamp.mvp.PresenterManager;
import etr.android.reamp.mvp.ReampProvider;
import etr.android.reamp.mvp.internal.RegularAppCompatActivity;
import etr.android.reamp.mvp.internal.TestMvpCustomView;

public class StandAloneViewTest extends BaseTest {

    @Before
    public void setUp() throws Exception {
        Robolectric.setupContentProvider(ReampProvider.class);
    }

    @Test
    public void simple() throws Exception {
        RegularAppCompatActivity activity = Robolectric.setupActivity(RegularAppCompatActivity.class);
        TestMvpCustomView testMvpCustomView = activity.getCustomView();
        TesteePresenter presenter = testMvpCustomView.getPresenter();
        Assert.assertNotNull(presenter);
        TesteeState stateModel = presenter.getStateModel();
        Assert.assertNotNull(stateModel);
        presenter.increment();
        Assert.assertEquals(testMvpCustomView.counter, 1);
    }

    @Test
    public void keepState() throws Exception {
        ActivityController<RegularAppCompatActivity> controller = Robolectric.buildActivity(RegularAppCompatActivity.class);
        RegularAppCompatActivity activity = controller.setup().get();
        TestMvpCustomView view = activity.getCustomView();
        view.getPresenter().increment();
        Bundle bundle = new Bundle();
        controller.saveInstanceState(bundle).pause().stop().destroy();
        activity = Robolectric.buildActivity(RegularAppCompatActivity.class).create(bundle).start().restoreInstanceState(bundle).resume().visible().get();
        view = activity.getCustomView();
        TesteePresenter presenter = view.getPresenter();
        Assert.assertNotNull(presenter);
        TesteeState stateModel = presenter.getStateModel();
        Assert.assertNotNull(stateModel);
        Assert.assertEquals(stateModel.counter, 1);
        Assert.assertEquals(view.counter, 1);
    }

    @Test
    public void destroyPresenter() throws Exception {
        ActivityController<RegularAppCompatActivity> controller = Robolectric.buildActivity(RegularAppCompatActivity.class);
        RegularAppCompatActivity activity = controller.get();
        controller.create().start().resume().visible();
        TestMvpCustomView view = activity.getCustomView();
        String mvpId = view.getMvpId();
        controller.userLeaving();
        activity.finish(); //make activity to think that it is being finished
        controller.pause().stop().destroy();
        MvpPresenter presenter = PresenterManager.getInstance().getPresenter(mvpId);
        Assert.assertNull(presenter);
    }

    @Test
    public void doNotDestroyPresenter() throws Exception {
        ActivityController<RegularAppCompatActivity> controller = Robolectric.buildActivity(RegularAppCompatActivity.class);
        RegularAppCompatActivity activity = controller.get();
        controller.create().start().resume().visible();
        TestMvpCustomView view = activity.getCustomView();
        String mvpId = view.getMvpId();
        controller.pause().stop().destroy();
        MvpPresenter presenter = PresenterManager.getInstance().getPresenter(mvpId);
        Assert.assertNotNull(presenter);
    }

    @Test
    public void viewLeak() throws Exception {
        ActivityController<RegularAppCompatActivity> controller = Robolectric.buildActivity(RegularAppCompatActivity.class);
        RegularAppCompatActivity activity = controller.get();
        controller.create().start().resume().visible();
        TestMvpCustomView view = activity.getCustomView();
        TesteePresenter presenter = view.getPresenter();
        activity.removeView();
        Assert.assertNull(view.getPresenter());
        Assert.assertNull(presenter.getView());
    }
}

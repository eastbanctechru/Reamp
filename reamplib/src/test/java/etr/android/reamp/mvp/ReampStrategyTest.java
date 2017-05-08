package etr.android.reamp.mvp;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.robolectric.Robolectric;
import org.robolectric.android.controller.ActivityController;

import etr.android.reamp.mvp.internal.TestMvpActivity;
import etr.android.reamp.mvp.internal.RegularActivity;

public class ReampStrategyTest extends BaseTest {

    @Before
    public void setUp() throws Exception {
        Robolectric.setupContentProvider(ReampProvider.class);
    }

    @Test
    public void normalBehaviorWithRegularActivity() throws Exception {
        ActivityController<RegularActivity> controller = Robolectric.buildActivity(RegularActivity.class);
        controller.create().start().resume().visible();
        controller.userLeaving();
        controller.get().finish(); //make activity to think that it is being finished
        controller.pause().stop().destroy();
    }

    @Test
    public void normalBehaviorWithNoPresenter() throws Exception {
        PresenterManager.getInstance().destroyPresenter("no-presenter-with-this-id");
    }

    @Test
    public void normalBehaviorWithNoPresenter2() throws Exception {
        ActivityController<TestMvpActivity> controller = Robolectric.buildActivity(TestMvpActivity.class);
        controller.create().start().resume().visible();
        PresenterManager.getInstance().destroyPresenter(controller.get().getMvpId());
        controller.userLeaving();
        controller.get().finish(); //make activity to think that it is being finished
        controller.pause().stop().destroy();
    }

    @Test
    public void checkStrategy() throws Exception {
        MockStrategy mockStrategy = new MockStrategy();
        PresenterManager.getInstance().setStrategy(mockStrategy);
        ReampStrategy strategy = PresenterManager.getInstance().getStrategy();
        Assert.assertEquals(strategy, mockStrategy);
    }

    @Test
    public void checkNullStrategy() throws Exception {
        ReampStrategy strategy = PresenterManager.getInstance().getStrategy();
        PresenterManager.getInstance().setStrategy(null);
        ReampStrategy strategy2 = PresenterManager.getInstance().getStrategy();
        Assert.assertEquals(strategy, strategy2);
    }

    private class MockStrategy extends ReampStrategy {
    }
}
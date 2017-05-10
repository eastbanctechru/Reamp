package etr.android.reamp.mvp;

import org.junit.Assert;
import org.junit.Test;

import etr.android.reamp.mvp.internal.SimpleView;
import etr.android.reamp.mvp.internal.TesteePresenter;

public class MvpPresenterTest extends BaseTest {


    @Test
    public void multiViewTest() throws Exception {
        SimpleView simpleView1 = new PersistentIdView();
        SimpleView simpleView2 = new PersistentIdView();
        simpleView1.onCreate();
        simpleView1.connect();
        TesteePresenter presenter1 = simpleView1.getPresenter();

        Assert.assertEquals(presenter1.onConnected, 1);
        Assert.assertEquals(presenter1.onConnectedWithView, 1);

        simpleView2.onCreate();
        simpleView2.connect();
        TesteePresenter presenter2 = simpleView2.getPresenter();

        Assert.assertTrue(presenter1 == presenter2);

        Assert.assertEquals(presenter1.onConnected, 1);
        Assert.assertEquals(presenter1.onConnectedWithView, 2);

        presenter1.increment();

        Assert.assertEquals(simpleView1.counter, 1);
        Assert.assertEquals(simpleView2.counter, 1);

        simpleView1.disconnect();

        Assert.assertEquals(presenter1.onDisconnected, 0);
        Assert.assertEquals(presenter1.onDisconnectWithView, 1);

        presenter1.increment();

        Assert.assertEquals(simpleView1.counter, 1);
        Assert.assertEquals(simpleView2.counter, 2);

        simpleView2.disconnect();

        Assert.assertEquals(presenter1.onDisconnected, 1);
        Assert.assertEquals(presenter1.onDisconnectWithView, 2);

        presenter1.increment();

        Assert.assertEquals(simpleView1.counter, 1);
        Assert.assertEquals(simpleView2.counter, 2);

        simpleView1.destroy();
        simpleView2.destroy();

        Assert.assertTrue(presenter1.getViews().isEmpty());
    }

    static class PersistentIdView extends SimpleView {
        @Override
        public String getMvpId() {
            return "persistent_id";
        }
    }
}
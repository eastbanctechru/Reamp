package etr.android.reamp.mvp.integrations;

import android.os.Bundle;

import org.junit.Assert;
import org.junit.Test;
import org.robolectric.Robolectric;
import org.robolectric.android.controller.ActivityController;

import etr.android.reamp.BuildConfig;
import etr.android.reamp.mvp.BaseTest;
import etr.android.reamp.debug.NotSerializableActivity;
import etr.android.reamp.debug.NotSerializablePresenter;
import etr.android.reamp.mvp.PresenterManager;
import etr.android.reamp.debug.TestMvpActivity;

public class SerializationTests extends BaseTest {

    @Test
    public void keepPresenterWithNotSerializableState() throws Exception {
        ActivityController<NotSerializableActivity> controller = Robolectric.buildActivity(NotSerializableActivity.class);
        NotSerializableActivity activity = controller.setup().get();
        NotSerializablePresenter presenter = activity.getPresenter();
        Bundle bundle = new Bundle();
        controller.saveInstanceState(bundle).pause().stop().destroy();
        activity = Robolectric.buildActivity(NotSerializableActivity.class).create(bundle).start().restoreInstanceState(bundle).resume().get();
        NotSerializablePresenter newPresenter = activity.getPresenter();
        Assert.assertEquals(presenter, newPresenter);
        Assert.assertNotNull(newPresenter.getStateModel());
    }

    @Test
    public void restorePresenterWithNotSerializableState() throws Exception {
        ActivityController<NotSerializableActivity> controller = Robolectric.buildActivity(NotSerializableActivity.class);
        NotSerializableActivity activity = controller.setup().get();
        String mvpId = activity.getMvpId();
        Bundle bundle = new Bundle();
        controller.saveInstanceState(bundle).pause().stop().destroy();
        PresenterManager.getInstance().destroyPresenter(mvpId);
        activity = Robolectric.buildActivity(NotSerializableActivity.class).create(bundle).start().restoreInstanceState(bundle).resume().get();
        NotSerializablePresenter newPresenter = activity.getPresenter();
        Assert.assertNotNull(newPresenter);
        Assert.assertNotNull(newPresenter.getStateModel());
    }

    @Test
    public void shouldThrowExceptionInDebug() throws Exception {
        if (!BuildConfig.DEBUG) {
            return;
        }
        class Container {
            Throwable e;
        }
        Container container = new Container();
        ActivityController<TestMvpActivity> controller = Robolectric.buildActivity(TestMvpActivity.class);
        TestMvpActivity activity = controller.setup().get();
        activity.getPresenter().setObj(new Object());
        Bundle bundle = new Bundle();
        try {
            controller.saveInstanceState(bundle).pause().stop().destroy();
        } catch (Exception e) {
            container.e = e;
            String message = e.getMessage();
            Assert.assertTrue(message.contains("Can not serialize state model"));
        }
        Assert.assertNotNull(container.e);
    }

    @Test
    public void shouldThrowExceptionInDebug2() throws Exception {
        if (!BuildConfig.DEBUG) {
            return;
        }
        class Container {
            Throwable e;
        }
        Container container = new Container();
        ActivityController<TestMvpActivity> controller = Robolectric.buildActivity(TestMvpActivity.class);
        TestMvpActivity activity = controller.setup().get();
        String mvpId = activity.getMvpId();
        Bundle bundle = new Bundle();
        controller.saveInstanceState(bundle).pause().stop().destroy();
        makeBundleBad(bundle);
        PresenterManager.getInstance().destroyPresenter(mvpId);
        try {
            activity = Robolectric.buildActivity(TestMvpActivity.class).create(bundle).start().restoreInstanceState(bundle).resume().get();
        } catch (Exception e) {
            container.e = e;
            Assert.assertTrue(e.getMessage().contains("Can not deserialize state model"));
        }

        Assert.assertNotNull(container.e);
    }

    @Test
    public void badSerialization() throws Exception {
        ActivityController<TestMvpActivity> controller = Robolectric.buildActivity(TestMvpActivity.class);
        TestMvpActivity activity = controller.setup().get();
        String mvpId = activity.getMvpId();
        activity.getPresenter().setObj(new Object());
        activity.getPresenter().setThrowOnSerializationError(false);
        Bundle bundle = new Bundle();
        controller.saveInstanceState(bundle).pause().stop().destroy();

        PresenterManager.getInstance().setPresenter(mvpId, null);
        makeBundleBad(bundle);

        controller = Robolectric.buildActivity(TestMvpActivity.class);
        controller.get().setThrowOnSerializationError(false);
        activity = controller.create(bundle).start().restoreInstanceState(bundle).resume().get();
        Assert.assertNull(activity.getPresenter().getStateModel().object);
    }


    private void makeBundleBad(Bundle bundle) {
        Bundle presenterBundle = bundle.getBundle("KEY_PRESENTER_STATE");
        if (presenterBundle == null) {
            presenterBundle = new Bundle();
            bundle.putBundle("KEY_PRESENTER_STATE", presenterBundle);
        }
        presenterBundle.putByteArray("EXTRA_INSTANCE_STATE", new byte[]{1, 2, 3});
    }
}

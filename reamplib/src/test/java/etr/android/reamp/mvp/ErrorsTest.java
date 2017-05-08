package etr.android.reamp.mvp;

import org.junit.Assert;
import org.junit.Test;

import etr.android.reamp.mvp.internal.SimpleView;
import etr.android.reamp.mvp.internal.TesteePresenter;
import etr.android.reamp.mvp.internal.TesteeState;

public class ErrorsTest extends BaseTest {

    @Test
    public void testErrorEvent() throws Exception {

        class Container {
            Throwable error;
        }

        final Container container = new Container();

        SimpleView simpleView = new SimpleView() {
            @Override
            public void onError(Throwable throwable) {
                super.onError(throwable);
                container.error = throwable;
            }
        };
        simpleView.onCreate();
        simpleView.connect();
        final TesteePresenter presenter = simpleView.getPresenter();
        presenter.sendError();
        Assert.assertNotNull(container.error);
    }

    @Test
    public void viewErrorHandling() throws Exception {

        class Container {
            Throwable error;
        }

        final Container container = new Container();

        SimpleView simpleView = new SimpleView() {
            @Override
            public void onError(Throwable throwable) {
                super.onError(throwable);
                container.error = throwable;
            }

            @Override
            public void onStateChanged(TesteeState stateModel) {
                super.onStateChanged(stateModel);
                throw new RuntimeException("AMA Bad view");
            }
        };
        simpleView.onCreate();
        simpleView.connect();
        Assert.assertNotNull(container.error);
    }
}

package etr.android.reamp.mvp;

import org.junit.Assert;
import org.junit.Test;

import etr.android.reamp.mvp.common.TestPresenter;
import etr.android.reamp.mvp.common.TestState;
import etr.android.reamp.mvp.common.TestView;

public class ErrorsTest extends BaseTest {

    @Test
    public void testErrorEvent() throws Exception {

        class Container {
            Throwable error;
        }

        final Container container = new Container();

        TestView testView = new TestView() {
            @Override
            public void onError(Throwable throwable) {
                super.onError(throwable);
                container.error = throwable;
            }
        };
        testView.onCreate();
        testView.connect();
        final TestPresenter presenter = testView.getPresenter();
        presenter.makeErrorMessage();
        Assert.assertNotNull(container.error);
    }

    @Test
    public void viewErrorHandling() throws Exception {

        class Container {
            Throwable error;
        }

        final Container container = new Container();

        TestView testView = new TestView() {
            @Override
            public void onError(Throwable throwable) {
                super.onError(throwable);
                container.error = throwable;
            }

            @Override
            public void onStateChanged(TestState stateModel) {
                super.onStateChanged(stateModel);
                throw new RuntimeException("AMA Bad view");
            }
        };
        testView.onCreate();
        testView.connect();
        Assert.assertNotNull(container.error);
    }
}

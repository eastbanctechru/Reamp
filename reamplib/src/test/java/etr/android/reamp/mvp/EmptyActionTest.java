package etr.android.reamp.mvp;

import org.junit.Assert;
import org.junit.Test;

public class EmptyActionTest {
    @Test
    public void test() {
        EmptyAction emptyAction = new EmptyAction();

        final boolean[] b = {false};
        emptyAction.consume(new Runnable() {
            @Override
            public void run() {
                b[0] = true;
            }
        });
        Assert.assertFalse(b[0]);

        emptyAction.set();
        emptyAction.consume(new Runnable() {
            @Override
            public void run() {
                b[0] = true;
            }
        });
        Assert.assertTrue(b[0]);

        b[0] = false;
        emptyAction.consume(new Runnable() {
            @Override
            public void run() {
                b[0] = true;
            }
        });
        Assert.assertFalse(b[0]);
    }
}

package etr.android.reamp.mvp.screen_one;

import android.support.annotation.NonNull;

import junit.framework.Assert;

import java.io.Serializable;

import etr.android.reamp.functional.ConsumerNonNull;
import etr.android.reamp.mvp.Action;
import etr.android.reamp.mvp.EmptyAction;

class Util {
    static void assertAndConsume(@NonNull EmptyAction emptyAction, boolean expectedHasAction) {
        final boolean[] actualHasAction = {false};
        emptyAction.consume(new Runnable() {
            @Override
            public void run() {
                actualHasAction[0] = true;
            }
        });
        Assert.assertEquals(expectedHasAction, actualHasAction[0]);
    }

    static <T extends Serializable> void assertHasValue(@NonNull final Action<T> action, @NonNull T expected) {
        final Serializable[] actual = new Serializable[]{null};
        action.consume(new ConsumerNonNull<T>() {
            @Override
            public void consume(@NonNull T t) {
                Assert.assertNotNull(t);
                actual[0] = t;
            }
        });
        Assert.assertEquals(expected, actual[0]);
    }

    static <T extends Serializable> void assertNoValue(@NonNull final Action<T> action) {
        final boolean[] hasValue = new boolean[]{false};
        action.consume(new ConsumerNonNull<T>() {
            @Override
            public void consume(@NonNull T t) {
                hasValue[0] = true;
            }
        });
        Assert.assertFalse(hasValue[0]);
    }
}

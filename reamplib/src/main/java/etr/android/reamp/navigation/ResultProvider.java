package etr.android.reamp.navigation;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import etr.android.reamp.functional.ConsumerNonNull;

public abstract class ResultProvider {
    @Nullable
    abstract public <R> R getResult(@NonNull ComplexNavigationUnit<?, R> navigationUnit);

    public <R> void consumeResult(
            @NonNull ComplexNavigationUnit<?, R> navigationUnit,
            @NonNull ConsumerNonNull<R> consumer
    ) {
        final R result = getResult(navigationUnit);
        if (result != null) {
            consumer.consume(result);
        }
    }

    public static final class Android extends ResultProvider {
        @NonNull
        private final Navigation navigation;
        private final int requestCode;
        private final int resultCode;
        private final Intent data;

        public Android(@NonNull Navigation navigation, int requestCode, int resultCode, Intent data) {
            this.navigation = navigation;
            this.requestCode = requestCode;
            this.resultCode = resultCode;
            this.data = data;
        }

        @Override
        @Nullable
        public <R> R getResult(@NonNull ComplexNavigationUnit<?, R> navigationUnit) {
            return navigation.getResult(navigationUnit, requestCode, resultCode, data);
        }
    }

    public static final class Test<R> extends ResultProvider {
        private final Class<? extends ComplexNavigationUnit<?, R>> nClass;
        private final R result;

        public Test(Class<? extends ComplexNavigationUnit<?, R>> nClass, R result) {
            this.nClass = nClass;
            this.result = result;
        }

        @SuppressWarnings("unchecked")
        @Nullable
        @Override
        public <R2> R2 getResult(@NonNull ComplexNavigationUnit<?, R2> navigationUnit) {
            if (navigationUnit.getClass() == nClass) {
                return (R2) result;
            }
            return null;
        }
    }
}
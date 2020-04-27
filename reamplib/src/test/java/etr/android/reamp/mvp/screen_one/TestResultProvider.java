package etr.android.reamp.mvp.screen_one;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import etr.android.reamp.navigation.ComplexNavigationUnit;
import etr.android.reamp.navigation.ResultProvider;

public final class TestResultProvider<R> extends ResultProvider {
    private final Class<? extends ComplexNavigationUnit<?, R>> nClass;
    private final R result;

    public TestResultProvider(Class<? extends ComplexNavigationUnit<?, R>> nClass, R result) {
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
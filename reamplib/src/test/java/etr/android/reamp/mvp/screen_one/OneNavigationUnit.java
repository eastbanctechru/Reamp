package etr.android.reamp.mvp.screen_one;

import java.util.Objects;

import etr.android.reamp.navigation.ComplexNavigationUnit;
import etr.android.reamp.navigation.Navigation;

public class OneNavigationUnit extends ComplexNavigationUnit<Integer, Void> {
    private final Integer i;

    public OneNavigationUnit() {
        this.i = null;
    }

    public OneNavigationUnit(int i) {
        this.i = i;
    }

    @Override
    protected void navigate(Navigation navigation) {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OneNavigationUnit that = (OneNavigationUnit) o;
        return Objects.equals(i, that.i);
    }

    @Override
    public int hashCode() {
        return Objects.hash(i);
    }
}

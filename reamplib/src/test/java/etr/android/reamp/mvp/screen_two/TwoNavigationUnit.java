package etr.android.reamp.mvp.screen_two;

import java.util.Objects;

import etr.android.reamp.navigation.Navigation;
import etr.android.reamp.navigation.NavigationUnit;

public class TwoNavigationUnit extends NavigationUnit<Integer> {

    private final Integer i;

    public TwoNavigationUnit() {
        this.i = null;
    }

    public TwoNavigationUnit(int i) {
        this.i = i;
    }

    @Override
    protected void navigate(Navigation navigation) {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TwoNavigationUnit that = (TwoNavigationUnit) o;
        return Objects.equals(i, that.i);
    }

    @Override
    public int hashCode() {
        return Objects.hash(i);
    }
}

package etr.android.reamp.navigation;

import android.content.Intent;

public abstract class NavigationUnit<T, R> {

    protected abstract void navigate(Navigation navigation);

    protected T getNavigationData(Navigation navigation) {
        return null;
    }

    protected void setNavigationResult(Navigation navigation) {

    }

    protected R getNavigationResult(Navigation navigation, int requestCode, int resultCode, Intent data) {
        return null;
    }
}

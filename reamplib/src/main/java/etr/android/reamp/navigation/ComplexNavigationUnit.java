package etr.android.reamp.navigation;

import android.content.Intent;

/**
 * Common navigation class which is designed to be used with {@link Navigation}
 * <br/>
 * Implement your navigation and data passing logic with the help of this class
 * @param <T> Forward navigation data type
 * @param <R> Backward (result) navigation data type
 */
public abstract class ComplexNavigationUnit<T, R> {

    /**
     * Implement your navigation logic here, such as starting an activity
     * and passing arguments through an intent
     */
    protected abstract void navigate(Navigation navigation);

    /**
     * Implement your logic of the retrieving a data you passed during the navigation,
     * such as getting a data from an intent
     */
    protected T getNavigationData(Navigation navigation) {
        return null;
    }

    /**
     * Implement your logic of the passing a result back to the requester
     */
    protected void setNavigationResult(Navigation navigation) {

    }

    /**
     * Implement your logic of the retrieving a result
     */
    protected R getNavigationResult(Navigation navigation, int requestCode, int resultCode, Intent data) {
        return null;
    }
}

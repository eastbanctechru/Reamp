package etr.android.reamp.navigation;

import android.app.Activity;
import android.content.Intent;

import etr.android.reamp.mvp.MvpView;

/**
 * Class which helps you to encapsulate your navigation logic
 */
public class Navigation {

    private final Activity activity;

    public Navigation(MvpView view) {
        this.activity = (Activity) view.getContext();
        checkContext();
    }

    private void checkContext() {
        if (!(activity instanceof MvpView)) {
            throw new IllegalStateException("Context of the view should be also an MvpView");
        }
    }

    /**
     * Returns the activity which the navigation instance is connected to
     */
    public Activity getActivity() {
        return activity;
    }

    /**
     * Finishes the current activity
     */
    public void close() {
        activity.finish();
    }

    /**
     * Performs the navigation action which is provided by the passed navigationUnit
     */
    public void open(ComplexNavigationUnit navigationUnit) {
        navigationUnit.navigate(this);
    }

    /**
     * Performs the data retrieving action which is provided by the passed navigationUnit
     */
    public <T> T getData(ComplexNavigationUnit<T, ?> navigationUnit) {
        return navigationUnit.getNavigationData(this);
    }

    /**
     * Performs the result storing action which is provided by the passed navigationUnit
     */
    public <R> void setResult(ComplexNavigationUnit<?, R> navigationUnit) {
        navigationUnit.setNavigationResult(this);
    }

    /**
     * Performs the result retrieving action which is provided by the passed navigationUnit
     */
    public <R> R getResult(ComplexNavigationUnit<?, R> navigationUnit, int requestCode, int resultCode, Intent data) {
        return navigationUnit.getNavigationResult(this, requestCode, resultCode, data);
    }
}

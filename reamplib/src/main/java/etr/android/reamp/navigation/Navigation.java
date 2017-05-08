package etr.android.reamp.navigation;

import android.app.Activity;
import android.content.Intent;

import etr.android.reamp.mvp.MvpView;

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

    public Activity getActivity() {
        return activity;
    }

    public void close() {
        activity.finish();
    }

    public void open(ComplexNavigationUnit navigationUnit) {
        navigationUnit.navigate(this);
    }

    public <T> T getData(ComplexNavigationUnit<T, ?> navigationUnit) {
        return navigationUnit.getNavigationData(this);
    }

    public <R> void setResult(ComplexNavigationUnit<?, R> navigationUnit) {
        navigationUnit.setNavigationResult(this);
    }

    public <R> R getResult(ComplexNavigationUnit<?, R> navigationUnit, int requestCode, int resultCode, Intent data) {
        return navigationUnit.getNavigationResult(this, requestCode, resultCode, data);
    }
}

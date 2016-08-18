package etr.android.reamp.navigation;

import android.app.Activity;
import android.content.Intent;

public class Navigation {

    private Activity activity;

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Activity getActivity() {
        return activity;
    }

    public void close() {
        activity.finish();
    }

    public void open(NavigationUnit navigationUnit) {
        navigationUnit.navigate(this);
    }

    public <T> T getData(NavigationUnit<T, ?> navigationUnit) {
        return navigationUnit.getNavigationData(this);
    }

    public <R> void setResult(NavigationUnit<?, R> navigationUnit) {
        navigationUnit.setNavigationResult(this);
    }

    public <R> R getResult(NavigationUnit<?, R> navigationUnit, int requestCode, int resultCode, Intent data) {
        return navigationUnit.getNavigationResult(this, requestCode, resultCode, data);
    }
}

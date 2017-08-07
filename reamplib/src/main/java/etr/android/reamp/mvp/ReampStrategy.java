package etr.android.reamp.mvp;

import android.app.Activity;

import java.util.List;

public class ReampStrategy {

    public void onActivityDestroyed(Activity activity) {

        if (activity.isFinishing()) {
            List<ReampView> views = PresenterManager.getInstance().getViewsOf(activity);
            for (ReampView view : views) {
                PresenterManager.getInstance().destroyPresenter(view.getMvpId());
            }
        }
    }
}

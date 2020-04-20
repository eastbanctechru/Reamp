package etr.android.reamp.mvp;

import android.app.Activity;
import android.content.Context;

import java.util.List;
import java.util.Map;

public class ReampStrategy {

    public void onActivityDestroyed(Activity activity) {

        if (activity.isFinishing()) {
            List<ReampView> views = PresenterManager.getInstance().getViewsOf(activity);
            for (ReampView view : views) {
                PresenterManager.getInstance().destroyPresenter(view.getMvpId());
            }
            List<String> phantomViews = PresenterManager.getInstance().getPhantomViews(activity);
            for (String phantomView : phantomViews) {
                PresenterManager.getInstance().destroyPresenter(phantomView);
            }
            PresenterManager.getInstance().releasePhantomViews(activity);
        }
    }
}

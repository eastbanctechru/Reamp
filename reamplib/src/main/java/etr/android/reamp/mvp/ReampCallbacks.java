package etr.android.reamp.mvp;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

class ReampCallbacks implements Application.ActivityLifecycleCallbacks {

    private static final String REAMP_ATTACHED_VIEWS = "REAMP_ATTACHED_VIEWS";

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            ArrayList<String> phantomIds = savedInstanceState.getStringArrayList(REAMP_ATTACHED_VIEWS);
            if (phantomIds != null) {
                for (String phantomId : phantomIds) {
                    PresenterManager.getInstance().registerPhantomView(phantomId, activity);
                }
            }
        }
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        List<ReampView> attachedViews = PresenterManager.getInstance().getViewsOf(activity);
        outState.putStringArrayList(REAMP_ATTACHED_VIEWS, mapToIds(attachedViews));
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        ReampStrategy strategy = PresenterManager.getInstance().getStrategy();
        strategy.onActivityDestroyed(activity);
        PresenterManager.getInstance().unregisterViewsOf(activity);
    }

    private ArrayList<String> mapToIds(List<ReampView> attachedViews) {
        if (attachedViews == null) return new ArrayList<>(0);
        ArrayList<String> attachedIds = new ArrayList<>(attachedViews.size());
        for (ReampView attachedView : attachedViews) {
            attachedIds.add(attachedView.getMvpId());
        }
        return attachedIds;
    }
}

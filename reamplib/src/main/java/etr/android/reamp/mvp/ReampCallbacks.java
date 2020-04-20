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
        List<String> phantomViews = PresenterManager.getInstance().getPhantomViews(activity);
        outState.putStringArrayList(REAMP_ATTACHED_VIEWS, mapToIds(attachedViews, phantomViews));
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        ReampStrategy strategy = PresenterManager.getInstance().getStrategy();
        strategy.onActivityDestroyed(activity);
        PresenterManager.getInstance().unregisterViewsOf(activity);
    }

    private ArrayList<String> mapToIds(List<ReampView> attachedViews, List<String> phantomViews) {
        ArrayList<String> attachedIds = new ArrayList<>();
        if (attachedViews != null) {
            for (ReampView attachedView : attachedViews) {
                attachedIds.add(attachedView.getMvpId());
            }
        }
        if (phantomViews != null) {
            attachedIds.addAll(phantomViews);
        }
        return attachedIds;
    }
}

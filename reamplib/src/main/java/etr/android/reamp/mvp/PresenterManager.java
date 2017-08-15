package etr.android.reamp.mvp;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class PresenterManager {

    private static PresenterManager instance;

    private final HashMap<String, ReampPresenter> presenters = new HashMap<>();
    private final Map<ReampView, Context> register = new HashMap<>();
    private ReampStrategy strategy = new ReampStrategy();

    private PresenterManager() {
    }

    public static PresenterManager getInstance() {
        if (instance == null) {
            instance = new PresenterManager();
        }
        return instance;
    }

    public ReampPresenter getPresenter(String mvpId) {
        return presenters.get(mvpId);
    }

    public void setPresenter(String mvpId, ReampPresenter presenter) {
        presenters.put(mvpId, presenter);
    }

    public void destroyPresenter(String mvpId) {
        ReampPresenter presenter = presenters.remove(mvpId);
        if (presenter != null) {
            presenter.releaseAllViews();
            presenter.onDestroyPresenter();
        }
    }

    public ReampStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(ReampStrategy strategy) {
        if (strategy != null) {
            this.strategy = strategy;
        }
    }

    public void registerView(ReampView view, Context context) {
        register.put(view, context);
    }

    public void unregisterViewsOf(Context context) {
        Iterator<Map.Entry<ReampView, Context>> iterator = register.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<ReampView, Context> entry = iterator.next();
            if (context.equals(entry.getValue())) {
                iterator.remove();
            }
        }
    }

    public List<ReampView> getViewsOf(Context context) {
        List<ReampView> views = new ArrayList<>();
        for (Map.Entry<ReampView, Context> entry : register.entrySet()) {
            if (context.equals(entry.getValue())) {
                views.add(entry.getKey());
            }
        }
        return views;
    }
}

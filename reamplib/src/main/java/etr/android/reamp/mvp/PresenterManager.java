package etr.android.reamp.mvp;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class PresenterManager {

    private static PresenterManager instance;

    private final HashMap<String, MvpPresenter> presenters = new HashMap<>();
    private final Map<MvpView, Context> register = new HashMap<>();
    private ReampStrategy strategy = new ReampStrategy();

    private PresenterManager() {
    }

    public static PresenterManager getInstance() {
        if (instance == null) {
            instance = new PresenterManager();
        }
        return instance;
    }

    public MvpPresenter getPresenter(String mvpId) {
        return presenters.get(mvpId);
    }

    public void setPresenter(String mvpId, MvpPresenter presenter) {
        presenters.put(mvpId, presenter);
    }

    public void destroyPresenter(String mvpId) {
        MvpPresenter presenter = presenters.remove(mvpId);
        if (presenter != null) {
            presenter.setView(null);
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

    public void registerView(MvpView view, Context context) {
        register.put(view, context);
    }

    public void unregisterViewsOf(Context context) {
        Iterator<Map.Entry<MvpView, Context>> iterator = register.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<MvpView, Context> entry = iterator.next();
            if (context.equals(entry.getValue())) {
                iterator.remove();
            }
        }
    }

    public List<MvpView> getViewsOf(Context context) {
        List<MvpView> views = new ArrayList<>();
        for (Map.Entry<MvpView, Context> entry : register.entrySet()) {
            if (context.equals(entry.getValue())) {
                views.add(entry.getKey());
            }
        }
        return views;
    }
}

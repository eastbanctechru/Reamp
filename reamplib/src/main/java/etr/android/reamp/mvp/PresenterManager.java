package etr.android.reamp.mvp;

import java.util.HashMap;

public class PresenterManager {

    private static PresenterManager instance;

    final private HashMap<String, MvpPresenter> presenters = new HashMap<>();
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
}

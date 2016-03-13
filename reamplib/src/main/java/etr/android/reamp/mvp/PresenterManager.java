package etr.android.reamp.mvp;

import java.util.HashMap;

public class PresenterManager {

    private static PresenterManager instance;

    final private HashMap<String, MvpPresenter> presenters = new HashMap<>();

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
        presenters.remove(mvpId);
    }
}

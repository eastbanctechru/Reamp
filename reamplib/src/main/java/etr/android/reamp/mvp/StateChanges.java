package etr.android.reamp.mvp;

public interface StateChanges {

    void onNewState(ReampStateModel state);

    void onError(Throwable e);
}

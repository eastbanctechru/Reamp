package etr.android.reamp.mvp;

public interface StateChanges {

    void onNewState(MvpStateModel state);

    void onError(Throwable e);
}

package etr.android.reamp.mvp;

import android.app.Activity;


public interface IMvpActivity<P extends MvpPresenter<SM>, SM extends MvpStateModel> extends MvpView<SM> {
    void setMvpId(String mvpId);

    SM onCreateStateModel();

    P onCreatePresenter();

    Activity getActivity();

    void setPresenter(P presenter);

    P getPresenter();
}

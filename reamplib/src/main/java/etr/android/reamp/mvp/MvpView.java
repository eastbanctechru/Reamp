package etr.android.reamp.mvp;

import android.content.Context;


public interface MvpView<SM extends MvpStateModel> {

    Context getContext();

    void onStateChanged(SM stateModel);

    void onError(Throwable throwable);

    String getMvpId();

    SM onCreateStateModel();

    MvpPresenter<SM> onCreatePresenter();

    void setPresenter(MvpPresenter<SM> presenter);

    MvpPresenter<SM> getPresenter();
}

package etr.android.reamp.mvp.internal.navigation;

import android.content.Intent;

import etr.android.reamp.mvp.MvpPresenter;
import etr.android.reamp.mvp.MvpStateModel;
import etr.android.reamp.mvp.SerializableStateModel;

public class NavigationPresenter extends MvpPresenter<SerializableStateModel> {

    public int requestCode;
    public int resultCode;
    public Intent data;

    @Override
    public void onResult(int requestCode, int resultCode, Intent data) {
        super.onResult(requestCode, resultCode, data);
        this.requestCode = requestCode;
        this.resultCode = resultCode;
        this.data = data;
    }
}

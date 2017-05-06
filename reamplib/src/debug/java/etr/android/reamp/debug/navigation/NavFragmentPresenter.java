package etr.android.reamp.debug.navigation;

import android.content.Intent;

import etr.android.reamp.mvp.MvpPresenter;

public class NavFragmentPresenter extends MvpPresenter<NavFragmentState> {

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

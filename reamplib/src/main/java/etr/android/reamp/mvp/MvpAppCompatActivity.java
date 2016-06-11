package etr.android.reamp.mvp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import etr.android.reamp.R;

public abstract class MvpAppCompatActivity<P extends MvpPresenter<SM>, SM extends MvpStateModel> extends AppCompatActivity implements IMvpActivity<P, SM> {

    private P presenter;
    private String mvpId;
    private MvpActivityDelegate<P, SM> mvpActivityDelegate = new MvpActivityDelegate<>(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mvpActivityDelegate.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mvpActivityDelegate.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mvpActivityDelegate.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mvpActivityDelegate.onSaveInstanceState(outState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mvpActivityDelegate.onResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mvpActivityDelegate.onDestroy(isFinishing());
    }

    @Override
    public Context getContext() {
        return this;
    }

    public void setPresenter(P presenter) {
        this.presenter = presenter;
    }


    public P getPresenter() {
        return presenter;
    }

    @Override
    public void onError(Throwable throwable) {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.reamp_common_error_title))
                .setMessage(Log.getStackTraceString(throwable))
                .show();

    }

    public String getMvpId() {
        return mvpId;
    }

    public void setMvpId(String mvpId) {
        this.mvpId = mvpId;
    }

    @Override
    public MvpAppCompatActivity getActivity() {
        return this;
    }
}

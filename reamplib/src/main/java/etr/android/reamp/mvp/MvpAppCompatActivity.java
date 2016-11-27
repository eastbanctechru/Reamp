package etr.android.reamp.mvp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.UUID;

import etr.android.reamp.R;

public class MvpAppCompatActivity<P extends MvpPresenter<SM>, SM extends MvpStateModel> extends AppCompatActivity implements MvpView<SM> {

    private P presenter;
    private MvpDelegate delegate = new MvpDelegate(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        delegate.onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        delegate.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        delegate.disconnect();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        delegate.onSaveInstanceState(outState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        delegate.onResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        delegate.onDestroy();
        super.onDestroy();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onStateChanged(SM stateModel) {

    }

    @Override
    public void setPresenter(MvpPresenter<SM> presenter) {
        this.presenter = (P) presenter;
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
        return delegate.generateId(this);
    }

    @Override
    public SM onCreateStateModel() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public MvpPresenter<SM> onCreatePresenter() {
        throw new UnsupportedOperationException("Not implemented");
    }
}

package etr.android.reamp.mvp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import etr.android.reamp.R;

/**
 * A base Activity which is {@link ReampView}.
 */
public abstract class ReampAppCompatActivity<P extends ReampPresenter<SM>, SM extends ReampStateModel> extends AppCompatActivity implements ReampView<SM> {

    private static final String TAG = "ReampAppCompatActivity";

    private MvpDelegate delegate = new MvpDelegate(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        delegate.onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        delegate.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
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

    public P getPresenter() {
        return delegate.<P, SM>getPresenter();
    }

    @Override
    public void onError(Throwable throwable) {
        Log.e(TAG, "Error while changing state", throwable);
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.reamp_common_error_title))
                .setMessage(R.string.reamp_common_error_message)
                .show();

    }

    public String getMvpId() {
        return delegate.getId();
    }
}

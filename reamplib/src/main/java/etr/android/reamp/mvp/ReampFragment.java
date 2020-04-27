package etr.android.reamp.mvp;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import android.util.Log;

import etr.android.reamp.R;

/**
 * A base Fragment which is {@link ReampView}.
 */
public abstract class ReampFragment<P extends ReampPresenter<SM>, SM extends ReampStateModel> extends Fragment implements ReampView<SM> {

    private static final String TAG = "ReampFragment";

    private MvpDelegate delegate = new MvpDelegate(this);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        delegate.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        delegate.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        delegate.disconnect();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        delegate.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        delegate.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        delegate.onResult(requestCode, resultCode, data);
    }

    @Override
    public void onError(Throwable throwable) {
        Log.e(TAG, "Error while changing state", throwable);
        new AlertDialog.Builder(getContext())
                .setTitle(R.string.reamp_common_error_title)
                .setMessage(R.string.reamp_common_error_message)
                .show();
    }

    @Override
    public P getPresenter() {
        return delegate.<P, SM>getPresenter();
    }

    @Override
    public String getMvpId() {
        return delegate.getId();
    }

}

package etr.android.reamp.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import etr.android.reamp.R;

public class MvpFragment<P extends MvpPresenter<SM>, SM extends MvpStateModel> extends Fragment implements MvpView<SM> {

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
    public void onStateChanged(SM stateModel) {

    }

    @Override
    public void onError(Throwable throwable) {
        new AlertDialog.Builder(getContext())
                .setTitle(R.string.reamp_common_error_title)
                .setMessage(Log.getStackTraceString(throwable))
                .show();
    }

    @Override
    public P getPresenter() {
        return delegate.<P, SM>getPresenter();
    }

    @Override
    public String getMvpId() {
        return delegate.generateId(this);
    }

    @Override
    public SM onCreateStateModel() {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public MvpPresenter<SM> onCreatePresenter() {
        throw new UnsupportedOperationException("not implemented");
    }
}

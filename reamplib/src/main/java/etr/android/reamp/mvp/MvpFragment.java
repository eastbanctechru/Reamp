package etr.android.reamp.mvp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;

import butterknife.ButterKnife;
import etr.android.reamp.R;

public abstract class MvpFragment<P extends MvpPresenter<SM>, SM extends MvpStateModel> extends Fragment implements IMvpFragment<P, SM> {

    private MvpFragmentDelegate<P, SM> mvpFragmentDelegate = new MvpFragmentDelegate<>(this);
    private P presenter;
    private String mvpId;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mvpFragmentDelegate.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mvpFragmentDelegate.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mvpFragmentDelegate.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mvpFragmentDelegate.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mvpFragmentDelegate.onDestroy();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
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
        return presenter;
    }

    @Override
    public void setPresenter(P presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setMvpId(String mvpId) {
        this.mvpId = mvpId;
    }

    @Override
    public String getMvpId() {
        return mvpId;
    }

    @Override
    public MvpFragmentDelegate getDelegate() {
        return mvpFragmentDelegate;
    }
}

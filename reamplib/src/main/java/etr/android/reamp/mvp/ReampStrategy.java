package etr.android.reamp.mvp;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class ReampStrategy {

    public void onActivityDestroyed(Activity activity) {
        if (activity.isFinishing()) {

            if (activity instanceof MvpView) {
                MvpView view = (MvpView) activity;
                String mvpId = view.getMvpId();
                releasePresenter(mvpId);
            }

            if (activity instanceof FragmentActivity) {
                FragmentManager fragmentManager = ((FragmentActivity) activity).getSupportFragmentManager();
                List<Fragment> fragments = fragmentManager.getFragments();
                if (fragments != null) {
                    for (Fragment fragment : fragments) {
                        if (fragment instanceof MvpView) {
                            MvpView mvpFragment = (MvpView) fragment;
                            String mvpId = mvpFragment.getMvpId();
                            releasePresenter(mvpId);
                        }
                    }
                }
            }

            //TODO: do the same for simple Activity from standard package

            View root = activity.getWindow().getDecorView();
            releaseViewPresenters(root);
        }
    }

    private void releaseViewPresenters(View view) {
        if (view instanceof MvpView) {
            MvpView mvpView = (MvpView) view;
            String mvpId = mvpView.getMvpId();
            releasePresenter(mvpId);
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int count = viewGroup.getChildCount();
            for (int i = 0; i < count; i++) {
                View child = viewGroup.getChildAt(i);
                releaseViewPresenters(child);
            }
        }
    }

    private void releasePresenter(String mvpId) {
        MvpPresenter presenter = PresenterManager.getInstance().getPresenter(mvpId);
        if (presenter != null) {
            presenter.setView(null);
            PresenterManager.getInstance().destroyPresenter(mvpId);
        }
    }
}

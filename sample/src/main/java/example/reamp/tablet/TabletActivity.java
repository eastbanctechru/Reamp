package example.reamp.tablet;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import etr.android.reamp.mvp.MvpAppCompatActivity;
import etr.android.reamp.mvp.MvpFragment;
import example.reamp.R;
import example.reamp.details.DetailsFragment;
import example.reamp.main.MainFragment;

public class TabletActivity extends MvpAppCompatActivity<TabletActivityPresenter, TabletActivityStateModel> {

    @Override
    public TabletActivityStateModel onCreateStateModel() {
        return new TabletActivityStateModel();
    }

    @Override
    public TabletActivityPresenter onCreatePresenter() {
        return new TabletActivityPresenter();
    }

    @Override
    public void onStateChanged(TabletActivityStateModel stateModel) {
        updateMainFrame();
        updateDetailsFrame(stateModel.showDetails);
        if (stateModel.broadcastResult.get()) {
            for (Fragment fragment : getSupportFragmentManager().getFragments()) {
                if (fragment instanceof MvpFragment) {
                    MvpFragment mvpFragment = (MvpFragment) fragment;
                    mvpFragment.getPresenter().onResult(0, Activity.RESULT_OK, null);
                }
            }
        }
    }

    private void updateDetailsFrame(boolean showDetails) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.content_right);
        if (showDetails) {
            if (fragment == null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_right, new DetailsFragment())
                        .commit();
            }
        } else {
            if (fragment != null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .remove(fragment)
                        .commit();
            }
        }
    }

    private void updateMainFrame() {
        if (getSupportFragmentManager().findFragmentById(R.id.content_left) == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_left, new MainFragment())
                    .commit();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablet);
    }
}

package example.reamp;

import android.app.Activity;
import android.app.Application;

import java.util.List;

import etr.android.reamp.mvp.ReampView;
import etr.android.reamp.mvp.PresenterManager;
import etr.android.reamp.mvp.ReampStrategy;
import example.reamp.share.ParticularActivity;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        PresenterManager.getInstance().setStrategy(new MyStrategy());
    }

    private class MyStrategy extends ReampStrategy {

        @Override
        public void onActivityDestroyed(Activity activity) {
            if (activity.isFinishing()) {
                List<ReampView> views = PresenterManager.getInstance().getViewsOf(activity);
                for (ReampView view : views) {
                    if (view instanceof ParticularActivity && !((ParticularActivity) view).isRoot()) {
                        //let the presenter of this view stay alive
                    } else {
                        PresenterManager.getInstance().destroyPresenter(view.getMvpId());
                    }
                }
            }
        }
    }
}

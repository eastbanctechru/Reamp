package example.reamp.lifecycle;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import etr.android.reamp.mvp.ReampPresenter;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;

public class LongRunningPresenter extends ReampPresenter<RunningState> {

    private final int intervalMs;
    private final String tag;

    private Subscription subscription;

    public LongRunningPresenter(int intervalMs, String tag) {
        this.intervalMs = intervalMs;
        this.tag = tag;
    }

    @Override
    public void onPresenterCreated() {
        super.onPresenterCreated();
        subscription = Observable.interval(intervalMs, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        Log.d(tag, "call: " + aLong);
                        getStateModel().times = aLong;
                        sendStateModel();
                    }
                });
    }

    //this is called when the user completely leaves the screen
    //for instance, by pressing the back button
    @Override
    public void onDestroyPresenter() {
        super.onDestroyPresenter();
        subscription.unsubscribe();
    }
}

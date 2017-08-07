package example.reamp.lifecycle;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import etr.android.reamp.mvp.ReampPresenter;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;

public class ShortRunnintPresenter extends ReampPresenter<RunningState> {

    private final int intervalMs;
    private final String tag;

    private Subscription subscription;

    public ShortRunnintPresenter(int intervalMs, String tag) {
        this.intervalMs = intervalMs;
        this.tag = tag;
    }

    @Override
    public void onConnect() {
        super.onConnect();
        subscription = Observable.interval(intervalMs, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        Log.d(tag, "call: " + aLong);
                        synchronized (getStateModel()) {
                            getStateModel().times++;
                        }
                        sendStateModel();
                    }
                });
    }

    @Override
    public void onDisconnect() {
        super.onDisconnect();
        subscription.unsubscribe();
    }
}

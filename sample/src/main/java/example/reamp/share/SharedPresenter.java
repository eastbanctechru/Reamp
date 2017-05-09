package example.reamp.share;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import etr.android.reamp.mvp.MvpPresenter;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;

public class SharedPresenter extends MvpPresenter<SharedState> {

    private Subscription subscription;

    @Override
    public void onPresenterCreated() {
        super.onPresenterCreated();
        subscription = Observable.interval(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        Log.d("SharedPresenter", "call: " + aLong);
                        getStateModel().times = aLong;
                        sendStateModel();
                    }
                });
    }

    @Override
    public void onDestroyPresenter() {
        super.onDestroyPresenter();
        subscription.unsubscribe();
    }
}

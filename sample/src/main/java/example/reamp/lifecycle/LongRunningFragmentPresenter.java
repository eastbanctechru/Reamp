package example.reamp.lifecycle;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import etr.android.reamp.mvp.MvpPresenter;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;

public class LongRunningFragmentPresenter extends MvpPresenter<LongRunningFragmentState> {

    private Subscription subscription;

    @Override
    public void onFirstCreate() {
        super.onFirstCreate();
        subscription = Observable.interval(500, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        Log.d("Fragment", "call: " + aLong);
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

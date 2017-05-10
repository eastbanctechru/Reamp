package example.reamp.view;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import etr.android.reamp.mvp.MvpPresenter;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;

public class MyPresenter extends MvpPresenter<MyState> {

    private Subscription subscription;

    @Override
    public void onConnect() {
        super.onConnect();
        final Random random = new Random();
        subscription = Observable.interval(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        getStateModel().r = random.nextInt(255);
                        getStateModel().g = random.nextInt(255);
                        getStateModel().b = random.nextInt(255);
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

package etr.android.reamp.mvp.screen_one;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import etr.android.reamp.mvp.ReampPresenter;
import etr.android.reamp.mvp.SendStateModelExecutor;
import etr.android.reamp.mvp.screen_two.TwoNavigationUnit;
import etr.android.reamp.navigation.ResultProvider;

class OnePresenter extends ReampPresenter<OneModel> {

    @Override
    protected SendStateModelExecutor createSendStateModelExecutor() {
        return new SendStateModelExecutor.Unconfined();
    }

    void onIncrement() {
        getStateModel().counter++;
        sendStateModel();
    }

    void onDecrement() {
        getStateModel().counter--;
        sendStateModel();
    }

    void onOpenScreenTwo() {
        getNavigation().open(new TwoNavigationUnit());
    }

    @Override
    public void onResult(@NonNull ResultProvider resultProvider) {
        @Nullable final Integer twoResult = resultProvider.getResult(new TwoNavigationUnit());
        if (twoResult != null) {
            getStateModel().counter = twoResult;
            sendStateModel();
        }
    }
}

package etr.android.reamp.mvp.screen_one;

import android.support.annotation.NonNull;

import etr.android.reamp.functional.ConsumerNonNull;
import etr.android.reamp.mvp.ReampPresenter;
import etr.android.reamp.mvp.screen_two.TwoNavigationUnit;
import etr.android.reamp.navigation.ResultProvider;

class OnePresenter extends ReampPresenter<OneModel> {

    @Override
    public void onPresenterCreated() {
        super.onPresenterCreated();

        getStateModel().counter = getNavigation().getData(new OneNavigationUnit());
        sendStateModel();
    }

    void onIncrement() {
        updateStateModel(new ConsumerNonNull<OneModel>() {
            @Override
            public void consume(@NonNull OneModel oneModel) {
                oneModel.counter++;
            }
        });
    }

    void onDecrement() {
        getStateModel().counter--;
        sendStateModel();
    }

    void onOpenScreenTwo() {
        getNavigation().open(new TwoNavigationUnit(getStateModel().counter));
    }

    void onShow() {
        getStateModel().action.set(getStateModel().counter);
        sendStateModel();
    }

    void onShowEmpty() {
        getStateModel().emptyAction.set();
        sendStateModel();
    }

    @Override
    public void onResult(@NonNull ResultProvider resultProvider) {
        resultProvider.consumeResult(new TwoNavigationUnit(), new ConsumerNonNull<Integer>() {
            @Override
            public void consume(@NonNull Integer result) {
                getStateModel().counter = result;
                sendStateModel();
            }
        });
    }
}

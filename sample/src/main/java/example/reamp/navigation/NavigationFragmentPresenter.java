package example.reamp.navigation;

import androidx.annotation.NonNull;

import etr.android.reamp.functional.ConsumerNonNull;
import etr.android.reamp.mvp.ReampPresenter;
import etr.android.reamp.navigation.ResultProvider;
import example.reamp.navigation.details.DetailsWithData;
import example.reamp.navigation.details.DetailsWithResultUnit;

public class NavigationFragmentPresenter extends ReampPresenter<NavigationFragmentState> {

    // a basic way to use Navigation and NavigationUnit
    public void openBrowser() {
        getNavigation().open(new BrowserNavUnit());
    }

    public void openForResult() {
        DetailsWithResultUnit detailsNavigationUnit = new DetailsWithResultUnit();
        getNavigation().open(detailsNavigationUnit);
    }

    @Override
    public void onResult(@NonNull ResultProvider resultProvider) {
        super.onResult(resultProvider);

        resultProvider.consumeResult(new DetailsWithResultUnit(), new ConsumerNonNull<String>() {
            @Override
            public void consume(@NonNull String s) {
                getStateModel().resultText = s;
                sendStateModel();
            }
        });
    }

    public void openWithData() {
        getNavigation().open(new DetailsWithData().withData(getStateModel().mainText));
    }

    public void onMainTextChanged(String text) {
        getStateModel().mainText = text;
    }
}

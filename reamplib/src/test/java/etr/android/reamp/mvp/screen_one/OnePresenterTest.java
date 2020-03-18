package etr.android.reamp.mvp.screen_one;

import org.junit.Assert;
import org.junit.Test;

import etr.android.reamp.mvp.screen_two.TwoNavigationUnit;
import etr.android.reamp.navigation.ResultProvider;

public class OnePresenterTest {
    @Test
    public void test() {
        OnePresenter onePresenter = new OnePresenter();
        onePresenter.attachStateModel(new OneModel());

        onePresenter.addView(new OneView());

        Assert.assertEquals(0, onePresenter.getStateModel().counter);

        onePresenter.onIncrement();
        Assert.assertEquals(1, onePresenter.getStateModel().counter);

        onePresenter.onIncrement();
        Assert.assertEquals(2, onePresenter.getStateModel().counter);

        onePresenter.onDecrement();
        Assert.assertEquals(1, onePresenter.getStateModel().counter);

        onePresenter.onOpenScreenTwo();
        onePresenter.onResult(new ResultProvider.Test<>(TwoNavigationUnit.class, 14));
        Assert.assertEquals(14, onePresenter.getStateModel().counter);
    }
}

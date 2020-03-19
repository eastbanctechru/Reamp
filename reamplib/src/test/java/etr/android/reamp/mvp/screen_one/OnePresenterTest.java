package etr.android.reamp.mvp.screen_one;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import etr.android.reamp.mvp.MvpDelegate;
import etr.android.reamp.mvp.screen_two.TwoNavigationUnit;
import etr.android.reamp.navigation.Navigation;
import etr.android.reamp.navigation.ResultProvider;

public class OnePresenterTest {
    @Test
    public void test() {
        final Navigation navigation = Mockito.mock(Navigation.class);
        Mockito.when(navigation.getData(new OneNavigationUnit())).thenReturn(10);

        OnePresenter onePresenter = new OnePresenter() {
            @Override
            public Navigation getNavigation() {
                return navigation;
            }
        };

        TestReampView<OnePresenter, OneModel> view1 = new TestReampView<>(onePresenter, new OneModel());
        MvpDelegate mvpDelegate = new MvpDelegate(view1);
        mvpDelegate.onCreate(null);
        mvpDelegate.connect();

        Mockito.verify(navigation).getData(new OneNavigationUnit());
        Assert.assertEquals(10, view1.sm.counter);
        Util.assertNoValue(view1.sm.action);
        Util.assertAndConsume(view1.sm.emptyAction, false);

        onePresenter.onIncrement();
        Assert.assertEquals(11, onePresenter.getStateModel().counter);

        onePresenter.onShow();
        Util.assertHasValue(view1.sm.action, 11);

        onePresenter.onShowEmpty();
        Util.assertAndConsume(view1.sm.emptyAction, true);

        onePresenter.onIncrement();
        Assert.assertEquals(12, onePresenter.getStateModel().counter);

        onePresenter.onDecrement();
        Assert.assertEquals(11, onePresenter.getStateModel().counter);

        onePresenter.onOpenScreenTwo();
        Mockito.verify(navigation).open(new TwoNavigationUnit(11));

        mvpDelegate.onResult(new ResultProvider.Test<>(TwoNavigationUnit.class, 14));
        Assert.assertEquals(14, onePresenter.getStateModel().counter);

        onePresenter.onShow();
        Util.assertHasValue(view1.sm.action, 14);

        Mockito.verifyNoMoreInteractions(navigation);
    }
}

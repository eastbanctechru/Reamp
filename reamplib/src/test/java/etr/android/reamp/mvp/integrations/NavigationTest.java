package etr.android.reamp.mvp.integrations;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;

import org.junit.Assert;
import org.junit.Test;
import org.robolectric.Robolectric;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowIntent;

import java.lang.reflect.Field;

import etr.android.reamp.R;
import etr.android.reamp.debug.navigation.FirstActivity;
import etr.android.reamp.debug.navigation.FirstPresenter;
import etr.android.reamp.debug.navigation.NavFragment;
import etr.android.reamp.debug.navigation.NavFragmentPresenter;
import etr.android.reamp.debug.navigation.SecondActivity;
import etr.android.reamp.mvp.BaseTest;
import etr.android.reamp.navigation.ComplexNavigationUnit;
import etr.android.reamp.navigation.Navigation;
import etr.android.reamp.navigation.NavigationUnit;

import static junit.framework.Assert.assertEquals;
import static org.robolectric.Shadows.shadowOf;

public class NavigationTest extends BaseTest {

    @Test
    public void simple() throws Exception {
        FirstActivity activity = Robolectric.setupActivity(FirstActivity.class);
        FirstPresenter presenter = activity.getPresenter();
        ComplexNavigationUnit navUnit = new SimpleNavigationUnit();
        presenter.getNavigation().open(navUnit);
        Intent startedIntent = shadowOf(activity).getNextStartedActivity();
        ShadowIntent shadowIntent = shadowOf(startedIntent);
        assertEquals(SecondActivity.class, shadowIntent.getIntentClass());
        presenter.getNavigation().close();
    }

    @Test
    public void simpleResultNavigation() throws Exception {
        FirstActivity activity = Robolectric.setupActivity(FirstActivity.class);
        FirstPresenter presenter = activity.getPresenter();
        presenter.getNavigation().open(new ForResultUnit());
        ShadowActivity.IntentForResult forResult = shadowOf(activity).getNextStartedActivityForResult();
        shadowOf(activity).receiveResult(forResult.intent, Activity.RESULT_OK, new Intent().putExtra(ForResultUnit.EXTRA_DATA, 10));
        Integer result = presenter.getNavigation().getResult(new ForResultUnit(), presenter.requestCode, presenter.resultCode, presenter.data);
        Assert.assertEquals(presenter.requestCode, ForResultUnit.REQUEST_CODE);
        Assert.assertEquals(presenter.resultCode, Activity.RESULT_OK);
        Assert.assertTrue(10 == result);
    }

    @Test
    public void simpleResultNavigation2() throws Exception {
        SecondActivity activity = Robolectric.setupActivity(SecondActivity.class);
        Navigation navigation = activity.getPresenter().getNavigation();
        ForResultUnit navigationUnit = new ForResultUnit();
        navigationUnit.setaNumber(10);
        navigation.setResult(navigationUnit);
    }

    @Test
    public void navigateWithData() throws Exception {
        Intent intent = new Intent().putExtra(SimpleNavigationUnit.EXTRA_DATA, "EXTRA_VALUE");
        ActivityController<SecondActivity> controller = Robolectric.buildActivity(SecondActivity.class, intent);
        controller.setup();
        SecondActivity activity = controller.get();
        Navigation navigation = activity.getPresenter().getNavigation();
        SimpleNavigationUnit navigationUnit = new SimpleNavigationUnit();
        String data = navigation.getData(navigationUnit);
        Assert.assertEquals(data, "EXTRA_VALUE");
    }

    @Test
    public void simpleNavigationWithFragments() throws Exception {
        FirstActivity activity = Robolectric.setupActivity(FirstActivity.class);
        NavFragment fragment = (NavFragment) activity.getSupportFragmentManager().findFragmentById(R.id.test_fragment);
        NavFragmentPresenter presenter = fragment.getPresenter();
        presenter.getNavigation().open(new ForResultUnit());
        ShadowActivity.IntentForResult forResult = shadowOf(activity).getNextStartedActivityForResult();
        shadowOf(activity).receiveResult(forResult.intent, Activity.RESULT_OK, new Intent().putExtra(ForResultUnit.EXTRA_DATA, 10));
        Integer result = presenter.getNavigation().getResult(new ForResultUnit(), presenter.requestCode, presenter.resultCode, presenter.data);
        Assert.assertEquals(presenter.requestCode, ForResultUnit.REQUEST_CODE);
        Assert.assertEquals(presenter.resultCode, Activity.RESULT_OK);
        Assert.assertTrue(10 == result);
    }

    @Test
    public void fragmentNavigationNoPresenter() throws Exception {
        //the case when somehow the presenter is missing
        FirstActivity activity = Robolectric.setupActivity(FirstActivity.class);
        NavFragment fragment = (NavFragment) activity.getSupportFragmentManager().findFragmentById(R.id.test_fragment);
        NavFragmentPresenter presenter = fragment.getPresenter();
        presenter.getNavigation().open(new ForResultUnit());
        ShadowActivity.IntentForResult forResult = shadowOf(activity).getNextStartedActivityForResult();
        fragment.spoilPresenter();
        shadowOf(activity).receiveResult(forResult.intent, Activity.RESULT_OK, new Intent().putExtra(ForResultUnit.EXTRA_DATA, 10));
        Assert.assertEquals(presenter.requestCode, 0);
        Assert.assertEquals(presenter.resultCode, 0);
        Assert.assertEquals(presenter.data, null);
    }

    @Test
    public void checkRegularFragmentNavigation() throws Exception {
        FirstActivity activity = Robolectric.setupActivity(FirstActivity.class);
        NavFragment fragment = (NavFragment) activity.getSupportFragmentManager().findFragmentById(R.id.test_fragment);
        NavFragmentPresenter presenter = fragment.getPresenter();
        fragment.startActivityForResult(new Intent(activity, SecondActivity.class), 1);
        fragment.onActivityResult(1, Activity.RESULT_OK, new Intent().putExtra(ForResultUnit.EXTRA_DATA, 10));
        //by this time, navigation does not support result delivering if the activity was started from a fragment
        Assert.assertEquals(presenter.requestCode, 0);
        Assert.assertEquals(presenter.resultCode, 0);
        Assert.assertEquals(presenter.data, null);
    }

    @NonNull
    private static Intent getIntent(Navigation navigation) {
        return new Intent(navigation.getActivity(), SecondActivity.class);
    }

    private static class SimpleNavigationUnit extends NavigationUnit<String> {

        static final String EXTRA_DATA = "EXTRA_DATA";

        @Override
        protected void navigate(Navigation navigation) {
            navigation.getActivity().startActivity(getIntent(navigation));
        }

        @Override
        protected String getNavigationData(Navigation navigation) {
            super.getNavigationData(navigation);
            return navigation.getActivity().getIntent().getStringExtra(EXTRA_DATA);
        }

    }
    static class ForResultUnit extends ComplexNavigationUnit<String, Integer> {

        static final String EXTRA_DATA = "EXTRA_DATA";

        static int REQUEST_CODE = 1;
        private int aNumber;
        public void setaNumber(int aNumber) {
            this.aNumber = aNumber;
        }

        @Override
        protected void navigate(Navigation navigation) {
            navigation.getActivity().startActivityForResult(getIntent(navigation), REQUEST_CODE);
        }

        @Override
        protected void setNavigationResult(Navigation navigation) {
            super.setNavigationResult(navigation);
            navigation.getActivity().setResult(Activity.RESULT_OK, new Intent().putExtra(EXTRA_DATA, aNumber));
        }

        @Override
        protected Integer getNavigationResult(Navigation navigation, int requestCode, int resultCode, Intent data) {
            super.getNavigationResult(navigation, requestCode, resultCode, data);
            if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
                return data.getIntExtra(EXTRA_DATA, 0);
            }
            return null;
        }

    }
}

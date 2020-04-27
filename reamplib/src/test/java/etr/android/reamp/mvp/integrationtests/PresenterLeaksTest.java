package etr.android.reamp.mvp.integrationtests;

import android.os.Bundle;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.robolectric.Robolectric;
import org.robolectric.android.controller.ActivityController;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import etr.android.reamp.R;
import etr.android.reamp.mvp.BaseTest;
import etr.android.reamp.mvp.PresenterManager;
import etr.android.reamp.mvp.ReampPresenter;
import etr.android.reamp.mvp.ReampProvider;
import etr.android.reamp.mvp.ReampView;
import etr.android.reamp.mvp.internal.EmptyAppCompatActivity;
import etr.android.reamp.mvp.internal.TestReampFragment;

public class PresenterLeaksTest extends BaseTest {

    @Before
    public void setUp() throws Exception {
        Robolectric.setupContentProvider(ReampProvider.class);
    }

    @Test
    public void checkPhantoms() throws Exception {

        // check that all context's presenters are removed when the activity is removed

        Set<String> spottedIds = new HashSet<>();

        ActivityController<EmptyAppCompatActivity> controller = Robolectric.buildActivity(EmptyAppCompatActivity.class);
        EmptyAppCompatActivity activity = controller.setup().get();

        for (int i = 0; i < 3; i++) {
            /*
            todo
            Раньше, когда targetSdkVersion был 25, фрагменты добавлялись в бек-стек,
            и тест проходил проверку "only the last fragment has attached".
            После миграции на AndroidX и поднятия targetSdkVersion до 29 тест стал падать в этом месте.
            В результате убрали добавление фрагментов в бек-стек и все заработало.
            Почему оно работало раньше -- не понятно. Так как:
            - в MvpDelegate.onCreate вызывается presenterManager.registerView(view, view.getContext());
            - и если положить фрагменты в бек стек, то после поворота активити у фрагментов будет вызван onCreate и все 3 фрагмента вызовут registerView.
            - у фрагментов в бек-стеке точно вызываются onCreate после поворота активити.
             */
            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.root, new TestReampFragment())
                    .commit();
        }

        List<ReampView> views = PresenterManager.getInstance().getViewsOf(activity);
        Assert.assertEquals(views.size(), 3);
        saveToSpotted(views, spottedIds);

        for (int i = 0; i < 1; i++) {
            //rotate
            Bundle bundle = new Bundle();
            controller.saveInstanceState(bundle).pause().stop().destroy();
            controller = Robolectric.buildActivity(EmptyAppCompatActivity.class);
            activity = controller.create(bundle).start().restoreInstanceState(bundle).resume().get();

            views = PresenterManager.getInstance().getViewsOf(activity);
            Assert.assertEquals(views.size(), 1); //only the last fragment has attached
            saveToSpotted(views, spottedIds);
            List<String> phantomViews = PresenterManager.getInstance().getPhantomViews(activity);
            Assert.assertEquals(phantomViews.size(), 3);
        }

        Assert.assertEquals(spottedIds.size(), 3);

        controller.userLeaving();
        activity.finish(); //make activity to think that it is being finished
        controller.pause().stop().destroy();

        for (String spottedId : spottedIds) {
            ReampPresenter presenter = PresenterManager.getInstance().getPresenter(spottedId);
            Assert.assertNull(presenter);
        }
    }

    private void saveToSpotted(List<ReampView> views, Set<String> spottedIds) {
        for (ReampView view : views) {
            spottedIds.add(view.getMvpId());
        }
    }
}

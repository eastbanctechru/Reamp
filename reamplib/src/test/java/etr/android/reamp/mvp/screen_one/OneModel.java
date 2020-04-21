package etr.android.reamp.mvp.screen_one;

import etr.android.reamp.mvp.Action;
import etr.android.reamp.mvp.EmptyAction;
import etr.android.reamp.mvp.SerializableStateModel;

class OneModel extends SerializableStateModel {
    int counter = 0;

    final Action<Integer> action = new Action<>();

    final EmptyAction emptyAction = new EmptyAction();
}

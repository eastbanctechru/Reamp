package example.reamp.navigation.details;

import etr.android.reamp.mvp.Action;
import etr.android.reamp.mvp.SerializableStateModel;

public class DetailsFragmentStateModel extends SerializableStateModel {
    public String text;
    public Action<String> showDataAction = new Action<>();
}

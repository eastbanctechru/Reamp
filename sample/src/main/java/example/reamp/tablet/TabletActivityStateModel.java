package example.reamp.tablet;

import etr.android.reamp.mvp.Consumable;
import etr.android.reamp.mvp.SerializableStateModel;

public class TabletActivityStateModel extends SerializableStateModel {
    public boolean showDetails;
    public Consumable<Boolean> broadcastResult = new Consumable<>(false);
}

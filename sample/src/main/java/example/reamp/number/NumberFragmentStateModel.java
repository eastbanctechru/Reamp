package example.reamp.number;

import etr.android.reamp.mvp.Consumable;
import etr.android.reamp.mvp.SerializableStateModel;

public class NumberFragmentStateModel extends SerializableStateModel {
    public String numberText;
    public Consumable<String> showErrorToast = new Consumable<>(null);
}

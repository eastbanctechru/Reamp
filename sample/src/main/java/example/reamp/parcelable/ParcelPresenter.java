package example.reamp.parcelable;

import android.os.Bundle;

import etr.android.reamp.mvp.ReampPresenter;

public class ParcelPresenter extends ReampPresenter<ParcelableState> {

    private static final String PARCELABLE_STATE = "parcelable_state";

    public void increment() {
        getStateModel().counter++;
        sendStateModel();
    }

    @Override
    public Bundle serializeState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(PARCELABLE_STATE, getStateModel());
        return bundle;
    }

    @Override
    public ParcelableState deserializeState(Bundle savedInstance) {
        return savedInstance.getParcelable(PARCELABLE_STATE);
    }
}

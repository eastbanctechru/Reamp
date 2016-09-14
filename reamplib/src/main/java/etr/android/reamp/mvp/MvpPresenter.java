package etr.android.reamp.mvp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import etr.android.reamp.BuildConfig;
import etr.android.reamp.navigation.Navigation;
import rx.subjects.BehaviorSubject;

public class MvpPresenter<SM extends MvpStateModel> {

    private static final String TAG = "MvpPresenter";
    private static final String EXTRA_INSTANCE_STATE = "EXTRA_INSTANCE_STATE";
    private BehaviorSubject<SM> stateSubject;

    private SM stateModel;
    private MvpView view;
    private Navigation navigation;

    public void attachStateModel(SM stateModel) {
        stateSubject = BehaviorSubject.create(stateModel);
        this.stateModel = stateModel;
    }

    public void setNavigation(Navigation navigation) {
        this.navigation = navigation;
    }

    public Navigation getNavigation() {
        return navigation;
    }

    public SM getStateModel() {
        return stateModel;
    }

    public final void sendStateModel(SM stateModel) {
        stateSubject.onNext(stateModel);
    }

    public final void sendStateModel() {
        sendStateModel(getStateModel());
    }

    public void setView(MvpView view) {
        this.view = view;
    }

    public void onFirstCreate() {

    }

    public void onRestore(Bundle savedInstanceState) {

    }

    public void onCreate() {

    }

    public void onResume() {

    }

    public MvpView getView() {
        return view;
    }

    public BehaviorSubject<SM> getStateUpdater() {
        return stateSubject;
    }

    public SM deserializeState(Bundle savedInstance) {
        try {
            Serializable serializable = fromByteArray(savedInstance.getByteArray(EXTRA_INSTANCE_STATE));
            return (SM) serializable;
        } catch (Exception e) {
            Log.e(TAG, "Can not deserialize state model: ", e);
            if (BuildConfig.DEBUG) {
                throw new RuntimeException("Can not deserialize state model", e);
            }
            return null;
        }
    }

    public Bundle serializeState() {
        if (stateModel instanceof Serializable) {
            try {
                Bundle bundle = new Bundle();
                bundle.putByteArray(EXTRA_INSTANCE_STATE, toByteArray((Serializable) stateModel));
                return bundle;
            } catch (Exception e) {
                Log.e(TAG, "Can not serialize state model: ", e);
                if (BuildConfig.DEBUG) {
                    throw new RuntimeException("Can not serialize state model", e);
                }
                return null;
            }
        }
        Log.d(TAG, "State model is no serializable: " + stateModel);
        return null;
    }

    private byte[] toByteArray(Serializable stateModel) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = new ObjectOutputStream(bos);
        out.writeObject(stateModel);
        return bos.toByteArray();
    }

    private Serializable fromByteArray(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInput in = new ObjectInputStream(bis);
        Object o = in.readObject();
        return (Serializable) o;

    }

    public void onResult(int requestCode, int resultCode, Intent data) {

    }

    public void onPause() {

    }

    public void onDestroy() {

    }

    public void onDestroyPresenter() {

    }
}

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
        SM sm = null;
        try {
            sm = deserializeState(savedInstanceState);
        } catch (Exception e) {
            Log.d("Parse error", "Can't deserialize state model for" + this);
        }
        if (sm != null) {
            sendStateModel(sm);
            this.stateModel = sm;
        }
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

    protected SM deserializeState(Bundle savedInstance) {
        Serializable serializable = fromByteArray(savedInstance.getByteArray(EXTRA_INSTANCE_STATE));
        return (SM) serializable;
    }

    public Bundle serializeState() {
        Bundle bundle = new Bundle();
        if (stateModel instanceof Serializable) {
            bundle.putByteArray(EXTRA_INSTANCE_STATE, toByteArray((Serializable) stateModel));
        }
        return bundle;
    }

    private byte[] toByteArray(Serializable stateModel) {
        byte[] bytes = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = null;
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(stateModel);
            bytes = bos.toByteArray();
        } catch (IOException e) {
            Log.e(TAG, "toByteArray ", e);
        } finally {
            try {
                bos.close();
                if (out != null) {
                    out.close();
                }
            } catch (IOException ignore) {
            }
        }

        return bytes;
    }

    private Serializable fromByteArray(byte[] bytes) {

        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInput in = null;
        Object o = null;
        try {
            in = new ObjectInputStream(bis);
            o = in.readObject();
        } catch (ClassNotFoundException | IOException ignore) {

        } finally {
            try {
                bis.close();
                if (in != null) {
                    in.close();
                }
            } catch (IOException ignore) {
            }
        }

        return (Serializable) o;

    }

    public void onResult(int requestCode, int resultCode, Intent data) {

    }

    public void onPause() {

    }

    public void onDestroy() {

    }
}

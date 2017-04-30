package etr.android.reamp.mvp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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

public class MvpPresenter<SM extends MvpStateModel> {

    private static final String TAG = "MvpPresenter";
    private static final String EXTRA_INSTANCE_STATE = "EXTRA_INSTANCE_STATE";
    private final Handler uiHandler = new Handler(Looper.getMainLooper());

    private SM stateModel;
    private MvpView view;
    private StateChanges stateChanges;

    public void attachStateModel(SM stateModel) {
        this.stateModel = stateModel;
    }

    public SM getStateModel() {
        return stateModel;
    }

    public final void sendStateModel(final SM stateModel) {
        if (stateChanges == null) {
            return;
        }
        if (Looper.myLooper() != Looper.getMainLooper()) {
            uiHandler.post(new Runnable() {
                @Override
                public void run() {
                    sendStateModel(stateModel);
                }
            });
        } else {
            try {
                stateChanges.onNewState(stateModel);
            } catch (Throwable e) {
                stateChanges.onError(e);
            }
        }
    }

    public final void sendStateModel() {
        sendStateModel(getStateModel());
    }

    public void setView(MvpView view) {
        this.view = view;
    }

    public void onPresenterCreated() {

    }

    public MvpView getView() {
        return view;
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

    /**
     * @deprecated Leave result parsing to the activity/fragment and make a presenter's method
     * to handle the result. Do not use this callback in a fragment's presenters if the host activity is not an MvpView
     */
    @Deprecated
    public void onResult(int requestCode, int resultCode, Intent data) {

    }

    public void onDestroyPresenter() {

    }

    public void onConnect() {

    }

    public void onDisconnect() {

    }

    public Navigation getNavigation() {
        return new Navigation((Activity) getView().getContext());
    }

    public void connect(StateChanges stateChanges) {
        this.stateChanges = stateChanges;
        sendStateModel();
    }

    public void disconnect() {
        stateChanges = null;
        uiHandler.removeCallbacks(null);
    }
}

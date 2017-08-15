package etr.android.reamp.mvp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import etr.android.reamp.BuildConfig;
import etr.android.reamp.navigation.Navigation;

public class ReampPresenter<SM extends ReampStateModel> {

    private static final String TAG = "ReampPresenter";
    private static final String EXTRA_INSTANCE_STATE = "EXTRA_INSTANCE_STATE";
    private final Handler uiHandler = new Handler(Looper.getMainLooper());

    private SM stateModel;
    private List<ReampView> views = new ArrayList<>();
    private List<StateChanges> stateChanges = new ArrayList<>();
    private boolean throwOnSerializationError = BuildConfig.DEBUG;

    public void attachStateModel(SM stateModel) {
        this.stateModel = stateModel;
    }

    /**
     * @return current state model
     */
    public SM getStateModel() {
        return stateModel;
    }

    /**
     * Send a state model to a view and save it as a current.
     * If the view is attached, {@link ReampView#onStateChanged(ReampStateModel)} is called.
     * If the view is not attached, {@link ReampView#onStateChanged(ReampStateModel)} will be called when the view is attached
     */
    public final void sendStateModel(final SM stateModel) {
        this.stateModel = stateModel;
        if (Looper.myLooper() != Looper.getMainLooper()) {
            uiHandler.post(new Runnable() {
                @Override
                public void run() {
                    sendStateModel(stateModel);
                }
            });
        } else {
            for (StateChanges stateChange : stateChanges) {
                try {
                    stateChange.onNewState(stateModel);
                } catch (Throwable e) {
                    stateChange.onError(e);
                }
            }
        }
    }

    /**
     * Send the current state model
     *
     * @see ReampPresenter#sendStateModel(ReampStateModel)
     */
    public final void sendStateModel() {
        sendStateModel(getStateModel());
    }

    public void addView(@NonNull ReampView view) {
        views.add(view);
    }

    public void removeView(@NonNull ReampView view) {
        views.remove(view);
    }

    /**
     * Called when the presenter is first created
     * <br/>
     * <i>Note: this method is not called when a view is re-attached</i>
     */
    public void onPresenterCreated() {

    }

    public ReampView getView() {
        if (views.isEmpty()) {
            return null;
        }
        return views.get(0);
    }

    public List<ReampView> getViews() {
        return new ArrayList<>(views);
    }

    /**
     * Restores a state model from the bundle
     *
     * @param savedInstance a bundle the state model saved into
     * @return restored {@link ReampStateModel} or null
     * @see ReampPresenter#serializeState()
     */
    public SM deserializeState(Bundle savedInstance) {
        try {
            Serializable serializable = fromByteArray(savedInstance.getByteArray(EXTRA_INSTANCE_STATE));
            return (SM) serializable;
        } catch (Exception e) {
            Log.e(TAG, "Can not deserialize state model: ", e);
            if (throwOnSerializationError()) {
                throw new RuntimeException("Can not deserialize state model", e);
            }
            return null;
        }
    }

    /**
     * Saves the current state model to a Bundle when the view serializes its' state
     * The default implementation attempts to serialize the current state if the state is {@link Serializable}
     * and does nothing otherwise
     *
     * @return a bundle with saved state model or null
     * @see ReampPresenter#deserializeState(Bundle)
     */
    public Bundle serializeState() {
        if (stateModel instanceof Serializable) {
            try {
                Bundle bundle = new Bundle();
                bundle.putByteArray(EXTRA_INSTANCE_STATE, toByteArray((Serializable) stateModel));
                return bundle;
            } catch (Exception e) {
                Log.e(TAG, "Can not serialize state model: ", e);
                if (throwOnSerializationError()) {
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
     * Callback from an activity or a fragment when they receive a result intent
     * Do not use this callback in a fragment's presenters if the host activity is not an ReampView
     */
    public void onResult(int requestCode, int resultCode, Intent data) {

    }

    /**
     * Called when the presenter is going to be completely destroyed
     */
    public void onDestroyPresenter() {

    }

    /**
     * Called when the first view has been connected
     * @see ReampPresenter#onConnect(ReampView)
     * @see ReampPresenter#onDisconnect(ReampView) ()
     * @see ReampPresenter#onDisconnect()
     */
    public void onConnect() {

    }

    /**
     * Called when the presenter's view is ready to receive updates ({@link ReampStateModel})
     * <br/>
     * In general, this means that the view is visible to a user and ready to show some data
     *
     * @see ReampPresenter#onConnect(ReampView)
     * @see ReampPresenter#onDisconnect(ReampView) ()
     * @see ReampPresenter#onDisconnect()
     */
    public void onConnect(ReampView view) {

    }

    /**
     * Called when the last view has been disconnected
     * @see ReampPresenter#onConnect(ReampView)
     * @see ReampPresenter#onDisconnect(ReampView) ()
     * @see ReampPresenter#onDisconnect()
     */
    public void onDisconnect() {

    }

    /**
     * Called when the view stops receiving updates from its' presenter
     * <br/>
     * In general, this means that the view is not visible to a user
     *
     * @see ReampPresenter#onConnect(ReampView)
     * @see ReampPresenter#onDisconnect(ReampView) ()
     * @see ReampPresenter#onDisconnect()
     */
    public void onDisconnect(ReampView view) {

    }

    public Navigation getNavigation() {
        return new Navigation(getView());
    }

    public void connect(StateChanges stateChanges, ReampView reampView) {
        this.stateChanges.add(stateChanges);
        sendStateModel();

        int activeViews = this.stateChanges.size();
        if (activeViews == 1) {
            onConnect();
        }
        onConnect(reampView);
    }

    public void disconnect(StateChanges stateChanges, ReampView reampView) {
        this.stateChanges.remove(stateChanges);

        onDisconnect(reampView);
        int activeViews = this.stateChanges.size();
        if (activeViews == 0) {
            onDisconnect();
        }
    }

    protected boolean throwOnSerializationError() {
        return throwOnSerializationError;
    }

    public void setThrowOnSerializationError(boolean throwOnSerializationError) {
        this.throwOnSerializationError = throwOnSerializationError;
    }

    public void releaseAllViews() {
        views.clear();
        stateChanges.clear();
        uiHandler.removeCallbacks(null);
    }
}

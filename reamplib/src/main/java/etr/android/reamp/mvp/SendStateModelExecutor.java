package etr.android.reamp.mvp;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

public abstract class SendStateModelExecutor {
    public abstract void execute(@NonNull Runnable sendStateModel);

    public abstract void cancelAll();

    @SuppressLint("ObsoleteSdkInt")
    public static SendStateModelExecutor createDefault() {
        return Build.VERSION.SDK_INT > 0 // SDK_INT == 0 in unit tests.
                ? new UiThread()
                : new Unconfined();
    }

    public static final class UiThread extends SendStateModelExecutor {
        private final Handler uiHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable sendStateModel) {
            if (Looper.myLooper() != Looper.getMainLooper()) {
                uiHandler.post(sendStateModel);
            } else {
                sendStateModel.run();
            }
        }

        @Override
        public void cancelAll() {
            uiHandler.removeCallbacks(null);
        }
    }

    public static final class Unconfined extends SendStateModelExecutor {
        @Override
        public void execute(@NonNull Runnable sendStateModel) {
            sendStateModel.run();
        }

        @Override
        public void cancelAll() {
            // nothing.
        }
    }
}
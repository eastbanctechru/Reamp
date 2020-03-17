package etr.android.reamp.mvp;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

public interface SendStateModelExecutor {
    void execute(@NonNull Runnable sendStateModel);

    void cancelAll();

    final class UiThread implements SendStateModelExecutor {
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

    final class Unconfined implements SendStateModelExecutor {
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
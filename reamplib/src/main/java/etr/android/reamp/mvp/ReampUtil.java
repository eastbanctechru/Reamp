package etr.android.reamp.mvp;

import android.support.annotation.NonNull;

public final class ReampUtil {
    public static void consumeBooleanAction(@NonNull Action<Boolean> action, @NonNull Runnable consumer) {
        if (action.hasAction() && action.get()) {
            consumer.run();
        }
    }
}

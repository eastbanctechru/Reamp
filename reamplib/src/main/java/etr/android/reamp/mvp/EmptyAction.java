package etr.android.reamp.mvp;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class EmptyAction implements Serializable {

    private boolean hasAction;

    public void set() {
        hasAction = true;
    }

    public void consume(@NonNull Runnable consumer) {
        if (hasAction) {
            hasAction = false;
            consumer.run();
        }
    }
}

package etr.android.reamp.mvp;

import java.io.Serializable;

public class Action<T extends Serializable> implements Serializable {

    private T value;
    private boolean hasAction;

    public void set(T value) {
        this.value = value;
        hasAction = true;
    }

    public T get() {
        if (!hasAction) {
            throw new IllegalStateException("No action yet");
        }
        T result = value;
        value = null;
        hasAction = false;
        return result;
    }

    public boolean hasAction() {
        return hasAction;
    }
}

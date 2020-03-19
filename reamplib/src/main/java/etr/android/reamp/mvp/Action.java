package etr.android.reamp.mvp;

import android.support.annotation.NonNull;

import java.io.Serializable;

import etr.android.reamp.functional.Consumer;

/**
 * A handy container-class which can be used to store "one-shot" data into {@link ReampStateModel}
 * <br/>
 * Use {@link Action#set(Serializable)} to store the value
 * <br/>
 * Use {@link Action#hasAction()} to check if there is an available value
 * <br/>
 * Use {@link Action#get()} to retrieve the value and remove it from the container
 */
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

    public void consume(@NonNull Consumer<T> consumer) {
        if (hasAction()) {
            consumer.consume(get());
        }
    }
}

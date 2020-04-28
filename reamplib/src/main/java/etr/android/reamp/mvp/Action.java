package etr.android.reamp.mvp;

import androidx.annotation.NonNull;

import java.io.Serializable;

import etr.android.reamp.functional.ConsumerNonNull;

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

    public void set(@NonNull T value) {
        this.value = value;
        hasAction = true;
    }

    /**
     * @deprecated use {@link Action#consume(ConsumerNonNull)}.
     */
    @Deprecated
    @NonNull
    public T get() {
        if (!hasAction) {
            throw new IllegalStateException("No action yet");
        }
        T result = value;
        value = null;
        hasAction = false;
        return result;
    }

    /**
     * @deprecated use {@link Action#consume(ConsumerNonNull)}.
     */
    @Deprecated
    public boolean hasAction() {
        return hasAction;
    }

    public void consume(@NonNull ConsumerNonNull<T> consumer) {
        if (hasAction()) {
            consumer.consume(get());
        }
    }
}

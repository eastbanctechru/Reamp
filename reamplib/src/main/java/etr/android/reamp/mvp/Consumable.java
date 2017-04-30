package etr.android.reamp.mvp;

import java.io.Serializable;

/**
 * @deprecated Use {@link Action}
 */
@Deprecated
public class Consumable<T> implements Serializable {

    private final T defaultValue;
    private T value;

    public Consumable(T value) {
        this.value = value;
        this.defaultValue = value;
    }

    public void set(T value) {
        this.value = value;
    }

    public T get() {
        T result = value;
        value = defaultValue;
        return result;
    }
}

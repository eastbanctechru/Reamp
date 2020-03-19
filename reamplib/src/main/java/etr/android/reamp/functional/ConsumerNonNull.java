package etr.android.reamp.functional;

import android.support.annotation.NonNull;

public interface ConsumerNonNull<T> {
    void consume(@NonNull T t);
}

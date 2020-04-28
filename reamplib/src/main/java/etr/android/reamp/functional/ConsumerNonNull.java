package etr.android.reamp.functional;

import androidx.annotation.NonNull;

public interface ConsumerNonNull<T> {
    void consume(@NonNull T t);
}

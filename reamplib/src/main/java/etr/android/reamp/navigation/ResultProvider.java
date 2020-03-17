package etr.android.reamp.navigation;

import android.content.Intent;
import android.support.annotation.NonNull;

public interface ResultProvider {
    <R> R getResult(@NonNull ComplexNavigationUnit<?, R> navigationUnit);

    final class Android implements ResultProvider {
        @NonNull
        private final Navigation navigation;
        private final int requestCode;
        private final int resultCode;
        private final Intent data;

        public Android(@NonNull Navigation navigation, int requestCode, int resultCode, Intent data) {
            this.navigation = navigation;
            this.requestCode = requestCode;
            this.resultCode = resultCode;
            this.data = data;
        }

        @Override
        public <R> R getResult(@NonNull ComplexNavigationUnit<?, R> navigationUnit) {
            return navigation.getResult(navigationUnit, requestCode, resultCode, data);
        }
    }
}
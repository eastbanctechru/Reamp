package example.reamp.navigation;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import etr.android.reamp.navigation.ComplexNavigationUnit;
import etr.android.reamp.navigation.Navigation;
import etr.android.reamp.navigation.NavigationUnit;

/**
 * This unit knows how to open a system browser
 * Use it with {@link Navigation#open(ComplexNavigationUnit)}
 */
class BrowserNavUnit extends NavigationUnit {

    @Override
    protected void navigate(Navigation navigation) {
        Activity activity = navigation.getActivity();
        activity.startActivity(makeBrowserIntent());
    }

    private Intent makeBrowserIntent() {
        return new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
    }
}

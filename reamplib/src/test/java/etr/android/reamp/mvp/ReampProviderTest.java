package etr.android.reamp.mvp;

import android.net.Uri;

import org.junit.Test;
import org.robolectric.Robolectric;

public class ReampProviderTest extends BaseTest {

    @Test
    public void testEmptyMethods() throws Exception {
        ReampProvider reampProvider = Robolectric.setupContentProvider(ReampProvider.class);
        reampProvider.query(Uri.EMPTY, null, null, null, null);
        reampProvider.update(Uri.EMPTY, null, null, null);
        reampProvider.getType(Uri.EMPTY);
        reampProvider.insert(Uri.EMPTY, null);
        reampProvider.delete(Uri.EMPTY, null, null);
    }
}
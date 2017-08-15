package etr.android.reamp.mvp;

import org.junit.Assert;
import org.junit.Test;

public class ConsumableTest extends BaseTest {

    @Test
    public void basics() throws Exception {
        Consumable<String> consumable = new Consumable<>(null);
        Assert.assertEquals(consumable.get(), null);
        consumable.set("test");
        Assert.assertEquals(consumable.get(), "test");
        Assert.assertEquals(consumable.get(), null);
    }
}
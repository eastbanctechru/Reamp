package etr.android.reamp.mvp;

import org.junit.Assert;
import org.junit.Test;

public class ActionTest extends BaseTest {

    @Test
    public void basics() {
        Action<String> action = new Action<>();
        action.set("test");
        Assert.assertTrue(action.hasAction());
        String actionValue = action.get();
        Assert.assertEquals("test", actionValue);
        Assert.assertFalse(action.hasAction());
    }

    @Test
    public void illegalState() throws Exception {
        Action<String> action = new Action<>();
        try {
            action.get();
        } catch (Exception e) {
            Assert.assertTrue(e instanceof IllegalStateException);
        }
    }

    @Test
    public void serialization() throws Exception {
        Action<String> action = new Action<>();
        byte[] bytes = new TestSerializer().toBytes(action);
        Action<String> actionFromBytes = new TestSerializer().fromBytes(bytes);
        Assert.assertFalse(actionFromBytes.hasAction());
        try {
            actionFromBytes.get();
        } catch (Exception e) {
            Assert.assertTrue(e instanceof IllegalStateException);
        }
    }

    @Test
    public void serializationWithContent() throws Exception {
        Action<String> action = new Action<>();
        action.set("test");
        byte[] bytes = new TestSerializer().toBytes(action);
        Action<String> actionFromBytes = new TestSerializer().fromBytes(bytes);
        Assert.assertEquals(actionFromBytes.get(), "test");
    }
}
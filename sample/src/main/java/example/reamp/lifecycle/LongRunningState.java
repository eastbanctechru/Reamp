package example.reamp.lifecycle;

import java.io.Serializable;

import etr.android.reamp.mvp.MvpStateModel;

public class LongRunningState extends MvpStateModel implements Serializable {
    public long times;
}

package example.reamp.lifecycle;

import java.io.Serializable;

import etr.android.reamp.mvp.MvpStateModel;

public class RunningState extends MvpStateModel implements Serializable {
    public long times;
}

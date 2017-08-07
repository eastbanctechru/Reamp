package example.reamp.lifecycle;

import java.io.Serializable;

import etr.android.reamp.mvp.ReampStateModel;

public class RunningState extends ReampStateModel implements Serializable {
    public long times;
}

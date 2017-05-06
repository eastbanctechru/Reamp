package etr.android.reamp.debug;

import etr.android.reamp.mvp.MvpAppCompatActivity;
import etr.android.reamp.mvp.MvpPresenter;

public class NotSerializableActivity extends MvpAppCompatActivity<NotSerializablePresenter, NotSerializableState> {
    @Override
    public void onStateChanged(NotSerializableState stateModel) {

    }

    @Override
    public NotSerializableState onCreateStateModel() {
        return new NotSerializableState();
    }

    @Override
    public MvpPresenter<NotSerializableState> onCreatePresenter() {
        return new NotSerializablePresenter();
    }
}

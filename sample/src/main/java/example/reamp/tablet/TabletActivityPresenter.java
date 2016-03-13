package example.reamp.tablet;

import etr.android.reamp.mvp.MvpPresenter;

public class TabletActivityPresenter extends MvpPresenter<TabletActivityStateModel> {
    public void openDetails() {
        getStateModel().showDetails = true;
        sendStateModel();
    }

    public void onSendDetailsBack() {
        getStateModel().showDetails = false;
        getStateModel().broadcastResult.set(true);
        sendStateModel();
    }
}

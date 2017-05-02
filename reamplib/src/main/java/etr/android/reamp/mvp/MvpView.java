package etr.android.reamp.mvp;

import android.content.Context;

/**
 * Interface which you implement to work with Reamp
 */
public interface MvpView<SM extends MvpStateModel> {

    Context getContext();

    /**
     * Called when the presenter has a new state model
     * <br/>
     * You should render your current view according to the state model passed
     */
    void onStateChanged(SM stateModel);

    /**
     * Called when the presenter encountered an error and wants to show it instead of sending a new state model
     */
    void onError(Throwable throwable);

    /**
     * Provides a view's ID.
     * Views with the same IDs are connected to the same presenters.
     * <br/>
     * In general, you may want to delegate this call to {@link MvpDelegate#getId()}
     */
    String getMvpId();

    /**
     * Asks the view implementation to create a new state model
     */
    SM onCreateStateModel();

    /**
     * Asks the view implementation to create a new presenter
     */
    MvpPresenter<SM> onCreatePresenter();

    /**
     * Provides the current view presenter.
     * <br/>
     * In general, you may want to delegate this call to {@link MvpDelegate#getPresenter()}
     */
    MvpPresenter<SM> getPresenter();
}

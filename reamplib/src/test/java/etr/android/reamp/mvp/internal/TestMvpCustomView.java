package etr.android.reamp.mvp.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import etr.android.reamp.mvp.MvpDelegate;
import etr.android.reamp.mvp.MvpPresenter;
import etr.android.reamp.mvp.MvpView;

public class TestMvpCustomView extends View implements MvpView<TesteeState> {

    MvpDelegate delegate = new MvpDelegate(this);
    public int counter;
    private Bundle savedState;

    public TestMvpCustomView(Context context) {
        super(context);
    }

    public TestMvpCustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TestMvpCustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onStateChanged(TesteeState stateModel) {
        this.counter = stateModel.counter;
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public String getMvpId() {
        return delegate.getId();
    }

    @Override
    public TesteeState onCreateStateModel() {
        return new TesteeState();
    }

    @Override
    public TesteePresenter onCreatePresenter() {
        return new TesteePresenter();
    }

    @Override
    public TesteePresenter getPresenter() {
        MvpPresenter presenter = delegate.getPresenter();
        return (TesteePresenter) presenter;
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable parcelable = super.onSaveInstanceState();
        Bundle state = new Bundle();
        delegate.onSaveInstanceState(state);
        return new SavedState(parcelable, state);
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        this.savedState = ss.bundle;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        delegate.onCreate(savedState);
        delegate.connect();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        delegate.disconnect();
        delegate.onDestroy();
    }

    public static class SavedState extends BaseSavedState {
        private final Bundle bundle;

        public SavedState(Parcelable parcelable, Bundle bundle) {
            super(parcelable);
            this.bundle = bundle;
        }

        private SavedState(Parcel in) {
            super(in);
            bundle = in.readBundle(getClass().getClassLoader());
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeBundle(bundle);
        }

        public static final Parcelable.Creator<SavedState> CREATOR =
                new Parcelable.Creator<SavedState>() {
                    public SavedState createFromParcel(Parcel in) {
                        return new SavedState(in);
                    }

                    public SavedState[] newArray(int size) {
                        return new SavedState[size];
                    }
                };
    }
}

package example.reamp.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import etr.android.reamp.mvp.MvpDelegate;
import etr.android.reamp.mvp.MvpView;

public class MyView extends View implements MvpView<MyState> {

    private static final String TAG = "MyView";
    private MvpDelegate mvpDelegate = new MvpDelegate(this);
    private Bundle savedState;
    private Paint textPaint;

    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private static final String TEXT = "Hi, I'm a custom view";

    private final float textWidth;

    {
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(50);
        textPaint.setColor(Color.MAGENTA);
        textWidth = textPaint.measureText(TEXT);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(500, 500);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText(TEXT, getWidth() / 2 - textWidth / 2, getHeight() / 2, textPaint);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable parcelable = super.onSaveInstanceState();
        Bundle state = new Bundle();
        mvpDelegate.onSaveInstanceState(state);
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
        mvpDelegate.onCreate(savedState);
        mvpDelegate.connect();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mvpDelegate.disconnect();
        mvpDelegate.onDestroy();
    }

    @Override
    public void onStateChanged(MyState stateModel) {
        textPaint.setColor(Color.rgb(stateModel.r, stateModel.g, stateModel.b));
        invalidate();
    }

    @Override
    public void onError(Throwable throwable) {
        Log.d(TAG, "onError() called with: throwable = [" + throwable + "]");
    }

    @Override
    public String getMvpId() {
        return mvpDelegate.getId();
    }

    @Override
    public MyState onCreateStateModel() {
        return new MyState();
    }

    @Override
    public MyPresenter onCreatePresenter() {
        return new MyPresenter();
    }

    @Override
    public MyPresenter getPresenter() {
        return mvpDelegate.<MyPresenter, MyState>getPresenter();
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

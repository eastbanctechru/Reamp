package example.reamp.parcelable;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import etr.android.reamp.mvp.ReampAppCompatActivity;
import etr.android.reamp.mvp.ReampPresenter;
import example.reamp.R;

public class ParcelActivity extends ReampAppCompatActivity<ParcelPresenter, ParcelableState> {

    private TextView counterView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parcel);
        findViewById(R.id.increment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPresenter().increment();
            }
        });
        counterView = (TextView) findViewById(R.id.counter);
    }

    @Override
    public void onStateChanged(ParcelableState stateModel) {
        counterView.setText(String.valueOf(stateModel.counter));
    }

    @Override
    public ParcelableState onCreateStateModel() {
        return new ParcelableState();
    }

    @Override
    public ReampPresenter<ParcelableState> onCreatePresenter() {
        return new ParcelPresenter();
    }
}

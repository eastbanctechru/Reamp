package example.reamp.share;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import etr.android.reamp.mvp.MvpAppCompatActivity;
import etr.android.reamp.mvp.MvpPresenter;
import example.reamp.R;

public class ParticularActivity extends MvpAppCompatActivity<SharedPresenter, SharedState> {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_particular);
        textView = (TextView) findViewById(R.id.text);
        findViewById(R.id.open_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ParticularActivity.this, ParticularActivity.class));
            }
        });
    }

    @Override
    public void onStateChanged(SharedState stateModel) {
        textView.setText(String.valueOf(stateModel.times));
    }

    @Override
    public SharedState onCreateStateModel() {
        return new SharedState();
    }

    @Override
    public MvpPresenter<SharedState> onCreatePresenter() {
        return new SharedPresenter();
    }

    @Override
    public String getMvpId() {
        return "PERSISTENT_MVP_ID";
    }


    public static Intent getRootIntent(Context context) {
        return new Intent(context, ParticularActivity.class).putExtra("EXTRA_ROOT", true);
    }

    public boolean isRoot(){
        return getIntent().getBooleanExtra("EXTRA_ROOT", false);
    }
}

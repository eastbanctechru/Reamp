package example.reamp.subject;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import etr.android.reamp.mvp.ReampAppCompatActivity;
import example.reamp.R;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func2;

public class SubjectActivity extends ReampAppCompatActivity<SubjectPresenter, SubjectStateModel> {

    private Button button;
    private Subscription subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);
        button = (Button) findViewById(R.id.button);
        EditText nameInput = (EditText) findViewById(R.id.name);
        if (nameInput != null) {
            nameInput.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    getPresenter().onNameChanged(s.toString());
                }
            });
        }

        EditText passInput = (EditText) findViewById(R.id.password);
        if (passInput != null) {
            passInput.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    getPresenter().onPassChanged(s.toString());
                }
            });
        }
    }

    @Override
    public SubjectStateModel onCreateStateModel() {
        return new SubjectStateModel();
    }

    @Override
    public SubjectPresenter onCreatePresenter() {
        return new SubjectPresenter();
    }

    @Override
    public void onStateChanged(SubjectStateModel stateModel) {
        subscription = Observable.combineLatest(stateModel.nameSubject, stateModel.passSubject, new Func2<String, String, Boolean>() {
            @Override
            public Boolean call(String name, String pass) {
                return !TextUtils.isEmpty(name) && !TextUtils.isEmpty(pass);
            }
        }).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean valid) {
                button.setEnabled(valid);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (subscription != null) {
            subscription.unsubscribe();
        }
    }
}

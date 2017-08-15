package example.reamp.login;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import etr.android.reamp.mvp.ReampAppCompatActivity;
import etr.android.reamp.mvp.ReampPresenter;
import example.reamp.R;

public class LoginActivity extends ReampAppCompatActivity<LoginPresenter, LoginState> {

    private EditText loginInput;
    private EditText passwordInput;
    private View loginActionView;
    private View progressView;
    public LoginService loginService = new LoginService();
    private View successView;
    private View errorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginInput = (EditText) findViewById(R.id.login);
        passwordInput = (EditText) findViewById(R.id.password);
        loginActionView = findViewById(R.id.login_action);
        progressView = findViewById(R.id.progress);
        successView = findViewById(R.id.login_success);
        errorView = findViewById(R.id.login_error);

        loginActionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().login();
            }
        });

        loginInput.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                getPresenter().loginChanged(s.toString());
            }
        });

        passwordInput.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                getPresenter().passwordChanged(s.toString());
            }
        });
    }

    @Override
    public LoginState onCreateStateModel() {
        return new LoginState();
    }

    @Override
    public ReampPresenter<LoginState> onCreatePresenter() {
        return new LoginPresenter(loginService);
    }

    @Override
    public void onStateChanged(LoginState stateModel) {
        progressView.setVisibility(stateModel.showProgress() ? View.VISIBLE : View.GONE);
        loginActionView.setEnabled(stateModel.isLoginActionEnabled());
        successView.setVisibility(stateModel.showSuccessLogin() ? View.VISIBLE : View.GONE);
        errorView.setVisibility(stateModel.showFailedLogin() ? View.VISIBLE : View.GONE);
        if (stateModel.errorAction().hasAction()) {
            showError(stateModel.errorAction().get());
        }
    }

    private void showError(String message) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .show();
    }

    static class SimpleTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}

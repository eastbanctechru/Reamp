package example.reamp.login;

import android.text.TextUtils;

import etr.android.reamp.mvp.Action;
import etr.android.reamp.mvp.SerializableStateModel;

public class LoginState extends SerializableStateModel {
    private Boolean loggedIn;
    private boolean showProgress;
    private Action<String> errorAction = new Action<>();
    private String login;
    private String password;

    public void setLoggedIn(Boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public void setShowProgress(boolean showProgress) {
        this.showProgress = showProgress;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void errorAction(String msg) {
        errorAction.set(msg);
    }

    public boolean isLoginActionEnabled() {
        return !showProgress
                && (loggedIn == null || !loggedIn)
                && !TextUtils.isEmpty(login)
                && !TextUtils.isEmpty(password);
    }

    public boolean showSuccessLogin() {
        return loggedIn != null && loggedIn;
    }

    public boolean showFailedLogin() {
        return loggedIn != null && !loggedIn;
    }

    public boolean showProgress() {
        return showProgress;
    }

    public Action<String> errorAction() {
        return errorAction;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}

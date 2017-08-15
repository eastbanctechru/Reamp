package example.reamp.login;

import android.os.AsyncTask;

import etr.android.reamp.mvp.ReampPresenter;

public class LoginPresenter extends ReampPresenter<LoginState> {

    private final LoginService loginService;

    public LoginPresenter(LoginService loginService) {
        this.loginService = loginService;
    }

    @Override
    public void onPresenterCreated() {
        super.onPresenterCreated();
        getStateModel().setLogin("");
        getStateModel().setPassword("");
        getStateModel().setLoggedIn(null);
        getStateModel().setShowProgress(false);
        sendStateModel();
    }

    public void login() {

        getStateModel().setShowProgress(true);
        getStateModel().setLoggedIn(null);
        sendStateModel();

        performLogin();
    }

    void performLogin() {
        LoginTask loginTask = new LoginTask(getStateModel().getLogin(), getStateModel().getPassword()) {
            @Override
            protected void onPostExecute(Boolean result) {
                super.onPostExecute(result);
                processLoginResult(result, error);
            }
        };

        loginTask.execute();
    }

    void processLoginResult(Boolean result, Throwable error) {
        if (error != null) {
            getStateModel().errorAction(error.getMessage());
        }
        getStateModel().setLoggedIn(result);
        getStateModel().setShowProgress(false);
        sendStateModel();
    }

    public void loginChanged(String login) {
        getStateModel().setLogin(login);
        sendStateModel();
    }

    public void passwordChanged(String password) {
        getStateModel().setPassword(password);
        sendStateModel();
    }

    private class LoginTask extends AsyncTask<Void, Void, Boolean> {
        final String login;
        final String password;
        Throwable error;

        LoginTask(String login, String password) {
            this.login = login;
            this.password = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                return loginService.login(login, password);
            } catch (Throwable e) {
                error = e;
                return null;
            }
        }
    }
}

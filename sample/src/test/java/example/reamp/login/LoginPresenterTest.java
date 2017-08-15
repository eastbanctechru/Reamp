package example.reamp.login;

import org.junit.Assert;
import org.junit.Test;

import example.reamp.TestView;

public class LoginPresenterTest {

    @Test
    public void loginSuccess() throws Exception {
        TestLoginView view = new TestLoginView();
        TestLoginPresenter presenter = view.getPresenter();

        presenter.loginChanged("admin");
        presenter.passwordChanged("password");

        presenter.login();
        presenter.processLoginResult(presenter.result, null);

        Assert.assertTrue(presenter.getStateModel().showSuccessLogin());
        Assert.assertFalse(presenter.getStateModel().showFailedLogin());
    }

    @Test
    public void loginFail() throws Exception {
        TestLoginView view = new TestLoginView();
        TestLoginPresenter presenter = view.getPresenter();

        presenter.loginChanged("nimda");
        presenter.passwordChanged("password");

        presenter.login();
        presenter.processLoginResult(presenter.result, null);

        Assert.assertTrue(presenter.getStateModel().showFailedLogin());
        Assert.assertFalse(presenter.getStateModel().showSuccessLogin());
    }

    @Test
    public void stateChecks() throws Exception {
        TestLoginView view = new TestLoginView();
        TestLoginPresenter presenter = view.getPresenter();

        LoginState state = presenter.getStateModel();
        Assert.assertEquals(state.getLogin(), "");
        Assert.assertEquals(state.getPassword(), "");
        Assert.assertFalse(state.showProgress());
        Assert.assertFalse(state.isLoginActionEnabled());
        Assert.assertFalse(state.showFailedLogin());
        Assert.assertFalse(state.showSuccessLogin());

        presenter.loginChanged("123");
        Assert.assertFalse(state.isLoginActionEnabled());
        presenter.passwordChanged("123");
        Assert.assertTrue(state.isLoginActionEnabled());

        presenter.login();
        Assert.assertTrue(state.showProgress());
        presenter.processLoginResult(false, null);

        Assert.assertFalse(state.showProgress());
        Assert.assertFalse(state.showSuccessLogin());
        Assert.assertTrue(state.showFailedLogin());
        Assert.assertTrue(state.isLoginActionEnabled());

        presenter.loginChanged("admin");
        Assert.assertTrue(state.isLoginActionEnabled());

        presenter.login();
        Assert.assertTrue(state.showProgress());
        presenter.processLoginResult(true, null);

        Assert.assertFalse(state.showProgress());
        Assert.assertTrue(state.showSuccessLogin());
        Assert.assertFalse(state.showFailedLogin());
        Assert.assertFalse(state.isLoginActionEnabled());
    }

    private class TestLoginService extends LoginService {
        @Override
        public boolean login(String login, String password) {
            return "admin".equals(login);
        }
    }

    private class TestLoginView extends TestView<LoginState> {

        @Override
        public LoginState onCreateStateModel() {
            return new LoginState();
        }

        @Override
        public TestLoginPresenter onCreatePresenter() {
            final TestLoginService loginService = new TestLoginService();
            return new TestLoginPresenter(loginService);
        }

        @Override
        public TestLoginPresenter getPresenter() {
            return (TestLoginPresenter) super.getPresenter();
        }
    }

    private class TestLoginPresenter extends LoginPresenter {

        private final LoginService loginService;
        private boolean result;

        TestLoginPresenter(LoginService loginService) {
            super(loginService);
            this.loginService = loginService;
        }

        @Override
        void performLogin() {
            result = loginService.login(getStateModel().getLogin(), getStateModel().getPassword());
        }
    }
}
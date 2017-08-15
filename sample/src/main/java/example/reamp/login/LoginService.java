package example.reamp.login;

class LoginService {

    public boolean login(String login, String password) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if ("error".equals(login)) {
            throw new RuntimeException("A server error");
        }
        return "admin".equals(login);
    }
}

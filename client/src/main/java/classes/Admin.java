package classes;

import java.io.Serializable;

public class Admin extends Person implements Serializable {
    private static final long serialVersionUID = 6529685098267757690L;

    public Admin () {}

    public Admin(String login, String password) {
        this.login = login;
        this.password = password;
    }
}

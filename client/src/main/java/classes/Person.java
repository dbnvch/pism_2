package classes;

import java.io.Serializable;

public class Person implements Persons, Serializable {
    private static final long serialVersionUID = 6529685098267757690L;
    public String login;
    public String password;
    public int id = 0;
    public String type = "";


    public void setType(String type) {
        this.type = type;
    }

    public Person(String log, String pass){
        this.login = log;
        this.password = pass;
    }

    public Person(){}

    @Override
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

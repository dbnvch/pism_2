package classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Person implements Persons, Serializable {
    private static final long serialVersionUID = 6529685098267757690L;
    private List<Observer> observers = new ArrayList<Observer>();

    public String login;
    public String password;
    public int id = 0;
    public String type = "";

    public Person(String log, String pass){
        this.login = log;
        this.password = pass;
    }

    public String getType() {
        return type;
    }

    public Person(){}

    @Override
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
        notifyAllObservers();
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyAllObservers();
    }

    public void notifyAllObservers(){
        for(Observer observer : observers){
            observer.update();
        }
    }

    public void attach(Observer observer){
        observers.add(observer);
    }
}

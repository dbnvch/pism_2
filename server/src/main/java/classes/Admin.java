package classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Admin extends Person  {
    private List<Observer> observers = new ArrayList<Observer>();

    public Admin(){
        super();
    }

    public Admin(String log, String pass){
        super(log, pass);
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

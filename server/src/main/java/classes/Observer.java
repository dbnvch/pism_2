package classes;

public abstract class Observer {
    protected Master master;
    protected Service service;
    protected Client client;
    protected Admin admin;
    protected User user;

    public abstract void update();
}

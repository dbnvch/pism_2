package classes;

public class BinaryObserver extends Observer  {
    public BinaryObserver (Master master){
        this.master = master;
        this.master.attach( this);
    }

    public BinaryObserver (Service service){
        this.service = service;
        this.service.attach(this);
    }

    public BinaryObserver (Client client){
        this.client = client;
        this.client.attach(this);
    }

    public BinaryObserver (Admin admin){
        this.admin = admin;
        this.admin.attach(this);
    }

    public BinaryObserver (User user){
        this.user = user;
        this.user.attach(this);
    }

    @Override
    public void update(){
        System.out.println("Изменена информация");
    }
}

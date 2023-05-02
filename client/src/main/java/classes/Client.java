package classes;

import java.io.Serializable;

public class Client implements Salon, Serializable {
    private static final long serialVersionUID = 6529685098267757690L;
    private int id = 0;
    private String surname;
    private String name;
    private String service;
    private String masterName;
    private String date;
    private String time;
    private  String phone;
    private String status;
    private String type = "";
    private int idService = 0;
    private String log;

    public Client() {}
    public Client(String surname, String type) {
        this.type= type;
        this.surname=surname;}

//    public Client(String status) {
//        this.status= status;
//        }

    public Client(String service, String masterName,String date,String time,String status) {
        this.service = service;
        this.masterName=masterName;
        this.date = date;
        this.time=time;
        this.status=status;
    }
    public Client(int id, String firstname,String name, String service, String masterName,String date,String time, String phone) {
        this.id = id;
        this.surname=firstname;
       // this.firstname=firstname;
        this.name = name;
        this.service = service;
        this.masterName=masterName;
        this.date = date;
        this.time=time;
        this.phone = phone;
    }

//    public Client(String firstname,String name, String service, String masterName,String date, String time, String phone) {
//        this.surname=firstname;
//        this.name = name;
//        this.service = service;
//        this.masterName=masterName;
//        this.date = date;
//        this.time=time;
//        this.phone = phone;
//        //this.status=status;
//    }
    public Client(String firstname,String name, String service,String masterName, String date, String time,String phone, String log) {
        this.surname=firstname;
        this.name = name;
        this.service = service;
        this.masterName=masterName;
        this.date = date;
        this.time=time;
        this.phone = phone;
        this.log = log;
        //this.status="Актульно";
    }
    public Client(String firstname,String name, String service,String masterName, String date, String time,String phone, String log,String status) {
        this.surname=firstname;
        this.name = name;
        this.service = service;
        this.masterName=masterName;
        this.date = date;
        this.time=time;
        this.phone = phone;
        this.log = log;
        this.status=status;
    }

    public int getId() {
        return id;
    }

 public String getFirstName() {
        return surname;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public String getService() {
        return service;
    }

    public String getMasterName() {
        return masterName;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getPhone() {
        return phone;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Client(int id, String type) {
        this.id = id;
        this.type = type;
    }

    public Client(String type) {
        this.type = type;
    }

    public Client(int id,String surname, String name, String service,String masterName, String date, String time, String  phone, String type) {
        this.id = id;
        this.surname=surname;
        //this.firstname=firstname;
        this.name = name;
        this.service = service;
        this.masterName=masterName;
        this.date = date;
        this.time=time;
        this.phone = phone;
        this.type = type;
    }

    public Client(String surname, String name, String service,String masterName, String date, String time, String type) {
        this.surname=surname;
        this.name = name;
        this.service = service;
        this.masterName=masterName;
        this.date = date;
        this.time=time;
        this.type = type;
    }


}

package classes;

import java.io.Serializable;

public class Shedule  implements Salon, Serializable {
    private static final long serialVersionUID = 6529685098267757690L;
    private int id = 0;
    private String master;
    private String client;
    private String service;
    private String date;
    private String time;
    private String field;
    private String type = "";
    private int idService = 0;

    public Shedule() {}

    public Shedule(int id, String service,String master, String date,String time) {
        this.id = id;
        this.service = service;
        this.master=master;
        this.date = date;
        this.time=time;
    }

    public Shedule( String service,String master, String date,String time) {
        this.service = service;
        this.master=master;
        this.date = date;
        this.time=time;
    }

    public Shedule(int id, String service,String master, String date,String time, String client) {
        this.id = id;
        this.service = service;
        this.master=master;
        this.date = date;
        this.time=time;
        this.client=client;
    }
    public Shedule( String service,String master, String date,String time, String client,String type) {
        this.service = service;
        this.master=master;
        this.date = date;
        this.time=time;
        this.client=client;
        this.type=type;
    }
    public Shedule(String service,String master, String date,String time, String client) {
        this.service = service;
        this.master=master;
        this.date = date;
        this.time=time;
        this.client=client;
    }
//    public Shedule(int id,String service,String master, String date,String time, String type) {
//        this.id = id;
//        this.service = service;
//        this.master=master;
//        this.date = date;
//        this.time=time;
//        this.client=null;
//        this.type=type;
//    }

    public int getId() {
        return id;
    }

    public String getField() {
        return master;
    }
    public String getMaster() {
        return master;
    }

    public String getService() {
        return service;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setService(String service) {
        this.service= service;
    }

    public void setMaster(String master) {
        this.master= master;
    }

    public void setDate(String date) {
        this.date= date;
    }

    public void setTime(String time) {
        this.time= time;
    }

    public Shedule(int id, String type) {
        this.id = id;
        this.type = type;
    }

    public Shedule(String type) {
        this.type = type;
    }

    public Shedule(int id, String service,String master, String date,String time, String client, String type) {
        this.id = id;
        this.master=master;
        this.client = client;
        this.service = service;
        this.date = date;
        this.time=time;
        this.type = type;
    }
//    public Shedule(int id, String service,String master, String date,String type, String time) {
//        this.id = id;
//        this.master=master;
//        this.service = service;
//        this.date = date;
//        this.time=time;
//        this.type = type;
//    }
}

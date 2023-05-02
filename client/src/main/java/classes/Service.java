package classes;

import java.io.Serializable;

public class Service implements Salon, Serializable {
    private static final long serialVersionUID = 6529685098267757690L;
    private String name;
    private String price;
  //  private String time;
    private String field;
    private int id = 0;
    private String type = "";

    public Service(){}

    public Service(String name, String price,  String field) {
        this.name = name;
        this.price = price;
      //  this.time = time;
        this.field = field;
    }

    public Service(String type) {
        this.type = type;
    }

    public String getField() {
        return field;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }
//
//    public String getTime() {
//        return time;
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Service(int id, String name,  String price, String field, String type) {
        this.name = name;
        this.price = price;
        //this.time = time;
        this.id = id;
        this.field = field;
        this.type = type;
    }

    public Service(int id, String type) {
        this.id = id;
        this.type = type;
    }
}

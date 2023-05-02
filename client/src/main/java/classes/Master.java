package classes;

import java.io.Serializable;

public class Master implements Salon, Serializable {
    private static final long serialVersionUID = 6529685098267757690L;
    private int id = 0;
    private String name;
    private String surname;
    private String experience;
    private String salary;
    private String type = "";
    private String field;

    public Master() {}

    public Master(String name, String surname, String experience, String salary, String field) {
        this.name = name;
        this.surname = surname;
        this.experience = experience;
        this.salary = salary;
        this.id = id;
        this.field = field;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getExperience() {
        return experience;
    }

    public String getSalary() {
        return salary;
    }

    public int getId() {
        return id;
    }

    public String getField() {
        return field;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Master(int id, String name, String surname, String experience, String salary,  String field, String type ) {
        this.name = name;
        this.surname = surname;
        this.experience = experience;
        this.salary = salary;
        this.id = id;
        this.type = type;
        this.field = field;
    }

    public Master(int id, String type) {
        this.id = id;
        this.type = type;
    }

    public Master(String type) {
        this.type = type;
    }
}

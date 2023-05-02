package classes;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Master implements Salon, Serializable {
    private List<Observer> observers = new ArrayList<Observer>();
    private static final long serialVersionUID = 6529685098267757690L;
    private String name;
    private String surname;
    private String experience;
   // private int salary;
    private String salary;
    private int id = 0;
    private String type = "";
    private String field;

    public Master() {}

    public Master(int id, String name, String surname, String experience, String salary, String field) {
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

    public String getType() {
        return type;
    }

    public String getField() {
        return field;
    }

    public List<Object> getAllMaster(Connection con, Master master){
        List<Object> list = new ArrayList();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from Master;");
            while (rs.next()) {
                list.add(new Master(rs.getInt("IdMaster"), rs.getString("Name"),
                        rs.getString("Surname"), rs.getString("Experience"),
                        rs.getString("Salary"), rs.getString("Field")));
            }
            rs.close();
            stmt.close();

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Object> editMaster(Connection con, Master master) {
        List<Object> list = new ArrayList();
        try{
            PreparedStatement st = con.prepareStatement("DELETE FROM Master WHERE IdMaster = " + master.getId() + ";");
            st.executeUpdate();
            st.close();
            Statement stmt = con.createStatement();
            String str_update = "insert into Master(IdMaster, Name, Surname, Experience, Salary, Field) values (" +
                    master.getId() + ",'" + master.getName() + "', '" + master.getSurname() + "', '" +
                    master.getExperience() + "', '" + master.getSalary()+ "', '" + master.getField() + "')";
            stmt.executeUpdate(str_update);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        list.add("true");
        return list;
    }

    public List<Object> removeMaster(Connection con, Master master) {
        try {
            PreparedStatement st = con.prepareStatement("DELETE FROM Master WHERE IdMaster = " + master.getId() + ";");
            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        List<Object> list = new ArrayList();
        list.add("true");
        return list;
    }

    public List<Object> addMaster(Connection con, Master master) {

        List<Object> list = new ArrayList();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(IdMaster) AS masterCount from master order by Surname,name,field,Experience,Salary;");
            int count = 1;
            while (rs.next()) {
               count = rs.getInt("masterCount");
            }
            String str_update = "insert into Master(IdMaster, Name, Surname, Experience, Salary, Field) values (" +
                    (count+1) + ",'" + master.getName() + "', '" + master.getSurname() + "', '" +
                    master.getExperience() + "', '" + master.getSalary()+ "', '" + master.getField() + "')";
            stmt.executeUpdate(str_update);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        list.add("true");
        return list;
    }

    public List<Object> getFieldService(Connection con){
        List<Object> list = new ArrayList();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select distinct Field from Service;");
            while (rs.next()) {
                list.add(rs.getString("Field"));
            }
            rs.close();
            stmt.close();

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public List<Object> getMaster(Connection con){
        List<Object> list = new ArrayList();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from Master;");
            while (rs.next()) {
                list.add(rs.getString("Surname"));
            }
            rs.close();
            stmt.close();

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
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

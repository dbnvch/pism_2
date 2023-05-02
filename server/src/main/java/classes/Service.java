package classes;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Service implements Salon, Serializable {

    private List<Observer> observers = new ArrayList<Observer>();
    private static final long serialVersionUID = 6529685098267757690L;
    private String name;
    private String price;
  //  private String time;
    private String field;
    private int id = 0;
    private String type = "";
    private int idMaster = 0;


    public Service(){}

    public Service(String name) {
        this.name = name;
    }

    public Service(int id, String name, String price, String field) {
        this.name = name;
        this.price = price;
        //this.time = time;
        this.id = id;
        this.field = field;
    }

    public Service(int id, String name, String price, String field, int idMaster) {
        this.name = name;
        this.price = price;
     //   this.time = time;
        this.field = field;
        this.id = id;
        this.idMaster = idMaster;
    }

    public int getId() {
        return id;
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

//    public String getTime() {
//        return time;
//    }

    public String getType() {
        return type;
    }

    public int getIdMaster() {
        return idMaster;
    }

    public void setIdMaster(int idMaster) {
        this.idMaster = idMaster;
    }

    public List<Object> getAllService(Connection con, Service service){
        List<Object> list = new ArrayList();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from Service order by Field,Name,Price;");
            while (rs.next()) {
                list.add(new Service(rs.getInt("IdService"), rs.getString("Name"),
                        rs.getString("Price"),
                        rs.getString("Field")));
            }
            rs.close();
            stmt.close();

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Object> editService(Connection con, Service service) {
        List<Object> list = new ArrayList();
        try{
            PreparedStatement st = con.prepareStatement("DELETE FROM Service WHERE IdService = " + service.getId() + ";");
            st.executeUpdate();
            st.close();
            Statement stmt = con.createStatement();
            String str_update = "insert into Service(IdService, Name, Price,  Field) values (" +
                    service.getId() + ",'" + service.getName() + "', '" + service.getPrice() + "', '" + service.getField()+ "')";
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

    public List<Object> removeService(Connection con, Service service) {
        String list = "";
        List<Object> list1 = new ArrayList();
        List<Object> serv = new ArrayList();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from Service where IdService = " + service.getId() + ";");
            while (rs.next()) {
                list = (rs.getString("Name"));
            }
            rs.close();
            stmt.close();

            stmt = con.createStatement();
            rs = stmt.executeQuery("select * from Client;");
            while (rs.next()) {
                serv.add((rs.getString("Service")));
            }
            rs.close();
            stmt.close();

            for (int i = 0; i < serv.size(); i++) {
                if ((serv.get(i)).equals(list)) {
                    list1.add("false");
                    return list1;
                }
            }
            PreparedStatement st = con.prepareStatement("DELETE FROM Service WHERE IdService = " + service.getId() + ";");
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
        list1.add("true");
        return list1;
    }

    public List<Object> addService(Connection con, Service service) throws SQLException {

        List<Object> list = new ArrayList();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(IdService) AS serviceCount from Service;");
            int count = 1;
            while (rs.next()) {
                count = rs.getInt("serviceCount");
            }
            rs = stmt.executeQuery("select * from Master;");
            while (rs.next()) {
                if(service.getField().equals(rs.getString("Field"))){
                    service.setIdMaster(rs.getInt("IdMaster"));
                    break;
                }
            }
            String str_update = "insert into Service(IdService, Name, Price,  Field/*, IdMaster*/) values (" +
                    (count + 1) + ",'" + service.getName() + "', '" + service.getPrice() + "', '"  +
                    service.getField()/*+ "', " + service.getIdMaster()*/+"')";
            stmt.executeUpdate(str_update);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            con.close();
        }
        list.add("true");
        return list;
    }

    public List<Object> getServiceName(Connection con){
        List<Object> list = new ArrayList();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from Service;");
            while (rs.next()) {
                list.add(rs.getString("Name"));
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

package classes;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class User extends Person implements Serializable {
    private static final long serialVersionUID = 6529685098267757690L;
    private int idAdmin = 0;
    private List<Observer> observers = new ArrayList<Observer>();
    String surname;
    String name;
    String phone;

    public User(String log, String pass) {
        super(log, pass);
    }

    public User() {
    }

    public User(String surname, String name, String phone) {
        this.surname = surname;
        this.name = name;
        this.phone = phone;
    }

    public User(String surname, String name, String phone, String login, String password) {
        this.surname = surname;
        this.name = name;
        this.phone = phone;
        this.login = login;
        this.password = password;
    }

    public User(String surname, String name, String phone, String login, String password, String type) {
        this.surname = surname;
        this.name = name;
        this.phone = phone;
        this.login = login;
        this.password = password;
        this.type = type;
    }

    public void notifyAllObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }

    public void setIdAdmin(int idAdmin) {
        this.idAdmin = idAdmin;
    }

    public int getIdAdmin() {
        return idAdmin;
    }

    public void attach(Observer observer) {
        observers.add(observer);
    }

    public String getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public List<Object> addUser(Connection con, User client) {

        List<Object> list = new ArrayList();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(IdUser) AS userCount from User;");
            System.out.println("start add");
            int count = 1;
            while (rs.next()) {
                count = rs.getInt("userCount");
            }

            String str_update;
            if (client.getLogin() != null) {
                rs = stmt.executeQuery("select * from User;");
                while (rs.next()) {
                    if (client.getLogin().equals(rs.getString("Login"))) {
                        System.out.println("444" + client.getLogin());
                        // client.setIdUser(rs.getInt("Id"));
                        System.out.println("55");
                        break;
                    }
                }
            }

            str_update = "insert into User(IdUser, Surname,Name, Phone, Login,Password) values (" +
                    (count + 1) + ",'" + client.getSurname() + "', '" + client.getName() + "', '" + client.getPhone() + "', '" +
                    client.getLogin() + "', '" +
                    client.getPassword() + "')";
            System.out.println("insert client");
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

    public List<Object> getUserInformation(Connection con, User user) {
        List<Object> list = new ArrayList();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from User where Login='" + user.getSurname() + "';");
            System.out.println(user.getSurname());
            while (rs.next()) {
                list.add(new User(rs.getString("Surname"), rs.getString("Name"),
                        rs.getString("Phone")));
                System.out.println(list.size());
            }
            System.out.println("Передача данных о юзере");
            rs.close();
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

}

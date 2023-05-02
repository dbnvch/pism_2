package classes;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.sql.*;
import java.util.*;
import java.util.function.Predicate;

public class Client implements Salon, Serializable {
    private static final long serialVersionUID = 6529685098267757690L;

    private static final List<DiscountInfo> NUMBER_OF_VISITORS_FOR_DISCOUNT = Arrays.asList(
            new DiscountInfo(0, 5,0),
            new DiscountInfo(6, 10,1),
            new DiscountInfo(11, 25,2),
            new DiscountInfo(26, 50,5));

    private List<Observer> observers = new ArrayList<Observer>();

    private int id = 0;
    //  private String firstname;
    private String surname;
    private String name;
    private String service;
    private String masterName;
    private String date;
    private String time;
    private String phone;
    private String type = "";
    private int idService = 0;
    private int idUser = 0;
    private String log = null;

    public Client(String surname, String name, String service, String masterName, String date, String time, String type) {
        this.surname = surname;
        this.name = name;
        this.service = service;
        this.masterName = masterName;
        this.date = date;
        this.time = time;
        this.type = type;
    }

    public Client(int idClient, String firstname, String name, String service, String masterName, String date, String phone) {
    }

    public Client(int id, String firstname, String name, String service, String masterName, String date, String time, String phone) {
        this.id = id;
        this.surname = firstname;
        this.name = name;
        this.service = service;
        this.masterName = masterName;
        this.date = date;
        this.time = time;
        this.phone = phone;
    }

    public Client(String firstname, String name, String service, String masterName, String date, String time, String phone, String log) {
        this.surname = firstname;
        this.name = name;
        this.service = service;
        this.masterName = masterName;
        this.date = date;
        this.time = time;
        this.phone = phone;
        this.log = log;
    }

    public Client() {

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

    public String getFirstname() {
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

    public Client(String surname, String type) {
        this.type = type;
        this.surname = surname;
    }

    public Client(String service, String masterName, String date, String time) {
        this.service = service;
        this.masterName = masterName;
        this.date = date;
        this.time = time;
    }

    public Client(String type) {
        this.type = type;
    }

    public Client(int id, String firstname, String name, String service, String masterName, String date, String time, String phone, String type) {
        this.id = id;
        this.surname = firstname;
        this.name = name;
        this.service = service;
        this.masterName = masterName;
        this.date = date;
        this.time = time;
        this.phone = phone;
        this.type = type;
    }

    public List<Object> getAllClient(Connection con, Client client) {
        List<Object> list = new ArrayList();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from Client order by date,time,Service,masterName;");
            while (rs.next()) {
                list.add(new Client(rs.getInt("IdClient"), rs.getString("Surname"), rs.getString("Name"),
                        rs.getString("Service"), rs.getString("MasterName"), rs.getString("date"), rs.getString("time"),
                        rs.getString("Phone")));
            }
            rs.close();
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Object> clientOrders(Connection con, Client client) {
        List<Object> list = new ArrayList();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from Client where Client.surname=(select User.surname  from User where User.login='" + client.getSurname() + "');");

            while (rs.next()) {
                list.add(new Client(rs.getString("Service"), rs.getString("MasterName"), rs.getString("date"),
                        rs.getString("time")));
            }
            rs.close();
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Object> editClient(Connection con, Client client) {
        List<Object> list = new ArrayList();
        try {
            PreparedStatement st = con.prepareStatement("DELETE FROM Client WHERE IdClient = " + client.getId() + ";");
            st.executeUpdate();
            st.close();
            Statement stmt = con.createStatement();
            String str_update = "insert into Client(IdClient,Surname,Name, Service, MasterName,Date,Time, Phone) values (" +
                    getId() + ",'" + client.getFirstname() + "', '" + client.getName() + "', '" + client.getService() + "', '" +
                    client.getMasterName() + "', '" +
                    client.getDate() + "', '" + client.getTime() + "', '" + client.getPhone() + "')";
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

    public List<Object> removeClient(Connection con, Client client) {
        try {
            PreparedStatement st = con.prepareStatement("DELETE FROM Client WHERE IdClient = " + client.getId() + ";");
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

    public List<Object> addClient(Connection con, Client client) {
        List<Object> list = new ArrayList();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(IdClient) AS clientCount  from Client;");
            System.out.println("start add");
            int count = 1;
            while (rs.next()) {
                count = rs.getInt("clientCount");
            }
            System.out.println("start add2");
            rs = stmt.executeQuery("select * from Service;");
            while (rs.next()) {
                System.out.println(client.getService());
                if (client.getService().equals(rs.getString("Name"))) {
                    client.setIdService(rs.getInt("IdService"));
                    break;
                }
            }
            System.out.println(client.getDate());
            String str_update;
            if (client.getLog() != null) {
                System.out.println("111");
                rs = stmt.executeQuery("select * from User;");
                System.out.println("222");
                while (rs.next()) {
                    System.out.println("333");
                    if (client.getLog().equals(rs.getString("Login"))) {
                        System.out.println("444" + client.getLog());
                        client.setIdUser(rs.getInt("Id"));
                        System.out.println("55");
                        break;
                    }
                }
            } else {
                client.setIdUser(4);
            }
            System.out.println("phone " + client.getType());
            str_update = "insert into Client(IdClient, Surname,Name, Service, MasterName,Date,Time, Phone/*, IdService, IdUser*/) values (" +
                    (count + 1) + ",'" + client.getFirstname() + "', '" + client.getName() + "', '" + client.getService() + "', '" +
                    client.getMasterName() + "', '" +
                    client.getDate() + "', '" + client.getTime() + "', '" + client.getType() + "')";
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

    public String getLog() {
        return log;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }


    public int getIdService() {
        return idService;
    }

    public void setIdService(int idService) {
        this.idService = idService;
    }

    public List<Object> getReport(Connection con) {
        List<Client> information = new ArrayList();

        try (FileWriter writer = new FileWriter("report.doc", false)) {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from Client;");
            while (rs.next()) {
                information.add(new Client(rs.getInt("IdClient"), rs.getString("Surname"), rs.getString("Name"),
                        rs.getString("Service"), rs.getString("MasterName"), rs.getString("date"), rs.getString("time"),
                        rs.getString("Phone")));
            }
            rs.close();
            stmt.close();
            String text = "~ ОТЧЕТ ~" + '\n';
            text += "Клиенты:" + '\n';
            for (int i = 0; i < information.size(); i++) {
                text += " | " + information.get(i).getId() + " | " +
                        information.get(i).getName() + " | " +
                        information.get(i).getService() + " | " +
                        information.get(i).getPhone() + " | " +
                        information.get(i).getDate() + " | " + '\n';
            }
            writer.write(text);
            writer.append('\n');
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        List<Object> list = new ArrayList();
        list.add("true");
        return list;
    }

    public void notifyAllObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }

    public void attach(Observer observer) {
        observers.add(observer);
    }

    public List<Object> getDiscountForClient(Connection con, Client client) throws SQLException {
        final String QUERY = "SELECT COUNT(idClient) FROM Client WHERE Surname = (SELECT Surname FROM User WHERE login = ?)";

        try (PreparedStatement preparedStatement = con.prepareStatement(QUERY)){
            preparedStatement.setString(1, client.getSurname());

             try(ResultSet resultSet = preparedStatement.executeQuery()){
                 if (!resultSet.next()) {//login = surname ??? :D
                     throw new SQLException("Search number of visits for client with login -> ", client.getSurname() + "is not found");
                 }
                 int numberOfVisits = resultSet.getInt(1);

                 return Collections.singletonList(resolveDiscount(numberOfVisits));
             }

        }
    }

    private int resolveDiscount(int numberOfVisits) {
        return NUMBER_OF_VISITORS_FOR_DISCOUNT.stream()
                .filter(pair -> numberOfVisits >= pair.getRight() && numberOfVisits <= pair.getLeft())
                .findFirst()
                .map(DiscountInfo::getDiscount)
                .orElse(10);
    }

}

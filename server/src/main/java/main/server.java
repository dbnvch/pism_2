package main;


import classes.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static main.server.*;


public class server {

    static final int DEFAULT_PORT = 8181;
    static final String ROOT = "root";
    static final String PASSWORD = "1234";
    static final String HOST = "jdbc:mysql://localhost:3306/beauty?serverTimezone=Europe/Moscow&useSSL=false&allowPublicKeyRetrieval=true";

    private static Socket clientDialog;

    public server(Socket client) {
        server.clientDialog = client;
    }

    static ExecutorService executeIt = Executors.newFixedThreadPool(2);
    static ObjectInputStream sois = null;
    static ObjectOutputStream soos = null;

    public static void main(String[] args) {
//
        try (ServerSocket server = new ServerSocket(DEFAULT_PORT);
             BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            while (!server.isClosed()) {
                if (br.ready()) {
                    String serverCommand = br.readLine();
                    if (serverCommand.equalsIgnoreCase("quit")) {
                        System.out.println("Main Server initiate exiting...");
                        server.close();
                        break;
                    }
                }
                Socket client = server.accept();
                try (FileWriter writer = new FileWriter("connect.txt", true)) {
                    String text = "Server port: " + server.getLocalPort() + " IP: " + server.getLocalSocketAddress() + '\n';
                    writer.write(text);
                    writer.append('\n');
                    writer.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                executeIt.execute(new MonoThreadClientHandler(client));
            }
            executeIt.shutdown();
        } catch (IOException e) {
            System.out.print("Номер порта введен неверно");
            e.printStackTrace();
        }
    }//}   // else System.out.print("Номер порта введен неверно");

}

class MonoThreadClientHandler implements Runnable {

    private static Socket clientDialog;
    private static Service s;

    public MonoThreadClientHandler(Socket client) {
        MonoThreadClientHandler.clientDialog = client;
    }

    @Override
    public void run() {
        ObjectOutputStream out = null;
        ObjectInputStream in = null;

        try {
            printInf();

            out = new ObjectOutputStream(clientDialog.getOutputStream());
            in = new ObjectInputStream(clientDialog.getInputStream());
            try (FileWriter writer = new FileWriter("connect.txt", true)) {
                String text = "Client port: " + clientDialog.getPort() + " IP: " + clientDialog.getInetAddress() + '\n';
                writer.write(text);
                writer.append('\n');
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

            while (true) {
                Object clientMassegeRecieved = (Object) in.readObject();
                List<Object> clientMessageAnswer = findClass(clientMassegeRecieved);
                out.writeObject(clientMessageAnswer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
                clientDialog.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void printInf() throws SQLException {

        Connection con = DriverManager.getConnection(HOST, ROOT, PASSWORD);

        List<Client> informationClients = new ArrayList();
        List<Shedule> informationShedule = new ArrayList();
        List<Service> informationServices = new ArrayList();
        List<Master> informationMasters = new ArrayList();
        List<User> informationUsers = new ArrayList();
        List<Admin> informationAdmin = new ArrayList();

        try (FileWriter writer = new FileWriter("print.doc", false)) {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from Client;");
            while (rs.next()) {
                informationClients.add(new Client(rs.getInt("IdClient"), rs.getString("Surname"), rs.getString("Name"),
                        rs.getString("Service"), rs.getString("MasterName"), rs.getString("date"), rs.getString("time"),
                        rs.getString("Phone")));
            }
            rs.close();
            stmt.close();

            stmt = con.createStatement();
            rs = stmt.executeQuery("select * from shedule;");
            while (rs.next()) {
                informationShedule.add(new Shedule(rs.getInt("IdShedule"), rs.getString("ServiceName"),
                        rs.getString("MasterName"), rs.getString("Date"),
                        rs.getString("Time")/*, rs.getInt("IdMaster")*/));
            }
            rs.close();
            stmt.close();

            stmt = con.createStatement();
            rs = stmt.executeQuery("select * from Service;");
            while (rs.next()) {
                informationServices.add(new Service(rs.getInt("IdService"), rs.getString("Name"),
                        rs.getString("Price"),
                        rs.getString("Field")/*, rs.getInt("IdMaster")*/));
            }
            rs.close();
            stmt.close();

            stmt = con.createStatement();
            rs = stmt.executeQuery("select * from Master;");
            while (rs.next()) {
                informationMasters.add(new Master(rs.getInt("idMaster"), rs.getString("Name"),
                        rs.getString("Surname"), rs.getString("Experience"),
                        rs.getString("Salary"), rs.getString("Field")));
            }
            rs.close();
            stmt.close();

            stmt = con.createStatement();
            rs = stmt.executeQuery("select * from Admin;");
            while (rs.next()) {
                informationAdmin.add(new Admin(rs.getString("Login"),
                        rs.getString("Password")));
            }
            rs.close();
            stmt.close();

            stmt = con.createStatement();
            rs = stmt.executeQuery("select * from User;");
            while (rs.next()) {
                informationUsers.add(new User(rs.getString("Login"),
                        rs.getString("Password")));
            }
            rs.close();
            stmt.close();


            String text = "~ ВЫГРУЗКА ДАННЫХ ИЗ БД ~" + '\n';
            text += "Клиенты:" + '\n';
            for (int i = 0; i < informationClients.size(); i++) {
                text += " | " + informationClients.get(i).getId() + " | " +
                        informationClients.get(i).getFirstname() + " | " +
                        informationClients.get(i).getName() + " | " +
                        informationClients.get(i).getService() + " | " +
                        informationClients.get(i).getMasterName() + " | " +
                        informationClients.get(i).getPhone() + " | " +
                        informationClients.get(i).getDate() + " | " + '\n';
            }
            text += "Мастера:" + '\n';
            for (int i = 0; i < informationMasters.size(); i++) {
                text += " | " + informationMasters.get(i).getId() + " | " +
                        informationMasters.get(i).getName() + " | " +
                        informationMasters.get(i).getSurname() + " | " +
                        informationMasters.get(i).getExperience() + " | " +
                        informationMasters.get(i).getSalary() + " | " +
                        informationMasters.get(i).getField() + " | " + '\n';
            }
            text += "Услуги:" + '\n';
            for (int i = 0; i < informationServices.size(); i++) {
                text += " | " + informationServices.get(i).getId() + " | " +
                        informationServices.get(i).getName() + " | " +
                        informationServices.get(i).getPrice() + " | " +
                        // informationServices.get(i).getTime() + " | " +
                        informationServices.get(i).getIdMaster() + " | " + '\n';
            }
            text += "Расписание:" + '\n';
            for (int i = 0; i < informationShedule.size(); i++) {
                text += " | " + informationShedule.get(i).getId() + " | " +
                        informationShedule.get(i).getService() + " | " +
                        informationShedule.get(i).getMaster() + " | " +
                        informationShedule.get(i).getDate() + " | " +
                        informationShedule.get(i).getTime() + " | " + '\n';
            }
            text += "Администратор:" + '\n';
            for (int i = 0; i < informationAdmin.size(); i++) {
                text += " | " + informationAdmin.get(i).getLogin() + " | " +
                        informationAdmin.get(i).getPassword() + " | " + '\n';
            }
            text += "Пользователи:" + '\n';
            for (int i = 0; i < informationUsers.size(); i++) {
                text += " | " + informationUsers.get(i).getLogin() + " | " +
                        informationUsers.get(i).getPassword() + " | " + '\n';
            }
            writer.write(text);
            writer.append('\n');
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Object> findClass(Object obj) {

        if (obj instanceof Service) {
            Service s = new Service();
            Service service = (Service) obj;
            try {
                System.out.println(obj);
                Connection con = DriverManager.getConnection(HOST, ROOT, PASSWORD);
                if (service.getType().equals("allService")) {
                    return s.getAllService(con, service);
                } else if (service.getType().equals("editService")) {
                    return s.editService(con, service);
                } else if (service.getType().equals("removeService")) {
                    return s.removeService(con, service);
                } else {
                    return s.addService(con, service);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (obj instanceof Master) {
            Master s = new Master();
            Master master = (Master) obj;
            try {
                Connection con = DriverManager.getConnection(HOST, ROOT, PASSWORD);

                if (master.getType().equals("allMaster")) {
                    return s.getAllMaster(con, master);
                } else if (master.getType().equals("editMaster")) {
                    return s.editMaster(con, master);
                } else if (master.getType().equals("removeMaster")) {
                    return s.removeMaster(con, master);
                } else if (master.getType().equals("fieldName")) {
                    return s.getFieldService(con);
                } else {
                    return s.addMaster(con, master);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (obj instanceof Client) {
            Service s = new Service();
            Client c = new Client();
            Client client = (Client) obj;
            try {
                Connection con = DriverManager.getConnection(HOST, ROOT, PASSWORD);
                String clientType = client.getType();

                if ("allClient".equalsIgnoreCase(clientType)) {
                    return c.getAllClient(con, client);
                } else if ("editClient".equalsIgnoreCase(clientType)) {
                    return c.editClient(con, client);
//                } else if (client.getType().equals("cancel")) {
//                    return c.cancel(con, client);
                } else if ("removeClient".equalsIgnoreCase(clientType)) {
                    return c.removeClient(con, client);
                } else if ("serviceName".equalsIgnoreCase(clientType)) {
                    return s.getServiceName(con);
                } else if ("clientInformation".equalsIgnoreCase(clientType)) {
                    return c.clientOrders(con, client);
                } else if ("getReport".equalsIgnoreCase(clientType)) {
                    return c.getReport(con);
                } else if ("getDiscountForClient".equalsIgnoreCase(clientType)) {
                    return c.getDiscountForClient(con, client);
                } else {
                    return c.addClient(con, client);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        if (obj instanceof User) {
            System.out.println("USER" + obj);
            User u = new User();
            User user = (User) obj;
            try {
                Connection con = DriverManager.getConnection(HOST, ROOT, PASSWORD);

                if (user.getName().equals("userInformation")) {
                    System.out.println("userInform");
                    return u.getUserInformation(con, user);
//                } else if (client.getType().equals("editClient")) {
//                    return c.editClient(con, client);
//                } else if (client.getType().equals("removeClient")) {
//                    return c.removeClient(con, client);
//                } else if (client.getType().equals("serviceName")) {
//                    return s.getServiceName(con);
//                } else if (client.getType().equals("getReport")) {
//                    return c.getReport(con);
                } else {
                    return u.addUser(con, user);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        if (obj instanceof Shedule) {
            Shedule s = new Shedule();
            Shedule shedule = (Shedule) obj;
            Service service = new Service();
            Master master = new Master();
            try {
                Connection con = DriverManager.getConnection(HOST, ROOT, PASSWORD);
                if (shedule.getType().equals("allService")) {
                    return s.getAllService(con, shedule);
                } else if (shedule.getType().equals("allShedule")) {
                    return s.getAllShedule(con, shedule);

                } else if (shedule.getType().equals("editShedule")) {
                    System.out.println("START EDIT");
                    return s.editShedule(con, shedule);
                } else if (shedule.getType().equals("allServiceName")) {
                    return s.getAllServiceName(con);
                } else if (shedule.getType().equals("removeShedule")) {
                    return s.removeShedule(con, shedule);
                } else if (shedule.getType().equals("booking")) {
                    return shedule.bookingShedule(con, shedule);
                } else if (shedule.getType().equals("serviceName")) {
                    return service.getServiceName(con);
                } else if (shedule.getType().equals("masterFirstName")) {
                    return master.getMaster(con);
                } else if (shedule.getType().equals("date")) {
                    System.out.println("datefind");
                    return shedule.getDateService(con);
                } else {
                    return s.addShedule(con, shedule);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (obj instanceof Person) {
            Person person = (Person) obj;
            List<String> login = new ArrayList();
            List<String> pass = new ArrayList();
            try {
                Connection con = DriverManager.getConnection(HOST, ROOT, PASSWORD);
                if (person.getType().equals("SIGN")) {
                    List<Object> list = new ArrayList();
                    try {
                        User user = new User(person.getLogin(), person.getPassword());
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery("SELECT COUNT(IdUser) as userCount from User;");
                        int count = 0;
                        while (rs.next()) {
                            count = rs.getInt("userCount");
                        }
                        user.setIdAdmin(0);
                        String str_update = "insert into User(/*idUser,*/ Login, Password/*, IdAdmin*/) values ('" + /*(count + 1) + ",'" +*/ user.getLogin() + "', " + "'" + user.getPassword() + /*"', "+ user.getIdAdmin() +*/"')";
                        stmt.executeUpdate(str_update);
                        stmt.close();
                    } finally {
                        con.close();
                    }
                    list.add("true");
                    return list;

                } else {
                    List<Object> list = new ArrayList();
                    try {
                        if (person.getLogin().equals("admin")) {
                            Admin admin = new Admin(person.getLogin(), person.getPassword());
                            System.out.println(admin.getLogin());
                            Statement stmt = con.createStatement();
                            System.out.println(stmt);
                            ResultSet rs = stmt.executeQuery("select * from Admin;");
                            System.out.println(rs);
                            while (rs.next()) {
                                login.add(rs.getString("Login"));
                                pass.add(rs.getString("Password"));
                            }
                            rs.close();
                            stmt.close();
                        } else {
                            User user = new User(person.getLogin(), person.getPassword());

                            Statement stmt = con.createStatement();
                            ResultSet rs = stmt.executeQuery("select * from User;");

                            while (rs.next()) {
                                login.add(rs.getString("Login"));
                                pass.add(rs.getString("Password"));
                            }
                            rs.close();
                            stmt.close();
                        }
                    } finally {
                        con.close();
                    }

                    if (login.contains(person.getLogin()) && pass.contains(person.getPassword())) {
                        System.out.println("Зашел пользователь");
                        list.add("true");
                        return list;
                    } else {
                        System.out.println("Зашел клиент");
                        list.add("false");
                        return list;
                    }


                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        List<Object> list = new ArrayList();
        list.add("false");
        return list;

    }

}




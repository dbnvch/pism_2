package user;

import admin.ControllerAdmin;
import classes.Client;
import classes.Master;
import classes.Service;
import classes.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import sample.*;

public class ControllerUser extends NetworkConnection {
    private ObservableList<Service> DataService = FXCollections.observableArrayList();
    private List<Service> services = new ArrayList<Service>();
    private ObservableList<String> DataServiceName = FXCollections.observableArrayList();
    private List<String> servicesName = new ArrayList<String>();
    private ObservableList<String> DataServiceField = FXCollections.observableArrayList();
    private List<String> servicesField = new ArrayList<String>();
    @FXML
    private Button buttonBack;
    @FXML
    private Button buttonSubmit;
    @FXML
    private Button buttonSortMin;
    @FXML
    private Button buttonSortMax;

    @FXML
    private ComboBox<String> text_service = new ComboBox<String>();
    @FXML
    private ComboBox<String> filter = new ComboBox<String>();

    @FXML
    private TextField text_name;
    @FXML
    private TextField text_date;
    @FXML
    private TextField text_phone;

    @FXML
    private TableView<Service> table;
    @FXML
    private TableColumn<Service, String> nameColumn;
    @FXML
    private TableColumn<Service, String> priceColumn;
    @FXML
    private TableColumn<Service, String> timeColumn;
    @FXML
    private TableColumn<Service, String> fieldColumn;

    @FXML
    private void initialize() {
        //Инициализация таблицы
        table.refresh();
        DataService.removeAll();
        services.clear();
        table.refresh();
        List<Object> list= new ArrayList();
        Service service = new Service("allService");
        Object obj = (Object) service;
        try {
            list = (send(obj));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


        for (int i = 0; i < list.size(); i++) {
            services.add((Service) list.get(i));
        }

        DataService = FXCollections.observableArrayList(services);

        nameColumn.setCellValueFactory(new PropertyValueFactory<Service, String>("name"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<Service, String>("time"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Service, String>("price"));
        fieldColumn.setCellValueFactory(new PropertyValueFactory<Service, String>("field"));
        table.setItems(DataService);

        //Инициализация Combobox услуг
        List<Object> listServiceName= new ArrayList();
        Client client = new Client("serviceName");
        Object objName = (Object) client;
        try {
            listServiceName = (send(objName));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < listServiceName.size(); i++) {
            servicesName.add((String)listServiceName.get(i));
        }
        DataServiceName = FXCollections.observableArrayList(servicesName);
        text_service.setItems(DataServiceName);

        //Инициализация Combobox области
        List<Object> listServiceField= new ArrayList();
        Master serviceField = new Master("fieldName");
        Object objField = (Object) serviceField;
        try {
            listServiceField = (send(objField));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < listServiceField.size(); i++) {
            servicesField.add((String)listServiceField.get(i));
        }
        DataServiceField = FXCollections.observableArrayList(servicesField);
        filter.setItems(DataServiceField);
    }

    @FXML
    private void back(ActionEvent event) throws IOException {
        Stage stage = (Stage) buttonBack.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load(Controller.class.getResource("/fxml/sample.fxml"));
        Scene scene = new Scene(root);

        Stage primaryStage = new Stage();
        primaryStage.setTitle("KRASIVO.");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    @FXML
    private void setbuttonSubmit(ActionEvent event) throws IOException {
        String firstname=text_name.getText();
        String name = text_name.getText();
        String master="master";
        String date = text_date.getText();
        String time=null;
        String phone=text_phone.getText();
        String service = text_service.getSelectionModel().getSelectedItem();
        Client client = new Client(firstname,name, service, date,time,master, phone, loginUser);
        if(sendToServer(client)) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Запись");
            alert.setHeaderText("Вы записаны успешно!");
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Запись");
            alert.setHeaderText("Вы не записаны");
            alert.showAndWait();
        }
    }
    static String loginUser;
    public void memoryLogin(String login){
        loginUser = login;
    }

    public boolean sendToServer(Object obj){
        List<Object> res = new ArrayList<Object>();
        try {
            res = (send(obj));
            System.out.println(res.get(0).toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Boolean.parseBoolean(res.get(0).toString());
    }
    @FXML
    private void setButtonSortMin(ActionEvent event) throws IOException {

        DataService.removeAll();
        DataService = table.getItems();
        services.clear();
        table.refresh();
        services = DataService;

        Collections.sort(services, new Comparator<Service>() {
            @Override
            public int compare(Service o1, Service o2) {
                return o2.getPrice().compareTo(o1.getPrice());
            }
        });

        DataService = FXCollections.observableArrayList(services);

        nameColumn.setCellValueFactory(new PropertyValueFactory<Service, String>("name"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<Service, String>("time"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Service, String>("price"));
        fieldColumn.setCellValueFactory(new PropertyValueFactory<Service, String>("field"));
        table.setItems(DataService);
    }
    @FXML
    private void setbuttonSortMax(ActionEvent event) throws IOException {
        DataService.removeAll();
        DataService = table.getItems();
        services.clear();
        table.refresh();
        services = DataService;

        Collections.sort(services, new Comparator<Service>() {
            @Override
            public int compare(Service o1, Service o2) {
                return o1.getPrice().compareTo(o2.getPrice());
            }
        });

        DataService = FXCollections.observableArrayList(services);

        nameColumn.setCellValueFactory(new PropertyValueFactory<Service, String>("name"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<Service, String>("time"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Service, String>("price"));
        fieldColumn.setCellValueFactory(new PropertyValueFactory<Service, String>("field"));
        table.setItems(DataService);
    }
    @FXML
    private void setFilter(ActionEvent event) throws IOException {
        table.refresh();
        DataService.removeAll();
        services.clear();
        table.refresh();
        List<Object> list= new ArrayList();
        Service service = new Service("allService");
        Object obj = (Object) service;
        try {
            list = (send(obj));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


        for (int i = 0; i < list.size(); i++) {
            services.add((Service) list.get(i));
        }

        String serviceText = filter.getSelectionModel().getSelectedItem();

        List<Service> filterServices = new ArrayList<Service>();

        for (int i = 0; i < services.size(); i++) {
            if(services.get(i).getField().equals(serviceText)){
                filterServices.add(services.get(i));
            }
        }

        DataService = FXCollections.observableArrayList(filterServices);
        nameColumn.setCellValueFactory(new PropertyValueFactory<Service, String>("name"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<Service, String>("time"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Service, String>("price"));
        fieldColumn.setCellValueFactory(new PropertyValueFactory<Service, String>("field"));
        table.setItems(DataService);

    }
    public User sendToServer(User obj){
        User res= new User();
        try {
            res = (classes.User) send(obj);
            // System.out.println(res.get(0).toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
}

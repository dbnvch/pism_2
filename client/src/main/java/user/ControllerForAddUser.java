package user;

import classes.*;
import classes.Shedule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import sample.Controller;
import sample.NetworkConnection;
import service.Services;

import java.io.IOException;
import java.util.*;

public class ControllerForAddUser extends NetworkConnection {

    private ObservableList<Shedule> DataShedule = FXCollections.observableArrayList();
    private List<Shedule> shedule = new ArrayList<Shedule>();
    private List<Shedule> shedules = new ArrayList<Shedule>();

    private ObservableList<String> DataServiceField = FXCollections.observableArrayList();
    private List<String> servicesField = new ArrayList<String>();

    private ObservableList<String> Data = FXCollections.observableArrayList();
    private List<String> servicesName = new ArrayList<String>();

    private ObservableList<Shedule> DataAllServices = FXCollections.observableArrayList();
    private List<Shedule> allSetvices = new ArrayList<Shedule>();

    private ObservableList<User> DataUserInform = FXCollections.observableArrayList();
    private List<User> users = new ArrayList<User>();

    private ObservableList<Client> DataClient = FXCollections.observableArrayList();
    private List<Client> clients = new ArrayList<Client>();

    private int discountForClient = 0;

    @FXML
    private Label discountLabel = new Label();

    @FXML
    private TableColumn<User, String> surnameColumn;

    @FXML
    private TableColumn<User, String> nameColumn;

    @FXML
    private TableColumn<User, String> phoneColumn;

    @FXML
    private TableView<User> tableUser;

    //    @FXML
//    private String text_name;
    @FXML
    private TableView<Shedule> table;
//    @FXML
//    private ComboBox<String> text_master = new ComboBox<String>();
//    @FXML
//    private String text_phone;

    @FXML
    private Button buttonSubmit;
    //
    @FXML
    private Button buttonShowPrice;
    @FXML
    private Button buttonOk;
    @FXML
    private Button buttonBack;
    @FXML
    private Button buttonCancel;
//
//    @FXML
//    private Button buttonSortMin;
//
//    @FXML
//    private Button buttonSortMax;

    @FXML
    private ComboBox<String> filter;

    @FXML
    private TableColumn<Shedule, String> serviceColumn;

    @FXML
    private TableColumn<Shedule, String> masterColumn;

    @FXML
    private TableColumn<Shedule, String> dateColumn;

    @FXML
    private TableColumn<Shedule, String> timeColumn;

    @FXML
    private TableView<Client> tableBooking;


    @FXML
    private TableColumn<Client, String> serviceBookongColumn;

    @FXML
    private TableColumn<Client, String> masterBooikingColumn;

    @FXML
    private TableColumn<Client, String> dateBookingColumn;

    @FXML
    private TableColumn<Client, String> timeBookingColumn;

    @FXML
    private TextField text_surname;

    @FXML
    private DatePicker text_date;
    @FXML
    private ComboBox<String> text_service = new ComboBox<String>();

    Shedule clickedRow = new Shedule();
    String service, master, date, time;
    private Object User;

    @FXML
    private void setText() {
        table.setRowFactory(tv -> {
            TableRow<Shedule> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY
                        && event.getClickCount() == 1) {
                    clickedRow = row.getItem();
                    //   text_service.setValue(clickedRow.getService());
                    // text_master.setValue(clickedRow.getMaster());
                    //       text_date.setValue(LocalDate.parse(clickedRow.getDate()));
                    //text_time.setText(clickedRow.getTime());
                    service = clickedRow.getService();
                    System.out.println(service + ' ' + clickedRow.getService());
                    master = clickedRow.getMaster();
                    date = clickedRow.getDate();
                    time = clickedRow.getTime();
                }
            });
            return row;
        });
    }

    String surname, name, phone;
    User clickedRowUser = new User();

    @FXML
    private void setTextUser() {
        tableUser.setRowFactory(tv -> {
            TableRow<User> rowUser = new TableRow<>();
            rowUser.setOnMouseClicked(event -> {
                if (!rowUser.isEmpty() && event.getButton() == MouseButton.PRIMARY
                        && event.getClickCount() == 1) {
                    clickedRowUser = rowUser.getItem();
                    //   text_service.setValue(clickedRow.getService());
                    // text_master.setValue(clickedRow.getMaster());
                    //       text_date.setValue(LocalDate.parse(clickedRow.getDate()));
                    //text_time.setText(clickedRow.getTime());
                    surname = clickedRowUser.getSurname();
                    name = clickedRowUser.getName();
                    phone = clickedRowUser.getPhone();
                }
            });
            return rowUser;
        });
    }

    @FXML
    private void initialize() {
        DataShedule.clear();
        shedules.clear();
        table.refresh();
        DataUserInform.clear();
        users.clear();
        tableUser.refresh();
        DataClient.clear();
        clients.clear();
        tableBooking.refresh();
        String surname;

        List<Object> listUser = new ArrayList();
        User user = new User(Controller.logUser, "userInformation", "1");
        User user1 = new User("", "", "");
        Object objUser = (Object) user;
        try {
            listUser = send(objUser);
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < listUser.size(); i++) {
            users.add((User) listUser.get(i));
        }
        DataUserInform = FXCollections.observableArrayList(users);
        surnameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("surname"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<User, String>("phone"));
        tableUser.setItems(DataUserInform);
        setTextUser();

        List<Object> listClient = new ArrayList();
        Client clientOrder = new Client(Controller.logUser, "clientInformation");
        Object objClient = (Object) clientOrder;
        try {
            listClient = send(objClient);
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < listClient.size(); i++) {
            clients.add((Client) listClient.get(i));
        }

        try {
            List<Object> getDiscountForClient = send(new Client(Controller.logUser, "getDiscountForClient"));

            if (Objects.isNull(getDiscountForClient) || getDiscountForClient.isEmpty()) {
                discountForClient = 0;
                System.err.println("Error while resolving client discount");
            } else {

                discountForClient = (int) getDiscountForClient.get(0);
                System.out.println("Discount ->" + discountForClient);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        DataClient = FXCollections.observableArrayList(clients);
        serviceBookongColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("service"));
        masterBooikingColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("masterName"));
        dateBookingColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("date"));
        timeBookingColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("time"));
        tableBooking.setItems(DataClient);
        setTextUser();

        DataShedule = FXCollections.observableArrayList(shedules);

        List<Object> listShedule = new ArrayList();
        Shedule shedule = new Shedule("allShedule");
        Object objShedule = (Object) shedule;
        try {
            listShedule = (send(objShedule));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


        for (int i = 0; i < listShedule.size(); i++) {
            shedules.add((Shedule) listShedule.get(i));
        }

        DataShedule = FXCollections.observableArrayList(shedules);

        dateColumn.setCellValueFactory(new PropertyValueFactory<Shedule, String>("date"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<Shedule, String>("time"));
        serviceColumn.setCellValueFactory(new PropertyValueFactory<Shedule, String>("service"));
        masterColumn.setCellValueFactory(new PropertyValueFactory<Shedule, String>("master"));

        table.setItems(DataShedule);

        List<Object> list = new ArrayList();
        Client client = new Client("serviceName");
        Object obj = (Object) client;
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
            servicesName.add((String) list.get(i));
        }
        System.out.println("allsetservice");
        Data = FXCollections.observableArrayList(servicesName);
        text_service.setItems(Data);

        discountLabel.setText("Ваша персональная скидка: " + discountForClient + "%");

        setText();

    }

    @FXML
    private void setbuttonBack(ActionEvent event) throws IOException {
        Stage stage = (Stage) buttonBack.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load(Controller.class.getResource("/fxml/sample.fxml"));
        Scene scene = new Scene(root);

        Stage primaryStage = new Stage();
        primaryStage.setTitle("Администратор");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    @FXML
    private void setButtonShowPrice(ActionEvent event) throws IOException {
        Stage stage = (Stage) buttonShowPrice.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load(ShowPrice.class.getResource("/fxml/price.fxml"));
        Scene scene = new Scene(root);

        Stage primaryStage = new Stage();
        primaryStage.setTitle("Прайс");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @FXML
    private void setbuttonSubmit(ActionEvent event) throws IOException {
        User activeUser = getActiveUser();
        Client client = new Client(activeUser.getSurname(), activeUser.getName(), service, master, date, time, phone);
        Shedule shedule = new Shedule(service, master, date, time, client.getSurname(), "booking");
        sendToServer(shedule);
        if (sendToServer(client)) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Запись");
            alert.setHeaderText("Вы записаны успешно!");
            alert.showAndWait();
            initialize();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Запись");
            alert.setHeaderText("Вы не записаны");
            alert.showAndWait();
        }
    }

    private User getActiveUser() {
        if (users.isEmpty()) {
            throw new RuntimeException("Unexpected error, active user is NULL");
        }

        return users.get(0);
    }

    @FXML
    private void setFilter(ActionEvent event) throws IOException {
        table.refresh();
        Data.removeAll();
        shedules.clear();
        table.refresh();
        List<Object> list = new ArrayList();
        Shedule shedule = new Shedule("allServiceName");
        Object obj = (Object) shedule;
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
            allSetvices.add((Shedule) list.get(i));
        }

        String serviceText = text_service.getValue();
        System.out.println(serviceText);

        List<Shedule> filterServices = new ArrayList<>();

        for (int i = 0; i < allSetvices.size(); i++) {
            if (allSetvices.get(i).getService().equals(serviceText)) {
                filterServices.add(allSetvices.get(i));
            }
        }

        dateColumn.setCellValueFactory(new PropertyValueFactory<Shedule, String>("date"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<Shedule, String>("time"));
        serviceColumn.setCellValueFactory(new PropertyValueFactory<Shedule, String>("service"));
        masterColumn.setCellValueFactory(new PropertyValueFactory<Shedule, String>("master"));
        table.setItems(DataAllServices);

    }

    @FXML
    private void setFilterService(ActionEvent event) throws IOException {

        table.refresh();
        Data.removeAll();
        shedules.clear();
        table.refresh();

        List<Object> listShedule = new ArrayList();
        Shedule shedule = new Shedule("allShedule");
        Object objShedule = (Object) shedule;
        try {
            listShedule = (send(objShedule));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


        for (int i = 0; i < listShedule.size(); i++) {
            allSetvices.add((Shedule) listShedule.get(i));

        }

        String serviceText = text_service.getValue();
        System.out.println(serviceText);
        List<Shedule> filterServices = new ArrayList<>();
        for (int i = 0; i < allSetvices.size(); i++) {
            if (allSetvices.get(i).getService().equals(serviceText)) {
                filterServices.add(allSetvices.get(i));
            }
        }
        DataAllServices = FXCollections.observableArrayList(filterServices);
        dateColumn.setCellValueFactory(new PropertyValueFactory<Shedule, String>("date"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<Shedule, String>("time"));
        serviceColumn.setCellValueFactory(new PropertyValueFactory<Shedule, String>("service"));
        masterColumn.setCellValueFactory(new PropertyValueFactory<Shedule, String>("master"));
        table.setItems(DataAllServices);

    }

    public boolean sendToServer(Object obj) {
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

}

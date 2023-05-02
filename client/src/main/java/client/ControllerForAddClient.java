package client;

import admin.*;
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
import sample.NetworkConnection;
import service.Services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ControllerForAddClient extends NetworkConnection {

    private ObservableList<Shedule> DataShedule = FXCollections.observableArrayList();
    private List<Shedule> shedule = new ArrayList<Shedule>();
    private List<Shedule> shedules = new ArrayList<Shedule>();

    private ObservableList<String> Data = FXCollections.observableArrayList();
    private List<String> servicesName = new ArrayList<String>();

    private ObservableList<Shedule> DataAllServices = FXCollections.observableArrayList();
    private List<Shedule> allSetvices = new ArrayList<Shedule>();

    @FXML
    private TextField text_name;
    @FXML
    private TableView<Shedule> table;

    @FXML
    private TextField text_phone;

    @FXML
    private Button buttonShowPrice;
    @FXML
    private Button buttonSubmit;
    @FXML
    private Button buttonOk;
    @FXML
    private Button buttonBack;

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

//    @FXML
//    private TableColumn<Client, String> priceColumn;

    @FXML
    private TextField text_surname;
    @FXML
    private ComboBox<String> text_service = new ComboBox<String>();

    public ControllerForAddClient() {
    }

    Shedule clickedRow = new Shedule();
    String service, master, date, time;

    @FXML
    private void setText() {
        table.setRowFactory(tv -> {
            TableRow<Shedule> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY
                        && event.getClickCount() == 1) {
                    clickedRow = row.getItem();
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


    @FXML
    private void initialize() {
        DataShedule.clear();
        shedules.clear();
        table.refresh();

        List<Object> listShedule = new ArrayList();
        Shedule shedule = new Shedule("allShedule");
        Object objShedule = (Object) shedule;
        System.out.println("allshedule");
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
            System.out.println(shedules.size());
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

        Data = FXCollections.observableArrayList(servicesName);
        text_service.setItems(Data);

        setText();
    }


    @FXML
    private void setbuttonBack(ActionEvent event) throws IOException {
        Stage stage = (Stage) buttonBack.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load(ControllerAdmin.class.getResource("/fxml/admin.fxml"));
        Scene scene = new Scene(root);

        Stage primaryStage = new Stage();
        primaryStage.setTitle("Администратор");
        primaryStage.setScene(scene);
        primaryStage.show();
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

    @FXML
    private void setButtonSubmit(ActionEvent event) throws IOException {
        if (ValidEnter()) {
            AlertEnter();
            text_surname.clear();
            text_name.clear();
        } else {
            if (ValidPhone()) {
                AlertPhone();
                text_phone.clear();
            } else {
                String surname = text_surname.getText();
                String name = text_name.getText();
                String phone = text_phone.getText();
                String activeService = text_service.getValue();
                Client client = new Client(surname, name, activeService, master, date, time, phone);
                Shedule shedule = new Shedule(service, master, date, time, surname, "booking");
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
        }
    }

    @FXML
    private void setFilter(ActionEvent event) throws IOException {
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

    @FXML
    private void setButtonShowPrice(ActionEvent event) throws IOException {
        Stage stage = (Stage) buttonShowPrice.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load(Services.class.getResource("/fxml/services.fxml"));
        Scene scene = new Scene(root);

        Stage primaryStage = new Stage();
        primaryStage.setTitle("Прайс");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public boolean ValidEnter() {
        if (text_surname.getText().matches("[\\d]+") | text_name.getText().matches("[\\d]+") |
                text_surname.getText().matches("[\\s]+") | text_name.getText().matches("[\\s]+"))
            return true;
        else return false;
    }

    public void AlertEnter() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка!");
        alert.setHeaderText("Введите Фамилию и Имя корректно");
        alert.setContentText("Эти поля не должны содержать цифры!");
        alert.showAndWait();
    }

    public boolean ValidPhone() {
        if (text_phone.getText().matches("(\\+*)\\d{12}"))
            return false;
        else return true;
    }

    public void AlertPhone() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка!");
        alert.setHeaderText("Введите Телефон корректно");
        alert.setContentText("Номер должен выглядить так: +(12 цифр)!");
        alert.showAndWait();
    }
}

package client;

import admin.ControllerAdmin;
import classes.Client;
import classes.Master;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ControllerForEditClient extends NetworkConnection {
    private ObservableList<Client> Data = FXCollections.observableArrayList();
    private List<Client> clients = new ArrayList<Client>();

    private ObservableList<String> DataMaster = FXCollections.observableArrayList();
    private List<String> masterFirstName = new ArrayList<String>();

    private ObservableList<String> DataString = FXCollections.observableArrayList();
    private List<String> servicesName = new ArrayList<String>();
    @FXML
    private Button buttonBack;
    @FXML
    private TextField text_firstname;
    @FXML
    private TextField text_name;
    @FXML
    private ComboBox<String> text_service = new ComboBox<String>();
    @FXML
    private TextField text_date;
    @FXML
    private TextField text_phone;
    @FXML
    private ComboBox<String> text_masterName= new ComboBox<String>();
    @FXML
    private TextField text_time;
    @FXML
    private TableView<Client> table;
    @FXML
    private TableColumn<Client, Integer> idColumn;
    @FXML
    private TableColumn<Client, String> firstnameColumn;
    @FXML
    private TableColumn<Client, String> nameColumn;
    @FXML
    private TableColumn<Client, String> serviceColumn;
    @FXML
    private TableColumn<Client, String> masterNameColumn;
    @FXML
    private TableColumn<Client, String> timeColumn;
    @FXML
    private TableColumn<Client, String> dateColumn;
    @FXML
    private TableColumn<Client, Integer> phoneColumn;
    @FXML
    private void initialize() {

        Data.clear();
        clients.clear();
        table.refresh();

        List<Object> list= new ArrayList();
        Client client = new Client("allClient");
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
            clients.add((Client) list.get(i));
        }

        Data = FXCollections.observableArrayList(clients);

        firstnameColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("surname"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("name"));
        serviceColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("service"));
        masterNameColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("masterName"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("time"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("date"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<Client, Integer>("phone"));
        table.setItems(Data);

        List<Object> listName= new ArrayList();
        Client cl = new Client("serviceName");
        Object ob = (Object) cl;
        try {
            listName = (send(ob));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < listName.size(); i++) {
            servicesName.add((String)listName.get(i));
        }
        DataString = FXCollections.observableArrayList(servicesName);
        text_service.setItems(DataString);
        Shedule sheduleM = new Shedule("masterFirstName");
        Object objM = (Object) sheduleM;
        try {
            list = (send(objM));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < list.size(); i++) {
            masterFirstName.add((String)list.get(i));
        }
        DataMaster = FXCollections.observableArrayList(masterFirstName);
        text_masterName.setItems(DataMaster);
        setText();
    }

    Client clickedRow = new Client();

    @FXML
    private void setText() {
        table.setRowFactory(tv -> {
            TableRow<Client> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (! row.isEmpty() && event.getButton()== MouseButton.PRIMARY
                        && event.getClickCount() == 1) {
                    clickedRow = row.getItem();
                    text_firstname.setText(clickedRow.getFirstName());
                    text_service.setValue(clickedRow.getService());
                    text_name.setText(clickedRow.getName());
                    text_date.setText(clickedRow.getDate());
                    text_time.setText(clickedRow.getTime());
                    text_masterName.setValue(clickedRow.getMasterName());
                    text_phone.setText(""+clickedRow.getPhone()+"");

                }
            });
            return row ;
        });
    }

    @FXML
    private void setButtonEdit(ActionEvent event) {
        String  firstname=text_firstname.getText();
        String name = text_name.getText();
        String date = text_date.getText();
        String time=text_time.getText();
        String master=" ";
        String phone=text_phone.getText();
        String service = text_service.getSelectionModel().getSelectedItem();
        text_firstname.setText("");
        text_name.setText("");
        text_date.setText("");
        text_time.setText("");
        text_phone.setText("");
        text_service.setValue("");
        Client client = new Client(clickedRow.getId(),firstname,name, service,master, date,time, phone, "editClient");
        List<Object> res = new ArrayList<Object>();
        try {
            res = (send(client));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(Boolean.parseBoolean(res.get(0).toString())){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Редактирование клиента");
            alert.setHeaderText("Клиент отредактирован успешно");
            alert.showAndWait();
            initialize();

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Редактирование клиента");
            alert.setHeaderText("Клиент не отредактирован");
            alert.showAndWait();
        }
    }

    @FXML
    private void setButtonBack(ActionEvent event) throws IOException {
        Stage stage = (Stage) buttonBack.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load(ControllerAdmin.class.getResource("/fxml/admin.fxml"));
        Scene scene = new Scene(root);

        Stage primaryStage = new Stage();
        primaryStage.setTitle("Администратор");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

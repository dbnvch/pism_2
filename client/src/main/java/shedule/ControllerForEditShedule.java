package shedule;

import admin.ControllerAdmin;

import classes.Client;
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
import java.lang.String;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ControllerForEditShedule extends NetworkConnection {
    private ObservableList<Shedule> Data = FXCollections.observableArrayList();
    private List<Shedule> shedule = new ArrayList<Shedule>();

    private ObservableList<String> DataService = FXCollections.observableArrayList();
    private List<String> servicesName = new ArrayList<String>();

    private ObservableList<String> DataMaster = FXCollections.observableArrayList();
    private List<String> masterFirstName = new ArrayList<String>();

   // private ObservableList<Client> Dat = FXCollections.observableArrayList();
    private List<Shedule> shedules = new ArrayList<Shedule>();

    @FXML
    private Button buttonBack;
    @FXML
    private ComboBox<String> text_service = new ComboBox<String>();

    @FXML
    private DatePicker text_date;

    @FXML
    private TextField text_time;

    @FXML
    private ComboBox<String> text_master = new ComboBox<String>();

    @FXML
    private TableView<Shedule> table;
    @FXML
    private TableColumn<Shedule, Integer> idColumn;
    @FXML
    private TableColumn<Shedule, String> timeColumn;
    @FXML
    private TableColumn<Shedule, String> masterColumn;
    @FXML
    private TableColumn<Shedule, String> serviceColumn;
    @FXML
    private TableColumn<Shedule, String> dateColumn;

    @FXML
    private void initialize() {

    //    text_field.setItems(FieldOptions);
        table.refresh();
      //Data.removeAll();
        shedules.clear();
        table.refresh();
        Data.clear();
        shedule.clear();
        table.refresh();

        List<Object> list= new ArrayList();
        Shedule shedule = new Shedule("allShedule");
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
            shedules.add((Shedule) list.get(i));
            System.out.println(shedules.size());
        }

        Data = FXCollections.observableArrayList(shedules);
        masterColumn.setCellValueFactory(new PropertyValueFactory<Shedule, String>("master"));
        serviceColumn.setCellValueFactory(new PropertyValueFactory<Shedule, String>("service"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<Shedule, String>("date"));
        timeColumn.setCellValueFactory(new  PropertyValueFactory<Shedule,String>("time"));
        table.setItems(Data);

        List<Object> listName= new ArrayList();
        Shedule shedule1 = new Shedule("serviceName");
        Object ob = (Object) shedule1;
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
        DataService = FXCollections.observableArrayList(servicesName);
        text_service.setItems(DataService);

        Shedule shedule2 = new Shedule("masterFirstName");
        ob = (Object) shedule2;
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
            masterFirstName.add((String)listName.get(i));
        }
        DataMaster= FXCollections.observableArrayList(masterFirstName);
        text_master.setItems(DataMaster);

        setText();
    }

    Shedule clickedRow = new Shedule();

    @FXML
    private void setText() {
        table.setRowFactory(tv -> {
            TableRow<Shedule> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (! row.isEmpty() && event.getButton()== MouseButton.PRIMARY
                        && event.getClickCount() == 1) {
                    clickedRow = row.getItem();
                    text_service.setValue(clickedRow.getService());
                    text_master.setValue(clickedRow.getMaster());
                    text_date.setValue(LocalDate.parse(clickedRow.getDate()));
                    text_time.setText(clickedRow.getTime());
                }
            });
            return row ;
        });
    }

    @FXML
    private void setButtonEdit(ActionEvent event) {
        String date =  String.valueOf(text_date.getValue());
        String time = text_time.getText();
        String service = text_service.getSelectionModel().getSelectedItem();
        String master = text_master.getSelectionModel().getSelectedItem();

        text_date.setValue(null);
        text_master.setValue(null);
        text_service.setValue(null);
        text_time.setText("");
        Shedule shedule = new Shedule(clickedRow.getId(),service,master,date,time,"null","editShedule");
        List<Object> res = new ArrayList<Object>();
        try {
            res = (send(shedule));
            System.out.println(shedule+" " +res);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(Boolean.parseBoolean(res.get(0).toString())){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Редактирование место для записи");
            alert.setHeaderText("Место для записи отредактирован успешно");
            alert.showAndWait();
            initialize();

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Редактирование места для записи");
            alert.setHeaderText("Место для записи не отредактирован");
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

package shedule;

import admin.*;
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
import javafx.stage.Stage;
import sample.NetworkConnection;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerForAddShedule extends NetworkConnection {
    private ObservableList<String> DataService = FXCollections.observableArrayList();
    private List<String> servicesName = new ArrayList<String>();
    private ObservableList<String> DataMaster = FXCollections.observableArrayList();
    private List<String> masterFirstName = new ArrayList<String>();
    private ObservableList<Client> Data = FXCollections.observableArrayList();
    private List<Shedule> shedules = new ArrayList<Shedule>();
    @FXML
    private Button buttonOk;

    @FXML
    private ComboBox<String> text_service = new ComboBox<String>();

    @FXML
    private Button buttonBack;

    @FXML
    private DatePicker text_date;

    @FXML
    private TextField text_time;

    @FXML
    private ComboBox<String> text_master = new ComboBox<String>();


    @FXML
    private void initialize() {
        List<Object> list= new ArrayList();
        Shedule shedule = new Shedule("serviceName");
        Object obj = (Object) shedule;
        Shedule sheduleM = new Shedule("masterFirstName");
        Object objM = (Object) sheduleM;
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
            servicesName.add((String)list.get(i));
        }
        DataService = FXCollections.observableArrayList(servicesName);
        text_service.setItems(DataService);
       // Object objM = (Object) shedule;
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
        text_master.setItems(DataMaster);
    }

    @FXML
    private void setButtonOK(ActionEvent event) throws IOException {

        String date =  String.valueOf(text_date.getValue());
        String time = text_time.getText();
        String service = text_service.getSelectionModel().getSelectedItem();
        String master = text_master.getSelectionModel().getSelectedItem();

        Shedule shedule = new Shedule(service, master, date, time);
        if(ValidTime()){
            AlertTime();
            text_time.clear();
        }else{  if(ValidDate()){
            AlertDate();
        }else{
        if(sendToServer(shedule)) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Добавление нового места");
            alert.setHeaderText("Место успешно создано");
            alert.showAndWait();

            Stage stage = (Stage) buttonOk.getScene().getWindow();
            stage.close();

            Parent root = FXMLLoader.load(ControllerAdmin.class.getResource("/fxml/admin.fxml"));
            Scene scene = new Scene(root);

            Stage primaryStage = new Stage();
            primaryStage.setTitle("Администратор");
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        else {
            System.out.println("Error service");
        }}
    }}
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
    public boolean ValidTime(){
        if(text_time.getText().matches("^(([0,1][0-9])|(2[0-3])):[0-5][0-9]$"))
            return false;
        else return true;
    }

    public void AlertTime(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка!");
        alert.setHeaderText("Введите Время корректно");
        alert.setContentText("Эти поля должны выглядеть так: hh:mm!");
        alert.showAndWait();
    }
    public boolean ValidDate(){
        SimpleDateFormat format=new SimpleDateFormat();
        format.applyPattern("yyyy-mm-dd");
      //  Date newDate=format.parse(text_date.getValue());
        LocalDate now=LocalDate.now();
        int compareResult=now.compareTo(text_date.getValue());
        if(compareResult<0)
            return false;
        else return true;
    }
    public void AlertDate(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка!");
        alert.setHeaderText("Введите Даты корректно");
        alert.setContentText("Дата не может быть раннеее сегодня!");
        alert.showAndWait();
    }
}

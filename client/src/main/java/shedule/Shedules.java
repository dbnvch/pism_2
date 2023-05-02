package shedule;

import admin.ControllerAdmin;
import classes.Client;
import classes.Shedule;
import java.lang.String;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.NetworkConnection;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class Shedules extends NetworkConnection {
    private ObservableList<Shedule> Data = FXCollections.observableArrayList();
    private List<Shedule> shedule = new ArrayList<Shedule>();
    private List<Shedule> shedules = new ArrayList<Shedule>();
    private ObservableList<String> DataServiceDate = FXCollections.observableArrayList();
    private List<String> servicesDate = new ArrayList<String>();

    @FXML
    private Button buttonBack;
    @FXML
    private TableView<Shedule> table;
    @FXML
    private TableColumn<Shedule, Integer> idColumn;
    @FXML
    private TableColumn<Client, String> dateColumn;
    @FXML
    private TableColumn<Shedule, String> timeColumn;

    @FXML
    private TableColumn<Shedule, String> serviceColumn;

    @FXML
    private TableColumn<Shedule, String> masterColumn;

//
//    @FXML
//    private DatePicker text_date;



    @FXML
    private void initialize() {
//        Data.clear();
//        shedule.clear();
//        table.refresh();
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

      //  idColumn.setCellValueFactory(new PropertyValueFactory<Shedule, Integer>("id"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("date"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<Shedule, String>("time"));
        serviceColumn.setCellValueFactory(new PropertyValueFactory<Shedule, String>("service"));
        masterColumn.setCellValueFactory(new PropertyValueFactory<Shedule, String>("master"));
       // timeColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("time"));
      //  clientColumn.setCellValueFactory(new PropertyValueFactory<Shedule, String>("client"));
        table.setItems(Data);

        //Инициализация Combobox услуг
//        List<Object> listServiceDate= new ArrayList();
//        Shedule shedule1 = new Shedule("date");
//        Object objName = (Object) shedule1;
//        try {
//            listServiceDate= (send(objName));
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        for (int i = 0; i < listServiceDate.size(); i++) {
//            servicesDate.add((String)listServiceDate.get(i));
//        }
//        DataServiceDate = FXCollections.observableArrayList(servicesDate);
//        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-mm-dd");
//        LocalDate date=LocalDate.parse((CharSequence) DataServiceDate,formatter);
//        text_date.setValue(date);
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
}

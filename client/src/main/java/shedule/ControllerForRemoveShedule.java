package shedule;

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

public class ControllerForRemoveShedule extends NetworkConnection {
    private ObservableList<Shedule> Data = FXCollections.observableArrayList();
    private List<Shedule> shedules = new ArrayList<Shedule>();
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
    private Button buttonBack;
    @FXML
    private Button buttonRemove;
    @FXML
    private void initialize() {
        Data.clear();
        shedules.clear();
        table.refresh();
        List<Object> list= new ArrayList();
       Shedule shedule= new Shedule("allShedule");
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

System.out.println("remove");
        for (int i = 0; i < list.size(); i++) {
            shedules.add((Shedule) list.get(i));
        }

        Data = FXCollections.observableArrayList(shedules);

       idColumn.setCellValueFactory(new PropertyValueFactory<Shedule, Integer>("id"));
        masterColumn.setCellValueFactory(new PropertyValueFactory<Shedule, String>("master"));
        serviceColumn.setCellValueFactory(new PropertyValueFactory<Shedule, String>("service"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<Shedule, String>("date"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<Shedule, String>("time"));
        table.setItems(Data);
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
                }
            });
            return row ;
        });
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


    @FXML
    private void setButtonRemove(ActionEvent event) throws IOException {
        Shedule shedule = new Shedule(clickedRow.getId(), "removeShedule");
        List<Object> res = new ArrayList<Object>();
        try {
            res = (send(shedule));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(Boolean.parseBoolean(res.get(0).toString())){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Удаление места");
            alert.setHeaderText("Место удалено успешно");
            alert.showAndWait();
            initialize();

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Удаление места");
            alert.setHeaderText("Место не удалено");
            alert.showAndWait();
        }
    }
}

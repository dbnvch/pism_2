package service;

import admin.ControllerAdmin;
import classes.Service;
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

public class ControllerForRemoveService extends NetworkConnection {
    private ObservableList<Service> Data = FXCollections.observableArrayList();
    private List<Service> services = new ArrayList<Service>();
    @FXML
    private Button buttonBack;

    @FXML
    private TableView<Service> table;

    @FXML
    private TableColumn<Service, Integer> idColumn;

    @FXML
    private TableColumn<Service, String> nameColumn;

    @FXML
    private TableColumn<Service, String> priceColumn;

    @FXML
    private TableColumn<Service, String> fieldColumn;

    @FXML
    private void initialize() {
        Data.clear();
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

        Data = FXCollections.observableArrayList(services);

        idColumn.setCellValueFactory(new PropertyValueFactory<Service, Integer>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Service, String>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Service, String>("price"));
        fieldColumn.setCellValueFactory(new PropertyValueFactory<Service, String>("field"));
        table.setItems(Data);
        getId();
    }
    Service clickedRow = new Service();

    @FXML
    private void getId() {
        table.setRowFactory(tv -> {
            TableRow<Service> row = new TableRow<>();
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
        Service service = new Service(clickedRow.getId(), "removeService");
        List<Object> res = new ArrayList<Object>();
        try {
            res = (send(service));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(Boolean.parseBoolean(res.get(0).toString())){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Удаление услуги");
            alert.setHeaderText("Услуга удалена успешно");
            alert.showAndWait();
            initialize();

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Удаление услуги");
            alert.setHeaderText("Услуга не может быть удалена, т.к заказана клиентом");
            alert.showAndWait();
        }
    }
}

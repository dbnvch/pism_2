package user;


import admin.ControllerAdmin;
import classes.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.NetworkConnection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class ShowPrice extends NetworkConnection {
    private ObservableList<Service> Data = FXCollections.observableArrayList();
    private List<Service> services = new ArrayList<>();
    @FXML
    private Button buttonBack;
    @FXML
    private Button buttonSortMin;
    @FXML
    private Button buttonSortMax;
    @FXML
    private TableView<Service> table;

    @FXML
    private TableColumn<Service, Integer> idColumn;

    @FXML
    private TableColumn<Service, String> nameColumn;

    @FXML
    private TableColumn<Service, String> priceColumn;

    @FXML
    private TableColumn<Service, String> priceWithDiscountColumn;

    @FXML
    private TableColumn<Service, String> timeColumn;

    @FXML
    private TableColumn<Service, String> fieldColumn;

    @FXML
    private void initialize() {

        List<Object> list = new ArrayList();
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

//        idColumn.setCellValueFactory(new PropertyValueFactory<Service, Integer>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Service, String>("name"));
//        timeColumn.setCellValueFactory(new PropertyValueFactory<Service, String>("time"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Service, String>("price"));
        fieldColumn.setCellValueFactory(new PropertyValueFactory<Service, String>("field"));
        table.setItems(Data);
    }


    @FXML
    private void setButtonBack(ActionEvent event) throws IOException {
        Stage stage = (Stage) buttonBack.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load(ControllerForAddUser.class.getResource("/fxml/addNewUser.fxml"));
        Scene scene = new Scene(root);

        Stage primaryStage = new Stage();
        primaryStage.setTitle("Личный кабинет пользователя");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @FXML
    private void setButtonSortMin(ActionEvent event) throws IOException {

        Data.removeAll();
        Data = table.getItems();
        services.clear();
        table.refresh();
        services = Data;

        Collections.sort(services, new Comparator<Service>() {
            @Override
            public int compare(Service o1, Service o2) {
                float s1 = Float.parseFloat(o1.getPrice());
                float s2 = Float.parseFloat(o2.getPrice());
                if (s1 < s2)
                    return -1;
                else return 1;
            }
        });

        Data = FXCollections.observableArrayList(services);

        nameColumn.setCellValueFactory(new PropertyValueFactory<Service, String>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Service, String>("price"));
        fieldColumn.setCellValueFactory(new PropertyValueFactory<Service, String>("field"));
        table.setItems(Data);

    }

    @FXML
    private void setbuttonSortMax(ActionEvent event) throws IOException {
        Data.removeAll();
        Data = table.getItems();
        services.clear();
        table.refresh();
        services = Data;

        Collections.sort(services, new Comparator<Service>() {
            @Override
            public int compare(Service o1, Service o2) {
                float s1 = Float.parseFloat(o1.getPrice());
                float s2 = Float.parseFloat(o2.getPrice());
                if (s2 < s1)
                    return -1;
                else return 2;
            }
        });

        Data = FXCollections.observableArrayList(services);

        nameColumn.setCellValueFactory(new PropertyValueFactory<Service, String>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Service, String>("price"));
        fieldColumn.setCellValueFactory(new PropertyValueFactory<Service, String>("field"));
        table.setItems(Data);
    }


}
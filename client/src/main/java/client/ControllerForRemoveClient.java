package client;

import admin.ControllerAdmin;
import classes.Client;
import classes.Master;
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

public class ControllerForRemoveClient extends NetworkConnection {
    private ObservableList<Client> Data = FXCollections.observableArrayList();
    private List<Client> clients = new ArrayList<Client>();
    @FXML
    private TableView<Client> table;
    @FXML
    private TableColumn<Client, String> firstnameColumn;
    @FXML
    private TableColumn<Client, String> nameColumn;
    @FXML
    private TableColumn<Client, String> masterNameColumn;
    @FXML
    private TableColumn<Client, String> serviceColumn;
    @FXML
    private TableColumn<Client, String> dateColumn;
    @FXML
    private TableColumn<Client, Integer> phoneColumn;
    @FXML
    private Button buttonBack;
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

       // idColumn.setCellValueFactory(new PropertyValueFactory<Client, Integer>("id"));
        firstnameColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("surname"));
        masterNameColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("masterName"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("name"));
        serviceColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("service"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("date"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<Client, Integer>("phone"));
        table.setItems(Data);
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
        Client client = new Client(clickedRow.getId(), "removeClient");
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
            alert.setTitle("Удаление клиента");
            alert.setHeaderText("Клиент удален успешно");
            alert.showAndWait();
            initialize();

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Удаление клиента");
            alert.setHeaderText("Клиент не удален");
            alert.showAndWait();
        }
    }
}

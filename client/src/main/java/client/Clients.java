package client;

import admin.ControllerAdmin;
import classes.*;
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
import java.util.List;


public class Clients extends NetworkConnection {
    private ObservableList<Client> Data = FXCollections.observableArrayList();
    private List<Client> clients = new ArrayList<Client>();
    @FXML
    private Button buttonBack;
    @FXML
    private TableView<Client> table;
    @FXML
    private TableColumn<Client, String> surnameColumn;
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
    private TableColumn<Client, Integer> timeColumn;
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

        surnameColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("surname"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("Name"));
        serviceColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("Service"));
        masterNameColumn.setCellValueFactory(new PropertyValueFactory<Client,String>("MasterName"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("date"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<Client, Integer>("time"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<Client, Integer>("phone"));
        table.setItems(Data);
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

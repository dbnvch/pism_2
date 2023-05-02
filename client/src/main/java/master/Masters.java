package master;

import admin.ControllerAdmin;
import classes.Master;
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

public class Masters extends NetworkConnection {
    private ObservableList<Master> Data = FXCollections.observableArrayList();
    private List<Master> masters = new ArrayList<Master>();
    @FXML
    private Button buttonBack;
    @FXML
    private TableView<Master> table;
    @FXML
    private TableColumn<Master, Integer> idColumn;
    @FXML
    private TableColumn<Master, String> nameColumn;
    @FXML
    private TableColumn<Master, String> surnameColumn;
    @FXML
    private TableColumn<Master, Integer> experienceColumn;
    @FXML
    private TableColumn<Master, Integer> salaryColumn;
    @FXML
    private TableColumn<Master, String> fieldColumn;

    @FXML
    private void initialize() {
        List<Object> list= new ArrayList();
        Master master = new Master("allMaster");
        Object obj = (Object) master;
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
            masters.add((Master) list.get(i));
        }

        Data = FXCollections.observableArrayList(masters);

        nameColumn.setCellValueFactory(new PropertyValueFactory<Master, String>("name"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<Master, String>("surname"));
        experienceColumn.setCellValueFactory(new PropertyValueFactory<Master, Integer>("experience"));
        salaryColumn.setCellValueFactory(new PropertyValueFactory<Master, Integer>("salary"));
        fieldColumn.setCellValueFactory(new PropertyValueFactory<Master, String>("field"));
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

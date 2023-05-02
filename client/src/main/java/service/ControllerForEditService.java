package service;

import admin.ControllerAdmin;
import classes.Person;
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
import java.util.Collection;
import java.util.List;

public class ControllerForEditService extends NetworkConnection {
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
    private TextField text_name;

    @FXML
    private TextField text_price;
    @FXML
    private ComboBox text_field;

    @FXML
    private void initialize() {
        ObservableList<String> FieldOptions = FXCollections.observableArrayList("Волосы", "Ногти", "Перманент", "Ресницы", "Брови");
        text_field.setItems(FieldOptions);
        table.refresh();
        Data.removeAll();
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
        setText();
    }

    Service clickedRow = new Service();

    @FXML
    private void setText() {
        table.setRowFactory(tv -> {
            TableRow<Service> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (! row.isEmpty() && event.getButton()== MouseButton.PRIMARY
                        && event.getClickCount() == 1) {

                    clickedRow = row.getItem();
                    text_name.setText(clickedRow.getName());
                  //  text_time.setText(clickedRow.getTime());
                    text_price.setText(clickedRow.getPrice());
                    text_field.setValue(clickedRow.getField());
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
    private void setButtonEdit(ActionEvent event) {

        if(ValidEnter()){
            AlertEnter();

        }else{

            String name = text_name.getText();
            String price = text_price.getText();
            String field =String.valueOf(text_field.getValue());
            text_name.setText("");
            text_price.setText("");
            text_field.setValue("");

            Service service = new Service(clickedRow.getId(), name,  price, field, "/fxml/editService");
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
            alert.setTitle("Редактирование услуги");
            alert.setHeaderText("Услуга отредактирована успешно");
            alert.showAndWait();
            initialize();

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Редактирование услуги");
            alert.setHeaderText("Услуга не отредактирована");
            alert.showAndWait();
        }
    }
//}
    }
    public boolean ValidEnter(){
        if(text_name.getText().matches("[\\d]+")|
                text_name.getText().matches("[\\s]+"))
            return true;
        else return false;
    }
    public void AlertEnter(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка!");
        alert.setHeaderText("Введите Название процедуры корректно");
        alert.setContentText("Эти поля не должны содержать цифры!");
        alert.showAndWait();
    }

    public boolean ValidPrice(){
        float price=0;
        try {
            price= Float.parseFloat(text_price.getText());
        }catch (NumberFormatException nfe){return  true;}
        if (price>0)
            return  false;
        else  return  true;

    }
    public void AlertPrice(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка!");
        alert.setHeaderText("Введите Зарплату и Стаж корректно");
        alert.setContentText("Это поле должно содержать цифры и быть меньше нуля!");
        alert.showAndWait();
    }
}

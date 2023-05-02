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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.NetworkConnection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ControllerForAddService extends NetworkConnection {
    @FXML
    private Button buttonOk;
    @FXML
    private Button buttonBack;
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
    }
    @FXML
    private Service setInf() {
        int flag = 1;
        String name = null;
        String price = null;
        String field = null;
        while(flag == 1) {
             name = text_name.getText();
             price = text_price.getText();
            try {
                int _price = Integer.parseInt(price);
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Добавление услуги");
                alert.setHeaderText("Ошибка ввода");
                alert.showAndWait();
             //   text_time.setText("");
                text_price.setText("");
            }
             field =String.valueOf(text_field.getValue());
            flag = 0;
        }

        Service service = new Service(name, price,  field);
        return service;
    }

    @FXML
    private void setButtonOK(ActionEvent event) throws IOException {
        Service service = setInf();
        if(ValidEnter()){
            AlertEnter();
            text_name.clear();
        }else{if(ValidPrice()){
            AlertPrice();
            text_price.clear();
        }else{
        if(sendToServer(service)) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Добавление услуги");
            alert.setHeaderText("Услуга добавлена успешно");
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
        }}}
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

    public boolean sendToServer(Service obj){

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
        //float salary=
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

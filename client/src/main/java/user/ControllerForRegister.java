package user;

import admin.ControllerAdmin;
import classes.Client;
import classes.Master;
import classes.Service;
import classes.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import sample.*;

public class ControllerForRegister extends NetworkConnection {

    @FXML
    private Button buttonOk;

    @FXML
    private TextField text_name;

    @FXML
    private TextField text_phone;

    @FXML
    private Button buttonBack;

    @FXML
    private TextField text_surname;

    @FXML
    private TextField text_login;

    @FXML
    private TextField text_password;


    private User setInf() {
        String name = text_name.getText();
        String surname = text_surname.getText();
        String phone=text_phone.getText();
        String login=text_login.getText();
        String password=text_password.getText();
        User user = new User(surname, name, phone, login, password);
        return user;
    }

    @FXML
    void setButtonOK(ActionEvent event) throws IOException {
        User user = setInf();
        if(ValidEnter()){
            AlertEnter();
            text_surname.clear();
            text_name.clear();
        }else{
            if(ValidPhone()){
                AlertPhone();

            }else{
        if(sendToServer(user)) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Регистрация");
            alert.setHeaderText("Вы зарегистрированы успешно");
            alert.showAndWait();

            Stage stage = (Stage) buttonOk.getScene().getWindow();
            stage.close();

            Parent root = FXMLLoader.load(Controller.class.getResource("/fxml/sample.fxml"));
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
    @FXML
    private void initialize() { }

    @FXML
    private void setbuttonBack(ActionEvent event) throws IOException {
        Stage stage = (Stage) buttonBack.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load(Controller.class.getResource("/fxml/sample.fxml"));
        Scene scene = new Scene(root);

        Stage primaryStage = new Stage();
        primaryStage.setTitle("KRASIVO.");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public boolean ValidEnter(){
        if(text_surname.getText().matches("[\\d]+")|text_name.getText().matches("[\\d]+")|
                text_surname.getText().matches("[\\s]+")|text_name.getText().matches("[\\s]+"))
            return true;
        else return false;
    }

    public void AlertEnter(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка!");
        alert.setHeaderText("Введите Фамилию и Имя корректно");
        alert.setContentText("Эти поля не должны содержать цифры!");
        alert.showAndWait();
    }
    public boolean ValidPhone(){
        if(text_phone.getText().matches("(\\+*)\\d{12}"))
            return false;
        else return true;
    }

    public void AlertPhone(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка!");
        alert.setHeaderText("Введите Телефон корректно");
        alert.setContentText("Номер должен выглядить так: +(12 цифр)!");
        alert.showAndWait();
    }
}

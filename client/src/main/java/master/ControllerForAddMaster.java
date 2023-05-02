package master;

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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ControllerForAddMaster extends NetworkConnection {
    @FXML
    private Button buttonOk;
    @FXML
    private Button buttonBack;
    @FXML
    private TextField text_name;
    @FXML
    private TextField text_surname;
    @FXML
    private TextField text_experience;
    @FXML
    private TextField text_salary;
    @FXML
    private ComboBox text_field;
    @FXML
    private void initialize() {
        ObservableList<String> FieldOptions = FXCollections.observableArrayList("Волосы", "Ногти", "Перманент", "Ресницы", "Брови");
        text_field.setItems(FieldOptions);
    }
    @FXML
    private Master setInf() {
        String name = text_name.getText();
        String surname = text_surname.getText();
       // String experience = Integer.parseInt(text_experience.getText());
       // int salary = Integer.parseInt(text_salary.getText());
      // float salary=Float.parseFloat(text_salary.getText();
        String experience=text_experience.getText();
        String salary=text_salary.getText();
        String field =String.valueOf(text_field.getValue());
        Master master = new Master(name, surname, experience, salary, field);
        return master;
    }

    @FXML
    private void setButtonOK(ActionEvent event) throws IOException {
        Master master = setInf();
        if(ValidEnter()){
            AlertEnter();
            text_surname.clear();
            text_name.clear();
        }else{
            if(ValidSalary()){
                AlertSalary();
                text_experience.clear();;
                text_salary.clear();
                System.out.println("error");
            }else{
        if(sendToServer(master)) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Добавление мастера");
            alert.setHeaderText("Мастер добавлен успешно");
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
    public boolean ValidEnter(){
        if(text_surname.getText().matches("[\\d]+")|text_name.getText().matches("[\\d]+")|
                text_surname.getText().matches("[\\s]+")|text_name.getText().matches("[\\s]+"))
            return true;
        else return false;
    }
    public boolean ValidSalary(){
        //float salary=
        float salary=0,experience=0;
        try {
          salary= Float.parseFloat(text_salary.getText());
          experience=Integer.parseInt(text_experience.getText());
        }catch (NumberFormatException nfe){return  true;}
        if (salary>0||experience>0)
        return  false;
        else  return  true;

    }
    public void AlertEnter(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка!");
        alert.setHeaderText("Введите Фамилию и Имя корректно");
        alert.setContentText("Эти поля не должны содержать цифры!");
        alert.showAndWait();
    }
    public void AlertSalary(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка!");
        alert.setHeaderText("Введите Зарплату и Стаж корректно");
        alert.setContentText("Это поле должно содержать цифры и быть меньше нуля!");
        alert.showAndWait();
    }
}

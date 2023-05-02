package master;

import admin.ControllerAdmin;
import classes.Master;
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

public class ControllerForEditMaster extends NetworkConnection {
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
        Data.clear();
        masters.clear();
        table.refresh();

        List<Object> list= new ArrayList();
        Master service = new Master("allMaster");
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
            masters.add((Master) list.get(i));
        }

        Data = FXCollections.observableArrayList(masters);

        nameColumn.setCellValueFactory(new PropertyValueFactory<Master, String>("name"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<Master, String>("surname"));
        experienceColumn.setCellValueFactory(new PropertyValueFactory<Master, Integer>("experience"));
        salaryColumn.setCellValueFactory(new PropertyValueFactory<Master, Integer>("salary"));
        fieldColumn.setCellValueFactory(new PropertyValueFactory<Master, String>("field"));
        table.setItems(Data);
        setText();
    }

    Master clickedRow = new Master();

    @FXML
    private void setText() {
        table.setRowFactory(tv -> {
            TableRow<Master> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (! row.isEmpty() && event.getButton()== MouseButton.PRIMARY
                        && event.getClickCount() == 1) {
                    clickedRow = row.getItem();
                    text_name.setText(clickedRow.getName());
                    text_surname.setText(clickedRow.getSurname());
                    text_experience.setText(""+clickedRow.getExperience()+"");
                    text_salary.setText(""+clickedRow.getSalary()+"");
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
        String name = text_name.getText();
        String surname = text_surname.getText();
        //int experience = Integer.parseInt(text_experience.getText());
       // int salary = Integer.parseInt(text_salary.getText());
        String experience=text_experience.getText();
        String salary=text_salary.getText();
        String field =String.valueOf(text_field.getValue());
        text_name.setText("");
        text_surname.setText("");
        text_experience.setText("");
        text_salary.setText("");
        text_field.setValue("");
        Master master = new Master(clickedRow.getId(), name, surname, experience, salary, field, "editMaster");
        if(ValidEnter()){
            AlertEnter();
            text_surname.clear();
            text_name.clear();
        }else{
            if(ValidSalary()){
                AlertSalary();
                System.out.println("error");
            }else{
        List<Object> res = new ArrayList<Object>();
        try {
            res = (send(master));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(Boolean.parseBoolean(res.get(0).toString())){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Редактирование мастера");
            alert.setHeaderText("Мастер отредактирован успешно");
            alert.showAndWait();
            initialize();

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Редактирование мастера");
            alert.setHeaderText("Мастер не отредактирован");
            alert.showAndWait();
        }}
    }
    }
    public boolean ValidEnter(){
        if(text_surname.getText().matches("[\\d]+")|text_name.getText().matches("[\\d]+")|
                text_surname.getText().matches("[\\s]+")|text_name.getText().matches("[\\s]+"))
            return true;
        else return false;
    }
    public boolean ValidSalary(){
        float salary=0,experience=0;
        try {
            salary= Float.parseFloat(text_salary.getText());
            System.out.println("SALARY "+salary);
            experience=Integer.parseInt(text_experience.getText());
        }catch (NumberFormatException nfe){System.out.println("ОШИБКА");return  false;}
            return  true;
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

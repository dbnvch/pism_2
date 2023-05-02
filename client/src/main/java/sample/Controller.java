package sample;


import admin.ControllerAdmin;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import classes.*;
import user.ControllerForAddUser;
import user.ControllerForRegister;
import user.ControllerUser;

import static java.util.Objects.nonNull;

public class Controller extends NetworkConnection implements Serializable {

    public static final int DEFAULT_PORT = 8181;

    public static String logUser;
    @FXML
    private Button btn_logIn;
    @FXML
    private Button btn_signIn;
    @FXML
    private Button buttonPort;
    @FXML
    private TextField text_login;
    @FXML
    private TextField text_password;
    @FXML
    private TextField text_port;
    Person person;

    public static NetworkConnection connection=null;

    public void port(){
//        btn_logIn.setDisable(false);
        btn_signIn.setDisable(false);
        connection = new NetworkConnection();
        connection.startConnection(DEFAULT_PORT);
//        btn_logIn.setDisable(false);
//        btn_signIn.setDisable(false);
    }

    @FXML
    private Object setInf() {
        String login = text_login.getText();
        String password = text_password.getText();
        person = new Person(login, password);
        System.out.println(person.login+" "+person.password);
        return  person;
    }

    @FXML
    private void onClose() throws IOException {
        if(nonNull(coos) && nonNull(cois) && nonNull(clientSocket)){
            cois.close();
            coos.close();
            clientSocket.close();
        }

        System.exit(1);
        System.out.println("Завершение приложения");
    }

    @FXML
    private void logIn(ActionEvent event) throws IOException {
            Object obj = setInf();
            System.out.println(obj);
        if(sendToServer(obj)) {
            if (text_login.getText().equals("admin")) {
                Stage stage = (Stage) btn_logIn.getScene().getWindow();
                stage.close();

                Parent root = FXMLLoader.load(ControllerAdmin.class.getResource("/fxml/admin.fxml"));
                Scene scene = new Scene(root);

                Stage primaryStage = new Stage();
                primaryStage.setTitle("Aдминистратор");
                primaryStage.setScene(scene);
                primaryStage.show();
            } else {
                //String log  = person.getLogin();
                logUser=person.getLogin();
                ControllerUser inf = new ControllerUser();
                inf.memoryLogin(logUser);

                Stage stage = (Stage) btn_logIn.getScene().getWindow();
                stage.close();

                Parent root = FXMLLoader.load(ControllerForAddUser.class.getResource("/fxml/addNewUser.fxml"));
                Scene scene = new Scene(root);

                Stage primaryStage = new Stage();
                primaryStage.setTitle("Пользователь");
                primaryStage.setScene(scene);
                primaryStage.show();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Ошибка входа");
            alert.setContentText("Проверьте правильность введенных данных");
            alert.showAndWait();
        }
    }


    @FXML
    private void signIn(ActionEvent event) throws IOException {
        Stage stage = (Stage) btn_signIn.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load(ControllerForRegister.class.getResource("/fxml/registration.fxml"));
        Scene scene = new Scene(root);

        Stage primaryStage = new Stage();
        primaryStage.setTitle("Регистрация");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public boolean sendToServer(Object obj){
        List<Object> res = new ArrayList<Object>();
        try {
            res = (send(obj));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Boolean.parseBoolean(res.get(0).toString());
    }
}

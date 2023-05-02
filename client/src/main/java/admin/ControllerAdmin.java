package admin;

import classes.Client;
import classes.Shedule;
import client.Clients;
import client.ControllerForAddClient;
import client.ControllerForRemoveClient;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javafx.util.Duration;
import master.*;
import sample.*;
import service.*;
import client.*;
import shedule.ControllerForAddShedule;
import shedule.ControllerForEditShedule;
import shedule.ControllerForRemoveShedule;
import shedule.Shedules;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ControllerAdmin extends NetworkConnection {
    private List<Client> services = new ArrayList();
//    final NumberAxis xAxis = new NumberAxis();
//    final CategoryAxis yAxis = new CategoryAxis();
    private List<String> servicesName = new ArrayList();

    @FXML
    private Button buttonBack;

    @FXML
    private Button buttonAddMaster;
    @FXML
    private Button buttonRemoveMaster;
    @FXML
    private Button buttonEditMaster;
    @FXML
    private Button buttonShowMaster;

    @FXML
    private Button buttonAddService;
    @FXML
    private Button buttonShowService;
    @FXML
    private Button buttonEditService;
    @FXML
    private Button buttonRemoveService;

    @FXML
    private Button buttonAddClient;
    @FXML
    private Button buttonShowClient;
    @FXML
    private Button buttonEditClient;
    @FXML
    private Button buttonRemoveClient;

    @FXML
    private Button buttonAddShedule;
    @FXML
    private Button buttonRemoveShedule;
    @FXML
    private Button buttonEditShedule;
    @FXML
    private Button buttonShowShedule;

    @FXML
    private void setbuttonCreateReport(ActionEvent event) throws IOException {
        Client client = new Client("getReport");
        List<Object> res = new ArrayList<Object>();

        try {
            res = (send(client));
            System.out.println(res.get(0).toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(Boolean.parseBoolean(res.get(0).toString())){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Создание отчета");
            alert.setHeaderText("Отчет создан и сохранен");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Создание отчета");
            alert.setHeaderText("Ошибка создания отчета");
            alert.showAndWait();
        }
    }
    @FXML
    private void back(ActionEvent event) throws IOException {
        Stage stage = (Stage) buttonBack.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load(Controller.class.getResource("/fxml/sample.fxml"));
        Scene scene = new Scene(root);

        Stage primaryStage = new Stage();
        primaryStage.setTitle("KRASVIVO.");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    @FXML
    private void setButtonDiagram(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        start(stage);
    }

    public void start(final Stage stage) {


        final NumberAxis yAxis = new NumberAxis();
        final CategoryAxis xAxis = new CategoryAxis();

        final BarChart<Number, String> barChart = new BarChart<Number, String>(yAxis,xAxis);

        xAxis.setLabel("Услуга");
        yAxis.setLabel("Количество заказов");

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
            services.add((Client) list.get(i));
        }

        for (int i = 0; i < services.size(); i++) {
            servicesName.add(services.get(i).getService());
        }
        int[] count = new int[servicesName.size()];
        for (int i = 0; i < servicesName.size(); i++) {
            for (int j = 0; j < servicesName.size(); j++) {

                if (servicesName.get(i).equals(servicesName.get(j))) {
                    count[i]++;
                }
            }
            count[i] = count[i];
        }

        XYChart.Series series = new XYChart.Series();
        series.setName("Количество");
        for (int i = 0; i < servicesName.size(); i++) {
            series.getData().add(new XYChart.Data(count[i], servicesName.get(i)));
        }



        Scene scene = new Scene(barChart, 400, 400);
        barChart.getData().addAll(series);
        stage.setScene(scene);
        stage.show();
    }

    //Работа с мастерами
    @FXML
    private void setButtonAddMaster(ActionEvent event) throws IOException {
        Stage stage = (Stage) buttonAddMaster.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load(ControllerForAddMaster.class.getResource("/fxml/addMaster.fxml"));
        Scene scene = new Scene(root);

        Stage primaryStage = new Stage();
        primaryStage.setTitle("Работа с мастерами");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @FXML
    private void setButtonEditMaster(ActionEvent event) throws IOException {
        Stage stage = (Stage) buttonEditMaster.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load(ControllerForEditMaster.class.getResource("/fxml/editMaster.fxml"));
        Scene scene = new Scene(root);

        Stage primaryStage = new Stage();
        primaryStage.setTitle("Работа с мастерами");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @FXML
    private void setButtonRemoveMaster(ActionEvent event) throws IOException {
        Stage stage = (Stage) buttonRemoveMaster.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load(ControllerForRemoveMaster.class.getResource("/fxml/removeMaster.fxml"));
        Scene scene = new Scene(root);

        Stage primaryStage = new Stage();
        primaryStage.setTitle("Работа с мастерами");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @FXML
    private void setButtonShowMaster(ActionEvent event) throws IOException {
        Masters masters;
        Stage stage = (Stage) buttonShowMaster.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load(Masters.class.getResource("/fxml/masters.fxml"));
        Scene scene = new Scene(root);

        Stage primaryStage = new Stage();
        primaryStage.setTitle("Работа с мастерами");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    //Работа с услугами
    @FXML
    private void setButtonAddService(ActionEvent event) throws IOException {
        Stage stage = (Stage) buttonAddService.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load(ControllerForAddService.class.getResource("/fxml/addService.fxml"));
        Scene scene = new Scene(root);

        Stage primaryStage = new Stage();
        primaryStage.setTitle("Работа с услугами");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @FXML
    private void setButtonShowService(ActionEvent event) throws IOException {
        Stage stage = (Stage) buttonShowService.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load(Services.class.getResource("/fxml/services.fxml"));
        Scene scene = new Scene(root);

        Stage primaryStage = new Stage();
        primaryStage.setTitle("Работа с услугами");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @FXML
    private void setButtonEditService(ActionEvent event) throws IOException {
        Stage stage = (Stage) buttonEditService.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load(ControllerForEditService.class.getResource("/fxml/editService.fxml"));
        Scene scene = new Scene(root);

        Stage primaryStage = new Stage();
        primaryStage.setTitle("Работа с услугами");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @FXML
    private void setButtonRemoveService(ActionEvent event) throws IOException {
        Stage stage = (Stage) buttonRemoveService.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load(ControllerForRemoveService.class.getResource("/fxml/removeService.fxml"));
        Scene scene = new Scene(root);

        Stage primaryStage = new Stage();
        primaryStage.setTitle("Работа с услугами");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    //Работа с клиентами
    @FXML
    private void setButtonAddClient(ActionEvent event) throws IOException {
        Stage stage = (Stage) buttonAddClient.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load(ControllerForAddClient.class.getResource("/fxml/addNewcClient.fxml"));
        Scene scene = new Scene(root);

        Stage primaryStage = new Stage();
        primaryStage.setTitle("Работа с клиентами");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @FXML
    private void setButtonShowClient(ActionEvent event) throws IOException {
        Stage stage = (Stage) buttonShowClient.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load(Clients.class.getResource("/fxml/clients.fxml"));
        Scene scene = new Scene(root);

        Stage primaryStage = new Stage();
        primaryStage.setTitle("Работа с клиентами");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @FXML
    private void setButtonEditClient(ActionEvent event) throws IOException {
        Stage stage = (Stage) buttonEditClient.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load(ControllerForEditClient.class.getResource("/fxml/editClient.fxml"));
        Scene scene = new Scene(root);

        Stage primaryStage = new Stage();
        primaryStage.setTitle("Работа с клиентами");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @FXML
    private void setButtonRemoveClient(ActionEvent event) throws IOException {
        Stage stage = (Stage) buttonRemoveClient.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load(ControllerForRemoveClient.class.getResource("/fxml/removeClient.fxml"));
        Scene scene = new Scene(root);

        Stage primaryStage = new Stage();
        primaryStage.setTitle("Работа с клиентами");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    //Работа с расписанием
    @FXML
    private void setButtonAddShedule(ActionEvent event) throws IOException {
        Stage stage = (Stage) buttonAddShedule.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load(ControllerForAddShedule.class.getResource("/fxml/addShedule.fxml"));
        Scene scene = new Scene(root);

        Stage primaryStage = new Stage();
        primaryStage.setTitle("Работа с расписанием");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @FXML
    private void setButtonShowShedule(ActionEvent event) throws IOException {
        Stage stage = (Stage) buttonShowShedule.getScene().getWindow();
        stage.close();
System.out.println("show start");
        Parent root = FXMLLoader.load(Shedules.class.getResource("/fxml/shedule.fxml"));
        Scene scene = new Scene(root);
        System.out.println("show ok");
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Работа с расписанием");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @FXML
    private void setButtonEditShedule(ActionEvent event) throws IOException {
        Stage stage = (Stage) buttonEditShedule.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load(ControllerForEditShedule.class.getResource("/fxml/editShedule.fxml"));
        Scene scene = new Scene(root);

        Stage primaryStage = new Stage();
        primaryStage.setTitle("Работа с расписанием");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @FXML
    private void setButtonRemoveShedule(ActionEvent event) throws IOException {
        Stage stage = (Stage) buttonRemoveShedule.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load(ControllerForRemoveShedule.class.getResource("/fxml/removeShedule.fxml"));
        Scene scene = new Scene(root);

        Stage primaryStage = new Stage();
        primaryStage.setTitle("Работа с расписанием");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

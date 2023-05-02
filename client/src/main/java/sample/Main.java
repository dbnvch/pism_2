package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {


    public NetworkConnection connection;

    @Override
    public void start(Stage primaryStage) throws Exception {


        Parent root = FXMLLoader.load(getClass().getResource("/fxml/sample.fxml"));
        primaryStage.setTitle("KRASIVO.");
        primaryStage.setScene(new Scene(root, 500, 500));
        primaryStage.show();

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                try {
                    connection.closeConnection();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Platform.exit();
                System.exit(0);
            }
        });

        connection = new NetworkConnection();
        //connection.startConnection();
    }


    public static void main(String[] args) {
            launch(args);
    }
}

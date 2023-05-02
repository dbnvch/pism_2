package sample;

import javafx.scene.control.Alert;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class NetworkConnection {

    public static Socket clientSocket;
    public static ObjectOutputStream coos;
    public static ObjectInputStream cois;


    public void startConnection(int port) {

        System.out.println("server connecting....");
        try {

            System.out.println("port:" + Controller.connection);
            clientSocket = new Socket("127.0.0.1", port);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Ошибка подключения");
            alert.setContentText("Проверьте работу сервера");
            alert.showAndWait();
            e.printStackTrace();
        }
        System.out.println("connection established....");
        try {
            coos = new ObjectOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            cois = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static List<Object> send(Object obj) throws Exception {
        coos.writeObject(obj);
        List<Object> list = (List<Object>) cois.readObject();
        return list;
    }

    public void closeConnection() throws Exception {
        cois.close();
        coos.close();
        clientSocket.close();
    }
}

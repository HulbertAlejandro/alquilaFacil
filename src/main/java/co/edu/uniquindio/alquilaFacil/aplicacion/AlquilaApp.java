package co.edu.uniquindio.alquilaFacil.aplicacion;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AlquilaApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(AlquilaApp.class.getResource("/login.fxml"));
        Parent parent = loader.load();

        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setTitle("Alquila Facil");
        stage.show();


    }

    public static void main(String[] args) {
        launch(AlquilaApp.class, args);
    }
}

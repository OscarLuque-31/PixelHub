package application;

import java.awt.Image;

import controllers.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class mainApp extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
			//Cargo la vista
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Login.fxml"));

			//Carga la escena
			Scene scene = new Scene(loader.load());

			//Carga la hoja de estilos
			scene.getStylesheets().add(getClass().getResource("/styles/styleLogin.css").toExternalForm());
			
			primaryStage.setMaximized(true);
			primaryStage.setFullScreen(true);
			// Configurar la escena y mostrar
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}

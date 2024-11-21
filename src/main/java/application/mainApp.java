package application;

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
			// Cargo la vista
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Login.fxml"));

	        Scene scene = new Scene(loader.load());

	        // Agregar estilos (si los tienes)
	        scene.getStylesheets().add(getClass().getResource("/styles/styleLogin.css").toExternalForm());

	        primaryStage.setMaximized(true);
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

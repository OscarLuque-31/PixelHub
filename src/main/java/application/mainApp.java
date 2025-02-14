package application;

import controllers.LoginController;
import javafx.application.Application;
import javafx.stage.Stage;

public class mainApp extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
			// Carga el logincontroller y lanza la pantalla
			LoginController loginController = new LoginController();
			loginController.cargarFXML(primaryStage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}	
}

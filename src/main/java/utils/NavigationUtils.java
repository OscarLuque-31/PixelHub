package utils;

import controllers.BibliotecaController;
import controllers.LoginController;
import controllers.RegistroController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.Usuario;

public class NavigationUtils {

	/**
	 * Método que administra la navegación entre pantallas
	 * @param stage - stage de la pantalla
	 * @param fxmlPath - path del fichero fxml
	 * @param title - titulo de la pantalla
	 */
	public static void navigateTo(Stage stage, String fxmlPath) {
		try {

			// Carga el nuevo contenido FXML
			FXMLLoader loader = new FXMLLoader(NavigationUtils.class.getResource(fxmlPath));
			Parent newRoot = loader.load();
			Object controller = loader.getController();

			// Asigna el stage al controlador
			if (controller instanceof BibliotecaController) {
				((BibliotecaController) controller).setStage(stage);
			} else if (controller instanceof LoginController) {
				((LoginController) controller).setStage(stage);
			} else if (controller instanceof RegistroController) {
				((RegistroController) controller).setStage(stage);
			} 

			prepararStage(stage, newRoot);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * Método que administra la navegación entre login y biblioteca
	 * @param stage - stage de la pantalla
	 * @param fxmlPath - path del fichero fxml
	 * @param title - titulo de la pantalla
	 */
	public static void navigateToBibliotecaWithUser(Stage stage, String fxmlPath, String title, Usuario usuario) {
		try {
			

			// Cargar el nuevo contenido FXML
			FXMLLoader loader = new FXMLLoader(NavigationUtils.class.getResource(fxmlPath));
			Parent newRoot = loader.load();
			Object controller = loader.getController();

			// Configurar el controlador con el usuario, si aplica
			if (controller instanceof BibliotecaController) {
				((BibliotecaController) controller).setUsuario(usuario);
				((BibliotecaController) controller).setStage(stage);
			}

			prepararStage(stage, newRoot);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	/**
	 * Método que prepara todo el stage
	 * @param stage
	 * @param wasMaximized
	 * @param scene
	 * @param title
	 */
	private static void prepararStage(Stage stage, Parent newRoot) {

		// Cambia la raíz de la escena actual
		Scene sceneActual = stage.getScene();
		if (sceneActual != null) {
			sceneActual.setRoot(newRoot);
		} else {
			// Si no hay escena actual, crea una nueva
			stage.setScene(new Scene(newRoot));
		}

		// Restaurar el estado de maximización
		stage.setMaximized(true);

		stage.show();
	}
}

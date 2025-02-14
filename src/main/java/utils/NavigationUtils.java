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
	 * Cambia la pantalla actual por una nueva vista FXML.
	 * Carga el archivo FXML y obtiene su controlador.
	 *
	 * @param stage    Escenario principal donde se mostrará la nueva vista.
	 * @param fxmlPath Ruta del archivo FXML de la nueva vista.
	 */

	public static void navigateTo(Stage stage, String fxmlPath) {
		try {
			FXMLLoader loader = new FXMLLoader(NavigationUtils.class.getResource(fxmlPath));
			Parent newRoot = loader.load();
			Object controller = loader.getController();

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
	 * Cambia la pantalla actual a la vista de la biblioteca pasando la información del usuario.
	 *
	 * @param stage    Escenario principal donde se mostrará la nueva vista.
	 * @param fxmlPath Ruta del archivo FXML de la biblioteca.
	 * @param usuario  Usuario con la información del usuario autenticado.
	 */

	public static void navigateToBibliotecaWithUser(Stage stage, String fxmlPath, String title, Usuario usuario) {
		try {
			FXMLLoader loader = new FXMLLoader(NavigationUtils.class.getResource(fxmlPath));
			Parent newRoot = loader.load();
			Object controller = loader.getController();

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
	 * Configura el Stage con la nueva vista y lo muestra.
	 * Si ya existe una escena cambia su raíz, de lo contrario crea una nueva escena.
	 *
	 * @param stage   Escenario principal donde se actualizará la vista.
	 * @param newRoot Nuevo contenido de la vista.
	 */

	private static void prepararStage(Stage stage, Parent newRoot) {
		Scene sceneActual = stage.getScene();
		if (sceneActual != null) {
			sceneActual.setRoot(newRoot);
		} else {
			stage.setScene(new Scene(newRoot));
		}
		stage.setMaximized(true);

		stage.show();
	}
}

package utils;

import controllers.BibliotecaController;
import controllers.CodigoEmailController;
import controllers.LoginController;
import controllers.RecuperarContrasenaController;
import controllers.RegistroController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class NavigationUtils {

	/**
	 * Método que administra la navegación entre pantallas
	 * @param stage - stage de la pantalla
	 * @param fxmlPath - path del fichero fxml
	 * @param title - titulo de la pantalla
	 */
    public static void navigateTo(Stage stage, String fxmlPath, String title) {
        try {

            // Cargar el FXML
            FXMLLoader loader = new FXMLLoader(NavigationUtils.class.getResource(fxmlPath));
            Scene scene = new Scene(loader.load());
            Object controller = loader.getController();

            // Si el controlador es de la ventana de Biblioteca o Login, establecemos el Stage
            if (controller instanceof BibliotecaController) {
                ((BibliotecaController) controller).setStage(stage);
            } else if (controller instanceof LoginController) {
                ((LoginController) controller).setStage(stage);
            } else if (controller instanceof RegistroController) {
                ((RegistroController) controller).setStage(stage);
            } else if (controller instanceof RecuperarContrasenaController) {
                ((RecuperarContrasenaController) controller).setStage(stage);
            } else if (controller instanceof CodigoEmailController) {
                ((CodigoEmailController) controller).setStage(stage);
            }

            // Establecer la nueva escena
            stage.setScene(scene);
            stage.setTitle(title);
            
            // Verificar si la ventana está maximizada
            boolean wasMaximized = stage.isMaximized();

            // Restaurar el estado maximizado
            if (wasMaximized) {
                stage.setMaximized(true);
            } else {
                stage.setMaximized(false);
            }

            // Mostrar la ventana
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

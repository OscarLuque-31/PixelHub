package utils;

import controllers.BibliotecaController;
import controllers.LoginController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class NavigationUtils {

    public static void navigateTo(Stage stage, String fxmlPath, String title) {
        try {
            // Verificar si la ventana está maximizada
            boolean wasMaximized = stage.isMaximized();

            // Cargar el FXML
            FXMLLoader loader = new FXMLLoader(NavigationUtils.class.getResource(fxmlPath));
            Scene scene = new Scene(loader.load());
            Object controller = loader.getController();

            // Si el controlador es de la ventana de Biblioteca o Login, establecemos el Stage
            if (controller instanceof BibliotecaController) {
                ((BibliotecaController) controller).setStage(stage);
            } else if (controller instanceof LoginController) {
                ((LoginController) controller).setStage(stage);
            }

            // Establecer la nueva escena
            stage.setScene(scene);
            stage.setTitle(title);

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

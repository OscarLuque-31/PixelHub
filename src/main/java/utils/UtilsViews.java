package utils;

import java.io.IOException;
import java.util.List;

import controllers.BibliotecaController;
import controllers.GameItemCuadriculaController;
import controllers.GameItemListaController;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import models.Game;

public class UtilsViews {

	//Variables para el desplazamiento 
	private static double xOffset = 0;
	private static double yOffset = 0;

	//Variable para saber si la pantalla esta maximizada
	private static boolean isMaximized = false;



	/**
	 * Método que hace hover en un Text en javaFX
	 * @param text
	 * @param hexColorOnHover
	 * @param hexColorOnExit
	 */
	public static void hoverEffectText(Text text, String hexColorOnHover, String hexColorOnExit) {

		//Convierte los códigos hexadecimales a objetos Color
		Color colorOnHover = Color.web(hexColorOnHover);
		Color colorOnExit = Color.web(hexColorOnExit);

		//Cambia el color cuando el mouse pasa sobre el Text
		text.setOnMouseEntered((MouseEvent event) -> {
			text.setFill(colorOnHover); 
			//Añade subrayado para identificar mejor el Text
			text.setUnderline(true);
			text.setCursor(Cursor.HAND);
		});

		//Restaura el color original cuando el mouse sale del Text
		text.setOnMouseExited((MouseEvent event) -> {
			text.setFill(colorOnExit);
			text.setUnderline(false);
			text.setCursor(Cursor.DEFAULT);
		});
	}

	/**
	 * Método que hace hover en un Button en javaFX
	 * @param Button btn
	 * @param hexColorOnHover
	 * @param hexColorOnExit
	 */
	public static void hoverEffectButton(Button btn, String hexColorOnHover, String hexColorOnExit) {
		btn.setOnMouseEntered((MouseEvent event) -> {
			btn.setStyle("-fx-background-color: " + hexColorOnHover + ";");
			btn.setCursor(Cursor.HAND); // Cambiar el cursor a mano
		});

		btn.setOnMouseExited((MouseEvent event) -> {
			btn.setStyle("-fx-background-color: " + hexColorOnExit + ";");
			btn.setCursor(Cursor.DEFAULT); // Restaurar el cursor
		});
	}


	/**
	 * Método que le añade la funcionalidad a la barra de navegación
	 * @param btnMinimizar
	 * @param btnMaximizar
	 * @param btnCerrar
	 * @param dragArea
	 * @param stage
	 */
	public static void funBtnsBar(Button btnMin, Button btnClose, Stage stage) {
		btnMin.setOnMouseClicked(event -> stage.setIconified(true));

		btnClose.setOnMouseClicked(event -> stage.close());

	}


	/**
	 * Método que muestra un dialogo de error en la aplicación
	 * @param clase
	 * @param mensaje
	 */
	public static void mostrarDialogo(Alert.AlertType alertType, Class<?> clase, String header, String mensaje) {
		// Crear el Alert
		Alert alert = new Alert(alertType);
		alert.setHeaderText(header); // Encabezado
		alert.setContentText(mensaje); // Mensaje

		// Personalizar el DialogPane
		DialogPane dialogPane = alert.getDialogPane();
		dialogPane.getStylesheets().add(clase.getResource("/styles/styleDialog.css").toExternalForm());
		dialogPane.getStyleClass().add("dialog-pane");

		// Hacer transparente el Stage
		Stage stage = (Stage) dialogPane.getScene().getWindow();
		stage.initStyle(StageStyle.TRANSPARENT); // Hace el Stage transparente

		// Ajustar el fondo del Scene para que también sea transparente
		dialogPane.getScene().setFill(Color.TRANSPARENT);

		// Configurar ícono y título (opcional)
		stage.getIcons().add(new Image(clase.getResourceAsStream("/images/logoPixelHub.png")));
		stage.setTitle(null);

		// Añadir efecto de desvanecimiento (fade)
		FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), alert.getDialogPane());
		fadeIn.setFromValue(0);
		fadeIn.setToValue(1);
		fadeIn.play();

		// Mostrar el dialogo
		alert.showAndWait();
	}

	



}

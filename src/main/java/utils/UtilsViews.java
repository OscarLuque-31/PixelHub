package utils;

import javafx.animation.FadeTransition;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class UtilsViews {

	/**
     * Aplica un efecto de hover a un Text, cambiando su color y subrayado.
     * 
     * @param text           El objeto Text al que se aplicará el efecto.
     * @param hexColorOnHover Color en formato hexadecimal al pasar el ratón por encima.
     * @param hexColorOnExit  Color en formato hexadecimal al retirar el ratón.
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
     * Aplica un efecto de hover a un Button en JavaFX, cambiando su color de fondo.
     * 
     * @param btn            El botón al que se aplicará el efecto.
     * @param hexColorOnHover Color en formato hexadecimal al pasar el ratón por encima.
     * @param hexColorOnExit  Color en formato hexadecimal al retirar el ratón.
     */
	public static void hoverEffectButton(Button btn, String hexColorOnHover, String hexColorOnExit) {
		btn.setOnMouseEntered((MouseEvent event) -> {
			btn.setStyle("-fx-background-color: " + hexColorOnHover + ";");
			btn.setCursor(Cursor.HAND);
		});

		btn.setOnMouseExited((MouseEvent event) -> {
			btn.setStyle("-fx-background-color: " + hexColorOnExit + ";");
			btn.setCursor(Cursor.DEFAULT);
		});
	}


	/**
     * Configura la funcionalidad de los botones de la barra de navegación.
     * 
     * @param btnMin   Botón para minimizar la ventana.
     * @param btnClose Botón para cerrar la ventana.
     * @param stage    La ventana Stage a la que afectan los botones.
     */
	public static void funBtnsBar(Button btnMin, Button btnClose, Stage stage) {
		btnMin.setOnMouseClicked(event -> stage.setIconified(true));

		btnClose.setOnMouseClicked(event -> stage.close());
	}


	/**
     * Muestra un cuadro de diálogo en la aplicación con un mensaje personalizado.
     * 
     * @param alertType Tipo de alerta.
     * @param clase     Clase desde donde se llama al método.
     * @param header    Encabezado del cuadro de diálogo.
     * @param mensaje   Mensaje a mostrar en el cuadro de diálogo.
     */
	public static void mostrarDialogo(Alert.AlertType alertType, Class<?> clase, String header, String mensaje) {
		Alert alert = new Alert(alertType);
		alert.setHeaderText(header);
		alert.setContentText(mensaje);

		DialogPane dialogPane = alert.getDialogPane();
		dialogPane.getStylesheets().add(clase.getResource("/styles/styleDialog.css").toExternalForm());
		dialogPane.getStyleClass().add("dialog-pane");
		
	    switch (alertType) {
	        case ERROR:
	            dialogPane.getStyleClass().add("alert-error");
	            break;
	        case WARNING:
	            dialogPane.getStyleClass().add("alert-warning");
	            break;
	        case INFORMATION:
	            dialogPane.getStyleClass().add("alert-info");
	            break;
		default:
			break;
	    }

		Stage stage = (Stage) dialogPane.getScene().getWindow();
		stage.initStyle(StageStyle.TRANSPARENT);

		dialogPane.getScene().setFill(Color.TRANSPARENT);

		stage.getIcons().add(new Image(clase.getResourceAsStream("/images/logoPixelHub.png")));
		stage.setTitle(null);

		FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), alert.getDialogPane());
		fadeIn.setFromValue(0);
		fadeIn.setToValue(1);
		fadeIn.play();

		alert.showAndWait();
	}
}

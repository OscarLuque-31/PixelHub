package utils;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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
		});

		//Restaura el color original cuando el mouse sale del Text
		text.setOnMouseExited((MouseEvent event) -> {
			text.setFill(colorOnExit);
			text.setUnderline(false);
		});
	}

	/**
	 * Método que hace hover en un Button en javaFX
	 * @param Button btn
	 * @param hexColorOnHover
	 * @param hexColorOnExit
	 */
	public static void hoverEffectButton(Button btn, String hexColorOnHover, String hexColorOnExit) {

		//Cambia el color cuando el mouse pasa sobre el Button
		btn.setOnMouseEntered((MouseEvent event) -> {
			btn.setStyle("-fx-background-color: " + hexColorOnHover + ";");            
		});

		//Restaura el color original cuando el mouse sale del Button
		btn.setOnMouseExited((MouseEvent event) -> {
			btn.setStyle("-fx-background-color: " + hexColorOnExit + ";");            
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
	public static void funBtnsBar(Button btnMinimizar, Button btnMaximizar, Button btnCerrar, HBox dragArea, Stage stage) {
		//Variables para controlar el estado del Stage
		isMaximized = false;

		//Botón minimizar
		btnMinimizar.setOnMouseClicked(event -> stage.setIconified(true));

		//Botón maximizar/restaurar
		btnMaximizar.setOnMouseClicked(event -> {
			if (isMaximized) {
				stage.setMaximized(false);
				isMaximized = false;
			} else {
				stage.setMaximized(true);
				isMaximized = true;
			}
		});

		//Botón cerrar
		btnCerrar.setOnMouseClicked(event -> stage.close());

		//Configurar el área de arrastre
		configureDrag(stage, dragArea);
	}

	/**
	 * Método que configuras el area de arratre de la ventana
	 * @param stage
	 * @param dragArea
	 */
	private static void configureDrag(Stage stage, HBox dragArea) {
		dragArea.setOnMousePressed(event -> {
			if (!stage.isMaximized()) {
				xOffset = event.getSceneX();
				yOffset = event.getSceneY();
			}
		});

		dragArea.setOnMouseDragged(event -> {
			if (!stage.isMaximized()) {
				stage.setX(event.getScreenX() - xOffset);
				stage.setY(event.getScreenY() - yOffset);
			} else {
				stage.setMaximized(false);
				isMaximized = false;
				stage.setX(event.getScreenX() - xOffset);
				stage.setY(event.getScreenY() - yOffset);
			}
		});
	}


}

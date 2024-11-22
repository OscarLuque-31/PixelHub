package utils;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class UtilsViews {



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


}

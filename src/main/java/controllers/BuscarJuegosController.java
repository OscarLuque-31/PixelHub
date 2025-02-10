package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class BuscarJuegosController implements Initializable {

	@FXML
	private ImageView imgLupa;

	@FXML
	private TextField textFieldBusqueda;

	@FXML
	private ComboBox<?> comboBoxOrdenar;

	@FXML
	private ComboBox<?> comboBoxPlataforma;

	@FXML
	private ComboBox<?> comboBoxGenero;
	
	@FXML
	private ScrollPane scrollPane;

	@FXML
	private VBox contenedorJuegos;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Inicializar imágenes
		initializeImagesBar();
		// Efectos de hover
		hoverEffect();
		// Obtener y cargar los juegos
	}

	/**
	 * Método que recopila todos los hoverEffect
	 */
	public void hoverEffect() {
		// UtilsViews.hoverEffectButton(btnMinimizar, "#2a3b47", "#192229");
		// UtilsViews.hoverEffectButton(btnMaximizar, "#2a3b47", "#192229");
		// UtilsViews.hoverEffectButton(btnCerrar, "#c63637", "#192229");
		// UtilsViews.hoverEffectButton(btnBiblioteca, "#415A6C", "#212E36");
		// UtilsViews.hoverEffectButton(btnBuscarJuegos, "#415A6C", "#212E36");
		// UtilsViews.hoverEffectButton(btnCerrarSesion, "#415A6C", "#212E36");
		// UtilsViews.hoverEffectButton(btnRecomendaciones, "#415A6C", "#212E36");
	}

	/**
	 * Método que inicializa las imagenes
	 */
	public void initializeImagesBar() {
		imgLupa.setImage(new Image(getClass().getResourceAsStream("/images/lupa.png")));
	}

}

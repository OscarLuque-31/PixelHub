package controllers;


import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class AnadirJuegoBibliotecaController implements Initializable {

	@FXML
	private GridPane gridPaneAnadir;

	@FXML
	private ImageView imageViewAnadirImagen;

	@FXML
	private ComboBox<?> dropDownPlataformas;

	@FXML
	private ComboBox<?> dropDownGenero;

	@FXML
	private TextField txtRating;

	@FXML
	private TextArea txtComentario;

	@FXML
	private Button btnAnadirJuegoCancelar;

	@FXML
	private Button btnAgregarJuego;

	@FXML
	private TextField txtTitulo;

	@FXML
	private TextArea txtDescripcion;

	@FXML
	private ImageView imagenAnterior;

	@FXML
	private ImageView siguienteImagen;

	@FXML
	private ImageView imageViewAnadirScreenshot;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initializeImagesBar();

		// Hacer que las imágenes sean clicables para subir una nueva imagen
		imageViewAnadirImagen.setOnMouseClicked(event -> subirImagen(imageViewAnadirImagen));
		imageViewAnadirScreenshot.setOnMouseClicked(event -> subirImagen(imageViewAnadirScreenshot));

		// Configurar el reescalado dinámico de las imágenes
		configurarReescalado();

		// Aplicar bordes redondeados a los ImageView
		aplicarBordesRedondeados(imageViewAnadirImagen);
		aplicarBordesRedondeados(imageViewAnadirScreenshot);
	}

	/**
	 * Método que inicializa las imágenes
	 */
	public void initializeImagesBar() {
		imagenAnterior.setImage(new Image(getClass().getResourceAsStream("/images/imagenAnterior.png")));
		siguienteImagen.setImage(new Image(getClass().getResourceAsStream("/images/siguienteImagen.png")));
		imageViewAnadirImagen.setImage(new Image(getClass().getResourceAsStream("/images/anadir_imagen.png")));
		imageViewAnadirScreenshot.setImage(new Image(getClass().getResourceAsStream("/images/anadir_screenshot.png")));
	}

	/**
	 * Método para subir una imagen y mostrarla en los ImageView
	 */
	private void subirImagen(ImageView imageView) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Seleccionar Imagen");
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg"));

		Stage stage = (Stage) gridPaneAnadir.getScene().getWindow();
		File file = fileChooser.showOpenDialog(stage);

		if (file != null) {
			Image imagen = new Image(file.toURI().toString());
			imageView.setImage(imagen);
		}
	}

	/**
	 * Configurar el reescalado dinámico de los ImageView
	 */
	private void configurarReescalado() {
		// Hacer que los ImageView se ajusten al tamaño del GridPane
		gridPaneAnadir.widthProperty().addListener((obs, oldVal, newVal) -> {
			imageViewAnadirImagen.setFitWidth(newVal.doubleValue() * 0.4);
			imageViewAnadirScreenshot.setFitWidth(newVal.doubleValue() * 0.4);
			aplicarBordesRedondeados(imageViewAnadirImagen); // Actualizar bordes redondeados
			aplicarBordesRedondeados(imageViewAnadirScreenshot); // Actualizar bordes redondeados
		});

		gridPaneAnadir.heightProperty().addListener((obs, oldVal, newVal) -> {
			imageViewAnadirImagen.setFitHeight(newVal.doubleValue() * 0.4);
			imageViewAnadirScreenshot.setFitHeight(newVal.doubleValue() * 0.4);
			aplicarBordesRedondeados(imageViewAnadirImagen); // Actualizar bordes redondeados
			aplicarBordesRedondeados(imageViewAnadirScreenshot); // Actualizar bordes redondeados
		});

		// Desactivar preserveRatio para que las imágenes ocupen todo el espacio del ImageView
		imageViewAnadirImagen.setPreserveRatio(false);
		imageViewAnadirScreenshot.setPreserveRatio(false);
	}

	/**
	 * Método para aplicar bordes redondeados a un ImageView
	 */
	private void aplicarBordesRedondeados(ImageView imageView) {
		Rectangle clip = new Rectangle();
		clip.setWidth(imageView.getFitWidth());
		clip.setHeight(imageView.getFitHeight());
		clip.setArcWidth(20); // Radio de los bordes redondeados
		clip.setArcHeight(20); // Radio de los bordes redondeados

		// Vincular el tamaño del clip al tamaño del ImageView
		imageView.fitWidthProperty().addListener((obs, oldVal, newVal) -> {
			clip.setWidth(newVal.doubleValue());
		});
		imageView.fitHeightProperty().addListener((obs, oldVal, newVal) -> {
			clip.setHeight(newVal.doubleValue());
		});

		imageView.setClip(clip);
	}
}
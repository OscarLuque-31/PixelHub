package controllers;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.hibernate.Session;

import dao.JuegosBibliotecaDaoImpl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.Capturas;
import models.JuegosBiblioteca;
import models.Plataformas;
import utils.HibernateUtil;
import utils.UtilsImages;
import utils.UtilsViews;

public class AnadirJuegoBibliotecaController implements Initializable {

	@FXML
	private GridPane gridPaneAnadir;

	@FXML
	private ImageView imageViewAnadirImagen;

	@FXML
	private ComboBox<String> dropDownPlataformas;

	@FXML
	private ComboBox<String> dropDownGenero;

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
	private CheckBox checkBoxComprado;

	@FXML
	private CheckBox checkBoxDeseado;

	@FXML
	private CheckBox checkBoxJugado;

	@FXML
	private ImageView imagenAnterior;

	@FXML
	private ImageView siguienteImagen;

	@FXML
	private ImageView imageViewAnadirScreenshot;

	// Imagen principal en bytes
	private byte[] imagenPrincipalBytes;
	// Lista de capturas en bytes
	private List<byte[]> capturasBytes = new ArrayList<>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initializeImagesBar();

		// Hace que las imágenes sean clicables para subir una nueva imagen
		imageViewAnadirImagen.setOnMouseClicked(event -> subirImagen(imageViewAnadirImagen));
		imageViewAnadirScreenshot.setOnMouseClicked(event -> subirImagen(imageViewAnadirScreenshot));

		// Configura el reescalado dinámico de las imágenes
		configurarReescalado();

		// Aplica bordes redondeados a los ImageView
		aplicarBordesRedondeados(imageViewAnadirImagen);
		aplicarBordesRedondeados(imageViewAnadirScreenshot);
		rellenarDropdowns();

		// Configura el botón para agregar el juego
		btnAgregarJuego.setOnAction(event -> agregarJuegoABaseDeDatos());
		btnAnadirJuegoCancelar.setOnAction(event -> limpiarCampos());

	}

	/**
	 * Método que rellena los dropdowns para añadir el juego a la biblioteca
	 */
	private void rellenarDropdowns() {
		dropDownPlataformas.getItems().addAll("PC", "PlayStation", "Xbox", "iOS", "Apple Macintosh", "Linux",
				"Nintendo", "Android", "Web");

		dropDownGenero.getItems().addAll("Acción", "Indie", "Aventura", "RPG", "Estrategia", "Shooter", "Casual",
				"Simulación", "Puzzle", "Arcade", "Plataformas", "Multijugador", "Carreras", "Deportes", "Lucha",
				"Familiar", "Juegos de mesa", "Educativo", "Cartas");
	}

	/**
	 * Método que inicializa las imágenes de la pantalla
	 */
	public void initializeImagesBar() {
		imagenAnterior.setImage(new Image(getClass().getResourceAsStream("/images/imagenAnterior.png")));
		siguienteImagen.setImage(new Image(getClass().getResourceAsStream("/images/siguienteImagen.png")));
		imageViewAnadirImagen.setImage(new Image(getClass().getResourceAsStream("/images/anadir_imagen.png")));
		imageViewAnadirScreenshot.setImage(new Image(getClass().getResourceAsStream("/images/anadir_screenshot.png")));
	}

	/**
	 * Método para subir una imagen y mostrarla en los ImageView
	 * 
	 * @param imageView donde se mostrará la imagen
	 */
	private void subirImagen(ImageView imageView) {
		// Instacia de fileChooser
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Seleccionar Imagen");
		// Solo se pueden meter estas extensiones
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg"));

		Stage stage = (Stage) gridPaneAnadir.getScene().getWindow();
		// Se abre el dialogo para elegir el archivo
		File file = fileChooser.showOpenDialog(stage);

		// Si no es nulo se pone en el ImageView
		if (file != null) {
			Image imagen = new Image(file.toURI().toString());
			imageView.setImage(imagen);

			// Convierte la imagen a bytes y la almacena
			byte[] bytes = UtilsImages.fileToByteArray(file);

			if (imageView == imageViewAnadirImagen) {
				// Imagen principal
				imagenPrincipalBytes = bytes;
			} else if (imageView == imageViewAnadirScreenshot) {
				// Se añade la captura a la lista de capturas
				capturasBytes.add(bytes);
			}
		}
	}

	/**
	 * Configura el reescalado dinámico de los ImageView
	 */
	private void configurarReescalado() {
		// Hacer que los ImageView se ajusten al tamaño del GridPane
		gridPaneAnadir.widthProperty().addListener((obs, oldVal, newVal) -> {
			imageViewAnadirImagen.setFitWidth(newVal.doubleValue() * 0.4);
			imageViewAnadirScreenshot.setFitWidth(newVal.doubleValue() * 0.4);
			aplicarBordesRedondeados(imageViewAnadirImagen);
			aplicarBordesRedondeados(imageViewAnadirScreenshot);
		});

		gridPaneAnadir.heightProperty().addListener((obs, oldVal, newVal) -> {
			imageViewAnadirImagen.setFitHeight(newVal.doubleValue() * 0.4);
			imageViewAnadirScreenshot.setFitHeight(newVal.doubleValue() * 0.4);
			aplicarBordesRedondeados(imageViewAnadirImagen);
			aplicarBordesRedondeados(imageViewAnadirScreenshot);
		});

		// Desactiva el preserveRatio para que las imágenes ocupen todo el espacio del
		// ImageView
		imageViewAnadirImagen.setPreserveRatio(false);
		imageViewAnadirScreenshot.setPreserveRatio(false);
	}

	/**
	 * Método para aplicar bordes redondeados a un ImageView
	 * 
	 * @param imageView
	 */
	private void aplicarBordesRedondeados(ImageView imageView) {
		Rectangle clip = new Rectangle();
		clip.setWidth(imageView.getFitWidth());
		clip.setHeight(imageView.getFitHeight());
		// Radio de los bordes redondeados
		clip.setArcWidth(20);
		clip.setArcHeight(20);

		// Vincula el tamaño del clip al tamaño del ImageView
		imageView.fitWidthProperty().addListener((obs, oldVal, newVal) -> {
			clip.setWidth(newVal.doubleValue());
		});
		imageView.fitHeightProperty().addListener((obs, oldVal, newVal) -> {
			clip.setHeight(newVal.doubleValue());
		});

		imageView.setClip(clip);
	}

	/**
	 * Método para validar los campos del formulario
	 */
	private boolean validarCampos() {
		StringBuilder errores = new StringBuilder();

		// El titulo no puede estar vacío
		if (txtTitulo.getText().isEmpty()) {
			errores.append("El título es obligatorio.\n");
		}
		// El descripción no puede estar vacío
		if (txtDescripcion.getText().isEmpty()) {
			errores.append("La descripción es obligatoria.\n");
		}
		// El rating no puede estar vacío
		if (txtRating.getText().isEmpty()) {
			errores.append("El rating es obligatorio.\n");
		} else {
			try {
				// Comprueba que sea un número y que este entre 0 y 5
				int rating = Integer.parseInt(txtRating.getText());
				if (rating < 0 || rating > 5) {
					errores.append("El rating debe estar entre 0 y 5.\n");
				}
			} catch (NumberFormatException e) {
				errores.append("El rating debe ser un número.\n");
			}
		}
		// Tiene que elegir una plataforma
		if (dropDownPlataformas.getValue() == null) {
			errores.append("Debes seleccionar una plataforma.\n");
		}
		// Tiene que elegir un genero
		if (dropDownGenero.getValue() == null) {
			errores.append("Debes seleccionar un género.\n");
		}
		// Tiene que añadir una imagen principal
		if (imagenPrincipalBytes == null) {
			errores.append("Debes seleccionar una imagen para la portada.\n");
		}

		// Valida las casuísticas de los checkboxes
		boolean comprado = checkBoxComprado.isSelected();
		boolean deseado = checkBoxDeseado.isSelected();
		boolean jugado = checkBoxJugado.isSelected();

		if (deseado && comprado && jugado) {
			errores.append("No puede estar comprado, jugado y deseado al mismo tiempo.\n");
		} else if (deseado && comprado) {
			errores.append("No puede estar comprado y deseado \nal mismo tiempo.\n");
		}

		// Muestra los errores si existen
		if (errores.length() > 0) {
			UtilsViews.mostrarDialogo(Alert.AlertType.ERROR, getClass(), "Errores en el formulario",
					errores.toString());
			return false;
		}

		return true;
	}

	/**
	 * Método para limpiar los campos
	 */
	private void limpiarCampos() {
		// Limpia los campos de texto
		txtTitulo.clear();
		txtDescripcion.clear();
		txtRating.clear();
		txtComentario.clear();

		// Restablece los ComboBox
		dropDownPlataformas.getSelectionModel().clearSelection();
		dropDownGenero.getSelectionModel().clearSelection();

		// Restablece los CheckBox
		checkBoxComprado.setSelected(false);
		checkBoxDeseado.setSelected(false);
		checkBoxJugado.setSelected(false);

		// Limpia las imágenes
		imageViewAnadirImagen.setImage(new Image(getClass().getResourceAsStream("/images/anadir_imagen.png")));
		imageViewAnadirScreenshot.setImage(new Image(getClass().getResourceAsStream("/images/anadir_screenshot.png")));

		// Limpia las listas de bytes de las imágenes
		imagenPrincipalBytes = null;
		capturasBytes.clear();
	}

	/**
	 * Método para agregar el juego a la base de datos
	 */
	private void agregarJuegoABaseDeDatos() {
		if (!validarCampos()) {
			return;
		}

		Session sesion = null;
		try {
			// Recupera la sesión
			sesion = HibernateUtil.getSession();

			// Verifica si ya existe una transacción activa antes de iniciar una nueva
			if (!sesion.getTransaction().isActive()) {
				sesion.beginTransaction();
			}

			// Crea el juego y asigna sus atributos
			JuegosBiblioteca newGame = new JuegosBiblioteca();
			newGame.setTitulo(txtTitulo.getText());
			newGame.setDescripcion(txtDescripcion.getText());
			newGame.setRating(Integer.parseInt(txtRating.getText()));
			newGame.setFechaAñadido(new java.util.Date());
			newGame.setComentario(txtComentario.getText());
			newGame.setComprado(checkBoxComprado.isSelected());
			newGame.setDeseado(checkBoxDeseado.isSelected());
			newGame.setJugado(checkBoxJugado.isSelected());
			newGame.setUsuario(BibliotecaController.getUsuario());
			newGame.setImagen(imagenPrincipalBytes);

			// Obtiene la plataforma seleccionada
			String plataformaSeleccionada = dropDownPlataformas.getValue();
			if (plataformaSeleccionada != null && !plataformaSeleccionada.isEmpty()) {
				Plataformas plataforma = new Plataformas();
				plataforma.setPlataforma(plataformaSeleccionada);
				plataforma.setJuego(newGame);

				// Asigna la lista de plataformas al juego
				List<Plataformas> plataformasList = new ArrayList<>();
				plataformasList.add(plataforma);
				newGame.setPlataformas(plataformasList);
			}

			// Crea las capturas y las asocia al juego (si existen)
			if (!capturasBytes.isEmpty()) {
				List<Capturas> capturasEntities = new ArrayList<>();
				for (byte[] capturaBytes : capturasBytes) {
					Capturas captura = new Capturas();
					captura.setCaptura(capturaBytes);
					captura.setJuego(newGame);
					capturasEntities.add(captura);
				}
				newGame.setCapturas(capturasEntities);
			}

			// Inserta el juego en la base de datos
			JuegosBibliotecaDaoImpl juegoNuevoDao = new JuegosBibliotecaDaoImpl(sesion);
			juegoNuevoDao.insert(newGame);

			// Realiza el commit
			sesion.getTransaction().commit();

			// Muestra mensaje de éxito
			UtilsViews.mostrarDialogo(Alert.AlertType.INFORMATION, getClass(), "Éxito", "Juego agregado correctamente");

			// Limpia todos los campos del formulario
			limpiarCampos();

		} catch (Exception e) {
			// Realiza rollback en caso de error
			if (sesion != null && sesion.getTransaction().isActive()) {
				sesion.getTransaction().rollback();
			}

			// Muestra mensaje de error
			UtilsViews.mostrarDialogo(Alert.AlertType.ERROR, getClass(), "Error",
					"No se pudo agregar el juego: " + e.getMessage());
			e.printStackTrace(); // Imprimir el stack trace para depuración
		} finally {
			// Cierra la sesión si es necesario
			if (sesion != null && sesion.isOpen()) {
				sesion.close();
			}
		}
	}
}
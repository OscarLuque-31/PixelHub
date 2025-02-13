package controllers;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.hibernate.Session;

import dao.JuegosBibliotecaDaoImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import models.Capturas;
import models.JuegosBiblioteca;
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

	private byte[] imagenPrincipalBytes; // Imagen principal en bytes
	private List<byte[]> capturasBytes = new ArrayList<>(); // Lista de capturas en bytes


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
		rellenarDropdowns();


		// Configurar el botón para agregar el juego
		btnAgregarJuego.setOnAction(event -> agregarJuegoABaseDeDatos());
	}

	private void rellenarDropdowns() {
		dropDownPlataformas.getItems().addAll(
				"PC", "PlayStation", "Xbox", "iOS", "Apple Macintosh", "Linux",
				"Nintendo", "Android", "Web"
				);

		// Rellenar el dropdown de géneros
		dropDownGenero.getItems().addAll(
				"Acción", "Indie", "Aventura", "RPG", "Estrategia", 
				"Shooter", "Casual", "Simulación", "Puzzle", "Arcade", 
				"Plataformas", "Multijugador", "Carreras", "Deportes", 
				"Lucha", "Familiar", "Juegos de mesa", "Educativo", "Cartas"
				);
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

	        // Convertir la imagen a bytes y almacenarla
	        byte[] bytes = UtilsImages.fileToByteArray(file);

	        if (imageView == imageViewAnadirImagen) {
	            imagenPrincipalBytes = bytes; // Imagen principal
	        } else if (imageView == imageViewAnadirScreenshot) {
	            capturasBytes.add(bytes); // Añadir captura a la lista
	        }
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

	
	/**
	 * Método para validar los campos del formulario
	 */
	private boolean validarCampos() {
		StringBuilder errores = new StringBuilder();

		// Validar campos obligatorios
		if (txtTitulo.getText().isEmpty()) {
			errores.append("El título es obligatorio.\n");
		}
		if (txtDescripcion.getText().isEmpty()) {
			errores.append("La descripción es obligatoria.\n");
		}
		if (txtRating.getText().isEmpty()) {
			errores.append("El rating es obligatorio.\n");
		} else {
			try {
				int rating = Integer.parseInt(txtRating.getText());
				if (rating < 0 || rating > 10) {
					errores.append("El rating debe estar entre 0 y 10.\n");
				}
			} catch (NumberFormatException e) {
				errores.append("El rating debe ser un número.\n");
			}
		}
		if (dropDownPlataformas.getValue() == null) {
			errores.append("Debes seleccionar una plataforma.\n");
		}
		if (dropDownGenero.getValue() == null) {
			errores.append("Debes seleccionar un género.\n");
		}
		if (imagenPrincipalBytes == null) { // Validar imagen principal
			errores.append("Debes seleccionar una imagen para la portada.\n");
		}

		// Validar las casuísticas de los checkboxes
		boolean comprado = checkBoxComprado.isSelected();
		boolean deseado = checkBoxDeseado.isSelected();
		boolean jugado = checkBoxJugado.isSelected();

		if (deseado && comprado && jugado) {
			errores.append("No puede estar comprado, jugado y deseado al mismo tiempo.\n");
		} else if (deseado && comprado) {
			errores.append("No puede estar comprado y deseado al mismo tiempo.\n");
		} else if (deseado && jugado) {
			errores.append("No puede estar jugado y deseado al mismo tiempo.\n");
		}

		// Mostrar errores si existen
		if (errores.length() > 0) {
			UtilsViews.mostrarDialogo(Alert.AlertType.ERROR, getClass(), "Errores en el formulario", errores.toString());
			return false;
		}

		return true;
	}
	
	private void limpiarCampos() {
	    // Limpiar campos de texto
	    txtTitulo.clear();
	    txtDescripcion.clear();
	    txtRating.clear();
	    txtComentario.clear();

	    // Restablecer los ComboBox
	    dropDownPlataformas.getSelectionModel().clearSelection();
	    dropDownGenero.getSelectionModel().clearSelection();

	    // Restablecer los CheckBox
	    checkBoxComprado.setSelected(false);
	    checkBoxDeseado.setSelected(false);
	    checkBoxJugado.setSelected(false);

	    // Limpiar las imágenes
	    imageViewAnadirImagen.setImage(new Image(getClass().getResourceAsStream("/images/anadir_imagen.png")));
	    imageViewAnadirScreenshot.setImage(new Image(getClass().getResourceAsStream("/images/anadir_screenshot.png")));

	    // Limpiar las listas de bytes de las imágenes
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

	    System.out.println("GUARDAR JUEGO");
	    System.out.println("-------------");

	    Session sesion = null;
	    try {
	        // Iniciar sesión
	        sesion = HibernateUtil.getSession();

	        // Verifica si ya existe una transacción activa antes de iniciar una nueva
	        if (!sesion.getTransaction().isActive()) {
	            sesion.beginTransaction();
	        }

	        // Crear el juego
	        JuegosBiblioteca newGame = new JuegosBiblioteca();
	        newGame.setTitulo(txtTitulo.getText());
	        newGame.setDescripcion(txtDescripcion.getText());
	        newGame.setRating(Integer.parseInt(txtRating.getText()));
	        newGame.setComentario(txtComentario.getText());
	        newGame.setComprado(checkBoxComprado.isSelected());
	        newGame.setDeseado(checkBoxDeseado.isSelected());
	        newGame.setJugado(checkBoxJugado.isSelected());
	        newGame.setUsuario(BibliotecaController.getUsuario()); // Asignar el usuario actual
	        newGame.setImagen(imagenPrincipalBytes); // Asignar imagen principal

	        // Crear las capturas y asociarlas al juego (si existen)
	        if (!capturasBytes.isEmpty()) {
	            List<Capturas> capturasEntities = new ArrayList<>();
	            for (byte[] capturaBytes : capturasBytes) {
	                Capturas captura = new Capturas();
	                captura.setCaptura(capturaBytes);
	                captura.setJuego(newGame); // Establecer la relación bidireccional
	                capturasEntities.add(captura);
	            }
	            newGame.setCapturas(capturasEntities); // Asignar la lista de capturas al juego
	        }

	        // Insertar el juego en la base de datos
	        JuegosBibliotecaDaoImpl juegoNuevoDao = new JuegosBibliotecaDaoImpl(sesion);
	        juegoNuevoDao.insert(newGame); // CascadeType.ALL guardará las capturas automáticamente

	        // Realizar commit
	        sesion.getTransaction().commit();

	        // Mostrar mensaje de éxito
	        UtilsViews.mostrarDialogo(Alert.AlertType.INFORMATION, getClass(), "Éxito", "Juego agregado correctamente");

	        // Limpiar todos los campos del formulario
	        limpiarCampos();
	        

	    } catch (Exception e) {
	        // Realizar rollback en caso de error
	        if (sesion != null && sesion.getTransaction().isActive()) {
	            sesion.getTransaction().rollback();
	        }

	        // Mostrar mensaje de error
	        UtilsViews.mostrarDialogo(Alert.AlertType.ERROR, getClass(), "Error", "No se pudo agregar el juego: " + e.getMessage());
	        e.printStackTrace(); // Imprimir el stack trace para depuración
	    } finally {
	        // Cerrar la sesión si es necesario
	        if (sesion != null && sesion.isOpen()) {
	            sesion.close();
	        }
	    }
	}
}
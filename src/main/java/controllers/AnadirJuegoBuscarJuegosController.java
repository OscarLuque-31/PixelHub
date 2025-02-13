package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import org.hibernate.Session;

import dao.JuegosBibliotecaDaoImpl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import models.Game;
import models.JuegosBiblioteca;
import utils.HibernateUtil;
import utils.UtilsViews;

public class AnadirJuegoBuscarJuegosController implements Initializable {

	@FXML private Label lblAnadirJuegoTitulo;
	@FXML private VBox vboxPane;
	@FXML private ImageView imagenJuego;
	@FXML private CheckBox checkBoxComprado;
	@FXML private CheckBox checkBoxDeseado;
	@FXML private CheckBox checkBoxJugado;
	@FXML private TextField txtFRating;
	@FXML private TextArea txtFComentario;
	@FXML private Button btnCancelar;
	@FXML private Button btnAgregar;


	private Stage primaryStage;  // Campo para almacenar el primaryStage

	private byte[] imagenPrincipalBytes; // Imagen principal en bytes
	private static Game game;  // Variable para almacenar el juego recibido

	// Método para recibir el objeto Game desde la vista anterior
	public static void setGame(Game juego) {
		game = juego;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		vboxPane.getStylesheets().add(getClass().getResource("/styles/styleAnadirJuegoBuscarJuego.css").toExternalForm());
		
		
		if (game != null) {
	        lblAnadirJuegoTitulo.setText(game.getName()); // Título del juego
	        aplicarBordesRedondeados(imagenJuego);
	        imagenJuego.setImage(new Image(game.getBackgroundImage())); // Imagen del juego
	    } else {
	        System.out.println("El juego es nulo o no se ha recibido correctamente.");
	    }

		// Configurar los botones
		btnAgregar.setOnAction(event -> agregarJuegoABaseDeDatos());
		cerrarVentana();
		
		 // Asegúrate de que 'primaryStage' no sea null
	    if (primaryStage != null) {
	        // Obtener el tamaño de la pantalla (puedes usar el tamaño de la ventana principal)
	        double screenWidth = primaryStage.getWidth();
	        double screenHeight = primaryStage.getHeight();

	        // Centrar la ventana secundaria en la pantalla o en la ventana principal
	        primaryStage.setX((screenWidth - primaryStage.getWidth()) / 2);
	        primaryStage.setY((screenHeight - primaryStage.getHeight()) / 2);
	    }
	}
	
	private void cerrarVentana(){
		btnCancelar.setOnAction(event -> {
			// Obtener el Stage de la ventana actual
			Stage stage = (Stage) btnCancelar.getScene().getWindow();
			stage.close(); // Cerrar la ventana
		});
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
	// Método para validar los campos
	private boolean validarCampos() {
		// Validar Rating (debe ser un número entero positivo)
		if (txtFRating.getText().isEmpty()) {
			UtilsViews.mostrarDialogo(Alert.AlertType.WARNING, getClass(), "Campo Rating vacío", "Por favor, ingresa un rating.");
			return false;
		}

		try {
			int rating = Integer.parseInt(txtFRating.getText());
			if (rating < 0 || rating > 5) {
				UtilsViews.mostrarDialogo(Alert.AlertType.WARNING, getClass(), "Rating inválido", "El rating debe ser un número entre 0 y 5.");
				return false;
			}
		} catch (NumberFormatException e) {
			UtilsViews.mostrarDialogo(Alert.AlertType.WARNING, getClass(), "Rating inválido", "El rating debe ser un número.");
			return false;
		}

		// Validar si se ha seleccionado al menos una opción de estado (Comprado, Deseado o Jugado)
		if (!checkBoxComprado.isSelected() && !checkBoxDeseado.isSelected() && !checkBoxJugado.isSelected()) {
			UtilsViews.mostrarDialogo(Alert.AlertType.WARNING, getClass(), "Estado del juego", "Por favor, selecciona al menos un estado (Comprado, Deseado, Jugado).");
			return false;
		}

		// Validar comentarios (opcional, pero si hay algo escrito, verificar longitud)
		if (!txtFComentario.getText().isEmpty() && txtFComentario.getText().length() > 500) {
			UtilsViews.mostrarDialogo(Alert.AlertType.WARNING, getClass(), "Comentario demasiado largo", "El comentario no puede tener más de 500 caracteres.");
			return false;
		}

		return true;
	}

	// Método para limpiar los campos del formulario
	private void limpiarCampos() {
		// Limpiar campos
		txtFRating.clear();
		txtFComentario.clear();
		checkBoxComprado.setSelected(false);
		checkBoxDeseado.setSelected(false);
		checkBoxJugado.setSelected(false);
		imagenJuego.setImage(null);  // Limpiar la imagen del juego
	}

	// Método para agregar el juego a la base de datos
	private void agregarJuegoABaseDeDatos() {
		if (!validarCampos()) {
			return; // Si los campos no son válidos, no continuamos
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

			// Crear el objeto JuegosBiblioteca con los datos
			JuegosBiblioteca newGame = new JuegosBiblioteca();
			newGame.setTitulo(game.getName()); // Usamos el nombre del objeto Game
			newGame.setDescripcion(game.getDescription()); // Agregar la descripción, si la tienes
			newGame.setRating(Integer.parseInt(txtFRating.getText()));
			newGame.setFechaAñadido(new java.util.Date());
			newGame.setComentario(txtFComentario.getText());
			newGame.setComprado(checkBoxComprado.isSelected());
			newGame.setDeseado(checkBoxDeseado.isSelected());
			newGame.setJugado(checkBoxJugado.isSelected());
			newGame.setUrlImagen(game.getBackgroundImage());
			newGame.setUsuario(BibliotecaController.getUsuario()); // Asignar el usuario actual

			// Insertar el juego en la base de datos
			JuegosBibliotecaDaoImpl juegoNuevoDao = new JuegosBibliotecaDaoImpl(sesion);
			juegoNuevoDao.insert(newGame); // CascadeType.ALL guardará las capturas automáticamente

			// Realizar commit
			sesion.getTransaction().commit();

			// Mostrar mensaje de éxito
			UtilsViews.mostrarDialogo(Alert.AlertType.INFORMATION, getClass(), "Éxito", "Juego agregado correctamente");

			// Cerrar la ventana actual después de agregar el juego
			Stage stage = (Stage) btnAgregar.getScene().getWindow();
			stage.close();


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

	public void setPrimaryStage(Stage stage) {
	    this.primaryStage = stage;
	    // Aquí puedes realizar ajustes si lo necesitas
	}
}


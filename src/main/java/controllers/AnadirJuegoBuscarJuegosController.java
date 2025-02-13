package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import dao.JuegosBibliotecaDaoImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import models.Capturas;
import models.JuegosBiblioteca;
import models.Game;
import utils.HibernateUtil;
import utils.UtilsImages;
import utils.UtilsViews;
import org.hibernate.Session;

public class AnadirJuegoBuscarJuegosController implements Initializable {

	@FXML private Label lblAnadirJuegoTitulo;
	@FXML private ImageView imagenJuego;
	@FXML private CheckBox checkBoxComprado;
	@FXML private CheckBox checkBoxDeseado;
	@FXML private CheckBox checkBoxJugado;
	@FXML private TextField txtFRating;
	@FXML private TextArea txtFComentario;
	@FXML private Button btnCancelar;
	@FXML private Button btnAgregar;
	
	private byte[] imagenPrincipalBytes; // Imagen principal en bytes


	private Game game;  // Variable para almacenar el juego recibido

	// Método para recibir el objeto Game desde la vista anterior
	public void setGame(Game game) {
		this.game = game;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		if (game != null) {
			lblAnadirJuegoTitulo.setText(game.getName()); // Título del juego
			imagenJuego.setImage(new Image(game.getBackgroundImage())); // Imagen del juego
		}

		// Configurar los botones
		btnAgregar.setOnAction(event -> agregarJuegoABaseDeDatos());
		btnCancelar.setOnAction(event -> limpiarCampos());
	}

	// Método para validar los campos (igual que en tu versión actual)
	private boolean validarCampos() {
		// ... implementación de validación
		return true;
	}

	// Método para limpiar los campos del formulario
	private void limpiarCampos() {
		// Limpiar campos
	}

	// Método para agregar el juego a la base de datos
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
			newGame.setUsuario(BibliotecaController.getUsuario()); // Asignar el usuario actual
//			newGame.setImagen(UtilsImages.imageToByteArray(imagenJuego.getImage()); // Asignar la imagen convertida a bytes

			// Aquí podrías agregar las capturas si es necesario

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


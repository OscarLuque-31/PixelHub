package controllers;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import org.hibernate.Session;
import org.hibernate.Transaction;

import dao.JuegosBibliotecaDaoImpl;
import dao.PreferenciasDaoImpl;
import dao.UsuarioDaoImpl;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Preferencias;
import models.Usuario;
import utils.DateUtils;
import utils.HibernateUtil;
import utils.UtilsBcrypt;
import utils.UtilsViews;


public class PerfilController implements Initializable {

	@FXML
	private VBox vboxContainer;

	@FXML
	private Label lblPerfil;

	@FXML
	private Button btnCancelar;

	@FXML
	private Button btnConfirmar;

	@FXML
	private ImageView imgEditar;

	@FXML
	private TextField txtFNombreUsuario;

	@FXML
	private TextField txtFNombre;

	@FXML
	private TextField txtFFechaNacimiento;

	@FXML
	private TextField txtFApellidos;

	@FXML
	private TextField txtFCorreoElectronico;

	@FXML
	private Label lblContrasenia;

	@FXML
	private PasswordField txtFContrasena;

	@FXML
	private Label lblContraseniaNueva;

	@FXML
	private PasswordField txtFContraseniaNueva;

	@FXML
	private ListView<String> listViewGenero;

	@FXML
	private Button btnAnadirGenero;

	@FXML
	private Button btnEliminarGenero;
	
    @FXML
    private Button btnAnadirPlataformas;

    @FXML
    private Button btnEliminarPlataformas;

	@FXML
	private ListView<String> listViewPlataforma;

	@FXML
	private Label lblPreferencias;

	@FXML
	private Label juegosAnadidos;

	@FXML
	private Label creacionFecha;


	private Usuario usuario;

	private void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	private Usuario getUsuario() {
		return this.usuario;
	}

	/**
     * Inicializa la vista del perfil.
     * @param location   URL de localización.
     * @param resources  Recursos utilizados en la vista.
     */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Enlaza el css
		vboxContainer.getStylesheets().add(getClass().getResource("/styles/stylePerfil.css").toExternalForm());

		// Inicializa imágenes y efectos hover

		initializeImagesBar();
		// Setea el usuario de BibliotecaController
		setUsuario(BibliotecaController.getUsuario());
		// Rellena los TextField con los datos del usuario
		initializeTextFields(getUsuario());
		// Rellena los ListView con las preferencias del usuario
		initializeListViews(getUsuario());
		desactivarBarritasListView();

		// Actualiza el Label de juegos añadidos
		actualizarJuegosAnadidos();
		// Formatea y muestra la fecha de creación
		actualizarFechaCreacion();

		imgEditar.setOnMouseClicked(event -> cambiarEstadoDePerfilAEditable());
		btnCancelar.setOnMouseClicked(event -> desactivarEdicion());
		btnConfirmar.setOnMouseClicked(event -> confirmarCambios());
		
		listViewGenero.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		listViewPlataforma.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
	}
	
	
	@FXML
	private void agregarGenero() {
	    List<String> generosPermitidos = List.of("Acción", "Indie", "Aventura", "RPG", "Estrategia", 
	        "Shooter", "Casual", "Simulación", "Puzzle", "Arcade", "Plataformas", "Multijugador", 
	        "Carreras", "Deportes", "Lucha", "Familiar", "Juegos de mesa", "Educativo", "Cartas");

	    ChoiceDialog<String> dialog = new ChoiceDialog<>(generosPermitidos.get(0), generosPermitidos);
	    dialog.setTitle("Agregar Género");
	    dialog.setHeaderText("Seleccione un género para añadir:");
	    dialog.setContentText("Género:");

	    // Aplica estilo CSS
	    dialog.getDialogPane().getStylesheets().add(getClass().getResource("/styles/styleDialogElegir.css").toExternalForm());
	    dialog.getDialogPane().getStyleClass().add("custom-dialog");


	    // Obtiene el Stage del diálogo y lo hace transparente
	    Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
	    stage.initStyle(StageStyle.TRANSPARENT); 
	    stage.getScene().setFill(Color.TRANSPARENT);
	    
	    Optional<String> result = dialog.showAndWait();
	    result.ifPresent(genero -> {
	        if (!listViewGenero.getItems().contains(genero)) {
	            listViewGenero.getItems().add(genero);
	        } else {
	            UtilsViews.mostrarDialogo(Alert.AlertType.WARNING, getClass(), "Duplicado", "El género ya ha sido agregado.");
	        }
	    });
	}

	
	@FXML
	private void agregarPlataforma() {
	    // Lista de plataformas permitidas
	    List<String> plataformasPermitidas = List.of("PC", "PlayStation", "Xbox", "iOS", 
	        "Apple Macintosh", "Linux", "Nintendo", "Android", "Web");

	    // Muestra un diálogo con las plataformas permitidas
	    ChoiceDialog<String> dialog = new ChoiceDialog<>(plataformasPermitidas.get(0), plataformasPermitidas);
	    dialog.setTitle("Agregar Plataforma");
	    dialog.setHeaderText("Seleccione una plataforma para añadir:");
	    dialog.setContentText("Plataforma:");
	    
	    dialog.getDialogPane().getStylesheets().add(getClass().getResource("/styles/styleDialogElegir.css").toExternalForm());
	    dialog.getDialogPane().getStyleClass().add("custom-dialog");
	    
	    // Obtiene el Stage del diálogo y hacer transparente
	    Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
	    stage.initStyle(StageStyle.TRANSPARENT); 
	    stage.getScene().setFill(Color.TRANSPARENT);

	    Optional<String> result = dialog.showAndWait();
	    result.ifPresent(plataforma -> {
	        if (!listViewPlataforma.getItems().contains(plataforma)) {
	            listViewPlataforma.getItems().add(plataforma);
	        } else {
	            UtilsViews.mostrarDialogo(Alert.AlertType.WARNING, getClass(), "Duplicado", "La plataforma ya ha sido agregada.");
	        }
	    });
	}
	
	@FXML
	private void eliminarPlataforma() {
	    // Obtiene las plataformas seleccionadas
	    ObservableList<String> selectedPlataformas = listViewPlataforma.getSelectionModel().getSelectedItems();

	    // Elimina las plataformas seleccionadas
	    listViewPlataforma.getItems().removeAll(selectedPlataformas);
	}

	
	@FXML
	private void eliminarGenero() {
	    // Obtener los géneros seleccionados
	    ObservableList<String> selectedGeneros = listViewGenero.getSelectionModel().getSelectedItems();

	    // Eliminar los géneros seleccionados
	    listViewGenero.getItems().removeAll(selectedGeneros);
	}

	/**
	 * Método que desactiva las barra de desplazamiento de los listView
	 */
	private void desactivarBarritasListView() {
		Platform.runLater(() -> {
			// Obtener las barras de desplazamiento de los ListView
			ScrollBar scrollBarVerticalGenero = (ScrollBar) listViewGenero.lookup(".scroll-bar:vertical");
			ScrollBar scrollBarVerticalPlataforma = (ScrollBar) listViewPlataforma.lookup(".scroll-bar:vertical");

			// Asegurarse de que los ScrollBars fueron encontrados
			if (scrollBarVerticalGenero != null) {
				scrollBarVerticalGenero.setVisible(false); // Ocultar la barra vertical de género
			}

			if (scrollBarVerticalPlataforma != null) {
				scrollBarVerticalPlataforma.setVisible(false); // Ocultar la barra vertical de plataforma
			}
		});
	}

	/**
	 * Actualiza el Label de juegos añadidos con el número de juegos del usuario.
	 */
	private void actualizarJuegosAnadidos() {
		JuegosBibliotecaDaoImpl juegosDao = new JuegosBibliotecaDaoImpl(HibernateUtil.getSession());
		int numJuegos = juegosDao.contarJuegosPorUsuario(usuario.getId());
		juegosAnadidos.setText("Juegos añadidos: " + numJuegos);
	}

	/**
	 * Formatea y muestra la fecha de creación del usuario.
	 */
	private void actualizarFechaCreacion() {
		LocalDate fechaCreacionLocal = usuario.getFecha_creacion(); 

		// Convierte LocalDate a Date 
		Date fechaCreacion = Date.valueOf(fechaCreacionLocal);

		// Formatea la fecha
		String fechaFormateada = DateUtils.formatearFecha(fechaCreacion);
		creacionFecha.setText("Fecha de creación: " + fechaFormateada);
	}

	/**
	 * Método que inicializa la imagen del botón de edición.
	 */
	private void initializeImagesBar() {
		imgEditar.setImage(new Image(getClass().getResourceAsStream("/images/Edit.png")));
	}

	/**
	 * Método que rellena los TextField con los datos del usuario.
	 */
	private void initializeTextFields(Usuario usuario) {
		txtFApellidos.setText(usuario.getApellidos());
		txtFApellidos.setEditable(false);
		txtFContrasena.setText(usuario.getPassword());
		txtFContrasena.setEditable(false);
		txtFFechaNacimiento.setText(usuario.getFecha_nacimiento().toString().substring(0,10));
		txtFFechaNacimiento.setEditable(false);
		txtFCorreoElectronico.setText(usuario.getEmail());
		txtFCorreoElectronico.setEditable(false);
		txtFNombre.setText(usuario.getNombre());
		txtFNombre.setEditable(false);
		txtFNombreUsuario.setText(usuario.getUsername());
		txtFNombreUsuario.setEditable(false);
		btnCancelar.setVisible(false);
		btnConfirmar.setVisible(false);
		lblContraseniaNueva.setVisible(false);
		txtFContraseniaNueva.setVisible(false);
		
		btnAnadirGenero.setVisible(false);
		btnEliminarGenero.setVisible(false);
		btnAnadirPlataformas.setVisible(false);
		btnEliminarPlataformas.setVisible(false);
	}

	/**
	 * Método que cambia el estado del perfil a editable
	 */
	private void cambiarEstadoDePerfilAEditable() {
		txtFApellidos.setEditable(true);
		txtFFechaNacimiento.setEditable(true);
		txtFCorreoElectronico.setEditable(true);
		txtFNombre.setEditable(true);
		txtFNombreUsuario.setEditable(true);
		btnCancelar.setVisible(true);
		btnConfirmar.setVisible(true);

		activarActualizarContrasenia();
		activarActualizarPreferencias();
	}

	/**
	 * Método que actualiza el cambiar contraseña
	 */
	private void activarActualizarContrasenia() {
		lblContrasenia.setText("Contraseña actual");
		txtFContrasena.clear();
		txtFContrasena.setEditable(true);

		lblContraseniaNueva.setVisible(true);
		txtFContraseniaNueva.setVisible(true);
	}
	
	/**
	 * Método que activa las preferncias
	 */
	private void activarActualizarPreferencias() {
		
		btnAnadirGenero.setVisible(true);
		btnEliminarGenero.setVisible(true);
		btnAnadirPlataformas.setVisible(true);
		btnEliminarPlataformas.setVisible(true);
	}

	/**
	 * Método que desactiva los camposdd
	 */
	private void desactivarEdicion() {

		txtFNombreUsuario.setEditable(false);
		txtFNombre.setEditable(false);
		txtFApellidos.setEditable(false);
		txtFFechaNacimiento.setEditable(false);
		txtFCorreoElectronico.setEditable(false);

		lblContrasenia.setText("Contraseña");
		txtFContrasena.setText(usuario.getPassword());

		lblContraseniaNueva.setVisible(false);
		txtFContraseniaNueva.setVisible(false);

		btnCancelar.setVisible(false);
		btnConfirmar.setVisible(false);
		
		btnAnadirGenero.setVisible(false);
		btnEliminarGenero.setVisible(false);
		btnAnadirPlataformas.setVisible(false);
		btnEliminarPlataformas.setVisible(false);
	}


	/**
	 * Método que rellena los ListView con las preferencias del usuario.
	 */
	private void initializeListViews(Usuario usuario) {
		// Crea la instancia del DAO utilizando la sesión de Hibernate.
		PreferenciasDaoImpl preferenciasDao = new PreferenciasDaoImpl(HibernateUtil.getSession());

		// Obtiene la lista de preferencias del usuario.
		List<Preferencias> preferencias = preferenciasDao.getPreferenciasByUsuario(usuario.getId());

		// Lista para almacenar las preferencias de género y plataforma.
		List<String> generos = new ArrayList<>();
		List<String> plataformas = new ArrayList<>();

		// Recorre las preferencias y separarlas según el tipo.
		for (Preferencias pref : preferencias) {
			if (pref.getTipo() == 1) {
				generos.add(pref.getPreferencia());
			} else if (pref.getTipo() == 2) {
				plataformas.add(pref.getPreferencia());
			}
		}

		// Convierte las listas en ObservableList para asignarlas a los ListView.
		ObservableList<String> observableGeneros = FXCollections.observableArrayList(generos);
		ObservableList<String> observablePlataformas = FXCollections.observableArrayList(plataformas);

		// Asigna las listas a los ListView.
		listViewGenero.setItems(observableGeneros);
		listViewPlataforma.setItems(observablePlataformas);


	}

	@FXML
	private void confirmarCambios() {
	    // Valida los datos antes de proceder
	    if (!validarDatos()) {
	        return; 
	    }

	    Session session = HibernateUtil.getSession();
	    Transaction tx = null;

	    try {
	        tx = session.beginTransaction();
	        UsuarioDaoImpl usuarioDaoImpl = new UsuarioDaoImpl(session);
	        PreferenciasDaoImpl preferenciasDao = new PreferenciasDaoImpl(session);

	        // Verifica si el usuario quiere cambiar la contraseña
	        if (!txtFContrasena.getText().trim().isEmpty() && !txtFContraseniaNueva.getText().trim().isEmpty()) {
	            if (!UtilsBcrypt.checkPassword(txtFContrasena.getText().trim(), usuario.getPassword())) {
	                UtilsViews.mostrarDialogo(Alert.AlertType.ERROR, getClass(), "Error de Contraseña", "La contraseña actual es incorrecta.");
	                return;
	            }

	            // Valida la nueva contraseña antes de actualizarla
	            if (!validarContrasena(txtFContraseniaNueva.getText().trim())) {
	                return;
	            }

	            // Hashea de la nueva contraseña
	            String nuevaPasswordHashed = UtilsBcrypt.hashPassword(txtFContraseniaNueva.getText().trim());
	            usuario.setPassword(nuevaPasswordHashed);
	        }

	        // Actualiza datos del usuario
	        usuario.setUsername(txtFNombreUsuario.getText().trim());
	        usuario.setNombre(txtFNombre.getText().trim());
	        usuario.setApellidos(txtFApellidos.getText().trim());
	        usuario.setEmail(txtFCorreoElectronico.getText().trim());
	        usuario.setFecha_nacimiento(Date.valueOf(LocalDate.parse(txtFFechaNacimiento.getText().trim())));

	        usuarioDaoImpl.update(usuario);

	        // Actualiza preferencias de género
	        List<Preferencias> preferenciasGenero = new ArrayList<>();
	        for (String genero : listViewGenero.getItems()) {
	            Preferencias pref = new Preferencias();
	            pref.setId_usuario(usuario.getId());
	            pref.setTipo(1); // Tipo 1 para géneros
	            pref.setPreferencia(genero);
	            preferenciasGenero.add(pref);
	        }

	        // Actualiza preferencias de plataforma
	        List<Preferencias> preferenciasPlataforma = new ArrayList<>();
	        for (String plataforma : listViewPlataforma.getItems()) {
	            Preferencias pref = new Preferencias();
	            pref.setId_usuario(usuario.getId());
	            pref.setTipo(2);
	            pref.setPreferencia(plataforma);
	            preferenciasPlataforma.add(pref);
	        }

	        // Elimina preferencias antiguas y guardar las nuevas
	        preferenciasDao.eliminarPreferenciasPorUsuario(usuario.getId());
	        preferenciasDao.insertarPreferencias(preferenciasGenero);
	        preferenciasDao.insertarPreferencias(preferenciasPlataforma);

	        tx.commit(); 

	        UtilsViews.mostrarDialogo(Alert.AlertType.INFORMATION, getClass(), "Éxito", "Perfil actualizado correctamente.");
	        desactivarEdicion();

	    } catch (Exception e) {
	        if (tx != null) {
	            tx.rollback();
	        }
	        UtilsViews.mostrarDialogo(Alert.AlertType.ERROR, getClass(), "Error", "No se pudo actualizar el perfil.");
	        e.printStackTrace();
	    } finally {
	        session.close();
	    }
	}



	/**
	 * Método que valida todos los campos antes de meterlos en base de datos
	 * @return
	 */
	private boolean validarDatos() {
		Session session = HibernateUtil.getSession();
		UsuarioDaoImpl usuarioDao = new UsuarioDaoImpl(session);

		try {
			// Valida que los campos no estén vacíos
			if (txtFNombreUsuario.getText().trim().isEmpty() ||
					txtFNombre.getText().trim().isEmpty() ||
					txtFApellidos.getText().trim().isEmpty() ||
					txtFCorreoElectronico.getText().trim().isEmpty() ||
					txtFFechaNacimiento.getText().trim().isEmpty()) {

				UtilsViews.mostrarDialogo(Alert.AlertType.ERROR, getClass(), "Campos Vacíos", "Todos los campos deben estar completos.");
				return false;
			}

			// Valida correo electrónico con regex
			String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
			if (!txtFCorreoElectronico.getText().matches(emailRegex)) {
				UtilsViews.mostrarDialogo(Alert.AlertType.ERROR, getClass(), "Correo Inválido", "El correo electrónico ingresado no es válido.");
				return false;
			}

			// Valida formato de fecha (YYYY-MM-DD)
			try {
				LocalDate.parse(txtFFechaNacimiento.getText().trim());
			} catch (Exception e) {
				UtilsViews.mostrarDialogo(Alert.AlertType.ERROR, getClass(), "Fecha Incorrecta", "Formato de fecha inválido. Use YYYY-MM-DD.");
				return false;
			}

			// Verifica si el nombre de usuario o el correo ya existen en la BD
			String nuevoUsername = txtFNombreUsuario.getText().trim();
			String nuevoEmail = txtFCorreoElectronico.getText().trim();

			if (!nuevoUsername.equals(usuario.getUsername()) || !nuevoEmail.equals(usuario.getEmail())) {
				boolean existe = usuarioDao.existeUsuario(nuevoUsername, nuevoEmail,usuario.getId());
				if (existe) {
					UtilsViews.mostrarDialogo(Alert.AlertType.ERROR, getClass(), "Duplicado", "El nombre de usuario o el correo ya están en uso.");
					return false;
				}
			}

			return true;
		} finally {
			session.close();
		}
	}

	/**
	 * Método para validar la contraseña con los nuevos requisitos
	 * @param password
	 * @return
	 */
	private boolean validarContrasena(String password) {
		StringBuilder errores = new StringBuilder();

		if (password == null || password.isEmpty()) {
			errores.append("La contraseña no puede estar vacía.\n");
		}

		// Verifica que tenga al menos 8 caracteres
		if (password.length() < 8) {
			errores.append("La contraseña debe tener al menos 8 caracteres.\n");
		}

		// Verifica que contenga al menos una letra
		if (!password.matches(".*[A-Za-z].*")) {
			errores.append("La contraseña debe contener al menos una letra.\n");
		}

		// Verifica que contenga al menos un número
		if (!password.matches(".*\\d.*")) {
			errores.append("La contraseña debe contener al menos un número.\n");
		}

		// Si hay errores, muestra un solo mensaje de alerta
		if (errores.length() > 0) {
			UtilsViews.mostrarDialogo(Alert.AlertType.ERROR, getClass(), "Error de Contraseña", errores.toString());
			return false;
		}

		return true;
	}
}

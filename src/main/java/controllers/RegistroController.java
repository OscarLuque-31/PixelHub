package controllers;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.controlsfx.control.CheckComboBox;
import org.hibernate.Hibernate;
import org.hibernate.Session;

import dao.PreferenciasDaoImpl;
import dao.UsuarioDaoImpl;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import models.Preferencias;
import models.Usuario;
import utils.HibernateUtil;
import utils.NavigationUtils;
import utils.UtilsBcrypt;
import utils.UtilsViews;

public class RegistroController {

	@FXML
	private Button btnRegister;

	@FXML
	private CheckComboBox<String> cmbGender;

	@FXML
	private CheckComboBox<String> cmbPlatform;

	@FXML
	private Label lblRegister;

	@FXML
	private PasswordField txtConfirmPassword;

	@FXML
	private DatePicker txtDate;

	@FXML
	private TextField txtEmail;

	@FXML
	private TextField txtName;

	@FXML
	private PasswordField txtPassword;

	@FXML
	private TextField txtSurname;

	@FXML
	private TextField txtUsername;
	@FXML
	private Button btnCerrar;

	@FXML
	private Button btnMaximizar;
	@FXML
	private HBox dragArea;

	@FXML
	private BorderPane borderPane;

	@FXML
	private Button btnMinimizar;

	@FXML
	private ImageView iconMaximizar;

	@FXML
	private ImageView iconCerrar;

	@FXML
	private ImageView iconMinimizar;

	@FXML
	private ImageView imgLogo;

	@FXML
	private ImageView imgFlechitaAtras;

	@FXML
	private Circle circleLogo;

	@FXML
	private Label lblPreferencias;

	@FXML
	private Button btnCancel;

	@FXML
	private GridPane gridPane;

	private Stage stage;

	/**
	 * Método para cargar la hoja de estilos (CSS) de la ventana de login
	 */
	private void cargarCSS() {
		// Cargar el archivo de estilo para la ventana de login
		borderPane.getStylesheets().addAll(getClass().getResource("/styles/styleRegister.css").toExternalForm(),
				getClass().getResource("/styles/styleTopBar.css").toExternalForm());
	}

	/**
	 * Método que controla la navegación entre ventanas
	 */
	private void navegacionEntreVentanas() {
		btnRegister.setOnMouseClicked(event -> NavigationUtils.navigateTo(stage, "/views/Login.fxml"));
		btnCancel.setOnMouseClicked(event -> NavigationUtils.navigateTo(stage, "/views/Login.fxml"));
	}

	public void setStage(Stage stage) {
		this.stage = stage;
		// Funcionalidad de los botones
		UtilsViews.funBtnsBar(btnMinimizar, btnMaximizar, btnCerrar, dragArea, stage);
		// Cargar el CSS de la ventana de login
		cargarCSS();
		// Inicializar imágenes
		initializeImagesBar();
		// Efectos de hover
		hoverEffect();
		// Navegación entre pantallas
		navegacionEntreVentanas();
		// Rellenar comboBox
		rellenarComboBox();
		// Registro de un usuario en bd
		registroUsuario();
	}

	/**
	 * Rellenar comboBox
	 */
	private void rellenarComboBox() {
		cmbGender.setTitle("Género");
		cmbPlatform.setTitle("Plataformas");
		cmbGender.getItems().addAll("Acción", "Indie", "Aventura", "RPG", "Estrategia", "Disparos", "Casual",
				"Simulación", "Rompecabezas", "Arcade", "Plataformas", "Multijugador masivo", "Carreras", "Deportes",
				"Peleas", "Familiar", "Juegos de mesa", "Educativos", "Cartas");
		cmbPlatform.getItems().addAll("PC", "PlayStation", "Xbox", "Móvil", "Nintento");
	}

	/**
	 * Método que registra al usuario
	 */
	private void registroUsuario() {
		btnRegister.setOnMouseClicked(event -> {
			if (registrarUsuario()) {
				NavigationUtils.navigateTo(stage, "/views/Login.fxml");
			} 
		});

	}

	/**
	 * Método que crea al usuario con todos los campos correctos
	 * 
	 * @return true si la operación fue exitosa, false en caso contrario.
	 */
	private boolean registrarUsuario() {
		if (validarCampos()) {
			System.out.println("REGISTRO DE USUARIO");
			System.out.println("-------------------");

			Session sesion = null;
			try {
				// Iniciar sesión
				sesion = HibernateUtil.getSession();

				// Verifica si ya existe una transacción activa antes de iniciar una nueva
				if (!sesion.getTransaction().isActive()) {
					sesion.beginTransaction();
				}

				UsuarioDaoImpl usuarioDao = new UsuarioDaoImpl(sesion);
				PreferenciasDaoImpl preferenciasDao = new PreferenciasDaoImpl(sesion);

				// Crear el nuevo usuario
				Usuario newUsuario = new Usuario();
				newUsuario.setNombre(txtName.getText());
				newUsuario.setApellidos(txtSurname.getText());
				newUsuario.setUsername(txtUsername.getText());
				newUsuario.setEmail(txtEmail.getText());

				if (txtDate.getValue() != null) {
					LocalDate localDate = txtDate.getValue();
					Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
					newUsuario.setFecha_nacimiento(date);
				}

				newUsuario.setPassword(UtilsBcrypt.hashPassword(txtPassword.getText()));
				newUsuario.setFecha_creacion(LocalDate.now());

				// Insertar usuario
				usuarioDao.insert(newUsuario);

				// Insertar preferencias de género
				for (String pref : cmbGender.getCheckModel().getCheckedItems()) {
					Preferencias preferencia = new Preferencias();
					preferencia.setId_usuario(newUsuario.getId());
					preferencia.setPreferencia(pref);
					preferencia.setTipo(1);
					preferenciasDao.insert(preferencia);
				}

				// Insertar preferencias de plataformas
				for (String pref : cmbPlatform.getCheckModel().getCheckedItems()) {
					Preferencias preferencia = new Preferencias();
					preferencia.setId_usuario(newUsuario.getId());
					preferencia.setPreferencia(pref);
					preferencia.setTipo(2);
					preferenciasDao.insert(preferencia);
				}

				// Realizar commit
				sesion.getTransaction().commit();
				return true;

			} catch (Exception e) {
				// Realizar rollback en caso de error
				if (sesion != null && sesion.getTransaction().isActive()) {
					sesion.getTransaction().rollback();
				}
				e.printStackTrace();
				return false;

			}
		}
		return false;
	}

	/**
	 * Método que valida los campos del registro
	 * 
	 * @return true si son todos validos
	 */
	private boolean validarCampos() {
		StringBuilder errores = new StringBuilder();

		// 1. Validación del Nombre
		if (txtName.getText().trim().isEmpty() || txtName.getText().length() > 30) {
			errores.append("El nombre no puede estar vacío y debe tener un máximo de 30 caracteres.\n");
		}

		// 2. Validación del Apellido
		if (txtSurname.getText().trim().isEmpty() || txtSurname.getText().length() > 50) {
			errores.append("El apellido no puede estar vacío y debe tener un máximo de 50 caracteres.\n");
		}

		// 3. Validación del Nombre de Usuario
		if (txtUsername.getText().trim().isEmpty() || txtUsername.getText().length() > 30) {
			errores.append("El nombre de usuario no puede estar vacío y debe tener un máximo de 30 caracteres.\n");
		}

		// 4. Validación del Email
		String email = txtEmail.getText().trim();
		if (email.isEmpty() || !email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")) {
			errores.append("El correo electrónico no es válido.\n");
		}

		// 5. Validación de la Contraseña
		String password = txtPassword.getText();
		if (password.isEmpty() || password.length() < 8 || !password.matches(".*\\d.*") || !password.matches(".*[A-Za-z].*")) {
			errores.append("La contraseña debe tener al menos 8 caracteres, incluir letras y números.\n");
		}

		// 6. Validación de Confirmación de Contraseña
		if (!password.equals(txtConfirmPassword.getText())) {
			errores.append("Las contraseñas no coinciden.\n");
		}

		// 7. Validación de la Fecha de Nacimiento
		if (txtDate.getValue() == null) {
			errores.append("La fecha de nacimiento no puede estar vacía.\n");
		} else {
			LocalDate birthDate = txtDate.getValue();
			LocalDate minDate = LocalDate.now().minusYears(6);
			if (birthDate.isAfter(minDate)) {
				errores.append("Debe tener al menos 6 años para registrarse.\n");
			}
		}

		// 8. Validación de Género
		if (cmbGender.getCheckModel().getCheckedItems().isEmpty()) {
			errores.append("Debe seleccionar al menos un género.\n");
		}

		// 9. Validación de Plataforma
		if (cmbPlatform.getCheckModel().getCheckedItems().isEmpty()) {
			errores.append("Debe seleccionar al menos una plataforma.\n");
		}

		// Mostrar errores, si existen
		if (errores.length() > 0) {
			UtilsViews.mostrarDialogo(Alert.AlertType.ERROR, getClass(), "Corrige estos errores, por favor", errores.toString());
			return false;
		}

		// Si todas las validaciones son correctas
		return true;
	}





	/**
	 * Método que recopila todos los hoverEffect
	 */
	public void hoverEffect() {
		UtilsViews.hoverEffectButton(btnMinimizar, "#2a3b47", "#192229");
		UtilsViews.hoverEffectButton(btnMaximizar, "#2a3b47", "#192229");
		UtilsViews.hoverEffectButton(btnCerrar, "#c63637", "#192229");
		UtilsViews.hoverEffectButton(btnRegister, "#0095FF", "#52A5E0");
	}

	/**
	 * Método que inicializa las imágenes
	 */
	public void initializeImagesBar() {
		imgLogo.setImage(new Image(getClass().getResourceAsStream("/images/logoPixelHub.png")));
		iconMinimizar.setImage(new Image(getClass().getResourceAsStream("/images/iconoMinimizar.png")));
		iconMaximizar.setImage(new Image(getClass().getResourceAsStream("/images/iconoMaximizar.png")));
		iconCerrar.setImage(new Image(getClass().getResourceAsStream("/images/iconoCerrar.png")));
		//		imgFlechitaAtras.setImage(new Image(getClass().getResourceAsStream("/images/flechaAtras.png")));
	}

}

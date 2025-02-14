package controllers;

import java.util.List;

import dao.UsuarioDaoImpl;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Usuario;
import utils.HibernateUtil;
import utils.NavigationUtils;
import utils.UtilsBcrypt;
import utils.UtilsViews;

public class LoginController {

	@FXML
	private BorderPane borderPane;

	@FXML
	private VBox vboxForm;

	@FXML
	private Label lblPixelHub;

	@FXML
	private TextField txtUsername;

	@FXML
	private PasswordField txtPassword;

	@FXML
	private Button btnLogin;

	@FXML
	private Text linkPassword;

	@FXML
	private Text linkRegister;

	@FXML
	private HBox dragArea;

	@FXML
	private GridPane gridPane;

	@FXML
	private Button btnMinimizar;

	@FXML
	private ImageView iconMinimizar;


	@FXML
	private Button btnCerrar;

	@FXML
	private ImageView iconCerrar;

	@FXML
	private Circle circleLogo;

	@FXML
	private ImageView imgLogo;

	private Stage stage;
	private Stage loadingStage;

	public void cargarFXML(Stage primaryStage) {	
		try {
			// Cargar el FXML
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Login.fxml"));
			Scene scene = new Scene(loader.load());
			// Establecer el Stage en el controlador
			primaryStage.initStyle(StageStyle.TRANSPARENT);
			LoginController controller = loader.getController();
			controller.setStage(primaryStage);
			primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/logoPixelHub.png")));


			// Tarea en segundo plano para inicializar Hibernate
			Task<Void> initTask = new Task<>() {
				@Override
				protected Void call() {
					HibernateUtil.getSession();  // Forzamos la carga de Hibernate
					return null;
				}
			};

			// Ejecutar la tarea en un hilo separado para evitar que la interfaz se congele
			new Thread(initTask).start();


			// Configuración del Stage
			primaryStage.setTitle("PixelHub");
			primaryStage.setScene(scene);
			primaryStage.setMaximized(true);

			// Mostrar la ventana
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Método para cargar la hoja de estilos (CSS) de la ventana de login
	 */
	private void cargarCSS() {
		// Cargar el archivo de estilo para la ventana de login
		getBorderPane().getStylesheets().addAll(getClass().getResource("/styles/styleLogin.css").toExternalForm(),
				getClass().getResource("/styles/styleTopBar.css").toExternalForm());
	}

	/**
	 * Método que controla la navegación entre ventanas
	 */
	private void navegacionEntreVentanas() {
		linkRegister.setOnMouseClicked(event -> NavigationUtils.navigateTo(stage, "/views/Registro.fxml"));
		linkPassword.setOnMouseClicked(event -> NavigationUtils.navigateTo(stage, "/views/RecuperarContrasena.fxml"));
	}

	public void setStage(Stage stage) {
		this.stage = stage;
		// Funciones de los botones de la barra de navegacion
		UtilsViews.funBtnsBar(btnMinimizar, btnCerrar, stage);
		// Cargar el CSS de la ventana de login
		cargarCSS();
		// Inicializar imágenes
		initializeImagesBar();
		// Efectos de hover
		hoverEffect();
		// Navegación entre pantallas
		navegacionEntreVentanas();
		// Control de inicio de sesión
		inicioDeSesion();
	}


	/**
	 * Método que inicia sesión si las credenciales son correctas
	 */
	public void inicioDeSesion() {
	    getBtnLogin().setOnMouseClicked(event -> {

	        String username = getTxtUsername().getText().trim();
	        String password = getTxtPassword().getText().trim();

	        if (validarCampos(username, password)) {
	            mostrarPantallaCarga();
	            
	            Task<Usuario> loginTask = new Task<>() {
	                @Override
	                protected Usuario call() {
	                    return devolverUsuario(username);
	                }
	            };

	            loginTask.setOnSucceeded(e -> {
	                Usuario usuario = loginTask.getValue();
	                cerrarPantallaCarga(); // Cerrar pantalla de carga

	                if (usuario != null && UtilsBcrypt.checkPassword(password, usuario.getPassword())) {
	                    NavigationUtils.navigateToBibliotecaWithUser(stage, "/views/Biblioteca.fxml", "Biblioteca", usuario);
	                } else {                        
	                    UtilsViews.mostrarDialogo(Alert.AlertType.WARNING, getClass(),
	                            "No se ha encontrado al usuario", "Por favor, compruebe que los datos son correctos");
	                }
	            });

	            loginTask.setOnFailed(e -> {
	                cerrarPantallaCarga(); // Cerrar pantalla de carga en caso de error
	                UtilsViews.mostrarDialogo(Alert.AlertType.ERROR, getClass(),
	                        "Error de conexión", "Hubo un problema al iniciar sesión. Inténtelo de nuevo.");
	            });

	            new Thread(loginTask).start(); // Iniciar el hilo en segundo plano
	        }
	    });
	}

	
	private void mostrarPantallaCarga() {
	    loadingStage = new Stage();
	    loadingStage.initStyle(StageStyle.UNDECORATED);
	    loadingStage.initModality(Modality.APPLICATION_MODAL);
	    loadingStage.setResizable(false);

	    VBox vbox = new VBox(15);
	    vbox.setAlignment(Pos.CENTER);
	    vbox.getStyleClass().add("vbox-loading");

	    ProgressIndicator progressIndicator = new ProgressIndicator();
	    progressIndicator.getStyleClass().add("progress-indicator");

	    Label lblCargando = new Label("Iniciando sesión...");
	    lblCargando.getStyleClass().add("label-loading");

	    vbox.getChildren().addAll(progressIndicator, lblCargando);

	    Scene scene = new Scene(vbox, 250, 150);
	    scene.getStylesheets().add(getClass().getResource("/styles/styleLoadingScreen.css").toExternalForm());

	    loadingStage.setScene(scene);
	    loadingStage.show();
	}


	private void cerrarPantallaCarga() {
	    if (loadingStage != null) {
	        loadingStage.close();
	    }
	}

	/**
	 * Método que devuelve el usuario si el nombre de usuario existe
	 * @return Usuario encontrado o null
	 */
	private Usuario devolverUsuario(String username) {
		UsuarioDaoImpl usuarioDao = new UsuarioDaoImpl(HibernateUtil.getSession());
		// Consulta directa por nombre de usuario
		return usuarioDao.findByUsername(username);
	}

	/**
	 * Método que valida los campos de entrada
	 * @return true si los campos son válidos
	 */
	private boolean validarCampos(String username, String password) {
		StringBuilder errores = new StringBuilder();

		if (username.isEmpty()) {
			errores.append("El nombre de usuario no puede estar vacío.\n");
		}

		if (password.isEmpty()) {
			errores.append("La contraseña no puede estar vacía.\n");
		}

		if (errores.length() > 0) {
			UtilsViews.mostrarDialogo(Alert.AlertType.ERROR, getClass(), "Corrige estos errores, por favor", errores.toString());
			return false;
		}

		return true;
	}



	/**
	 * Método que recopila todos los hoverEffect
	 */
	public void hoverEffect() {
		UtilsViews.hoverEffectText(linkPassword, "#0095FF", "#52A5E0");
		UtilsViews.hoverEffectText(linkRegister, "#0095FF", "#52A5E0");
		UtilsViews.hoverEffectButton(getBtnLogin(), "#0095FF", "#52A5E0");
		UtilsViews.hoverEffectButton(btnMinimizar, "#2a3b47", "#192229");
		UtilsViews.hoverEffectButton(btnCerrar, "#c63637", "#192229");
	}

	/**
	 * Método que inicializa las imágenes
	 */
	public void initializeImagesBar() {
		imgLogo.setImage(new Image(getClass().getResourceAsStream("/images/logoPixelHub.png")));
		iconMinimizar.setImage(new Image(getClass().getResourceAsStream("/images/iconoMinimizar.png")));
		iconCerrar.setImage(new Image(getClass().getResourceAsStream("/images/iconoCerrar.png")));
	}

	public TextField getTxtUsername() {
		return txtUsername;
	}

	public void setTxtUsername(TextField txtUsername) {
		this.txtUsername = txtUsername;
	}

	public PasswordField getTxtPassword() {
		return txtPassword;
	}

	public void setTxtPassword(PasswordField txtPassword) {
		this.txtPassword = txtPassword;
	}

	public Button getBtnLogin() {
		return btnLogin;
	}

	public void setBtnLogin(Button btnLogin) {
		this.btnLogin = btnLogin;
	}

	public BorderPane getBorderPane() {
		return borderPane;
	}

	public void setBorderPane(BorderPane borderPane) {
		this.borderPane = borderPane;
	}


}

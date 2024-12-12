package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utils.NavigationUtils;
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
	private Button btnMaximizar;

	@FXML
	private ImageView iconMaximizar;

	@FXML
	private Button btnCerrar;

	@FXML
	private ImageView iconCerrar;

	@FXML
	private Circle circleLogo;

	@FXML
	private ImageView imgLogo;

	private Stage stage;


	public void cargarFXML(Stage primaryStage) {	
		try {
			// Cargar el FXML
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Login.fxml"));
			Scene scene = new Scene(loader.load());
			// Establecer el Stage en el controlador
			LoginController controller = loader.getController();
			controller.setStage(primaryStage);

			// Configuración del Stage
			primaryStage.setTitle("PixelHub");
			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.setScene(scene);

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
		borderPane.getStylesheets().addAll(getClass().getResource("/styles/styleLogin.css").toExternalForm(),
				getClass().getResource("/styles/styleTopBar.css").toExternalForm());
	}

	/**
	 * Método que controla la navegación entre ventanas
	 */
	private void navegacionEntreVentanas() {
		btnLogin.setOnMouseClicked(event -> NavigationUtils.navigateTo(stage, "/views/Biblioteca.fxml", "Biblioteca"));
		linkRegister.setOnMouseClicked(event -> NavigationUtils.navigateTo(stage, "/views/Registro.fxml", "Registro"));

	}

	public void setStage(Stage stage) {
		this.stage = stage;
		//Funciones de los botones de la barra de navegacion
		UtilsViews.funBtnsBar(btnMinimizar, btnMaximizar, btnCerrar, dragArea, stage);
		//Cargar el CSS de la ventana de login
		cargarCSS();
		// Inicializar imágenes
		initializeImagesBar();
		//Efectos de hover
		hoverEffect();
		//Navegación entre pantallas
		navegacionEntreVentanas();
	}



	/**
	 * Método que recopila todos los hoverEffect
	 */
	public void hoverEffect() {
		UtilsViews.hoverEffectText(linkPassword, "#0095FF", "#52A5E0");
		UtilsViews.hoverEffectText(linkRegister, "#0095FF", "#52A5E0");
		UtilsViews.hoverEffectButton(btnLogin, "#0095FF", "#52A5E0");
		UtilsViews.hoverEffectButton(btnMinimizar, "#2a3b47", "#192229");
		UtilsViews.hoverEffectButton(btnMaximizar, "#2a3b47", "#192229");
		UtilsViews.hoverEffectButton(btnCerrar, "#c63637", "#192229");
	}

	/**
	 * Método que inicializa las imágenes
	 */
	public void initializeImagesBar() {
		imgLogo.setImage(new Image(getClass().getResourceAsStream("/images/logoPixelHub.png")));
		iconMinimizar.setImage(new Image(getClass().getResourceAsStream("/images/iconoMinimizar.png")));
		iconMaximizar.setImage(new Image(getClass().getResourceAsStream("/images/iconoMaximizar.png")));
		iconCerrar.setImage(new Image(getClass().getResourceAsStream("/images/iconoCerrar.png")));
	}


}

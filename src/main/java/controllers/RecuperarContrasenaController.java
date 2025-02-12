package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import utils.NavigationUtils;
import utils.UtilsViews;

public class RecuperarContrasenaController {

    @FXML
    private BorderPane borderPane;

    @FXML
    private HBox dragArea;

    @FXML
    private Button btnMinimizar;

    @FXML
    private ImageView iconMinimizar;

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

    @FXML
    private VBox vboxForm;

    @FXML
    private Label lblRecuperarContrasena;

    @FXML
    private TextField txtEmail;

    @FXML
    private Button btnEnviarCodigo;

    @FXML
    private Label lblCancelar;
    
    private Stage stage;    
    
    /**
     * Método para cargar la hoja de estilos (CSS) de la ventana de login
     */
    private void cargarCSS() {
        // Cargar el archivo de estilo para la ventana de login
        borderPane.getStylesheets().addAll(getClass().getResource("/styles/styleRecuperarContrasena.css").toExternalForm(),
        								   getClass().getResource("/styles/styleTopBar.css").toExternalForm());
    }

    /**
     * Método que controla la navegación entre ventanas
     */
    private void navegacionEntreVentanas() {
    	lblCancelar.setOnMouseClicked(event -> NavigationUtils.navigateTo(stage, "/views/Login.fxml"));
    	btnEnviarCodigo.setOnMouseClicked(event -> NavigationUtils.navigateTo(stage, "/views/CodigoEmail.fxml"));
    }

    public void setStage(Stage stage) {
        this.stage = stage;
        //Funcionalidad de los botones
        UtilsViews.funBtnsBar(btnMinimizar, btnCerrar, stage);
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

}

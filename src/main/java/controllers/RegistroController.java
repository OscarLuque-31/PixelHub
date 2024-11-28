package controllers;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import utils.NavigationUtils;
import utils.UtilsViews;

public class RegistroController {


    @FXML
    private Button btnRegister;

    @FXML
    private ComboBox<?> cmbGender;

    @FXML
    private ComboBox<?> cmbPlatform;

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

    private Stage stage;
    
    
    /**
     * Método para cargar la hoja de estilos (CSS) de la ventana de login
     */
    private void cargarCSS() {
        // Cargar el archivo de estilo para la ventana de login
        borderPane.getStylesheets().add(getClass().getResource("/styles/styleRegister.css").toExternalForm());
    }

//    /**
//     * Método que controla la navegación entre ventanas
//     */
//    private void navegacionEntreVentanas() {
//    	btnLogin.setOnMouseClicked(event -> NavigationUtils.navigateTo(stage, "/views/Biblioteca.fxml", "Biblioteca"));
//    	linkRegister.setOnMouseClicked(event -> NavigationUtils.navigateTo(stage, "/views/Registro.fxml", "Registro"));
//
//    }

    public void setStage(Stage stage) {
        this.stage = stage;
//        UtilsViews.funBtnsBar(btnMinimizar, btnMaximizar, btnCerrar, dragArea, stage);
        //Cargar el CSS de la ventana de login
        cargarCSS();
        // Inicializar imágenes
        initializeImagesBar();
        //Efectos de hover
        hoverEffect();
        //Navegación entre pantallas
//        navegacionEntreVentanas();
    }

   

    /**
     * Método que recopila todos los hoverEffect
     */
    public void hoverEffect() {
//        UtilsViews.hoverEffectText(linkPassword, "#0095FF", "#52A5E0");
//        UtilsViews.hoverEffectText(linkRegister, "#0095FF", "#52A5E0");
//        UtilsViews.hoverEffectButton(btnLogin, "#0095FF", "#52A5E0");
//        UtilsViews.hoverEffectButton(btnMinimizar, "#2a3b47", "#192229");
//        UtilsViews.hoverEffectButton(btnMaximizar, "#2a3b47", "#192229");
//        UtilsViews.hoverEffectButton(btnCerrar, "#2a3b47", "#192229");
    }

    /**
     * Método que inicializa las imágenes
     */
    public void initializeImagesBar() {
//        imgLogo.setImage(new Image(getClass().getResourceAsStream("/images/logoPixelHub.png")));
//        iconMinimizar.setImage(new Image(getClass().getResourceAsStream("/images/iconoMinimizar.png")));
//        iconMaximizar.setImage(new Image(getClass().getResourceAsStream("/images/iconoMaximizar.png")));
//        iconCerrar.setImage(new Image(getClass().getResourceAsStream("/images/iconoCerrar.png")));
    }
    
}

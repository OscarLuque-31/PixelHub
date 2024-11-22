package controllers;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import utils.UtilsViews;

public class LoginController {

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
    private Circle circleLogo;

    @FXML
    private ImageView imgLogo;
    

    //Método que se llama cuando la pantalla se carga
    @FXML
    public void initialize() {
    	//Carga la imagen
        Image imagen = new Image(getClass().getResourceAsStream("/images/logoPixelHub.png"));
        imgLogo.setImage(imagen);
        
        UtilsViews.hoverEffectText(linkPassword, "#0095FF", "#52A5E0");
        UtilsViews.hoverEffectText(linkRegister, "#0095FF", "#52A5E0");
        UtilsViews.hoverEffectButton(btnLogin, "#0095FF", "#52A5E0");
    }

}


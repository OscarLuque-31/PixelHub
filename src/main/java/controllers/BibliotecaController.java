package controllers;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utils.UtilsViews;

public class BibliotecaController {

	@FXML
	private Label lblBiblioteca;

	@FXML
	private Label lblRecomendaciones;

	@FXML
	private Label lblCerrarSesión;

	@FXML
	private Label lblBuscarJuegos;

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
	private VBox panelLateral;

	@FXML
	private Circle circleLogo;

	@FXML
	private ImageView imgLogo;

	@FXML
	private BorderPane borderPane;

	private Stage stage;

	public void setStage(Stage stage) {
		this.stage = stage;
		
        UtilsViews.funBtnsBar(btnMinimizar, btnMaximizar, btnCerrar, dragArea, stage);
        //Cargar el CSS de la ventana de login
        cargarCSS();
        // Inicializar imágenes
        initializeImagesBar();
        //Efectos de hover
        hoverEffect();
	}

	/**
     * Método para cargar la hoja de estilos (CSS) de la ventana de login
     */
    private void cargarCSS() {
        // Cargar el archivo de estilo para la ventana de login
        borderPane.getStylesheets().addAll(getClass().getResource("/styles/styleBiblioteca.css").toExternalForm(),
        							       getClass().getResource("/styles/styleTopBar.css").toExternalForm());
    }
	/**
	 * Método que recopila todos los hoverEffect
	 */
	public void hoverEffect() {
		UtilsViews.hoverEffectButton(btnMinimizar, "#2a3b47", "#192229");
		UtilsViews.hoverEffectButton(btnMaximizar, "#2a3b47", "#192229");
		UtilsViews.hoverEffectButton(btnCerrar, "#c63637", "#192229");
	}

	/**
	 * Método que inicializa las imagenes
	 */
	public void initializeImagesBar() {
		imgLogo.setImage(new Image(getClass().getResourceAsStream("/images/logoPixelHub.png")));
		iconMinimizar.setImage(new Image(getClass().getResourceAsStream("/images/iconoMinimizar.png")));
		iconMaximizar.setImage(new Image(getClass().getResourceAsStream("/images/iconoMaximizar.png")));
		iconCerrar.setImage(new Image(getClass().getResourceAsStream("/images/iconoCerrar.png")));
	}
}

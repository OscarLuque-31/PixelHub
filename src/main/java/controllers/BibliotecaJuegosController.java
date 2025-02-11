package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Usuario;
import utils.NavigationUtils;
import utils.UtilsViews;

public class BibliotecaJuegosController implements Initializable{


	@FXML
	private ImageView imgFiltro;

	@FXML
	private ImageView imgAdd;

	@FXML
	private ImageView imgLupa;

	@FXML
	private TextField textFieldBusqueda;

	@FXML
	private ImageView imgModoTarjeta;

	@FXML
	private ImageView imgModoLista;

	@FXML
	private ScrollPane scrollPane;

	@FXML
	private VBox contenedorJuegos;
	    

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		
		
		// Inicializar imágenes
		initializeImagesBar();
		//Efectos de hover
		hoverEffect();
		//Obtener y cargar los juegos
		navegacionEntrePestañas();		
	}
	
	/**
	 * Método que controla la navegación entre ventanas
	 */
	private void navegacionEntrePestañas() {

		imgAdd.setOnMouseClicked(event -> cambiarContenidoCentro("/views/AñadirJuegoBiblioteca.fxml"));
		
		

	}
	

	/**
	 * Método que cambia el contenido del centro
	 * @param rutaFXML
	 */
	private void cambiarContenidoCentro(String rutaFXML) {
	    try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));
	        BorderPane nuevoContenido = loader.load();
            BibliotecaController.getInstance().getBorderPaneCentro().setCenter(nuevoContenido);
	    } catch (Exception e) {
	        e.printStackTrace();
	        Label errorLabel = new Label("Error cargando contenido: " + rutaFXML);
	        errorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 16;");
            BibliotecaController.getInstance().getBorderPaneCentro().setCenter(errorLabel);
	    }
	}
	

	/**
	 * Método que recopila todos los hoverEffect
	 */
	public void hoverEffect() {
//		UtilsViews.hoverEffectButton(btnMinimizar, "#2a3b47", "#192229");
//		UtilsViews.hoverEffectButton(btnMaximizar, "#2a3b47", "#192229");
//		UtilsViews.hoverEffectButton(btnCerrar, "#c63637", "#192229");
//		UtilsViews.hoverEffectButton(btnBiblioteca, "#415A6C", "#212E36");
//		UtilsViews.hoverEffectButton(btnBuscarJuegos, "#415A6C", "#212E36");
//		UtilsViews.hoverEffectButton(btnCerrarSesion, "#415A6C", "#212E36");
//		UtilsViews.hoverEffectButton(btnRecomendaciones, "#415A6C", "#212E36");
	}

	

	
	/**
	 * Método que inicializa las imagenes
	 */
	public void initializeImagesBar() {
		imgFiltro.setImage(new Image(getClass().getResourceAsStream("/images/filtro.png")));
		imgAdd.setImage(new Image(getClass().getResourceAsStream("/images/CirculoMas.png")));
		imgModoLista.setImage(new Image(getClass().getResourceAsStream("/images/modoLista.png")));
		imgModoTarjeta.setImage(new Image(getClass().getResourceAsStream("/images/modoTarjeta.png")));
		imgLupa.setImage(new Image(getClass().getResourceAsStream("/images/lupa.png")));
	}





}

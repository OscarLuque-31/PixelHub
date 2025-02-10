package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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


	private boolean vistaLista;


	public void setVistaLista(Boolean value) {
		this.vistaLista = value;
	}
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		
		this.vistaLista = false;
		
		// Inicializar imágenes
		initializeImagesBar();
		//Efectos de hover
		hoverEffect();
		//Obtener y cargar los juegos
//		showGames(stage);
		
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

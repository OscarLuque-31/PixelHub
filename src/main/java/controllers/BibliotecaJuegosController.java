package controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import dao.JuegosBibliotecaDaoImpl;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Game;
import models.JuegosBiblioteca;
import models.Usuario;
import utils.APIUtils;
import utils.HibernateUtil;
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
	
	@FXML
	private BorderPane borderPaneCentro;

	

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

		imgAdd.setOnMouseClicked(event -> cambiarContenidoCentro("/views/AnadirJuegoBiblioteca.fxml"));
		
		

	}
	

	/**
	 * Método que cambia el contenido del centro
	 * @param rutaFXML
	 */
	private void cambiarContenidoCentro(String rutaFXML) {
	    try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));
	        BorderPane nuevoContenido = loader.load();
	        borderPaneCentro.setCenter(nuevoContenido);
	    } catch (Exception e) {
	        e.printStackTrace();
	        Label errorLabel = new Label("Error cargando contenido: " + rutaFXML);
	        errorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 16;");
	        borderPaneCentro.setCenter(errorLabel);
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

	public void mostrarDetallesJuego(JuegosBiblioteca game) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/GameDetails.fxml"));
			HBox gameDetails = loader.load();

			// Obtener el controlador del detalle
			//
			GameDetailsBibliotecaController controller = loader.getController();
			controller.setGameDetails(game);

			// Reemplazar el contenido del contenedor principal con la nueva vista
			contenedorJuegos.getChildren().clear();
			contenedorJuegos.getChildren().add(gameDetails);

		} catch (Exception e) {
			e.printStackTrace();
			contenedorJuegos.getChildren().add(new Label("Error al cargar los detalles del juego."));
		}
	}


	@FXML
    void buscarJuegos(MouseEvent event) {
		showGames(textFieldBusqueda.getText()); 
    }
	
	private void showGames(String title) {
//	    scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
//	    scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

	        try {
	        	
	        	JuegosBibliotecaDaoImpl juegosBibliotecaDaoImpl = new JuegosBibliotecaDaoImpl(HibernateUtil.getSession());
	        	
	            List<JuegosBiblioteca> games = juegosBibliotecaDaoImpl.searchAll();
	        	
	        	List<VBox> bloques = new ArrayList<>();
	        	for (JuegosBiblioteca game:games) {
	        		bloques.add(crearBloqueVideojuego(game));
				}
	        	
	        	mostrarJuegos(bloques);

	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	}
	
	private void mostrarJuegos(List<VBox> bloquesJuegos) {
	    contenedorJuegos.getChildren().clear();

	    HBox filaActual = new HBox();
	    filaActual.setSpacing(20);
	    filaActual.setAlignment(Pos.CENTER); 

	    int contador = 0;
	    for (VBox bloque:bloquesJuegos) {
	        filaActual.getChildren().add(bloque);
	        contador++;

	        if (contador % 3 == 0) {
	            contenedorJuegos.setSpacing(30);
	            contenedorJuegos.setAlignment(Pos.CENTER); 
	            contenedorJuegos.getChildren().add(filaActual);

	            filaActual = new HBox();
	            filaActual.setSpacing(20);
	            filaActual.setAlignment(Pos.CENTER); 
	        }
	    }
	    if (!filaActual.getChildren().isEmpty()) {
	        contenedorJuegos.getChildren().add(filaActual);
	    }
	}
	
	private VBox crearBloqueVideojuego(JuegosBiblioteca game) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/GameItemCuadriculaBiblioteca.fxml"));
			VBox gameItem = loader.load();

			// Obtener el controlador del FXML
			GameItemCuadriculaBibliotecaController controller = loader.getController();
			controller.setGameData(game);
			controller.setGamesController(this);

			return gameItem;
		} catch (Exception e) {
			e.printStackTrace();
			return new VBox(new Label("Error cargando juego"));
		}
	}

}

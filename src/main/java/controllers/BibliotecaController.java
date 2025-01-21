package controllers;

import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Usuario;
import models.Game;
import utils.APIUtils;
import utils.NavigationUtils;
import utils.UtilsViews;

public class BibliotecaController {

	@FXML
	private BorderPane borderPane;

	@FXML
	private VBox panelLateralContainer;

	@FXML
	private GridPane panelLateral;

	@FXML
	private Button btnBiblioteca;

	@FXML
	private Button btnRecomendaciones;

	@FXML
	private Button btnBuscarJuegos;

	@FXML
	private Button btnCerrarSesion;

	@FXML
	private HBox dragArea;

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

	@FXML
	private Label lblTitulo;

	@FXML
	private Label lblNombreUsuario;

	@FXML
	private ImageView imgFiltro;

	@FXML
	private ImageView imgAdd;

	@FXML
	private ImageView imgUsuario;

	@FXML
	private ImageView imgLupa;

	@FXML
	private TextField textFieldBusqueda;

	@FXML
	private ImageView imgModoTarjeta;

	@FXML
	private ImageView imgModoLista;
	
	@FXML
	private VBox contenedorJuegos;
	
	@FXML
    private ScrollPane scrollPane;

	private Stage stage;

	private Usuario usuario;
	
	private boolean vistaLista;

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public void setVistaLista(Boolean value) {
		this.vistaLista = value;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
		this.vistaLista = false;

		UtilsViews.funBtnsBar(btnMinimizar, btnMaximizar, btnCerrar, dragArea, stage);
		//Cargar el CSS de la ventana de login
		cargarCSS();
		// Inicializar imágenes
		initializeImagesBar();
		//Efectos de hover
		hoverEffect();
		//Navegacion entre pantallas
		navegacionEntreVentanas();
		//Obtener y cargar los juegos
		showGames();

		// Usa el objeto usuario solo si ya ha sido inicializado
		if (usuario != null) {
			lblNombreUsuario.setText(usuario.getUsername());
		} else {
			System.err.println("Usuario no inicializado antes de usar setStage.");
		}
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
		UtilsViews.hoverEffectButton(btnBiblioteca, "#415A6C", "#212E36");
		UtilsViews.hoverEffectButton(btnBuscarJuegos, "#415A6C", "#212E36");
		UtilsViews.hoverEffectButton(btnCerrarSesion, "#415A6C", "#212E36");
		UtilsViews.hoverEffectButton(btnRecomendaciones, "#415A6C", "#212E36");
	}

	/**
	 * Método que controla la navegación entre ventanas
	 */
	private void navegacionEntreVentanas() {
		btnCerrarSesion.setOnMouseClicked(event -> NavigationUtils.navigateTo(stage, "/views/Login.fxml"));
		imgModoLista.setOnMouseClicked(event -> {showGames();setVistaLista(true);});
		imgModoTarjeta.setOnMouseClicked(event -> {showGames();setVistaLista(false);});
	}

	/**
	 * Método que inicializa las imagenes
	 */
	public void initializeImagesBar() {
		imgLogo.setImage(new Image(getClass().getResourceAsStream("/images/logoPixelHub.png")));
		iconMinimizar.setImage(new Image(getClass().getResourceAsStream("/images/iconoMinimizar.png")));
		iconMaximizar.setImage(new Image(getClass().getResourceAsStream("/images/iconoMaximizar.png")));
		iconCerrar.setImage(new Image(getClass().getResourceAsStream("/images/iconoCerrar.png")));
		imgAdd.setImage(new Image(getClass().getResourceAsStream("/images/CirculoMas.png")));
		imgFiltro.setImage(new Image(getClass().getResourceAsStream("/images/filtro.png")));
		imgLupa.setImage(new Image(getClass().getResourceAsStream("/images/lupa.png")));
		imgModoLista.setImage(new Image(getClass().getResourceAsStream("/images/modoLista.png")));
		imgModoTarjeta.setImage(new Image(getClass().getResourceAsStream("/images/modoTarjeta.png")));
		imgUsuario.setImage(new Image(getClass().getResourceAsStream("/images/iconoUsuario.png")));

	}
	
	/**
	 * Método que obtiene los juegos de la API y los muestra
	 */
	private void showGames() {
		scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		
		try {
			List<Game> listaJuegos = APIUtils.getGames();
			contenedorJuegos.getChildren().clear();

			if (!vistaLista) {
				HBox filaActual = new HBox();
				filaActual.setSpacing(20);
				filaActual.setAlignment(Pos.CENTER); 
				
				int contador = 0;
				for (Game juego:listaJuegos) {
					VBox bloqueJuego = crearBloqueVideojuego(juego);
					bloqueJuego.setMaxWidth(Double.MAX_VALUE);
					filaActual.getChildren().add(bloqueJuego);
					contador++;

					if (contador % 3 == 0) {
						contenedorJuegos.setSpacing(20);
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
			}else {
				for (Game juego:listaJuegos) {
			        contenedorJuegos.getChildren().add(crearFilaVideojuego(juego));
			    }
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private VBox crearBloqueVideojuego(Game juego) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/GameItemCuadricula.fxml"));
			VBox gameItem = loader.load();

			// Obtener el controlador del FXML
			GameItemCuadriculaController controller = loader.getController();
			controller.setGameData(juego); // Método para asignar los datos del juego
			controller.setGamesController(this);
			
			return gameItem;
		} catch (Exception e) {
			e.printStackTrace();
			return new VBox(new Label("Error cargando juego"));
		}
	}

	private HBox crearFilaVideojuego(Game juego) {
	    try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/GameItemLista.fxml"));
	        HBox gameItem = loader.load();

	        GameItemListaController controller = loader.getController();
	        controller.setGameData(juego);

	        return gameItem;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return new HBox(new Label("Error cargando juego"));
	    }
	}
	
}

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
	private BorderPane borderPaneCentro;

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
	private VBox contenedorJuegos;

	@FXML
	private ScrollPane scrollPane;

	private Stage stage;

	private static Usuario usuario;

	private boolean vistaLista;

	public void setUsuario(Usuario usuario) {
		BibliotecaController.usuario = usuario;
	}
	
	public static Usuario getUsuario() {
		return usuario;
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
		//Navegacion entre pestañas
		//Por defecto estará en biblioteca
		cambiarPestana(btnBiblioteca,"/views/BibliotecaJuegos.fxml");
		navegacionEntrePestañas();
		//Obtener y cargar los juegos
//		showGames(stage);

		// Usa el objeto usuario solo si ya ha sido inicializado
		if (usuario != null) {
			lblNombreUsuario.setText(usuario.getUsername());
		} else {
			System.err.println("Usuario no inicializado antes de usar setStage.");
		}
	}

	/**
	 * Método para cargar la hoja de estilos (CSS) 
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
		
		

	    // Hover normal, pero solo si el botón NO está activo
	    btnBiblioteca.setOnMouseEntered(e -> {
	        if (!btnBiblioteca.getStyleClass().contains("btn-activo")) {
	            btnBiblioteca.setStyle("-fx-background-color: #212E36;");
	        }
	    });
	    btnBiblioteca.setOnMouseExited(e -> {
	        if (!btnBiblioteca.getStyleClass().contains("btn-activo")) {
	            btnBiblioteca.setStyle("-fx-background-color: transparent;");
	        }
	    });

	    btnBuscarJuegos.setOnMouseEntered(e -> {
	        if (!btnBuscarJuegos.getStyleClass().contains("btn-activo")) {
	            btnBuscarJuegos.setStyle("-fx-background-color: #212E36;");
	        }
	    });
	    btnBuscarJuegos.setOnMouseExited(e -> {
	        if (!btnBuscarJuegos.getStyleClass().contains("btn-activo")) {
	            btnBuscarJuegos.setStyle("-fx-background-color: transparent;");
	        }
	    });

	    btnRecomendaciones.setOnMouseEntered(e -> {
	        if (!btnRecomendaciones.getStyleClass().contains("btn-activo")) {
	            btnRecomendaciones.setStyle("-fx-background-color: #212E36;");
	        }
	    });
	    btnRecomendaciones.setOnMouseExited(e -> {
	        if (!btnRecomendaciones.getStyleClass().contains("btn-activo")) {
	            btnRecomendaciones.setStyle("-fx-background-color: transparent;");
	        }
	    });
	}

	/**
	 * Método que controla la navegación entre ventanas
	 */
	private void navegacionEntrePestañas() {

		btnBiblioteca.setOnMouseClicked(event -> cambiarPestana(btnBiblioteca,"/views/BibliotecaJuegos.fxml"));
		btnRecomendaciones.setOnMouseClicked(event -> cambiarPestana(btnRecomendaciones,"/views/RecomendacionesJuegos.fxml"));
		btnBuscarJuegos.setOnMouseClicked(event -> cambiarPestana(btnBuscarJuegos,"/views/BuscarJuegos.fxml"));


		btnCerrarSesion.setOnMouseClicked(event -> NavigationUtils.navigateTo(stage, "/views/Login.fxml"));
		lblNombreUsuario.setOnMouseClicked(event -> cambiarContenidoCentro("/views/Perfil.fxml"));
		
	}
	
	/**
	 * Método que cambia de pestaña
	 * @param botonSeleccionado
	 * @param rutaFXML
	 */
	private void cambiarPestana(Button botonSeleccionado, String rutaFXML) {
	    // Eliminar la clase "btn-activo" de todos los botones
	    btnBiblioteca.getStyleClass().remove("btn-activo");
	    btnRecomendaciones.getStyleClass().remove("btn-activo");
	    btnBuscarJuegos.getStyleClass().remove("btn-activo");

	    // Resetear estilos para evitar colores manuales previos
	    btnBiblioteca.setStyle("");
	    btnRecomendaciones.setStyle("");
	    btnBuscarJuegos.setStyle("");

	    // Agregar la clase "btn-activo" solo al botón seleccionado
	    botonSeleccionado.getStyleClass().add("btn-activo");

	    // Cambiar la vista
	    cambiarContenidoCentro(rutaFXML);
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
	 * Método que inicializa las imagenes
	 */
	public void initializeImagesBar() {
		imgLogo.setImage(new Image(getClass().getResourceAsStream("/images/logoPixelHub.png")));
		iconMinimizar.setImage(new Image(getClass().getResourceAsStream("/images/iconoMinimizar.png")));
		iconMaximizar.setImage(new Image(getClass().getResourceAsStream("/images/iconoMaximizar.png")));
		iconCerrar.setImage(new Image(getClass().getResourceAsStream("/images/iconoCerrar.png")));
		imgUsuario.setImage(new Image(getClass().getResourceAsStream("/images/iconoUsuario.png")));
	}

	/**
	 * Método que obtiene los juegos de la API y los muestra
	 */
	private void showGames(Stage stage) {
		scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

		try {
			List<Game> listaJuegos = APIUtils.getGames();
			contenedorJuegos.getChildren().clear();

			if (!vistaLista) {
				HBox filaActual = new HBox();
				filaActual.setSpacing(20);
				filaActual.setAlignment(Pos.CENTER); 

				int juegosPorFila = stage.isMaximized() ? 4 : 3;

				int contador = 0;
				for (Game juego:listaJuegos) {
					VBox bloqueJuego = crearBloqueVideojuego(juego);
					bloqueJuego.setMaxWidth(Double.MAX_VALUE);
					filaActual.getChildren().add(bloqueJuego);
					contador++;

					if (contador % juegosPorFila == 0) {
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

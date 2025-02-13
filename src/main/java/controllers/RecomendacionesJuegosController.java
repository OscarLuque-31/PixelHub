package controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import dao.PreferenciasDaoImpl;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import models.Game;
import models.Preferencias;
import models.Usuario;
import utils.APIUtils;
import utils.HibernateUtil;
import utils.LogicaUtils;

public class RecomendacionesJuegosController implements Initializable {

	@FXML
	private BorderPane contenedorJuegos;

	@FXML
	private ScrollPane scrollPane;
	
	@FXML
    private StackPane stackPane;
	
	@FXML
	private VBox pantallaCarga;
	
	private Map<String, Integer> platforms;
	private Map<String, Integer> genres;

	private List<String> plataformas;
	private List<String> generos;
	private PreferenciasDaoImpl preferenciasDao;

	private VBox listaDeJuegosVBox;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		listaDeJuegosVBox = new VBox();
		listaDeJuegosVBox.setSpacing(20);  // Espaciado entre bloques de juegos
		listaDeJuegosVBox.setAlignment(Pos.CENTER); 

		scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

		scrollPane.setContent(listaDeJuegosVBox);

		scrollPane.setStyle("-fx-background: #192229; -fx-background-color: #192229;");

		setMaps();

		getRecomendaciones();

		cargarCategorias();
	}

	private void setMaps() {
		LogicaUtils.setMapContent();
		platforms = LogicaUtils.platforms;
		genres = LogicaUtils.genres;
	}

	private void getRecomendaciones() {
		preferenciasDao = new PreferenciasDaoImpl(HibernateUtil.getSession());
		int id = BibliotecaController.getUsuario().getId();

		List<Preferencias> preferencias = preferenciasDao.getPreferenciasByUsuario(id);
		plataformas = new ArrayList<>();
		generos = new ArrayList<>();

		for (Preferencias pref:preferencias) {
			if (pref.getTipo() == 1){
				generos.add(pref.getPreferencia());
			}else if (pref.getTipo() == 2) {
				plataformas.add(pref.getPreferencia());
			}
		}
	}

	private void cargarCategorias() {
		Platform.runLater(() -> pantallaCarga.setVisible(true));
		
		ExecutorService executor = Executors.newFixedThreadPool(2); 
		List<Future<?>> futures = new ArrayList<>();

		executor.submit(() -> cargarJuegosPorCategoria("Los más populares", null, null, "-rating"));

		for (String genero:generos) {
			executor.submit(() -> cargarJuegosPorCategoria("Juegos de " + genero + " mejor valorados", null, genres.get(genero), "-rating"));
		}

		for (String plataforma:plataformas) {
			executor.submit(() -> cargarJuegosPorCategoria("Juegos de " + plataforma + " mejor valorados", platforms.get(plataforma), null, "-rating"));
		}

		executor.shutdown();
		
		new Thread(() -> {
	        for (Future<?> future:futures) {
	            try {
	                future.get();
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	        Platform.runLater(() -> pantallaCarga.setVisible(false));
	    }).start();
	}

	private void cargarJuegosPorCategoria(String titulo, Integer platform, Integer genre, String order) {
		try {
			List<Game> listaJuegos = APIUtils.getGames(null, platform, genre, order, 12);
			VBox juegosConTitulo = crearFilaJuegosConTitulo(titulo, listaJuegos);

			Platform.runLater(() -> mostrarJuegos(juegosConTitulo));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private VBox crearFilaJuegosConTitulo(String titulo, List<Game> listaJuegos) {
		VBox vboxCompleto = new VBox();
		vboxCompleto.setAlignment(Pos.CENTER);
		vboxCompleto.setSpacing(10);
		vboxCompleto.setPadding(new Insets(0, 0, 0, 20));

		// Crear y añadir el título de la categoría
		Label labelTitulo = new Label(titulo);
		labelTitulo.setStyle("-fx-font-size: 22px; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10px;");
		labelTitulo.setMaxWidth(Double.MAX_VALUE);

		vboxCompleto.getChildren().add(labelTitulo);

		// Crear un HBox para las imágenes de los juegos
		HBox hboxJuegos = new HBox();
		hboxJuegos.setSpacing(20);
		hboxJuegos.setAlignment(Pos.CENTER_LEFT);
		hboxJuegos.setPadding(new Insets(0, 0, 0, 20));

		for (Game game : listaJuegos) {
			ImageView imageViewJuego = new ImageView(new Image(game.getBackgroundImage()));
			imageViewJuego.setFitWidth(250);
			imageViewJuego.setFitHeight(200);
			
			// Crear un rectángulo con bordes redondeados
			Rectangle clip = new Rectangle(imageViewJuego.getFitWidth(), imageViewJuego.getFitHeight());
			clip.setArcWidth(15);
			clip.setArcHeight(15);
			imageViewJuego.setClip(clip);
			
			hboxJuegos.getChildren().add(imageViewJuego);

			imageViewJuego.setCursor(Cursor.HAND);
			imageViewJuego.setOnMouseClicked((MouseEvent event) -> {
				showGameDetails(game.getId());
			});

		}

		ScrollPane scrollPaneFila = new ScrollPane(hboxJuegos);
		scrollPaneFila.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		scrollPaneFila.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		scrollPaneFila.setFitToHeight(true);
		scrollPaneFila.setPannable(true); // Permitir arrastrar con el mouse
		scrollPaneFila.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
		//scrollPaneFila.lookup(".scroll-bar").setStyle("-fx-opacity: 0; -fx-background-color: transparent;");

		scrollPaneFila.setPrefWidth(scrollPane.getWidth() - 20);

		vboxCompleto.getChildren().add(scrollPaneFila);

		return vboxCompleto;
	}

	private void mostrarJuegos(VBox juegosConTitulo) {
		listaDeJuegosVBox.getChildren().add(juegosConTitulo);
	}

	private void showGameDetails(int gameId) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/GameDetails.fxml"));

			GameDetailsController controller = loader.getController();
			Game game = APIUtils.getGameDetails(gameId);
			List<String> screenshots = APIUtils.getGameScreenshots(gameId);
			List<Game> dlcs = APIUtils.getGameDLCs(gameId);
			mostrarDetallesJuego(game, screenshots, dlcs);
			controller.setGameDetails(game, screenshots, dlcs); // Pasar el objeto Game

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void mostrarDetallesJuego(Game game, List<String> screenshots, List<Game> dlcs) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/GameDetails.fxml"));
			HBox gameDetails = loader.load();

			// Obtener el controlador del detalle
			GameDetailsController controller = loader.getController();
			controller.setGameDetails(game, screenshots, dlcs);

			// Reemplazar el contenido del contenedor principal con la nueva vista
			contenedorJuegos.getChildren().clear();
			contenedorJuegos.getChildren().add(gameDetails);

		} catch (Exception e) {
			e.printStackTrace();
			contenedorJuegos.getChildren().add(new Label("Error al cargar los detalles del juego."));
		}
	}

}
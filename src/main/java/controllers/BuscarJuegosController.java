package controllers;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Game;
import utils.APIUtils;
import utils.LogicaUtils;
import utils.NavigationUtils;
import utils.UtilsViews;

public class BuscarJuegosController implements Initializable {

	@FXML
    private StackPane stackPane;

    @FXML
    private VBox loadingPane; 
	
	@FXML
	private ImageView imgLupa;

	@FXML
	private TextField textFieldBusqueda;

	@FXML
	private ComboBox<String> comboBoxOrdenar;

	@FXML
	private ComboBox<String> comboBoxPlataforma;

	@FXML
	private ComboBox<String> comboBoxGenero;

	@FXML
	private ScrollPane scrollPane;

	@FXML
	private VBox contenedorJuegos;

	private Map<String, Integer> platforms;
	private Map<String, Integer> genres;
	private Map<String, String> order;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Inicializar imágenes
		initializeImagesBar();
		// Inicializar combos
		setComboContent();
		// Establecer plataformas y géneros
		setMaps();
		// Traer juegos por defecto
		buscarJuegos(null);
	}

	private void setMaps() {
		LogicaUtils.setMapContent();
		platforms = LogicaUtils.platforms;
		genres = LogicaUtils.genres;
		order = LogicaUtils.order;		
	}

	private void setComboContent() {
		comboBoxPlataforma.getItems().addAll("PC", "PlayStation", "Xbox",
				"iOS", "Apple Macintosh", "Linux",
				"Nintendo", "Android", "Web");
		comboBoxGenero.getItems().addAll("Acción", "Indie", "Aventura", "RPG", "Estrategia", 
				"Shooter", "Casual", "Simulación", "Puzzle", "Arcade", 
				"Plataformas", "Multijugador", "Carreras", "Deportes", 
				"Lucha", "Familiar", "Juegos de mesa", "Educativo", "Cartas");
		comboBoxOrdenar.getItems().addAll("A - Z", "Z - A", "Newest - Oldest", "Oldest - Newest", "Best - Worst", "Worst - Best");
	}


	/**
	 * Método que inicializa las imagenes
	 */
	public void initializeImagesBar() {
		imgLupa.setImage(new Image(getClass().getResourceAsStream("/images/lupa.png")));
	}


	private void showGames(String title, Integer platform, Integer genre, String order) {
	    scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
	    scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
	    
	    Platform.runLater(() -> loadingPane.setVisible(true));

	    // Crear un pool de hilos con el número óptimo de núcleos del sistema
	    ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

	    // Hilo para obtener los juegos de la API
	    executor.submit(() -> {
	        try {
	            List<Game> listaJuegos = APIUtils.getGames(title, platform, genre, order, 30);

	            // Crear una lista de tareas para procesar cada juego en paralelo
	            List<Future<VBox>> futures = listaJuegos.stream()
	                .map(juego -> executor.submit(() -> crearBloqueVideojuego(juego)))
	                .toList();

	            // Obtener los resultados de los hilos
	            List<VBox> bloquesJuegos = futures.stream()
	                .map(future -> {
	                    try {
	                        return future.get(); // Obtener el resultado del hilo
	                    } catch (Exception e) {
	                        e.printStackTrace();
	                        return new VBox(new Label("Error procesando juego"));
	                    }
	                })
	                .toList();

	            Platform.runLater(() -> {
                    mostrarJuegos(bloquesJuegos);
                    loadingPane.setVisible(false);
                });

	        } catch (Exception e) {
	            e.printStackTrace();
                Platform.runLater(() -> loadingPane.setVisible(false));
	        } finally {
	            executor.shutdown(); // Cerrar el pool de hilos
	        }
	    });
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

	private VBox crearBloqueVideojuego(Game juego) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/GameItemCuadricula.fxml"));
			VBox gameItem = loader.load();

			// Obtener el controlador del FXML
			GameItemCuadriculaController controller = loader.getController();
			controller.setGameData(juego);
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

	@FXML
	void buscarJuegos(MouseEvent event) {
		showGames(textFieldBusqueda.getText(), platforms.get(comboBoxPlataforma.getValue()), genres.get(comboBoxGenero.getValue()), order.get(comboBoxOrdenar.getValue())); 
	}

}

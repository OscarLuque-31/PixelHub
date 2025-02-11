package controllers;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Game;
import utils.APIUtils;
import utils.NavigationUtils;
import utils.UtilsViews;

public class BuscarJuegosController implements Initializable {

	@FXML
	private ImageView imgLupa;

	@FXML
	private TextField textFieldBusqueda;

	@FXML
	private ComboBox<?> comboBoxOrdenar;

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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Inicializar imágenes
		initializeImagesBar();
		// Inicializar combos
		setComboContent();
		// Establecer plataformas y géneros
		setMapContent();
		// Efectos de hover
		hoverEffect();
	}

	private void setMapContent() {
		platforms = new HashMap<>();
		genres = new HashMap<>();

		platforms.put("PC", 1);
		platforms.put("PlayStation", 2);
		platforms.put("Xbox", 3);
		platforms.put("iOS", 4);
		platforms.put("Apple Macintosh", 5);
		platforms.put("Linux", 6);
		platforms.put("Nintendo", 7);
		platforms.put("Android", 8);
		platforms.put("Web", 14);

		genres.put("Acción", 4);
		genres.put("Indie", 51);
		genres.put("Aventura", 3);
		genres.put("RPG", 5);
		genres.put("Estrategia", 10);
		genres.put("Shooter", 2);
		genres.put("Casual", 40);
		genres.put("Simulación", 14);
		genres.put("Puzzle", 7);
		genres.put("Arcade", 11);
		genres.put("Plataformas", 83);
		genres.put("Multijugador", 59);
		genres.put("Carreras", 1);
		genres.put("Deportes", 15);
		genres.put("Lucha", 6);
		genres.put("Familiar", 19);
		genres.put("Juegos de mesa", 28);
		genres.put("Educativo", 34);
		genres.put("Cartas", 17);
	}

	private void setComboContent() {
		comboBoxPlataforma.getItems().addAll("PC", "PlayStation", "Xbox",
				"iOS", "Apple Macintosh", "Linux",
				"Nintendo", "Android", "Web");
		comboBoxGenero.getItems().addAll("Acción", "Indie", "Aventura", "RPG", "Estrategia", 
				"Shooter", "Casual", "Simulación", "Puzzle", "Arcade", 
				"Plataformas", "Multijugador", "Carreras", "Deportes", 
				"Lucha", "Familiar", "Juegos de mesa", "Educativo", "Cartas");
	}

	/**
	 * Método que recopila todos los hoverEffect
	 */
	public void hoverEffect() {
		// UtilsViews.hoverEffectButton(btnMinimizar, "#2a3b47", "#192229");
		// UtilsViews.hoverEffectButton(btnMaximizar, "#2a3b47", "#192229");
		// UtilsViews.hoverEffectButton(btnCerrar, "#c63637", "#192229");
		// UtilsViews.hoverEffectButton(btnBiblioteca, "#415A6C", "#212E36");
		// UtilsViews.hoverEffectButton(btnBuscarJuegos, "#415A6C", "#212E36");
		// UtilsViews.hoverEffectButton(btnCerrarSesion, "#415A6C", "#212E36");
		// UtilsViews.hoverEffectButton(btnRecomendaciones, "#415A6C", "#212E36");
	}

	/**
	 * Método que inicializa las imagenes
	 */
	public void initializeImagesBar() {
		imgLupa.setImage(new Image(getClass().getResourceAsStream("/images/lupa.png")));
	}

	/**
	 * Método que obtiene los juegos de la API y los muestra
	 * @param title 
	 * @param genre 
	 * @param platfom 
	 */
	private void showGames(String title, Integer platform, Integer genre) {
		scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

		try {
			List<Game> listaJuegos = APIUtils.getGames(title, platform, genre);
			contenedorJuegos.getChildren().clear();

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

	public void mostrarDetallesJuego(Game game) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/GameDetails.fxml"));
			HBox gameDetails = loader.load();

			// Obtener el controlador del detalle
			GameDetailsController controller = loader.getController();
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
		showGames(textFieldBusqueda.getText(), platforms.get(comboBoxPlataforma.getValue()), genres.get(comboBoxGenero.getValue())); 
	}

}

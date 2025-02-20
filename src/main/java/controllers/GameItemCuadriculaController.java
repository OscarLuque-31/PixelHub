package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Game;
import utils.APIUtils;

public class GameItemCuadriculaController implements Initializable {

	@FXML
	private ImageView gameImage;
	@FXML
	private HBox gamePlatforms;
	@FXML
	private Label gameTitle;
	@FXML
	private Label gameRating;
	@FXML
	private ImageView addButton;
	@FXML
	private VBox mainBox;

	private int gameId;
	private BuscarJuegosController buscarJuegosController;

	public ImageView getImageView() {
		return this.gameImage;
	}

	/**
	 * Método que agrega en los campos correspondientes los datos del juego
	 * @param game
	 */
	public void setGameData(Game game) {
		mainBox.getStylesheets().add(getClass().getResource("/styles/styleGameItemCuadricula.css").toExternalForm());

		gameId = game.getId();
		gameTitle.setText(game.getName());
		gameRating.setText("⭐ " + game.getRating());
		gamePlatforms.getChildren().add(crearBloquePlataformas(game));

		try {
			gameImage.setImage(new Image(game.getBackgroundImage()));
		} catch (Exception e) {
			gameImage.setImage(new Image(getClass().getResource("/images/error.png").toExternalForm()));
		}
		gameImage.setPreserveRatio(false);
		gameImage.setSmooth(true);
		gameImage.setCache(true);

		double width = gameImage.getFitWidth();
		double height = gameImage.getFitHeight();

		// Crea un clip con una ruta SVG para redondear solo las esquinas superiores
		SVGPath clip = new SVGPath();
		clip.setContent("M0," + 15 + " " + "Q0,0 " + 15 + ",0 " + "L" + (width - 15) + ",0 " + "Q" + width + ",0 "
				+ width + "," + 15 + "L" + width + "," + height + "L0," + height + "Z");

		gameImage.setClip(clip);

		addButton.setImage(new Image(getClass().getResource("/images/CirculoMas.png").toExternalForm()));
	}
	
	/**
	 * Método que muestra los juegos creando los bloques o card de cada juego
	 * @param game
	 * @return
	 */
	private HBox crearBloquePlataformas(Game game) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Plataformas.fxml"));
			HBox plataformas = loader.load();

			// Obtiene el controlador del FXML
			PlataformasController controller = loader.getController();
			controller.setPlataformasFoto(game);

			return plataformas;
		} catch (Exception e) {
			e.printStackTrace();
			return new HBox(new Label("Error cargando juego"));
		}
	}

	public void setGamesController(BuscarJuegosController controller) {
		this.buscarJuegosController = controller;
	}

	@FXML
	void showGameDetails() {
		if (buscarJuegosController != null) {
			try {
				// Obtiene los detalles del juego de la api y los muestra
				Game game = APIUtils.getGameDetails(gameId);
				List<String> screenshots = APIUtils.getGameScreenshots(gameId);
				List<Game> dlcs = APIUtils.getGameDLCs(gameId);
				buscarJuegosController.mostrarDetallesJuego(game, screenshots, dlcs);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Asocia el clic del botón a la función que abrirá la ventana emergente
		addButton.setOnMouseClicked(event -> accionBoton());
	}

	private void accionBoton() {
		// Si el gameId es correcto entra
		if (gameId > 0) {
			try {
				// Obtiene los detalles del juego usando gameId
				Game game = APIUtils.getGameDetails(gameId);

				// Llama a la función que abre la ventana emergente y pasar el objeto game
				abrirVentanaAñadirJuego(game);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("El gameId no es válido.");
		}
	}

	/**
	 * Método que abre la ventana emergente para añadir un juego en la biblioteca
	 * @param game
	 */
	private void abrirVentanaAñadirJuego(Game game) {
		// Crea un nuevo Stage para la ventana emergente
		Stage stage = new Stage();
		stage.initStyle(StageStyle.TRANSPARENT); 

		if (game != null) {
			 // Pasa el objeto Game al controlador
			AnadirJuegoBuscarJuegosController.setGame(game);
		} else {
			System.out.println("Game is null");
		}
		// Crea un FXMLLoader para cargar la vista del formulario de añadir juego
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AnadirJuegoBuscarJuegos.fxml"));

		try {
			VBox root = loader.load();

			AnadirJuegoBuscarJuegosController controller = loader.getController();

			// Crea la escena con fondo transparente
			Scene scene = new Scene(root, Color.TRANSPARENT);
			stage.setScene(scene);


			// Centra la ventana cuando se muestre
			controller.setPrimaryStage(stage); 

			// Muestra la ventana emergente
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

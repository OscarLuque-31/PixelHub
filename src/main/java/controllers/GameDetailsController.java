package controllers;

import java.io.IOException;

import org.jsoup.Jsoup;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import models.Game;
import utils.APIUtils;
import utils.NavigationUtils;

public class GameDetailsController {

	@FXML private ImageView arrowBack;
	@FXML private VBox detailsContainer;
	@FXML private VBox gameDLC;
	@FXML private TextFlow gameDescription;
	@FXML private Text descriptionText;
	@FXML private ImageView gameImage;
	@FXML private HBox gamePlatforms;
	@FXML private Label gameRating;
	@FXML private ImageView gameScreenshots;
	@FXML private Label gameTitle;
	@FXML private ImageView next;
	@FXML private ImageView previous;

	private BuscarJuegosController buscarJuegosController;
	private int gameId;

	public void setGameDetails(Game game) {
		gameTitle.setText(game.getName());
		descriptionText.setText(extractSpanishDescription(game.getDescription()));
		gameRating.setText("⭐ " + game.getRating());

		gamePlatforms.getChildren().add(crearBloquePlataformas(game));

		try {
			gameImage.setImage(new Image(game.getBackgroundImage()));
		} catch (Exception e) {
			gameImage.setImage(new Image(getClass().getResource("/images/error.png").toExternalForm()));
		}

		gameImage.setFitWidth(300); // Ancho fijo
		gameImage.setFitHeight(250); // Alto fijo
		gameImage.setPreserveRatio(false); // Evita que la imagen se ajuste al tamaño cambiando su relación de aspecto
		gameImage.setSmooth(true); // Suaviza la imagen
		gameImage.setCache(true);  // Optimiza la carga

		arrowBack.setImage(new Image(getClass().getResource("/images/arrow_back.png").toExternalForm()));
	}

	private HBox crearBloquePlataformas(Game game) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Plataformas.fxml"));
			HBox plataformas = loader.load();

			// Obtener el controlador del FXML
			PlataformasController controller = loader.getController();
			controller.setPlataformasFoto(game);

			return plataformas;
		} catch (Exception e) {
			e.printStackTrace();
			return new HBox(new Label("Error cargando juego"));
		}
	}


	private String extractSpanishDescription(String description) {
		// Usa Jsoup para limpiar el HTML y extraer texto
		String plainText = Jsoup.parse(description).text();

		// Busca el índice donde comienza la parte en español
		String startMarker = "Español";
		int startIndex = plainText.indexOf(startMarker);

		if (startIndex == -1) {
			return "No se ha encontrado ninguna descripción del juego";
		}

		// Extrae el texto desde "Español"
		String spanishText = plainText.substring(startIndex + startMarker.length()).trim();

		// Opcionalmente puedes detenerte si detectas el fin del texto en español
		int endIndex = spanishText.indexOf("English"); // Ejemplo de un marcador de fin
		if (endIndex != -1) {
			spanishText = spanishText.substring(0, endIndex).trim();
		}

		return spanishText;
	}

	public void setGamesController(BuscarJuegosController controller) {
		this.buscarJuegosController = controller;
	}

	@FXML
	void turnBack(MouseEvent event) {
		NavigationUtils.navigateTo(BibliotecaController.getStage(), "/views/Biblioteca.fxml");
	}

}

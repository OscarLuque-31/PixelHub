package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;
import models.Game;

public class GameItemListaController {

	@FXML 
	private ImageView gameImage;
	
	@FXML 
	private HBox gamePlatforms;
	
	@FXML 
	private Label gameRating;
	
	@FXML 
	private Label gameTitle;
	
	@FXML 
	private Region spacer;

	/**
	 * Método que setea todos los datos del juego
	 * @param juego
	 */
	public void setGameData(Game juego) {
		gameTitle.setText(juego.getName());

		gameRating.setText("⭐ " + juego.getRating());

		gamePlatforms.getChildren().add(crearBloquePlataformas(juego));

		try {
			gameImage.setImage(new Image(juego.getBackgroundImage()));
		} catch (Exception e) {
			gameImage.setImage(new Image(getClass().getResource("/images/error.png").toExternalForm()));
		}

		gameImage.setFitWidth(100);
		gameImage.setFitHeight(70); 
		gameImage.setPreserveRatio(false); 
		gameImage.setSmooth(true);
		gameImage.setCache(true); 

		Rectangle clip = new Rectangle(gameImage.getFitWidth(), gameImage.getFitHeight());
		clip.setArcWidth(10);  
		clip.setArcHeight(10);
		gameImage.setClip(clip);
	}

	/**
	 * Método que muestra los juegos creando los bloques o card de cada juego
	 * @param game
	 * @return
	 */
	private HBox crearBloquePlataformas(Game juego) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Plataformas.fxml"));
			HBox plataformas = loader.load();

			// Obtener el controlador del FXML
			PlataformasController controller = loader.getController();
			controller.setPlataformasFoto(juego);

			return plataformas;
		} catch (Exception e) {
			e.printStackTrace();
			return new HBox(new Label("Error cargando juego"));
		}
	}

}

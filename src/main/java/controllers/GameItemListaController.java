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

	public void setGameData(Game juego) {
		gameTitle.setText(juego.getName());

		gameRating.setText("⭐ " + juego.getRating());

		gamePlatforms.getChildren().add(crearBloquePlataformas(juego));

		try {
			gameImage.setImage(new Image(juego.getBackgroundImage()));
		} catch (Exception e) {
			gameImage.setImage(new Image(getClass().getResource("/images/error.png").toExternalForm()));
		}

		gameImage.setFitWidth(100); // Ancho fijo
		gameImage.setFitHeight(70); // Alto fijo
		gameImage.setPreserveRatio(false); // Evita que la imagen se ajuste al tamaño cambiando su relación de aspecto
		gameImage.setSmooth(true); // Suaviza la imagen
		gameImage.setCache(true);  // Optimiza la carga

		// Clipping para recortar los lados sobrantes
		Rectangle clip = new Rectangle(gameImage.getFitWidth(), gameImage.getFitHeight());
		clip.setArcWidth(10);  // Opcional: Bordes redondeados
		clip.setArcHeight(10);
		gameImage.setClip(clip);
	}

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

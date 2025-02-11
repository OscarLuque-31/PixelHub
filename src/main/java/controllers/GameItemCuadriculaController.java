package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import models.Game;
import utils.APIUtils;

public class GameItemCuadriculaController {

    @FXML private ImageView gameImage;
    @FXML private HBox gamePlatforms;
    @FXML private Label gameTitle;
    @FXML private Label gameRating;
    
    private int gameId;
    private BuscarJuegosController buscarJuegosController;
    
    public ImageView getImageView() {
		return this.gameImage;
	}

    public void setGameData(Game game) {
    	gameId = game.getId();
        gameTitle.setText(game.getName());
        
        gameRating.setText("⭐ " + game.getRating());
        
        gamePlatforms.getChildren().add(crearBloquePlataformas(game));

        try {
            gameImage.setImage(new Image(game.getBackgroundImage()));
        } catch (Exception e) {
            gameImage.setImage(new Image(getClass().getResource("/images/error.png").toExternalForm()));
        }

        
//        gameImage.setFitWidth(300); // Ancho fijo
//        gameImage.setFitHeight(200); // Alto fijo
        gameImage.setPreserveRatio(false); // Evita que la imagen se ajuste al tamaño cambiando su relación de aspecto
        gameImage.setSmooth(true); // Suaviza la imagen
        gameImage.setCache(true);  // Optimiza la carga
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
    
    public void setGamesController(BuscarJuegosController controller) {
        this.buscarJuegosController = controller;
    }
    
    @FXML
    void showGameDetails(MouseEvent event) {
    	if (buscarJuegosController != null) {
            try {
                Game game = APIUtils.getGameDetails(gameId);
                buscarJuegosController.mostrarDetallesJuego(game);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

	
    
}

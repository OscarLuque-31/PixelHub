package controllers;

import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import models.Game;
import utils.APIUtils;

public class GameItemCuadriculaController {

    @FXML private ImageView gameImage;
    @FXML private HBox gamePlatforms;
    @FXML private Label gameTitle;
    @FXML private Label gameRating;
    @FXML private ImageView addButton;
    @FXML private VBox mainBox;
    
    private int gameId;
    private BuscarJuegosController buscarJuegosController;
    
    public ImageView getImageView() {
		return this.gameImage;
	}

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

        // Crear un clip con una ruta SVG para redondear solo las esquinas superiores
        SVGPath clip = new SVGPath();
        clip.setContent("M0," + 15 + " " + 
                        "Q0,0 " + 15 + ",0 " + 
                        "L" + (width - 15) + ",0 " +
                        "Q" + width + ",0 " + width + "," + 15 + 
                        "L" + width + "," + height + 
                        "L0," + height + "Z");

        gameImage.setClip(clip);
        
        addButton.setImage(new Image(getClass().getResource("/images/CirculoMas.png").toExternalForm()));
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
                List<String> screenshots = APIUtils.getGameScreenshots(gameId);
                List<Game> dlcs = APIUtils.getGameDLCs(gameId);
                for (Game juego:dlcs) {
                	System.out.println(juego.getName());
                }
                buscarJuegosController.mostrarDetallesJuego(game, screenshots, dlcs);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

	
    
}

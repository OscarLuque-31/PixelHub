package controllers;

import java.io.ByteArrayInputStream;

import org.hibernate.Session;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import models.JuegosBiblioteca;
import utils.HibernateUtil;

public class GameItemCuadriculaBibliotecaController {

    @FXML
    private ImageView gameImage;

    @FXML
    private HBox gamePlatforms;

    @FXML
    private Label gameRating;

    @FXML
    private Label gameTitle;

    @FXML
    private VBox mainBox;
    
    @FXML
    private ImageView removeButton;
    
    private JuegosBiblioteca game;
    
    private BibliotecaJuegosController bibliotecaController;    
    
    /**
     * Método que setea el juego
     * @param game
     */
    public void setGameData(JuegosBiblioteca game) {
    	this.game = game;
    	
    	mainBox.getStylesheets().add(getClass().getResource("/styles/styleGameItemCuadricula.css").toExternalForm());
    	
        gameTitle.setText(game.getTitulo());
        gameRating.setText("⭐ " + game.getRating());
        gamePlatforms.getChildren().add(crearBloquePlataformas(game));

        try {
        	// Comprueba que no sea nulo
            if (game.getUrlImagen() != null) {
				gameImage.setImage(new Image(game.getUrlImagen()));
			}else {
				gameImage.setImage(new Image(new ByteArrayInputStream(game.getImagen())));
			}
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
        clip.setContent("M0," + 15 + " " + 
                        "Q0,0 " + 15 + ",0 " + 
                        "L" + (width - 15) + ",0 " +
                        "Q" + width + ",0 " + width + "," + 15 + 
                        "L" + width + "," + height + 
                        "L0," + height + "Z");

        gameImage.setClip(clip);
        // Añade el boton de remover
        removeButton.setImage(new Image(getClass().getResource("/images/circuloMenos.png").toExternalForm()));
    }
    
    /**
	 * Método que muestra los juegos creando los bloques o card de cada juego
	 * @param game
	 * @return
	 */
    private HBox crearBloquePlataformas(JuegosBiblioteca game) {
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
    
    public void setGamesController(BibliotecaJuegosController controller) {
        this.bibliotecaController = controller;
    }
    
    @FXML
    void showGameDetails() {
    	if (bibliotecaController != null) {
            try {

                bibliotecaController.mostrarDetallesJuego(game);
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void removeGame(MouseEvent event) {
        if (bibliotecaController != null) {
            // Crea la sesión de Hibernate
            try (Session session = HibernateUtil.getSession()) {
                // Inicia una transacción
                session.beginTransaction();

                // Busca el juego en la base de datos
                JuegosBiblioteca juego = session.get(JuegosBiblioteca.class, game.getIdJuego());

                if (juego != null) {
                    // Limpia las capturas asociadas si es necesario
                    juego.getCapturas().clear(); 

                    // Elimina el juego
                    session.remove(juego);
                }

                // Confirma la transacción
                session.getTransaction().commit();

                // Actualiza la vista de biblioteca
                bibliotecaController.buscarJuegos(event);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


 

}

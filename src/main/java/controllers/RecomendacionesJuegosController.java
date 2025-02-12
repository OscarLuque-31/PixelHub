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
import javafx.scene.layout.VBox;
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
    
    private Map<String, Integer> platforms;
	private Map<String, Integer> genres;
    
    private List<String> plataformas;
    private List<String> generos;
    private PreferenciasDaoImpl preferenciasDao;

    private VBox listaDeJuegosVBox;
    private BuscarJuegosController buscarJuegosController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listaDeJuegosVBox = new VBox();
        listaDeJuegosVBox.setSpacing(30);  // Espaciado entre bloques de juegos
        listaDeJuegosVBox.setAlignment(Pos.CENTER); 
        
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
	    
	    contenedorJuegos.setCenter(listaDeJuegosVBox);
	    
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
        ExecutorService executor = Executors.newFixedThreadPool(2); 

        executor.submit(() -> cargarJuegosPorCategoria("Los más populares", null, null, "-rating"));
        
        for (String genero:generos) {
        	executor.submit(() -> cargarJuegosPorCategoria("Juegos de " + genero + " mejor valorados", null, genres.get(genero), "-rating"));
        }
        
        for (String plataforma:plataformas) {
        	executor.submit(() -> cargarJuegosPorCategoria("Juegos de " + plataforma + " mejor valorados", platforms.get(plataforma), null, "-rating"));
        }


        executor.shutdown();
    }

    private void cargarJuegosPorCategoria(String titulo, Integer platform, Integer genre, String order) {
        try {
            List<Game> listaJuegos = APIUtils.getGames(null, platform, genre, order, 4);
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
            hboxJuegos.getChildren().add(imageViewJuego);
            
            imageViewJuego.setOnMouseClicked((MouseEvent event) -> {
            	showGameDetails(game.getId());
            });
            
        }

        vboxCompleto.getChildren().add(hboxJuegos);

        return vboxCompleto;
    }

    private void mostrarJuegos(VBox juegosConTitulo) {
        listaDeJuegosVBox.getChildren().add(juegosConTitulo); // Agrega los juegos dentro del VBox
    }
    
    public void setGamesController(BuscarJuegosController controller) {
        this.buscarJuegosController = controller;
    }
    
//    private void showGameDetails(int gameId) {
//    	if (buscarJuegosController != null) {
//            try {
//                Game game = APIUtils.getGameDetails(gameId);
//                List<String> screenshots = APIUtils.getGameScreenshots(gameId);
//                List<Game> dlcs = APIUtils.getGameDLCs(gameId);
//                buscarJuegosController.mostrarDetallesJuego(game, screenshots, dlcs);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
    
    private void showGameDetails(int gameId) {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/GameDetails.fxml"));
            Parent root = loader.load();

            GameDetailsController controller = loader.getController();
            Game game = APIUtils.getGameDetails(gameId);
          List<String> screenshots = APIUtils.getGameScreenshots(gameId);
          List<Game> dlcs = APIUtils.getGameDLCs(gameId);
          buscarJuegosController.mostrarDetallesJuego(game, screenshots, dlcs);
//            controller.setGameDetails(gameId ); // Pasar el objeto Game

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

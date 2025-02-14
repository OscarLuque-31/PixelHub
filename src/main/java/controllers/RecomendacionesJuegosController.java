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
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import models.Game;
import models.Preferencias;
import utils.APIUtils;
import utils.HibernateUtil;
import utils.LogicaUtils;

public class RecomendacionesJuegosController implements Initializable {

    @FXML
    private VBox contenedorJuegos;

    @FXML
    private ScrollPane scrollPane;
    
    private Map<String, Integer> platforms;
    private Map<String, Integer> genres;
    private List<String> plataformas;
    private List<String> generos;
    private PreferenciasDaoImpl preferenciasDao;

    /**
     * Inicializa la vista de recomendaciones de juegos.
     * Configura la apariencia y obtiene las recomendaciones de juegos.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        contenedorJuegos.setSpacing(20);
        contenedorJuegos.setAlignment(Pos.CENTER);

        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setContent(contenedorJuegos);
        scrollPane.setStyle("-fx-background: #192229; -fx-background-color: #192229;");

        setMaps();
        getRecomendaciones();
        cargarCategorias();
    }

    /**
     * Configura los mapas de plataformas y géneros.
     */
    private void setMaps() {
        LogicaUtils.setMapContent();
        platforms = LogicaUtils.platforms;
        genres = LogicaUtils.genres;
    }

    /**
     * Obtiene las preferencias del usuario desde la base de datos y
     * las almacena en listas de plataformas y géneros.
     */
    private void getRecomendaciones() {
        preferenciasDao = new PreferenciasDaoImpl(HibernateUtil.getSession());
        int id = BibliotecaController.getUsuario().getId();

        List<Preferencias> preferencias = preferenciasDao.getPreferenciasByUsuario(id);
        plataformas = new ArrayList<>();
        generos = new ArrayList<>();

        for (Preferencias pref : preferencias) {
            if (pref.getTipo() == 1) {
                generos.add(pref.getPreferencia());
            } else if (pref.getTipo() == 2) {
                plataformas.add(pref.getPreferencia());
            }
        }
    }

    /**
     * Carga las categorías de juegos recomendados en función de las preferencias del usuario.
     */
    private void cargarCategorias() {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        List<Future<?>> futures = new ArrayList<>();

        executor.submit(() -> cargarJuegosPorCategoria("Los más populares", null, null, "-rating"));

        for (String genero : generos) {
            executor.submit(() -> cargarJuegosPorCategoria("Juegos de " + genero + " mejor valorados", null, genres.get(genero), "-rating"));
        }

        for (String plataforma : plataformas) {
            executor.submit(() -> cargarJuegosPorCategoria("Juegos de " + plataforma + " mejor valorados", platforms.get(plataforma), null, "-rating"));
        }

        executor.shutdown();

        new Thread(() -> {
            for (Future<?> future : futures) {
                try {
                    future.get();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * Carga una lista de juegos de una categoría específica.
     *
     * @param titulo   Título de la categoría.
     * @param platform ID de la plataforma.
     * @param genre    ID del género.
     * @param order    Orden de los juegos.
     */
    private void cargarJuegosPorCategoria(String titulo, Integer platform, Integer genre, String order) {
        try {
            List<Game> listaJuegos = APIUtils.getGames(null, platform, genre, order, 12);
            VBox juegosConTitulo = crearFilaJuegosConTitulo(titulo, listaJuegos);

            Platform.runLater(() -> mostrarJuegos(juegosConTitulo));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Crea una fila de juegos con las imágenes de los juegos.
     *
     * @param listaJuegos  Lista de juegos a mostrar.
     * @return VBox        Contenedor con el título y los juegos.
     */
    private VBox crearFilaJuegosConTitulo(String titulo, List<Game> listaJuegos) {
		VBox vboxCompleto = new VBox();
		vboxCompleto.setAlignment(Pos.CENTER);
		vboxCompleto.setSpacing(10);
		vboxCompleto.setPadding(new Insets(0, 0, 0, 20));
		vboxCompleto.setMinHeight(270);

		// Crear y añadir el título de la categoría
		Label labelTitulo = new Label(titulo);
		labelTitulo.setStyle("-fx-font-size: 22px; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10px;");
		labelTitulo.setMaxWidth(Double.MAX_VALUE);

		vboxCompleto.getChildren().add(labelTitulo);

		HBox hboxJuegos = new HBox();
		hboxJuegos.setSpacing(20);
		hboxJuegos.setAlignment(Pos.CENTER_LEFT);
		hboxJuegos.setPadding(new Insets(0, 0, 0, 20));

		for (Game game : listaJuegos) {
			ImageView imageViewJuego = new ImageView(new Image(game.getBackgroundImage()));
			imageViewJuego.setFitWidth(250);
			imageViewJuego.setFitHeight(200);
			
			Rectangle clip = new Rectangle(imageViewJuego.getFitWidth(), imageViewJuego.getFitHeight());
			clip.setArcWidth(15);
			clip.setArcHeight(15);
			imageViewJuego.setClip(clip);
			
			hboxJuegos.getChildren().add(imageViewJuego);

			imageViewJuego.setCursor(Cursor.HAND);
			imageViewJuego.setOnMouseClicked((MouseEvent event) -> {
				showGameDetails(game.getId());
			});
		}

		ScrollPane scrollPaneFila = new ScrollPane(hboxJuegos);
		scrollPaneFila.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		scrollPaneFila.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		scrollPaneFila.setFitToHeight(true);
		scrollPaneFila.setPannable(true);
		scrollPaneFila.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
		scrollPaneFila.setPrefWidth(scrollPane.getWidth() - 20);

		vboxCompleto.getChildren().add(scrollPaneFila);

		return vboxCompleto;
	}

    /**
     * Muestra los juegos en el contenedor principal.
     *
     * @param juegosConTitulo VBox que contiene los juegos a mostrar.
     */
    private void mostrarJuegos(VBox juegosConTitulo) {
        contenedorJuegos.getChildren().add(juegosConTitulo);
    }

    /**
     * Muestra los detalles de un juego específico.
     *
     * @param gameId ID del juego a mostrar.
     */
    private void showGameDetails(int gameId) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/GameDetails.fxml"));
            HBox gameDetails = loader.load();

            GameDetailsController controller = loader.getController();
            Game game = APIUtils.getGameDetails(gameId);
            List<String> screenshots = APIUtils.getGameScreenshots(gameId);
            List<Game> dlcs = APIUtils.getGameDLCs(gameId);

            controller.setGameDetails(game, screenshots, dlcs);
            mostrarDetallesJuego(gameDetails);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Reemplaza el contenido del contenedor con los detalles del juego.
     *
     * @param gameDetails Contenedor con la información del juego.
     */
    public void mostrarDetallesJuego(HBox gameDetails) {
        contenedorJuegos.getChildren().clear();
        contenedorJuegos.getChildren().add(gameDetails);
    }
}

package controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import dao.JuegosBibliotecaDaoImpl;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Game;
import models.JuegosBiblioteca;
import models.Usuario;
import utils.APIUtils;
import utils.HibernateUtil;
import utils.LogicaUtils;
import utils.NavigationUtils;
import utils.UtilsViews;

public class BibliotecaJuegosController implements Initializable {

	@FXML
	private ImageView imgAdd;

	@FXML
	private ImageView imgLupa;

	@FXML
	private TextField textFieldBusqueda;

	@FXML
	private ScrollPane scrollPane;

	@FXML
	private VBox contenedorJuegos;

	@FXML
	private BorderPane borderPaneCentro;

	@FXML
	private VBox loadingPane;

	@FXML
	private ComboBox<String> comboBoxOrdenar;

	@FXML
	private ComboBox<String> comboBoxPlataforma;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Inicializa las imágenes
		initializeImagesBar();
		// Carga todos los juegos
		buscarJuegos(null);
		// Navegación entre pestañas
		navegacionEntrePestañas();
		setComboContent();
	}

	/**
	 * Método que controla la navegación entre ventanas
	 */
	private void navegacionEntrePestañas() {
		imgAdd.setOnMouseClicked(event -> cambiarContenidoCentro("/views/AnadirJuegoBiblioteca.fxml"));
	}

	/**
	 * Método que cambia el contenido del centro
	 * @param rutaFXML
	 */
	private void cambiarContenidoCentro(String rutaFXML) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));
			BorderPane nuevoContenido = loader.load();
			borderPaneCentro.setCenter(nuevoContenido);
		} catch (Exception e) {
			e.printStackTrace();
			Label errorLabel = new Label("Error cargando contenido: " + rutaFXML);
			errorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 16;");
			borderPaneCentro.setCenter(errorLabel);
		}
	}

	/**
	 * Método que inicializa las imagenes
	 */
	public void initializeImagesBar() {
		imgAdd.setImage(new Image(getClass().getResourceAsStream("/images/CirculoMas.png")));
		imgLupa.setImage(new Image(getClass().getResourceAsStream("/images/lupa.png")));
	}


	/**
	 * Método que rellena los comboBox para el posterior filtrado
	 */
	private void setComboContent() {
		comboBoxPlataforma.getItems().addAll("PC", "PlayStation", "Xbox", "iOS", "Apple Macintosh", "Linux", "Nintendo",
				"Android", "Web");
		comboBoxOrdenar.getItems().addAll("A - Z", "Z - A", "Rating", "Fecha añadido");
	}

	/**
	 * Método que muestra los detalles de cada juego
	 * @param game
	 */
	public void mostrarDetallesJuego(JuegosBiblioteca game) {
		try {
			// Carga el fxml
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/GameDetailsBiblioteca.fxml"));
			HBox gameDetails = loader.load();

			// Obtiene el controlador de los detalles
			GameDetailsBibliotecaController controller = loader.getController();
			controller.setGameDetails(game);

			// Reemplaza el contenido del contenedor principal con la nueva vista
			contenedorJuegos.getChildren().clear();
			contenedorJuegos.getChildren().add(gameDetails);
		} catch (Exception e) {
			e.printStackTrace();
			contenedorJuegos.getChildren().add(new Label("Error al cargar los detalles del juego."));
		}
	}

	@FXML
	void buscarJuegos(MouseEvent event) {
		String searchText = textFieldBusqueda.getText();
		String plataforma = comboBoxPlataforma.getValue();
		String ordenarPor = comboBoxOrdenar.getValue();
		
		// Busca los juegos en base de datos y los muestra
		showGames(searchText, plataforma, ordenarPor);
	}

	
	/**
	 * Método que trae desde base de datos los juegos filtrados y los muestra en la pantalla principal
	 * @param title - titulo por el que se filtra
	 * @param plataforma - plataforma por la que se filtra
	 * @param ordenarPor - forma de ordenación
	 */
	private void showGames(String title, String plataforma, String ordenarPor) {
		scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

		loadingPane.setVisible(true);

		// Un hilo se encarga de hacer la llamada a la base de datos
		Task<List<VBox>> loadGamesTask = new Task<>() {
			@Override
			protected List<VBox> call() throws Exception {
				JuegosBibliotecaDaoImpl juegosBibliotecaDaoImpl = new JuegosBibliotecaDaoImpl(
						HibernateUtil.getSession());
				// Filtra los juegos por usuario y por los criterios seleccionados
				List<JuegosBiblioteca> games = juegosBibliotecaDaoImpl.searchJuegosByFilters(
						BibliotecaController.getUsuario().getId(), title, plataforma, ordenarPor);

				List<VBox> bloques = new ArrayList<>();
				for (JuegosBiblioteca game : games) {
					bloques.add(crearBloqueVideojuego(game));
				}
				return bloques;
			}
		};

		// Cuando la tarea termine, oculta el loadingPane y muestra los juegos
		loadGamesTask.setOnSucceeded(event -> {
			List<VBox> juegosCargados = loadGamesTask.getValue();
			mostrarJuegos(juegosCargados);
			// Oculta el loadingPane
			loadingPane.setVisible(false); 
		});

		loadGamesTask.setOnFailed(event -> {
			System.out.println("Error al cargar juegos: " + loadGamesTask.getException().getMessage());
			// Se oculta en caso de error
			loadingPane.setVisible(false); 
		});

		// Ejecuta la carga en un nuevo hilo para no bloquear la Interfaz
		new Thread(loadGamesTask).start();
	}

	/**
	 * Método que muestra los juegos uniendo todas las tarjeta de juegos
	 * @param bloquesJuegos
	 */
	private void mostrarJuegos(List<VBox> bloquesJuegos) {
		contenedorJuegos.getChildren().clear();

		HBox filaActual = new HBox();
		filaActual.setSpacing(20);
		filaActual.setAlignment(Pos.CENTER);

		int contador = 0;
		for (VBox bloque : bloquesJuegos) {
			filaActual.getChildren().add(bloque);
			contador++;

			// Se añaden 3 juegos por fila
			if (contador % 3 == 0) {
				contenedorJuegos.setSpacing(30);
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
	}

	/**
	 * Método que muestra los juegos creando los bloques o card de cada juego
	 * @param game
	 * @return
	 */
	private VBox crearBloqueVideojuego(JuegosBiblioteca game) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/GameItemCuadriculaBiblioteca.fxml"));
			VBox gameItem = loader.load();

			// Obtiene el controlador del FXML
			GameItemCuadriculaBibliotecaController controller = loader.getController();
			controller.setGameData(game);
			controller.setGamesController(this);

			return gameItem;
		} catch (Exception e) {
			e.printStackTrace();
			return new VBox(new Label("Error cargando juego"));
		}
	}

}

package controllers;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import models.Game;

public class GameDetailsController {

	@FXML 
	private VBox detailsContainer;

	@FXML 
	private VBox gameDLC;

	@FXML 
	private TextFlow gameDescription;

	@FXML 
	private Text descriptionText;

	@FXML 
	private ImageView gameImage;

	@FXML 
	private HBox gamePlatforms;

	@FXML 
	private Label gameRating;

	@FXML 
	private ImageView gameScreenshots;

	@FXML 
	private Label gameTitle;

	@FXML 
	private ImageView next;

	@FXML 
	private ImageView previous;

	@FXML
	private Label nombreDlcCuatro;

	@FXML
	private Label nombreDlcDos;

	@FXML
	private Label nombreDlcTres;

	@FXML
	private Label nombreDlcUno;

	@FXML
	private ImageView fotoDlcCuatro;

	@FXML
	private ImageView fotoDlcDos;

	@FXML
	private ImageView fotoDlcTres;

	@FXML
	private ImageView fotoDlcUno;

	@FXML
	private GridPane plataformasYRating;

	private BuscarJuegosController buscarJuegosController;
	private List<String> screenshots;
	private int posicionCapturas;

	/**
	 * Método que agrega en los campos correspondientes los datos del juego
	 * @param game
	 */
	public void setGameDetails(Game game, List<String> screenshots, List<Game> dlcs) {
    	detailsContainer.getStylesheets().add(getClass().getResource("/styles/styleGameDetails.css").toExternalForm());

		// Setea todos los campos
		gameTitle.setText(game.getName());
		descriptionText.setText(extractDescription(game.getDescription()));
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
		
		setImageBorderRadius(gameImage, 15);

		previous.setImage(new Image(getClass().getResource("/images/imagenAnterior.png").toExternalForm()));
		next.setImage(new Image(getClass().getResource("/images/siguienteImagen.png").toExternalForm()));

		posicionCapturas = 0;
		this.screenshots = screenshots;
		setCaptura("");

		// Setea los dlcs
		setDLCsPorDefecto(fotoDlcUno, nombreDlcUno);
		setDLCsPorDefecto(fotoDlcDos, nombreDlcDos);
		setDLCsPorDefecto(fotoDlcTres, nombreDlcTres);
		setDLCsPorDefecto(fotoDlcCuatro, nombreDlcCuatro);
		
		try {
			setDLCs(dlcs.get(0), fotoDlcUno, nombreDlcUno);
			setDLCs(dlcs.get(1), fotoDlcDos, nombreDlcDos);
			setDLCs(dlcs.get(2), fotoDlcTres, nombreDlcTres);
			setDLCs(dlcs.get(3), fotoDlcCuatro, nombreDlcCuatro);
		} catch (Exception e) {

		}
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

			// Obtener el controlador del FXML
			PlataformasController controller = loader.getController();
			controller.setPlataformasFoto(game);

			return plataformas;
		} catch (Exception e) {
			e.printStackTrace();
			return new HBox(new Label("Error cargando juego"));
		}
	}
	

	/**
	 * Método que setea los dlcs por defecto
	 * @param cuadroFoto
	 * @param nombre
	 */
	private void setDLCsPorDefecto(ImageView cuadroFoto, Label nombre) {
		cuadroFoto.setImage(new Image(getClass().getResource("/images/error.png").toExternalForm()));
		nombre.setText("DLC no encontrado");
		
		cuadroFoto.setFitWidth(80);
		cuadroFoto.setFitHeight(50);
		cuadroFoto.setPreserveRatio(false);
		setImageBorderRadius(cuadroFoto, 5);
	}

	/**
	 * Metodo que setea los dlc
	 * @param dlc
	 * @param cuadroFoto
	 * @param nombre
	 */
	private void setDLCs(Game dlc, ImageView cuadroFoto, Label nombre) {
		try {
			cuadroFoto.setImage(new Image(dlc.getBackgroundImage()));
			nombre.setText(dlc.getName());
		} catch (Exception e){
			setDLCsPorDefecto(cuadroFoto, nombre);
		}

	}
	
	/**
	 * Método que setea la captura
	 * @param direction
	 */
	private void setCaptura(String direction) {
		try {
			if (direction.equals(">") && posicionCapturas < screenshots.size() - 1) {
				posicionCapturas++;

			} else if (direction.equals("<") && posicionCapturas > 0) {
				posicionCapturas--;
			}

			if (screenshots.size() > 0) {
				gameScreenshots.setImage(new Image(screenshots.get(posicionCapturas)));
			}
			gameScreenshots.setPreserveRatio(false);
			gameScreenshots.setSmooth(true);
			gameScreenshots.setCache(true);
			setImageBorderRadius(gameScreenshots, 10);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Metódo que agrega el borderRadius
	 * @param imageView
	 * @param px
	 */
	private void setImageBorderRadius(ImageView imageView, int px) {
		Rectangle clip = new Rectangle(imageView.getFitWidth(), imageView.getFitHeight());
		clip.setArcWidth(px);
		clip.setArcHeight(px);
		imageView.setClip(clip);
	}

	
	/**
	 * Método que extrae la descripción en inglés o en español.
	 * @param html
	 * @return
	 */
	public String extractDescription(String html) {
		// Patrón para extraer la descripción en español
	    Pattern patternSpanish = Pattern.compile("<p>Español<br ?/>(.*?)</p>", Pattern.DOTALL);
	    Matcher matcherSpanish = patternSpanish.matcher(html);

	    if (matcherSpanish.find()) {
	        return limpiarTexto(matcherSpanish.group(1));
	    }

	    // Si no hay descripción en español, extrae el primer párrafo (en inglés)
	    Pattern patternEnglish = Pattern.compile("<p>(.*?)</p>", Pattern.DOTALL);
	    Matcher matcherEnglish = patternEnglish.matcher(html);

	    if (matcherEnglish.find()) {
	        return limpiarTexto(matcherEnglish.group(1));
	    }

	    return "Descripción no disponible.";
	}

	/**
	 * Método para limpiar el texto
	 * @param texto
	 * @return
	 */
	private String limpiarTexto(String texto) {
	    // Elimina las etiquetas HTML y los saltos de línea (sin reemplazar <br />)
	    return texto.replaceAll("<.*?>", "").trim();
	}

	/**
	 * Método que setea el controllar de BuscarJuegosController
	 * @param controller
	 */
	public void setGamesController(BuscarJuegosController controller) {
		this.buscarJuegosController = controller;
	}

	@FXML
	void nextScreenshot(MouseEvent event) {
		setCaptura(">");
	}

	@FXML
	void previousScreenshot(MouseEvent event) {
		setCaptura("<");
	}

}

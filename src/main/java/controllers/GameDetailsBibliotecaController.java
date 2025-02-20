package controllers;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import models.Capturas;
import models.JuegosBiblioteca;

public class GameDetailsBibliotecaController {

	@FXML
	private Text comentario;

	@FXML
	private Text descriptionText;

	@FXML
	private VBox detailsContainer;

	@FXML
	private TextFlow gameDescription;

	@FXML	
	private TextFlow gameDescription1;

	@FXML
	private Label gameFecha;

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

	// Lista de capturas
	private List<Capturas> capturas;

	// Capturas de la api
	private boolean areCapturasApi;
	private int posicionCapturas;

	/**
	 * Método que agrega en los campos correspondientes los datos del juego
	 * @param game
	 */
	public void setGameDetails(JuegosBiblioteca game) {
		detailsContainer.getStylesheets().add(getClass().getResource("/styles/styleGameDetails.css").toExternalForm());

		// Setea todos los campos
		gameTitle.setText(game.getTitulo());
		gameRating.setText("Rating personal: ⭐ " + game.getRating());
		comentario.setText(game.getComentario());
		gameFecha.setText("Fecha en la que se añadió a la biblioteca: " + game.getFechaAñadido());
		gamePlatforms.getChildren().add(crearBloquePlataformas(game));

		try {
			// Se asegura de que no sea nula
			if (game.getUrlImagen() != null) {
				gameImage.setImage(new Image(game.getUrlImagen()));
				areCapturasApi = true;
				descriptionText.setText(extractDescription(game.getDescripcion()));
			} else {
				gameImage.setImage(new Image(new ByteArrayInputStream(game.getImagen())));
				areCapturasApi = false;
				descriptionText.setText(game.getDescripcion());
			}
		} catch (Exception e) {
			gameImage.setImage(new Image(getClass().getResource("/images/error.png").toExternalForm()));
		}
		gameImage.setPreserveRatio(false); 
		gameImage.setSmooth(true); 
		gameImage.setCache(true); 

		// Añade el borderRadius
		setImageBorderRadius(gameImage, 15);

		previous.setImage(new Image(getClass().getResource("/images/imagenAnterior.png").toExternalForm()));
		next.setImage(new Image(getClass().getResource("/images/siguienteImagen.png").toExternalForm()));

		posicionCapturas = 0;
		capturas = game.getCapturas();
		setCaptura("");

	}

	/**
	 * Método que muestra los juegos creando los bloques o card de cada juego
	 * @param game
	 * @return
	 */
	private HBox crearBloquePlataformas(JuegosBiblioteca game) {
		try {
			// Carga el fxml
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Plataformas.fxml"));
			HBox plataformas = loader.load();

			// Obtiene el controlador del FXML
			PlataformasController controller = loader.getController();
			controller.setPlataformasFoto(game);

			return plataformas;
		} catch (Exception e) {
			e.printStackTrace();
			return new HBox(new Label("Error cargando juego"));
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
	 * Método que setea la captura
	 * @param direction
	 */
	private void setCaptura(String direction) {
		try {
			if (direction.equals(">") && posicionCapturas < capturas.size() - 1) {
				posicionCapturas++;
			} else if (direction.equals("<") && posicionCapturas > 0) {
				posicionCapturas--;
			}

			if (capturas.size() > 0) {
				if (areCapturasApi) {
					gameScreenshots.setImage(new Image(capturas.get(posicionCapturas).getUrlImagen()));
				} else {
					gameScreenshots
							.setImage(new Image(new ByteArrayInputStream(capturas.get(posicionCapturas).getCaptura())));
				}
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

	@FXML
	void nextScreenshot(MouseEvent event) {
		setCaptura(">");
	}

	@FXML
	void previousScreenshot(MouseEvent event) {
		setCaptura("<");
	}

}

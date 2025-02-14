package controllers;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.jsoup.Jsoup;

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
import models.Game;
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
    
	private List<Capturas> capturas;

	private boolean areCapturasApi;
	private int posicionCapturas;
    
    public void setGameDetails(JuegosBiblioteca game) {
    	detailsContainer.getStylesheets().add(getClass().getResource("/styles/styleGameDetails.css").toExternalForm());

		gameTitle.setText(game.getTitulo());
		gameRating.setText("Rating personal: ⭐ " + game.getRating());
		comentario.setText(game.getComentario());
		gameFecha.setText("Fecha en la que se añadió a la biblioteca: " + game.getFechaAñadido());
		gamePlatforms.getChildren().add(crearBloquePlataformas(game));
		
		try {
			if (game.getUrlImagen() != null) {
				gameImage.setImage(new Image(game.getUrlImagen()));
				areCapturasApi = true;
				descriptionText.setText(extractSpanishDescription(game.getDescripcion()));
			}else {
				gameImage.setImage(new Image(new ByteArrayInputStream(game.getImagen())));
				areCapturasApi = false;
				descriptionText.setText(game.getDescripcion());
			}
		} catch (Exception e) {
			gameImage.setImage(new Image(getClass().getResource("/images/error.png").toExternalForm()));
		}
		gameImage.setPreserveRatio(false); // Evita que la imagen se ajuste al tamaño cambiando su relación de aspecto
		gameImage.setSmooth(true); // Suaviza la imagen
		gameImage.setCache(true);  // Optimiza la carga
		
		setImageBorderRadius(gameImage, 15);

		previous.setImage(new Image(getClass().getResource("/images/imagenAnterior.png").toExternalForm()));
		next.setImage(new Image(getClass().getResource("/images/siguienteImagen.png").toExternalForm()));

		posicionCapturas = 0;
		capturas = game.getCapturas();
		setCaptura("");
		
	}
    
    
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
    
    private void setImageBorderRadius(ImageView imageView, int px) {
		Rectangle clip = new Rectangle(imageView.getFitWidth(), imageView.getFitHeight());
		clip.setArcWidth(px);
		clip.setArcHeight(px);
		imageView.setClip(clip);
	}
    
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
				}else {
					gameScreenshots.setImage(new Image(new ByteArrayInputStream(capturas.get(posicionCapturas).getCaptura())));
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
    
    private String extractSpanishDescription(String description) {
		// Usa Jsoup para limpiar el HTML y extraer texto
		String plainText = Jsoup.parse(description).text();

		// Busca el índice donde comienza la parte en español
		String startMarker = "Español";
		int startIndex = plainText.indexOf(startMarker);

		if (startIndex == -1) {
			return "No se ha encontrado ninguna descripción del juego en español";
		}

		// Extrae el texto desde "Español"
		String spanishText = plainText.substring(startIndex + startMarker.length()).trim();

		// Opcionalmente puedes detenerte si detectas el fin del texto en español
		int endIndex = spanishText.indexOf("English"); // Ejemplo de un marcador de fin
		if (endIndex != -1) {
			spanishText = spanishText.substring(0, endIndex).trim();
		}

		return spanishText;
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

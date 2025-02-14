package controllers;

import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import models.Game;
import models.JuegosBiblioteca;
import models.Plataformas;

public class PlataformasController {
	
	@FXML
    private HBox platforms;
	
	/**
     * Muestra las plataformas de un juego en formato texto.
     * 
     * @param game Juego del cual se extraerán las plataformas.
     */
	public void setPlataformas(Game game) {
		List<String> plataformas = getPlataformas(game);
		
		for(String name:plataformas) {
			Label label = new Label(name);
			label.setStyle("-fx-text-fill: FFFFFF");
			platforms.getChildren().add(label);
		} 
    }
	
	/**
     * Muestra las plataformas de un juego en formato imagen.
     * 
     * @param game Juego del cual se extraerán las plataformas.
     */
	public void setPlataformasFoto(Game game) {
		List<String> plataformas = getPlataformas(game);
		platforms.setSpacing(10);
		platforms.setAlignment(Pos.CENTER);
		
		int contadorPlataformas = 0;
		
		for(String name:plataformas) {
			ImageView image = new ImageView();
			
			try {
				image.setImage(new Image(getClass().getResource("/images/" + getImage(name) + ".png").toExternalForm()));
				image.setFitWidth(20);
		        image.setFitHeight(20);
				platforms.getChildren().add(image);
				contadorPlataformas++;
			} catch (Exception e) {
				
			}
		}
        if (contadorPlataformas == 0) {
        	ImageView image = new ImageView();
        	image.setImage(new Image(getClass().getResource("/images/noplatform.png").toExternalForm()));
        	platforms.getChildren().add(image);
        }
    }
	
	/**
     * Muestra las plataformas de un juego de la biblioteca en formato imagen.
     * 
     * @param game Objeto JuegosBiblioteca del cual se extraerán las plataformas.
     */
	public void setPlataformasFoto(JuegosBiblioteca game) {
		List<String> plataformas = getPlataformas(game);
		platforms.setSpacing(10);
		platforms.setAlignment(Pos.CENTER);
		
		int contadorPlataformas = 0;
		
		for(String name:plataformas) {
			ImageView image = new ImageView();
						
			try {
				image.setImage(new Image(getClass().getResource("/images/" + getImage(name) + ".png").toExternalForm()));
				image.setFitWidth(20);
		        image.setFitHeight(20);
				platforms.getChildren().add(image);
				contadorPlataformas++;
			} catch (Exception e) {
				
			}
		}
        if (contadorPlataformas == 0) {
        	ImageView image = new ImageView();
        	image.setImage(new Image(getClass().getResource("/images/noplatform.png").toExternalForm()));
        	platforms.getChildren().add(image);
        }
    }
	
	 /**
     * Obtiene la imagen correspondiente a la plataforma.
     * 
     * @param name Nombre de la plataforma.
     * @return Nombre de la imagen correspondiente a la plataforma.
     */
	private String getImage(String name) {
		String image = "";
		switch (name) {
		case "PlayStation":
			image = "PlayStation";
			break;
		case "Xbox":
			image = "Xbox";
			break;
		case "Nintendo":
			image = "Nintendo";
			break;
		case "PC":
			image = "Windows";
			break;
		case "Apple Macintosh":
			image = "iMac";
			break;
		case "Linux":
			image = "Linux";
			break;
		case "Android":
			image = "Android";
			break;
		case "iOS":
			image = "Apple";
			break;
		case "Web":
			image = "Phone";
			break;
		default:
			image = "noplatform";
		}
		return image;
	}

	/**
     * Extrae las plataformas de un Juego.
     * 
     * @param game Juego del cual se extraerán las plataformas.
     * @return Lista de nombres de plataformas.
     */
	private List<String> getPlataformas(Game game){
		List<String> platforms = new ArrayList<>();
		for (Game.ParentPlatform parentPlatform:game.getParentPlatforms()) {
			if (parentPlatform.getPlatform() != null) {
				platforms.add(parentPlatform.getPlatform().getName());
			}
		}
		return platforms;
	}
	
	/**
     * Extrae las plataformas de un juego.
     * 
     * @param game Juego del cual se extraerán las plataformas.
     * @return Lista de nombres de plataformas.
     */
	private List<String> getPlataformas(JuegosBiblioteca game){
		List<String> platforms = new ArrayList<>();
		for (Plataformas plataforma:game.getPlataformas()) {
			if (plataforma.getPlataforma() != null) {
				platforms.add(plataforma.getPlataforma());
			}
		}
		return platforms;
	}

}

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

public class PlataformasController {
	
	@FXML
    private HBox platforms;
	
	
	public void setPlataformas(Game game) {
		List<String> plataformas = getPlataformas(game);
		
		for(String name:plataformas) {
			Label label = new Label(name);
			
			label.setStyle("-fx-text-fill: FFFFFF");
			
			platforms.getChildren().add(label);
		}
        
    }
	
	public void setPlataformasFoto(Game game) {
		List<String> plataformas = getPlataformas(game);
		platforms.setSpacing(10);
		platforms.setAlignment(Pos.CENTER);
		
		int contadorPlataformas = 0;
		
		for(String name:plataformas) {
			ImageView image = new ImageView();
			
			image.setStyle("");//Estilo del texto
			
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

	private List<String> getPlataformas(Game game){
		List<String> platforms = new ArrayList<>();
		for (Game.ParentPlatform parentPlatform:game.getParentPlatforms()) {
			if (parentPlatform.getPlatform() != null) {
				platforms.add(parentPlatform.getPlatform().getName());
			}
		}
		return platforms;
	}

}

package utils;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;

import com.google.gson.Gson;

import configuration.Config;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import models.Game;

public class APIUtils {
	
	private static final OkHttpClient client = new OkHttpClient();
	
	/**
	 * Hace una llamada a la API esperando una lista de juegos
	 * 
	 * @return Una lista de los juegos que devuelve la API
	 * @throws Exception
	 */
	public static List<Game> getGames() throws Exception {
		//Url de llamada a la API
		String url = Config.API_URL + Config.API_KEY + Config.API_SEARCH + Config.API_PAGE_SIZE + Config.API_PAGE + 1;

		Request request = new Request.Builder()
				.url(url)
				.build();

		//Accede al resultado de la API, convierte cada juego en un objeto y los devuelve en una lista
		try (Response response = client.newCall(request).execute()) {
			if (response.isSuccessful()) {
				
				Gson gson = new Gson();
				ApiResponse apiResponse = gson.fromJson(response.body().string(), ApiResponse.class);
				
				List<Game> games = apiResponse.getResults();
				
				return games;
				
			} else {
				throw new Exception("Error en la solicitud: " + response.code());
			}
		}
	}
	
	public static Game getGameDetails(int gameId) throws Exception {
		String url = Config.API_URL + "/" + gameId + Config.API_KEY;

		Request request = new Request.Builder()
				.url(url)
				.build();

		try (Response response = client.newCall(request).execute()) {
			if (response.isSuccessful()) {
				Gson gson = new Gson();
	            Game gameDetails = gson.fromJson(response.body().string(), Game.class);
	            
	            return gameDetails;
			} else {
				throw new Exception("Error en la solicitud: " + response.code());
			}
		}
	}

	public static List<String> getPlatforms(Game game){
		List<String> platforms = new ArrayList<>();
		for (Game.ParentPlatform parentPlatform:game.getParentPlatforms()) {
			if (parentPlatform.getPlatform() != null) {
				platforms.add(parentPlatform.getPlatform().getName());
			}
		}
		return platforms;
	}
	
	public static String getGameScreenshots(int gameId) throws Exception {
		String url = Config.API_URL + "games/" + gameId + "/screenshots?key=" + Config.API_KEY;

		Request request = new Request.Builder()
				.url(url)
				.build();

		try (Response response = client.newCall(request).execute()) {
			if (response.isSuccessful()) {
				return response.body().string();
			} else {
				throw new Exception("Error en la solicitud: " + response.code());
			}
		}
	}
	
	public static String extractSpanishDescription(String description) {
		//Usa Jsoup para limpiar el HTML y extraer texto
		String plainText = Jsoup.parse(description).text();

		//Busca el índice donde comienza la parte en español
		String startMarker = "Español";
		int startIndex = plainText.indexOf(startMarker);

		if (startIndex == -1) {
			return "No se ha encontrado ninguna descripción del juego";
		}

		//Extrae el texto desde "Español"
		String spanishText = plainText.substring(startIndex + startMarker.length()).trim();

		//Opcionalmente puedes detenerte si detectas el fin del texto en español
		int endIndex = spanishText.indexOf("English"); // Ejemplo de un marcador de fin
		if (endIndex != -1) {
			spanishText = spanishText.substring(0, endIndex).trim();
		}

		return spanishText;
	}
	
}

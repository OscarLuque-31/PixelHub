package utils;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;

import com.google.gson.Gson;

import configuration.Config;
import javafx.scene.control.TextField;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import models.Game;
import models.Game.Screenshot;

public class APIUtils {
    
    private static final OkHttpClient client = new OkHttpClient();

    /**
     * Realiza una llamada a la API para obtener una lista de juegos según los parámetros proporcionados.
     *
     * @param title     Título del juego a buscar.
     * @param platform  ID de la plataforma del juego.
     * @param genre     ID del género del juego.
     * @param order     Orden de los resultados.
     * @param pageSize  Cantidad de juegos por página.
     * @return          Una lista de juegos obtenidos de la API.
     * @throws Exception Si hay un error en la solicitud.
     */
    public static List<Game> getGames(String title, Integer platform, Integer genre, String order, int pageSize) throws Exception {
        String plataforma = platform == null ? "" : Config.API_PLATFORM + platform;
        String genero = genre == null ? "" : Config.API_GENRE + genre;
        
        String url = Config.API_URL + Config.API_KEY + Config.API_SEARCH + title + plataforma + genero + Config.API_ORDER + order + Config.API_PAGE_SIZE + pageSize + Config.API_PAGE + 1;

        Request request = new Request.Builder().url(url).build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                Gson gson = new Gson();
                ApiResponse apiResponse = gson.fromJson(response.body().string(), ApiResponse.class);
                return apiResponse.getResults();
            } else {
                throw new Exception("Error en la solicitud: " + response.code());
            }
        }
    }

    /**
     * Obtiene los detalles de un juego específico a partir de su ID.
     *
     * @param gameId ID del juego.
     * @return       Juego obtenido de la API.
     * @throws Exception Si hay un error en la solicitud a la API.
     */
    public static Game getGameDetails(int gameId) throws Exception {
        String url = Config.API_URL + "/" + gameId + Config.API_KEY;
        Request request = new Request.Builder().url(url).build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                Gson gson = new Gson();
                return gson.fromJson(response.body().string(), Game.class);
            } else {
                throw new Exception("Error en la solicitud: " + response.code());
            }
        }
    }

    /**
     * Obtiene la lista de plataformas en las que está disponible un juego.
     *
     * @param game Juego del cual se extraerán las plataformas.
     * @return     Lista de nombres de plataformas en las que está disponible el juego.
     */
    public static List<String> getPlatforms(Game game) {
        List<String> platforms = new ArrayList<>();
        for (Game.ParentPlatform parentPlatform:game.getParentPlatforms()) {
            if (parentPlatform.getPlatform() != null) {
                platforms.add(parentPlatform.getPlatform().getName());
            }
        }
        return platforms;
    }

    /**
     * Obtiene las capturas de pantalla de un juego.
     *
     * @param gameId ID del juego.
     * @return       Lista de URLs de capturas de pantalla del juego.
     * @throws Exception Si hay un error en la solicitud a la API.
     */
    public static List<String> getGameScreenshots(int gameId) throws Exception {
        String url = Config.API_URL + "/" + gameId + Config.API_SCREENSHOTS + Config.API_KEY;
        Request request = new Request.Builder().url(url).build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                return new ArrayList<>();
            }

            Gson gson = new Gson();
            ScreenshotApiResponse screenshotResponse = gson.fromJson(response.body().string(), ScreenshotApiResponse.class);

            List<String> screenshots = new ArrayList<>();
            if (screenshotResponse == null || screenshotResponse.getResults() == null || screenshotResponse.getResults().isEmpty()) {
                screenshots.add("No se encontraron capturas de pantalla.");
            } else {
                for (Screenshot screenshot:screenshotResponse.getResults()) {
                    screenshots.add(screenshot.getImage());
                }
            }
            return screenshots;
        }
    }

    /**
     * Obtiene la lista de DLCs disponibles para un juego.
     *
     * @param gameId ID del juego.
     * @return       Lista de DLCs del juego.
     * @throws Exception Si hay un error en la solicitud a la API.
     */
    public static List<Game> getGameDLCs(int gameId) throws Exception {
        String url = Config.API_URL + "/" + gameId + Config.API_DLCS + Config.API_KEY;
        Request request = new Request.Builder().url(url).build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                Gson gson = new Gson();
                ApiResponse apiResponse = gson.fromJson(response.body().string(), ApiResponse.class);
                return apiResponse.getResults();
            } else {
                return new ArrayList<>();
            }
        }
    }

    /**
     * Extrae la descripción de un texto en HTML.
     *
     * @param description Descripción en formato HTML.
     * @return            La descripción en español si se encuentra o en inglés en su defecto.
     */
    public static String extractSpanishDescription(String description) {
        String plainText = Jsoup.parse(description).text();
        String startMarker = "Español";
        int startIndex = plainText.indexOf(startMarker);

        if (startIndex == -1) {
            return "No se ha encontrado ninguna descripción del juego";
        }

        String spanishText = plainText.substring(startIndex + startMarker.length()).trim();
        int endIndex = spanishText.indexOf("English");

        if (endIndex != -1) {
            spanishText = spanishText.substring(0, endIndex).trim();
        }

        return spanishText;
    }
}


package utils;

import java.util.HashMap;
import java.util.Map;

public class LogicaUtils {
	
	public static  Map<String, Integer> platforms;
	public static Map<String, Integer> genres;
	public static Map<String, String> order;
	
	/**
	 * Setea el contenido de los mapas que contienen las plataformas y géneros con sus respectivos ids
	 * y las palabras claves para ordenar los juegos
	 */
	public static void setMapContent() {
		platforms = new HashMap<>();
		genres = new HashMap<>();
		order = new HashMap<>();
		
		platforms.put("PC", 1);
		platforms.put("PlayStation", 2);
		platforms.put("Xbox", 3);
		platforms.put("iOS", 4);
		platforms.put("Apple Macintosh", 5);
		platforms.put("Linux", 6);
		platforms.put("Nintendo", 7);
		platforms.put("Android", 8);
		platforms.put("Web", 14);

		genres.put("Acción", 4);
		genres.put("Indie", 51);
		genres.put("Aventura", 3);
		genres.put("RPG", 5);
		genres.put("Estrategia", 10);
		genres.put("Shooter", 2);
		genres.put("Casual", 40);
		genres.put("Simulación", 14);
		genres.put("Puzzle", 7);
		genres.put("Arcade", 11);
		genres.put("Plataformas", 83);
		genres.put("Multijugador", 59);
		genres.put("Carreras", 1);
		genres.put("Deportes", 15);
		genres.put("Lucha", 6);
		genres.put("Familiar", 19);
		genres.put("Juegos de mesa", 28);
		genres.put("Educativo", 34);
		genres.put("Cartas", 17);
		
		order.put("A - Z", "-name");
		order.put("Z - A", "name");
		order.put("Newest - Oldest", "-released");
		order.put("Oldest - Newest", "released");
		order.put("Best - Worst", "-rating");
		order.put("Worst - Best", "rating");
	}

}

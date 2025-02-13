package utils;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
    /**
     * Formatea una fecha en el formato "día mes año".
     *
     * @param fecha La fecha a formatear.
     * @return La fecha formateada como una cadena.
     */
    public static String formatearFecha(Date fecha) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy", new Locale("es", "ES"));
        return sdf.format(fecha);
    }
}
package utils;

import org.mindrot.jbcrypt.BCrypt;

public class UtilsBcrypt {
	

	/**
	 * Método que hashea la contraseña a meter en base de datos.
	 * 
	 * @param password
	 * @return
	 */
	public static String hashPassword(String password) {
	    return BCrypt.hashpw(password, BCrypt.gensalt());
	}


	/**
	 * Método que checkea la contraseña hasheada para comprobar si es correcta.
	 * 
	 * @param plainPassword
	 * @param hashedPassword
	 * @return
	 */
	public static boolean checkPassword(String plainPassword, String hashedPassword) {
	    return BCrypt.checkpw(plainPassword, hashedPassword);
	}

}

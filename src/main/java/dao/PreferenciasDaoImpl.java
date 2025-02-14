package dao;

import models.Preferencias;
import models.Usuario;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class PreferenciasDaoImpl extends CommonDaoImpl<Preferencias> implements PreferenciasDao {

	public PreferenciasDaoImpl(Session session) {
		super(session);
	}

	/**
	 * Método para buscar un usuario por su nombre de usuario
	 * @param username Nombre de usuario a buscar
	 * @return Usuario encontrado o null si no existe
	 */
	public List<Preferencias> getPreferenciasByUsuario(int idUsuario) {
		try {
			// Crear una consulta para buscar por username
			Query<Preferencias> query = session.createQuery(
					"FROM Preferencias WHERE id_usuario = :idUsuario", Preferencias.class
					);
			query.setParameter("idUsuario", idUsuario);

			// Retornar el resultado único o null si no hay coincidencias
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	
	 /**
     * Método para eliminar las preferencias de un usuario.
     * @param idUsuario El ID del usuario cuyas preferencias se eliminarán
     */
    public void eliminarPreferenciasPorUsuario(int idUsuario) {
        try {
            // Crear y ejecutar la consulta de eliminación
            Query query = session.createQuery("DELETE FROM Preferencias p WHERE p.id_usuario = :idUsuario");
            query.setParameter("idUsuario", idUsuario);
            query.executeUpdate();  // Ejecuta la eliminación

            // Hibernate maneja automáticamente la transacción y commit
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Método para insertar una lista de preferencias.
     * @param preferencias Lista de preferencias a insertar
     */
    public void insertarPreferencias(List<Preferencias> preferencias) {
        try {
            int i = 0;
            for (Preferencias pref : preferencias) {
                session.saveOrUpdate(pref); // Insertar o actualizar la preferencia
                
                // Enviar el batch cada 50 registros para optimizar el rendimiento
                if (i % 50 == 0) {
                    session.flush();
                    session.clear();
                }
                i++;
            }

            // Hibernate maneja automáticamente el commit de la sesión
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

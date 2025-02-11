package dao;

import models.Preferencias;
import models.Usuario;

import java.util.List;

import org.hibernate.Session;
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
}

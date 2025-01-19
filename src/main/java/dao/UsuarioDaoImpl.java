package dao;

import models.Usuario;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class UsuarioDaoImpl extends CommonDaoImpl<Usuario> implements UsuarioDao {

    public UsuarioDaoImpl(Session session) {
        super(session);
    }

    /**
     * Método para buscar un usuario por su nombre de usuario
     * @param username Nombre de usuario a buscar
     * @return Usuario encontrado o null si no existe
     */
    public Usuario findByUsername(String username) {
        try {
            // Crear una consulta para buscar por username
            Query<Usuario> query = session.createQuery(
                "FROM Usuario WHERE username = :username", Usuario.class
            );
            query.setParameter("username", username);

            // Retornar el resultado único o null si no hay coincidencias
            return query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

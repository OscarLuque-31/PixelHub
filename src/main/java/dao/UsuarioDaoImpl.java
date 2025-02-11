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
    
    /**
     * Método que devuelve un booleano con la condición de si existe un 
     * nombre de usuario y un email igual en base de datos
     * en ese caso devolvera true.
     * @param username
     * @param email
     * @return
     */
    public boolean existeUsuario(String username, String email) {
        String hql = "FROM Usuario WHERE username = :username OR email = :email";
        Usuario usuario = session.createQuery(hql, Usuario.class)
                .setParameter("username", username)
                .setParameter("email", email)
                .uniqueResult();
        return usuario != null;
    }

}

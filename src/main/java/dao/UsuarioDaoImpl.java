package dao;

import dao.UsuarioDao;
import models.Usuario;
import org.hibernate.Session;

public class UsuarioDaoImpl extends CommonDaoImpl<Usuario> implements UsuarioDao {

    public UsuarioDaoImpl(Session session) {
        super(session);
    }
}

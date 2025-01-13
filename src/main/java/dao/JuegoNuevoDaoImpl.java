package dao;

import models.JuegoNuevo;
import org.hibernate.Session;

public class JuegoNuevoDaoImpl extends CommonDaoImpl<JuegoNuevo> implements JuegoNuevoDao {

    public JuegoNuevoDaoImpl(Session session) {
        super(session);
    }
}

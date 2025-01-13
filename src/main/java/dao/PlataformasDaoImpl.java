package dao;

import models.Plataformas;
import org.hibernate.Session;

public class PlataformasDaoImpl extends CommonDaoImpl<Plataformas> implements PlataformasDao {

    public PlataformasDaoImpl(Session session) {
        super(session);
    }
}

package dao;

import models.Capturas;
import org.hibernate.Session;

public class CapturasDaoImpl extends CommonDaoImpl<Capturas> implements CapturasDao {

    public CapturasDaoImpl(Session session) {
        super(session);
    }
}

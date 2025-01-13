package dao;

import models.Preferencias;
import org.hibernate.Session;

public class PreferenciasDaoImpl extends CommonDaoImpl<Preferencias> implements PreferenciasDao {

    public PreferenciasDaoImpl(Session session) {
        super(session);
    }
}

package dao;

import models.JuegosBiblioteca;
import org.hibernate.Session;

public class JuegosBibliotecaDaoImpl extends CommonDaoImpl<JuegosBiblioteca> implements JuegosBibliotecaDao {

    public JuegosBibliotecaDaoImpl(Session session) {
        super(session);
    }
}

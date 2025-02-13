package dao;

import models.JuegosBiblioteca;
import models.Usuario;

import org.hibernate.Session;

public class JuegosBibliotecaDaoImpl extends CommonDaoImpl<JuegosBiblioteca> implements JuegosBibliotecaDao {

    public JuegosBibliotecaDaoImpl(Session session) {
        super(session);
    }
    
    public boolean existeJuegoEnBiblioteca(Usuario usuario, String tituloJuego) {
        String hql = "SELECT COUNT(j) FROM JuegosBiblioteca j WHERE j.usuario = :usuario AND j.titulo = :titulo";
        Long count = (Long) session.createQuery(hql, Long.class)
            .setParameter("usuario", usuario)
            .setParameter("titulo", tituloJuego)
            .uniqueResult();
        return count > 0; // Si hay al menos un resultado, el juego ya existe
    }

    public int contarJuegosPorUsuario(Integer idUsuario) {
        // Consulta HQL para contar los juegos asociados a un usuario
        String hql = "SELECT COUNT(j) FROM JuegosBiblioteca j WHERE j.usuario.id = :idUsuario";
        
        // Ejecutar la consulta y obtener el resultado
        Long count = (Long) session.createQuery(hql, Long.class)
                .setParameter("idUsuario", idUsuario)
                .uniqueResult();
        
        // Devolver el número de juegos como un entero
        return count != null ? count.intValue() : 0;
    }

}

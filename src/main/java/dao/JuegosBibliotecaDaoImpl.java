package dao;

import models.JuegosBiblioteca;
import models.Usuario;
import utils.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.sql.ast.tree.predicate.Predicate;

import controllers.BibliotecaController;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

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

	public List<JuegosBiblioteca> searchJuegosByUsuario(Integer idUsuario) {
		// Consulta HQL para obtener todos los juegos asociados a un usuario
		String hql = "FROM JuegosBiblioteca j WHERE j.usuario.id = :idUsuario";

		// Ejecutar la consulta y obtener la lista de juegos
		List<JuegosBiblioteca> juegos = session.createQuery(hql, JuegosBiblioteca.class)
				.setParameter("idUsuario", idUsuario)
				.getResultList();

		return juegos; // Devolver la lista de juegos
	}

	public List<JuegosBiblioteca> searchJuegosByFilters(Integer userId, String title, String plataforma, String ordenarPor) { 
	    Session session = HibernateUtil.getSession();

	    // Construir la consulta base
	    StringBuilder hql = new StringBuilder("FROM JuegosBiblioteca jb JOIN jb.plataformas p WHERE jb.usuario.id = :userId");

	    // Agregar filtros según los parámetros proporcionados
	    if (title != null && !title.isEmpty()) {
	        hql.append(" AND jb.titulo LIKE :title");
	    }
	    if (plataforma != null && !plataforma.isEmpty()) {
	        hql.append(" AND p.plataforma = :plataforma"); // Cambié "p.nombre" a "p.plataforma"
	    }

	    // Agregar ordenación
	    if (ordenarPor != null) {
	        switch (ordenarPor) {
	        case "A - Z":
	            hql.append(" ORDER BY jb.titulo ASC");
	            break;
	        case "Z - A":
	            hql.append(" ORDER BY jb.titulo DESC");
	            break;
	        case "Rating":
	            hql.append(" ORDER BY jb.rating DESC");
	            break;
	        case "Fecha añadido":
	            hql.append(" ORDER BY jb.fechaAñadido DESC");
	            break;
	        default:
	            break;
	        }
	    }

	    // Crear la consulta
	    Query<JuegosBiblioteca> query = session.createQuery(hql.toString(), JuegosBiblioteca.class);
	    query.setParameter("userId", userId);

	    // Establecer los parámetros de los filtros
	    if (title != null && !title.isEmpty()) {
	        query.setParameter("title", "%" + title + "%");
	    }
	    if (plataforma != null && !plataforma.isEmpty()) {
	        query.setParameter("plataforma", plataforma); // Usamos "plataforma" en lugar de "nombre"
	    }

	    // Ejecutar la consulta y retornar los resultados
	    return query.getResultList();
	}
}

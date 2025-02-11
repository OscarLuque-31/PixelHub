package utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    // Inicializa Hibernate al cargar la clase
    static {
        try {
            StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception ex) {
            System.err.println("Error al iniciar Hibernate: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * Devuelve una nueva sesión de Hibernate.
     * @return Objeto Session.
     */
    public static Session getSession() {
        return sessionFactory.openSession();
    }

    /**
     * Cierra el SessionFactory.
     */
    public static void closeSessionFactory() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
        }
    }
}

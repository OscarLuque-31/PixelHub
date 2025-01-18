package dao;

import org.hibernate.Session;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Clase con el DAO generico, CommonDaoImpl
 * 
 */
public abstract class CommonDaoImpl<T> implements CommonDao<T> {

    private Class<T> entityClass;
    private Session session;

    @SuppressWarnings("unchecked")
    protected CommonDaoImpl(Session session) {
        this.entityClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
        this.session = session;
    }

    public void insert(final T paramT) {
        session.persist(paramT);
    }

    public void update(final T paramT) {
        session.merge(paramT);
    }

    public void delete(final T paramT) {
        session.remove(paramT);
    }

    public List<T> searchAll() {
        return session.createQuery("FROM " + this.entityClass.getName(), entityClass).getResultList();
    }

    public Class<T> getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(Class<T> entityClass) {
        this.entityClass = entityClass;
    }
}

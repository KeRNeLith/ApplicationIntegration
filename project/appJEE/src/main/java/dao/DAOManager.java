package dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Basic class for all DAO classes.
 * Created by kernelith on 16/11/16.
 */
@Stateless
public abstract class DAOManager
{
    /**
     * Entity manager (injected).
     */
    @PersistenceContext
    protected EntityManager m_manager;

    /**
     * Get a doctor entity from persistence context.
     * @param id Database doctor id.
     * @param classType Class type of the entity.
     * @return The required entity.
     */
    protected <T> T readEntity(long id, Class<T> classType)
    {
        T entity = null;
        try
        {
            entity = m_manager.find(classType, id);   // Managed
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return (entity);
    }

    /**
     * Get the list of every entity of a type from persistence context.
     * @param classType Class type of the entity.
     * @param q Query specific to the entity to get every entity.
     * @return List of required entities.
     */
    protected <T> List<T> getList(Class<T> classType, String q)
    {
        Query query = m_manager.createNamedQuery(q);
        return query.getResultList();
    }

    /**
     * Update data for the given entity.
     * @param entity Entity.
     * @return Updated entity.
     */
    protected  <T> T updateEntity(T entity)
    {
        T managedEntity = null;

        try
        {
            managedEntity = m_manager.merge(entity); // Managé <= Détaché
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return (managedEntity);
    }

    /**
     * Delete the entity corresponding to the given id.
     * @param id Entity id.
     * @param classType Class type of the entity.
     */
    protected  <T> void deleteEntity(long id, Class<T> classType)
    {
        try
        {
            T entity = m_manager.find(classType, id);
            m_manager.remove(entity);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}

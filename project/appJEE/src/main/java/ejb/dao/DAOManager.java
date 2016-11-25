package ejb.dao;

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
     * Get the list of every entity matching the specified query name.
     * @param queryName Query specific to the entity to get every entity.
     * @param args Query arguments.
     * @return List of required entities.
     */
    protected <T> List<T> getList(String queryName, Object... args)
    {
        Query query = m_manager.createNamedQuery(queryName);
        for (int i = 0 ; i < args.length ; ++i)
        {
            query.setParameter((i + 1), args[i]);
        }

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
     * @return True if the operation succeeded, otherwise false.
     */
    protected <T> boolean deleteEntity(long id, Class<T> classType)
    {
        boolean ret = false;

        try
        {
            T entity = m_manager.find(classType, id);
            if(entity != null) {
                m_manager.remove(entity);
                ret = true;
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return ret;
    }
}

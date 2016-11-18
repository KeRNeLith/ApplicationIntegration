package dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Basic class for all DAO classes.
 * Created by kernelith on 16/11/16.
 */
@Stateless
public abstract class DAOManager
{
    @PersistenceContext
    protected EntityManager m_manager;

    /**
     * Get a doctor entity from persistence context.
     * @param id Database doctor id.
     * @param classType Class type of the entity.
     * @return Doctor entity.
     */
    protected <T> T readEntity(int id, Class<T> classType)
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
    protected  <T> void deleteEntity(int id, Class<T> classType)
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

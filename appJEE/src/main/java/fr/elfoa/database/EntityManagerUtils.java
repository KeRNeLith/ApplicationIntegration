package fr.elfoa.database;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Class that provide utils functions to use EntityManager.
 * Created by ace_nanter on 13/10/16.
 */
public class EntityManagerUtils
{
    /**
     * EntityManagerFactory used to use persistence.
     */
    private static EntityManagerFactory m_emf = null;

    /**
     * EntityManager used to use JPA to handle entities.
     */
    private static EntityManager m_em = null;

    static
    {
        try
        {
            m_emf = Persistence.createEntityManagerFactory("myPersistenceUnit");
            m_em = m_emf.createEntityManager();
        }
        catch (Exception e)
        {
            throw new ExceptionInInitializerError(e);
        }
    }

    /**
     * Get the EntityManager.
     * @return EntityManager.
     */
    public static EntityManager getEntityManager()
    {
        return m_em;
    }
}
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
     * EntityManagerFactory used to use persistance.
     */
    private static EntityManagerFactory emf = null;

    /**
     * EntityManager used to use CRUD on entities.
     */
    private static EntityManager em = null;

    static {
        emf = Persistence.createEntityManagerFactory("defaultPersistanceUnit");
        em = emf.createEntityManager();
    }

    /**
     * Get the EntityManager
     * @return EntityManager
     */
    public static EntityManager getEntityManager() {
        return em;
    }
}

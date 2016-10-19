package fr.elfoa.entities;

import fr.elfoa.database.EntityManagerUtils;

import javax.persistence.EntityManager;

/**
 * Class that store informations about a user (implementing CRUD for JTA).
 * Created by kernelith on 18/10/16.
 */
public class ManagedUser
{
    public User createUser(String name)
    {
        User user = null;

        try
        {
            user = new User(name); // In Memory

            EntityManagerUtils.getEntityManager().persist(user);   // Managed
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return (user);
    }

    public User readUser(int id)
    {
        User user = null;
        try
        {
            user = EntityManagerUtils.getEntityManager().find(User.class, id);   // Managed
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return (user);
    }

    public User updateUser(User user)
    {
        User managedUser = null;

        try
        {
            managedUser = EntityManagerUtils.getEntityManager().merge(user); // Managé <= Détaché
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return (managedUser);
    }

    public void deleteUser(int id)
    {
        try
        {
            EntityManager em = EntityManagerUtils.getEntityManager();

            User user = em.find(User.class, id);
            em.remove(user);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}

package fr.elfoa.entities;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Class that store informations about a user (implementing CRUD for JTA).
 * Created by kernelith on 18/10/16.
 */
@Stateless
public class ManagedUser
{
    @PersistenceContext
    private EntityManager m_manager;

    public User createUser(String name)
    {
        User user = null;

        try
        {
            user = new User(name); // In Memory

            m_manager.persist(user);   // Managed
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
            user = m_manager.find(User.class, id);   // Managed
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
            managedUser = m_manager.merge(user); // Managé <= Détaché
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
            User user = m_manager.find(User.class, id);
            m_manager.remove(user);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}

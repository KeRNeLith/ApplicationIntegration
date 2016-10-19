package fr.elfoa.entities;


import fr.elfoa.database.EntityManagerUtils;

import javax.persistence.EntityManager;

/**
 * Class that store informations about a todo task (implementing CRUD for JTA).
 * Created by kernelith on 18/10/16.
 */
public class ManagedTodo
{
    public Todo createTodo(String name)
    {
        Todo todo = null;

        try
        {
            todo = new Todo(name); // In Memory

            EntityManagerUtils.getEntityManager().persist(todo);   // Managed
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return (todo);
    }

    public Todo readTodo(int id)
    {
        Todo todo = null;
        try
        {
            todo = EntityManagerUtils.getEntityManager().find(Todo.class, id);   // Managed
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return (todo);
    }

    public Todo updateTodo(Todo todo)
    {
        Todo managedTodo = null;

        try
        {
            managedTodo = EntityManagerUtils.getEntityManager().merge(todo); // Managé <= Détaché
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return (managedTodo);
    }

    public void deleteTodo(int id)
    {
        try
        {
            EntityManager em = EntityManagerUtils.getEntityManager();

            Todo todo = em.find(Todo.class, id);
            em.remove(todo);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}

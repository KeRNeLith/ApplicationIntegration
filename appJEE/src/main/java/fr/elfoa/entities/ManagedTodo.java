package fr.elfoa.entities;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Class that store informations about a todo task (implementing CRUD for JTA).
 * Created by kernelith on 18/10/16.
 */
@Stateless
public class ManagedTodo
{
    @PersistenceContext
    private EntityManager m_manager;

    public Todo createTodo(String name)
    {
        Todo todo = null;

        try
        {
            todo = new Todo(name); // In Memory

            m_manager.persist(todo);   // Managed
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
            todo = m_manager.find(Todo.class, id);   // Managed
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
            managedTodo = m_manager.merge(todo); // Managé <= Détaché
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
            Todo todo = m_manager.find(Todo.class, id);
            m_manager.remove(todo);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}

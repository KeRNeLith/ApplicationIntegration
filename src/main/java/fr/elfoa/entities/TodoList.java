package fr.elfoa.entities;

import java.util.*;

/**
 * Class that store a association of todos and a given id.
 * @author Pierre Colomb
 */
public class TodoList 
{
    /**
     * Association of an Id and a todo task.
     */
    private final Map<Integer, Todo> todos = new HashMap<>();

    /**
     * Add a todo in the list of todos.
     * @param todo Todo to add.
     * @return The current todo list with the todo added.
     */
    public TodoList add(Todo todo)
    {
        Integer id = todos.keySet()
                          .stream()
                          .max(Comparator.naturalOrder())
                          .orElse(0);

        todos.put(id + 1 ,todo);

        return this;
    }

    /**
     * Add or update (if the id already exists) the given todo to the todo list.
     * @param id Todo's Id.
     * @param todo Todo to add.
     * @return The current todo list with the todo added.
     */
    public TodoList add(Integer id, Todo todo)
    {
        todos.put(id,todo);
        return this;
    }

    /**
     * Set the todo with the given Id as done (if it exists).
     * @param id Todo's Id.
     * @return The current todo list.
     */
    public TodoList done(Integer id)
    {
        Todo todo = todos.get(id);

        if(todo != null)
        {
            todo.setDone(true);
        }

        return this;
    }

    /**
     * Set the todo with the given Id as not done (if it exists).
     * @param id Todo's Id.
     * @return The current todo list.
     */
    public TodoList unDone(Integer id)
    {
        Todo todo = todos.get(id);

        if(todo != null)
        {
            todo.setDone(false);
        }

        return this;
    }

    /**
     * Update the todo corresponding to the given Id.
     * @param id Todo's Id.
     * @param todo Todo to update.
     * @return Updated todo.
     */
    public Todo update(Integer id, Todo todo)
    {
        return todos.replace(id,todo);
    }

    /**
     * Delete the todo corresponding to the given id.
     * @param id Todo's Id.
     * @return Todo removed.
     */
    public Todo delete(Integer id)
    {
        return todos.remove(id);
    }

    /**
     * Get a todo based on its Id.
     * @param id Todo's Id.
     * @return The todo corresponding to the given Id if it exists, otherwise null.
     */
    public Todo getbyId(Integer id)
    {
        return todos.get(id);
    }

    /**
     * Get the list of all todo tasks.
     * @return Collection of todos.
     */
    public Collection<Todo> getAll()
    {
        return todos.values();
    }
}

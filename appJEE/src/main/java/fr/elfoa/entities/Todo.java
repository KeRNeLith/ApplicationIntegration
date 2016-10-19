package fr.elfoa.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * Class that store informations about a todo task.
 * @author Pierre Colomb
 */
@Entity
public class Todo
{
    /**
     * ID of the todo.
     */
    @Id
    @GeneratedValue
    @Column(name = "id_todo")
    private Integer id;

    /**
     * Name of the todo.
     */
    @Column(name = "name")
    private String name;

    /**
     * Due date of the todo.
     */
    @Column(name = "due_date")
    private final Date dueDate;

    /**
     * State "done" or not of the todo task.
     */
    @Column(name = "done")
    private boolean done = false;

    /**
     * Constructor.
     * @param name Name of the todo.
     * @param dueDate Date when the todo should be finished.
     */
    public Todo(String name, Date dueDate)
    {

        this.name = name;
        this.dueDate = dueDate;
    }

    /**
     * Constructor.
     * This initialization will have no due date defined.
     * @param name Name of the todo
     */
    public Todo(String name)
    {
        this(name, null);
    }

    /**
     * Get the name of the todo task.
     * @return Todo name.
     */
    public String getName()
    {
        return name;
    }

    /**
     * Get the date when the todo should be finished.
     * @return Due date.
     */
    public Date getDueDate()
    {
        return dueDate;
    }

    /**
     * Get the "done" state of the todo.
     * @return True if the todo is done.
     */
    public boolean isDone()
    {
        return done;
    }

    /**
     * Set the todo as done or not.
     * @param done State done of the todo.
     */
    public void setDone(boolean done)
    {
        this.done = done;
    }
}
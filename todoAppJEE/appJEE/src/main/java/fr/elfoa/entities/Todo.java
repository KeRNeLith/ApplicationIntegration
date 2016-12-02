package fr.elfoa.entities;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * Class that store informations about a todo task.
 * @author Pierre Colomb
 */
@Entity
@Table(name = "todo")
@XmlRootElement
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
     * User associated to the todo.
     */
    @ManyToOne
    private User user;

    /**
     * Constructeur par défaut.
     */
    public Todo()
    {
        this("Default");
    }

    /**
     * Constructor.
     * This initialization will have no due date defined.
     * @param name Name of the todo
     */
    public Todo(String name)
    {
        this(name, new Date());
    }

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
         * Constructeur par recopie.
         * @param todo Todo.
         */
        public Todo(Todo todo)
        {
            this.id = todo.id;
            this.name = todo.name;
            this.done = todo.done;
            this.dueDate = todo.dueDate;
            this.user = todo.user;
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

    /**
     * Set the todo owner.
     * @param user Owner.
     */
    public void setUser(User user)
    {
        this.user = user;
    }
}

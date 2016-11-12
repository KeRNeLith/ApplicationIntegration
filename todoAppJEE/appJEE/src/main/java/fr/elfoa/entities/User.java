package fr.elfoa.entities;

import javax.persistence.*;
import java.util.List;

/**
 * Class that store informations about a user.
 * Created by kernelith on 18/10/16.
 */
@Entity
@Table(name = "user")
public class User
{
    /**
     * ID of the user.
     */
    @Id
    @GeneratedValue
    @Column(name = "id_user")
    private Integer id;

    /**
     * Name of the user.
     */
    @Column(name = "name")
    private String name;

    /**
     * List of todos associated to the user.
     */
    @OneToMany(mappedBy = "user")
    private List<Todo> todos;

    /**
     * Constructeur par défaut.
     */
    public User()
    {
        this("Default");
    }

    /**
     * Constructor.
     * @param name Name of the user.
     */
    public User(String name)
    {
        this.name = name;
    }

    /**
     * Get the name of the user.
     * @return Username.
     */
    public String getName()
    {
        return name;
    }

    /**
     * Get the list of user's todos.
     * @return List of todos.
     */
    public List<Todo> getTodos()
    {
        return todos;
    }

    public void addTodo(Todo todo)
    {
        todos.add(todo);
    }
}

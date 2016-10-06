package fr.elfoa.entities;

import java.util.Date;

/**
 * @author Pierre Colomb
 */
public class Todo {


    private final String name;

    private final Date dueDate;

    private boolean done = false;



    public Todo(String name, Date dueDate) {
        this.name = name;
        this.dueDate = dueDate;
    }

    public Todo(String name) {
        this.name = name;
        this.dueDate = null;
    }



    public String getName() {
        return name;
    }



    public Date getDueDate() {
        return dueDate;
    }



    public boolean isDone() {
        return done;
    }



    public void setDone(boolean done) {
        this.done = done;
    }
}

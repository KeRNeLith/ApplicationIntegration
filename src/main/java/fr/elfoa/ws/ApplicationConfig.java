package fr.elfoa.ws;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * Class that handle the definition of the application.
 * The definiton of all web services offered by the application.
 */
@ApplicationPath("ws")
public class ApplicationConfig extends Application 
{
    /**
     * Get all classes that define web service interfaces.
     * @return Set of classes defining web services available through the application.
     */
    @Override
    public Set<Class<?>> getClasses() 
    {
        Set<Class<?>> service = new HashSet<>();

        // Add N web services classes
        service.add(HelloWorld.class);
        service.add(TodoListWS.class);

        return service;
    }     
}

package webservice;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * Class that handle the definition of the web service application.
 * The definition of all web services offered by the application.
 * Created by kernelith on 12/11/16.
 */
@ApplicationPath("api")
public class WebServiceApplication extends Application
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
        // Test API
        service.add(HelloWorld.class);

        // Doctor APIs
        service.add(DoctorAPI.class);
        service.add(DoctorsAPI.class);

        // TimeInterval APIs
        service.add(TimeSlotAPI.class);

        return service;
    }
}

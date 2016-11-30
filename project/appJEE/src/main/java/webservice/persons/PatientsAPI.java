package webservice.persons;

import ejb.dao.persons.ManagedPatient;
import entities.persons.Patient;
import org.codehaus.jackson.map.ObjectMapper;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class that handle Patients web service routes.
 * Created by kernelith on 30/11/16.
 */
@Path("patients")
public class PatientsAPI
{
    /**
     * Logger for patient web service.
     */
    private static final Logger LOG = Logger.getLogger(PatientsAPI.class.getCanonicalName());

    /**
     * Manager of Patient : EJB.
     */
    @EJB
    private ManagedPatient m_patientManager;

    /**
     * Get the list of Patient already created.
     * @return A Response containing a list of Patient as JSON.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response getAllPatients()
    {
        Response response = Response.ok("{}", MediaType.APPLICATION_JSON).build();

        List<Patient> patientList = null;

        // Get List of Patients
        try
        {
            patientList = m_patientManager.readAllPatients();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            response = Response .status(Response.Status.INTERNAL_SERVER_ERROR)
                                .entity("{\n\t\"error\": \"" + e.getMessage() + "\"\n}")
                                .build();
        }

        // Create the JSON response including all Doctors.
        if (patientList != null)
        {
            ObjectMapper mapper = new ObjectMapper();
            Writer writer = new StringWriter();

            try
            {
                mapper.writeValue(writer, patientList);

                writer.close();
                response = Response.ok(writer.toString(), MediaType.APPLICATION_JSON).build();
            }
            catch (IOException e)
            {
                LOG.log(Level.SEVERE, "Unable to serialize patient list.");
                response = Response .status(Response.Status.INTERNAL_SERVER_ERROR)
                                    .entity("{\n\t\"error\": \"Unable to serialize patient list.\"\n}")
                                    .build();
            }
        }

        return response;
    }
}

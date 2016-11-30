package webservice;

import ejb.dao.persons.ManagedDoctor;
import ejb.dao.persons.ManagedPatient;
import ejb.face.AppointmentEJB;
import ejb.face.TimeSlotEJB;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.Calendar;
import java.util.Date;

/**
 * Class that handle Hello World web service route.
 */
@Path("hello")
public class HelloWorld 
{
    /**
     * Hello world page template (as string).
     */
    private static final String TEMPLATE  = "{ \"Hello\" : \"world\" }";

    @EJB
    AppointmentEJB ejba;
    @EJB
    TimeSlotEJB ejbt;
    @EJB
    ManagedPatient ejbp;
    @EJB
    ManagedDoctor ejbd;
    /**
     * Route to test the application : Hello World.
     * @return Hello world page as JSON content.
     */
    @GET
    @Produces("application/json")
    public String sayHello() 
    {
        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        cal.add(Calendar.HOUR, 1);
        now = cal.getTime();

        cal.setTime(now);
        cal.add(Calendar.HOUR, 15);
        Date nowPlus = cal.getTime();

        //ejbd.deleteDoctor(1);
        //ejbt.deleteTimeSlot(1);
        /*String str = "Before : \n";
        for (TimeSlot tm : ejbt.getAvailableTimeSlots())
        {
            str += "Nb app : " + tm.getAppointments().size() + "\n Nb interval : " + tm.getAvailableSlots().size() + '\n';
            for (TimeInterval ti : tm.getAvailableSlots())
            {
                str += ti.getBegin().toString() + " " + ti.getEnd().toString() + '\n';
            }
        }

        ejba.cancelAppointment(1);

        str += "\nAfter : \n";
        for (TimeSlot tm : ejbt.getAvailableTimeSlots())
        {
            str += "Nb app : " + tm.getAppointments().size() + "\n Nb interval : " + tm.getAvailableSlots().size() + '\n';
            for (TimeInterval ti : tm.getAvailableSlots())
            {
             str += ti.getBegin().toString() + " " + ti.getEnd().toString() + '\n';
            }
        }*/
        //ejbp.deletePatient(2);
        //ejba.modifyAppointment(1, 3);
        ejba.modifyAppointment(15, now, nowPlus);

        //ejb.createAppointment(now, nowPlus, ejb2.readTimeSlot(1), ejbp.readPatient(3));
        return TEMPLATE + ejbt.getAvailableTimeSlots().size();
    }
}

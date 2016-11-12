package fr.elfoa.utils;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Class that handle interaction with JNDI and provide convenient functions.
 * @author kernelith
 */
public class JNDIUtils 
{   
    /**
     * Get the value in the JNDI corresponding to the given resource name.
     * @param name Resource name.
     * @return Value associated to the given resource name, otherwise null.
     */
    public static <T> T getValue(final String name)
    {
        T retValue = null;
        
        try 
        {
            Context context = new InitialContext();
            retValue = (T) context.lookup(name);    // Get the property value of the JNDI ressource matching the given name
        } 
        catch (NamingException ex) 
        {
            Logger.getLogger(JNDIUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return retValue;
    }
    
    /**
     * Set the value in the JNDI to the given resource name.
     * @param name Resource name.
     * @param value Value to set in the JNDI.
     */
    public static <T> void setValue(final String name, final T value)
    {
        try 
        {
            Context context = new InitialContext();
            context.rebind(name, value);
        } 
        catch (NamingException ex) 
        {
            Logger.getLogger(JNDIUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

package fr.elfoa.database;

import fr.elfoa.utils.JNDIUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Class that provide utils function to access database.
 * Created by kernelith on 11/10/16.
 */
public class DatabaseUtils
{
    /**
     * Datasource used to make SQL requests (contains connection parameters).
     */
    private static DataSource m_source;

    /**
     * Connection used to make request.
     */
    private static Connection m_connection;

    /**
     * Static constructor.
     */
    static
    {
        m_source = JNDIUtils.getValue("tp_iae");
        try
        {
            m_connection = m_source.getConnection();
        }
        catch (SQLException e)
        {
            System.err.println("Impossible to connect to datasource.");
        }
    }

    /**
     * Get the connection to the database.
     * @return Database connection.
     */
    public static Connection getConnection()
    {
        return m_connection;
    }
}

package fr.elfoa.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Provide convenient function to get information from User database table.
 * Created by kernelith on 19/10/16.
 */
public class UserDBAccess
{
    /**
     * Get the user name from it's id.
     * @param userId Id of the searched user.
     */
    public static String getUserName(int userId)
    {
        String ret = "Nobody matching id = " + userId + ".";

        PreparedStatement ps;
        try
        {
            ps = DatabaseUtils.getConnection().prepareStatement("SELECT name FROM user WHERE id = ?;");
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();
            if (rs.next())
            {
                int NAME = 1;
                ret = rs.getString(NAME);
                rs.close();
            }

            ps.close();
        }
        catch (SQLException e)
        {
            System.err.println("There is a problem with the connection to database.");
        }

        return ret;
    }
}

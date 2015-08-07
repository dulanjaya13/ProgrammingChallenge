package database.connector;

import com.mysql.jdbc.Driver;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import org.apache.log4j.Logger;
import util.definitions.SQLConstants;

/**
 *
 * @author Nadheesh
 */
public class DBConnection {

    
    
    private static DBConnection dbConnection;
    private Connection connection;
    
    private DBConnection() throws SQLException{
        
        DriverManager.registerDriver(new Driver());
        try {
            Properties connectionProps = new Properties();
            connectionProps.put("user", SQLConstants.USER_NAME);
            connectionProps.put("password", SQLConstants.PASSWORD);
            
            connection = DriverManager.getConnection("jdbc:mysql://" + SQLConstants.SERVER + ":" + SQLConstants.PORT + "/" + SQLConstants.DATABASE, connectionProps);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static DBConnection getDBConnection() throws SQLException{
        
        if (dbConnection == null){
            dbConnection=new DBConnection();
        }
        return dbConnection;
    }
    
    private Connection getConnection(){
        
        return connection;
    }
    
    private void closeConnection() throws SQLException {
        
        if (connection != null) {
            connection.close();
            
        }
    }
    
    public static Connection getConnectionToDB() throws SQLException {
        return getDBConnection().getConnection();
    }

    public static void closeConnectionToDB() throws SQLException {
        
        if (dbConnection != null) {
            dbConnection.closeConnection();
            dbConnection = null;
            System.gc();
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        closeConnection();
    }
}

 /**
 * Name: Amit Nijjar
 * PID: A11489111
 * ucsd email: a2nijjar@ucsd.edu
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
public class Project2Class {
 
 
 public static void main(String[] args) {
 Connection conn = null; // Database connection.
 try {
	 // Load the JDBC class.
	 Class.forName("org.sqlite.JDBC");

	 conn = DriverManager.getConnection("jdbc:sqlite:pa2.db");
	 System.out.println("Opened database successfully."); 

	 Statement stmt = conn.createStatement();
	 
	 stmt.executeUpdate("DROP TABLE IF EXISTS Current;");
	 stmt.executeUpdate("DROP TABLE IF EXISTS changed;");
	 stmt.executeUpdate("DROP TABLE IF EXISTS T_old;");
	 stmt.executeUpdate("DROP TABLE IF EXISTS Connected;");


	 stmt.executeUpdate("CREATE TABLE Connected(Airline char(32), Origin char(32), " +
	 	"Destination char(32), Stop int);");
	 stmt.executeUpdate("CREATE TABLE Current AS SELECT * FROM Flight;");
	 stmt.executeUpdate("CREATE TABLE changed AS SELECT * FROM Flight;");
	 stmt.executeUpdate("CREATE TABLE T_old(Airline char(32), Origin char(32), " + 
	 	"Destination char(32));");	

	int delta = stmt.executeUpdate("INSERT INTO Connected SELECT Airline, " + 	
	"Origin, Destination, 0 FROM Flight;");

	
        // main chunk of algorithm
	int tau = 1;
	while(delta != 0)
	{
		stmt.executeUpdate("DROP TABLE IF EXISTS T_old;");
		stmt.executeUpdate("CREATE TABLE T_old AS SELECT * FROM Current;");
		stmt.executeUpdate("DROP TABLE IF EXISTS Current;");
        
        String firstQuery = "CREATE TABLE Current AS SELECT * FROM T_old UNION " + 
        		"SELECT f.Airline, f.Origin, c.Destination FROM Flight f, Changed c " + 
        		"WHERE f.Destination = c.Origin AND f.Airline = c.Airline AND " + 
        		"f.Origin <> c.Origin AND f.Destination <> c.Destination AND " + "f.Origin <> c.Destination;";
        
        stmt.executeUpdate(firstQuery);
        stmt.executeUpdate("DROP TABLE IF EXISTS Changed;");
                
        String secondQuery = "CREATE TABLE Changed AS SELECT * FROM Current " + 
        		"EXCEPT SELECT * FROM T_old;";
        stmt.executeUpdate(secondQuery);

        String thirdQuery = "INSERT INTO Connected SELECT Airline, Origin, " + 
        		"Destination, " + tau + " FROM Changed;";
                delta = stmt.executeUpdate(thirdQuery);
        tau++;
	}


    stmt.executeUpdate("DROP TABLE IF EXISTS Current;");
    stmt.executeUpdate("DROP TABLE IF EXISTS Changed;");
    stmt.executeUpdate("DROP TABLE IF EXISTS T_old;");
	 stmt.close();
 	} catch (Exception e) {
 	throw new RuntimeException("There was a runtime problem!", e);
 	} finally {
 	try {
 	if (conn != null) conn.close();
 	} catch (SQLException e) {
 	throw new RuntimeException(
 	"Cannot close the connection!", e);
 	}
   }
 }
}
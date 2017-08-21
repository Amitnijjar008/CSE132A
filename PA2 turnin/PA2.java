/**
 * Name: Amit Nijjar
 * PID: A11489111
 * UCSD Email: a2nijjar@ucsd.edu
 */


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.Statement;




public class PA2 {
 
 
 public static void main(String[] args) {
 Connection conn = null; // Database connection.
 try {
	// Load the JDBC class.
	Class.forName("org.sqlite.JDBC");
	
	//set up connection
    conn = DriverManager.getConnection("jdbc:sqlite:pa2.db");
	System.out.println("Opened database successfully."); 
    
        
	Statement stmt = conn.createStatement();
    
	
	stmt.executeUpdate("DROP TABLE IF EXISTS temp;");
	
	stmt.executeUpdate("DROP TABLE IF EXISTS updated;");
	
	stmt.executeUpdate("DROP TABLE IF EXISTS prev;");
	
	stmt.executeUpdate("DROP TABLE IF EXISTS Connected;");
    
    
	stmt.executeUpdate("CREATE TABLE Connected(Airline char(32), Origin char(32), Destination char(32), Stop int);");
	
	stmt.executeUpdate("CREATE TABLE temp AS SELECT * FROM Flight;");
	
	stmt.executeUpdate("CREATE TABLE updated AS SELECT * FROM Flight;");
	
	stmt.executeUpdate("CREATE TABLE prev(Airline char(32), Origin char(32), Destination char(32));");	

	int y = stmt.executeUpdate("INSERT INTO Connected SELECT Airline, Origin, Destination, 0 FROM Flight;");

	
	int x = 1;
	while(y != 0)
	{
		
		stmt.executeUpdate("DROP TABLE IF EXISTS prev;");
		
		stmt.executeUpdate("CREATE TABLE prev AS SELECT * FROM temp;");
		
		stmt.executeUpdate("DROP TABLE IF EXISTS temp;");
        
        String firstQuery = "CREATE TABLE temp AS SELECT * FROM prev UNION " + 
        		"SELECT f.Airline, f.Origin, c.Destination FROM Flight f, updated c " + 
        		"WHERE f.Destination = c.Origin AND f.Airline = c.Airline AND " + 
        		"f.Origin <> c.Origin AND f.Destination <> c.Destination AND " + "f.Origin <> c.Destination;";
        
        stmt.executeUpdate(firstQuery);
        
		stmt.executeUpdate("DROP TABLE IF EXISTS updated;");
                
        String secondQuery = "CREATE TABLE updated AS SELECT * FROM temp " + 
        		"EXCEPT SELECT * FROM prev;";
        
		stmt.executeUpdate(secondQuery);

        String thirdQuery = "INSERT INTO Connected SELECT Airline, Origin, " + 
        		"Destination, " + x + " FROM updated;";
                y = stmt.executeUpdate(thirdQuery);
        x++;
	}

	
    stmt.executeUpdate("DROP TABLE IF EXISTS temp;");
    stmt.executeUpdate("DROP TABLE IF EXISTS updated;");
    stmt.executeUpdate("DROP TABLE IF EXISTS prev;");
	stmt.close();
 	
	} catch (Exception e) {
		throw new RuntimeException("RuntimeException 1", e);
 	} 
	
	finally 
	{
		try 
		{
		if (conn != null) conn.close();
		}
		catch (SQLException e) 
		{
		throw new RuntimeException("RuntimeException 2", e);
		}
		}
	}
}
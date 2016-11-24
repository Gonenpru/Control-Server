package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	
	private Connection connection;

	public DBConnection(){
		createConnection();
	}

	private void createConnection(){
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/gonenpru","postgres", "123");
			connection.close();	
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
			
	}



}

package agent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import exceptions.DatabaseConnectionException;

/**
 * The 'DbAccess' class establishes a connection to a MySQL database and
 * provides methods for
 * initializing, getting, and closing the connection.
 */
public class DbAccess {

	private static String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
	private final String DBMS = "jdbc:mysql";
	private final String SERVER = "localhost";
	private final String DATABASE = "MapDB";
	private final int PORT = 3306;
	private final String USER_ID = "MapUser";
	private final String PASSWORD = "map";

	private Connection conn;

	/**
	 * This function initializes a database connection 'conn'and throws exceptions
	 * if there are errors. <br>
	 * <br>
	 * These are instance variables that store the necessary information to
	 * establish a connection to a MySQL database: <br>
	 * <br>
	 * 'DRIVER_CLASS_NAME' stores the name of the JDBC driver class for MySQL.
	 * <br>
	 * <br>
	 * 'DBMS' stores the protocol used to connect to the database.
	 * <br>
	 * <br>
	 * 'SERVER' stores the name of the server where the database is hosted.
	 * <br>
	 * <br>
	 * 'DATABASE' stores the name of the database,
	 * <br>
	 * <br>
	 * 'PORT' stores the port number used to connect to the database.
	 * <br>
	 * <br>
	 * 'USER_ID' stores the username used to authenticate the connection.
	 * <br>
	 * <br>
	 * 'PASSWORD' stores the password used to authenticate the connection.
	 * 
	 * @throws DatabaseConnectionException
	 * 
	 */
	public void initConnection() throws DatabaseConnectionException {
		try {
			Class.forName(DRIVER_CLASS_NAME);
			String url = DBMS + "://" + SERVER + ":" + PORT + "/" + DATABASE;
			conn = DriverManager.getConnection(url, USER_ID, PASSWORD);
		} catch (ClassNotFoundException e) {
			throw new DatabaseConnectionException("Driver class not found: " + e.getMessage());
		} catch (SQLException e) {
			throw new DatabaseConnectionException("Failed to connect to database: " + e.getMessage());
		}
	}

	/**
	 * The function returns a connection object for a database and throws a
	 * SQLException if there is an error.
	 * 
	 * @return returns a 'Connection' object.
	 * @throws SQLException
	 */
	public Connection getConnection() throws SQLException {
		return conn;
	}

	/**
	 * This function closes a database connection and throws a SQLException if there
	 * is an error.
	 * 
	 * @throws SQLException
	 */
	public void closeConnection() throws SQLException {
		conn.close();
	}
}
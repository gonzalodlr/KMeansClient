package agent;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The TableSchema class retrieves and stores the schema information of a
 * database table.
 */
public class TableSchema {
	protected DbAccess db;

	/**
	 * The "Column" class represents a database column with a name and type, and
	 * provides methods to retrieve the column name and check if the column is a
	 * number. It is an inner class of 'TableSchema'.
	 */
	public class Column {
		private String name;
		private String type;

		/**
		 * This is a constructor for the inner class 'Column' that takes in two
		 * parameters: 'name' and 'type' and assigns them to the instance variables
		 * 'name' and 'type'. This constructor is used to create a new 'Column'.
		 * 
		 * @param name name of the column
		 * @param type type of the column
		 */
		public Column(String name, String type) {
			this.name = name;
			this.type = type;
		}

		/**
		 * The function returns the name of a column.
		 * 
		 * @return the value of the variable 'name'.
		 */
		public String getColumnName() {
			return name;
		}

		/**
		 * The function checks if a given type is equal to "number" and returns a
		 * boolean value.
		 * 
		 * @return A boolean value indicating whether the type of the object is "number"
		 *         or not.
		 */
		public boolean isNumber() {
			return type.equals("number");
		}

		/**
		 * The function returns a string representation of an object's name and type.
		 * 
		 * @return the name and type, separated by a colon.
		 */
		public String toString() {
			return name + ":" + type;
		}
	}

	private List<Column> tableSchema = new ArrayList<Column>();

	/**
	 * The constructor for the TableSchema class that takes in a DbAccess
	 * object and a table name as parameters.
	 * It retrieves the schema information of the specified table from the database
	 * using the DbAccess object and stores it in a list of Column objects called
	 * tableSchema. It uses a HashMap to map SQL data types to Java data types, and
	 * then uses the DatabaseMetaData object to retrieve information about the
	 * columns in the table. For each column, it checks if the data type is in the
	 * HashMap and if so, adds a new Column object to the tableSchema list with the
	 * column name and the corresponding Java data type. Finally, it closes the
	 * ResultSet object.
	 * 
	 * @param db        the database
	 * @param tableName name of the table which contain the data
	 * @throws SQLException
	 */
	public TableSchema(DbAccess db, String tableName) throws SQLException {
		this.db = db;
		HashMap<String, String> mapSQL_JAVATypes = new HashMap<String, String>();
		// http://java.sun.com/j2se/1.3/docs/guide/jdbc/getstart/mapping.html
		mapSQL_JAVATypes.put("CHAR", "string");
		mapSQL_JAVATypes.put("VARCHAR", "string");
		mapSQL_JAVATypes.put("LONGVARCHAR", "string");
		mapSQL_JAVATypes.put("BIT", "string");
		mapSQL_JAVATypes.put("SHORT", "number");
		mapSQL_JAVATypes.put("INT", "number");
		mapSQL_JAVATypes.put("LONG", "number");
		mapSQL_JAVATypes.put("FLOAT", "number");
		mapSQL_JAVATypes.put("DOUBLE", "number");

		Connection con = db.getConnection();
		DatabaseMetaData meta = con.getMetaData();
		ResultSet res = meta.getColumns(null, null, tableName, null);

		while (res.next()) {
			if (mapSQL_JAVATypes.containsKey(res.getString("TYPE_NAME")))
				tableSchema.add(
						new Column(res.getString("COLUMN_NAME"), mapSQL_JAVATypes.get(res.getString("TYPE_NAME"))));
		}
		res.close();
	}

	/**
	 * This function returns the number of attributes in a table schema.
	 * 
	 * @return the size of the 'tableSchema', which
	 *         represents the number of attributes in a table.
	 */
	public int getNumberOfAttributes() {
		return tableSchema.size();
	}

	/**
	 * This function returns a Column object from a table schema based on the given
	 * index.
	 * 
	 * @param index integer value representing the index of the column that
	 *              needs to be retrieved from the table schema.
	 * @return A Column object is being returned. The specific Column object being
	 *         returned is determined by the index parameter passed to the method.
	 */
	public Column getColumn(int index) {
		return tableSchema.get(index);
	}
}
package agent;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import agent.TableSchema.Column;
import exceptions.EmptySetException;
import exceptions.NoValueException;

/**
 * The TableData class provides methods for retrieving distinct transactions,
 * distinct column values, and aggregate column values from a database table.
 */
public class TableData {

	private DbAccess db;

	/**
	 * This is a constructor for the 'TableData' class that takes a 'DbAccess'
	 * object as a parameter and assigns it to the 'db' instance variable of the
	 * 'TableData' object being created. This allows the 'TableData' object to acces
	 * the database through the 'DbAccess' object.
	 * 
	 * @param db
	 */
	public TableData(DbAccess db) {
		this.db = db;
	}

	/**
	 * This Java function retrieves distinct transactions from a specified table on
	 * a database and returns them as a list of Example objects.
	 * 
	 * @param table The name of the database table from which to retrieve distinct
	 *              transactions.
	 * @return The method is returning a List of Example objects, which represent
	 *         distinct transactions from a specified table in a database.
	 * @throws SQLException
	 */
	public List<Example> getDistinctTransactions(String table) throws SQLException, EmptySetException {
		List<Example> distinctTransactions = new ArrayList<Example>();
		TableSchema tableSchema = new TableSchema(db, table);

		Connection con = db.getConnection();
		Statement s = con.createStatement();
		ResultSet r = s.executeQuery("SELECT DISTINCT * FROM " + table);

		if (!r.next()) {
			// ResultSet empty exception
			throw new EmptySetException("No distinct transactions found in the table.");
		} else {
			do {
				Example example = new Example();
				for (int i = 0; i < tableSchema.getNumberOfAttributes(); i++) {
					TableSchema.Column column = tableSchema.getColumn(i);
					if (column.isNumber()) {
						// Should add the Double value, as getDouble()?
						example.add(new BigDecimal(Float.toString(r.getFloat(column.getColumnName()))).doubleValue());
					} else {
						example.add(r.getString(column.getColumnName()));
					}
				}
				distinctTransactions.add(example);
			} while (r.next());
		}
		r.close();
		s.close();

		return distinctTransactions;
	}

	/**
	 * This Java function retrieves distinct values from a specified column in a
	 * database table and returns them in a sorted set.
	 * 
	 * @param table  The name of the database table from which to retrieve the
	 *               distinct column values.
	 * @param column The column whose distinct values are to be retrieved from the
	 *               specified table.
	 * @return A set of distinct values from a specific column in a database table.
	 * @throws SQLException
	 */
	public Set<Object> getDistinctColumnValues(String table, Column column) throws SQLException {
		Set<Object> distinctValues = new TreeSet<>();

		Connection con = db.getConnection();
		Statement s = con.createStatement();
		ResultSet r = s.executeQuery("SELECT DISTINCT " + column.getColumnName() + " FROM " + table + " ORDER BY "
				+ column.getColumnName() + " ASC");

		while (r.next()) {
			Object value = r.getObject(column.getColumnName());
			distinctValues.add(value);
		}
		r.close();
		s.close();

		return distinctValues;
	}

	/**
	 * This function retrieves the minimum or maximum value of a specified column in
	 * a given table using a SQL query and returns it as a double.
	 * 
	 * @param table     The name of the table in the database from which to obtain
	 *                  the data.
	 * @param column    The column for which the aggregate value is being
	 *                  calculated.
	 * @param aggregate an enum type QUERY_TYPE that specifies the type of aggregate
	 *                  function to be used (either MIN or MAX)
	 * @return the value of the aggregate column specified by the input parameters.
	 * @throws SQLException
	 * @throws NoValueException
	 */
	public Object getAggregateColumnValue(String table, Column column, QUERY_TYPE aggregate)
			throws SQLException, NoValueException {
		Object result = null;
		Connection con = db.getConnection();
		Statement s = con.createStatement();
		String query = "";

		if (aggregate == QUERY_TYPE.MIN) {
			query = "SELECT MIN(" + column.getColumnName() + ") AS " + column.getColumnName() + " FROM " + table;
		} else if (aggregate == QUERY_TYPE.MAX) {
			query = "SELECT MAX(" + column.getColumnName() + ") AS " + column.getColumnName() + " FROM " + table;
		}

		ResultSet r = s.executeQuery(query);

		if (r.next()) {
			// result = r.getObject(column.getColumnName());
			result = new BigDecimal(Float.toString(r.getFloat(column.getColumnName()))).doubleValue();
		} else {
			// ResultSet empty NoValueException
			throw new NoValueException("No value found for aggregate " + aggregate);
		}

		if (result.equals(null)) {
			// ResultSet null: NoValueException
			throw new NoValueException("No value found for aggregate " + aggregate);
		}

		r.close();
		s.close();

		return result;
	}
}
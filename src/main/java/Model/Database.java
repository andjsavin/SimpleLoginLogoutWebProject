package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Database extends SQLException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Pobiera z bazy danych pe³n¹ nazwê u¿ytkownika.
	 * 
	 * @param userid
	 *            ci¹g identyfikatora u¿ytkownika
	 * @return ci¹g pe³nej nazwy
	 * @throws SQLException
	 *             w przypadku problemu z baz¹ danych
	 */
	public static boolean login(String login, String password) throws SQLException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		int blocked;
		String DRIVER = "oracle.jdbc.driver.OracleDriver";
		String URL = "jdbc:oracle:thin:@localhost:1521:xe";
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		String QUERY1 = "SELECT BLOCKED FROM USERS WHERE LOGIN = ? AND PASSWORD = ?";
		String QUERY2 = "UPDATE USERS" + " SET LOGGED_IN = ?," + " LAST_LOGIN = ?," + " FAILED = ?"
				+ " WHERE LOGIN = ? AND PASSWORD = ?";
		String QUERY3 = "SELECT FAILED FROM USERS WHERE LOGIN = ?";
		String QUERY4 = "UPDATE USERS" + " SET FAILED = ?" + " WHERE LOGIN = ?";
		String QUERY5 = "UPDATE USERS" + " SET BLOCKED = ?," + " FAILED = ?" + " WHERE LOGIN = ?";
		String USER = "SYSTEM";
		String PASSWORD = "12345";
		try {
			Class.forName(DRIVER);
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			statement = connection.prepareStatement(QUERY1);
			statement.setString(1, login);
			statement.setString(2, password);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				if (resultSet.getInt("BLOCKED") == 0) {
					statement.close();
					statement = connection.prepareStatement(QUERY2);
					statement.setInt(1, 1);
					statement.setInt(3, 0);
					statement.setTimestamp(2, timestamp);
					statement.setString(4, login);
					statement.setString(5, password);
					statement.executeUpdate();
				} else {
					return false;
				}
			} else {
				statement.close();
				resultSet.close();
				statement = connection.prepareStatement(QUERY3);
				statement.setString(1, login);
				resultSet = statement.executeQuery();
				if (resultSet.next()) {
					blocked = resultSet.getInt("FAILED");
					blocked += 1;
					if (blocked < 5) {
						statement.close();
						statement = connection.prepareStatement(QUERY4);
						statement.setInt(1, blocked);
						statement.setString(2, login);
						statement.executeUpdate();
					} else {
						blocked = 0;
						statement.close();
						statement = connection.prepareStatement(QUERY5);
						statement.setInt(1, 1);
						statement.setInt(2, 0);
						statement.setString(3, login);
						statement.executeUpdate();
						statement.close();
						Thread.sleep(60000);
						statement = connection.prepareStatement(QUERY5);
						statement.setInt(1, 0);
						statement.setInt(2, 0);
						statement.setString(3, login);
						statement.executeUpdate();
						statement.close();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (resultSet != null)
				resultSet.close();
			if (statement != null)
				statement.close();
			if (connection != null)
				connection.close();
		}
		return true;
	}

	public static String register(String name, String surname, String login, String password) throws SQLException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String DRIVER = "oracle.jdbc.driver.OracleDriver";
		String URL = "jdbc:oracle:thin:@localhost:1521:xe";
		String USER = "SYSTEM";
		String PASSWORD = "12345";
		String QUERY1 = "SELECT NAME FROM USERS WHERE LOGIN = ?";
		String QUERY2 = "INSERT INTO USERS(NAME, SURNAME, LOGIN, PASSWORD) VALUES(?, ?, ?, ?)";
		try {
			Class.forName(DRIVER);
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			statement = connection.prepareStatement(QUERY1);
			statement.setString(1, login);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				return "Login already taken";
			} else {
				statement.close();
				resultSet.close();
				statement = connection.prepareStatement(QUERY2);
				statement.setString(1, name);
				statement.setString(2, surname);
				statement.setString(3, login);
				statement.setString(4, password);
				statement.execute();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (resultSet != null)
				resultSet.close();
			if (statement != null)
				statement.close();
			if (connection != null)
				connection.close();
		}
		return "Registration succesfull";
	}

	public static String loggedin(String login) throws SQLException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String DRIVER = "oracle.jdbc.driver.OracleDriver";
		String URL = "jdbc:oracle:thin:@localhost:1521:xe";
		String USER = "SYSTEM";
		String PASSWORD = "12345";
		String QUERY1 = "SELECT * FROM USERS WHERE LOGIN = ?";
		String res = "";
		try {
			Class.forName(DRIVER);
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			statement = connection.prepareStatement(QUERY1);
			statement.setString(1, login);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				res += "Name - " + resultSet.getString("NAME") + "<br>"
						+ "Surname - " + resultSet.getString("SURNAME") + "<br>"
						+ "Login - "+ resultSet.getString("LOGIN") + "<br>"
						+ "Password - "+ resultSet.getString("PASSWORD") + "<br>"
						+ "Last_login - "+ resultSet.getTimestamp("LAST_LOGIN") + "<br>"
						+ "Last_logout - "+ resultSet.getTimestamp("LAST_LOGUT") + "<br>";
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (resultSet != null)
				resultSet.close();
			if (statement != null)
				statement.close();
			if (connection != null)
				connection.close();
		}
		return res;
	}
	
	public static void logout(String login) throws SQLException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String DRIVER = "oracle.jdbc.driver.OracleDriver";
		String URL = "jdbc:oracle:thin:@localhost:1521:xe";
		String USER = "SYSTEM";
		String PASSWORD = "12345";
		String QUERY1 = "UPDATE USERS" + " SET LOGGED_IN = ?," + " LAST_LOGUT = ?"
				+ " WHERE LOGIN = ?";
		try {
			Class.forName(DRIVER);
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			statement = connection.prepareStatement(QUERY1);
			statement.setInt(1, 0);
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			statement.setTimestamp(2, timestamp);
			statement.setString(3, login);
			statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (resultSet != null)
				resultSet.close();
			if (statement != null)
				statement.close();
			if (connection != null)
				connection.close();
		}
		return;
	}
}

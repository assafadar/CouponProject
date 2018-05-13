package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import enums.ErrorType;
import exceptions.ApplicationException;

public class JdbcUtils {

	// Connection String
	public static Connection getConnection() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		
		Connection	connection = (Connection) DriverManager.getConnection("jdbc:mysql://xq7t6tasopo9xxbs.cbetxkdyhwsb.us-east-1.rds.amazonaws.com:3306/shny3suigth3yfcv?useSSL=false&serverTimezone=UTC","alhe0cmwslel8wdl","be3vz7hz02tng2fm");

		return connection;

	}

	// Close connection
	private static void closeConnection(Connection connection) throws ApplicationException{
		if (connection!=null){
			try{

				connection.close();

			}catch (Exception e){

				throw new ApplicationException("Couldn't close connection", ErrorType.CONNECTION_ERROR,e);
			}
		}

	}

	// Close prepared Satatement 
	private static void closePreparedStatement(PreparedStatement preparedStatement) throws ApplicationException{
		if(preparedStatement!=null){
			try{

				preparedStatement.close();

			}catch (Exception e){

				throw new ApplicationException("Couldn't close PreparedStatement", ErrorType.CONNECTION_ERROR,e);
			}
		}
	}

	// Close result set
	private static void closeResultSet(ResultSet resultSet)throws ApplicationException{
		if (resultSet!=null){
			try {

				resultSet.close();

			} catch (Exception e) {

				throw new ApplicationException("Clodn't close ResultSet", ErrorType.CONNECTION_ERROR,e);
			}
		}
	}

	// Close all open resources
	public static void closeResources(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) throws ApplicationException{

		closeResultSet(resultSet);
		closePreparedStatement(preparedStatement);
		closeConnection(connection);
	}

	// Close all open resouces - option 2
	public static void closeResources(Connection connection, PreparedStatement preparedStatement) throws ApplicationException{

		closePreparedStatement(preparedStatement);
		closeConnection(connection);
	}
}

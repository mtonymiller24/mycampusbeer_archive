package factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Campus;
import bean.Store;

public class SQLTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{ 
		Connection con = null;
		Campus campus = new Campus();
		campus.setStateId("IL");
		try
		{
			Class.forName("org.postgresql.Driver");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return;
		}
		//
		try
		{
			String SQL = "SELECT * FROM campus WHERE state_id='" + campus.getStateId() + "'";
			//
			List list = null;
			Campus resultObject = null;
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/mycampusbeer", "postgres", "OwsUHmk8");
			PreparedStatement statement = null;
			ResultSet resultSet = null;
			try
			{
				statement = con.prepareStatement(SQL);
				statement.execute();
				resultSet = statement.getResultSet();
				while (resultSet.next())
				{
					if (list == null)
					{
						list = new java.util.ArrayList();
					}
					resultObject = new Campus();
					System.out.println(resultSet.getInt("id"));
					System.out.println(resultSet.getString("state_id"));
					System.out.println(resultSet.getString("state"));
					System.out.println(resultSet.getString("name"));
					System.out.println(resultSet.getString("city"));
					System.out.println(resultSet.getInt("zip"));
					System.out.println("=======================");
				}
			}
			catch (SQLException e)
			{
				System.out.println(e);
			}
		}
		catch(Exception e)
		{
			System.out.println("FUCK: " + e);
		}
		finally
		{
			try
			{
				con.close();
			}
			catch (Exception e)
			{}
		}
	}
}



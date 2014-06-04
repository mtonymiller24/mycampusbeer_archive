package factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import bean.Brand;
import bean.Campus;
import bean.Item;
import bean.MyCampusEnvironment;
import bean.Product;
import bean.Search;
import bean.Store;
import bean.Submitter;

public class SQLFactory 
{
	private String database;
	private String dbuser;
	private String dbpassword;
		
	public SQLFactory()
	{
		try
		{
			Class.forName("org.postgresql.Driver");
			MyCampusEnvironment myCampusEnvironment = new MyCampusEnvironment();
			this.database = myCampusEnvironment.getDatabase();
			this.dbuser = myCampusEnvironment.getDatabaseUser();
			this.dbpassword = myCampusEnvironment.getDatabasePassword();
		}
		catch (Exception e)
		{
			e.printStackTrace(System.out);
		}
	}

	public Connection getConnection()
	{
		try
		{
			return DriverManager.getConnection(database, dbuser, dbpassword);
		}
		catch (Exception e)
		{
			e.printStackTrace(System.out);
			return null;
		}
	}
	
	public Item getCheapestBeer()
	{
		String SQL = "SELECT *,to_char(edited_timestamp, 'MM/DD/YYYY') AS edited_timestamp_str, to_char(special_start, 'MM/DD/YYYY') AS special_start_str, to_char(special_stop, 'MM/DD/YYYY') AS special_stop_str, to_char(now(), 'MM/DD/YYYY') as current_timestamp FROM item";
			SQL += "WHERE (quantity=24 OR quantity=30) AND container='cans' AND product_id IN (SELECT id FROM product WHERE is_popular=true) ORDER BY price ASC LIMIT 1";
		//
		Item resultObject = null;
		Connection con = this.getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try
		{
			statement = con.prepareStatement(SQL);
			statement.execute();
			resultSet = statement.getResultSet();
			if (resultSet.next())
			{
				resultObject = new Item();
				resultObject.setId(resultSet.getInt("id"));
				resultObject.setStoreId(resultSet.getInt("store_id"));
				resultObject.setCampusId(resultSet.getInt("campus_id"));
				resultObject.setProductId(resultSet.getInt("product_id"));
				resultObject.setPrice(resultSet.getInt("price"));
				resultObject.setQuantity(resultSet.getInt("quantity"));
				resultObject.setPriceDisplay("$"+((double)resultSet.getInt("price"))/100);
				resultObject.setName(resultSet.getString("name"));
				resultObject.setBrand(resultSet.getString("brand"));
				resultObject.setType(resultSet.getString("type"));
				resultObject.setContainer(resultSet.getString("container"));
				resultObject.setEditedTimestamp(resultSet.getString("edited_timestamp_str"));
				resultObject.setFlagged(resultSet.getBoolean("is_flagged"));
				resultObject.setPopular(resultSet.getBoolean("is_popular"));
				resultObject.setSpecial(resultSet.getBoolean("is_special"));
				resultObject.setSpecialStart(resultSet.getString("special_start_str"));
				resultObject.setSpecialStop(resultSet.getString("special_stop_str"));
				resultObject.setSpecialPrice(resultSet.getInt("special_price"));
				resultObject.setSpecialPriceDisplay("$"+((double)resultSet.getInt("special_price"))/100);
				resultObject.setSubmitterId(resultSet.getString("submitter_id"));
				resultObject.setStoreName(resultSet.getString("store_name"));
				resultObject.setStaticPrice(resultSet.getBoolean("is_static_price"));
				//checkDates(resultObject, resultSet);
			}
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
		finally
		{
			try
			{
				if (resultSet != null)
				{
					resultSet.close();
				}
			}
			catch (Exception e)
			{
			}
			try
			{
				if (statement != null)
				{
					statement.close();
				}
			}
			catch (Exception e)
			{
			}
			try
			{
				if (con != null)
				{
					con.close();
				}
			}
			catch (Exception e)
			{
			}
		}
		return resultObject;
	}
	
	public List getItemsAdvancedSearch(Search search)
	{
		String SQL = "SELECT *,to_char(edited_timestamp, 'MM/DD/YYYY') AS edited_timestamp_str, to_char(special_start, 'MM/DD/YYYY') AS special_start_str, to_char(special_stop, 'MM/DD/YYYY') AS special_stop_str, to_char(now(), 'MM/DD/YYYY') as current_timestamp FROM item WHERE campus_id=" + search.getCampusId();
		if(search.getBrandId() > 0)
		{
			SQL += " AND brand_id=" + search.getBrandId();
		}
		if(search.getProductId() > 0)
		{
			SQL += " AND product_id=" + search.getProductId();
		}
		if(search.getStoreId() > 0)
		{
			SQL += " AND store_id=" + search.getStoreId();
		}
		if(search.isSpecialOnly())
		{
			SQL += " AND is_special=true";
		}
		if(search.getMinPrice() > 0)
		{
			SQL += " AND price>=" + search.getMinPrice();
		}
		if(search.getMaxPrice() > 0)
		{
			SQL += " AND price<=" + search.getMaxPrice();
		}
		//
		ArrayList<String> containers = search.getContainerStrings();
		if(containers != null && containers.size() > 0)
		{
			SQL += " AND (";
			for(Iterator iter = containers.iterator(); iter.hasNext();)
			{
				SQL += "container='" + (String)iter.next() + "'";
				if(iter.hasNext())
				{
					SQL += " OR ";
				}
			}
			SQL += ")";
		}
		//
		ArrayList<String> quantity = search.getQuantityStrings();
		if(quantity != null && quantity.size() > 0)
		{
			SQL += " AND (";
			for(Iterator iter = quantity.iterator(); iter.hasNext();)
			{
				SQL += "quantity=" + (String)iter.next();
				if(iter.hasNext())
				{
					SQL += " OR ";
				}
			}
			SQL += ")";
		}
		//
		ArrayList<String> types = search.getTypeStrings();
		if(types != null && types.size() > 0)
		{
			SQL += " AND (";
			for(Iterator iter = types.iterator(); iter.hasNext();)
			{
				SQL += "type='" + (String)iter.next() + "'";
				if(iter.hasNext())
				{
					SQL += " OR ";
				}
			}
			SQL += ")";
		}
		SQL += " ORDER BY name ASC";
		//
		List list = null;
		Item resultObject = null;
		Connection con = this.getConnection();
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
				resultObject = new Item();
				resultObject.setId(resultSet.getInt("id"));
				resultObject.setStoreId(resultSet.getInt("store_id"));
				resultObject.setCampusId(resultSet.getInt("campus_id"));
				resultObject.setProductId(resultSet.getInt("product_id"));
				resultObject.setPrice(resultSet.getInt("price"));
				resultObject.setQuantity(resultSet.getInt("quantity"));
				resultObject.setPriceDisplay("$"+((double)resultSet.getInt("price"))/100);
				resultObject.setName(resultSet.getString("name"));
				resultObject.setBrand(resultSet.getString("brand"));
				resultObject.setType(resultSet.getString("type"));
				resultObject.setContainer(resultSet.getString("container"));
				resultObject.setEditedTimestamp(resultSet.getString("edited_timestamp_str"));
				resultObject.setFlagged(resultSet.getBoolean("is_flagged"));
				resultObject.setPopular(resultSet.getBoolean("is_popular"));
				resultObject.setSpecial(resultSet.getBoolean("is_special"));
				resultObject.setSpecialStart(resultSet.getString("special_start_str"));
				resultObject.setSpecialStop(resultSet.getString("special_stop_str"));
				resultObject.setSpecialPrice(resultSet.getInt("special_price"));
				resultObject.setSpecialPriceDisplay("$"+((double)resultSet.getInt("special_price"))/100);
				resultObject.setSubmitterId(resultSet.getString("submitter_id"));
				resultObject.setStoreName(resultSet.getString("store_name"));
				resultObject.setStaticPrice(resultSet.getBoolean("is_static_price"));
				//checkDates(resultObject, resultSet);
				list.add(resultObject);
			}
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
		finally
		{
			try
			{
				if (resultSet != null)
				{
					resultSet.close();
				}
			}
			catch (Exception e)
			{
			}
			try
			{
				if (statement != null)
				{
					statement.close();
				}
			}
			catch (Exception e)
			{
			}
			try
			{
				if (con != null)
				{
					con.close();
				}
			}
			catch (Exception e)
			{
			}
		}
		return list;
	}
	
	
	
	public List getItemsByPrice(Campus campus)
	{
		List list = new ArrayList();
		List products = this.getProducts();
		//
		Item resultObject = null;
		Connection con = this.getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try
		{
			for(Iterator iter = products.iterator(); iter.hasNext();)
			{
				Product product = (Product)iter.next();
				String SQL = "SELECT *,to_char(edited_timestamp, 'MM/DD/YYYY') AS edited_timestamp_str, to_char(special_start, 'MM/DD/YYYY') AS special_start_str, to_char(special_stop, 'MM/DD/YYYY') AS special_stop_str, to_char(now(), 'MM/DD/YYYY') as current_timestamp FROM item WHERE campus_id=" + campus.getId() + " AND product_id=" + product.getId() + " AND (quantity=24 OR quantity=30) AND container='cans' ORDER BY price ASC LIMIT 1";
				//
				statement = con.prepareStatement(SQL);
				statement.execute();
				resultSet = statement.getResultSet();
				if (resultSet.next())
				{
					resultObject = new Item();
					resultObject.setId(resultSet.getInt("id"));
					resultObject.setStoreId(resultSet.getInt("store_id"));
					resultObject.setCampusId(resultSet.getInt("campus_id"));
					resultObject.setProductId(resultSet.getInt("product_id"));
					resultObject.setPrice(resultSet.getInt("price"));
					resultObject.setQuantity(resultSet.getInt("quantity"));
					resultObject.setPriceDisplay("$"+((double)resultSet.getInt("price"))/100);
					resultObject.setName(resultSet.getString("name"));
					resultObject.setBrand(resultSet.getString("brand"));
					resultObject.setType(resultSet.getString("type"));
					resultObject.setContainer(resultSet.getString("container"));
					resultObject.setEditedTimestamp(resultSet.getString("edited_timestamp_str"));
					resultObject.setFlagged(resultSet.getBoolean("is_flagged"));
					resultObject.setPopular(resultSet.getBoolean("is_popular"));
					resultObject.setSpecial(resultSet.getBoolean("is_special"));
					resultObject.setSpecialStart(resultSet.getString("special_start_str"));
					resultObject.setSpecialStop(resultSet.getString("special_stop_str"));
					resultObject.setSpecialPrice(resultSet.getInt("special_price"));
					resultObject.setSpecialPriceDisplay("$"+((double)resultSet.getInt("special_price"))/100);
					resultObject.setSubmitterId(resultSet.getString("submitter_id"));
					resultObject.setStoreName(resultSet.getString("store_name"));
					resultObject.setStaticPrice(resultSet.getBoolean("is_static_price"));
					//checkDates(resultObject, resultSet);
					list.add(resultObject);
				}
			}
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
		finally
		{
			try
			{
				if (resultSet != null)
				{
					resultSet.close();
				}
			}
			catch (Exception e)
			{
			}
			try
			{
				if (statement != null)
				{
					statement.close();
				}
			}
			catch (Exception e)
			{
			}
			try
			{
				if (con != null)
				{
					con.close();
				}
			}
			catch (Exception e)
			{
			}
		}
		return list;
	}

	public List getCheapestBeers(Campus campus)
	{
		List list = new ArrayList();
		List popularProduct = getPopularProducts();
		//
		Item resultObject = null;
		Connection con = this.getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try
		{
			for(Iterator iter = popularProduct.iterator(); iter.hasNext();)
			{
				Product product = (Product)iter.next();
				String SQL = "SELECT *,to_char(edited_timestamp, 'MM/DD/YYYY') AS edited_timestamp_str, to_char(special_start, 'MM/DD/YYYY') AS special_start_str, to_char(special_stop, 'MM/DD/YYYY') AS special_stop_str, to_char(now(), 'MM/DD/YYYY') as current_timestamp FROM item WHERE campus_id=" + campus.getId() + " AND (quantity=24 OR quantity=30) AND container='cans' AND is_flagged=false AND name='" + product.getName() + "' ORDER BY price ASC LIMIT 1";
				//
				statement = con.prepareStatement(SQL);
				statement.execute();
				resultSet = statement.getResultSet();
				if (resultSet.next())
				{
					resultObject = new Item();
					resultObject.setId(resultSet.getInt("id"));
					resultObject.setStoreId(resultSet.getInt("store_id"));
					resultObject.setCampusId(resultSet.getInt("campus_id"));
					resultObject.setProductId(resultSet.getInt("product_id"));
					resultObject.setPrice(resultSet.getInt("price"));
					resultObject.setQuantity(resultSet.getInt("quantity"));
					resultObject.setPriceDisplay("$"+((double)resultSet.getInt("price"))/100);
					resultObject.setName(resultSet.getString("name"));
					resultObject.setBrand(resultSet.getString("brand"));
					resultObject.setType(resultSet.getString("type"));
					resultObject.setContainer(resultSet.getString("container"));
					resultObject.setEditedTimestamp(resultSet.getString("edited_timestamp_str"));
					resultObject.setFlagged(resultSet.getBoolean("is_flagged"));
					resultObject.setPopular(resultSet.getBoolean("is_popular"));
					resultObject.setSpecial(resultSet.getBoolean("is_special"));
					resultObject.setSpecialStart(resultSet.getString("special_start_str"));
					resultObject.setSpecialStop(resultSet.getString("special_stop_str"));
					resultObject.setSpecialPrice(resultSet.getInt("special_price"));
					resultObject.setSpecialPriceDisplay("$"+((double)resultSet.getInt("special_price"))/100);
					resultObject.setSubmitterId(resultSet.getString("submitter_id"));
					resultObject.setStoreName(resultSet.getString("store_name"));
					resultObject.setStaticPrice(resultSet.getBoolean("is_static_price"));
					//checkDates(resultObject, resultSet);
					list.add(resultObject);
				}
			}
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
		finally
		{
			try
			{
				if (resultSet != null)
				{
					resultSet.close();
				}
			}
			catch (Exception e)
			{
			}
			try
			{
				if (statement != null)
				{
					statement.close();
				}
			}
			catch (Exception e)
			{
			}
			try
			{
				if (con != null)
				{
					con.close();
				}
			}
			catch (Exception e)
			{
			}
		}
		return list;
	}
		
	public List getItemsByStore(Store store)
	{
		String SQL = "SELECT *,to_char(edited_timestamp, 'MM/DD/YYYY') AS edited_timestamp_str, to_char(special_start, 'MM/DD/YYYY') AS special_start_str, to_char(special_stop, 'MM/DD/YYYY') AS special_stop_str, to_char(now(), 'MM/DD/YYYY') as current_timestamp FROM item WHERE store_id=" + store.getId() + " ORDER BY name DESC";
		//
		List list = null;
		Item resultObject = null;
		Connection con = this.getConnection();
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
				resultObject = new Item();
				resultObject.setId(resultSet.getInt("id"));
				resultObject.setStoreId(resultSet.getInt("store_id"));
				resultObject.setCampusId(resultSet.getInt("campus_id"));
				resultObject.setProductId(resultSet.getInt("product_id"));
				resultObject.setPrice(resultSet.getInt("price"));
				resultObject.setQuantity(resultSet.getInt("quantity"));
				resultObject.setPriceDisplay("$"+((double)resultSet.getInt("price"))/100);
				resultObject.setName(resultSet.getString("name"));
				resultObject.setBrand(resultSet.getString("brand"));
				resultObject.setType(resultSet.getString("type"));
				resultObject.setContainer(resultSet.getString("container"));
				resultObject.setEditedTimestamp(resultSet.getString("edited_timestamp_str"));
				resultObject.setFlagged(resultSet.getBoolean("is_flagged"));
				resultObject.setPopular(resultSet.getBoolean("is_popular"));
				resultObject.setSpecial(resultSet.getBoolean("is_special"));
				resultObject.setSpecialStart(resultSet.getString("special_start_str"));
				resultObject.setSpecialStop(resultSet.getString("special_stop_str"));
				resultObject.setSpecialPrice(resultSet.getInt("special_price"));
				resultObject.setSpecialPriceDisplay("$"+((double)resultSet.getInt("special_price"))/100);
				resultObject.setSubmitterId(resultSet.getString("submitter_id"));
				resultObject.setStoreName(resultSet.getString("store_name"));
				resultObject.setStaticPrice(resultSet.getBoolean("is_static_price"));
				//checkDates(resultObject, resultSet);
				list.add(resultObject);
			}
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
		finally
		{
			try
			{
				if (resultSet != null)
				{
					resultSet.close();
				}
			}
			catch (Exception e)
			{
			}
			try
			{
				if (statement != null)
				{
					statement.close();
				}
			}
			catch (Exception e)
			{
			}
			try
			{
				if (con != null)
				{
					con.close();
				}
			}
			catch (Exception e)
			{
			}
		}
		return list;
	}
	
	public List getItemsByProduct(Item item)
	{
		String SQL = "SELECT *,to_char(edited_timestamp, 'MM/DD/YYYY') AS edited_timestamp_str, to_char(special_start, 'MM/DD/YYYY') AS special_start_str, to_char(special_stop, 'MM/DD/YYYY') AS special_stop_str, to_char(now(), 'MM/DD/YYYY') as current_timestamp FROM item WHERE type='" + item.getType() + "' AND campus_id=" + item.getCampusId() + " ORDER BY price ASC";
		//
		List list = null;
		Item resultObject = null;
		Connection con = this.getConnection();
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
				resultObject = new Item();
				resultObject.setId(resultSet.getInt("id"));
				resultObject.setStoreId(resultSet.getInt("store_id"));
				resultObject.setCampusId(resultSet.getInt("campus_id"));
				resultObject.setProductId(resultSet.getInt("product_id"));
				resultObject.setPrice(resultSet.getInt("price"));
				resultObject.setQuantity(resultSet.getInt("quantity"));
				resultObject.setPriceDisplay("$"+((double)resultSet.getInt("price"))/100);
				resultObject.setName(resultSet.getString("name"));
				resultObject.setBrand(resultSet.getString("brand"));
				resultObject.setType(resultSet.getString("type"));
				resultObject.setContainer(resultSet.getString("container"));
				resultObject.setEditedTimestamp(resultSet.getString("edited_timestamp_str"));
				resultObject.setFlagged(resultSet.getBoolean("is_flagged"));
				resultObject.setPopular(resultSet.getBoolean("is_popular"));
				resultObject.setSpecial(resultSet.getBoolean("is_special"));
				resultObject.setSpecialStart(resultSet.getString("special_start_str"));
				resultObject.setSpecialStop(resultSet.getString("special_stop_str"));
				resultObject.setSpecialPrice(resultSet.getInt("special_price"));
				resultObject.setSpecialPriceDisplay("$"+((double)resultSet.getInt("special_price"))/100);
				resultObject.setSubmitterId(resultSet.getString("submitter_id"));
				resultObject.setStoreName(resultSet.getString("store_name"));
				resultObject.setStaticPrice(resultSet.getBoolean("is_static_price"));
				//checkDates(resultObject, resultSet);
				list.add(resultObject);
			}
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
		finally
		{
			try
			{
				if (resultSet != null)
				{
					resultSet.close();
				}
			}
			catch (Exception e)
			{
			}
			try
			{
				if (statement != null)
				{
					statement.close();
				}
			}
			catch (Exception e)
			{
			}
			try
			{
				if (con != null)
				{
					con.close();
				}
			}
			catch (Exception e)
			{
			}
		}
		return list;
	}
	
	public List getItemsByBrand(Item item)
	{
		String SQL = "SELECT *,to_char(edited_timestamp, 'MM/DD/YYYY') AS edited_timestamp_str, to_char(special_start, 'MM/DD/YYYY') AS special_start_str, to_char(special_stop, 'MM/DD/YYYY') AS special_stop_str, to_char(now(), 'MM/DD/YYYY') as current_timestamp FROM item WHERE brand='" + item.getBrand() + "' AND campus_id=" + item.getCampusId() + " ORDER BY name, price";
		//
		List list = null;
		Item resultObject = null;
		Connection con = this.getConnection();
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
				resultObject = new Item();
				resultObject.setId(resultSet.getInt("id"));
				resultObject.setStoreId(resultSet.getInt("store_id"));
				resultObject.setCampusId(resultSet.getInt("campus_id"));
				resultObject.setProductId(resultSet.getInt("product_id"));
				resultObject.setPrice(resultSet.getInt("price"));
				resultObject.setQuantity(resultSet.getInt("quantity"));
				resultObject.setPriceDisplay("$"+((double)resultSet.getInt("price"))/100);
				resultObject.setName(resultSet.getString("name"));
				resultObject.setBrand(resultSet.getString("brand"));
				resultObject.setType(resultSet.getString("type"));
				resultObject.setContainer(resultSet.getString("container"));
				resultObject.setEditedTimestamp(resultSet.getString("edited_timestamp_str"));
				resultObject.setFlagged(resultSet.getBoolean("is_flagged"));
				resultObject.setPopular(resultSet.getBoolean("is_popular"));
				resultObject.setSpecial(resultSet.getBoolean("is_special"));
				resultObject.setSpecialStart(resultSet.getString("special_start_str"));
				resultObject.setSpecialStop(resultSet.getString("special_stop_str"));
				resultObject.setSpecialPrice(resultSet.getInt("special_price"));
				resultObject.setSpecialPriceDisplay("$"+((double)resultSet.getInt("special_price"))/100);
				resultObject.setSubmitterId(resultSet.getString("submitter_id"));
				resultObject.setStoreName(resultSet.getString("store_name"));
				resultObject.setStaticPrice(resultSet.getBoolean("is_static_price"));
				//checkDates(resultObject, resultSet);
				list.add(resultObject);
			}
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
		finally
		{
			try
			{
				if (resultSet != null)
				{
					resultSet.close();
				}
			}
			catch (Exception e)
			{
			}
			try
			{
				if (statement != null)
				{
					statement.close();
				}
			}
			catch (Exception e)
			{
			}
			try
			{
				if (con != null)
				{
					con.close();
				}
			}
			catch (Exception e)
			{
			}
		}
		return list;
	}
	
	public List getSpecialsForCampus(Campus campus)
	{
		String SQL = "SELECT *,to_char(edited_timestamp, 'MM/DD/YYYY') AS edited_timestamp_str, to_char(special_start, 'MM/DD/YYYY') AS special_start_str, to_char(special_stop, 'MM/DD/YYYY') AS special_stop_str, to_char(now(), 'MM/DD/YYYY') as current_timestamp FROM item WHERE is_special=true AND special_start<now() AND special_stop>now() AND campus_id=" + campus.getId() + " ORDER BY name";
		//
		List list = null;
		Item resultObject = null;
		Connection con = this.getConnection();
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
				resultObject = new Item();
				resultObject.setId(resultSet.getInt("id"));
				resultObject.setStoreId(resultSet.getInt("store_id"));
				resultObject.setCampusId(resultSet.getInt("campus_id"));
				resultObject.setProductId(resultSet.getInt("product_id"));
				resultObject.setPrice(resultSet.getInt("price"));
				resultObject.setQuantity(resultSet.getInt("quantity"));
				resultObject.setPriceDisplay("$"+((double)resultSet.getInt("price"))/100);
				resultObject.setName(resultSet.getString("name"));
				resultObject.setBrand(resultSet.getString("brand"));
				resultObject.setType(resultSet.getString("type"));
				resultObject.setContainer(resultSet.getString("container"));
				resultObject.setEditedTimestamp(resultSet.getString("edited_timestamp_str"));
				resultObject.setFlagged(resultSet.getBoolean("is_flagged"));
				resultObject.setPopular(resultSet.getBoolean("is_popular"));
				resultObject.setSpecial(resultSet.getBoolean("is_special"));
				resultObject.setSpecialStart(resultSet.getString("special_start_str"));
				resultObject.setSpecialStop(resultSet.getString("special_stop_str"));
				resultObject.setSpecialPrice(resultSet.getInt("special_price"));
				resultObject.setSpecialPriceDisplay("$"+((double)resultSet.getInt("special_price"))/100);
				resultObject.setSubmitterId(resultSet.getString("submitter_id"));
				resultObject.setStoreName(resultSet.getString("store_name"));
				resultObject.setStaticPrice(resultSet.getBoolean("is_static_price"));
				//checkDates(resultObject, resultSet);
				list.add(resultObject);
			}
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
		finally
		{
			try
			{
				if (resultSet != null)
				{
					resultSet.close();
				}
			}
			catch (Exception e)
			{
			}
			try
			{
				if (statement != null)
				{
					statement.close();
				}
			}
			catch (Exception e)
			{
			}
			try
			{
				if (con != null)
				{
					con.close();
				}
			}
			catch (Exception e)
			{
			}
		}
		return list;
	}
	
	public List getSpecialsForStore(Store store)
	{
		String SQL = "SELECT *,to_char(edited_timestamp, 'MM/DD/YYYY') AS edited_timestamp, to_char(special_start, 'MM/DD/YYYY') AS special_start_str, to_char(special_stop, 'MM/DD/YYYY') AS special_stop_str, to_char(now(), 'MM/DD/YYYY') as current_timestamp FROM item WHERE is_special=true AND special_start<now() AND special_stop>now() AND store_id=" + store.getId() + " ORDER BY name";
		//
		List list = null;
		Item resultObject = null;
		Connection con = this.getConnection();
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
				resultObject = new Item();
				resultObject.setId(resultSet.getInt("id"));
				resultObject.setStoreId(resultSet.getInt("store_id"));
				resultObject.setCampusId(resultSet.getInt("campus_id"));
				resultObject.setProductId(resultSet.getInt("product_id"));
				resultObject.setPrice(resultSet.getInt("price"));
				resultObject.setQuantity(resultSet.getInt("quantity"));
				resultObject.setPriceDisplay("$"+((double)resultSet.getInt("price"))/100);
				resultObject.setName(resultSet.getString("name"));
				resultObject.setBrand(resultSet.getString("brand"));
				resultObject.setType(resultSet.getString("type"));
				resultObject.setContainer(resultSet.getString("container"));
				resultObject.setEditedTimestamp(resultSet.getString("edited_timestamp_str"));
				resultObject.setFlagged(resultSet.getBoolean("is_flagged"));
				resultObject.setPopular(resultSet.getBoolean("is_popular"));
				resultObject.setSpecial(resultSet.getBoolean("is_special"));
				resultObject.setSpecialStart(resultSet.getString("special_start_str"));
				resultObject.setSpecialStop(resultSet.getString("special_stop_str"));
				resultObject.setSpecialPrice(resultSet.getInt("special_price"));
				resultObject.setSpecialPriceDisplay("$"+((double)resultSet.getInt("special_price"))/100);
				resultObject.setSubmitterId(resultSet.getString("submitter_id"));
				resultObject.setStoreName(resultSet.getString("store_name"));
				resultObject.setStaticPrice(resultSet.getBoolean("is_static_price"));
				//checkDates(resultObject, resultSet);
				list.add(resultObject);
			}
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
		finally
		{
			try
			{
				if (resultSet != null)
				{
					resultSet.close();
				}
			}
			catch (Exception e)
			{
			}
			try
			{
				if (statement != null)
				{
					statement.close();
				}
			}
			catch (Exception e)
			{
			}
			try
			{
				if (con != null)
				{
					con.close();
				}
			}
			catch (Exception e)
			{
			}
		}
		return list;
	}
	
	public List searchItemsByString(Search search)
	{
		String SQL = "SELECT *,to_char(edited_timestamp, 'MM/DD/YYYY') AS edited_timestamp, to_char(special_start, 'MM/DD/YYYY') AS special_start_str, to_char(special_stop, 'MM/DD/YYYY') AS special_stop_str, to_char(now(), 'MM/DD/YYYY') as current_timestamp FROM item WHERE (";
		for (Iterator iter = search.getSearchStrings().iterator(); iter.hasNext();)
		{
			SQL += "name LIKE % ||'" + (String) iter.next() + "'|| %";
			if (iter.hasNext())
			{
				SQL += " OR ";
			}
		}
		SQL += ") AND campus_id=" + search.getCampusId() + "  ORDER BY name";
		//
		List list = null;
		Item resultObject = null;
		Connection con = this.getConnection();
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
				resultObject = new Item();
				resultObject.setId(resultSet.getInt("id"));
				resultObject.setStoreId(resultSet.getInt("store_id"));
				resultObject.setCampusId(resultSet.getInt("campus_id"));
				resultObject.setProductId(resultSet.getInt("product_id"));
				resultObject.setPrice(resultSet.getInt("price"));
				resultObject.setQuantity(resultSet.getInt("quantity"));
				resultObject.setPriceDisplay("$"+((double)resultSet.getInt("price"))/100);
				resultObject.setName(resultSet.getString("name"));
				resultObject.setBrand(resultSet.getString("brand"));
				resultObject.setType(resultSet.getString("type"));
				resultObject.setContainer(resultSet.getString("container"));
				resultObject.setEditedTimestamp(resultSet.getString("edited_timestamp_str"));
				resultObject.setFlagged(resultSet.getBoolean("is_flagged"));
				resultObject.setPopular(resultSet.getBoolean("is_popular"));
				resultObject.setSpecial(resultSet.getBoolean("is_special"));
				resultObject.setSpecialStart(resultSet.getString("special_start_str"));
				resultObject.setSpecialStop(resultSet.getString("special_stop_str"));
				resultObject.setSpecialPrice(resultSet.getInt("special_price"));
				resultObject.setSpecialPriceDisplay("$"+((double)resultSet.getInt("special_price"))/100);
				resultObject.setSubmitterId(resultSet.getString("submitter_id"));
				resultObject.setStoreName(resultSet.getString("store_name"));
				resultObject.setStaticPrice(resultSet.getBoolean("is_static_price"));
				//checkDates(resultObject, resultSet);
				list.add(resultObject);
			}
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
		finally
		{
			try
			{
				if (resultSet != null)
				{
					resultSet.close();
				}
			}
			catch (Exception e)
			{
			}
			try
			{
				if (statement != null)
				{
					statement.close();
				}
			}
			catch (Exception e)
			{
			}
			try
			{
				if (con != null)
				{
					con.close();
				}
			}
			catch (Exception e)
			{
			}
		}
		return list;
	}
	
	public List searchStoreByString(Search search)
	{
		String SQL = "SELECT * FROM store WHERE ";
		for (Iterator iter = search.getSearchStrings().iterator(); iter.hasNext();)
		{
			SQL += "name LIKE % ||'" + (String) iter.next() + "'|| %";
			if (iter.hasNext())
			{
				SQL += " OR ";
			}
		}
		SQL += ") AND campus_id=" + search.getCampusId() + " ORDER BY name";
		//
		List list = null;
		Item resultObject = null;
		Connection con = this.getConnection();
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
				resultObject = new Item();
				resultObject.setId(resultSet.getInt("id"));
				resultObject.setStoreId(resultSet.getInt("store_id"));
				resultObject.setCampusId(resultSet.getInt("campus_id"));
				resultObject.setProductId(resultSet.getInt("product_id"));
				resultObject.setPrice(resultSet.getInt("price"));
				resultObject.setQuantity(resultSet.getInt("quantity"));
				resultObject.setPriceDisplay("$"+((double)resultSet.getInt("price"))/100);
				resultObject.setName(resultSet.getString("name"));
				resultObject.setBrand(resultSet.getString("brand"));
				resultObject.setType(resultSet.getString("type"));
				resultObject.setContainer(resultSet.getString("container"));
				resultObject.setEditedTimestamp(resultSet.getString("edited_timestamp_str"));
				resultObject.setFlagged(resultSet.getBoolean("is_flagged"));
				resultObject.setPopular(resultSet.getBoolean("is_popular"));
				resultObject.setSpecial(resultSet.getBoolean("is_special"));
				resultObject.setSpecialStart(resultSet.getString("special_start_str"));
				resultObject.setSpecialStop(resultSet.getString("special_stop_str"));
				resultObject.setSpecialPrice(resultSet.getInt("special_price"));
				resultObject.setSpecialPriceDisplay("$"+((double)resultSet.getInt("special_price"))/100);
				resultObject.setSubmitterId(resultSet.getString("submitter_id"));
				resultObject.setStoreName(resultSet.getString("store_name"));
				resultObject.setStaticPrice(resultSet.getBoolean("is_static_price"));
				//checkDates(resultObject, resultSet);
				list.add(resultObject);
			}
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
		finally
		{
			try
			{
				if (resultSet != null)
				{
					resultSet.close();
				}
			}
			catch (Exception e)
			{
			}
			try
			{
				if (statement != null)
				{
					statement.close();
				}
			}
			catch (Exception e)
			{
			}
			try
			{
				if (con != null)
				{
					con.close();
				}
			}
			catch (Exception e)
			{
			}
		}
		return list;
	}
	
	public List searchBrandByString(Search search)
	{
		String SQL = "SELECT * FROM brand WHERE ";
		for (Iterator iter = search.getSearchStrings().iterator(); iter.hasNext();)
		{
			SQL += "name LIKE % ||'" + (String) iter.next() + "'|| %";
			if (iter.hasNext())
			{
				SQL += " OR ";
			}
		}
		SQL += ") ORDER BY name";
		//
		List list = null;
		Brand resultObject = null;
		Connection con = this.getConnection();
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
				resultObject = new Brand();
				resultObject.setId(resultSet.getInt("id"));
				resultObject.setName(resultSet.getString("name"));
				resultObject.setType(resultSet.getString("type"));
				list.add(resultObject);
			}
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
		finally
		{
			try
			{
				if (resultSet != null)
				{
					resultSet.close();
				}
			}
			catch (Exception e)
			{
			}
			try
			{
				if (statement != null)
				{
					statement.close();
				}
			}
			catch (Exception e)
			{
			}
			try
			{
				if (con != null)
				{
					con.close();
				}
			}
			catch (Exception e)
			{
			}
		}
		return list;
	}
	
	public List searchProductByString(Search search)
	{
		String SQL = "SELECT * FROM product WHERE ";
		for (Iterator iter = search.getSearchStrings().iterator(); iter.hasNext();)
		{
			SQL += "name LIKE % ||'" + (String) iter.next() + "'|| %";
			if (iter.hasNext())
			{
				SQL += " OR ";
			}
		}
		SQL += ") ORDER BY name";
		//
		List list = null;
		Product resultObject = null;
		Connection con = this.getConnection();
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
				resultObject = new Product();
				resultObject.setId(resultSet.getInt("id"));
				resultObject.setBrandId(resultSet.getInt("brand_id"));
				resultObject.setName(resultSet.getString("name"));
				resultObject.setType(resultSet.getString("type"));
				resultObject.setPopular(resultSet.getBoolean("is_popular"));
				list.add(resultObject);
			}
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
		finally
		{
			try
			{
				if (resultSet != null)
				{
					resultSet.close();
				}
			}
			catch (Exception e)
			{
			}
			try
			{
				if (statement != null)
				{
					statement.close();
				}
			}
			catch (Exception e)
			{
			}
			try
			{
				if (con != null)
				{
					con.close();
				}
			}
			catch (Exception e)
			{
			}
		}
		return list;
	}
	
	//
	//
	//
	public List getCampusList(Campus campus)
	{
		String SQL = "SELECT * FROM campus WHERE state_id='" + campus.getStateId() + "'";
		//
		List list = null;
		Campus resultObject = null;
		Connection con = this.getConnection();
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
				resultObject.setId(resultSet.getInt("id"));
				resultObject.setStateId(resultSet.getString("state_id"));
				resultObject.setState(resultSet.getString("state"));
				resultObject.setName(resultSet.getString("name"));
				resultObject.setCity(resultSet.getString("city"));
				resultObject.setZip(resultSet.getInt("zip"));
				list.add(resultObject);
			}
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
		finally
		{
			try
			{
				if (resultSet != null)
				{
					resultSet.close();
				}
			}
			catch (Exception e)
			{
			}
			try
			{
				if (statement != null)
				{
					statement.close();
				}
			}
			catch (Exception e)
			{
			}
			try
			{
				if (con != null)
				{
					con.close();
				}
			}
			catch (Exception e)
			{
			}
		}
		return list;
	}
	
	public List getStoreList(Campus campus)
	{
		String SQL = "SELECT * FROM store WHERE campus_id=" + campus.getId();
		//
		List list = null;
		Store resultObject = null;
		Connection con = this.getConnection();
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
				resultObject = new Store();
				resultObject.setId(resultSet.getInt("id"));
				resultObject.setCampusId(resultSet.getInt("campus_id"));
				resultObject.setName(resultSet.getString("name"));
				resultObject.setAddress(resultSet.getString("address"));
				resultObject.setCity(resultSet.getString("city"));
				resultObject.setPhone(resultSet.getString("phone"));
				resultObject.setZip(resultSet.getInt("zip"));
				list.add(resultObject);
			}
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
		finally
		{
			try
			{
				if (resultSet != null)
				{
					resultSet.close();
				}
			}
			catch (Exception e)
			{
			}
			try
			{
				if (statement != null)
				{
					statement.close();
				}
			}
			catch (Exception e)
			{
			}
			try
			{
				if (con != null)
				{
					con.close();
				}
			}
			catch (Exception e)
			{
			}
		}
		return list;
	}
	
	//
	//	ADMIN
	//
	
	
	
	public List getItemsByStoreAdmin(Store store, List listBy)
	{
		String SQL = "SELECT *,to_char(edited_timestamp, 'MM/DD/YYYY') AS edited_timestamp, to_char(special_start, 'MM/DD/YYYY') AS special_start_str, to_char(special_stop, 'MM/DD/YYYY') AS special_stop_str, to_char(now(), 'MM/DD/YYYY') as current_timestamp FROM item WHERE store_id=" + store.getId() + " ";
		
		if(listBy != null && listBy.size() > 0)
		{
			SQL += "AND (";
			for(Iterator iter = listBy.iterator(); iter.hasNext();)
			{
				String type = (String)iter.next();
				SQL += "type='" + type + "'";
				if(iter.hasNext())
				{
					SQL += " OR ";
				}
			}
			SQL += ") ";
		}
		SQL += "ORDER BY type, name";
		//
		List list = null;
		Item resultObject = null;
		Connection con = this.getConnection();
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
				resultObject = new Item();
				resultObject.setId(resultSet.getInt("id"));
				resultObject.setStoreId(resultSet.getInt("store_id"));
				resultObject.setCampusId(resultSet.getInt("campus_id"));
				resultObject.setProductId(resultSet.getInt("product_id"));
				resultObject.setPrice(resultSet.getInt("price"));
				resultObject.setQuantity(resultSet.getInt("quantity"));
				resultObject.setPriceDisplay("$"+((double)resultSet.getInt("price"))/100);
				resultObject.setName(resultSet.getString("name"));
				resultObject.setBrand(resultSet.getString("brand"));
				resultObject.setType(resultSet.getString("type"));
				resultObject.setContainer(resultSet.getString("container"));
				resultObject.setEditedTimestamp(resultSet.getString("edited_timestamp_str"));
				resultObject.setFlagged(resultSet.getBoolean("is_flagged"));
				resultObject.setPopular(resultSet.getBoolean("is_popular"));
				resultObject.setSpecial(resultSet.getBoolean("is_special"));
				resultObject.setSpecialStart(resultSet.getString("special_start_str"));
				resultObject.setSpecialStop(resultSet.getString("special_stop_str"));
				resultObject.setSpecialPrice(resultSet.getInt("special_price"));
				resultObject.setSpecialPriceDisplay("$"+((double)resultSet.getInt("special_price"))/100);
				resultObject.setSubmitterId(resultSet.getString("submitter_id"));
				resultObject.setStoreName(resultSet.getString("store_name"));
				resultObject.setStaticPrice(resultSet.getBoolean("is_static_price"));
				//checkDates(resultObject, resultSet);
				list.add(resultObject);
			}
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
		finally
		{
			try
			{
				if (resultSet != null)
				{
					resultSet.close();
				}
			}
			catch (Exception e)
			{
			}
			try
			{
				if (statement != null)
				{
					statement.close();
				}
			}
			catch (Exception e)
			{
			}
			try
			{
				if (con != null)
				{
					con.close();
				}
			}
			catch (Exception e)
			{
			}
		}
		return list;
	}
	
	public void saveStoreItems(Store store, Submitter submitter)
	{
		for(Iterator iter = store.getItem().iterator(); iter.hasNext();)
		{
			Item item = (Item)iter.next();
			item.setSubmitterId(submitter.getUserId());
			this.updateItem(item);
		}
	}
	//
	//
	
	public Campus getCampus(Campus campus)
	{
		String SQL = "SELECT * FROM campus where id=" + campus.getId();
		//
		Campus resultObject = null;
		Connection con = this.getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try
		{
			statement = con.prepareStatement(SQL);
			statement.execute();
			resultSet = statement.getResultSet();
			if (resultSet.next())
			{
				resultObject = new Campus();
				resultObject.setId(resultSet.getInt("id"));
				resultObject.setStateId(resultSet.getString("state_id"));
				resultObject.setState(resultSet.getString("state"));
				resultObject.setName(resultSet.getString("name"));
				resultObject.setCity(resultSet.getString("city"));
				resultObject.setZip(resultSet.getInt("zip"));
			}
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
		finally
		{
			try
			{
				if (resultSet != null)
				{
					resultSet.close();
				}
			}
			catch (Exception e)
			{
			}
			try
			{
				if (statement != null)
				{
					statement.close();
				}
			}
			catch (Exception e)
			{
			}
			try
			{
				if (con != null)
				{
					con.close();
				}
			}
			catch (Exception e)
			{
			}
		}
		return resultObject;
	}
	
	public Campus insertCampus(Campus campus)
	{
		String SQL = "INSERT INTO campus (name, state_id, state, city, zip" +
			") VALUES ('" 
			+ campus.getName() + "','" 
			+ campus.getStateId() + "','" 
			+ campus.getState() + "','" 
			+ campus.getCity() + "'," 
			+ campus.getZip() + ")";
		//
		Campus resultObject = null;
		Connection con = this.getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try
		{
			statement = con.prepareStatement(SQL);
			statement.execute();
			//
			SQL = "SELECT * FROM campus WHERE state='" + campus.getState() + "' ORDER BY id DESC LIMIT 1";
			statement = con.prepareStatement(SQL);
			statement.execute();
			resultSet = statement.getResultSet();
			if (resultSet.next())
			{
				resultObject = new Campus();
				resultObject.setId(resultSet.getInt("id"));
				resultObject.setStateId(resultSet.getString("state_id"));
				resultObject.setState(resultSet.getString("state"));
				resultObject.setName(resultSet.getString("name"));
				resultObject.setCity(resultSet.getString("city"));
				resultObject.setZip(resultSet.getInt("zip"));
			}
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
		finally
		{
			try
			{
				if (resultSet != null)
				{
					resultSet.close();
				}
			}
			catch (Exception e)
			{
			}
			try
			{
				if (statement != null)
				{
					statement.close();
				}
			}
			catch (Exception e)
			{
			}
			try
			{
				if (con != null)
				{
					con.close();
				}
			}
			catch (Exception e)
			{
			}
		}
		return resultObject;
	}
	
	public void updateCampus(Campus campus)
	{
		String SQL = 
			"UPDATE campus SET name='" + campus.getName() + "'" +
			", state_id='" + campus.getStateId() + "'" +
			", state='" + campus.getState() + "'" +
			", city='" + campus.getCity() + "'" +
			", zip= " + campus.getZip() + 
			" WHERE id=" + campus.getId();
		//
		Connection con = this.getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try
		{
			statement = con.prepareStatement(SQL);
			statement.execute();
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
		finally
		{
			try
			{
				if (statement != null)
				{
					statement.close();
				}
			}
			catch (Exception e)
			{
			}
			try
			{
				if (con != null)
				{
					con.close();
				}
			}
			catch (Exception e)
			{
			}
		}
		return;
	}
	
	public void deleteCampus(Campus campus)
	{
		String SQL = "DELETE FROM campus WHERE id=" + campus.getId() + " CASCADE";
		//
		Connection con = this.getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try
		{
			statement = con.prepareStatement(SQL);
			statement.execute();
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
		finally
		{
			try
			{
				if (statement != null)
				{
					statement.close();
				}
			}
			catch (Exception e)
			{
			}
			try
			{
				if (con != null)
				{
					con.close();
				}
			}
			catch (Exception e)
			{
			}
		}
		return;
	}
	//
	//
	
	public Store getStore(Store store)
	{
		String SQL = "SELECT * FROM store WHERE id=" + store.getId();
		//
		Store resultObject = null;
		Connection con = this.getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try
		{
			statement = con.prepareStatement(SQL);
			statement.execute();
			resultSet = statement.getResultSet();
			if (resultSet.next())
			{
				resultObject = new Store();
				resultObject.setId(resultSet.getInt("id"));
				resultObject.setCampusId(resultSet.getInt("campus_id"));
				resultObject.setName(resultSet.getString("name"));
				resultObject.setAddress(resultSet.getString("address"));
				resultObject.setCity(resultSet.getString("city"));
				resultObject.setPhone(resultSet.getString("phone"));
				resultObject.setZip(resultSet.getInt("zip"));
			}
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
		finally
		{
			try
			{
				if (resultSet != null)
				{
					resultSet.close();
				}
			}
			catch (Exception e)
			{
			}
			try
			{
				if (statement != null)
				{
					statement.close();
				}
			}
			catch (Exception e)
			{
			}
			try
			{
				if (con != null)
				{
					con.close();
				}
			}
			catch (Exception e)
			{
			}
		}
		return resultObject;
	}
	
	public Store insertStore(Store store)
	{
		String SQL = 
			"INSERT INTO store (campus_id, name, city, address, zip, phone" +
			") VALUES (" 
			+ store.getCampusId() + ",'" 
			+ store.getName() + "','" 
			+ store.getCity() + "','" 
			+ store.getAddress()  + "'," 
			+ store.getZip() + ",'" 
			+ store.getPhone() + "')";
		//
		Store resultObject = null;
		Connection con = this.getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try
		{
			statement = con.prepareStatement(SQL);
			statement.execute();
			//
			SQL = "SELECT * FROM store WHERE campus_id=" + store.getCampusId() + " ORDER BY id DESC LIMIT 1";
			statement = con.prepareStatement(SQL);
			statement.execute();
			resultSet = statement.getResultSet();
			if (resultSet.next())
			{
				resultObject = new Store();
				resultObject.setId(resultSet.getInt("id"));
				resultObject.setCampusId(resultSet.getInt("campus_id"));
				resultObject.setName(resultSet.getString("name"));
				resultObject.setAddress(resultSet.getString("address"));
				resultObject.setCity(resultSet.getString("city"));
				resultObject.setPhone(resultSet.getString("phone"));
				resultObject.setZip(resultSet.getInt("zip"));
			}
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
		finally
		{
			try
			{
				if (resultSet != null)
				{
					resultSet.close();
				}
			}
			catch (Exception e)
			{
			}
			try
			{
				if (statement != null)
				{
					statement.close();
				}
			}
			catch (Exception e)
			{
			}
			try
			{
				if (con != null)
				{
					con.close();
				}
			}
			catch (Exception e)
			{
			}
		}
		return resultObject;
		
	}
	
	public void updateStore(Store store)
	{
		String SQL = 
			"UPDATE store SET campus_id=" + store.getCampusId()  + 
			", name='" + store.getName() + "'" +
			", city='" + store.getCity() + "'" +
			", address='" + store.getAddress() + "'" + 
			", zip=" + store.getZip() + 
			", phone='" + store.getPhone() + "'" +
			" WHERE id=" + store.getId();   
		//
		Connection con = this.getConnection();
		PreparedStatement statement = null;
		try
		{
			statement = con.prepareStatement(SQL);
			statement.execute();
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
		finally
		{
			try
			{
				if (statement != null)
				{
					statement.close();
				}
			}
			catch (Exception e)
			{
			}
			try
			{
				if (con != null)
				{
					con.close();
				}
			}
			catch (Exception e)
			{
			}
		}
		return;
	}
	
	public void deleteStore(Store store)
	{
		String SQL = "DELETE FROM store WHERE id=" + store.getId() + " CASCADE";
		///
		Connection con = this.getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try
		{
			statement = con.prepareStatement(SQL);
			statement.execute();
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
		finally
		{
			try
			{
				if (statement != null)
				{
					statement.close();
				}
			}
			catch (Exception e)
			{
			}
			try
			{
				if (con != null)
				{
					con.close();
				}
			}
			catch (Exception e)
			{
			}
		}
		return;
	}
	//
	//
	
	public Item getItem(Item item)
	{
		String SQL = "SELECT *,to_char(edited_timestamp, 'MM/DD/YYYY') AS edited_timestamp, to_char(special_start, 'MM/DD/YYYY') AS special_start_str, to_char(special_stop, 'MM/DD/YYYY') AS special_stop_str, to_char(now(), 'MM/DD/YYYY') as current_timestamp FROM item WHERE id=" + item.getId();
		//
		Item resultObject = null;
		Connection con = this.getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try
		{
			statement = con.prepareStatement(SQL);
			statement.execute();
			resultSet = statement.getResultSet();
			if (resultSet.next())
			{
				resultObject = new Item();
				resultObject.setId(resultSet.getInt("id"));
				resultObject.setStoreId(resultSet.getInt("store_id"));
				resultObject.setCampusId(resultSet.getInt("campus_id"));
				resultObject.setProductId(resultSet.getInt("product_id"));
				resultObject.setPrice(resultSet.getInt("price"));
				resultObject.setQuantity(resultSet.getInt("quantity"));
				resultObject.setPriceDisplay("$"+((double)resultSet.getInt("price"))/100);
				resultObject.setName(resultSet.getString("name"));
				resultObject.setBrand(resultSet.getString("brand"));
				resultObject.setType(resultSet.getString("type"));
				resultObject.setContainer(resultSet.getString("container"));
				resultObject.setEditedTimestamp(resultSet.getString("edited_timestamp_str"));
				resultObject.setFlagged(resultSet.getBoolean("is_flagged"));
				resultObject.setPopular(resultSet.getBoolean("is_popular"));
				resultObject.setSpecial(resultSet.getBoolean("is_special"));
				resultObject.setSpecialStart(resultSet.getString("special_start_str"));
				resultObject.setSpecialStop(resultSet.getString("special_stop_str"));
				resultObject.setSpecialPrice(resultSet.getInt("special_price"));
				resultObject.setSpecialPriceDisplay("$"+((double)resultSet.getInt("special_price"))/100);
				resultObject.setSubmitterId(resultSet.getString("submitter_id"));
				resultObject.setStoreName(resultSet.getString("store_name"));
				resultObject.setStaticPrice(resultSet.getBoolean("is_static_price"));
				//checkDates(resultObject, resultSet);
			}
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
		finally
		{
			try
			{
				if (resultSet != null)
				{
					resultSet.close();
				}
			}
			catch (Exception e)
			{
			}
			try
			{
				if (statement != null)
				{
					statement.close();
				}
			}
			catch (Exception e)
			{
			}
			try
			{
				if (con != null)
				{
					con.close();
				}
			}
			catch (Exception e)
			{
			}
		}
		return resultObject;
	}
	
	public Item insertItem(Item item)
	{
		String SQL = 
			"INSERT INTO item (" + 
			"store_id, campus_id, name, price, brand, type, container, " +
			"submitter_id, is_flagged, is_special, special_start, special_stop, special_price, " +
			"is_popular, quantity, edited_timestamp" +
			") VALUES (" + 
			+ item.getStoreId() + ","
			+ item.getCampusId() + ",'"
			+ item.getName() + "'," 
			+ item.getPrice() + ",'" 
			+ item.getBrand() + "','" 
			+ item.getType() + "','" 
			+ item.getContainer() + "','" 
			+ item.getSubmitterId() + "'," 
			+ item.isFlagged() + "," 
			+ item.isSpecial() + "," 
			+ "to_timestamp('" + item.getSpecialStart() + "','MM/DD/YYYY')," 
			+ "to_timestamp('" + item.getSpecialStop() + "','MM/DD/YYYY'),"
			+ item.getSpecialPrice() + ","
			+ item.isPopular() + ","
			+ item.getQuantity() + "," 
			+ "now())";
		//
		Item resultObject = null;
		Connection con = this.getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try
		{
			statement = con.prepareStatement(SQL);
			statement.execute();
			//
			SQL = "SELECT *,to_char(edited_timestamp, 'MM/DD/YYYY') AS edited_timestamp, to_char(special_start, 'MM/DD/YYYY') AS special_start_str, to_char(special_stop, 'MM/DD/YYYY') AS special_stop_str, to_char(now(), 'MM/DD/YYYY') as current_timestamp FROM item WHERE store_id=" + item.getStoreId() + " ORDER BY id DESC LIMIT 1";
			statement = con.prepareStatement(SQL);
			statement.execute();
			resultSet = statement.getResultSet();
			if (resultSet.next())
			{
				resultObject = new Item();
				resultObject.setId(resultSet.getInt("id"));
				resultObject.setStoreId(resultSet.getInt("store_id"));
				resultObject.setCampusId(resultSet.getInt("campus_id"));
				resultObject.setProductId(resultSet.getInt("product_id"));
				resultObject.setPrice(resultSet.getInt("price"));
				resultObject.setQuantity(resultSet.getInt("quantity"));
				resultObject.setPriceDisplay("$"+((double)resultSet.getInt("price"))/100);
				resultObject.setName(resultSet.getString("name"));
				resultObject.setBrand(resultSet.getString("brand"));
				resultObject.setType(resultSet.getString("type"));
				resultObject.setContainer(resultSet.getString("container"));
				resultObject.setEditedTimestamp(resultSet.getString("edited_timestamp_str"));
				resultObject.setFlagged(resultSet.getBoolean("is_flagged"));
				resultObject.setPopular(resultSet.getBoolean("is_popular"));
				resultObject.setSpecial(resultSet.getBoolean("is_special"));
				resultObject.setSpecialStart(resultSet.getString("special_start_str"));
				resultObject.setSpecialStop(resultSet.getString("special_stop_str"));
				resultObject.setSpecialPrice(resultSet.getInt("special_price"));
				resultObject.setSpecialPriceDisplay("$"+((double)resultSet.getInt("special_price"))/100);
				resultObject.setSubmitterId(resultSet.getString("submitter_id"));
				resultObject.setStoreName(resultSet.getString("store_name"));
				resultObject.setStaticPrice(resultSet.getBoolean("is_static_price"));
				//checkDates(resultObject, resultSet);
			}
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
		finally
		{
			try
			{
				if (resultSet != null)
				{
					resultSet.close();
				}
			}
			catch (Exception e)
			{
			}
			try
			{
				if (statement != null)
				{
					statement.close();
				}
			}
			catch (Exception e)
			{
			}
			try
			{
				if (con != null)
				{
					con.close();
				}
			}
			catch (Exception e)
			{
			}
		}
		return resultObject;
	}
	
	public void updateItem(Item item)
	{
		String SQL = 
			"UPDATE item SET store_id=" + item.getStoreId() + 
			", campus_id" + item.getCampusId() +
			", name='" + item.getName() + "'" +
			", price=" + item.getPrice() +
			", brand='" + item.getBrand() + "'" +
			", type='" + item.getType() + "'" +
			", container='" + item.getContainer() + "'" + 
			", submitter_id='" + item.getSubmitterId() + "'" +
			", is_flagged=" + item.isFlagged()  + 
			", is_special=" + item.isSpecial() + 
			", special_start=to_timestamp('" + item.getSpecialStart() + "','MM/DD/YYYY')" +
			", special_stop=to_timestamp('" + item.getSpecialStop() +  "','MM/DD/YYYY')" +
			", special_price=" + item.getSpecialPrice() +
			", is_popular=" + item.isPopular() +
			", quantity=" + item.getQuantity() +
			", is_static_price=" + item.isStaticPrice() +
			", store_name='" + item.getStoreName() + "'" +
			", edited_timestamp=now() " +
			"WHERE id=" + item.getId();
		//
		Connection con = this.getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try
		{
			statement = con.prepareStatement(SQL);
			statement.execute();
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
		finally
		{
			try
			{
				if (statement != null)
				{
					statement.close();
				}
			}
			catch (Exception e)
			{
			}
			try
			{
				if (con != null)
				{
					con.close();
				}
			}
			catch (Exception e)
			{
			}
		}
		return;
	}
	
	public void deleteItem(Item item)
	{
		String SQL = "DELETE FROM item WHERE id=" + item.getId();
		//
		Connection con = this.getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try
		{
			statement = con.prepareStatement(SQL);
			statement.execute();
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
		finally
		{			
			try
			{
				if (statement != null)
				{
					statement.close();
				}
			}
			catch (Exception e)
			{
			}
			try
			{
				if (con != null)
				{
					con.close();
				}
			}
			catch (Exception e)
			{
			}
		}
		return;
	}
	
	public void flagItem(Item item)
	{
		String SQL = "UPDATE item SET is_flagged=true WHERE id=" + item.getId();
		//
		Connection con = this.getConnection();
		PreparedStatement statement = null;
		try
		{
			statement = con.prepareStatement(SQL);
			statement.execute();
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
		finally
		{
			try
			{
				if (statement != null)
				{
					statement.close();
				}
			}
			catch (Exception e)
			{
			}
			try
			{
				if (con != null)
				{
					con.close();
				}
			}
			catch (Exception e)
			{
			}
		}
		return;
	}
	
	public void unflagItem(Item item)
	{
		String SQL = "UPDATE item SET is_flagged=false WHERE id=" + item.getId();
		//
		Connection con = this.getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try
		{
			statement = con.prepareStatement(SQL);
			statement.execute();
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
		finally
		{			
			try
			{
				if (statement != null)
				{
					statement.close();
				}
			}
			catch (Exception e)
			{
			}
			try
			{
				if (con != null)
				{
					con.close();
				}
			}
			catch (Exception e)
			{
			}
		}
		return;
	}
	
	public void setItemPopular(Item item)
	{
		String SQL = "UPDATE item SET is_popular=true WHERE id=" + item.getId();
		//
		Connection con = this.getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try
		{
			statement = con.prepareStatement(SQL);
			statement.execute();
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
		finally
		{			
			try
			{
				if (statement != null)
				{
					statement.close();
				}
			}
			catch (Exception e)
			{
			}
			try
			{
				if (con != null)
				{
					con.close();
				}
			}
			catch (Exception e)
			{
			}
		}
		return;
	}
	
	public void setItemUnpopular(Item item)
	{
		String SQL = "UPDATE item SET is_popular=flase WHERE id=" + item.getId();
		//
		Connection con = this.getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try
		{
			statement = con.prepareStatement(SQL);
			statement.execute();
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
		finally
		{			
			try
			{
				if (statement != null)
				{
					statement.close();
				}
			}
			catch (Exception e)
			{
			}
			try
			{
				if (con != null)
				{
					con.close();
				}
			}
			catch (Exception e)
			{
			}
		}
		return;
	}
	
	public void endSpecial(Item item)
	{
		item.setSpecial(false);
		String SQL = "UPDATE item SET is_special=flase, special_start='', special_stop='', special_price=0 WHERE id=" + item.getId();
		//
		Connection con = this.getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try
		{
			statement = con.prepareStatement(SQL);
			statement.execute();
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
		finally
		{			
			try
			{
				if (statement != null)
				{
					statement.close();
				}
			}
			catch (Exception e)
			{
			}
			try
			{
				if (con != null)
				{
					con.close();
				}
			}
			catch (Exception e)
			{
			}
		}
		return;
	}
	//
	//
	
	public Brand insertBrand(Brand brand)
	{
		String SQL = "INSERT INTO brand (name, type) VALUES ('" + brand.getName() + "', '" + brand.getType() + "')";
		//
		Brand resultObject = null;
		Connection con = this.getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try
		{
			statement = con.prepareStatement(SQL);
			statement.execute();
			//
			SQL = "SELECT * FROM brand ORDER BY id DESC LIMIT 1";
			statement = con.prepareStatement(SQL);
			statement.execute();
			resultSet = statement.getResultSet();
			if (resultSet.next())
			{
				resultObject = new Brand();
				resultObject.setId(resultSet.getInt("id"));
				resultObject.setName(resultSet.getString("name"));
				resultObject.setType(resultSet.getString("type"));
			}
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
		finally
		{
			try
			{
				if (resultSet != null)
				{
					resultSet.close();
				}
			}
			catch (Exception e)
			{
			}
			try
			{
				if (statement != null)
				{
					statement.close();
				}
			}
			catch (Exception e)
			{
			}
			try
			{
				if (con != null)
				{
					con.close();
				}
			}
			catch (Exception e)
			{
			}
		}
		return resultObject;
	}
	
	public void updateBrand(Brand brand)
	{
		String SQL = "UPDATE brand SET name='" + brand.getName() + "'" +
			", type='" + brand.getType() + "'" +
			"WHERE id=" + brand.getId();
		//
		Connection con = this.getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try
		{
			statement = con.prepareStatement(SQL);
			statement.execute();
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
		finally
		{			
			try
			{
				if (statement != null)
				{
					statement.close();
				}
			}
			catch (Exception e)
			{
			}
			try
			{
				if (con != null)
				{
					con.close();
				}
			}
			catch (Exception e)
			{
			}
		}
		return;
	}
	
	public void deleteBrand(Brand brand)
	{
		String SQL = "DELETE FROM brand WHERE id=" + brand.getId() + " CASCADE";
		//
		Connection con = this.getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try
		{
			statement = con.prepareStatement(SQL);
			statement.execute();
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
		finally
		{			
			try
			{
				if (statement != null)
				{
					statement.close();
				}
			}
			catch (Exception e)
			{
			}
			try
			{
				if (con != null)
				{
					con.close();
				}
			}
			catch (Exception e)
			{
			}
		}
		return;
	}
	
	public Product insertProduct(Product product)
	{
		String SQL = "INSERT INTO product (brand_id, name, brand_name, type, is_popular) " 
			+ "VALUES (" + 
			+ product.getBrandId() + ",' "
			+ product.getName() + "',' "
			+ product.getBrandName() + "',' "
			+ product.getType() + "', "
			+ product.isPopular() + ")";
		//
		Product resultObject = null;
		Connection con = this.getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try
		{
			statement = con.prepareStatement(SQL);
			statement.execute();
			//
			SQL = "SELECT * FROM product WHERE brand_id=" + product.getBrandId() + " ORDER BY id DESC LIMIT 1";
			statement = con.prepareStatement(SQL);
			statement.execute();
			resultSet = statement.getResultSet();
			if (resultSet.next())
			{
				resultObject = new Product();
				resultObject.setId(resultSet.getInt("id"));
				resultObject.setBrandId(resultSet.getInt("brand_id"));
				resultObject.setName(resultSet.getString("name"));
				resultObject.setBrandName(resultSet.getString("brand_name"));
				resultObject.setType(resultSet.getString("type"));
				resultObject.setPopular(resultSet.getBoolean("is_popular"));
			}
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
		finally
		{
			try
			{
				if (resultSet != null)
				{
					resultSet.close();
				}
			}
			catch (Exception e)
			{
			}
			try
			{
				if (statement != null)
				{
					statement.close();
				}
			}
			catch (Exception e)
			{
			}
			try
			{
				if (con != null)
				{
					con.close();
				}
			}
			catch (Exception e)
			{
			}
		}
		return resultObject;
	}
	
	public void updateProduct(Product product)
	{
		String SQL = "UPDATE product SET brand_id=" + product.getBrandId() +
			", name='" + product.getName() + "'" +
			", brand_name='" + product.getBrandName() + "'" +
			", type='" + product.getType() + "'" +
			", is_popular=" + product.isPopular() + 
			" WHERE id=" + product.getId();
		//
		Connection con = this.getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try
		{
			statement = con.prepareStatement(SQL);
			statement.execute();
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
		finally
		{			
			try
			{
				if (statement != null)
				{
					statement.close();
				}
			}
			catch (Exception e)
			{
			}
			try
			{
				if (con != null)
				{
					con.close();
				}
			}
			catch (Exception e)
			{
			}
		}
		return;
	}
	
	public void deleteProduct(Product product)
	{
		String SQL = "DELETE FROM product WHERE id=" + product.getId();
		//
		Connection con = this.getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try
		{
			statement = con.prepareStatement(SQL);
			statement.execute();
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
		finally
		{			
			try
			{
				if (statement != null)
				{
					statement.close();
				}
			}
			catch (Exception e)
			{
			}
			try
			{
				if (con != null)
				{
					con.close();
				}
			}
			catch (Exception e)
			{
			}
		}
		return;
	}
	
	//
	// SUPPORT
	//
	

	public List getPopularProducts()
	{
		String SQL = "SELECT * FROM product WHERE is_popular=true";
		//
		List list = null;
		Product resultObject = null;
		Connection con = this.getConnection();
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
				resultObject = new Product();
				resultObject.setId(resultSet.getInt("id"));
				resultObject.setBrandId(resultSet.getInt("brand_id"));
				resultObject.setName(resultSet.getString("name"));
				resultObject.setBrandName(resultSet.getString("brand_name"));
				resultObject.setType(resultSet.getString("type"));
				resultObject.setPopular(resultSet.getBoolean("is_popular"));
				list.add(resultObject);
			}
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
		finally
		{
			try
			{
				if (resultSet != null)
				{
					resultSet.close();
				}
			}
			catch (Exception e)
			{
			}
			try
			{
				if (statement != null)
				{
					statement.close();
				}
			}
			catch (Exception e)
			{
			}
			try
			{
				if (con != null)
				{
					con.close();
				}
			}
			catch (Exception e)
			{
			}
		}
		return list;
	}
	
	public List getNonPopularProducts()
	{
		String SQL = "SELECT * FROM product WHERE is_popular=false";
		//
		List list = null;
		Product resultObject = null;
		Connection con = this.getConnection();
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
				resultObject = new Product();
				resultObject.setId(resultSet.getInt("id"));
				resultObject.setBrandId(resultSet.getInt("brand_id"));
				resultObject.setName(resultSet.getString("name"));
				resultObject.setBrandName(resultSet.getString("brand_name"));
				resultObject.setType(resultSet.getString("type"));
				resultObject.setPopular(resultSet.getBoolean("is_popular"));
				list.add(resultObject);
			}
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
		finally
		{
			try
			{
				if (resultSet != null)
				{
					resultSet.close();
				}
			}
			catch (Exception e)
			{
			}
			try
			{
				if (statement != null)
				{
					statement.close();
				}
			}
			catch (Exception e)
			{
			}
			try
			{
				if (con != null)
				{
					con.close();
				}
			}
			catch (Exception e)
			{
			}
		}
		return list;
	}
	
	
	public List getProductsByBrand(Brand brand)
	{
		String SQL = "SELECT * FROM product WHERE brand_id=" + brand.getId();
		//
		List list = null;
		Product resultObject = null;
		Connection con = this.getConnection();
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
				resultObject = new Product();
				resultObject.setId(resultSet.getInt("id"));
				resultObject.setBrandId(resultSet.getInt("brand_id"));
				resultObject.setName(resultSet.getString("name"));
				resultObject.setBrandName(resultSet.getString("brand_name"));
				resultObject.setType(resultSet.getString("type"));
				resultObject.setPopular(resultSet.getBoolean("is_popular"));
				list.add(resultObject);
			}
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
		finally
		{
			try
			{
				if (resultSet != null)
				{
					resultSet.close();
				}
			}
			catch (Exception e)
			{
			}
			try
			{
				if (statement != null)
				{
					statement.close();
				}
			}
			catch (Exception e)
			{
			}
			try
			{
				if (con != null)
				{
					con.close();
				}
			}
			catch (Exception e)
			{
			}
		}
		return list;
	}
	
	public List getBrands()
	{
		String SQL = "SELECT * from brand";
		//
		List list = null;
		Brand resultObject = null;
		Connection con = this.getConnection();
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
				resultObject = new Brand();
				resultObject.setId(resultSet.getInt("id"));
				resultObject.setName(resultSet.getString("name"));
				resultObject.setType(resultSet.getString("type"));
				list.add(resultObject);
			}
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
		finally
		{
			try
			{
				if (resultSet != null)
				{
					resultSet.close();
				}
			}
			catch (Exception e)
			{
			}
			try
			{
				if (statement != null)
				{
					statement.close();
				}
			}
			catch (Exception e)
			{
			}
			try
			{
				if (con != null)
				{
					con.close();
				}
			}
			catch (Exception e)
			{
			}
		}
		return list;
	}
	
	public List getProducts()
	{
		String SQL = "SELECT * from product";
		//
		List list = null;
		Product resultObject = null;
		Connection con = this.getConnection();
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
				resultObject = new Product();
				resultObject.setId(resultSet.getInt("id"));
				resultObject.setBrandId(resultSet.getInt("brand_id"));
				resultObject.setName(resultSet.getString("name"));
				resultObject.setBrandName(resultSet.getString("brand_name"));
				resultObject.setType(resultSet.getString("type"));
				resultObject.setPopular(resultSet.getBoolean("is_popular"));
				list.add(resultObject);
			}
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
		finally
		{
			try
			{
				if (resultSet != null)
				{
					resultSet.close();
				}
			}
			catch (Exception e)
			{
			}
			try
			{
				if (statement != null)
				{
					statement.close();
				}
			}
			catch (Exception e)
			{
			}
			try
			{
				if (con != null)
				{
					con.close();
				}
			}
			catch (Exception e)
			{
			}
		}
		return list;
	}
	
	public List checkForItem(Item item)
	{
		String SQL = "SELECT id FROM item WHERE campus_id=" + item.getCampusId() + " AND store_id=" + item.getStoreId() + " AND brand='" + item.getBrand() + "' AND product_id=" + item.getProductId();
			SQL += " AND container='" + item.getContainer() + "' AND quantity=" + item.getQuantity() + " AND id!=" + item.getId();
		//
		List list = null;
		Item resultObject = null;
		Connection con = this.getConnection();
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
				resultObject = new Item();
				resultObject.setId(resultSet.getInt("id"));
				resultObject.setStoreId(resultSet.getInt("store_id"));
				resultObject.setCampusId(resultSet.getInt("campus_id"));
				resultObject.setProductId(resultSet.getInt("product_id"));
				resultObject.setPrice(resultSet.getInt("price"));
				resultObject.setQuantity(resultSet.getInt("quantity"));
				resultObject.setPriceDisplay("$"+((double)resultSet.getInt("price"))/100);
				resultObject.setName(resultSet.getString("name"));
				resultObject.setBrand(resultSet.getString("brand"));
				resultObject.setType(resultSet.getString("type"));
				resultObject.setContainer(resultSet.getString("container"));
				resultObject.setEditedTimestamp(resultSet.getString("edited_timestamp_str"));
				resultObject.setFlagged(resultSet.getBoolean("is_flagged"));
				resultObject.setPopular(resultSet.getBoolean("is_popular"));
				resultObject.setSpecial(resultSet.getBoolean("is_special"));
				resultObject.setSpecialStart(resultSet.getString("special_start_str"));
				resultObject.setSpecialStop(resultSet.getString("special_stop_str"));
				resultObject.setSpecialPrice(resultSet.getInt("special_price"));
				resultObject.setSpecialPriceDisplay("$"+((double)resultSet.getInt("special_price"))/100);
				resultObject.setSubmitterId(resultSet.getString("submitter_id"));
				resultObject.setStoreName(resultSet.getString("store_name"));
				resultObject.setStaticPrice(resultSet.getBoolean("is_static_price"));
				//checkDates(resultObject, resultSet);
				list.add(resultObject);
			}
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
		finally
		{
			try
			{
				if (resultSet != null)
				{
					resultSet.close();
				}
			}
			catch (Exception e)
			{
			}
			try
			{
				if (statement != null)
				{
					statement.close();
				}
			}
			catch (Exception e)
			{
			}
			try
			{
				if (con != null)
				{
					con.close();
				}
			}
			catch (Exception e)
			{
			}
		}
		return list;
	}
	
	//
	// USER LOGIN	
	//
	
	public Submitter checkUserId(Submitter submitter)
	{
		String SQL = "SELECT * FROM submitter where user_id='" + submitter.getUserId() + "'";
		//
		Submitter resultObject = null;
		Connection con = this.getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try
		{
			statement = con.prepareStatement(SQL);
			statement.execute();
			resultSet = statement.getResultSet();
			if (resultSet.next())
			{
				resultObject = new Submitter();
				resultObject.setUserId(resultSet.getString("user_id"));
				resultObject.setPassword(resultSet.getString("password"));
				resultObject.setAuthenticated(resultSet.getBoolean("authenticated"));
				resultObject.setAuthenticationCode(resultSet.getInt("authentication_code"));
				resultObject.setAdmin(resultSet.getBoolean("admin"));
				resultObject.setSubmissions(resultSet.getInt("submissions"));
				resultObject.setRank(resultSet.getString("rank"));
			}
			else
			{
				resultObject = null; 
			}
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
		finally
		{
			try
			{
				if (resultSet != null)
				{
					resultSet.close();
				}
			}
			catch (Exception e)
			{
			}
			try
			{
				if (statement != null)
				{
					statement.close();
				}
			}
			catch (Exception e)
			{
			}
			try
			{
				if (con != null)
				{
					con.close();
				}
			}
			catch (Exception e)
			{
			}
		}
		return resultObject;
	}

	public void loginSubmitter(Submitter submitter)
	{
		String SQL = "SELECT * FROM submitter where user_id='" + submitter.getUserId() + "' AND password='" + submitter.getPassword() + "' AND authenticated=true";
		//
		Submitter resultObject = null;
		Connection con = this.getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try
		{
			statement = con.prepareStatement(SQL);
			statement.execute();
			resultSet = statement.getResultSet();
			if (resultSet.next())
			{
				resultObject = new Submitter();
				resultObject.setUserId(resultSet.getString("user_id"));
				resultObject.setPassword(resultSet.getString("password"));
				resultObject.setAuthenticated(resultSet.getBoolean("authenticated"));
				resultObject.setAuthenticationCode(resultSet.getInt("authentication_code"));
				resultObject.setAdmin(resultSet.getBoolean("admin"));
				resultObject.setSubmissions(resultSet.getInt("submissions"));
				resultObject.setRank(resultSet.getString("rank"));
				resultObject.setLoggedIn(true);
				submitter = resultObject;
			}
			else
			{
				submitter.setLoggedIn(false);
			}
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
		finally
		{
			try
			{
				if (resultSet != null)
				{
					resultSet.close();
				}
			}
			catch (Exception e)
			{
			}
			try
			{
				if (statement != null)
				{
					statement.close();
				}
			}
			catch (Exception e)
			{
			}
			try
			{
				if (con != null)
				{
					con.close();
				}
			}
			catch (Exception e)
			{
			}
		}
	}

	public Submitter getSubmitter(Submitter submitter)
	{
		String SQL = "SELECT * FROM submitter where user_id='" + submitter.getUserId() + "'";
		//
		Submitter resultObject = null;
		Connection con = this.getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try
		{
			statement = con.prepareStatement(SQL);
			statement.execute();
			resultSet = statement.getResultSet();
			if (resultSet.next())
			{
				resultObject = new Submitter();
				resultObject.setUserId(resultSet.getString("user_id"));
				resultObject.setPassword(resultSet.getString("password"));
				resultObject.setAuthenticated(resultSet.getBoolean("authenticated"));
				resultObject.setAuthenticationCode(resultSet.getInt("authentication_code"));
				resultObject.setAdmin(resultSet.getBoolean("admin"));
				resultObject.setSubmissions(resultSet.getInt("submissions"));
				resultObject.setRank(resultSet.getString("rank"));
			}
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
		finally
		{
			try
			{
				if (resultSet != null)
				{
					resultSet.close();
				}
			}
			catch (Exception e)
			{
			}
			try
			{
				if (statement != null)
				{
					statement.close();
				}
			}
			catch (Exception e)
			{
			}
			try
			{
				if (con != null)
				{
					con.close();
				}
			}
			catch (Exception e)
			{
			}
		}
		return resultObject;
	}
	
	public void insertSubmitter(Submitter submitter)
	{
		String SQL = "INSERT INTO submitter (user_id, password, authenticated, authentication_code, admin, submissions, rank" +
			") VALUES ('" 
			+ submitter.getUserId() + "','" 
			+ submitter.getPassword() + "'," 
			+ submitter.isAuthenticated() + ","
			+ submitter.getAuthenticationCode() + ","
			+ submitter.isAdmin() + ","  
			+ submitter.getSubmissions() + ",'"			 
			+ submitter.getRank() + "')";
		//
		Connection con = this.getConnection();
		PreparedStatement statement = null;
		try
		{
			statement = con.prepareStatement(SQL);
			statement.execute();
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
		finally
		{
			try
			{
				if (statement != null)
				{
					statement.close();
				}
			}
			catch (Exception e)
			{
			}
			try
			{
				if (con != null)
				{
					con.close();
				}
			}
			catch (Exception e)
			{
			}
		}
	}
	
	public void updateSubmitter(Submitter submitter)
	{
		String SQL = 
			"UPDATE submitter SET user_id='" + submitter.getUserId() + "'" +
			", password='" + submitter.getPassword() + "'" +
			", authenticated=" + submitter.isAuthenticated() + 
			", authentication_code=" + submitter.getAuthenticationCode() +
			", admin=" + submitter.isAdmin() +  
			", submissions=" + submitter.getSubmissions() + 
			", rank='" + submitter.getRank() +  "'" +
			" WHERE id=" + submitter.getUserId();
		//
		Connection con = this.getConnection();
		PreparedStatement statement = null;
		try
		{
			statement = con.prepareStatement(SQL);
			statement.execute();
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
		finally
		{
			try
			{
				if (statement != null)
				{
					statement.close();
				}
			}
			catch (Exception e)
			{
			}
			try
			{
				if (con != null)
				{
					con.close();
				}
			}
			catch (Exception e)
			{
			}
		}
		return;
	}
	
	public void deleteSubmitter(Submitter submitter)
	{
		String SQL = "DELETE FROM submitter WHERE user_id='" + submitter.getUserId() + "'";
		//
		Connection con = this.getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try
		{
			statement = con.prepareStatement(SQL);
			statement.execute();
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
		finally
		{
			try
			{
				if (statement != null)
				{
					statement.close();
				}
			}
			catch (Exception e)
			{
			}
			try
			{
				if (con != null)
				{
					con.close();
				}
			}
			catch (Exception e)
			{
			}
		}
		return;
	}
	
	public void authenticateSubmitter(Submitter submitter)
	{
		String SQL = "UPDATE submitter SET authenticate=true WHERE user_id='" + submitter.getUserId() + "'";
		//
		Connection con = this.getConnection();
		PreparedStatement statement = null;
		try
		{
			statement = con.prepareStatement(SQL);
			statement.execute();
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
		finally
		{			
			try
			{
				if (statement != null)
				{
					statement.close();
				}
			}
			catch (Exception e)
			{
			}
			try
			{
				if (con != null)
				{
					con.close();
				}
			}
			catch (Exception e)
			{
			}
		}
		return;
	}
	
	public void addSubmission(Submitter submitter)
	{
		String SQL = "UPDATE submitter SET submissions=" + submitter.addSubmission() + " WHERE user_id=" + submitter.getUserId();
		//
		Connection con = this.getConnection();
		PreparedStatement statement = null;
		try
		{
			statement = con.prepareStatement(SQL);
			statement.execute();
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
		finally
		{			
			try
			{
				if (statement != null)
				{
					statement.close();
				}
			}
			catch (Exception e)
			{
			}
			try
			{
				if (con != null)
				{
					con.close();
				}
			}
			catch (Exception e)
			{
			}
		}
		return;
	}
	//
	//
	
	//
	// HELPER METHODS
	//
	private void checkDates(Item resultObject, ResultSet resultSet)
	{
		try
		{
			if(resultObject.isStaticPrice() && !resultObject.isFlagged())
			{
				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
				Date edited = sdf.parse(resultObject.getEditedTimestamp());
				Date current = sdf.parse(resultSet.getString("current_timestamp"));
				if ((edited.getDay() + current.getDay() - 30) > 7)
				{
					resultObject.setOutOfDate(false);
				}
			}
			if(resultObject.isSpecial() && !resultObject.isFlagged())
			{
				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
				Date start = sdf.parse(resultObject.getSpecialStart());
				Date stop = sdf.parse(resultObject.getSpecialStop());
				Date current = sdf.parse(resultSet.getString("current_timestamp"));
				if (current.before(start))
				{
					resultObject.setSpecial(false);
				}
				if (current.after(stop))
				{
					endSpecial(resultObject);
				}
			}
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}
	
}

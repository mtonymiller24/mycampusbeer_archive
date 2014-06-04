package bean;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlRootElement;

//<xsd:element name="data">
//<xsd:complexType>
//	<xsd:sequence>
//		<xsd:element name="product" type="Product" minOccurs="0" maxOccurs="unbounded" />
//		<xsd:element name="brand" type="Brand" minOccurs="0" maxOccurs="unbounded" />
//		<xsd:element name="campus" type="Campus" minOccurs="0" maxOccurs="unbounded" />
//		<xsd:element name="store" type="Store" minOccurs="0" maxOccurs="unbounded" />
//		<xsd:element name="item" type="Item" minOccurs="0" maxOccurs="unbounded" />
//		<xsd:element name="user" type="User" minOccurs="0" />
//		
//		<xsd:element name="state" type="xsd:string" minOccurs="0" maxOccurs="unbounded" />
//	</xsd:sequence>
//</xsd:complexType>
//</xsd:element>

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "submitter", "campus", "store", "item", "brand", "product", "messages", "myCampusEnvironment", "quantities", "containers" })
@XmlRootElement(name = "data")
public class Data
{
	protected Submitter submitter;
	protected ArrayList<Campus> campus;
	protected ArrayList<Store> store;
	protected ArrayList<Item> item;
	protected ArrayList<Brand> brand;
	protected ArrayList<Product> product;
	protected ArrayList<Message> messages;
	protected List quantities;
	protected List containers;
	protected MyCampusEnvironment myCampusEnvironment;
	
	public Submitter getSubmitter()
	{
		return submitter;
	}
	
	public void setSubmitter(Submitter value)
	{
		this.submitter = value;
	}
	//
	
	public ArrayList<Campus> getCampus()
	{
		if (campus == null)
		{
			campus = new ArrayList<Campus>();
		}
		return this.campus;
	}
	//
	public ArrayList<Store> getStore()
	{
		if (store == null)
		{
			store = new ArrayList<Store>();
		}
		return this.store;
	}
	//
	public ArrayList<Item> getItem()
	{
		if (item == null)
		{
			item = new ArrayList<Item>();
		}
		return this.item;
	}
	//
	public ArrayList<Brand> getBrand()
	{
		if (brand == null)
		{
			brand = new ArrayList<Brand>();
		}
		return this.brand;
	}
	//
	public ArrayList<Product> getProduct()
	{
		if (product == null)
		{
			product = new ArrayList<Product>();
		}
		return this.product;
	}
	
	public ArrayList<Message> getMessage()
	{
		if(messages == null)
		{
			messages = new ArrayList<Message>();
		}
		return this.messages;
	}
	
	public List getQuantities()
	{
		if(quantities == null)
		{
			quantities = new ArrayList();
		}
		return this.quantities;
	}
	
	public List getContainers()
	{
		if(containers == null)
		{
			containers = new ArrayList();
		}
		return this.containers;
	}
	
	public void setMyCampusEnvironment(MyCampusEnvironment value)	
	{
		this.myCampusEnvironment = value;
	}
}
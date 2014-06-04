package bean;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

//<xsd:element name="id" type="xsd:int" />
//<xsd:element name="name" type="xsd:string" />
//<xsd:element name="state" type="xsd:string" />
//<xsd:element name="city" type="xsd:string" />
//<xsd:element name="zip" type="xsd:int" />
//<xsd:element name="store" type="Store" minOccurs="0" maxOccurs="unbounded" />
//<xsd:element name="item" type="Item" minOccurs="0" maxOccurs="unbounded" />


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Campus", propOrder = { "id", "stateId", "state", "name", "city", "zip", "store", "item" })
public class Campus
{
	protected int id;
	protected String stateId;
	protected String name;
	protected String state;
	protected String city;
	protected int zip;
	protected ArrayList<Store> store;
	protected ArrayList<Item> item;
	
	public int getId()
	{
		return id;
	}
	
	public void setId(int value)
	{
		this.id = value;
	}
	//
	
	public String getStateId()
	{
		return stateId;
	}
	
	public void setStateId(String value)
	{
		this.stateId = value;
	}
	//
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String value)
	{
		this.name = value;
	}
	//
	
	public String getState()
	{
		return state;
	}
	
	public void setState(String value)
	{
		this.state = value;
	}
	//
	
	public String getCity()
	{
		return city;
	}
	
	public void setCity(String value)
	{
		this.city = value;
	}
	//
	
	public int getZip()
	{
		return zip;
	}
	
	public void setZip(int value)
	{
		this.zip = value;
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
}
package bean;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

//<xsd:complexType name="WorkingStorage">
//<xsd:sequence>
//	<xsd:element name="screenId" type="xsd:int" />
//				
//	<xsd:element name="user" type="User" minOccurs="0" />
//	<xsd:element name="campus" type="Campus" minOccurs="0" />
//	<xsd:element name="store" type="Store" minOccurs="0" />
//	<xsd:element name="item" type="Item" minOccurs="0" />
//	<xsd:element name="brand" type="Brand" minOccurs="0" />
//	<xsd:element name="product" type="Product" minOccurs="0" />
//	
//	<xsd:element name="param" type="parameters:Param" minOccurs="0" maxOccurs="unbounded" />
//</xsd:sequence>
//</xsd:complexType>

public class WorkingStorage
{
	protected int screenId;
	protected int lastScreenId;
	protected String screenName;
	protected String stateId;
	protected Submitter submitter;
	protected Campus campus;
	protected Store store;
	protected Item item;
	protected Brand brand;
	protected Product product;
	protected Search search;
	protected MyCampusEnvironment myCampusEnvironment;
	
	protected ArrayList<Message> messages;
	
	
	public int getScreenId()
	{
		return screenId;
	}
	
	public void setScreenId(int value)
	{
		this.screenId = value;
	}
	//
	
	public int getLastScreenId()
	{
		return lastScreenId;
	}
	
	public void setLastScreenId(int value)
	{
		this.lastScreenId = value;
	}
	//
	
	public String getScreenName()
	{
		return screenName;
	}
	
	public void setScreenName(String value)
	{
		this.screenName = value;
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
	public Submitter getSubmitter()
	{
		return submitter;
	}
	
	public void setSubmitter(Submitter value)
	{
		this.submitter = value;
	}
	//
	
	public Campus getCampus()
	{
		return campus;
	}
	
	public void setCampus(Campus value)
	{
		this.campus = value;
	}
	//
	
	public Store getStore()
	{
		return store;
	}
	
	public void setStore(Store value)
	{
		this.store = value;
	}
	//
	
	public Item getItem()
	{
		return item;
	}
	
	public void setItem(Item value)
	{
		this.item = value;
	}
	//
	public Brand getBrand()
	{
		return brand;
	}
	
	public void setBrand(Brand value)
	{
		this.brand = value;
	}
	//
	
	public Product getProduct()
	{
		return product;
	}
	
	public void setProduct(Product value)
	{
		this.product = value;
	}
	//
	
	public Search getSearch()
	{
		return search;
	}
	
	public void setSearch(Search value)
	{
		this.search = value;
	}
	//
	
	public MyCampusEnvironment getMyCampusEnvironment()
	{
		return myCampusEnvironment;
	}
	
	public void setMyCampusEnvironment(MyCampusEnvironment value)
	{
		this.myCampusEnvironment = value;
	}
	
	public ArrayList<Message> getMessages()
	{
		if(messages == null)
		{
			messages = new ArrayList<Message>();
		}
		return messages;
	}
}
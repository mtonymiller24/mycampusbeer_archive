package bean;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

//<xsd:complexType name="Store">
//<xsd:sequence>
//<xsd:element name="id" type="xsd:int" />
//<xsd:element name="campusId" type="xsd:int" />
//<xsd:element name="name" type="xsd:string" />
//<xsd:element name="address" type="xsd:string" />
//<xsd:element name="city" type="xsd:string" />
//<xsd:element name="zip" type="xsd:int" />
//<xsd:element name="item" type="Item" minOccurs="0" maxOccurs="unbounded" />
//</xsd:sequence>
//</xsd:complexType>

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Store", propOrder = { "id", "campusId", "name", "city", "address", "phone", "zip", "item" })
public class Store
{
	protected int id;
	protected int campusId;
	protected String name;
	protected String city;
	protected String address;
	protected String phone;
	protected int zip;
	protected List<Item> item;
	
	public int getId()
	{
		return id;
	}
	
	public void setId(int value)
	{
		this.id = value;
	}
	//
	
	public int getCampusId()
	{
		return campusId;
	}
	
	public void setCampusId(int value)
	{
		this.campusId = value;
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
	
	public String getCity()
	{
		return city;
	}
	
	public void setCity(String value)
	{
		this.city = value;
	}
	//
	
	public String getAddress()
	{
		return address;
	}
	
	public void setAddress(String value)
	{
		this.address = value;
	}
	//
	
	public String getPhone()
	{
		return phone;
	}
	
	public void setPhone(String value)
	{
		this.phone = value;
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
	
	public List<Item> getItem()
	{
		if (item == null)
		{
			item = new ArrayList<Item>();
		}
		return this.item;
	}
}
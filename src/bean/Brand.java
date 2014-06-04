package bean;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

//<xsd:complexType name="Brand">
//<xsd:sequence>
//	  <xsd:element name="id" type="xsd:int" minOccurs="0" />
//	  <xsd:element name="name" type="xsd:string" minOccurs="0" />
//	  <xsd:element name="type" type="xsd:string" minOccurs="0" />
//	  <xsd:element name="product" type="Product" minOccurs="0" maxOccurs="unbounded" />
//</xsd:sequence>
//</xsd:complexType>

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Brand", propOrder = { "id", "name", "type", "product" })
public class Brand
{
	protected int id;
	protected String name;
	protected String type;
	protected ArrayList<Product> product;
	
	public int getId()
	{
		return id;
	}
	
	public void setId(int value)
	{
		this.id = value;
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
	
	public String getType()
	{
		return type;
	}
	
	public void setType(String value)
	{
		this.type = value;
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
}
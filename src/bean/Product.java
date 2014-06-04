package bean;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

//<xsd:complexType name="Product">
//<xsd:sequence>
//	<xsd:element name="id" type="xsd:string" minOccurs="0" />
//	<xsd:element name="brandId" type="xsd:string" minOccurs="0" />
//	<xsd:element name="name" type="xsd:string" minOccurs="0" />
//	<xsd:element name="type" type="xsd:string" minOccurs="0" />
//</xsd:sequence>
//</xsd:complexType>

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Product", propOrder = { "id", "brandId", "brandName", "name", "type", "popular"})
public class Product
{
	protected int id;
	protected int brandId;
	protected String brandName;
	protected String name;
	protected String type;
	protected boolean popular;

	
	public int getId()
	{
		return id;
	}
	
	public void setId(int value)
	{
		this.id = value;
	}
	//
	
	public int getBrandId()
	{
		return brandId;
	}
	
	public void setBrandId(int value)
	{
		this.brandId = value;
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

	public String getBrandName()
	{
		return brandName;
	}
	
	public void setBrandName(String value)
	{
		this.brandName = value;
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
	
	public boolean isPopular()
	{
		return popular;
	}
	
	public void setPopular(boolean value)
	{
		this.popular = value;
	}
	
}
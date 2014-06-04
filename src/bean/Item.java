package bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;



//<xsd:complexType name="Item">
//<xsd:sequence>
//	<xsd:element name="id" type="xsd:int" />
//	<xsd:element name="storeId" type="xsd:int" />
//	<xsd:element name="price" type="xsd:int" />
//	<xsd:element name="quantity" type="xsd:int" />
//	<xsd:element name="name" type="xsd:string" />
//	<xsd:element name="brand" type="xsd:string" />
//	<xsd:element name="type" type="xsd:string" />
//	<xsd:element name="container" type="xsd:string" />
//	<xsd:element name="editedTimestamp" type="xsd:string" />
//	<xsd:element name="flagged" type="xsd:boolean" />
//	<xsd:element name="special" type="xsd:boolean" />
//	<xsd:element name="specialStart" type="xsd:string" />
//	<xsd:element name="specialStop" type="xsd:string" />
//</xsd:sequence>
//</xsd:complexType>

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Item", propOrder = { "id", "storeId", "campusId", "productId", "price", "quantity", "priceDisplay", "storeName", "name", "brand",
		"type", "container", "editedTimestamp", "flagged", "popular", "outOfDate", "staticPrice", "special", "specialStart", "specialStop", "specialPrice",
		"specialPriceDisplay", "submitterId"})
public class Item
{
	protected int id;
	protected int storeId;
	protected int campusId;
	protected int productId;
	protected int price;
	protected int quantity;
	protected String priceDisplay;
	protected String storeName;
	protected String name;
	protected String brand;
	protected String type;
	protected String container;
	protected String editedTimestamp;
	protected boolean flagged;
	protected boolean popular;
	protected boolean outOfDate;
	protected boolean staticPrice;
	protected boolean special;
	protected String specialStart;
	protected String specialStop;
	protected int specialPrice;
	protected String specialPriceDisplay;
	protected String submitterId;
	
	
	public int getId()
	{
		return id;
	}
	
	public void setId(int value)
	{
		this.id = value;
	}
	//
	
	public int getStoreId()
	{
		return storeId;
	}
	
	public void setStoreId(int value)
	{
		this.storeId = value;
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
	
	public int getProductId()
	{
		return productId;
	}
	
	public void setProductId(int value)
	{
		this.productId = value;
	}
	//
	
	public int getPrice()
	{
		return price;
	}
	
	public void setPrice(int value)
	{
		this.price = value;
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
	
	public String getBrand()
	{
		return brand;
	}
	
	public void setBrand(String value)
	{
		this.brand = value;
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
	
	public String getContainer()
	{
		return container;
	}
	
	public void setContainer(String value)
	{
		this.container = value;
	}
	//
	
	public String getEditedTimestamp()
	{
		return editedTimestamp;
	}
	
	public void setEditedTimestamp(String value)
	{
		this.editedTimestamp = value;
	}
	//
	
	public boolean isFlagged()
	{
		return flagged;
	}
	
	public void setFlagged(boolean value)
	{
		this.flagged = value;
	}
	//
	
	public boolean isSpecial()
	{
		return special;
	}
	
	public void setSpecial(boolean value)
	{
		this.special = value;
	}
	//
	
	public String getSpecialStart()
	{
		return specialStart;
	}
	
	public void setSpecialStart(String value)
	{
		this.specialStart = value;
	}
	//
	
	public String getSpecialStop()
	{
		return specialStop;
	}
	
	public void setSpecialStop(String value)
	{
		this.specialStop = value;
	}
	//
	
	public int getSpecialPrice()
	{
		return specialPrice;
	}
	
	public void setSpecialPrice(int value)
	{
		this.specialPrice = value;
	}
	//
	
	public String getSubmitterId()
	{
		return submitterId;
	}
	
	public void setSubmitterId(String value)
	{
		this.submitterId = value;
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
	//

	public boolean isOutOfDate()
	{
		return outOfDate;
	}
	
	public void setOutOfDate(boolean value)
	{
		this.outOfDate = value;
	}
	//
	
	public boolean isStaticPrice()
	{
		return staticPrice;
	}
	
	public void setStaticPrice(boolean value)
	{
		this.staticPrice = value;
	}
	//
	
	public int getQuantity()
	{
		return quantity;
	}
	
	public void setQuantity(int value)
	{
		this.quantity = value;
	}
	//
	
	public String getPriceDisplay()
	{
		return priceDisplay;
	}
	
	public void setPriceDisplay(String value)
	{
		this.priceDisplay = value;
	}
	//
	
	public String getSpecialPriceDisplay()
	{
		return specialPriceDisplay;
	}
	
	public void setSpecialPriceDisplay(String value)
	{
		this.specialPriceDisplay = value;
	}
	//
	
	public String getStoreName()
	{
		return storeName;
	}
	
	public void setStoreName(String value)
	{
		this.storeName = value;
	}
	//
}
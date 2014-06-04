package bean;

import java.util.ArrayList;


//<xsd:complexType name="Brand">
//<xsd:sequence>
//	  <xsd:element name="id" type="xsd:int" minOccurs="0" />
//	  <xsd:element name="name" type="xsd:string" minOccurs="0" />
//	  <xsd:element name="type" type="xsd:string" minOccurs="0" />
//	  <xsd:element name="product" type="Product" minOccurs="0" maxOccurs="unbounded" />
//</xsd:sequence>
//</xsd:complexType>

public class Search
{
	protected int campusId;
	protected int storeId;
	protected int brandId;
	protected int productId;
	protected boolean storeSearch;
	protected boolean brandSearch;
	protected boolean productSearch;
	protected boolean itemSearch;
	protected boolean beer;
	protected boolean wine;
	protected boolean liquor;
	protected boolean champagne;
	protected boolean other;
	protected boolean specialOnly;
	protected String searchBy;
	protected ArrayList<String> searchStrings;
	//
	protected int maxPrice;
	protected int minPrice;
	protected ArrayList<String> containerStrings;
	protected ArrayList<String> quantityStrings;
	protected ArrayList<String> typeStrings;
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
	public int getStoreId()
	{
		return storeId;
	}
	
	public void setStoreId(int value)
	{
		this.storeId = value;
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
	public int getProductId()
	{
		return productId;
	}
	
	public void setProductId(int value)
	{
		this.productId = value;
	}
	//
	
	public boolean isStoreSearch()
	{
		return storeSearch;
	}
	
	public void setStoreSearch(boolean value)
	{
		this.storeSearch = value;
	}
	//
	
	public boolean isBrandSearch()
	{
		return brandSearch;
	}
	
	public void setBrandSearch(boolean value)
	{
		this.brandSearch = value;
	}
	//
		
	public boolean isProductSearch()
	{
		return productSearch;
	}
	
	public void setProductSearch(boolean value)
	{
		this.productSearch = value;
	}
	//
	
	public boolean isItemSearch()
	{
		return itemSearch;
	}
	
	public void setItemSearch(boolean value)
	{
		this.itemSearch = value;
	}
	//
	
	public String getSearchBy()
	{
		return searchBy;
	}
	
	public void setSearchBy(String value)
	{
		this.searchBy = value;
	}
	//
	
	public boolean isBeer()
	{
		return beer;
	}
	
	public void setBeer(boolean value)
	{
		this.beer = value;
	}
	//
	
	public boolean isChampagne()
	{
		return champagne;
	}
	
	public void setChampagne(boolean value)
	{
		this.champagne = value;
	}
	//
	
	public boolean isWine()
	{
		return wine;
	}
	
	public void setWine(boolean value)
	{
		this.wine = value;
	}
	//
	
	public boolean isLiquor()
	{
		return liquor;
	}
	
	public void setLiquor(boolean value)
	{
		this.liquor = value;
	}
	//
	
	public boolean isOther()
	{
		return other;
	}
	
	public void setOther(boolean value)
	{
		this.other = value;
	}
	//
	
	public ArrayList<String> getSearchStrings()
	{
		if (searchStrings == null)
		{
			searchStrings = new ArrayList<String>();
		}
		return searchStrings;
	}
		
	public ArrayList<String> getContainerStrings()
	{
		if (containerStrings == null)
		{
			containerStrings = new ArrayList<String>();
		}
		return containerStrings;
	}
	
	public ArrayList<String> getQuantityStrings()
	{
		if (quantityStrings == null)
		{
			quantityStrings = new ArrayList<String>();
		}
		return quantityStrings;
	}
		
	public ArrayList<String> getTypeStrings()
	{
		if (typeStrings == null)
		{
			typeStrings = new ArrayList<String>();
		}
		return typeStrings;
	}
		
	public int getMinPrice()
	{
		return minPrice;
	}
	
	public void setMinPrice(int value)
	{
		this.minPrice = value;
	}
	//
	
	public int getMaxPrice()
	{
		return maxPrice;
	}
	
	public void setMaxPrice(int value)
	{
		this.maxPrice = value;
	}
	//
	
	public boolean isSpecialOnly()
	{
		return specialOnly;
	}
	
	public void setSpecialOnly(boolean value)
	{
		this.specialOnly = value;
	}
	//
}
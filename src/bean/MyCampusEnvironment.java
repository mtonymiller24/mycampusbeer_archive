package bean;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import manage.JaxbServices;

//<xsd:complexType name="Brand">
//<xsd:sequence>
//	  <xsd:element name="id" type="xsd:int" minOccurs="0" />
//	  <xsd:element name="name" type="xsd:string" minOccurs="0" />
//	  <xsd:element name="type" type="xsd:string" minOccurs="0" />
//	  <xsd:element name="product" type="Product" minOccurs="0" maxOccurs="unbounded" />
//</xsd:sequence>
//</xsd:complexType>

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MyCampusEnvionment", propOrder = { "serverName", "secureServerName", "cssWebLocation", "fileDiskLocation",
		"fileWebLocation", "xslDiskLocation", "templateLocation", "database", "dbuser", "dbpassword", "isLocal" })
public class MyCampusEnvironment
{
	protected String serverName;
	protected String secureServerName;
	protected String cssWebLocation;
	protected String fileDiskLocation;
	protected String fileWebLocation;
	protected String xslDiskLocation;
	protected String templateLocation;
	protected String database;
	protected String dbuser;
	protected String dbpassword;
	//
	protected boolean isLocal = true;
	
	public MyCampusEnvironment()
	{
		if(isLocal)
		{
			serverName = "http://localhost:8080/mycampusbeer/";
			secureServerName = "http://localhost:8080/mycampusbeer/";
			cssWebLocation = "WebContent/css/";
			fileDiskLocation = "c:/workspace/mycampusbeer/WebContent/images/";
			fileWebLocation = "images/";
			xslDiskLocation = "c:/workspace/mycampusbeer/WebContent/css/xsl/";
			templateLocation = "c:/workspace/mycampusbeer/WebContent/css/html/template.html";
			database = "jdbc:postgresql://localhost:5432/mycampusbeer";
			dbuser = "postgres";
			dbpassword = "OwsUHmk8";
		}
		else
		{
			serverName = "http://mycampusbeer.com/";
			secureServerName = "https://mycampusbeer.com/";
			cssWebLocation = "css/";
			fileDiskLocation = "/www/mycampusbeer/images/";
			fileWebLocation = "images/";
			xslDiskLocation = "/www/mycampusbeer/xsl/";
			templateLocation = "/www/mycampusbeer/html/template.html";
			database = "";
			dbuser = "";
			dbpassword = "";
		}
	}
	
	public String getServerName()
	{
		return serverName;
	}
	//
	
	public String getDatabase()
	{
		return database;
	}
	//
	
	public String getDatabaseUser()
	{
		return dbuser;
	}
	//
	
	public String getDatabasePassword()
	{
		return dbpassword;
	}
	//
	
	public String getCssWebLocation()
	{
		return cssWebLocation;
	}
	//
	
	public String getFileDiskLocation()
	{
		return fileDiskLocation;
	}
	//
	
	public String getFileWebLocation()
	{
		return fileWebLocation;
	}
	//
	
	public String getXslDiskLocation()
	{
		return xslDiskLocation;
	}
	//
	
	public String getTemplateLocation()
	{
		return templateLocation;
	}
	//
}
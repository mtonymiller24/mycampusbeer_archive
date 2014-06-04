package bean;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

//<xsd:complexType name="User">
//<xsd:sequence>
//	<xsd:element name="userId" type="xsd:string" minOccurs="0" />
//	<xsd:element name="admin" type="xsd:boolean" minOccurs="0" />
//	<xsd:element name="submitter" type="xsd:boolean" minOccurs="0" />
//	<xsd:element name="loggedIn" type="xsd:boolean" minOccurs="0" />
//</xsd:sequence>
//</xsd:complexType>

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Submitter", propOrder = { "userId", "password", "authenticated", "authenticationCode", "admin", "loggedIn", "submissions", "rank"})
public class Submitter
{
	protected String userId;
	protected String password;
	protected boolean authenticated;
	protected int authenticationCode;
	protected boolean admin;
	protected boolean loggedIn;
	protected int submissions;
	protected String rank;
	
	
	public String getUserId()
	{
		return userId;
	}
	
	public void setUserId(String value)
	{
		this.userId = value;
	}
	//
	
	public String getPassword()
	{
		return password;
	}
	
	public void setPassword(String value)
	{
		this.userId = value;
	}
	
	public boolean checkPassword(String value)
	{
		return this.password.equals(value);
	}
	//
	
	public boolean isAuthenticated()
	{
		return authenticated;
	}
	
	public void setAuthenticated(boolean value)
	{
		this.authenticated = value;
	}
	//

	public int getAuthenticationCode()
	{
		return authenticationCode;
	}
	
	public void setAuthenticationCode(int value)
	{
		this.authenticationCode = value;
	}
	//
	
	public boolean isAdmin()
	{
		return admin;
	}
	
	public void setAdmin(boolean value)
	{
		this.admin = value;
	}
	//
	
	public boolean isLoggedIn()
	{
		return loggedIn;
	}
	
	public void setLoggedIn(boolean value)
	{
		this.loggedIn = value;
	}
	//
	
	public int getSubmissions()
	{
		return submissions;
	}
	
	public void setSubmissions(int value)
	{
		this.submissions = value;
	}
	
	public int addSubmission()
	{
		return submissions++;
	}
	//
	
	public String getRank()
	{
		return rank;
	}
	
	public void setRank(String value)
	{
		this.rank = value;
	}
}
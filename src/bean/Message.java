package bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import manage.Messages;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Message", propOrder = { "message", "type"})
public class Message
{
	public Message()
	{}
	
	public Message(String value, String typeString)
	{
		this.message = value;
		this.type = typeString;
	}
	
	protected String message;
	protected String type;
}

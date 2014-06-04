package manage;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Node;

public class JaxbServices
{
	private String packageName;
	private JAXBContext jaxbContext = null;

	public JaxbServices(String newPackageName)
	{
		this.packageName = newPackageName;
		try
		{
			this.jaxbContext = JAXBContext.newInstance(this.packageName);
		}
		catch (Exception e)
		{
			e.printStackTrace(System.out);
		}
	}

	public void marshal(Object obj, OutputStream out) throws JAXBException, PropertyException
	{
		Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		marshaller.marshal(obj, out);
	}

	public void marshal(Object obj, Node node) throws JAXBException, PropertyException
	{
		Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		marshaller.marshal(obj, node);
	}

	public String marshal(Object obj) throws JAXBException, PropertyException
	{
		Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		StringWriter sw = new StringWriter();
		marshaller.marshal(obj, sw);
		String out = sw.toString();
		return out;
	}
	
//	public String marshal(Object obj) throws JAXBException, PropertyException
//	{
//		Marshaller marshaller = jaxbContext.createMarshaller();
//		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
//		ByteArrayOutputStream baos = new ByteArrayOutputStream();
//		BufferedOutputStream bos = new BufferedOutputStream(baos);
//		marshaller.marshal(obj, bos);
//		String out = baos.toString();
//		return out;
//	}

	public String marshal(Object obj, String xsl) throws JAXBException, PropertyException
	{
		try
		{
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			TransformerHandler identityTransformer = ((SAXTransformerFactory) TransformerFactory.newInstance()).newTransformerHandler();
			identityTransformer.setResult(new StreamResult(baos));
			StylesheetInserter inserter = new StylesheetInserter(xsl, "text/xsl");
			inserter.setContentHandler(identityTransformer);
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshaller.marshal(obj, inserter);
			String out = baos.toString();
			return out;
		}
		catch (TransformerConfigurationException e)
		{
			e.printStackTrace();
		}
		catch (TransformerFactoryConfigurationError e)
		{
			e.printStackTrace();
		}
		return "";
	}

	public Object unmarshal(InputStream in) throws JAXBException, PropertyException
	{
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		return unmarshaller.unmarshal(in);
	}

	public Object unmarshal(String in) throws JAXBException, PropertyException
	{
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		ByteArrayInputStream bais = new ByteArrayInputStream(in.getBytes());
		Object obj = unmarshaller.unmarshal(bais);
		try
		{
			bais.close();
		}
		catch (Exception e)
		{
		}
		return obj;
	}

	public Object unmarshal(Node node) throws JAXBException, PropertyException
	{
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		Object obj = unmarshaller.unmarshal(node);
		return obj;
	}
}

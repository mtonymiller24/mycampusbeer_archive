package manage;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.util.Iterator;
import java.util.List;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * @author lance
 * 
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates. To enable and disable the creation of type
 * comments go to Window>Preferences>Java>Code Generation.
 */
public class XSLTransformer
{
	public static void transform(OutputStream out, String xml, String xsl) throws TransformerException
	{
		StreamSource xml_source = new StreamSource(new StringReader(xml));
		StreamSource xsl_source = new StreamSource(new StringReader(xsl));
		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer transformer = factory.newTransformer(xsl_source);
		transformer.transform(xml_source, new StreamResult(out));
	}

	public static void transform(OutputStream out, String xml, String xsl, List parameters) throws TransformerException
	{
		StreamSource xml_source = new StreamSource(new StringReader(xml));
		StreamSource xsl_source = new StreamSource(new StringReader(xsl));
		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer transformer = factory.newTransformer(xsl_source);
		if (parameters != null)
		{
			for (Iterator iter = parameters.iterator(); iter.hasNext();)
			{
				String name = (String) iter.next();
				String value = name.substring(name.indexOf("=") + 1);
				name = name.substring(0, name.indexOf("="));
				transformer.setParameter(name, value);
			}

		}
		transformer.transform(xml_source, new StreamResult(out));
	}

	public static void transform(OutputStream out, InputStream xmlInputStream, InputStream xslInputStream) throws TransformerException
	{
		StreamSource xml_source = new StreamSource(xmlInputStream);
		StreamSource xsl_source = new StreamSource(xslInputStream);
		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer transformer = factory.newTransformer(xsl_source);
		transformer.transform(xml_source, new StreamResult(out));
	}

	public static void transform(OutputStream out, InputStream xmlInputStream, InputStream xslInputStream, List parameters) throws TransformerException
	{
		StreamSource xml_source = new StreamSource(xmlInputStream);
		StreamSource xsl_source = new StreamSource(xslInputStream);
		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer transformer = factory.newTransformer(xsl_source);
		if (parameters != null)
		{
			for (Iterator iter = parameters.iterator(); iter.hasNext();)
			{
				String name = (String) iter.next();
				String value = name.substring(name.indexOf("=") + 1);
				name = name.substring(0, name.indexOf("="));
				transformer.setParameter(name, value);
			}

		}
		transformer.transform(xml_source, new StreamResult(out));
	}

	public static void transform(OutputStream out, Reader xmlReader, Reader xslReader) throws TransformerException
	{
		StreamSource xml_source = new StreamSource(xmlReader);
		StreamSource xsl_source = new StreamSource(xslReader);
		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer transformer = factory.newTransformer(xsl_source);
		transformer.transform(xml_source, new StreamResult(out));
	}

	public static void transform(OutputStream out, Reader xmlReader, Reader xslReader, List parameters) throws TransformerException
	{
		StreamSource xml_source = new StreamSource(xmlReader);
		StreamSource xsl_source = new StreamSource(xslReader);
		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer transformer = factory.newTransformer(xsl_source);
		if (parameters != null)
		{
			for (Iterator iter = parameters.iterator(); iter.hasNext();)
			{
				String name = (String) iter.next();
				String value = name.substring(name.indexOf("=") + 1);
				name = name.substring(0, name.indexOf("="));
				transformer.setParameter(name, value);
			}

		}
		transformer.transform(xml_source, new StreamResult(out));
	}

	public static void transform(PrintWriter out, String xml, String xsl) throws TransformerException
	{
		StreamSource xml_source = new StreamSource(new StringReader(xml));
		StreamSource xsl_source = new StreamSource(new StringReader(xsl));
		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer transformer = factory.newTransformer(xsl_source);
		transformer.transform(xml_source, new StreamResult(out));
	}

	public static void transform(PrintWriter out, String xml, String xsl, List parameters) throws TransformerException
	{
		StreamSource xml_source = new StreamSource(new StringReader(xml));
		StreamSource xsl_source = new StreamSource(new StringReader(xsl));
		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer transformer = factory.newTransformer(xsl_source);
		if (parameters != null)
		{
			for (Iterator iter = parameters.iterator(); iter.hasNext();)
			{
				String name = (String) iter.next();
				String value = name.substring(name.indexOf("=") + 1);
				name = name.substring(0, name.indexOf("="));
				transformer.setParameter(name, value);
			}

		}
		transformer.transform(xml_source, new StreamResult(out));
	}

	public static void transform(PrintWriter out, InputStream xmlInputStream, InputStream xslInputStream) throws TransformerException
	{
		StreamSource xml_source = new StreamSource(xmlInputStream);
		StreamSource xsl_source = new StreamSource(xslInputStream);
		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer transformer = factory.newTransformer(xsl_source);
		transformer.transform(xml_source, new StreamResult(out));
	}

	public static void transform(PrintWriter out, InputStream xmlInputStream, InputStream xslInputStream, List parameters) throws TransformerException
	{
		StreamSource xml_source = new StreamSource(xmlInputStream);
		StreamSource xsl_source = new StreamSource(xslInputStream);
		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer transformer = factory.newTransformer(xsl_source);
		if (parameters != null)
		{
			for (Iterator iter = parameters.iterator(); iter.hasNext();)
			{
				String name = (String) iter.next();
				String value = name.substring(name.indexOf("=") + 1);
				name = name.substring(0, name.indexOf("="));
				transformer.setParameter(name, value);
			}

		}
		transformer.transform(xml_source, new StreamResult(out));
	}

	public static void transform(PrintWriter out, Reader xmlReader, Reader xslReader) throws TransformerException
	{
		StreamSource xml_source = new StreamSource(xmlReader);
		StreamSource xsl_source = new StreamSource(xslReader);
		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer transformer = factory.newTransformer(xsl_source);
		transformer.transform(xml_source, new StreamResult(out));
	}

	public static void transform(PrintWriter out, Reader xmlReader, Reader xslReader, List parameters) throws TransformerException
	{
		StreamSource xml_source = new StreamSource(xmlReader);
		StreamSource xsl_source = new StreamSource(xslReader);
		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer transformer = factory.newTransformer(xsl_source);
		if (parameters != null)
		{
			for (Iterator iter = parameters.iterator(); iter.hasNext();)
			{
				String name = (String) iter.next();
				String value = name.substring(name.indexOf("=") + 1);
				name = name.substring(0, name.indexOf("="));
				transformer.setParameter(name, value);
			}

		}
		transformer.transform(xml_source, new StreamResult(out));
	}

	public static String transform(String xml, String xsl) throws TransformerException
	{
		StreamSource xml_source = new StreamSource(new StringReader(xml));
		StreamSource xsl_source = new StreamSource(new StringReader(xsl));
		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer transformer = factory.newTransformer(xsl_source);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		transformer.transform(xml_source, new StreamResult(out));
		return out.toString();
	}

	public static String transform(String xml, String xsl, List parameters) throws TransformerException
	{
		StreamSource xml_source = new StreamSource(new StringReader(xml));
		StreamSource xsl_source = new StreamSource(new StringReader(xsl));
		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer transformer = factory.newTransformer(xsl_source);
		if (parameters != null)
		{
			for (Iterator iter = parameters.iterator(); iter.hasNext();)
			{
				String name = (String) iter.next();
				String value = name.substring(name.indexOf("=") + 1);
				name = name.substring(0, name.indexOf("="));
				transformer.setParameter(name, value);
			}

		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		transformer.transform(xml_source, new StreamResult(out));
		return out.toString();
	}

	public static String transform(InputStream xmlInputStream, InputStream xslInputStream) throws TransformerException
	{
		StreamSource xml_source = new StreamSource(xmlInputStream);
		StreamSource xsl_source = new StreamSource(xslInputStream);
		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer transformer = factory.newTransformer(xsl_source);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		transformer.transform(xml_source, new StreamResult(out));
		return out.toString();
	}

	public static String transform(InputStream xmlInputStream, InputStream xslInputStream, List parameters) throws TransformerException
	{
		StreamSource xml_source = new StreamSource(xmlInputStream);
		StreamSource xsl_source = new StreamSource(xslInputStream);
		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer transformer = factory.newTransformer(xsl_source);
		if (parameters != null)
		{
			for (Iterator iter = parameters.iterator(); iter.hasNext();)
			{
				String name = (String) iter.next();
				String value = name.substring(name.indexOf("=") + 1);
				name = name.substring(0, name.indexOf("="));
				transformer.setParameter(name, value);
			}

		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		transformer.transform(xml_source, new StreamResult(out));
		return out.toString();
	}

	public static String transform(Reader xmlReader, Reader xslReader) throws TransformerException
	{
		StreamSource xml_source = new StreamSource(xmlReader);
		StreamSource xsl_source = new StreamSource(xslReader);
		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer transformer = factory.newTransformer(xsl_source);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		transformer.transform(xml_source, new StreamResult(out));
		return out.toString();
	}

	public static String transform(Reader xmlReader, Reader xslReader, List parameters) throws TransformerException
	{
		StreamSource xml_source = new StreamSource(xmlReader);
		StreamSource xsl_source = new StreamSource(xslReader);
		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer transformer = factory.newTransformer(xsl_source);
		if (parameters != null)
		{
			for (Iterator iter = parameters.iterator(); iter.hasNext();)
			{
				String name = (String) iter.next();
				String value = name.substring(name.indexOf("=") + 1);
				name = name.substring(0, name.indexOf("="));
				transformer.setParameter(name, value);
			}

		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		transformer.transform(xml_source, new StreamResult(out));
		return out.toString();
	}
}
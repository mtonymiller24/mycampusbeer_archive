package manage;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

/**
 * @author tony
 * 
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates. To enable and disable the creation of type
 * comments go to Window>Preferences>Java>Code Generation.
 */
public class ParameterValues
{
	private Map mapNames = new HashMap();

	private Map mapFiles = new HashMap();

	private boolean multiPart = false;

	private List urlParts = new ArrayList();

	private String servletName = "";

	private String type = "";

	private String requestUrl = null;

	private static final String regexp = "[\\x00-\\x09[\\x0b-\\x0c][\\x0e-\\x1f][\\x7f-\\xff]]";

	public void setParameters(Map parameters)
	{
		mapNames.putAll(parameters);
	}

	public String getServletName()
	{
		return servletName;
	}

	public List getUrlParts()
	{
		return urlParts;
	}

	public String getType()
	{
		String type = "";
		try
		{
			int dotIndex = requestUrl.lastIndexOf(".");
			if (dotIndex > -1)
			{
				type = requestUrl.substring(dotIndex);
			}
		}
		catch (Exception e)
		{
			type = "";
		}
		return type;
	}

	public void setParameters(HttpServletRequest req)
	{
		requestUrl = req.getRequestURL().toString();
		try
		{
			servletName = req.getServletPath().substring(1);
		}
		catch (Exception e)
		{
			servletName = "";
		}

		// System.out.println("servletName=" + servletName);
		try
		{
			String url = requestUrl.substring(9);
			// System.out.println(url);
			StringTokenizer st = new StringTokenizer(url, "/");
			boolean foundServlet = false;
			for (; st.hasMoreTokens();)
			{
				String token = st.nextToken();
				int equalLocation = token.indexOf('=');
				if (equalLocation > -1)
				{
					String name = token.substring(0, equalLocation);
					// System.out.print(name + " '");
					String value = token.substring(equalLocation + 1);
					// System.out.println(value + "'");
					String[] values = new String[1];
					values[0] = value;
					mapNames.put(name, values);
				}
				else
				{
					if (token.equals(servletName))
					{
						foundServlet = true;
					}
					else if (foundServlet)
					{
						urlParts.add(token);
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace(System.out);
		}
		//
//		if (FileUpload.isMultipartContent(req))
//		{
//			this.multiPart = true;
//			DiskFileUpload upload = new DiskFileUpload();
//			try
//			{
//				List formItems = upload.parseRequest(req);
//				for (Iterator iter = formItems.iterator(); iter.hasNext();)
//				{
//					FileItem fileItem = (FileItem) iter.next();
//					if (fileItem.isFormField())
//					{
//						// System.out.println("1");
//						// places the value in a String[] for compatibility with
//						// the rest of the class
//						if (mapNames.containsKey(fileItem.getFieldName()))
//						{
//							// System.out.println("2:" +
//							// fileItem.getFieldName());
//							String[] strAry = (String[]) mapNames.get(fileItem.getFieldName());
//							//
//							// System.out.print("strAry::");
//							// for(int index=0;index<strAry.length;index++)
//							// {
//							// System.out.print(strAry[index]);
//							// }
//							// System.out.println("");
//							//
//							String[] strNew = new String[strAry.length + 1];
//							System.arraycopy(strAry, 0, strNew, 0, strAry.length);
//							strNew[strNew.length - 1] = fileItem.getString();
//							//
//							// System.out.print("strNew::");
//							// for(int index=0;index<strNew.length;index++)
//							// {
//							// System.out.print(strNew[index]);
//							// }
//							// System.out.println("");
//							//
//							mapNames.put(fileItem.getFieldName(), strNew);
//						}
//						else
//						{
//							// System.out.println("3:" +
//							// fileItem.getFieldName());
//							mapNames.put(fileItem.getFieldName(), new String[] { fileItem.getString() });
//						}
//
//					}
//					else
//					{
//						if (mapFiles.containsKey(fileItem.getFieldName()))
//						{
//							FileItem[] fileItemAry = (FileItem[]) mapFiles.get(fileItem.getFieldName());
//							FileItem[] fileItemNew = new FileItem[fileItemAry.length + 1];
//							System.arraycopy(fileItemAry, 0, fileItemNew, 0, fileItemAry.length);
//							fileItemNew[fileItemNew.length - 1] = fileItem;
//							mapFiles.put(fileItem.getFieldName(), fileItemNew);
//						}
//						else
//						{
//							// System.out.println("4:" +
//							// fileItem.getFieldName());
//							mapFiles.put(fileItem.getFieldName(), new FileItem[] { fileItem });
//						}
//					}
//				}
//			}
//			catch (FileUploadException e)
//			{
//				e.printStackTrace();
//			}
//		}
//		else
//		{
//			Enumeration names = req.getParameterNames();
//			for (; names.hasMoreElements();)
//			{
//				String name = names.nextElement().toString();
//				String[] paramaters = req.getParameterValues(name);
//				mapNames.put(name, paramaters);
//			}
//		}
	}

	public void addParameter(String name, String value)
	{
		if (value == null)
		{
		}
		else
		{
			String[] strArrayValue = new String[1];
			strArrayValue[0] = value;
			mapNames.put(name, strArrayValue);
		}
	}

	public void addParamaters(String name, String[] value)
	{
		mapNames.put(name, value);
	}

	public String[] getParameters(String name)
	{
		String[] values = (String[]) mapNames.get(name);

		// if (this.multiPart && values != null && values.length > 0)
		if (values != null && values.length > 0)
		{
			String[] tempValues = new String[values.length];
			for (int index = 0; index < values.length; index++)
			{
				try
				{
					if (this.multiPart)
					{
						tempValues[index] = URLDecoder.decode(values[index], "UTF-8").replaceAll(regexp, "");
					}
					else
					{
						tempValues[index] = values[index].replaceAll(regexp, "");

					}
				}
				catch (Exception e)
				{
					tempValues[index] = "";
				}
			}
			values = tempValues;
		}

		return values;
	}

	public String getParameter(String name)
	{
		String[] values = (String[]) mapNames.get(name);
		if (values == null)
		{
			return null;
		}
		else if (values.length == 0)
		{
			return null;
		}
		else
		{
			String value = values[0];
			try
			{
				if (this.multiPart)
				{
					value = URLDecoder.decode(value, "UTF-8").replaceAll(regexp, "");
				}
				else
				{
					value = value.replaceAll(regexp, "");
				}
			}
			catch (Exception e)
			{
				value = "";
				System.out.println("decode problem for:" + name);
			}
			return value;
		}
	}

	public String getAction()
	{
		String action = getParameter("ACTION");
		if (action == null)
		{
			action = "";
		}
		return action;
	}

//	public FileItem[] getFiles(String fieldName)
//	{
//		return (FileItem[]) mapFiles.get(fieldName);
//	}
//
//	public FileItem getFile(String fieldName)
//	{
//		FileItem[] fileItems = (FileItem[]) mapFiles.get(fieldName);
//		if (fileItems == null)
//		{
//			return null;
//		}
//		else if (fileItems.length == 0)
//		{
//			return null;
//		}
//		else
//		{
//			return fileItems[0];
//		}
//	}

	public Map getParameterMap()
	{
		return this.mapNames;
	}

	public Map getFileMap()
	{
		return this.mapFiles;
	}

	public boolean isMultiPart()
	{
		return this.multiPart;
	}

	public static List getStringList(String parameterValue)
	{
		List strings = new ArrayList();
		int index = 0;
		while ((index = parameterValue.indexOf(",")) > -1)
		{
			String value = parameterValue.substring(0, index).trim();
			strings.add(value);
			parameterValue = parameterValue.substring(index + 1);
		}
		if (parameterValue.trim().length() > 0)
		{
			strings.add(parameterValue);
		}
		return strings;
	}

	public void removeParameter(String parameter)
	{
		if (mapNames.containsKey(parameter))
		{
			mapNames.remove(parameter);
		}
	}

	public static final void main(String[] args)
	{
		try
		{
			String values = "a,b,,cde,f,";
			List stringValues = ParameterValues.getStringList(values);
			System.out.println("start");
			for (int index = 0; index < stringValues.size(); index++)
			{
				System.out.println("|" + stringValues.get(index) + "|");
			}
			System.out.println("done");
		}
		catch (Exception e)
		{
			e.printStackTrace(System.out);
		}
	}
}

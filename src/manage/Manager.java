package manage;

import javax.naming.Context;
import javax.naming.InitialContext;

import factory.SQLFactory;



/**
 * @author lance
 * 
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates. To enable and disable the creation of type
 * comments go to Window>Preferences>Java>Code Generation.
 */
public class Manager
{
	/*
	public static final int SCREEN_VIEW_EVENT_LIST = 1;
	private static JaxbServices jaxbServiceRss = null;
	private static JaxbServices jaxbServiceGeneral = null;
	private static MycampusbeerEnvironment mycampusbeerEnvironment = null;
	
	private static SQLFactory sqlFactory = null;
	private static ParametersFactory parametersFactory = null;
	
	
	static
	{
		try
		{
			jaxbServiceRss = new JaxbServices("edu.uiuc.webservices.rssmanager.bean");
			jaxbServiceGeneral = new JaxbServices("edu.uiuc.webservices.rssmanager.bean:edu.uiuc.webservices.message.bean:edu.uiuc.webservices.groupmanager.bean");
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			rssManagerEnvironment = (EventCalendarEnvironment) envCtx.lookup("bean/RssManagerEnvironmentFactory");
			try
			{
				//
				rssManagerEnvironment.setEnableJavaScriptThread(Boolean.valueOf(rssManagerEnvironment.getEnableJavaScriptThreadValue()).booleanValue());
				//
				String serverName = rssManagerEnvironment.getServerName().trim();
				String secureServerName = "";
				if (serverName.startsWith("http://"))
				{
					serverName = serverName.substring(7);
				}
				else if (serverName.startsWith("https://"))
				{
					serverName = serverName.substring(8);
				}
				if (rssManagerEnvironment.getSecureServerName() != null && rssManagerEnvironment.getSecureServerName().trim().length() > 0)
				{
					secureServerName = rssManagerEnvironment.getSecureServerName();
				}
				else
				{
					secureServerName = "https://" + serverName;
				}
				serverName = "http://" + serverName;
				rssManagerEnvironment.setServerName(serverName);
				rssManagerEnvironment.setSecureServerName(secureServerName);
			}
			catch (Exception e)
			{
				System.out.println("Problem while setting up environment");
				e.printStackTrace(System.out);
			}
			try
			{
				PropertyConfigurator.configure(rssManagerEnvironment.getLogPropertyFile());
				logger = Logger.getLogger("rssmanager.RssManager");
			}
			catch (Exception e)
			{
				e.printStackTrace(System.out);
				System.out.println("Log4J not set");
			}
			//
			//
			initTimes();
			//
			makeClearTemp();
			//        
		}
		catch (Exception e)
		{
			e.printStackTrace(System.out);
		}
	}

	public static synchronized JaxbServices getJaxbServicesGeneral()
	{
		return jaxbServiceGeneral;
	}

	public static synchronized JaxbServices getJaxbServicesRss()
	{
		return jaxbServiceRss;
	}

	public static synchronized EventCalendarEnvironment getRssManagerEnvironment()
	{
		return Manager.rssManagerEnvironment;
	}

	public static synchronized SQLFactory getSQLFactory()
	{
		if (sqlFactory == null)
		{
			try
			{
				sqlFactory = new SQLFactory();
				sqlFactory.setLogger(logger);
				sqlFactory.setDataSourceName(rssManagerEnvironment.getDataSourceName());
			}
			catch (Exception e)
			{
				e.printStackTrace(System.out);
			}
		}
		return Manager.sqlFactory;
	}

	

	public static String getTime()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		return sdf.format(new Date());
	}
	*/
}

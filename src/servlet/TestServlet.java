package servlet;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Campus;
import bean.MyCampusEnvironment;
import bean.WorkingStorage;
import process.AdminProcess;
import manage.ConstantMaps;
import manage.Constants;
import manage.Manager;
import manage.ParameterValues;
import manage.XSLTransformer;


/**
 * @version 1.0
 * @author tony
 */
public class TestServlet extends HttpServlet
{
	//
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	{
		doPost(req, resp);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
	{
		try
		{
			ParameterValues parameterValues = new ParameterValues();
			parameterValues.setParameters(req.getParameterMap());
			String x = parameterValues.getParameter("ACTION");
			//
			resp.setContentType("text/html");
			PrintWriter out = resp.getWriter();
			//
			String template = readTemplate("C:\\workspace\\mycampusbeer\\WebContent\\css\\test.html");
			out.println(template);
		}
		catch (Exception e)
		{
			System.err.println(e.getMessage());
			return;
		}
	}

	private static void send404(HttpServletResponse resp)
	{
		try
		{
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private static String readTemplate(String templateLocation)
	{
		String template = "";
		try
		{
		    FileInputStream fstream = new FileInputStream(templateLocation);
		    DataInputStream in = new DataInputStream(fstream);
		    BufferedReader br = new BufferedReader(new InputStreamReader(in));
		    String strLine;
		    while ((strLine = br.readLine()) != null)
		    { 
		    	template += strLine;
		    }   
		    in.close();
	    }
		catch (Exception e)
		{
			System.err.println("Template Read Error: " + e);
	    }
		return template;
	}
	
	private static String readXsl(String xslLocation, int screenId)
	{
		String template = "";
		try
		{
			String xslFilePath = "C:\\workspace\\mycampusbeer\\WebContent\\css\\xsl\\search_results.xsl";
			//xslFilePath = xslLocation + map.get(screenId);
			FileInputStream fstream = new FileInputStream(xslFilePath);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			while ((strLine = br.readLine()) != null)
			{ 
				template += strLine;
			}   
			in.close();
		}
		catch (Exception e)
		{
			System.err.println("XSL Read Error: " + e);
		}
		return template;
	}
	
	private static String buildHeader(Campus campus)
	{
		List campuses = new ArrayList();
		campuses.add(campus);
		Map stateMap = getStateMap();
		List stateIdList = new ArrayList();
		stateIdList.addAll(stateMap.keySet());
		//
		String campusLocation = "Campus Location &#xA0; &gt;&gt; &#xA0; ";
		//
		campusLocation += "<select name='STATE'>";
		for(Iterator iter = stateIdList.iterator(); iter.hasNext();)
		{
			String stateId = (String)iter.next();
			if(campus != null && campus.getStateId().equals(stateId))
			{
				campusLocation += "<option value='" + stateId +"' selected='selected'>";
			}
			else
			{
				campusLocation += "<option value='" + stateId +"'>";
			} 
			campusLocation += stateMap.get(stateId) + "</option>";
		}
		campusLocation += "</select>";
		//
		campusLocation += "&#xA0; &gt;&gt; &#xA0;";
		campusLocation += "<select name='CAMPUS_ID'>";
		for(Iterator iter = campuses.iterator(); iter.hasNext();)
		{
			Campus c = (Campus)iter.next();
			if(campus != null && campus.getId() == c.getId())
			{
				campusLocation += "<option value='" + campus.getId() +"' selected='selected'>";
			}
			else
			{
				campusLocation += "<option value='" + campus.getId() +"'>";
			}
			campusLocation += campus.getName() + "</option>";
		}	
		campusLocation += "</select>";
		//
		return campusLocation;
	}
	
	private static Map getStateMap()
	{
		Map stateMap = new HashMap();
		//
		//	STATE MAP
		// 1
		stateMap.put("AL", "Alabama");
		stateMap.put("AK", "Alaska");
		stateMap.put("AZ", "Arizona");
		stateMap.put("AR", "Arkansas");
		stateMap.put("CA", "California");
		// 2
		stateMap.put("CO", "Colorado");		
		stateMap.put("CT", "Conneticut");
		stateMap.put("DE", "Delaware");
		stateMap.put("FL", "Florida");
		stateMap.put("GA", "Georgia");
		// 3
		stateMap.put("HI", "Hawaii");
		stateMap.put("ID", "Idaho");
		stateMap.put("IL", "Illinois");
		stateMap.put("IN", "Indiana");
		stateMap.put("IA", "Iowa");
		// 4
		stateMap.put("KS", "Kansas");
		stateMap.put("KY", "Kentucky");
		stateMap.put("LA", "Louisiana");
		stateMap.put("ME", "Maine");
		stateMap.put("MD", "Maryland");
		// 5
		stateMap.put("MA", "Massachusettes");
		stateMap.put("MI", "Michigan");
		stateMap.put("MN", "Minnesota");
		stateMap.put("MS", "Mississippi");
		stateMap.put("MO", "Missouri");
		// 6
		stateMap.put("MT", "Montana");
		stateMap.put("NE", "Nebraska");
		stateMap.put("NV", "Nevada");
		stateMap.put("NH", "New Hempshire");
		stateMap.put("NJ", "New Jersey");
		// 7
		stateMap.put("NM", "New Mexico");
		stateMap.put("NY", "New York");
		stateMap.put("NC", "North Carolina");
		stateMap.put("ND", "North Dakota");
		stateMap.put("OH", "Ohio");
		// 8
		stateMap.put("OK", "Oklahoma");
		stateMap.put("OR", "Oregon");
		stateMap.put("PA", "Pennsylvania");
		stateMap.put("RI", "Rhode Island");
		stateMap.put("SC", "South Carolina");
		// 9
		stateMap.put("SD", "South Dakota");
		stateMap.put("TN", "Tennessee");
		stateMap.put("TX", "Texas");
		stateMap.put("UT", "Utah");
		stateMap.put("VT", "Vermont");
		// 10
		stateMap.put("VA", "Virginia");
		stateMap.put("WA", "Washington");
		stateMap.put("WV", "West Virginia");
		stateMap.put("WI", "Wisconsin");
		stateMap.put("WY", "Wyoming");
		//
		return stateMap;
	}
}

package servlet;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Campus;
import bean.MyCampusEnvironment;
import bean.Submitter;
import bean.WorkingStorage;
import process.AdminProcess;
import manage.ParameterValues;
import manage.XSLTransformer;


/**
 * @version 1.0
 * @author tony
 */
public class CampusServlet extends HttpServlet
{
	private static AdminProcess adminProcess = null;
	//
	static
	{
		adminProcess = new AdminProcess();
	}

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
			//
			WorkingStorage workingStorage = (WorkingStorage)req.getSession().getAttribute("workingStorage");
			if(workingStorage == null)
			{
				workingStorage = new WorkingStorage();
				workingStorage.setScreenId(-1);
				workingStorage.setMyCampusEnvironment(new MyCampusEnvironment());
			}
			//
			adminProcess.doAction(parameterValues, workingStorage);
			String xml = adminProcess.getXML(workingStorage);
			//
			req.getSession().setAttribute("workingStorage", workingStorage);
			resp.setContentType("text/html");
			PrintWriter out = resp.getWriter();
			//
			String template = readTemplate(workingStorage.getMyCampusEnvironment().getTemplateLocation());
			//
			template = template.replaceAll("\\{\\$NAME\\}", "MyCampusBeer.com - " + workingStorage.getCampus().getState() + " - " + workingStorage.getCampus().getName());
			template = template.replaceAll("\\{\\$CAMPUS_LOCATION\\}", adminProcess.buildHeader(workingStorage.getCampus()));
			template = template.replaceAll("\\{\\$LOGIN\\}", buildLogin(workingStorage.getSubmitter()));
			//
			String xsl = readXsl(workingStorage.getMyCampusEnvironment().getXslDiskLocation() + workingStorage.getScreenName());
			String content = "";
			try
			{
				content = XSLTransformer.transform(xml, xsl);
			}
			catch (Exception e)
			{
				content = e.getMessage();
			}
			template = template.replace("{$CONTENT}", content);
			//
			System.out.println(template);
			out.println(template);
		}
		catch (Exception e)
		{
			System.err.println(e);
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
	
	private static String readXsl(String xslLocation)
	{
		String xsl = "";
		try
		{
			String xslFilePath = xslLocation;
			FileInputStream fstream = new FileInputStream(xslFilePath);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			while ((strLine = br.readLine()) != null)
			{ 
				xsl += strLine;
			}   
			in.close();
		}
		catch (Exception e)
		{
			System.err.println("XSL Read Error: " + e);
		}
		return xsl;
	}
	
	private static String buildLogin(Submitter submitter)
	{
		String loginStr = "";
		if(submitter == null || !submitter.isLoggedIn())
		{
			loginStr += "<br/>";
			loginStr += "<a style='cursor: pointer;' onClick=\"javascript:hide_show(document.getElementById('login'));\">Login</a>";
		}
		else
		{
			loginStr += "<div>" + submitter.getUserId() + "</div>";
			loginStr += "<a style='cursor: pointer;' href=\"javascript:document.search_form['ACTION'].value='LOGOUT';document.search_form.submit();\">Logout</a>";
		}
		return loginStr;
	}
}

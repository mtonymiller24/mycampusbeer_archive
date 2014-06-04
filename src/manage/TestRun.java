package manage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import process.AdminProcess;

import bean.Campus;
import servlet.CampusServlet;

public class TestRun {

	//
//	private static AdminProcess adminProcess = null;
//	private static ConstantMaps constantMaps = null;
	//private static EventCalendarEnvironment rssManagerEnvironment = null;
	
	//
	static
	{
		//constantMaps = new ConstantMaps();
//		adminProcess = new AdminProcess();
		//rssManagerEnvironment = Manager.getRssManagerEnvironment();
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		testDate();
	}
	
	private static String buildHeader(Campus campus)
	{
		List campuses = new ArrayList();
		Map stateMap = constantMaps.stateMap;
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
	
	public static void testDate()
	{
		try
		{
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			Date date = sdf.parse("11/28/2008");
			//
			Date current = sdf.parse("11/28/2009");
			//
			System.out.println(date.compareTo(current));
			if(date.after(current))
			{
				System.out.println("after");
			}
			else
			{
				System.out.println("before");
			}
		}
		catch(Exception e)
		{
			System.out.println("Exception");
		}
	}
	
}

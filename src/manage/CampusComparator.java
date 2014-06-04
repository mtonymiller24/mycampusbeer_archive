/*
 * Created on Sep 14, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package manage;

import java.util.Comparator;

import bean.Campus;


/**
 * @author tony
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CampusComparator implements Comparator
{

	public int compare(Object o1, Object o2)
	{
		Campus c1 = (Campus) o1;
		Campus c2 = (Campus) o2;
		String value1 = c1.getName().toUpperCase() + c1.getId();
		String value2 = c2.getName().toUpperCase() + c2.getId();
		return value1.compareTo(value2);
	}
}

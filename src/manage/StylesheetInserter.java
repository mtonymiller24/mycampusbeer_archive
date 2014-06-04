package manage;

/*
 * Copyright 2003 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

/*
 * @(#)$Id: StylesheetInserter.java,v 1.1 2007/11/26 17:04:39 lance Exp $
 *
 * Copyright 2003 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */
import org.xml.sax.SAXException;
import org.xml.sax.helpers.XMLFilterImpl;

/**
 * Inserts &lt;?xml-stylesheet ... ?> PI.
 */
public class StylesheetInserter extends XMLFilterImpl
{

	/**
	 * @param styleSheetUri
	 *            The value to put in the <code>href</code> pseudo-attribute
	 *            of the PI. For example, "mystyle.xsl"
	 * @param type
	 *            The value to put in the <code>type</code> pseudo-attribute
	 *            of the PI. For example, "text/xsl"
	 */
	public StylesheetInserter(String styleSheetUri, String type)
	{
		this.styleSheetUri = styleSheetUri;
		this.type = type;
	}

	private final String styleSheetUri, type;

	public void startDocument() throws SAXException
	{
		super.startDocument();

		// insert the PI at the beginning of the document.
		StringBuffer buf = new StringBuffer();
		buf.append("type='");
		buf.append(type);
		buf.append("' href='");
		buf.append(styleSheetUri);
		buf.append("'");

		super.processingInstruction("xml-stylesheet", buf.toString());
	}
}

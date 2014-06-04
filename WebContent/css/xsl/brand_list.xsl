<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0" xmlns:xalan="http://xml.apache.org/xslt">
	<xsl:output method="html" encoding="UTF-8" />
	<xsl:param name="baseActionURL">baseActionURL_false</xsl:param>
	<xsl:param name="baseServerURL"></xsl:param>
	<xsl:variable name="apos">'</xsl:variable>
	<xsl:variable name="amp">&#38;</xsl:variable>
	
	<xsl:variable name="screen" select="'search_results'" />
	
	<xsl:template match="/">
		<script language="JavaScript" type="text/javascript">
			function
			changeClass(id,newClassName) { document.getElementById(id).className =
			newClassName; }
		</script>
		
		<form name="search_form"
			method="post"
			target="_self"
			action="campus/{/data/campus/id}">
			<input type="hidden" name="ACTION" />
			
		   	<div class="content">
		   		<xsl:call-template name="message" />
		   		<xsl:call-template name="brand_list" />				
			</div>
		</form>
	</xsl:template>
	     
     <xsl:template name="brand_list" >
   			<xsl:if test="/data/brand and count(/data/brand) &gt; 0">
          		<h2>Brands</h2>
          		
          		<table class="table-list" border="0" margin="0" cellspacing="0">
	               <tr class="header-row">
	               		<td>Name</td>                           		
	               		<td class="last-column">Type</td>
	               </tr>
	               
	               <xsl:for-each select="/data/brand">
	               		<xsl:variable name="rowStyle">
			           		<xsl:choose>
			           			<xsl:when test="position() mod 2 = 1">even-row</xsl:when>
			           			<xsl:otherwise>odd-row</xsl:otherwise>
			           		</xsl:choose>
			           	</xsl:variable>
	               
	               		<tr class="{$rowStyle}">
	               			<td class="table-cell no-underline">
	               				<a href="javascript:document.search_form['ACTION'].value='PAGE_BRAND';document.search_form['BRAND_ID'].value={id};document.search_form.submit();">
	               					<xsl:value-of select="name"/>
	               				</a>
	               			</td>
	               			<td class="last-column table-cell">
	               				<xsl:value-of select="type" />
	               			</td>
	               		</tr>
	               </xsl:for-each>
               	</table>
        	</xsl:if>
         </xsl:template>
         
    <xsl:template name="message">
		<xsl:if test="/data/messages and count(/data/messages) &gt; 0">
			<div class="message-content">
				<xsl:for-each select="/data/messages">
					<p>
						<xsl:choose>
							<xsl:when test="type = 'validation'">
								<xsl:attribute name="class">message-validation</xsl:attribute>
							</xsl:when>
							<xsl:when test="type = 'information'">
								<xsl:attribute name="class">message-information</xsl:attribute>
							</xsl:when>
						</xsl:choose>
						<xsl:value-of select="message" disable-output-escaping="yes" />
					</p>
				</xsl:for-each>
			</div>
		</xsl:if>
	</xsl:template>
</xsl:stylesheet>
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
		   		<xsl:call-template name="store_list" />				
			</div>
		</form>
	</xsl:template>
	     
     <xsl:template name="store_list">
     	<xsl:if test="/data/store and count(/data/store) &gt; 0">
     		<div class="content">
				<h2>Stores</h2>
				<ul>
					<xsl:for-each select="/data/store">
						<li>
							<p class="name-text">
								<a href="javascript:document.search_form['ACTION'].value='PAGE_STORE';document.search_form['STORE_ID'].value={id};document.search_form.submit();">							
									<xsl:value-of select="name" />
								</a>	
							</p>
							<p class="address-info">
								<xsl:value-of select="address" />
								<br/>
								<xsl:value-of select="concat(city,', ',/data/campus/stateId,' ',zip)" />
								<br/>
								<xsl:value-of select="phone" />
							</p>
						</li>
					</xsl:for-each>
				</ul>
			</div>
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
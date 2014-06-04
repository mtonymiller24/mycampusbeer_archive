<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0" xmlns:xalan="http://xml.apache.org/xslt">
	<xsl:output method="html" encoding="UTF-8" />
	<xsl:param name="baseActionURL">baseActionURL_false</xsl:param>
	<xsl:param name="baseServerURL"></xsl:param>
	<xsl:variable name="apos">'</xsl:variable>
	<xsl:variable name="amp">&#38;</xsl:variable>
	
	<xsl:variable name="screen" select="'general'" />
	
	<xsl:template match="/">
		<script language="JavaScript" type="text/javascript">
			function
			changeClass(id,newClassName) { document.getElementById(id).className =
			newClassName; }
		</script>
		
		<form name="search_form" target="_self" method="post" action="{$baseActionURL}">
			<input type="hidden" name="ACTION" />
			<input type="hidden" name="CAMPUS_ID" value="{/data/campus/id}"/>
			
        	<div class="content">
				<xsl:call-template name="message" />
				<xsl:call-template name="campus_add" />
         	</div>
		</form>
	</xsl:template>
	
	<xsl:template name="campus_add">
		<h2>Add Campus</h2>
			
        <p class="content-label">State</p>
        <p class="content-option">
        	<xsl:value-of select="/data/campus/stateId" />
        </p>
     
        <p class="content-label">Campus Name</p>
        <p class="content-option">
        	<input type="text" name="NAME" size="30" value="{/data/campus/name}"/>
        </p>
    
        <p class="content-label">City</p>
        <p class="content-option">
        	<input type="text" name="CITY" size="30" value="{/data/campus/city}"/>
        </p>
     
        <p class="content-label">Zip</p>
        <p class="content-option">
        	<input type="text" name="ZIP" size="6" value="{/data/campus/zip}"/>
        </p>
        
        <div class="submit-button">
	    	<p class="content-option">
	    		<input type="Submit" name="SUBMIT" value="Submit"/>
	    	</p>
      	</div>
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
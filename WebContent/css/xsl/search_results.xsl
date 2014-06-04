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
		   		<xsl:call-template name="item_list" />
		   		<xsl:call-template name="store_list" />
		   		<xsl:call-template name="brand_list" />
		   		<xsl:call-template name="product_list" />				
			</div>
		</form>
	</xsl:template>
	
	<xsl:template name="item_list">
      	<xsl:if test="/data/item and count(/data/item) &gt; 0">
			<h2>Items</h2>
          
	           <table class="table-list" border="0" margin="0" cellspacing="0">
	               <tr class="header-row">
	               		<td>Name</td>
	               		<td>Price</td>
	               		<td>Pack</td>
	               		<td>Store</td>	                           		
	               		<td class="last-column">Info</td>
	               </tr>
		           <xsl:for-each select="/data/item">
			           <xsl:variable name="rowStyle">
			           		<xsl:choose>
			           			<xsl:when test="position() mod 2 = 1">even-row</xsl:when>
			           			<xsl:otherwise>odd-row</xsl:otherwise>
			           		</xsl:choose>
			           		<xsl:if test="special = 'true'">
			           			special
			           		</xsl:if>
			           		<xsl:if test="flagged = 'true' or outOfDate = 'true'">
			           			flagged
			           		</xsl:if>
			           	</xsl:variable>
			           	
			           	 <tr class="{$rowStyle}">
		               		<td class="name-text table-cell">
		               			<xsl:value-of select="name" />
		               		</td>
		               		<xsl:choose>
		               			<xsl:when test="special = 'true'">
		               				<td class="special-price-list table-cell">
				               			<xsl:value-of select="specialPriceDisplay" />
				               		</td>
		               			</xsl:when>
		               			<xsl:otherwise>
		               				<td class="price-list table-cell">
				               			<xsl:value-of select="priceDisplay" />
				               		</td>
		               			</xsl:otherwise>
		               		</xsl:choose>
		               		
		               		<td class="package-list table-cell">
		               			<xsl:value-of select="quantity" />
		               			<xsl:if test="quantity &gt; 1">
		               				pk
		               			</xsl:if>
		               			&#xA0;
		               			<xsl:value-of select="container" />
		               		</td>
		               		<td class="no-underline name-text table-cell">
		               			<a href="javascript:document.search_form['ACTION'].value='PAGE_STORE';document.search_form['STORE_ID'].value={storeId};document.search_form.submit();">
		               				<xsl:value-of select="storeName" />
								</a>
							</td>
		               		<td class="last-column table-cell item-info">
		               			<xsl:choose>
		               				<xsl:when test="flagged = 'true'">
		               					This price has been flagged as incorrect.
		               				</xsl:when>
		               				<xsl:when test="outOfDate = 'true'">
		               					This price is over a week old.
		               				</xsl:when>
		               				<xsl:otherwise>
		               					<xsl:if test="special = 'true'">
		               						Original Price: <xsl:value-of select="priceDisplay" />
		               						&#xA0;
		               						Special <xsl:value-of select="concat(specialStart, ' to ', specialStop)" />
		               					</xsl:if>
		               				</xsl:otherwise>
		               			</xsl:choose>		               			
		               		</td>
	               		</tr>
		           </xsl:for-each>
	           	</table>
           </xsl:if>
         </xsl:template>
         
        <xsl:template name="store_list">
	   		<xsl:if test="/data/store and count(/data/store) &gt; 0">
	   			<h2>Stores</h2>
	   			
	   			<table class="table-list" border="0" margin="0" cellspacing="0">
	               <tr class="header-row">
	               		<td>Name</td>
	               		<td>City</td>
	               		<td>Address</td>
	               		<td>Phone</td>	                           		
	               		<td class="last-column">Zip</td>
	               </tr>
	               <xsl:for-each select="/data/store">
	               		<xsl:variable name="rowStyle">
			           		<xsl:choose>
			           			<xsl:when test="position() mod 2 = 1">even-row</xsl:when>
			           			<xsl:otherwise>odd-row</xsl:otherwise>
			           		</xsl:choose>
			           	</xsl:variable>
			           	
			           	<tr class="{$rowStyle}">
		               		<td class="table-cell no-underline">
		               			<a href="javascript:document.search_form['ACTION'].value='PAGE_STORE';document.search_form['STORE_ID'].value={id};document.search_form.submit();">
		               				<xsl:value-of select="name" />
		               			</a>
		               		</td>
		               		<td class="table-cell">
								<xsl:value-of select="city" />
							</td>
		               		<td class="table-cell">
		               			<xsl:value-of select="address"/>
							</td>
		               		<td class="table-cell">
		               			<xsl:value-of select="phone" />
		               		</td>
		               		<td class="last-column table-cell">
		               			<xsl:value-of select="zip" />
		               		</td>
		               </tr>
	             	</xsl:for-each>
	            </table>	
	   		</xsl:if>
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
         
         <xsl:template name="product_list">	
         	<xsl:if test="/data/product and count(/data/product) &gt; 0">
	          	<h2>Products</h2>
	          	
	          	<table class="table-list" border="0" margin="0" cellspacing="0">
	               <tr class="header-row">
	               		<td>Name</td>
	               		<td>Brand</td>	               		
	               		<td class="last-column">Type</td>
	               </tr>
	               
	               <xsl:for-each select="/data/product">
	               		<xsl:variable name="rowStyle">
			           		<xsl:choose>
			           			<xsl:when test="position() mod 2 = 1">even-row</xsl:when>
			           			<xsl:otherwise>odd-row</xsl:otherwise>
			           		</xsl:choose>
			           	</xsl:variable>
	               
	               		<tr class="{$rowStyle}">
		               		<td class="table-cell no-underline">
		               			<a href="javascript:document.search_form['ACTION'].value='PAGE_PRODUCT';document.search_form['PRODUCT_ID'].value={id};document.search_form.submit();">
		               				<xsl:value-of select="name" />
		               			</a>
		               		</td>
		               		<td class="table-cell">	
		               			<xsl:value-of select="brandName" />
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
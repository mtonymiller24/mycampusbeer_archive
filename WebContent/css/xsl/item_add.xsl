<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0" xmlns:xalan="http://xml.apache.org/xslt">
	<xsl:output method="html" encoding="UTF-8" />
	<xsl:param name="baseActionURL">baseActionURL_false</xsl:param>
	<xsl:param name="baseServerURL"></xsl:param>
	<xsl:variable name="apos">'</xsl:variable>
	<xsl:variable name="amp">&#38;</xsl:variable>
	<xsl:variable name="specialClass">
		<xsl:choose>
			<xsl:when test="/data/item/special = 'true'">
				show
			</xsl:when>
			<xsl:otherwise>
				hidden
			</xsl:otherwise>
		</xsl:choose>
	</xsl:variable>
	
	<xsl:variable name="screen" select="'item_add'" />
	
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
        		<xsl:call-template name="item_add" />        		
            </div>
		</form>
	</xsl:template>
	
	<xsl:template name="item_add">
		<h2>Add item</h2>
          	<table width="100%" border="0" padding="0" margin="0" cellpadding="0" cellspacing="0" class="align-center">
           	<tr>
            	<td width="30%">
					<p class="content-label">Store</p>
               		<p class="content-option">
	               		<select name="STORE_ID">
	               			<xsl:for-each select="/data/campus/store">
	               				<xsl:choose>
	               					<xsl:when test="/data/item/storeId = id">
	               						<option value="{id}" selected="selected">
			               					<xsl:value-of select="name" />
			               				</option>
	               					</xsl:when>
	               					<xsl:otherwise>
	               						<option value="{id}">
			               					<xsl:value-of select="name" />
			               				</option>
	               					</xsl:otherwise>
	               				</xsl:choose>
	               			</xsl:for-each>
	               		</select>
	               		<a class="add-new" href="javascript:document.search_form['ACTION'].value='ADD_STORE';document.search_form.submit();">add new >></a>
               		</p>
               </td>
               <td width="30%">
	               <p class="content-label"></p>
	               <p class="content-option">
	               		Picodillis<br/>
	               		115 E. Springfield<br/>
	               		Champaign, IL - 61820<br/>
	               </p>
               </td>
              </tr>
              <tr>
               <td width="30%">
	               <p class="content-label">Brand</p>
	               <p class="content-option">
	               		<select name="BRAND_ID">
	               			<xsl:for-each select="/data/brand">
	               				<xsl:choose>
	               					<xsl:when test="/data/item/brandId = id">
	               						<option value="{id}" selected="selected">
			               					<xsl:value-of select="name" />
			               				</option>
	               					</xsl:when>
	               					<xsl:otherwise>
	               						<option value="{id}">
			               					<xsl:value-of select="name" />
			               				</option>
	               					</xsl:otherwise>
	               				</xsl:choose>
	               			</xsl:for-each>
	               		</select>
	               		<a class="add-new" href="javascript:document.search_form['ACTION'].value='ADD_BRAND';document.search_form.submit();">add new >></a>
	               </p>
               
	               <p class="content-label">Type</p>
	               <p class="content-option">
	               		Beer
	               </p>
               </td>
              
               <td>
	               <p class="content-label">Product</p>
	               <p class="content-option">
	               		<select name="NAME">
	               			<xsl:for-each select="/data/brand/product">
	               				<xsl:choose>
	               					<xsl:when test="/data/item/productId = id">
	               						<option value="{id}" selected="selected">
			               					<xsl:value-of select="name" />
			               				</option>
	               					</xsl:when>
	               					<xsl:otherwise>
	               						<option value="{id}">
			               					<xsl:value-of select="name" />
			               				</option>
	               					</xsl:otherwise>
	               				</xsl:choose>
	               			</xsl:for-each>
	               		</select>
	               		<a class="add-new" href="javascript:document.search_form['ACTION'].value='ADD_PRODUCT';document.search_form.submit();">add new >></a>
	               </p>
               </td>
               <td></td>
              </tr>
              <tr>
               <td width="30%">
	               <p class="content-label">Quantity</p>
	               <p class="content-option">
	               		<select name="QUANTITY">
	               			<xsl:choose>
               					<xsl:when test="/data/item/quantity = 24">
               						<option value="24" selected="selected">
		               					24
		               				</option>
               					</xsl:when>
               					<xsl:otherwise>
               						<option value="24">
		               					24
		               				</option>
               					</xsl:otherwise>
               				</xsl:choose>
	               		</select>
	               </p>
               </td>
               <td>
	               <p class="content-label">Container</p>
	               <p class="content-option">
	               		<xsl:choose>
	               			<xsl:when test="/data/item/container = 'cans'">
	               				<input type="radio" name="CONTAINER" value="cans" checked="checked"/>cans
			               		<input type="radio" name="CONTAINER" value="bottles"/>bottles
			               		<input type="radio" name="CONTAINER" value="keg"/>keg
	               			</xsl:when>
	               			<xsl:when test="/data/item/container = 'bottles'">
		               			<input type="radio" name="CONTAINER" value="cans"/>cans
			               		<input type="radio" name="CONTAINER" value="bottles" checked="checked"/>bottles
			               		<input type="radio" name="CONTAINER" value="keg"/>keg
	               			</xsl:when>
	               			<xsl:otherwise>
		               			<input type="radio" name="CONTAINER" value="cans"/>cans
			               		<input type="radio" name="CONTAINER" value="bottles"/>bottles
			               		<input type="radio" name="CONTAINER" value="keg" checked="checked"/>keg
		               		</xsl:otherwise>
	               		</xsl:choose>
	               </p>
               </td>
              </tr>
              <tr>
	              	<xsl:variable name="specialPriceLength" >
	        			<xsl:value-of select="string-length(/data/item/price)" />
	        		</xsl:variable>
	        		<xsl:variable name="specialDollars">
	        			<xsl:value-of select="substring(/data/item/specialPrice,1,$specialPriceLength -2)" />
	        		</xsl:variable>
	        		<xsl:variable name="specialCents">
	        			<xsl:value-of select="substring(/data/item/specialPrice,$specialPriceLength -1)" />
	        		</xsl:variable>
              
              
              		<td width="30%" height="50">
              			<input type="checkbox" name="IS_SPECIAL" value="true" onClick="javascript:hide_show(document.getElementById('special'));hide_show(document.getElementById('original'));">
              				<xsl:attribute name="checked">
              					<xsl:if test="/data/item/special = 'true'">
              						checked
              					</xsl:if>
              				</xsl:attribute>
              			</input>
              			 Limited Special
					</td>
              		<td>
              			<div id="special" class="{$specialClass}">
              				<h3>Special Information</h3>
              				<p class="special-content">
              					From <input type="text" size="5" name="SPECIAL_START" value="{/data/item/specialStart}"/>
              					to<input type="text" size="5" name="SPECIAL_STOP" value="{/data/item/specialStop}"/>
              				</p>
              				<p class="special-content">Special Price</p>
              				<p class="special-content">
	               			$<input type="text" class="price-dollars" name="SPECIAL_PRICE_DOLLAR" value="{$specialDollars}" maxlength="3"/>
	               			.<input type="text" class="price-cents" name="SPECIAL_PRICE_CENTS" value="{$specialCents}" maxlength="2"/> US Dollars
	               		</p>
              			</div>
              		</td>
              </tr>
              <tr>
               <td colspan="2">
               	<xsl:variable name="priceLength" >
        			<xsl:value-of select="string-length(/data/item/price)" />
        		</xsl:variable>
        		<xsl:variable name="dollars">
        			<xsl:value-of select="substring(/data/item/price,1,$priceLength -2)" />
        		</xsl:variable>
        		<xsl:variable name="cents">
        			<xsl:value-of select="substring(/data/item/price,$priceLength -1)" />
        		</xsl:variable>
        		
	               <p class="content-label">
	               		<span id="original" class="{$specialClass}">Original &#xA0;</span>Price</p>
	               <p class="content-option">
	               		$<input type="text" class="price-dollars" name="PRICE_DOLLAR" value="{$dollars}" maxlength="3"/>
	               		.<input type="text" class="price-cents" name="PRICE_CENTS" value="{$cents}" maxlength="2"/> US Dollars
	               </p>
               </td>
           </tr>
           <tr>
               <td colspan="2" style="padding-top: 20px;">
	               <p class="content-option">
	               		<input type="Submit" name="SUBMIT" value="Submit"/>
	               </p>
               </td>
              </tr>
    	</table>
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
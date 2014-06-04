<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0" xmlns:xalan="http://xml.apache.org/xslt">
	<xsl:output method="html" encoding="UTF-8" />
	
	<xsl:variable name="apos">'</xsl:variable>
	<xsl:variable name="amp">&#38;</xsl:variable>
	
	<xsl:template match="/">
		
<html>
<head>
	<LINK REL="StyleSheet" HREF="home.css" TYPE="text/css" MEDIA="screen"/>
	<!--[if IE]>
		<style type="text/css">
		.content li
		{
			float: left;
			height: 100px;
			width: 250px;
			margin-right: 6px;
			list-style: none;
			padding-bottom: 10px;
		}
		</style> 
	<![endif]-->
	<title>Mycampusbeer.com</title>
</head>
<body>

    <form name="search_form">
        <div class="header">
            <img src="./CampusBeer_v1_01.jpg" />
        </div>
        <div class="search-bar standard-border">
            <table width="100%" border="0" padding="0" margin="0">
                <tr>
                    <td width="65%">
                        <span class="location-content">
                        	Campus Location &#xA0; &gt;&gt; &#xA0; Illinois &#xA0; &gt;&gt; &#xA0; UIUC
                        </span>
                    </td>
                    <td width="35%" align="right">
                        <span class="search-content">
                            search
                            <input type="text" name="SEARCH" size="30" />
                        </span>
                    </td>
                </tr>
            </table>
        </div>
        
        <div class="left-nav">
	         <div class="upper-left-nav standard-border">
	             <h3>Filter by:</h3>
	             <ul>
	                 <li>
	                 	<a href="javascript:document.search_form['ACTION'].value='SEARCH_LOCATION';document.search_form.submit();">location</a>
	                 </li>
	                 <li>
	                 	<a href="javascript:document.search_form['ACTION'].value='SEARCH_PRICE';document.search_form.submit();">price</a>
	                 </li>
	                 <li>
	                 	<a href="javascript:document.search_form['ACTION'].value='SEARCH_BRAND';document.search_form.submit();">brand</a>
	                 </li>
	                 <li>
	                 	<a href="javascript:document.search_form['ACTION'].value='SEARCH_PRODUCT';document.search_form.submit();">product</a>
	                 </li>
	                 <li>
	                 	<a href="javascript:document.search_form['ACTION'].value='SEARCH_SPECIAL';document.search_form.submit();">specials</a>
	                 </li>
	             </ul>
	             <h3>
	             	<a href="javascript:document.search_form['ACTION'].value='PAGE_ADVANCED_SEARCH';document.search_form.submit();">Custom Search</a>
	             </h3>
	         </div>
	         <div class="lower-left-nav standard-border">
	             <h3>Manage</h3>
	             <ul>
	             	<li>
	             		<a href="javascript:document.search_form['ACTION'].value='ADD_CAMPUS';document.search_form.submit();">Add Campus</a>
	             	</li>
	                 <li>
	                 	<a href="javascript:document.search_form['ACTION'].value='ADD_STORE';document.search_form.submit();">Add Store</a>
	                 </li>
	                 <li>
	                 	<a href="javascript:document.search_form['ACTION'].value='MANAGE_PRICES';document.search_form.submit();">Manage Prices</a>
	                 </li>
	                 <li>
	                 	<a href="javascript:document.search_form['ACTION'].value='REMOVE_ITEM';document.search_form.submit();">Remove Item</a>
	                 </li>
	             </ul>
	         </div>
	 	</div>
        <div class="center standard-border">
        	<input type="hidden" name="ACTION" />
			<input type="hidden" name="CAMPUS_ID" value="{/data/campus/id}"/>
			
		   	<div class="content">
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
				               		<xsl:for-each select="/data/quantity">
				               			<xsl:choose>
			               					<xsl:when test="/data/item/quantity = .">
			               						<option value="{.}" selected="selected">
					               					<xsl:value-of select="." />
					               				</option>
			               					</xsl:when>
			               					<xsl:otherwise>
			               						<option value="{.}">
					               					<xsl:value-of select="." />
					               				</option>
			               					</xsl:otherwise>
			               				</xsl:choose>
			               			</xsl:for-each>
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
	               		<td width="30%" height="50">
	               			<input type="checkbox" name="IS_SPECIAL" value="true" onClick="javascript:toggleSpecial();"/> Limited Special
						</td>
	               		<td>
	               			<div id="special" class="special-info standard-border" style="display: none;">
	               				<h3>Special Information</h3>
	               				<p class="special-content">From <input type="text" size="5" name="SPECIAL_START" value="/data/item/specialStart"/>to<input type="text" size="5" name="SPECIAL_STOP" value="/data/item/specialStop"/></p>
	               				<p class="special-content">Special Price</p>
	               				<p class="special-content">
			               			$<input type="text" size="3" name="SPECIAL_PRICE_DOLLAR" value="sub_string(/data/item/specialPrice,0,string_length(/data/item/specialPrice)-2)"/>.<input type="text" size="1" name="SPECIAL_PRICE_CENTS" value="substring(/data/item/specialPrice,string_length(/data/item/specialPrice)-2)"/> US Dollars
			               		</p>
	               			</div>
	               		</td>
	               </tr>
	               <tr>
		               <td colspan="2">
			               <p class="content-label"><span id="original" style="display: none;">Original &#xA0;</span>Price</p>
			               <p class="content-option">
			               		$<input type="text" size="3" name="PRICE_DOLLAR" value="sub_string({/data/item/price},0,length({/data/item/price})-2);"/>.<input type="text" size="1" name="PRICE_CENTS" value="sub_string({/data/item/price},string_length({/data/item/price})-2);"/> US Dollars
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
            </div>   
        </div>
        
        <div class="footer standard-border">
            Contact Us &#xA0;&#xA0;|&#xA0;&#xA0; Admin &#xA0;&#xA0;|&#xA0;&#xA0; Legal &#xA0;&#xA0;|&#xA0;&#xA0; Suggestions
        </div>
    </form>
    
</body>
</html>
	</xsl:template>
	 
	
            
</xsl:stylesheet>
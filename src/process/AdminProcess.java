package process;

import java.sql.Date;
import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBException;
import javax.xml.bind.PropertyException;


import factory.SQLFactory;

import manage.Constants;
import manage.JaxbServices;
import manage.Messages;
import manage.ParameterValues;

import bean.Brand;
import bean.Campus;
import bean.Data;
import bean.Item;
import bean.Product;
import bean.Search;
import bean.Store;
import bean.Submitter;

import bean.WorkingStorage;

public class AdminProcess
{

	private static Map actions = new HashMap();
	//
	// MENU BAR
	//
	private static final int FIRST_TIME = 0;
	private static final int PAGE_HOME = 1;
	private static final int PAGE_CAMPUS = 2;
	private static final int PAGE_STORE = 3;
	private static final int PAGE_STORE_LIST = 4;
	private static final int PAGE_BRAND_LIST = 5;
	private static final int PAGE_PRODUCT_LIST = 6;
	//
	private static final int ADD_CAMPUS = 11;
	private static final int ADD_STORE = 12;
	private static final int ADD_ITEM = 13;
	private static final int DELETE_CAMPUS = 14;
	private static final int DELETE_STORE = 15;
	private static final int DELETE_ITEM = 16;
	private static final int EDIT_CAMPUS = 17;
	private static final int EDIT_STORE = 18;
	private static final int EDIT_ITEM = 19;
	private static final int CAMPUS_SAVE = 20;
	private static final int STORE_SAVE = 21;
	private static final int ITEM_SAVE = 22;
	//
	private static final int EDIT_STORE_ITEMS = 23;
	private static final int SAVE_STORE_ITEMS = 24;
	//
	private static final int SEARCH_PRICE = 30;
	private static final int SEARCH_LOCATION = 31;
	private static final int SEARCH_BRAND = 32;
	private static final int SEARCH_PRODUCT = 33;
	private static final int PAGE_ADVANCED_SEARCH = 34;
	private static final int SEARCH_ADVANCED = 35;
	private static final int SEARCH_BY_STRING = 36;
	private static final int SEARCH_SPECIAL = 37;
	//
	private static final int LOGIN = 38;
	private static final int LOGOUT = 39;
	private static final int ADD_SUBMITTER = 40;
	private static final int AUTHENTICATE = 41;
	//
	// private static final int CALENDAR = 9001;
	// private static final int SCREEN = 9002;
	// private static final int GENERATE_JAVASCRIPT_INCLUDES = 9003;
	//
	private static JaxbServices jaxbServices = null;
	private static SQLFactory sqlFactory = null;
	//
	static
	{
		sqlFactory = new SQLFactory();
		jaxbServices = new JaxbServices("bean");
		//
		actions.put("FIRST_TIME", new Integer(FIRST_TIME));
		actions.put("PAGE_HOME", new Integer(PAGE_HOME));
		actions.put("PAGE_CAMPUS", new Integer(PAGE_CAMPUS));
		actions.put("PAGE_STORE", new Integer(PAGE_STORE));
		actions.put("PAGE_STORE_LIST", new Integer(PAGE_STORE_LIST));
		actions.put("PAGE_BRAND_LIST", new Integer(PAGE_BRAND_LIST));
		actions.put("PAGE_PRODUCT_LIST", new Integer(PAGE_PRODUCT_LIST));
		//
		actions.put("ADD_CAMPUS", new Integer(ADD_CAMPUS));
		actions.put("ADD_STORE", new Integer(ADD_STORE));
		actions.put("ADD_ITEM", new Integer(ADD_ITEM));
		actions.put("DELETE_CAMPUS", new Integer(DELETE_CAMPUS));
		actions.put("DELETE_STORE", new Integer(DELETE_STORE));
		actions.put("DELETE_ITEM", new Integer(DELETE_ITEM));
		actions.put("EDIT_CAMPUS", new Integer(EDIT_CAMPUS));
		actions.put("EDIT_STORE", new Integer(EDIT_STORE));
		actions.put("EDIT_ITEM", new Integer(EDIT_ITEM));
		actions.put("CAMPUS_SAVE", new Integer(CAMPUS_SAVE));
		actions.put("STORE_SAVE", new Integer(STORE_SAVE));
		actions.put("ITEM_SAVE", new Integer(ITEM_SAVE));
		//
		actions.put("EDIT_STORE_ITEMS", new Integer(EDIT_STORE_ITEMS));
		actions.put("SAVE_STORE_ITEMS", new Integer(SAVE_STORE_ITEMS));
		//
		actions.put("SEARCH_PRICE", new Integer(SEARCH_PRICE));
		actions.put("SEARCH_LOCATION", new Integer(SEARCH_LOCATION));
		actions.put("SEARCH_BRAND", new Integer(SEARCH_BRAND));
		actions.put("SEARCH_PRODUCT", new Integer(SEARCH_PRODUCT));
		actions.put("PAGE_ADVANCED_SEARCH", new Integer(PAGE_ADVANCED_SEARCH));
		actions.put("SEARCH_ADVANCED", new Integer(SEARCH_ADVANCED));
		actions.put("SEARCH_BY_STRING", new Integer(SEARCH_BY_STRING));
		actions.put("SEARCH_SPECIAL", new Integer(SEARCH_SPECIAL));
		//
		actions.put("LOGIN", new Integer(LOGIN));
		actions.put("LOGOUT", new Integer(LOGOUT));
		actions.put("ADD_SUBMITTER", new Integer(ADD_SUBMITTER));
		actions.put("AUTHENTICATE", new Integer(AUTHENTICATE));
	}

	public void init()
	{}

	public boolean doAction(ParameterValues parameters, Object object)
	{
		WorkingStorage workingStorage = (WorkingStorage) object;
		workingStorage.getMessages().clear();
		if(workingStorage.getCampus() == null || workingStorage.getCampus().getId() == 0)
		{
			Campus campus = new Campus();
			String campusIdStr = parameters.getParameter("CAMPUS_ID");
			if(campusIdStr != null && campusIdStr.length() > 0)
			{
				int campusId = Integer.parseInt(campusIdStr);
				campus.setId(campusId);
				campus = sqlFactory.getCampus(campus);
				workingStorage.setCampus(campus);
			}
		}
		//
		String actionStr = parameters.getParameter("ACTION");
		if (actionStr == null)
		{
			actionStr = "";
		}
		int action = getAction(actionStr, actions);
		if (action == 0 && workingStorage.getScreenId() != -1)
		{
			return false;
		}

		switch (action) {
		case FIRST_TIME:
		case PAGE_HOME:
		{
			if(workingStorage.getCampus() == null)
			{
				Campus campus = new Campus();
				campus.setId(1);
				campus = sqlFactory.getCampus(campus);
				workingStorage.setCampus(campus);
			}
			workingStorage.setScreenId(PAGE_HOME);
			break;
		}
		case PAGE_CAMPUS:
		{
			Campus campus = workingStorage.getCampus();
			if(campus == null)
			{
				campus = new Campus();
			}
			getParameters(parameters, campus);
			workingStorage.setCampus(campus);
			workingStorage.setScreenId(PAGE_CAMPUS);
			break;
		}
		case PAGE_STORE:
		{
			Store store = workingStorage.getStore();
			if(store == null)
			{
				store = new Store();
			}
			getParameters(parameters, store);
			workingStorage.setStore(store);
			workingStorage.setScreenId(PAGE_STORE);
			break;
		}
		case PAGE_STORE_LIST:
		{
			Campus campus = workingStorage.getCampus();
			if(campus == null)
			{
				campus = new Campus();
			}
			getParameters(parameters, campus);
			workingStorage.setCampus(campus);
			workingStorage.setScreenId(PAGE_STORE_LIST);
			break;
		}
		case PAGE_BRAND_LIST:
		{
			workingStorage.setScreenId(PAGE_BRAND_LIST);
			break;
		}
		case PAGE_PRODUCT_LIST:
		{
			workingStorage.setScreenId(PAGE_PRODUCT_LIST);
			break;
		}
		case ADD_CAMPUS:
		{
			Campus campus = new Campus();
			initCampus(campus);
			campus.setStateId(workingStorage.getStateId());
			workingStorage.setCampus(campus);
			workingStorage.setScreenId(EDIT_CAMPUS);
			break;
		}
		case DELETE_CAMPUS:
		{
			Campus campus = new Campus();
			getParameters(parameters, campus);
			sqlFactory.deleteCampus(campus);
			workingStorage.getMessages().add(Messages.createInformationMessage(Messages.MESSAGE_CAMPUS_DELETED));
			workingStorage.setScreenId(PAGE_HOME);
			break;
		}
		case EDIT_CAMPUS:
		{
			Campus campus = new Campus(); 
			getParameters(parameters, campus);
			sqlFactory.getCampus(campus);
			workingStorage.setCampus(campus);
			workingStorage.setScreenId(EDIT_CAMPUS);
			break;
		}
		case CAMPUS_SAVE:
		{
			Campus campus = workingStorage.getCampus();
			getParameters(parameters, campus);
			if (isValid(campus, workingStorage.getMessages()))
			{
				saveCampus(campus, workingStorage.getMessages());
				workingStorage.setScreenId(PAGE_CAMPUS);
			}
			else
			{
				workingStorage.setScreenId(EDIT_CAMPUS);
			}
			break;
		}
		//
		case ADD_STORE:
		{
			Store store = new Store();
			initStore(store);
			store.setCity(workingStorage.getCampus().getCity());
			workingStorage.setStore(store);
			workingStorage.setScreenId(EDIT_STORE);
			break;
		}
		case DELETE_STORE:
		{
			Store store = new Store();
			getParameters(parameters, store);
			sqlFactory.deleteStore(store);
			workingStorage.getMessages().add(Messages.createInformationMessage(Messages.MESSAGE_STORE_DELETED));
			workingStorage.setScreenId(PAGE_CAMPUS);
			break;
		}
		case EDIT_STORE:
		{
			Store store = new Store();
			getParameters(parameters, store);
			sqlFactory.getStore(store);
			workingStorage.setStore(store);
			workingStorage.setScreenId(EDIT_STORE);
			break;
		}
		case STORE_SAVE:
		{
			Store store = workingStorage.getStore();
			getParameters(parameters, store);
			if (isValid(store, workingStorage.getMessages()))
			{
				saveStore(store, workingStorage.getMessages());
				workingStorage.setScreenId(PAGE_STORE);
			}
			else
			{
				workingStorage.setScreenId(EDIT_STORE);
			}
			break;
		}
		//
		case ADD_ITEM:
		{
			Item item = new Item();
			initItem(item);
			item.setCampusId(workingStorage.getStore().getCampusId());
			item.setStoreId(workingStorage.getStore().getId());
			item.setStoreName(workingStorage.getStore().getName());
			workingStorage.setItem(item);
			workingStorage.setScreenId(EDIT_ITEM);
			break;
		}
		case DELETE_ITEM:
		{
			Item item = new Item();
			getParameters(parameters, item);
			sqlFactory.deleteItem(item);
			workingStorage.getMessages().add(Messages.createInformationMessage(Messages.MESSAGE_ITEM_DELETED));
			workingStorage.setScreenId(PAGE_STORE);
			break;
		}
		case EDIT_ITEM:
		{
			Item item = new Item();
			getParameters(parameters, item);
			sqlFactory.getItem(item);
			workingStorage.setItem(item);
			workingStorage.setScreenId(EDIT_ITEM);
			break;
		}
		case ITEM_SAVE:
		{
			Item item = workingStorage.getItem();
			getParameters(parameters, item);
			if (isValid(item, workingStorage.getMessages()))
			{
				sqlFactory.addSubmission(workingStorage.getSubmitter());
				saveItem(item, workingStorage.getMessages());
				workingStorage.setScreenId(PAGE_STORE);
			}
			else
			{
				workingStorage.setScreenId(EDIT_ITEM);
			}
			break;
		}
		//
		case EDIT_STORE_ITEMS:
		{
			Store store = new Store();
			getParameters(parameters, store);
			store = sqlFactory.getStore(store);
			//
			List listBy = new ArrayList();
			populateListBy(parameters, listBy);
			//
			List storeItems = sqlFactory.getItemsByStoreAdmin(store, listBy);
			store.getItem().addAll(storeItems);
			workingStorage.setStore(store);
			workingStorage.setScreenId(EDIT_STORE_ITEMS);
		}
		case SAVE_STORE_ITEMS:
		{
			Store store = workingStorage.getStore();
			getParameters(parameters, store, store.getItem());
			if(isValid(store, store.getItem(), workingStorage.getMessages()))
			{
				sqlFactory.addSubmission(workingStorage.getSubmitter());
				sqlFactory.saveStoreItems(store, workingStorage.getSubmitter());
				workingStorage.getMessages().add(Messages.createInformationMessage(Messages.MESSAGE_STORE_ITEM_SAVE));
				workingStorage.setScreenId(EDIT_STORE);
			}
			else
			{
				workingStorage.setScreenId(EDIT_STORE_ITEMS);
			}
			break;
		}
		//
		case PAGE_ADVANCED_SEARCH:
		{
			Search search = new Search();
			initSearch(search);
			workingStorage.setSearch(search);
			//
			workingStorage.setScreenId(SEARCH_ADVANCED);
			break;
		}
		case SEARCH_ADVANCED:
		{
			Search search = workingStorage.getSearch();
			getParameters(parameters, search);
			workingStorage.setSearch(search);
			//
			workingStorage.setScreenId(SEARCH_ADVANCED);
			break;
		}
		case SEARCH_PRICE:
		{
			Campus campus = workingStorage.getCampus();
			getParameters(parameters, campus);
			workingStorage.setCampus(campus);
			//
			workingStorage.setScreenId(SEARCH_PRICE);
			break;
		}
		case SEARCH_LOCATION:
		{
			Store store = new Store();
			getParameters(parameters, store);
			workingStorage.setStore(store);
			//
			workingStorage.setScreenId(SEARCH_LOCATION);
			break;
		}
		case SEARCH_BRAND:
		{
			Item item = new Item();
			getParameters(parameters, item);
			workingStorage.setItem(item);
			//
			workingStorage.setScreenId(SEARCH_BRAND);
			break;
		}
		case SEARCH_PRODUCT:
		{
			Item item = new Item();
			getParameters(parameters, item);
			workingStorage.setItem(item);
			//
			workingStorage.setScreenId(SEARCH_PRODUCT);
			break;
		}
		case SEARCH_BY_STRING:
		{
			Search search = new Search();
			initSearch(search);
			getParameters(parameters, search);
			workingStorage.setSearch(search);
			//
			workingStorage.setScreenId(SEARCH_BY_STRING);
			break;
		}
		case SEARCH_SPECIAL:
		{
			Campus campus = workingStorage.getCampus();
			getParameters(parameters, campus);
			workingStorage.setCampus(campus);
			//
			workingStorage.setScreenId(SEARCH_SPECIAL);
			break;
		}
		case LOGIN:
		{
			Submitter submitter = new Submitter();
			initSubmitter(submitter);
			getParameters(parameters, submitter);
			if(isValid(submitter, workingStorage.getMessages()))
			{
				Submitter check = sqlFactory.getSubmitter(submitter);
				if(check == null)
				{
					workingStorage.getMessages().add(Messages.createValidationMessage(Messages.VALIDATE_USER_MISSING));
				}
				else if(!submitter.checkPassword(check.getPassword()))
				{
					workingStorage.getMessages().add(Messages.createValidationMessage(Messages.VALIDATE_INCORRECT_PASSWORD));
				}
				else
				{
					workingStorage.getMessages().add(Messages.createInformationMessage(Messages.MESSAGE_LOGIN_SUCCESSFUL));
				}
			}
			workingStorage.setScreenId(workingStorage.getLastScreenId());
		}
		case LOGOUT:
		{
			workingStorage.setSubmitter(null);
			workingStorage.getMessages().add(Messages.createInformationMessage(Messages.MESSAGE_LOGOUT_SUCCESSFUL));
			workingStorage.setScreenId(workingStorage.getLastScreenId());
		}
		case ADD_SUBMITTER:
		{
			Submitter submitter = new Submitter();
			initSubmitter(submitter);
			getParameters(parameters, submitter);
			if(isValid(submitter, workingStorage.getMessages()))
			{
				Submitter check = sqlFactory.checkUserId(submitter);
				if(check == null)
				{
					int code = (int) (Math.random()*1000000) +1;
					submitter.setAuthenticationCode(code);
					sqlFactory.insertSubmitter(submitter);
					// SEND EMAIL
					workingStorage.getMessages().add(Messages.createInformationMessage(Messages.MESSAGE_EMAIL_SENT));
				}
				else if(check.isAuthenticated())
				{
					workingStorage.getMessages().add(Messages.createValidationMessage(Messages.VALIDATE_USER_ID_TAKEN));
				}
				else
				{
					// SEND EMAIL AGAIN
					workingStorage.getMessages().add(Messages.createInformationMessage(Messages.MESSAGE_EMAIL_RESENT));
				}	
			}
			workingStorage.setScreenId(workingStorage.getLastScreenId());
		}
		case AUTHENTICATE:
		{
			Submitter submitter = new Submitter();
			initSubmitter(submitter);
			getParameters(parameters, submitter);
			Submitter check = sqlFactory.getSubmitter(submitter);
			if(check != null && submitter.getAuthenticationCode() == check.getAuthenticationCode())
			{
				workingStorage.getMessages().add(Messages.createInformationMessage(Messages.MESSAGE_SUBMITTER_ADDED));
				workingStorage.setSubmitter(submitter);
			}
			else
			{
				workingStorage.getMessages().add(Messages.createValidationMessage(Messages.VALIDATE_AUTHENTICATION_FAILED));
			}
		}
		}
		return true;
	}

	public String getXML(Object object)
	{
		WorkingStorage workingStorage = (WorkingStorage) object;
		Data data = new Data();
		if(workingStorage.getSubmitter() != null)
		{
			data.setSubmitter(workingStorage.getSubmitter());
		}
		if(workingStorage.getCampus() != null)
		{
			data.getCampus().add(workingStorage.getCampus());
		}
		data.setMyCampusEnvironment(workingStorage.getMyCampusEnvironment());
		switch (workingStorage.getScreenId()) {
		case PAGE_HOME:
		{
			List cheapest = sqlFactory.getCheapestBeers(workingStorage.getCampus());
			if(cheapest != null && cheapest.size() > 0)
			{
				data.getItem().addAll(cheapest);
			}
			
//			
//			
//			Item item = new Item();
//			initItem(item);
//			item.setId(1);
//			item.setStoreId(1);
//			item.setProductId(1);
//			item.setStoreName("Discount Den");
//			item.setCampusId(1);
//			item.setPrice(1399);
//			item.setPriceDisplay("$13.99");
//			item.setName("Miller Lt");
//			item.setQuantity(24);
//			item.setContainer("cans");
//			item.setSpecial(true);
//			item.setSpecialPrice(1299);
//			item.setSpecialPriceDisplay("$12.99");
//			item.setSpecialStart("12/5");
//			item.setSpecialStop("12/25");
//			data.getItem().add(item);
//			
//			
//			
//			
//			Item item2 = new Item();
//			initItem(item2);
//			item2.setId(2);
//			item2.setStoreId(2);
//			item2.setProductId(2);
//			item2.setStoreName("Colonial Pantry");
//			item2.setCampusId(1);
//			item2.setPrice(1249);
//			item2.setPriceDisplay("$12.49");
//			item2.setName("Bud Lt");
//			item2.setQuantity(24);
//			item2.setContainer("cans");
//			item2.setFlagged(true);
//			data.getItem().add(item2);
//			Item item3 = new Item();
//			initItem(item3);
//			item3.setId(3);
//			item3.setStoreId(2);
//			item3.setProductId(3);
//			item3.setStoreName("Colonial Pantry");
//			item3.setCampusId(1);
//			item3.setPrice(1249);
//			item3.setPriceDisplay("$12.49");
//			item3.setName("Coors Lt");
//			item3.setQuantity(24);
//			item3.setContainer("cans");
//			//item3.setOutOfDate(true);
//			data.getItem().add(item3);
//			data.getItem().add(item3);
//			data.getItem().add(item3);
//			data.getItem().add(item3);
//			data.getItem().add(item3);
//			data.getItem().add(item3);
//			data.getItem().add(item3);
//			
//			Store store = new Store();
//			initStore(store);
//			store.setId(1);
//			store.setCampusId(1);
//			store.setName("Discount Den");
//			store.setCity("Champaign");
//			store.setPhone("217-621-6128");
//			store.setAddress("100 Springfield");
//			store.setZip(61820);
//			store.getItem().add(item);
//			data.getStore().add(store);
//			data.getStore().add(store);
//			data.getStore().add(store);
//			data.getStore().add(store);
//			data.getStore().add(store);
//			
//			
//			Campus campus = new Campus();
//			initCampus(campus);
//			campus.setId(1);
//			campus.setName("UIUC");
//			campus.setCity("Urbana");
//			campus.setState("Illinois");
//			campus.setStateId("IL");
//			campus.getItem().add(item);
//			campus.getStore().add(store);
//			data.getCampus().add(campus);
//			workingStorage.setCampus(campus);
//			workingStorage.setStore(store);
//			workingStorage.setItem(item);
//			
//			Brand brand = new Brand();
//			initBrand(brand);
//			brand.setId(1);
//			brand.setName("Miller");
//			brand.setType("beer");
//			
//			
//			
//			Product product = new Product();
//			initProduct(product);
//			product.setId(1);
//			product.setBrandId(1);
//			product.setBrandName("Miller");
//			product.setName("Miller Lt");
//			product.setPopular(true);
//			product.setType("beer");
//			data.getProduct().add(product);
//			data.getProduct().add(product);
//			data.getProduct().add(product);
//			data.getProduct().add(product);
//			data.getProduct().add(product);
//			
//			brand.getProduct().add(product);
//			data.getBrand().add(brand);
//			data.getBrand().add(brand);
//			data.getBrand().add(brand);
//			data.getBrand().add(brand);
//			data.getBrand().add(brand);
//			data.getMessage().add(Messages.createInformationMessage(Messages.MESSAGE_STORE_ITEM_SAVE));
//			data.getMessage().add(Messages.createValidationMessage(Messages.VALIDATE_NO_RESULTS));
			
//			Submitter s = new Submitter();
//			initSubmitter(s);
//			s.setUserId("mycampusbeer@gmail.com");
//			s.setLoggedIn(true);
//			workingStorage.setSubmitter(s);
//			data.setSubmitter(s);
//			
//			
			workingStorage.setScreenName(Constants.PAGE_HOME);
			break;
		}
		case EDIT_CAMPUS:
		{
			workingStorage.setScreenName(Constants.PAGE_EDIT_CAMPUS);
			break;
		}
		case EDIT_STORE:
		{
			data.getStore().add(workingStorage.getStore());
			workingStorage.setScreenName(Constants.PAGE_EDIT_STORE);
			break;
		}
		case EDIT_ITEM:
		{
			data.getItem().add(workingStorage.getItem());
			workingStorage.setScreenName(Constants.PAGE_EDIT_ITEM);
			break;
		}
		case EDIT_STORE_ITEMS:
		{
			data.getStore().add(workingStorage.getStore());
			workingStorage.setScreenName(Constants.PAGE_EDIT_STORE_ITEMS);
			break;
		}
		case PAGE_STORE_LIST:
		{
			List stores = sqlFactory.getStoreList(workingStorage.getCampus());
			if(stores != null && stores.size() > 0)
			{
				data.getStore().addAll(stores);
			}
			workingStorage.setScreenName(Constants.PAGE_STORE_LIST);
			break;
		}
		case PAGE_BRAND_LIST:
		{
			List brands = sqlFactory.getBrands();
			if(brands != null && brands.size() > 0)
			{
				data.getBrand().addAll(brands);
			}
			workingStorage.setScreenName(Constants.PAGE_BRAND_LIST);
			break;
		}
		case PAGE_PRODUCT_LIST:
		{
			List products = sqlFactory.getProducts();
			if(products != null && products.size() > 0)
			{
				data.getProduct().addAll(products);
			}
			workingStorage.setScreenName(Constants.PAGE_PRODUCT_LIST);
			break;
		}
		//
		case SEARCH_PRICE:
		{
			List itemsList = sqlFactory.getItemsByPrice(workingStorage.getCampus());
			if(itemsList != null && itemsList.size() > 0)
			{
				data.getItem().addAll(itemsList);
			}
			else
			{
				workingStorage.getMessages().add(Messages.createValidationMessage(Messages.VALIDATE_NO_RESULTS));
			}
			workingStorage.setScreenName(Constants.PAGE_SEARCH_RESULTS);
			break;
		}
		case SEARCH_BRAND:
		{
			List itemsList = sqlFactory.getItemsByBrand(workingStorage.getItem());
			if(itemsList != null && itemsList.size() > 0)
			{
				data.getItem().addAll(itemsList);
			}
			else
			{
				workingStorage.getMessages().add(Messages.createValidationMessage(Messages.VALIDATE_NO_RESULTS));
			}
			workingStorage.setScreenName(Constants.PAGE_SEARCH_RESULTS);
			break;
		}
		case SEARCH_PRODUCT:
		{
			List itemsList = sqlFactory.getItemsByProduct(workingStorage.getItem());
			if(itemsList != null && itemsList.size() > 0)
			{
				data.getItem().addAll(itemsList);
			}
			else
			{
				workingStorage.getMessages().add(Messages.createValidationMessage(Messages.VALIDATE_NO_RESULTS));
			}
			workingStorage.setScreenName(Constants.PAGE_SEARCH_RESULTS);
			break;
		}
		case SEARCH_LOCATION:
		{
			List itemsList = sqlFactory.getItemsByStore(workingStorage.getStore());
			if(itemsList != null && itemsList.size() > 0)
			{
				data.getItem().addAll(itemsList);
			}
			else
			{
				workingStorage.getMessages().add(Messages.createValidationMessage(Messages.VALIDATE_NO_RESULTS));
			}
			workingStorage.setScreenName(Constants.PAGE_SEARCH_RESULTS);
			break;
		}
		case SEARCH_SPECIAL:
		{
			List itemsList = sqlFactory.getSpecialsForCampus(workingStorage.getCampus());
			if(itemsList != null && itemsList.size() > 0)
			{
				data.getItem().addAll(itemsList);
			}
			else
			{
				workingStorage.getMessages().add(Messages.createValidationMessage(Messages.VALIDATE_NO_RESULTS));
			}
			workingStorage.setScreenName(Constants.PAGE_SEARCH_RESULTS);
			break;
		}
		case SEARCH_BY_STRING:
		{
			Search search = workingStorage.getSearch();
			boolean resultsFound = false;
			
			List brandResults = sqlFactory.searchBrandByString(search);
			if (brandResults != null && brandResults.size() > 0)
			{
				data.getBrand().addAll(brandResults);
				resultsFound = true;
			}
		
			List productResults = sqlFactory.searchProductByString(search);
			if (productResults != null && productResults.size() > 0)
			{
				data.getProduct().addAll(productResults);
				resultsFound = true;
			}
		
			List storeResults = sqlFactory.searchStoreByString(search);
			if (storeResults != null && storeResults.size() > 0)
			{
				data.getStore().addAll(storeResults);
				resultsFound = true;
			}
		
			List itemResults = sqlFactory.searchItemsByString(search);
			if (itemResults != null && itemResults.size() > 0)
			{
				data.getItem().addAll(itemResults);
				resultsFound = true;
			}
	
			if(!resultsFound)
			{
				workingStorage.getMessages().add(Messages.createValidationMessage(Messages.VALIDATE_NO_RESULTS));
			}
			workingStorage.setScreenName(Constants.PAGE_SEARCH_RESULTS);
		}
		case SEARCH_ADVANCED:
		{
			List itemResults = sqlFactory.getItemsAdvancedSearch(workingStorage.getSearch());
			if (itemResults != null && itemResults.size() > 0)
			{
				data.getItem().addAll(itemResults);
			}
			else
			{
				workingStorage.getMessages().add(Messages.createValidationMessage(Messages.VALIDATE_NO_RESULTS));
			}
			workingStorage.setScreenName(Constants.PAGE_SEARCH_RESULTS);
		}
		}
		if(workingStorage.getMessages() != null && workingStorage.getMessages().size() > 0)
		{
			data.getMessage().addAll(workingStorage.getMessages());
		}
		data.getQuantities().addAll(getQuantityList());
		data.getContainers().addAll(getContainerList());
		workingStorage.setScreenId(workingStorage.getLastScreenId());
		String xml;
		try
		{
			xml = jaxbServices.marshal(data);
			xml = xml.replaceAll("\r", "&#13;");
		}
		catch (PropertyException e)
		{
			System.out.println(e);
			xml = "";
		}
		catch (JAXBException e)
		{
			System.out.println(e);
			xml = "";
		}
		System.out.println(xml);
		return xml;
	}

	private int getAction(String actionStr, Map actions)
	{
		int action = FIRST_TIME;
		try
		{
			action = ((Integer) actions.get(actionStr)).intValue();
		}
		catch (Exception e)
		{}
		return action;
	}

	private Campus initCampus(Campus campus)
	{
		campus.setId(0);
		campus.setState("");
		campus.setCity("");
		campus.setName("");
		campus.setZip(00000);
		//
		return campus;
	}

	//
	private Store initStore(Store store)
	{
		store.setId(0);
		store.setCampusId(0);
		store.setCity("");
		store.setAddress("");
		store.setPhone("");
		store.setZip(00000);
		store.setName("");
		//
		return store;
	}

	//
	private Item initItem(Item item)
	{
		item.setId(0);
		item.setStoreId(0);
		item.setCampusId(0);
		item.setProductId(0);
		item.setName("");
		item.setBrand("");
		item.setContainer("");
		item.setEditedTimestamp("");
		item.setFlagged(false);
		item.setPrice(0);
		item.setPriceDisplay("");
		item.setSpecial(false);
		item.setSpecialPrice(0);
		item.setSpecialPriceDisplay("");
		item.setSpecialStart("");
		item.setSpecialStop("");
		item.setType("");
		item.setStoreName("");
		item.setOutOfDate(false);
		item.setStaticPrice(false);
		//
		return item;
	}

	//
	private Brand initBrand(Brand brand)
	{
		brand.setId(0);
		brand.setName("");
		brand.setType("");
		//
		return brand;
	}

	//
	private Product initProduct(Product product)
	{
		product.setId(0);
		product.setBrandId(0);
		product.setName("");
		product.setBrandName("");
		product.setType("");
		product.setPopular(false);
		//
		return product;
	}

	//
	private Search initSearch(Search search)
	{
		search.setCampusId(0);
		search.setStoreId(0);
		search.setBrandId(0);
		search.setProductId(0);
		search.setStoreSearch(false);
		search.setBrandSearch(false);
		search.setProductSearch(false);
		search.setItemSearch(true);
		search.setBeer(true);
		search.setWine(false);
		search.setLiquor(false);
		search.setChampagne(false);
		search.setOther(false);
		search.setSpecialOnly(false);
		search.setSearchBy("");
		//
		return search;
	}
	
	//
	private Submitter initSubmitter(Submitter submitter)
	{
		submitter.setAdmin(false);
		submitter.setAuthenticated(false);
		submitter.setLoggedIn(false);
		submitter.setPassword("");
		submitter.setRank("Newbie");
		submitter.setSubmissions(0);
		submitter.setUserId("");
		//
		return submitter;
	}

	private void saveCampus(Campus campus, List messages)
	{
		if (campus.getId() == 0)
		{
			sqlFactory.insertCampus(campus);
			messages.add(Messages.createInformationMessage(Messages.MESSAGE_CAMPUS_ADDED));
		}
		else
		{
			sqlFactory.updateCampus(campus);
			messages.add(Messages.createInformationMessage(Messages.MESSAGE_CAMPUS_SAVE));
		}
	}

	//
	private void saveStore(Store store, List messages)
	{
		if (store.getId() == 0)
		{
			sqlFactory.insertStore(store);
			messages.add(Messages.createInformationMessage(Messages.MESSAGE_STORE_ADDED));
		}
		else
		{
			sqlFactory.updateStore(store);
			messages.add(Messages.createInformationMessage(Messages.MESSAGE_STORE_SAVE));
		}
	}

	//
	private void saveItem(Item item, List messages)
	{
		if (item.getId() == 0)
		{
			sqlFactory.insertItem(item);
			messages.add(Messages.createInformationMessage(Messages.MESSAGE_ITEM_ADDED));
		}
		else
		{
			sqlFactory.updateItem(item);
			messages.add(Messages.createInformationMessage(Messages.MESSAGE_ITEM_SAVE));
		}
	}

	//
	//
	private boolean isValid(Campus campus, List messages)
	{
		if (campus.getState() == null || campus.getState().length() == 0)
		{
			messages.add(Messages.createValidationMessage(Messages.VALIDATE_STATE));
			return false;
		}
		//
		if (campus.getName() == null || campus.getName().length() == 0)
		{
			messages.add(Messages.createValidationMessage(Messages.VALIDATE_NAME));
			return false;
		}
		//
		if (campus.getCity() == null || campus.getCity().length() == 0)
		{
			messages.add(Messages.createValidationMessage(Messages.VALIDATE_CITY));
			return false;
		}
		// zip not required
		return true;
	}

	//
	private boolean isValid(Store store, List messages)
	{
		if (store.getAddress() == null || store.getAddress().length() == 0)
		{
			messages.add(Messages.createValidationMessage(Messages.VALIDATE_ADDRESS));
			return false;
		}
		//
		if (store.getCity() == null || store.getCity().length() == 0)
		{
			messages.add(Messages.createValidationMessage(Messages.VALIDATE_CITY));
			return false;
		}
		//
		if (store.getCampusId() == 0)
		{
			return false;
		}
		//
		if (store.getName() == null || store.getName().length() == 0)
		{
			messages.add(Messages.createValidationMessage(Messages.VALIDATE_NAME));
			return false;
		}
		//
		if (store.getPhone() == null || store.getPhone().length() == 0)
		{
			messages.add(Messages.createValidationMessage(Messages.VALIDATE_PHONE));
			return false;
		}
		//
		if (store.getZip() == 0)
		{
			messages.add(Messages.createValidationMessage(Messages.VALIDATE_ZIP));
			return false;
		}
		return true;
	}
	
	//
	private boolean isValid(Store store, List items, List messages)
	{
		for(Iterator iter = items.iterator(); iter.hasNext();)
		{
			Item item = (Item)iter.next();
			if(!this.isValid(item, new ArrayList()))
			{
				messages.add(Messages.createValidationMessage(Messages.VALIDATE_LIST_PRICE));
				return false;
			}
		}
		return true;
	}

	//
	private boolean isValid(Item item, List messages)
	{
		if (item.getBrand() == null || item.getBrand().length() == 0)
		{
			messages.add(Messages.createValidationMessage(Messages.VALIDATE_BRAND));
			return false;
		}
		//
		if (item.getContainer() == null || item.getContainer().length() == 0)
		{
			messages.add(Messages.createValidationMessage(Messages.VALIDATE_CONTAINER));
			return false;
		}
		//
		if (item.getName() == null || item.getName().length() == 0)
		{
			messages.add(Messages.createValidationMessage(Messages.VALIDATE_NAME));
			return false;
		}
		//
		if (item.getPrice() == 0)
		{
			messages.add(Messages.createValidationMessage(Messages.VALIDATE_PRICE));
			return false;
		}
		//
		if (item.getQuantity() == 0)
		{
			messages.add(Messages.createValidationMessage(Messages.VALIDATE_QUANTITY));
			return false;
		}
		//
		if (item.isSpecial())
		{
			if (item.getSpecialStart() == null || item.getSpecialStart().length() == 0)
			{
				messages.add(Messages.createValidationMessage(Messages.VALIDATE_SPECIAL_START));
				return false;
			}
			//
			if (item.getSpecialStop() == null || item.getSpecialStop().length() == 0)
			{
				messages.add(Messages.createValidationMessage(Messages.VALIDATE_SPECIAL_STOP));
				return false;
			}
			//
//			if (item.getSpecialStart() > item.getSpecialStop() && item.getSpecialStop() < now())
//			{
//				messages.add(Messages.createValidationMessage(Messages.VALIDATE_SPECIAL_DATES));
//				return false;
//			}
			//
			if (item.getSpecialPrice() == 0 && item.getSpecialPrice() != item.getPrice())
			{
				messages.add(Messages.createValidationMessage(Messages.VALIDATE_SPECIAL_PRICE));
				return false;
			}
		}
		//
		if (item.getType() == null || item.getType().length() == 0)
		{
			messages.add(Messages.createValidationMessage(Messages.VALIDATE_TYPE));
			return false;
		}
		//
		List matchingItems = sqlFactory.checkForItem(item);
		if (matchingItems != null && matchingItems.size() > 0)
		{
			String message = "There is already a " + item.getBrand() + " - " + item.getName() + " "  + item.getQuantity() + "pk " + item.getContainer() + " at " + item.getStoreName() + " please edit it.";
			messages.add(Messages.createValidationMessage(message));
			return false;
		}
		//
		return true;
	}

	//
	private boolean isValid(Submitter submitter, List messages)
	{
		if (submitter.getUserId() == null || submitter.getUserId().length() == 0
				|| submitter.getUserId().contains("@"))
		{
			messages.add(Messages.createValidationMessage(Messages.VALIDATE_USER_ID));
			return false;
		}
		//
		if (submitter.getPassword() == null || submitter.getPassword().length() == 0)
		{
			messages.add(Messages.createValidationMessage(Messages.VALIDATE_PASSWORD));
			return false;
		}
		//
		return true;
	}
	

	private void getParameters(ParameterValues parameters, Campus campus)
	{
		String idStr = parameters.getParameter("CAMPUS_ID");
		String stateIdStr = parameters.getParameter("STATE_ID");
		String nameStr = parameters.getParameter("NAME");
		// String stateStr = parameters.getParameter("STATE");
		String cityStr = parameters.getParameter("CITY");
		String zipStr = parameters.getParameter("ZIP");

		if (idStr != null && idStr.trim().length() > 0)
		{
			int campusId = Integer.parseInt(idStr);
			campus.setId(campusId);
		}
		//
		if (stateIdStr != null && idStr.trim().length() > 0)
		{
			campus.setStateId(stateIdStr);
			//
			//String state = constantMaps.stateMap.get(stateIdStr);
			//campus.setState(state);
		}
		//
		if (nameStr != null && nameStr.trim().length() > 0)
		{
			campus.setName(nameStr);
		}
		//
		if (cityStr != null && cityStr.trim().length() > 0)
		{
			campus.setCity(cityStr);
		}
		//
		if (zipStr != null && zipStr.trim().length() > 0)
		{
			int zip = Integer.parseInt(zipStr);
			campus.setZip(zip);
		}
	}

	private void getParameters(ParameterValues parameters, Store store)
	{
		String idStr = parameters.getParameter("STORE_ID");
		String campusIdStr = parameters.getParameter("CAMPUS_ID");
		String nameStr = parameters.getParameter("NAME");
		String cityStr = parameters.getParameter("CITY");
		String addressStr = parameters.getParameter("ADDRESS");
		String phoneStr = parameters.getParameter("PHONE");
		String zipStr = parameters.getParameter("ZIP");

		if (idStr != null && idStr.trim().length() > 0)
		{
			int id = Integer.parseInt(idStr);
			store.setId(id);
		}
		//
		if (campusIdStr != null && campusIdStr.trim().length() > 0)
		{
			int campusId = Integer.parseInt(campusIdStr);
			store.setCampusId(campusId);
		}
		//
		if (nameStr != null && nameStr.trim().length() > 0)
		{
			store.setName(nameStr);
		}
		//
		if (cityStr != null && cityStr.trim().length() > 0)
		{
			store.setCity(cityStr);
		}
		//
		if (addressStr != null && addressStr.trim().length() > 0)
		{
			store.setAddress(addressStr);
		}
		//
		if (phoneStr != null && phoneStr.trim().length() > 0)
		{
			store.setPhone(phoneStr);
		}
		//
		if (zipStr != null && zipStr.trim().length() > 0)
		{
			int zip = Integer.parseInt(zipStr);
			store.setZip(zip);
		}
	}

	private void getParameters(ParameterValues parameters, Store store, List itemsList)
	{
		String idStr = parameters.getParameter("STORE_ID");
		//
		if (idStr != null && idStr.trim().length() > 0)
		{
			int id = Integer.parseInt(idStr);
			store.setId(id);
		}

		for (Iterator iter = itemsList.iterator(); iter.hasNext();)
		{
			Item item = (Item) iter.next();
			String itemPriceDollarStr = parameters.getParameter("ITEM_PRICE_DOLLAR_BY_ID_" + item.getId());
			String itemPriceCentStr = parameters.getParameter("ITEM_PRICE_CENT_BY_ID_" + item.getId());
			//
			int price = Integer.parseInt(itemPriceDollarStr + "." + itemPriceCentStr);
		}

	}

	private void getParameters(ParameterValues parameters, Item item)
	{
		String idStr = parameters.getParameter("ITEM_ID");
		String storeIdStr = parameters.getParameter("STORE_ID");
		String campusIdStr = parameters.getParameter("CAMPUS_ID");
		String productIdStr = parameters.getParameter("PRODUCT_ID");
		String priceDollarStr = parameters.getParameter("PRICE_DOLLAR");
		String priceCentStr = parameters.getParameter("PRICE_CENT");
		String quantityStr = parameters.getParameter("QUANTITY");
		String nameStr = parameters.getParameter("NAME");
		String brandStr = parameters.getParameter("BRAND");
		String typeStr = parameters.getParameter("TYPE");
		String containerStr = parameters.getParameter("CONTAINER");
		// String editedTimestampStr =
		// parameters.getParameter("EDITED_TIMESTAMP");
		String flaggedStr = parameters.getParameter("IS_FLAGGED");
		String popularStr = parameters.getParameter("IS_POPULAR");
		String staticPriceStr = parameters.getParameter("IS_STATIC_PRICE");
		String specialStr = parameters.getParameter("IS_SPECIAL");
		String specialPriceDollarStr = parameters.getParameter("SPECIAL_PRICE_DOLLAR");
		String specialPriceCentStr = parameters.getParameter("SPECIAL_PRICE_CENT");
		String specialStartStr = parameters.getParameter("SPECIAL_START");
		String specialStopStr = parameters.getParameter("SPECIAL_STOP");
		String submitterId = parameters.getParameter("SUBMITTER_ID");
		String storeName = parameters.getParameter("STORE_NAME");
		//
		if (idStr != null && idStr.trim().length() > 0)
		{
			int id = Integer.parseInt(idStr);
			item.setId(id);
		}
		//
		if (storeIdStr != null && storeIdStr.trim().length() > 0)
		{
			int storeId = Integer.parseInt(storeIdStr);
			item.setStoreId(storeId);
		}
		//
		if (campusIdStr != null && campusIdStr.trim().length() > 0)
		{
			int campusId = Integer.parseInt(campusIdStr);
			item.setCampusId(campusId);
		}
		//
		if (productIdStr != null && productIdStr.trim().length() > 0)
		{
			int productId = Integer.parseInt(productIdStr);
			item.setProductId(productId);
		}
		//
		if (priceDollarStr != null && priceDollarStr.trim().length() > 0 && priceCentStr != null && priceCentStr.trim().length() > 0)
		{
			int priceDollar = Integer.parseInt(priceDollarStr);
			if (priceDollar <= 0)
			{
				priceDollar = 0;
			}
			else
			{
				priceDollar = priceDollar * 100;
			}
			//
			int priceCent = Integer.parseInt(priceCentStr);
			if (priceCent < 0)
			{
				priceCent = 0;
			}
			item.setPrice(priceDollar + priceCent);
		}
		//
		if (quantityStr != null && quantityStr.trim().length() > 0)
		{
			int quantity = Integer.parseInt(quantityStr);
			item.setQuantity(quantity);
		}
		//
		if (nameStr != null && nameStr.trim().length() > 0)
		{
			item.setName(nameStr);
		}
		//
		if (brandStr != null && brandStr.trim().length() > 0)
		{
			item.setBrand(brandStr);
		}
		//
		if (typeStr != null && typeStr.trim().length() > 0)
		{
			item.setType(typeStr);
		}
		//
		if (containerStr != null && containerStr.trim().length() > 0)
		{
			item.setContainer(containerStr);
		}
		//
		if (flaggedStr != null && flaggedStr.trim().length() > 0)
		{
			boolean isFlagged = Boolean.parseBoolean(flaggedStr);
			item.setFlagged(isFlagged);
		}
		//
		if (popularStr != null && popularStr.trim().length() > 0)
		{
			boolean isPopular = Boolean.parseBoolean(popularStr);
			item.setPopular(isPopular);
		}
		//
		if (staticPriceStr != null && staticPriceStr.trim().length() > 0)
		{
			boolean isStaticPrice = Boolean.parseBoolean(staticPriceStr);
			item.setStaticPrice(isStaticPrice);
		}
		//
		if (specialStr != null && specialStr.trim().length() > 0)
		{
			boolean isSpecial = Boolean.parseBoolean(specialStr);
			item.setSpecial(isSpecial);
		}
		if (item.isSpecial())
		{
			if (specialStartStr != null && specialStartStr.trim().length() > 0)
			{
				item.setSpecialStart(specialStartStr);
			}
			if (specialStopStr != null && specialStopStr.trim().length() > 0)
			{
				item.setSpecialStop(specialStopStr);
			}
			if (specialPriceDollarStr != null && specialPriceDollarStr.trim().length() > 0 && specialPriceCentStr != null && specialPriceCentStr.trim().length() > 0)
			{
				int specialPriceDollar = Integer.parseInt(specialPriceDollarStr);
				if (specialPriceDollar <= 0)
				{
					specialPriceDollar = 0;
				}
				else
				{
					specialPriceDollar = specialPriceDollar * 100;
				}
				//
				int specialPriceCent = Integer.parseInt(specialPriceCentStr);
				if (specialPriceCent < 0)
				{
					specialPriceCent = 0;
				}
				//
				item.setSpecialPrice(specialPriceDollar + specialPriceCent);
			}
		}
		//
		if (submitterId != null && submitterId.trim().length() > 0)
		{
			item.setSubmitterId(submitterId);
		}
		//
		if (storeName != null && storeName.trim().length() > 0)
		{
			item.setStoreName(storeName);
		}
	}

	private void getParameters(ParameterValues parameters, Brand brand)
	{
		String idStr = parameters.getParameter("BRAND_ID");
		String nameStr = parameters.getParameter("BRAND_NAME");
		String typeStr = parameters.getParameter("BRAND_TYPE");

		if (idStr != null && idStr.trim().length() > 0)
		{
			int id = Integer.getInteger(idStr);
			brand.setId(id);
		}
		//
		if (nameStr != null && nameStr.trim().length() > 0)
		{
			brand.setName(nameStr);
		}
		//
		if (typeStr != null && typeStr.trim().length() > 0)
		{
			brand.setType(typeStr);
		}
	}

	private void getParameters(ParameterValues parameters, Product product)
	{
		String idStr = parameters.getParameter("PRODUCT_ID");
		String brandIdStr = parameters.getParameter("BRAND_ID");
		String nameStr = parameters.getParameter("PRODUCT_NAME");
		String brandNameStr = parameters.getParameter("BRAND_NAME");
		String typeStr = parameters.getParameter("PRODUCT_TYPE");
		String popularStr = parameters.getParameter("PRODUCT_POPULAR");

		if (idStr != null && idStr.trim().length() > 0)
		{
			int id = Integer.parseInt(idStr);
			product.setId(id);
		}
		//
		if (brandIdStr != null && brandIdStr.trim().length() > 0)
		{
			int brandId = Integer.parseInt(brandIdStr);
			product.setBrandId(brandId);
		}
		//
		if (nameStr != null && nameStr.trim().length() > 0)
		{
			product.setName(nameStr);
		}
		//
		if (brandNameStr != null && brandNameStr.trim().length() > 0)
		{
			product.setBrandName(brandNameStr);
		}
		//
		if (typeStr != null && typeStr.trim().length() > 0)
		{
			product.setType(typeStr);
		}
		//
		if (popularStr != null && popularStr.trim().length() > 0)
		{
			boolean isPopular = Boolean.parseBoolean(popularStr);
			product.setPopular(isPopular);
		}
	}

	private void getParameters(ParameterValues parameters, Search search)
	{
		String campusIdStr = parameters.getParameter("CAMPUS_ID");
		String storeIdStr = parameters.getParameter("STORE_ID");
		String brandIdStr = parameters.getParameter("BRAND_ID");
		String productIdStr = parameters.getParameter("PRODUCT_ID");
		String isStoreSearchStr = parameters.getParameter("IS_STORE_SEARCH");
		String isBrandSearchStr = parameters.getParameter("IS_PRODUCT_SEARCH");
		String isProductSearchStr = parameters.getParameter("IS_PRODUCT_SEARCH");
		String isItemSearchStr = parameters.getParameter("IS_ITEM_SEARCH");
		String isBeerStr = parameters.getParameter("IS_BEER");
		String isWineStr = parameters.getParameter("IS_WINE");
		String isLiquorStr = parameters.getParameter("IS_LIQUOR");
		String isChampagneStr = parameters.getParameter("IS_CHAMPAGNE");
		String isOtherStr = parameters.getParameter("IS_OTHER");
		String isSpecialOnlyStr = parameters.getParameter("IS_SPECIAL_ONLY");
		String searchByStr = parameters.getParameter("SEARCH_BY");
		String searchStringStr = parameters.getParameter("SEARCH_STRING");
		String[] containerArray = parameters.getParameters("CONTAINER_ARRAY");
		String[] quantityArray = parameters.getParameters("QUANTITY_ARRAY");
		String[] typeArray = parameters.getParameters("TYPE_ARRAY");

		if (campusIdStr != null && campusIdStr.trim().length() > 0)
		{
			int campusId = Integer.parseInt(campusIdStr);
			search.setCampusId(campusId);
		}
		if (storeIdStr != null && storeIdStr.trim().length() > 0)
		{
			int storeId = Integer.parseInt(storeIdStr);
			search.setStoreId(storeId);
		}
		if (brandIdStr != null && brandIdStr.trim().length() > 0)
		{
			int brandId = Integer.parseInt(brandIdStr);
			search.setBrandId(brandId);
		}
		if (productIdStr != null && productIdStr.trim().length() > 0)
		{
			int productId = Integer.parseInt(productIdStr);
			search.setProductId(productId);
		}
		//
		if (isStoreSearchStr != null && isStoreSearchStr.trim().length() > 0)
		{
			boolean isStoreSearch = Boolean.parseBoolean(isStoreSearchStr);
			search.setStoreSearch(isStoreSearch);
		}
		if (isBrandSearchStr != null && isBrandSearchStr.trim().length() > 0)
		{
			boolean isBrandSearch = Boolean.parseBoolean(isBrandSearchStr);
			search.setBrandSearch(isBrandSearch);
		}
		if (isProductSearchStr != null && isProductSearchStr.trim().length() > 0)
		{
			boolean isProductSearch = Boolean.parseBoolean(isProductSearchStr);
			search.setProductSearch(isProductSearch);
		}
		if (isItemSearchStr != null && isItemSearchStr.trim().length() > 0)
		{
			boolean isItemSearch = Boolean.parseBoolean(isItemSearchStr);
			search.setItemSearch(isItemSearch);
		}
		if (isBeerStr != null && isBeerStr.trim().length() > 0)
		{
			boolean isBeer = Boolean.parseBoolean(isBeerStr);
			search.setBeer(isBeer);
		}
		if (isWineStr != null && isWineStr.trim().length() > 0)
		{
			boolean isWine = Boolean.parseBoolean(isWineStr);
			search.setWine(isWine);
		}
		if (isLiquorStr != null && isLiquorStr.trim().length() > 0)
		{
			boolean isLiquor = Boolean.parseBoolean(isLiquorStr);
			search.setLiquor(isLiquor);
		}
		if (isChampagneStr != null && isChampagneStr.trim().length() > 0)
		{
			boolean isChampagne = Boolean.parseBoolean(isChampagneStr);
			search.setChampagne(isChampagne);
		}
		if (isOtherStr != null && isOtherStr.trim().length() > 0)
		{
			boolean isOther = Boolean.parseBoolean(isOtherStr);
			search.setOther(isOther);
		}
		if (isSpecialOnlyStr != null && isSpecialOnlyStr.trim().length() > 0)
		{
			boolean isSpecialOnly = Boolean.parseBoolean(isSpecialOnlyStr);
			search.setSpecialOnly(isSpecialOnly);
		}
		//
		if (searchByStr != null && searchByStr.trim().length() > 0)
		{
			search.setSearchBy(searchByStr);
		}
		if (searchStringStr != null && searchStringStr.trim().length() > 0)
		{
			ArrayList<String> searchStringList = search.getSearchStrings();
			while (searchStringStr.contains(" "))
			{
				int index = searchStringStr.indexOf(" ");
				//
				String newString = searchStringStr.substring(0, index);
				if (newString != null && newString.trim().length() > 0)
				{
					searchStringList.add(newString);
				}
				searchStringStr = searchStringStr.substring(index);
			}
			if (searchStringStr != null && searchStringStr.trim().length() > 0)
			{
				searchStringList.add(searchStringStr);
			}
		}
		//
		if(containerArray != null && containerArray.length > 0)
		{
			for(int index = 0; containerArray.length > index; index++)
			{
				search.getContainerStrings().add(containerArray[index]);
			}
		}
		//
		if(quantityArray != null && quantityArray.length > 0)
		{
			for(int index = 0; quantityArray.length > index; index++)
			{
				search.getQuantityStrings().add(quantityArray[index]);
			}
		}
		//
		if(typeArray != null && typeArray.length > 0)
		{
			for(int index = 0; typeArray.length > index; index++)
			{
				search.getTypeStrings().add(typeArray[index]);
			}
		}
	}
	
	
	private void getParameters(ParameterValues parameters, Submitter submitter)
	{
		String userIdStr = parameters.getParameter("USER_ID");
		String passwordStr = parameters.getParameter("PASSWORD");
		String authenticationCodeStr = parameters.getParameter("AUTHENTICATION_CODE");
		//
		if (userIdStr != null && userIdStr.trim().length() > 0)
		{
			submitter.setUserId(userIdStr);
		}
		//
		if (passwordStr != null && passwordStr.trim().length() > 0)
		{
			submitter.setPassword(passwordStr);
		}
		//
		if (authenticationCodeStr != null && authenticationCodeStr.trim().length() > 0)
		{
			int authenticationCode = Integer.parseInt(authenticationCodeStr);
			submitter.setAuthenticationCode(authenticationCode);
		}
	}
	
	private void populateListBy(ParameterValues parameters, List listBy)
	{
		String[] listByArray = parameters.getParameters("LIST_BY");
		//
		for (int index = 0; listByArray.length > index; index++)
		{
			listBy.add(listByArray[index]);
		}
	}

	private void setSearchBy(Search search)
	{
		search.setBrandSearch(false);
		search.setItemSearch(false);
		search.setProductSearch(false);
		search.setStoreSearch(false);
		//
		if (search.getSearchBy().equals(Constants.SEARCH_BY_BRAND)) ;
		{
			search.setBrandSearch(true);
		}
		if (search.getSearchBy().equals(Constants.SEARCH_BY_ITEM)) ;
		{
			search.setItemSearch(true);
		}
		if (search.getSearchBy().equals(Constants.SEARCH_BY_PRODUCT)) ;
		{
			search.setProductSearch(true);
		}
		if (search.getSearchBy().equals(Constants.SEARCH_BY_STORE)) ;
		{
			search.setStoreSearch(true);
		}
	}

	private List getQuantityList()
	{
		List list = new ArrayList();
		list.add("30");
		list.add("24");
		list.add("18");
		list.add("12");
		list.add("6");
		list.add("4");
		list.add("1");
		return list;
	}
	
	private List getContainerList()
	{
		List list = new ArrayList();
		list.add("cans");
		list.add("bottles");
		list.add("keg");
		return list;
	}
	
	public String buildHeader(Campus campus)
	{
		//List campuses = sqlFactory.getCampusList(campus);
		List campuses = new ArrayList();
		//
		Map stateMap = getStateMap();
		List stateList = new ArrayList();
		stateList.addAll(getStateList());
		//
		String campusLocation = "Campus Location &#xA0; &gt;&gt; &#xA0; ";
		//
		campusLocation += "<select name='STATE'>";
		for(Iterator iter = stateList.iterator(); iter.hasNext();)
		{
			String state = (String)iter.next();
			if(campus != null && campus.getState().equals(state))
			{
				campusLocation += "<option value='" + state +"' selected='selected'>";
			}
			else
			{
				campusLocation += "<option value='" + state +"'>";
			} 
			campusLocation += state + "</option>";
		}
		campusLocation += "</select>";
		//
		campusLocation += "&#xA0; &gt;&gt; &#xA0;";
		
		if (campuses != null && campuses.size() > 0)
		{
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
		}
		else
		{
			campusLocation += "<a href=\"javascript:document.search_form['ACTION'].value='ADD_CAMPUS';document.search_form.submit();\">Add Campus</a>";
		}
		
		//
		return campusLocation;
	}
	
	public Map getStateMap()
	{
		Map stateMap = new HashMap();
		//
		//	STATE MAP
		// 1
		stateMap.put("AL", "Alabama");
		stateMap.put("AK", "Alaska");
		stateMap.put("AZ", "Arizona");
		stateMap.put("AR", "Arkansas");
		stateMap.put("CA", "California");
		// 2
		stateMap.put("CO", "Colorado");		
		stateMap.put("CT", "Conneticut");
		stateMap.put("DE", "Delaware");
		stateMap.put("FL", "Florida");
		stateMap.put("GA", "Georgia");
		// 3
		stateMap.put("HI", "Hawaii");
		stateMap.put("ID", "Idaho");
		stateMap.put("IL", "Illinois");
		stateMap.put("IN", "Indiana");
		stateMap.put("IA", "Iowa");
		// 4
		stateMap.put("KS", "Kansas");
		stateMap.put("KY", "Kentucky");
		stateMap.put("LA", "Louisiana");
		stateMap.put("ME", "Maine");
		stateMap.put("MD", "Maryland");
		// 5
		stateMap.put("MA", "Massachusettes");
		stateMap.put("MI", "Michigan");
		stateMap.put("MN", "Minnesota");
		stateMap.put("MS", "Mississippi");
		stateMap.put("MO", "Missouri");
		// 6
		stateMap.put("MT", "Montana");
		stateMap.put("NE", "Nebraska");
		stateMap.put("NV", "Nevada");
		stateMap.put("NH", "New Hempshire");
		stateMap.put("NJ", "New Jersey");
		// 7
		stateMap.put("NM", "New Mexico");
		stateMap.put("NY", "New York");
		stateMap.put("NC", "North Carolina");
		stateMap.put("ND", "North Dakota");
		stateMap.put("OH", "Ohio");
		// 8
		stateMap.put("OK", "Oklahoma");
		stateMap.put("OR", "Oregon");
		stateMap.put("PA", "Pennsylvania");
		stateMap.put("RI", "Rhode Island");
		stateMap.put("SC", "South Carolina");
		// 9
		stateMap.put("SD", "South Dakota");
		stateMap.put("TN", "Tennessee");
		stateMap.put("TX", "Texas");
		stateMap.put("UT", "Utah");
		stateMap.put("VT", "Vermont");
		// 10
		stateMap.put("VA", "Virginia");
		stateMap.put("WA", "Washington");
		stateMap.put("WV", "West Virginia");
		stateMap.put("WI", "Wisconsin");
		stateMap.put("WY", "Wyoming");
		//
		return stateMap;
	}
	
	public List getStateList()
	{
		List stateList = new ArrayList();
		//
		//	STATE LIST
		// 1
		stateList.add("Alabama");
		stateList.add("Alaska");
		stateList.add("Arizona");
		stateList.add("Arkansas");
		stateList.add("California");
		// 2
		stateList.add("Colorado");		
		stateList.add("Conneticut");
		stateList.add("Delaware");
		stateList.add("Florida");
		stateList.add("Georgia");
		// 3
		stateList.add("Hawaii");
		stateList.add("Idaho");
		stateList.add("Illinois");
		stateList.add("Indiana");
		stateList.add("Iowa");
		// 4
		stateList.add("Kansas");
		stateList.add("Kentucky");
		stateList.add("Louisiana");
		stateList.add("Maine");
		stateList.add("Maryland");
		// 5
		stateList.add("Massachusettes");
		stateList.add("Michigan");
		stateList.add("Minnesota");
		stateList.add("Mississippi");
		stateList.add("Missouri");
		// 6
		stateList.add("Montana");
		stateList.add("Nebraska");
		stateList.add("Nevada");
		stateList.add("New Hempshire");
		stateList.add("New Jersey");
		// 7
		stateList.add("New Mexico");
		stateList.add("New York");
		stateList.add("North Carolina");
		stateList.add("North Dakota");
		stateList.add("Ohio");
		// 8
		stateList.add("Oklahoma");
		stateList.add("Oregon");
		stateList.add("Pennsylvania");
		stateList.add("Rhode Island");
		stateList.add("South Carolina");
		// 9
		stateList.add("South Dakota");
		stateList.add("Tennessee");
		stateList.add("Texas");
		stateList.add("Utah");
		stateList.add("Vermont");
		// 10
		stateList.add("Virginia");
		stateList.add("Washington");
		stateList.add("West Virginia");
		stateList.add("Wisconsin");
		stateList.add("Wyoming");
		//
		return stateList;
	}
}

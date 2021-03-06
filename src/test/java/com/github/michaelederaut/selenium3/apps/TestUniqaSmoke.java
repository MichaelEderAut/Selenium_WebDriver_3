package com.github.michaelederaut.selenium3.apps;

import java.io.IOException;
import java.util.Arrays;
// import java.util.HashSet;
import java.util.List;
// import java.util.Set;

import org.apache.logging.log4j.LogManager;
// import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebElement;
// import org.openqa.selenium.support.ui.ExpectedConditions;
// import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.github.michaelederaut.basics.ToolsBasics;
import com.github.michaelederaut.selenium3.framework.ByXp;
import com.github.michaelederaut.selenium3.framework.ByXp.Loc;
import com.github.michaelederaut.selenium3.framework.NavigationUtils;
import com.github.michaelederaut.selenium3.framework.NavigationUtils.ClickMode;
import com.github.michaelederaut.selenium3.framework.RemoteWebElementXp;
import com.github.michaelederaut.selenium3.framework.RemoteWebElementXp.LocatorSelector;
import com.github.michaelederaut.selenium3.platform.WaiterFactory;
import com.github.michaelederaut.selenium3.platform.XpathGenerators;
import com.github.michaelederaut.selenium3.platform.XpathGenerators.Locator;
import com.github.michaelederaut.selenium3.platform.XpathGenerators.LocatorVariant;
import com.github.michaelederaut.selenium3.sitelib.BrowserTypes;

import junit.framework.Assert;

public class TestUniqaSmoke {
   
	public static final String S_url_uniqa = "http://www.uniqa.at/";
	public static final String[] AS_class_presoftware_visible = {"presoftware", "visible"};
	public static final String S_class_name_sw_closer = "presoftware-closer";
	public static final String S_url_closing_cross = "https://webbot.cloud.uniqa.at/resources/icons/icons_close-md.svg";
	
	public static final String S_lnk_txt_uniqua_products_en = "Products";
	public static final String S_lnk_txt_software_and_it = "Software & IT";
//	public static final String[] AS_class_names_of_cookies_ok_button_2 = {"btn", "btn-primary"};
//	public static final String[] AS_class_names_of_cookies_ok_button_5 = {"btn", "btn-primary", "jb", "accept", "blue"};

	public static WebElement O_web_element_xchange;
	public static String S_sub_wdw_handle_xchange;
//	public static String S_parent_wdw_handle_xchange;
	
	@Test
	public static void main(String[] args) {
		IOException E_io;
		RuntimeException E_rt;
		RemoteWebDriver /*RemoteWebDriver */  O_rem_web_drv;
		WebDriverWait O_waiter;
		
		LocatorSelector O_loc_sel_1, O_loc_sel_1_native, O_loc_sel_2;
		List<RemoteWebElementXp>  AO_web_elements_navbar_dummy;
		RemoteWebElementXp  O_web_ele_uniqa_div_presoftware, 
		                    O_web_ele_uniqa_div_sw_closer,
		                    O_web_ele_uniqa_closing_cross,
		                    O_web_ele_home_page, O_web_element_kontakt, 
		                    O_web_element_name_input, O_web_element_tel_nr_input, O_web_element_email_input,
		                    O_web_element_betreff_input, O_web_element_nachricht_input, O_web_element_suche_input_field_xp;
		 
		RemoteWebElement O_rem_web_ele_1, O_rem_web_ele_1_native, O_rem_web_ele_2;
		WebElement       O_web_ele_native;
		

		Class O_clazz;
		Logger O_logger;
		
		String S_msg_1, S_parent_wdw_handle, S_parent_wdw_title, S_clazz_name_full, 
		S_found_by_1, S_found_by_1_native, S_found_by_2;
	//	Set<String> AS_sub_wdw_handles;
		int I_nbr_rows_f1, I_nbr_rows_f0;

		BrowserTypes  E_browser_type;
		NavigationUtils O_nav_utils;
		
		// I N I T 
		
		O_clazz = TestUniqaSmoke.class;
		S_clazz_name_full = O_clazz.getName();
		O_logger = LogManager.getLogger(O_clazz);
		
		O_logger.traceEntry();
			
        E_browser_type = BrowserTypes.Edge;
    	E_browser_type = BrowserTypes.InternetExplorer;
  // 	E_browser_type = BrowserTypes.FireFox;
  //  	E_browser_type = BrowserTypes.Chrome;

	    O_nav_utils = new NavigationUtils(E_browser_type);
	    
	    O_nav_utils.FO_get_fluent(
	    		S_url_uniqa,
	    		WaiterFactory.I_timeout_long * 10, 
	    		WaiterFactory.I_poll_std,
	    		NavigationUtils.FB_accecpt_equ_hostnames);
// W O R K	    
         S_parent_wdw_handle = NavigationUtils.O_rem_drv.getWindowHandle();
         S_parent_wdw_title =  NavigationUtils.O_rem_drv.getTitle();
         S_msg_1 = "Parent winwdow title: " + S_parent_wdw_title + "\'  --- handle: " + S_parent_wdw_handle + "\'";
         System.out.println(S_msg_1);
    
//	O_web_element_ivm_jobsite = NavigationUtils.O_rem_drv.findElementByLinkText(S_lnk_txt_ivm_job_site);
    
    O_web_ele_uniqa_div_presoftware = (RemoteWebElementXp)NavigationUtils.O_rem_drv.findElement(
    		ByXp.loc(Locator.className, LocatorVariant.and,
    				AS_class_presoftware_visible, 
    				"div"));
    if (O_web_ele_uniqa_div_presoftware == null) {
       if (E_browser_type == BrowserTypes.InternetExplorer) {
    	//   O_rem_web_ele_1 = (RemoteWebElement)NavigationUtils.O_rem_drv.findElementByCssSelector("div.presoftware.visible");
    	   O_rem_web_ele_1 = (RemoteWebElement) O_nav_utils.FO_find_fluent(
    			   ByXp.loc(Locator.cssSelector, "div.presoftware.visible"), 
    			   5000, 500);
    	   
    	   O_rem_web_ele_1_native = (RemoteWebElement) NavigationUtils.O_rem_drv.findElementByCssSelector("div.presoftware.visible");
    	   S_found_by_1_native = RemoteWebElementXp.FS_get_found_by(O_rem_web_ele_1_native);
    	   O_loc_sel_1_native =  RemoteWebElementXp.FO_get_loc_sel(S_found_by_1_native);
    	   O_rem_web_ele_2 = (RemoteWebElement)NavigationUtils.O_rem_drv.findElementByXPath("//div[@class='presoftware visible']");
    	   S_found_by_1 = RemoteWebElementXp.FS_get_found_by(O_rem_web_ele_1);
    	   O_loc_sel_1  = RemoteWebElementXp.FO_get_loc_sel(S_found_by_1);
    	   S_found_by_2 = RemoteWebElementXp.FS_get_found_by(O_rem_web_ele_2);
    	   O_loc_sel_2  = RemoteWebElementXp.FO_get_loc_sel(S_found_by_2);
           O_web_ele_uniqa_div_presoftware = (RemoteWebElementXp)NavigationUtils.O_rem_drv.findElement(
    	       ByXp.loc(Locator.className, LocatorVariant.and,
    				"presoftware", 
    				"div"));
            }}
    if (O_web_ele_uniqa_div_presoftware != null) {
    	O_web_ele_native = O_web_ele_uniqa_div_presoftware.findElementByCssSelector(".presoftware-closer");
        O_web_ele_uniqa_div_sw_closer = (RemoteWebElementXp)O_web_ele_uniqa_div_presoftware.findElement(Loc.loc(
    		  Locator.className, S_class_name_sw_closer, "div"));
       if (O_web_ele_uniqa_div_presoftware != null) {
		    O_web_ele_uniqa_closing_cross =  (RemoteWebElementXp)O_web_ele_uniqa_div_sw_closer.
		    		         findElement(ByXp.loc(Locator.src, S_url_closing_cross, "img"));
   
          // ToolsBasics.FV_sleep(1000); 
          //   O_nav_utils.FO_click_act(O_web_ele_breanos_prducts_2);
          //   ToolsBasics.FV_sleep(200);
          //   O_nav_utils.FO_click_act(O_web_ele_breanos_prducts_2);
          // TODO Click js
          NavigationUtils.FO_click_js(O_web_ele_uniqa_closing_cross, ClickMode.dispatchMouseEvent);
       }
    }
    
    ToolsBasics.FV_sleep(5000);
    
    O_web_ele_home_page = (RemoteWebElementXp)NavigationUtils.O_rem_drv.findElement(Loc.loc(
    		Locator.className, "navbar-brand", "a")).findElement(
    				ByXp.loc(Locator.alt, "Breanos GmbH", "img"));
    
    Assert.assertNotNull(O_web_ele_home_page);
    try {
		O_nav_utils.FO_click_act(O_web_ele_home_page);
	} catch (ElementNotInteractableException PI_E_element_not_interactible) {
	    S_msg_1 = "Problems when clicking on Breanos homepage.";
		E_rt = new RuntimeException(S_msg_1, PI_E_element_not_interactible);
		E_rt.printStackTrace(System.out);
	    }
    
    ToolsBasics.FV_sleep(2000);
    
    O_web_element_kontakt = (RemoteWebElementXp)NavigationUtils.O_rem_drv.findElement(Loc.loc(
    		Locator.linkText, "Kontakt", "a", 0, ".//"));
     Assert.assertNotNull(O_web_element_kontakt);
  
 //  O_nav_utils.FO_click_act(O_web_element_kontakt);
       O_nav_utils.FO_click_js(O_web_element_kontakt);
       ToolsBasics.FV_sleep(3000);
       O_web_element_name_input = (RemoteWebElementXp)NavigationUtils.O_rem_drv.findElement(Loc.loc(
    		Locator.id, "name193", "input"));
       Assert.assertNotNull(O_web_element_name_input);
       O_nav_utils.FO_sendkeys_act(O_web_element_name_input, "Michael Eder");
       
        O_web_element_tel_nr_input = (RemoteWebElementXp)NavigationUtils.O_rem_drv.findElement(Loc.loc(
    		Locator.id, "phone193", "input"));
         Assert.assertNotNull (O_web_element_tel_nr_input);
         O_nav_utils.FO_sendkeys_act(O_web_element_tel_nr_input, "+4312041466");
         
        O_web_element_email_input = (RemoteWebElementXp)NavigationUtils.O_rem_drv.findElement(Loc.loc(
    		Locator.id, "email193", "input"));
        Assert.assertNotNull (O_web_element_email_input);
        O_nav_utils.FO_sendkeys_act(O_web_element_email_input, "Michael.Eder.Vie@gmx.at");
        
        O_web_element_betreff_input = (RemoteWebElementXp)NavigationUtils.O_rem_drv.findElement(Loc.loc(
    		Locator.id, "input_custom_one193", "input"));
        Assert.assertNotNull (O_web_element_betreff_input);
        O_nav_utils.FO_sendkeys_act(O_web_element_betreff_input, "Neue Nachricht");
        
        O_web_element_nachricht_input = (RemoteWebElementXp)NavigationUtils.O_rem_drv.findElement(Loc.loc(
    		Locator.id, "message193", "textarea"));
        Assert.assertNotNull (O_web_element_nachricht_input);
        O_nav_utils.FO_sendkeys_act(O_web_element_nachricht_input, "Eine kurze Nachricht zu Testzwecken.");  
        
        ToolsBasics.FV_sleep(7000);
        
        O_web_ele_home_page = (RemoteWebElementXp)NavigationUtils.O_rem_drv.findElement(Loc.loc(
    		Locator.className, "navbar-brand", "a")).findElement(
    				ByXp.loc(Locator.alt, "Breanos GmbH", "img"));
    
    Assert.assertNotNull(O_web_ele_home_page);
     try {
		O_nav_utils.FO_click_act(O_web_ele_home_page);
	} catch (ElementNotInteractableException PI_E_element_not_interactible) {
		S_msg_1 = "Problems when clicking on Breanos homepage.";
		E_rt = new RuntimeException(S_msg_1, PI_E_element_not_interactible);
		E_rt.printStackTrace(System.out);
	    }
        
	 O_logger.trace("Test run completed");

	}

}

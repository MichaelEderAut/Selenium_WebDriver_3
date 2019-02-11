package com.github.michaelederaut.selenium3.framework;

import java.lang.reflect.Constructor;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.FileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebElement;

import com.github.michaelederaut.basics.props.PropertyContainer;
import com.github.michaelederaut.basics.props.PropertyContainerUtils;
import com.github.michaelederaut.selenium3.framework.RemoteWebElementXp.FoundBy;
import com.github.michaelederaut.selenium3.framework.RemoteWebElementXp.LocatorSelector;
import com.github.michaelederaut.selenium3.framework.RemoteWebElementXp.LocatorSelectorXp;
import com.github.michaelederaut.selenium3.platform.XpathConcatenator;
import com.github.michaelederaut.selenium3.platform.XpathGenerators;
import com.github.michaelederaut.selenium3.platform.CssSGenerators.ExtendedCssSelector;
import com.github.michaelederaut.selenium3.platform.CssSGenerators.LinkText;
import com.github.michaelederaut.selenium3.platform.XpathGenerators.DomOffset;
import com.github.michaelederaut.selenium3.platform.XpathGenerators.DomVectorExtendedSelector;
import com.github.michaelederaut.selenium3.platform.XpathGenerators.Locator;
import com.github.michaelederaut.selenium3.platform.XpathGenerators.LocatorVariant;

public class RemoteWebElementCssS extends RemoteWebElement {
	
	public DomVectorExtendedSelector SBO_xpath;
//	public StringBuffer SB_xpath_cummulated = new StringBuffer();
	public FoundBy      O_found_by;
	public int          I_nbr_duplicates_f1; // nbr elems with idential xpath
	public String       S_tag_received;
	public String       S_inner_txt;
	public String       S_txt_content; // Selenium: lintText, html >xxxxx<
	public String       S_lnk_txt;    
	public String       S_inner_html;
	public String       AAS_attrs[][], AAS_comp_styles[][], AAS_styles[][];
	public PropertyContainer O_attrs, O_comp_styles, O_styles;
	
	
	public static class LocatorSelectorCss extends LocatorSelector {
		public String                      S_tag_expected;
		public int                         I_idx_f0;
		public LinkText                    O_lnk_txt;
		public String                      S_prefix;
	
		public Constructor<? extends ByCssS> M_ctor;
		
	    protected static void FV_ctor (
				 final LocatorSelectorCss          PO_O_loc_sel_xp,
				 final String                      PI_S_tag_expected,
				 final LinkText                    PI_O_lnk_txt,
				 final int                         PI_I_idx_f0,
				 final String                      PI_S_prefix,
				 final Constructor<? extends ByCssS> PI_M_ctor) {

			 PO_O_loc_sel_xp.S_tag_expected     =  PI_S_tag_expected;
			 PO_O_loc_sel_xp.O_lnk_txt          =  PI_O_lnk_txt;
			 PO_O_loc_sel_xp.I_idx_f0           =  PI_I_idx_f0;
			 PO_O_loc_sel_xp.S_prefix           =  PI_S_prefix;
			
			 PO_O_loc_sel_xp.M_ctor             = PI_M_ctor;
		 }
		 
		 public LocatorSelectorCss (
			final String         PI_S_locator,
			final LocatorVariant PI_E_locator_variant,
			final DomVectorExtendedSelector   PI_O_using,
			final String         PI_S_tag_expected,
			final LinkText       PI_O_lnk_txt,
			final int            PI_I_idx_f0,
			final String         PI_S_prefix,
			final Constructor<? extends ByCssS> PI_M_ctor) {
			 
		
			super (PI_S_locator, PI_E_locator_variant, PI_O_using);
		    FV_ctor(this, PI_S_tag_expected, PI_O_lnk_txt, PI_I_idx_f0, PI_S_prefix, PI_M_ctor);
			return;
		 }
		 
		  public  LocatorSelectorCss (
	     			final Locator        PI_E_locator,
	     			final LocatorVariant PI_E_locator_variant,
	     			final DomVectorExtendedSelector  PI_O_using,
	     			final String         PI_S_tag_expected,
	     			final LinkText       PI_O_lnk_txt,
	     			final int            PI_I_idx_f0,
					final String         PI_S_prefix,
					final Constructor<? extends ByCssS> PI_M_ctor) { 
	    	  
	   		 super (PI_E_locator, PI_E_locator_variant, PI_O_using);
	   		 FV_ctor(this, PI_S_tag_expected, PI_O_lnk_txt, PI_I_idx_f0, PI_S_prefix, PI_M_ctor);
	   		 return;
	      }
     }
	//----------------------------
	// FoundBy
	public static class FoundBy {
		public SearchContext O_driver_info;
		// public Stack<? extends LocatorSelector> AO_by_locators;
		public Stack<LocatorSelector> AO_by_locators;
	
	   protected static void FV_ctor(
			final FoundBy         PO_O_found_by,
			final SearchContext   PI_O_driver_info,
			final Object          PI_O_locator,
			final LocatorVariant  PI_E_locator_variant,
			final DomVectorExtendedSelector  PI_O_using,
			final String          PI_S_tag_expected,
			final int             PI_I_idx_f0,
			final String          PI_S_prefix,
			final LinkText        PI_O_lnk_txt_expected,
			final Constructor<? extends ByCssS> PI_M_ctor) {
		 LocatorSelector O_loc_sel_css;
		
		
	    if (PI_O_driver_info != null) {
	    	PO_O_found_by.O_driver_info = PI_O_driver_info;
	       }
	    PO_O_found_by.AO_by_locators = new Stack<LocatorSelector>();
		if (PI_O_locator instanceof Locator) {
		    O_loc_sel_css = new LocatorSelectorCss((Locator)PI_O_locator, PI_E_locator_variant, PI_O_using, PI_S_tag_expected, PI_O_lnk_txt_expected, PI_I_idx_f0, PI_S_prefix, PI_M_ctor);
		    }
		else {  // locator as String eg "xpath", "id", "name", "value" ...
		    O_loc_sel_css = new LocatorSelectorCss((String)PI_O_locator, PI_E_locator_variant, PI_O_using, PI_S_tag_expected, PI_O_lnk_txt_expected, PI_I_idx_f0, PI_S_prefix, PI_M_ctor);
		    }
		PO_O_found_by.AO_by_locators.push(O_loc_sel_css);
	    return;
	}
	
	public FoundBy (
			final SearchContext   PI_O_driver_info,
			final String          PI_S_locator,
			final LocatorVariant  PI_E_locator_variant,
			final DomVectorExtendedSelector  PI_O_using,
			final String          PI_S_tag_expected,
			final int             PI_I_idx_f0,
			final String          PI_S_prefix,
			final LinkText        PI_O_lnk_txt_expected,
			final Constructor<? extends ByCssS> PI_M_ctor) {
		
		FV_ctor(
			this,
			PI_O_driver_info,
			PI_S_locator,
			PI_E_locator_variant,
			PI_O_using,
			PI_S_tag_expected,
			PI_I_idx_f0,
			PI_S_prefix,
			PI_O_lnk_txt_expected,
			PI_M_ctor);
		return;
	    }
	
	public FoundBy (
			final SearchContext   PI_O_driver_info,
			final Locator         PI_E_locator,
			final LocatorVariant  PI_E_locator_variant,
			final DomVectorExtendedSelector PI_O_using,
			final String          PI_S_tag_expected,
			final int             PI_I_idx_f0,
			final String          PI_S_prefix,
			final LinkText        PI_O_lnk_txt,
			final Constructor<? extends ByCssS> PI_M_ctor) {
		
		FV_ctor(
			this,
			PI_O_driver_info,
			PI_E_locator,
			PI_E_locator_variant,
			PI_O_using,
			PI_S_tag_expected,
		    PI_I_idx_f0,
			PI_S_prefix,
			PI_O_lnk_txt,
			PI_M_ctor);
		return;
	    }
	}
	
	//----------------------------
	
	public RemoteWebElementCssS() {
		super();
		return;
	    }
	
	public RemoteWebElementCssS(
			final WebElement      PI_O_web_ele,
			final Locator         PI_E_locator,
			final LocatorVariant  PI_E_locator_variant,
			final /* DomVectorExtendedSelector */ ExtendedCssSelector PI_O_using,
			final String          PI_S_tag_expected,
			final LinkText        PI_O_lnk_txt_expected,
			final int             PI_I_idx_f0,
			final String          PI_S_prefix,
			final Constructor<? extends ByCssS> PI_M_ctor,
			final DomOffset PI_AO_dom_offset_vector[],
			final int       PI_I_nbr_duplicates_f1,
			final String    PI_S_tag_received,
			final String    PI_S_inner_txt,
			final String    PI_S_txt_content,
			final String    PI_S_lnk_txt_found,
			final String    PI_S_inner_html,
			final ArrayList<ArrayList<String>> PI_AAS_attrs,
			final ArrayList<ArrayList<String>> PI_AAS_comp_style, // computed style
			final ArrayList<ArrayList<String>> PI_AAS_comp) {
		
		super();
		
		FV_ctor(
		   this,
		   PI_O_web_ele,
		   PI_E_locator,
		   PI_E_locator_variant,		
		   PI_O_using,	
		   PI_S_tag_expected,
		   PI_O_lnk_txt_expected,
		   PI_I_idx_f0,
		   PI_S_prefix,
		   PI_M_ctor,
		   PI_AO_dom_offset_vector,
		   PI_I_nbr_duplicates_f1,
		   PI_S_tag_received,
		   PI_S_inner_txt,
		   PI_S_txt_content,
		   PI_S_lnk_txt_found,
		   PI_S_inner_html,
		   PI_AAS_attrs,
		   PI_AAS_comp_style,  // computed style
		   PI_AAS_comp);
		
		return;
	}
	
	protected static void FV_ctor(
			final RemoteWebElementCssS PO_O_rem_web_elem_css,
			final WebElement         PI_O_web_elem,
			final Locator            PI_E_locator,
			final LocatorVariant     PI_E_locator_variant,
			final /* DomVectorExtendedSelector */ ExtendedCssSelector PI_O_using,
			final String             PI_S_tag_expected,
			final LinkText           PI_O_lnk_txt_expected,
			final int                PI_I_idx_f0,
			final String             PI_S_prefix,
			final Constructor<? extends ByCssS> PI_M_ctor, 
			final DomOffset PI_AO_dom_offset_vector[],
			final int       PI_I_nbr_duplicates_f1,
			final String    PI_S_tag_received,
			final String    PI_S_inner_txt,
			final String    PI_S_txt_content,
			final String    PI_S_lnk_txt_received,
			final String    PI_S_inner_html,
			final ArrayList<ArrayList<String>> PI_AAS_attrs,
			final ArrayList<ArrayList<String>> PI_AAS_comp_style,  // computed style
			final ArrayList<ArrayList<String>> PI_AAS_style) {
		
		Exception                E_cause;
		RuntimeException         E_rt;
		NullPointerException     E_np;
		
		FileDetector  O_file_detector;
		RemoteWebDriver   O_parent;
		
	    String        S_id, S_found_by;
		
	    RemoteWebElement   O_remote_web_elem;
		Object             O_intermediary;
		String S_msg_1, S_msg_2;
		int i1;
	    
		E_cause = null;
		if (PI_O_web_elem == null) {
			S_msg_1 = "Element of type: " +  WebElement.class.getName()  + " must not be null";
			E_cause = new IllegalArgumentException(S_msg_1);
		   }
		else if (!(PI_O_web_elem instanceof RemoteWebElement)) {
			S_msg_1 = "Unable to cast " + PI_O_web_elem.getClass().getName() + " to " + RemoteWebElement.class.getName();
			E_cause = new ClassCastException(S_msg_1);
		    }
		if (E_cause != null) {
			S_msg_2 = "Error obtaining object of type: " + WebElement.class.getName() + " from first method argument \'" + PI_O_web_elem + "\'." ;
			E_rt = new RuntimeException(S_msg_2, E_cause);
			throw E_rt;
		   }
		
		O_remote_web_elem = (RemoteWebElement)PI_O_web_elem;
		O_parent = RemoteWebElementXp.FO_get_parent_driver(O_remote_web_elem);
		
		E_cause = null;
		if (PI_E_locator == null) {
			S_msg_1 = "Argument for locator must not be null";
			E_cause = new NullPointerException(S_msg_1); 
			}
		else { 	
			PO_O_rem_web_elem_css.O_found_by = new FoundBy(
					O_parent, 
					PI_E_locator, 
					PI_E_locator_variant, 
					PI_O_using, 
					PI_S_tag_expected, 
					PI_I_idx_f0,
					PI_S_prefix,
				 PI_O_lnk_txt_expected,
					PI_M_ctor); 
		   }
		
		 if (E_cause != null) {  
			S_msg_2 = "Unable to determine type of locator." + System.lineSeparator() +
					  "must be a " + String.class.getName() + " or an enum " + Locator.class.getName() + ",";
            E_rt = new RuntimeException(S_msg_2, E_cause);
            throw E_rt;
		    }

		O_intermediary = null;
		try {
			O_intermediary = FieldUtils.readDeclaredField(O_remote_web_elem, RemoteWebElementXp.S_field_name_id, true);
		} catch (IllegalArgumentException | IllegalAccessException PI_E_ill_arg) {
			S_msg_1 = "Unable to read field: \'" + RemoteWebElementXp.S_field_name_id + "\' from object of type: \'" + O_remote_web_elem.getClass().getName() + "\""; 
			E_rt = new RuntimeException(S_msg_1, PI_E_ill_arg);
			throw E_rt;
		    }
		
		 if (O_intermediary == null) {
			 S_msg_1 = "Result field of type: \'" + String.class.getName() + "\' must not be null.";
			 E_np = new NullPointerException(S_msg_1);
			 S_msg_2 =  "Unable to get field: \'" +  RemoteWebElementXp.S_field_name_id + "\' from object of type: \'" + O_remote_web_elem.getClass().getName() + "\""; 
			 E_rt = new RuntimeException(S_msg_2, E_np);
			 throw E_rt;
		     }
		 
		 try {
			S_id = (String)O_intermediary;
		} catch (ClassCastException PI_E_clsc) {
			S_msg_1 = "Unable to obtain field: \'" + RemoteWebElementXp.S_field_name_id + "\' from object of type: \'" + O_intermediary.getClass().getName() + "\""; 
			E_rt = new RuntimeException(S_msg_1, PI_E_clsc);
			throw E_rt;
		    } 
		 
		try {
			O_intermediary = FieldUtils.readDeclaredField(O_remote_web_elem, RemoteWebElementXp.S_field_name_file_detector, true);
		} catch (IllegalArgumentException | IllegalAccessException PI_E_ill_arg) {
			S_msg_1 = "Unable to read field: \'" + RemoteWebElementXp.S_field_name_file_detector + "\' from object of type: \'" + O_remote_web_elem.getClass().getName() + "\""; 
			E_rt = new RuntimeException(S_msg_1, PI_E_ill_arg);
			throw E_rt;
		    }
		
		 if (O_intermediary == null) {
			 S_msg_1 = "Result field of type: \'" + FileDetector.class.getName() + "\' must not be null.";
			 E_np = new NullPointerException(S_msg_1);
			 S_msg_2 =  "Unable to get field: \'" + RemoteWebElementXp.S_field_name_file_detector + "\' from object of type: \'" + O_remote_web_elem.getClass().getName() + "\""; 
			 E_rt = new RuntimeException(S_msg_2, E_np);
			 throw E_rt;
		     }
		 
		 try {
			O_file_detector = (FileDetector)O_intermediary;
		 } catch (ClassCastException PI_E_clsc) {
			S_msg_1 = "Unable to obtain field: \'" + RemoteWebElementXp.S_field_name_file_detector + "\' from object of type: \'" + O_intermediary.getClass().getName() + "\""; 
			E_rt = new RuntimeException(S_msg_1, PI_E_clsc);
			throw E_rt;
		    } 

		 S_found_by = RemoteWebElementXp.FS_get_found_by(O_remote_web_elem);
		 O_parent = RemoteWebElementXp.FO_get_parent_driver(O_remote_web_elem);
		 
		 PO_O_rem_web_elem_css.setId(S_id);
		 PO_O_rem_web_elem_css.setFileDetector(O_file_detector);
		 PO_O_rem_web_elem_css.setParent(O_parent);
		 RemoteWebElementXp.FV_set_found_by(PO_O_rem_web_elem_css, S_found_by);
	     PO_O_rem_web_elem_css.SBO_xpath             = new DomVectorExtendedSelector(PI_AO_dom_offset_vector);
		
	//	 PO_O_rem_web_elem_css.SB_xpath_cummulated  = new StringBuffer(PI_O_using.FS_get_buffer());
		 
		 PO_O_rem_web_elem_css.I_nbr_duplicates_f1  = PI_I_nbr_duplicates_f1;
		 
		 PO_O_rem_web_elem_css.S_tag_received       = PI_S_tag_received;
		 PO_O_rem_web_elem_css.S_inner_txt          = PI_S_inner_txt;
		 PO_O_rem_web_elem_css.S_txt_content        = PI_S_txt_content;
		 PO_O_rem_web_elem_css.S_lnk_txt            = PI_S_lnk_txt_received;
		 PO_O_rem_web_elem_css.S_inner_html         = PI_S_inner_html;
		 if (PI_AAS_attrs != null) {
			PO_O_rem_web_elem_css.AAS_attrs = PropertyContainerUtils.FLLS_to_array(PI_AAS_attrs);
//			I_nbr_attrs_f1 = PI_AAS_attrs.size();
//			PO_O_rem_web_elem_xp.AAS_attrs = new String[I_nbr_attrs_f1][2];
//			for (i1 = 0; i1 < I_nbr_attrs_f1; i1++) {
//				AS_attr = PI_AAS_attrs.get(i1);
//				S_attr_name = AS_attr.get(0);
//				S_attr_val = AS_attr.get(1);
//				PO_O_rem_web_elem_xp.AAS_attrs[i1][0] = S_attr_name;
//			    PO_O_rem_web_elem_xp.AAS_attrs[i1][1] = S_attr_val;
//			 }
		 }
		 if (PI_AAS_comp_style != null) {
			 PO_O_rem_web_elem_css.AAS_comp_styles = PropertyContainerUtils.FLLS_to_array(PI_AAS_comp_style);
			 }
		 if (PI_AAS_style != null) {
			 PO_O_rem_web_elem_css.AAS_styles = PropertyContainerUtils.FLLS_to_array(PI_AAS_style); 
			}
		
		return;
	}
	
	//-----------------
	
	public static WebElement FO_find_element_by_css(
			final ByCssS PI_O_locator) {
	
	List<WebElement> AO_web_elements;
	int I_nbr_web_elements;
	WebElement O_retval_web_element = null;
	
	AO_web_elements = FAO_find_elements_by_css(PI_O_locator);
	if (AO_web_elements != null) {
		I_nbr_web_elements = AO_web_elements.size();
		if (I_nbr_web_elements >= 1) {
			O_retval_web_element = AO_web_elements.get(0);
		    }
	    }
	
	return O_retval_web_element;
	}
	
		public static List<WebElement> FAO_find_elements_by_css(
			final ByCssS PI_O_locator) {
		
	    LocatorSelectorCss O_by_locator_css;
	    LinkText O_lnk_txt;
	    XpathGenerators.LocatorVariant E_locator_variant;
		DomOffset  AO_DOM_offset_vector_requested[];
		/* DomVectorExtendedSelector */  ExtendedCssSelector SB_css_equivalent;
		AbstractMap<String, ? extends Object> HO_res_exec;
		
			
		StringBuilder     SB_document_root;	
	    String S_cmd_js_multiple, S_css_unindexed;
		Stack<WebElement> AO_retval_web_element = new Stack<WebElement>();
		
		RemoteWebDriver O_web_driver_parent;
		RemoteWebElement O_res_web_element;
		RemoteWebElementCssS O_res_web_element_css;
		StringBuilder SB_cmd_js_multiple;
		
		String S_web_driver_parent, S_found_by, S_lnk_txt_comp_operation, S_dom_node_name,
		       S_tag_received,  S_inner_txt, S_txt_content, S_lnk_txt, S_inner_html;
		Object O_res_exec, O_res_exec_elements_extended, O_res_extended_element, O_res_DOM_offset_vector,
		       O_res_attrs, O_res_comp_style, O_res_style;
		Integer IO_requested_idx_f0;
		long L_nbr_elems_f1, L_dom_idx_any_tag_f0, L_dom_idx_same_tag_f0;
		int i1, i2_up, i2_down, I_requested_idx_f0, I_nbr_returned_elems_f1, 
		    I_len_offset_vector_f1, I_dom_idx_f0_any_tag, I_dom_idx_f0_same_tag;
		boolean B_is_pure_css;
		
		ArrayList<Object> AO_res_exec_elements_extended, AO_res_vectors;
		ArrayList<Object> A_DOM_offset, AO_extended_element;
		ArrayList<ArrayList<Object>> AA_DOM_offset_vector;
		ArrayList<ArrayList<String>> AAS_attrs, AAS_comp_style, AAS_style;
		DomOffset AO_DOM_offset_vector_received[], O_DOM_offset_received;
		
		O_by_locator_css = PI_O_locator.O_loc_sel_css;
		O_lnk_txt = O_by_locator_css.O_lnk_txt;
		AO_DOM_offset_vector_requested = O_by_locator_css.SBO_using.AO_dom_offsets;
		
		SB_cmd_js_multiple = new StringBuilder();
		SB_document_root = RemoteWebElementXp.FS_generate_root_element(AO_DOM_offset_vector_requested);
		
		SB_css_equivalent = (ExtendedCssSelector)O_by_locator_css.SBO_using;
		S_css_unindexed = SB_css_equivalent.FS_get_buffer();
		I_requested_idx_f0 = O_by_locator_css.I_idx_f0;
		if (I_requested_idx_f0 == XpathGenerators.IGNORED_IDX) {
			I_requested_idx_f0 = 0;
		    }
		IO_requested_idx_f0 = Integer.valueOf(I_requested_idx_f0);
		
		// https://www.w3schools.com/jsref/prop_anchor_text.asp
		// https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/String/endsWith
		// https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/String/match
        // 'The quick brown fox jumps over the lazy dog. It barked.'.match('^.*$')
		// --> object array
		
		E_locator_variant = null;
		S_lnk_txt_comp_operation = null;
		if (O_lnk_txt != null) {
			E_locator_variant = O_lnk_txt.E_variant; 
			if (E_locator_variant == LocatorVariant.regular) {
				S_lnk_txt_comp_operation = "S_lnk_txt === S_comp_patt";
			    }
			else if (E_locator_variant == LocatorVariant.prefix) {
				S_lnk_txt_comp_operation = "S_lnk_txt.startsWith('S_comp_patt')";
			    }
			else if (E_locator_variant == LocatorVariant.regexp) {
				S_lnk_txt_comp_operation = "S_lnk_txt.match('S_comp_patt')";
			    }
			else if (E_locator_variant == LocatorVariant.partial) {
				S_lnk_txt_comp_operation = "S_lnk_txt.includes('S_comp_patt')";
			    }
			else if (E_locator_variant == LocatorVariant.suffix) {
				S_lnk_txt_comp_operation = "S_lnk_txt.endsWith('S_comp_patt')";
			   }
		    }
		if (NavigationUtils.O_rem_drv instanceof InternetExplorerDriver) {
			// inject polyfill here if necessary
			if (E_locator_variant == LocatorVariant.prefix) {  // startsWith()
				// stackoverflow.com/questions/30867172/code-not-running-in-ie-11-works-fine-in-chrome
		        SB_cmd_js_multiple.append(
	    		"if (!String.prototype.startsWith) { " +
                    "String.prototype.startsWith = function(searchString, position) { " +
                       "position = position || 0; " +
                       "return this.indexOf(searchString, position) === position;" +
                       "};}");
			     }
			else if (E_locator_variant == LocatorVariant.partial) {  // includes()
				// stackoverflow.com/questions/31119300/ie11-object-doesnt-support-property-or-method-includes-javascript-window
			   SB_cmd_js_multiple.append(
			   "if (!String.prototype.includes) {" +
                  "String.prototype.includes = function(search, start) { " +
                  "if (typeof start !== 'number') {" +
                      "start = 0; " +
                      "}" +
                  "if (start + search.length > this.length) { " +
                       "return false; " +
                  "} else { " +
                     "return this.indexOf(search, start) !== -1; " +
                  "}};}");
			      }
			 else if (E_locator_variant == LocatorVariant.suffix) { // endsWith()
				 // developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/String/endsWith
				  SB_cmd_js_multiple.append(
				  "if (!String.prototype.endsWith) { " +
	                  "String.prototype.endsWith = function(search, this_len) { " + 
		              "if (this_len === undefined || this_len > this.length) { " +
			              "this_len = this.length; " + 
		                  "} " +
		               "return this.substring(this_len - search.length, this_len) === search; " +
	                   "};}");
			      }
		       }
		SB_cmd_js_multiple.append(
			"var HS_retval = {'elemcount' : 0 , 'vector' : []}; " +
		    "var I_req_idx_f0 = arguments[0]; " +
			"var AAA_vectors = []; " +
		    "var AA_vectors_interim = []; " +
			"var AA_offs_vector, AAS_comp_styles, AAS_styles, AO_attrs; " +
		    "var i1, I_nbr_elems_f1, I_nbr_elems_interim_f1, I_nbr_elems_interim_f0, AO_vector_iterim, " + 
			    "I_nbr_prev_any_tag_f1, I_nbr_prev_same_tag_f1, I_node_type, " +
		        "B_add_this_node, B_cont_loop_prv, " +
		        "S_lnk_txt, S_node_name, S_tag_name, S_tag_name_prv, S_inner_txt, S_inner_html, S_txt_content, " + 
		        "O_elem, O_node_retval, O_node_retval, O_node_prev; " +
			"var AO_elems = []; " + 
		    "I_nbr_elemens_f1 = 0; " + 
			"I_nbr_elemens_interim_f1 = 0; " +
		    "AO_elems = document.querySelectorAll(\"" + S_css_unindexed  + "\"); " +
			"if (AO_elems) {" +
	            "I_nbr_elemens_interim_f1 = AO_elems.length;} " +
			"else { " +
	            "I_nbr_elemens_f1 = 0; " +
			    "} " + 
	         "for (i1 = 0; i1 < I_nbr_elemens_interim_f1; i1++) { " +
				  "O_elem = AO_elems[i1]; " +
	              "S_lnk_txt = O_elem.text; " +
				  "B_add_this_node = true; "); 
		if (O_lnk_txt != null) {
			SB_cmd_js_multiple.append(
					"if (!(" + S_lnk_txt_comp_operation + ")) {" +
		                "B_add_this_node = false; } ");
		     }   
		            SB_cmd_js_multiple.append(
			        "if (B_add_this_node) {" +
		                "AA_vectors_interim.push([O_elem, S_lnk_txt]); " +
			        "}}" +
		         "I_nbr_elems_interim_f1 = AA_vectors_interim.length; " + 
			     "I_nbr_elems_interim_f0 = I_nbr_elems_interim_f1 - 1; " +
			     "for (i1 = 0; i1 < I_nbr_elemens_interim_f1; i1++) { " +
		             "B_add_this_node = false;" +
			         "if (I_req_idx_f0 >= 0) { " +
		             "if (i1 == I_req_idx_f0) {" +
		                 "B_add_this_node = true; }" +
		                 "}" +
		             "else if (I_req_idx_f0 == " + XpathGenerators.ALL_IDX + ") { " + 
		                     "B_add_this_node = true; }" +
		             "else if (I_req_idx_f0 == " + XpathGenerators.LAST_IDX + ") { " + 
		                 "if (i1 == I_nbr_elemens_interim_f1) { " +
		                 "B_add_this_node = true; }}" +
		             "if (B_add_this_node) {" +         
		                 "AO_vector_iterim = AA_vectors_interim[i1]; " +
			             "O_node_retval = AO_vector_iterim[0]; " +
		                 "S_lnk_txt = AO_vector_iterim[1]; " +
		                 "AA_offs_vector = []; " +
		                 "AAS_styles = []; " +
                         "AAS_comp_styles = []; " +
		                 "O_node_current = O_node_retval; " +
                         "S_tag_name    = O_node_current.tagName; " +
		                 "S_inner_txt   = O_node_current.innerText; " +
		                 "S_txt_content = O_node_current.textContent; " + // Selenium: lintText, html >xxxxx< - but doesn't work for html-functions
		                 "S_inner_html  = O_node_current.innerHTML; " +
		                 "AO_attrs      = O_node_current.attributes; " +
		                 "O_comp_style  = window.getComputedStyle(O_node_current); " +
              	         "O_style = O_node_current.style; " +
		                 "LOOP_PARENTS: while (O_node_current) { " + // start calculating absolute xpath
			                 "I_node_type = O_node_current.nodeType; " +
		                     "if (I_node_type != 1) {"  +
//		                           "console.log('Node Type 2: ' + I_node_type); " +
		                           "break LOOP_PARENTS; } " +
		                     "S_node_name = O_node_current.nodeName; " +
//		                     "console.log('Node-Name 1: ' + S_node_name); " +    
		                     "if (S_node_name == 'BODY') { " +
		                         "break LOOP_PARENTS; } " +
		                     "if (S_node_name == 'HTML') { " +
		                         "break LOOP_PARENTS; } " +
		                     "if (S_node_name == '#document') {" +
		                         "break LOOP_PARENTS; } " +
		                     "B_cont_loop_prv = true; " +
		                     "I_nbr_prev_any_tag_f1  = 0; " +
		                     "I_nbr_prev_same_tag_f1 = 0; " +
		                     "O_node_prev = O_node_current; " +   
		                     "LOOP_PRV_SIBLINGS: while (B_cont_loop_prv) { " + 
			                     "O_node_prev = O_node_prev.previousElementSibling; " +
	//		                     "console.log('I_nbr_prev_any_tag 1: ' + I_nbr_prev_any_tag_f1); " +
			                     "if (!O_node_prev) {" +
	//		                         "console.log('I_nbr_prev 2: ' + I_nbr_prev_any_tag_f1); " +
			                         "break LOOP_PRV_SIBLINGS; } " +
			                     "I_nbr_prev_any_tag_f1++; " +
			                     "S_tag_name_prv = O_node_prev.tagName; " +   
			                     "if (S_tag_name_prv === S_node_name) { "+
			                        "I_nbr_prev_same_tag_f1++ ; }} " +  
		                     "AA_offs_vector.push([I_nbr_prev_any_tag_f1, S_node_name, I_nbr_prev_same_tag_f1]); " +
		                     "O_node_current = O_node_current.parentNode; " +
			                 "} " +
		                 "I_nbr_attrs_f1 = AO_attrs.length; " +  
			             "AAS_attrs = []; " +
             	         "for (i2 = 0; i2 < I_nbr_attrs_f1; i2++) { " +
        	                  "O_attr = AO_attrs[i2]; " +
        	                  "S_attr_name  = O_attr.name; " +
        	                  "S_attr_value = O_attr.value; " +
//        	                  "console.log('name: ' + S_attr_name + ' - value: ' + S_attr_value); " +
        	                  "AS_attr = [S_attr_name, S_attr_value]; " +
        	                  "AAS_attrs.push(AS_attr); " +
        	                  "} " +    
		                 "I_nbr_styles_f1 = O_comp_style.length; " +
//        	                  "console.log('Number of comp-styles: ' + I_nbr_styles_f1); " + 
                         "AAS_comp_styles = []; " +
        	             "for (i2 = 0; i2 < I_nbr_styles_f1; i2++) { " +
        	                     "S_style_key = O_comp_style[i2]; " +
//         	                     "console.log('Style-Key: ' + S_style_key); " + 
        	                    "S_style_val = O_comp_style.getPropertyValue(S_style_key); " +
//     	                        "console.log('name comp: ' + S_style_key + ' - value: ' + S_style_val); " +
//        	                     "console.log('Style-Val-Comp: ' + S_style_val); " + 
        	                     "AS_style = [S_style_key, S_style_val]; " +
                                 "AAS_comp_styles.push(AS_style); " +    // computed styles
        	                  "} " +  
        	                  "AAS_styles = []; " +
                              "for (S_style_key in O_style) {" +
                                 "S_style_val = O_style[S_style_key]; " +
                                 "if (S_style_val) {" +
                                    "S_style_val_str = S_style_val.toString(); " +
//        	                        "console.log('name: ' + S_style_key + ' - value: ' + S_style_val_str); " +
//        	                        "console.log('src: ' + S_style_val.toSource()); " +
                                    "AS_style = [S_style_key, S_style_val_str]; " +
                                    "AAS_styles.push(AS_style); " + 
                                 "}} " +
		                 "AAA_vectors.push([O_node_retval, AA_offs_vector, S_tag_name, S_inner_txt, S_txt_content, S_lnk_txt, S_inner_html, AAS_attrs, AAS_comp_styles, AAS_styles]); " +
		                 "}} " +
			 "HS_retval = {'"+  RemoteWebElementXp.S_key_name_elemcount + "' : I_nbr_elemens_interim_f1, '" + RemoteWebElementXp.S_key_name_vectors + "' : AAA_vectors}; " +
			 "return HS_retval;") ; 

		
		S_cmd_js_multiple = SB_cmd_js_multiple.toString();
        O_res_exec = NavigationUtils.O_rem_drv.executeScript(S_cmd_js_multiple, IO_requested_idx_f0);
        HO_res_exec = (AbstractMap<String, ? extends Object>)O_res_exec;
        L_nbr_elems_f1 = (Long)(HO_res_exec.get(RemoteWebElementXp.S_key_name_elemcount));
        O_res_exec_elements_extended  =  HO_res_exec.get(RemoteWebElementXp.S_key_name_vectors);
        AO_res_exec_elements_extended = (ArrayList<Object>)O_res_exec_elements_extended ;
        I_nbr_returned_elems_f1 = AO_res_exec_elements_extended.size();
        S_found_by = null;
        for (i1 = 0; i1 < I_nbr_returned_elems_f1; i1++) {
        	O_res_extended_element = AO_res_exec_elements_extended.get(i1);
        	AO_extended_element = (ArrayList<Object>)O_res_extended_element;
        	O_res_web_element = (RemoteWebElement)AO_extended_element.get(0);
        	if (i1 == 0) {
	        	O_web_driver_parent = RemoteWebElementXp.FO_get_parent_driver(O_res_web_element);
				S_web_driver_parent = O_web_driver_parent.toString();
				S_found_by = String.format("[%s] -> %s: %s", S_web_driver_parent, "css selector", S_css_unindexed);
        	    }
    		RemoteWebElementXp.FV_set_found_by(O_res_web_element, S_found_by);
    		O_res_DOM_offset_vector = AO_extended_element.get(1);  // get the absolute Xpath
    		AA_DOM_offset_vector = (ArrayList<ArrayList<Object>>)O_res_DOM_offset_vector;
			I_len_offset_vector_f1 = AA_DOM_offset_vector.size();
			AO_DOM_offset_vector_received = new DomOffset[I_len_offset_vector_f1];
			i2_down = I_len_offset_vector_f1 - 1;
			for (i2_up = 0; i2_up < I_len_offset_vector_f1; i2_up++) { 
				A_DOM_offset = AA_DOM_offset_vector.get(i2_up);
				L_dom_idx_any_tag_f0 = (Long)(A_DOM_offset.get(0));
				I_dom_idx_f0_any_tag = (int)L_dom_idx_any_tag_f0;
				S_dom_node_name = (String)(A_DOM_offset.get(1));
				O_DOM_offset_received = new DomOffset(I_dom_idx_f0_any_tag, S_dom_node_name);
				AO_DOM_offset_vector_received[i2_down] = O_DOM_offset_received;
				i2_down--;
				}
    		S_tag_received = (String)AO_extended_element.get(2);
    		S_inner_txt    = (String)AO_extended_element.get(3);
			S_txt_content  = (String)AO_extended_element.get(4);
			S_lnk_txt      = (String)AO_extended_element.get(5);
    		S_inner_html   = (String)AO_extended_element.get(6);
    		O_res_attrs   = AO_extended_element.get(7);
		    AAS_attrs     = (ArrayList<ArrayList<String>>)O_res_attrs;
			O_res_comp_style  = AO_extended_element.get(8);
			AAS_comp_style    = (ArrayList<ArrayList<String>>)O_res_comp_style;
			O_res_style   = AO_extended_element.get(9);
			AAS_style     = (ArrayList<ArrayList<String>>)O_res_style;
			
    		O_res_web_element_css = new RemoteWebElementCssS(
						O_res_web_element,
						O_by_locator_css.E_locator,
						O_by_locator_css.E_locator_variant,
						(ExtendedCssSelector)O_by_locator_css.SBO_using,
						O_by_locator_css.S_tag_expected,
						O_lnk_txt,
						O_by_locator_css.I_idx_f0,
						O_by_locator_css.S_prefix,
						O_by_locator_css.M_ctor,
						AO_DOM_offset_vector_received,
						(int)L_nbr_elems_f1,
						S_tag_received,
						S_inner_txt,
						S_txt_content,
						S_lnk_txt,
						S_inner_html,
						AAS_attrs,
						AAS_comp_style,
						AAS_style
						);
			
        	AO_retval_web_element.push(O_res_web_element_css);
            }
		return AO_retval_web_element; 
		}
	
}

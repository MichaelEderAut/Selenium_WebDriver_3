package com.github.michaelederaut.selenium3.framework;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

/**
 * @author <a href="mailto:michael.eder.vie@gmx.at?subject=github&nbsp;Selenium">Mr. Michael Eder</a>
 */
public abstract class ByCssS extends By {
	public static class Loc extends ByCssS {
		@Override
			public List<WebElement> findElements(SearchContext context) {	
			// TODO
			return null;
		}
		@Override
			public WebElement findElement(SearchContext context) {
			// TODO
			return null;
		}
	}
	  @Override
	  public boolean equals(final Object PI_O_other) {
		  return true;
	  }
	}
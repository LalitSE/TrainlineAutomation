package com.thetrainline.app;

import com.thetrainline.framework.selenium.Browser;
import com.thetrainline.framework.selenium.BrowserManager;

public abstract class AbstractApp {
	Browser browser;

	public AbstractApp() {
		this.browser = BrowserManager.getBrowser();
	}
	
	public void openApp(){
		browser.openURL(getURL());
	}
	
	public abstract String getURL();
}

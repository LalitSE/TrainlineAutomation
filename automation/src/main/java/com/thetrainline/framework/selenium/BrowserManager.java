package com.thetrainline.framework.selenium;

import com.thetrainline.framework.utils.Configuration;

public class BrowserManager {
	private static ThreadLocal<Browser> ThreadDriver = new ThreadLocal<Browser>();

	public static Browser getBrowser() {
		Browser browser = BrowserManager.ThreadDriver.get();
		if (browser == null) {
			browser = new Browser();
			browser.launchBrowser(Configuration.getInstance().getBrowser());
			ThreadDriver.set(browser);
		}
		return browser;
	}

	public static void closeBrowser() {
		getBrowser().closeBrowser();
		BrowserManager.ThreadDriver.set(null);
	}

}

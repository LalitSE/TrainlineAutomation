package com.thetrainline.framework.selenium;

import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import com.thetrainline.framework.utils.SleepUtils;

import ru.yandex.qatools.allure.annotations.Step;

public abstract class Page {
	
	static Logger log = Logger.getLogger(Page.class);
	
	public Browser browser;
	
	public Page(){
		this.browser = BrowserManager.getBrowser();
		PageFactory.initElements(browser.getDriver(), this);
	}
	
	public void sendKey(Keys key) {
		browser.getActionDriver().sendKeys(key).perform();
	}

	/**
	 * Wait till ajax requests is completed
	 */
	public void waitForAjaxRequestToLoad() {
		log.info("Waiting For Ajax Call to Finish...");
		SleepUtils.sleep(2);
		this.browser.getWebDriverWait().until(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver webDriver) {
				try {
					return ((Long) ((JavascriptExecutor) webDriver).executeScript("return jQuery.active")) == 0;
				} catch (Exception e) {
					return false;
				}
			}
			@Override
			public String toString() {
				return "Ajax Call complete";
			}
		});
	}

	public void waitForPageToLoad() {
		log.info("Waiting For Page To Load...");
		SleepUtils.sleep(2);
		this.browser.getWebDriverWait().until(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver webDriver) {
				try {
					return ((String) ((JavascriptExecutor) webDriver).executeScript("return document.readyState"))
							.equals("complete");
				} catch (Exception e) {
					return false;
				}
			}

			@Override
			public String toString() {
				return "Page load complete";
			}

		});
	}

	@Step("Verify Alert Message : {0}")
	public void verifyAndAcceptAlert(String message){
		browser.waitDriver.until(ExpectedConditions.alertIsPresent());
		Alert alert = browser.getDriver().switchTo().alert();
		Assert.assertEquals(alert.getText(),message, "Alert Message doesn't match.");
		alert.accept();
	}
}

package com.thetrainline.framework.selenium;

import org.apache.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import ru.yandex.qatools.allure.annotations.Step;

public abstract class BaseElement {

	static Logger log = Logger.getLogger(BaseElement.class);

	private Browser browser;
	private WebElement webElement;
	private String objectName;

	public BaseElement(WebElement element, String objectName) {
		this.browser = BrowserManager.getBrowser();
		this.setWebElement(element);
		this.setObjectName(objectName);
	}


	public WebElement getWebElement() {
		return webElement;
	}

	public void setWebElement(WebElement webElement) {
		this.webElement = webElement;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public Browser getBrowser() {
		return browser;
	}

	public void shouldExist() {
		log.info(String.format("Field : %s, Verification :Field Should Exists On The Page.", getObjectName()));
		try {
			webElement.getText();// dummy call
		} catch (NoSuchElementException | NullPointerException e) {
			String failureMessage = String.format(
					"Field : %s, Expected : Field should exists on the page. Actual : Field Doesn't exists on the page.",
					getObjectName());
			log.error(failureMessage);
			throw new AssertionError(failureMessage, new Throwable(failureMessage));
		}
	}


	/**
	 * Verify Text
	 * 
	 * @param text
	 */
	public void textShouldBe(String text) {
		textShouldBe(getObjectName(), text);
	}

	@Step("{0} should contain exact text : {1} ")
	private void textShouldBe(String objectName, String text) {
		log.info(String.format("Field : %s, Verification :Field Should have the text '%s'", getObjectName(), text));
		Assert.assertEquals(getText(), text, "Text Doesn't Match For The Field : " + objectName + ",");
	}
	
	
	/**
	 * Verify Text
	 * 
	 * @param text
	 */
	public void textShouldBe(String text,boolean ignorecase) {
		textShouldBe(getObjectName(), text,ignorecase);
	}

	@Step("{0} should contain exact text : {1}, Ignore case : {2}")
	private void textShouldBe(String objectName, String text,boolean ignorecase) {
		log.info(String.format("Field : %s, Verification :Field Should have the text '%s'", getObjectName(), text));
		Assert.assertEquals(getText().toLowerCase(), text.toLowerCase(), "Text Doesn't Match For The Field : " + objectName + ",");
	}
	

	protected boolean isEnabled() {
		shouldExist();
		return getWebElement().isEnabled();
	}

	protected boolean isSelected() {
		shouldExist();
		return getWebElement().isSelected();
	}

	/**
	 * Check if object is displayed
	 * 
	 * @return
	 */
	public boolean isDisplayed() {
		shouldExist();
		return getWebElement().isDisplayed();
	}

	public void shouldBeEnabled() {
		shouldBeEnabled(getObjectName());
	}

	@Step("Field : {0} should be enabled. ")
	private void shouldBeEnabled(String objectName) {
		log.info(String.format("Field : %s, Verification :Field Should Be Enabled.", objectName));
		Assert.assertTrue(isEnabled(), objectName + " Not Enabled.");
	}

	/**
	 * Element should be visible
	 */
	public void shouldBeVisible() {
		shouldBeVisible(getObjectName());
	}

	@Step("Field : {0} should be visible. ")
	private void shouldBeVisible(String objectName) {
		log.info(String.format("Field : %s, Verification :Field Should Be Visible.", objectName));
		Assert.assertTrue(isDisplayed(), objectName + " Not visible.");
	}


	/**
	 * 
	 * @return element text
	 */
	public String getText() {
		shouldExist();
		return getWebElement().getText();
	}

	public void waituntilVisible() {
		try{
		browser.getWebDriverWait().until(ExpectedConditions.visibilityOf(getWebElement()));
		}catch (TimeoutException e) {
			Assert.fail(getObjectName() + " Field is not visible.");
		}
	}

}

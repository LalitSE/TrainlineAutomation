package com.thetrainline.framework.html.elements;

import org.apache.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import ru.yandex.qatools.allure.annotations.Step;

public class SelectBox extends EditableElement {

	static Logger log = Logger.getLogger(SelectBox.class);

	private Select select;

	public SelectBox( WebElement webElement, String objectName) {
		super(webElement, objectName + " Dropdown");
		select = new Select(webElement);
	}

	/**
	 * Select Value From Dropdown
	 * 
	 * @param visbileText
	 */
	public void selectByVisibleText(String visbileText) {
		selectByVisibleText(getObjectName(), visbileText);
	}

	@Step("Select Value. Field : {0} , Value: {1}")
	private void selectByVisibleText(String objectName, String visibleText) {
		shouldExist();
		try {
			log.info(String.format("Select Value. Field : %s , Value: %s", objectName, visibleText));
			select.selectByVisibleText(visibleText);
		} catch (NoSuchElementException e) {
			Assert.fail(visibleText + " Option not available in dropdown : " + getObjectName());
		}

	}
}

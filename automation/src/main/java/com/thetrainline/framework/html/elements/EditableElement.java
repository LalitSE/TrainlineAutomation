package com.thetrainline.framework.html.elements;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;

import com.thetrainline.framework.selenium.BaseElement;

import ru.yandex.qatools.allure.annotations.Step;

public class EditableElement extends BaseElement {

	static Logger log = Logger.getLogger(EditableElement.class);

	public EditableElement(WebElement webElement, String objectName) {
		super(webElement, objectName);
	}

	/**
	 * Set Value
	 * 
	 * @param value
	 */
	public void setValue(String value) {
		setValue(getObjectName(), value);
	}

	@Step("Field : {0}, Enter Value : {1}")
	private void setValue(String objName, String value) {
		shouldExist();
		log.info(String.format("Field : %s, Action : Set Value, Value : %s .", objName, value));
		getWebElement().sendKeys(value);
	}
}

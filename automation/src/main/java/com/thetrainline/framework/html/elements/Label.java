package com.thetrainline.framework.html.elements;

import org.openqa.selenium.WebElement;

import com.thetrainline.framework.selenium.BaseElement;

public class Label extends BaseElement {

	public Label(WebElement webElement, String objectName) {
		super(webElement, objectName + " Label");
	}

}

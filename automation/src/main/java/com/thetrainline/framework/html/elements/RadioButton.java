package com.thetrainline.framework.html.elements;

import org.openqa.selenium.WebElement;

public class RadioButton extends ClickableElement {

	public RadioButton(WebElement webElement, String objectName) {
		super(webElement, objectName + " Radio Button");
	}

}

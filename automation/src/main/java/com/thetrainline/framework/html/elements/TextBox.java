package com.thetrainline.framework.html.elements;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;

public class TextBox extends EditableElement {

	static Logger log = Logger.getLogger(TextBox.class);

	public TextBox(WebElement webElement, String objectName) {
		super(webElement, objectName + " Text Box");
	}

}

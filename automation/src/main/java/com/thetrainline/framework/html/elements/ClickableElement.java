package com.thetrainline.framework.html.elements;

import org.apache.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;

import com.thetrainline.framework.selenium.BaseElement;

import ru.yandex.qatools.allure.annotations.Step;

public class ClickableElement extends BaseElement {

	static Logger log = Logger.getLogger(BaseElement.class);

	public ClickableElement(WebElement webElement, String objectName) {
		super(webElement, objectName);
	}

	/**
	 * Click on the Object
	 */
	public void click() {
		click(getObjectName(), ClickType.NORMAL);
	}

	/**
	 * Click on the Object
	 */
	public void click(ClickType clickType) {
		click(getObjectName(), clickType);
	}

	/**
	 * 
	 * @param objectName
	 */
	@Step("Field : {0}, Action : Click")
	private void click(String objectName, ClickType clickType) {
		shouldBeClickable();
		log.info(String.format("Field : %s, Action : Click, Click Type : %s", getObjectName(), clickType));
		if (clickType == ClickType.NORMAL) {
			getWebElement().click();
		} else if (clickType == ClickType.ACTION) {
			getBrowser().getActionDriver().moveToElement(getWebElement()).click().perform();
		} else if (clickType == ClickType.JAVASCRIPT) {
			getBrowser().getJSDriver().executeScript("arguments[0].click();", getWebElement());
		} else if (clickType == ClickType.DIMENSION) {
			Dimension elemenDimension = getWebElement().getSize();
			Point elementLocation = getWebElement().getLocation();
			int width = elemenDimension.getWidth();
			int height = elemenDimension.getHeight();
			int xCordinate = elementLocation.getX() + (width / 2);
			int yCordinate = elementLocation.getY() + (height / 2);
			log.debug(String.format("Dimension Click. X: %s, Y: %s", xCordinate, yCordinate));
			getBrowser().getActionDriver().moveByOffset(xCordinate, yCordinate).click().perform();
		}
	}

	/**
	 * Verify that object should be clickable.
	 */
	public void shouldBeClickable() {
		log.info(String.format("Field : %s, Verification :Field Should Be Clickable.", getObjectName()));
		shouldBeEnabled();
		shouldBeVisible();
	}


}

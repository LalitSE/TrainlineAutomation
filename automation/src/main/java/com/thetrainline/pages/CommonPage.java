package com.thetrainline.pages;


import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.thetrainline.framework.html.elements.Button;
import com.thetrainline.framework.html.elements.ClickType;
import com.thetrainline.framework.html.elements.Label;
import com.thetrainline.framework.selenium.Page;

public class CommonPage extends Page {

	@FindBy(css = "[data-test=\"cjs-price\"]")
	private WebElement totalPrice;
	
	@FindBy(css="[data-test=\"journey-summary-passengers-count\"]")
	private WebElement passengerCount;
	
	@FindBy(css="[data-test=\"cjs-button-continue\"]")
	private WebElement continueBtn;
			
	@FindBy(tagName="body")
	private WebElement bodyEle;
	
	public Label gettotalPriceElement() {
		return new Label(totalPrice, "Total Price");
	}
	
	public Label getPassengerCount() {
		return new Label(passengerCount, "Passenger Count");
	}
	
	public void clickOnContinueButton() {
		new Button(continueBtn,"Continue").click(ClickType.ACTION);
	}
	
	public Label getBody() {
		return new Label(bodyEle, "Body");
	}
	
	
}

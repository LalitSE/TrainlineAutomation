package com.thetrainline.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.thetrainline.framework.html.elements.Label;
import com.thetrainline.framework.selenium.Page;

public class CheckoutPage extends Page {

	@FindBy(css = "[data-test=\"time-and-changes-price\"]")
	private WebElement priceLabel;

	@FindBy(css = "[data-test=\"passengers\"]")
	private WebElement passengerCount;

	public Label getPriceLabel() {
		return new Label(priceLabel, "Price");
	}

	public Label getPassengerCount() {
		return new Label(passengerCount, "Passenger Count");
	}
}

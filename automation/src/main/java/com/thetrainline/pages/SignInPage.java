package com.thetrainline.pages;


import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.thetrainline.framework.html.elements.Button;
import com.thetrainline.framework.html.elements.TextBox;
import com.thetrainline.framework.selenium.Page;

public class SignInPage extends Page {

	
	@FindBy(id = "isGuest")
	private WebElement newCustomerRadioBtn;
	
	@FindBy(id="email")
	private WebElement email;
	
	@FindBy(css="[data-test=\"login-submit-button\"]")
	private WebElement continueBtn;

	public Button getNewCustomerBtn() {
		return new Button(newCustomerRadioBtn, "I'm a new customer");
	}
	
	public TextBox getEmailTextBox() {
		return new TextBox(email, "Email");
	}
	
	public Button getContinueBtn() {
		return new Button(continueBtn, "Continue");
	}
	public void signInAsNewCustomer() {
		getNewCustomerBtn().click();
		getEmailTextBox().setValue(RandomStringUtils.randomAlphabetic(12)+"@gmail.com");
		getContinueBtn().click();
		
	}
	
}

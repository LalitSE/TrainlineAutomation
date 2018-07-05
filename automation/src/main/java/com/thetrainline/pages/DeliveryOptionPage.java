package com.thetrainline.pages;


import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.thetrainline.framework.html.elements.TextBox;
import com.thetrainline.framework.utils.SleepUtils;

public class DeliveryOptionPage extends CommonPage {

	static Logger log = Logger.getLogger(DeliveryOptionPage.class);
	
	@FindBy(css="[data-test=\"mTicket-traveller-name-field\"]")
	private WebElement travellerName;
	
	
	public TextBox getTravellerNameTextBox() {
		return new TextBox(travellerName, "Traveller Name");
	}
	
	public void enterTravellerNameIfFieldAvailable() {
		SleepUtils.sleep(3);
		if(getBody().getText().contains("Skip queues with our mobile app")){
			log.info("Entering Traveller Name");
			getTravellerNameTextBox().waituntilVisible();
			getTravellerNameTextBox().setValue(RandomStringUtils.randomAlphabetic(8));
		}
		
	}
	
}

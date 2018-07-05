package com.thetrainline.pages;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

import com.thetrainline.framework.html.elements.Button;
import com.thetrainline.framework.html.elements.RadioButton;
import com.thetrainline.framework.utils.RandomUtils;
import com.thetrainline.framework.utils.SleepUtils;

public class MatrixPage extends CommonPage {

	static Logger log = Logger.getLogger(MatrixPage.class);
	@FindBys({
			@FindBy(xpath = "//*[contains(@data-test,'matrix-alternative-price-cheapest-standard-outward') or contains(@data-test,'matrix-alternative-price-cheapest-first-outward')]") })
	List<WebElement> ticketOptions;

	@FindBy(xpath = "//*[@id='seatResOutwardNo']/..")
	private WebElement noSeatReservationBtn;

	public String selectTicketRandomlyFromAvailableOptions() {
		WebElement tickeOption = RandomUtils.getRandomFromList(ticketOptions);
		String ticketPrice = tickeOption.getText();
		String ticketType = tickeOption
				.findElement(By.xpath("./ancestor::div[contains(@data-test,'cheapest-')][1]/div[1]"))
				.getAttribute("innerText");

		RadioButton raidoBtn = new RadioButton(tickeOption.findElement(By.xpath("./..//input")),
				ticketType + " " + ticketPrice);
		raidoBtn.click();
		return ticketPrice;
	}

	public Button getNoSeatReservationBtn() {
		return new Button(noSeatReservationBtn, "No Seat Reservation");
	}

	public void noSeatReservation() {
		SleepUtils.sleep(3);
		if (getBody().getText().contains("Would you like to reserve a seat?")) {
			log.info("Clicking On No Reservation");
			getNoSeatReservationBtn().click();
		}

	}

}

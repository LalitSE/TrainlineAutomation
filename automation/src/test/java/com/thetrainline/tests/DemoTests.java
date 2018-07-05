package com.thetrainline.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.thetrainline.app.TrainlineApp;
import com.thetrainline.basetests.BaseTest;
import com.thetrainline.framework.selenium.BrowserManager;
import com.thetrainline.framework.utils.SleepUtils;
import com.thetrainline.pages.CheckoutPage;
import com.thetrainline.pages.DeliveryOptionPage;
import com.thetrainline.pages.MatrixPage;
import com.thetrainline.pages.SearchPage;
import com.thetrainline.pages.SignInPage;

import ru.yandex.qatools.allure.annotations.Description;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Severity;
import ru.yandex.qatools.allure.annotations.Title;
import ru.yandex.qatools.allure.model.SeverityLevel;

public class DemoTests extends BaseTest {

	@BeforeMethod
	public void loadHomePage() {
		browser = BrowserManager.getBrowser();
		browser.setPageLoadTimeOut(config.getPageLoadTimeOut());
		browser.setImpilicitTimeOut(config.getImplicitWait());
		browser.setExplicitWait(config.getExplicitWait());
		TrainlineApp trainlineApp = new TrainlineApp();
		trainlineApp.openApp();
	}
	
	@AfterMethod
	public void closeBrowser() {
		BrowserManager.closeBrowser();
	}

	private final String FROM_LOCATION = "London";
	private final String TO_LOCATION = "Manchester";

	@Severity(SeverityLevel.CRITICAL)
	@Title("An Adult and children should be able to book a ticket")
	@Description("An Adult and children should be able to book a ticket from London To Manchester")
	@Features({ "Book Ticket" })
	@Test(priority = 0,dataProvider="passengerDetails")
	public void bookATicket(String noOfAdults, String noOfChildren) {
		SearchPage searchPage = new SearchPage();
		searchPage.searchTicket(FROM_LOCATION, TO_LOCATION, noOfAdults, noOfChildren);
		searchPage.waitForPageToLoad();

		MatrixPage matrixPage = new MatrixPage();
		String ticketPrice = matrixPage.selectTicketRandomlyFromAvailableOptions();

		matrixPage.gettotalPriceElement().textShouldBe(ticketPrice);
		StringBuilder passengerCountString = new StringBuilder();
		if(Integer.valueOf(noOfAdults) != 0) {
			passengerCountString.append(noOfAdults + " adult");
		}
		
		if(Integer.valueOf(noOfChildren) != 0) {
			passengerCountString.append(noOfChildren + " child");
		}
		
		matrixPage.getPassengerCount().textShouldBe(passengerCountString.toString());
		SleepUtils.sleep(3);
		matrixPage.clickOnContinueButton();
		matrixPage.waitForPageToLoad();
		matrixPage.noSeatReservation();
		matrixPage.clickOnContinueButton();
		
		matrixPage.waitForPageToLoad();
		SignInPage signInPage = new SignInPage();
		signInPage.signInAsNewCustomer();
		
		DeliveryOptionPage deliveryOptionPag = new DeliveryOptionPage();
		deliveryOptionPag.enterTravellerNameIfFieldAvailable();
		matrixPage.clickOnContinueButton();
		matrixPage.waitForPageToLoad();
		
		CheckoutPage checkoutPage = new CheckoutPage();
		checkoutPage.getPriceLabel().textShouldBe(ticketPrice);
		checkoutPage.getPassengerCount().textShouldBe(passengerCountString.toString());
	}

	
	  @DataProvider(name = "passengerDetails")
	  public static Object[][] passengerDetails() {
	        return new Object[][] { { "1", "0" }, { "0", "1" }};
	 
	  }
}
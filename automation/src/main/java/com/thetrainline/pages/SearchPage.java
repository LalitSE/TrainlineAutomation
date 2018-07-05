package com.thetrainline.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

import com.thetrainline.framework.html.elements.Button;
import com.thetrainline.framework.html.elements.ClickableElement;
import com.thetrainline.framework.html.elements.SelectBox;
import com.thetrainline.framework.html.elements.TextBox;
import com.thetrainline.framework.selenium.Page;

public class SearchPage extends Page {

	@FindBy(id = "from.text")
	private WebElement fromLocation;

	@FindBys({ @FindBy(css = "#stations_from li") })
	List<WebElement> fromLocationSuggestionsList;

	@FindBy(id = "to.text")
	private WebElement toLocation;

	@FindBys({ @FindBy(css = "#stations_to li") })
	List<WebElement> toLocationSuggestionsList;

	@FindBy(id = "passenger-summary-btn")
	private WebElement passengerSummaryBtn;

	@FindBy(name = "adults")
	private WebElement adultsDropDown;

	@FindBy(name = "children")
	private WebElement childrenDropDown;

	@FindBy(css = "[data-test='passenger-summary-btn-done']")
	private WebElement passengetSummaryDoneBtn;

	@FindBy(css = "[data-test='submit-journey-search-button']")
	private WebElement submitJourneySearchBtn;

	public TextBox getFromLocation() {
		return new TextBox(fromLocation, "From Location");
	}

	public List<ClickableElement> getFromLocationSuggestions() {
		List<ClickableElement> fromLocationSuggestionsElementList = new ArrayList<>();
		for (WebElement element : fromLocationSuggestionsList) {
			fromLocationSuggestionsElementList.add(new ClickableElement(element, element.getText()));
		}
		return fromLocationSuggestionsElementList;
	}

	public TextBox getToLocation() {
		return new TextBox(toLocation, "To Location");
	}

	public List<ClickableElement> getToLocationSuggestions() {
		List<ClickableElement> toLocationSuggestionsElementList = new ArrayList<>();
		for (WebElement element : toLocationSuggestionsList) {
			toLocationSuggestionsElementList.add(new ClickableElement(element, element.getText()));
		}
		return toLocationSuggestionsElementList;
	}

	public Button getPassengerSummaryBtn() {
		return new Button(passengerSummaryBtn, "Passenger Summary");
	}

	public SelectBox getAdultDropdown() {
		return new SelectBox(adultsDropDown, "Adult");
	}

	public SelectBox getChildrentDropdown() {
		return new SelectBox(childrenDropDown, "Children");
	}

	public Button getPassengerSummaryDoneBtn() {
		return new Button(passengetSummaryDoneBtn, "Passenger Summary Done");
	}

	public Button getTimeAndTicketBtn() {
		return new Button(submitJourneySearchBtn, "Get times & tickets");
	}

	public void searchTicket(String fromLocation, String toLocation, String noOfAdult, String noOfChildren) {
		getFromLocation().setValue(fromLocation);

		for (ClickableElement fromSuggestionElement : getFromLocationSuggestions()) {
			if (fromSuggestionElement.getText().contains(fromLocation)) {
				fromSuggestionElement.click();
				break;
			}
		}

		getToLocation().setValue(toLocation);
		for (ClickableElement toSuggestionElement : getToLocationSuggestions()) {
			if (toSuggestionElement.getText().contains(toLocation)) {
				toSuggestionElement.click();
				break;
			}
		}

		getPassengerSummaryBtn().click();

		getAdultDropdown().waituntilVisible();

		getAdultDropdown().selectByVisibleText(noOfAdult);

		getChildrentDropdown().selectByVisibleText(noOfChildren);

		getPassengerSummaryDoneBtn().click();

		getTimeAndTicketBtn().click();
	}

}

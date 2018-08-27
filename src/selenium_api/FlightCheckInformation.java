package selenium_api;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class FlightCheckInformation {
	WebDriver driver;

	@Test()
	@Parameters({ "flight_fromairport", "flight_toairport", "flight_checkindate", "selectagencyintbound" })
	public void TC_Flight_CheckAllInformation(String flight_fromairport, String flight_toairport,
			String flight_checkindate, String selectagencyintbound) throws Exception {

		driver.findElement(By.xpath("//div[contains(text(),'Vé máy bay')]")).click();

		WebElement eFromAirport = driver.findElement(By.id("flight-from-airport-value"));
		eFromAirport.sendKeys(flight_fromairport);
		Thread.sleep(2000);
		eFromAirport.sendKeys(Keys.RETURN);

		WebElement eToAirport = driver.findElement(By.id("flight-to-airport-value"));
		eToAirport.sendKeys(flight_toairport);
		Thread.sleep(2000);
		eToAirport.sendKeys(Keys.RETURN);

		WebElement depaturedate = driver.findElement(By.xpath("//input[@id='flight-checkin-date']"));
		depaturedate.click();
		selectdatepicker(flight_checkindate);

		WebElement searchbutton = driver
				.findElement(By.xpath("//button[@class='flight-search-button btn btn-search']"));
		searchbutton.click();
		Thread.sleep(2000);

		WebElement outBoundTicketsDiv = driver.findElement(By.cssSelector("#outBoundTickets"));
		List<WebElement> outboundTickets = outBoundTicketsDiv.findElements(By.cssSelector(".ticket-info"));
		boolean Selected = false;
		for (WebElement tickets : outboundTickets) {
			WebElement logo = tickets.findElement(By.cssSelector(".alogo"));
			String agency = logo.getAttribute("alt");

			if (agency.contains(selectagencyintbound)) {
				WebElement selectBtn = tickets.findElement(By.cssSelector(".flight-select-single-ticket"));
				selectBtn.click();
				Selected = true;
				break;
			}
		}
		if (!Selected) {
			Assert.fail("Không có vé. Vui lòng chọn vé khác!");
		}
		
		
		
		
		
		
		// Click on confirm button
		WebElement comfirmbtn = driver.findElement(By.cssSelector(".flight-search-booking-ticket"));
		comfirmbtn.click();
		Thread.sleep(10000);	
	}

	public void selectdatepicker(String date) {
		WebElement dateWidget = driver.findElement(By.className("startHoliday"));
		List<WebElement> columns = dateWidget.findElements(By.tagName("td"));
		for (WebElement cell : columns) {
			if (cell.getText().equals(date)) {
				cell.click();
				break;
			}
		}
	}

	@BeforeClass
	public void beforeClass() {
		driver = new FirefoxDriver();
		driver.get("https://www.tripi.vn/");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@AfterClass
	public void afterClass() {
		driver.quit();

	}

}
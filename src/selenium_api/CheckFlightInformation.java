package selenium_api;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class CheckFlightInformation {
	WebDriver driver;

	@Test()
	@Parameters({ "flightfromairportv", "flighttoairportv", "flightcheckindatev", "selectagencyintbound" })
	public void TC_01_Validatesamedestination(String flightfromairportv, String flighttoairportv,
			String flightcheckindatev, String selectagencyintbound) throws Exception {

		driver.findElement(By.xpath("//div[contains(text(),'Vé máy bay')]")).click();

		WebElement eFromAirport = driver.findElement(By.id("flight-from-airport-value"));
		eFromAirport.sendKeys(flightfromairportv);
		Thread.sleep(2000);
		eFromAirport.sendKeys(Keys.RETURN);

		WebElement eToAirport = driver.findElement(By.id("flight-to-airport-value"));
		eToAirport.sendKeys(flighttoairportv);
		Thread.sleep(2000);
		eToAirport.sendKeys(Keys.RETURN);

		WebElement depaturedate = driver.findElement(By.xpath("//input[@id='flight-checkin-date']"));
		depaturedate.click();
		selectdatepicker(flightcheckindatev);

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
			}
			if (!Selected) {
				Assert.fail("Không có vé. Vui lòng chọn vé khác!");
			}
		}

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
		// driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@AfterClass
	public void afterClass() {
		driver.quit();

	}

}
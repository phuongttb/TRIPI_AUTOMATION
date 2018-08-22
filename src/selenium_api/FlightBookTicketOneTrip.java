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

public class FlightBookTicketOneTrip {
	WebDriver driver;

	@Test()
	@Parameters({ "flightfromairportv", "flighttoairportv", "flightcheckindatev", "selectagencyintbound", "LastName",
			"FirstName", "Baggage", "Gender", "GenderContact", "LastNameContact", "FirstNameContact", "Email",
			"phone" })
	public void TC_01_BookingTicketOneTrip(String flightfromairportv, String flighttoairportv,
			String flightcheckindatev, String selectagencyintbound, String LastName, String FirstName, String Baggage,
			String Gender, String GenderContact, String LastNameContact, String FirstNameContact, String Email,
			String phone) throws Exception {

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
		// Đi tới màn hình thanh toán
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(0));
		// Thread.sleep(1000);
		// Fill thông tin khách hàng
		driver.findElement(By.cssSelector("input[ng-model='adult.lastName']")).sendKeys(LastName);
		Thread.sleep(1000);
		driver.findElement(By.xpath("//input[contains(@class,'first-name')]")).sendKeys(FirstName);
		// CHỌN GIỚI TÍNH KHÁCH HÀNG
		new Select(driver.findElement(By.xpath("(//select[@ng-model='adult.gender'])[1]"))).selectByVisibleText(Gender);
		// Chọn hành lý
		WebElement selectBox = driver.findElement(By.xpath("//select[@ng-model='adult.outboundBaggageId']"));
		Select X = new Select(selectBox);
		List<WebElement> options = selectBox.findElements(By.tagName("option"));
		int index = 0;
		for (WebElement option : options) {
			String value = option.getText();
			if (value.contains(Baggage)) {
				X.selectByIndex(index);
			}
			index++;
		}

		// NHẬP THÔNG TIN LIÊN HỆ
		new Select(driver.findElement(By.id("ticket-booking-select-title"))).selectByVisibleText(GenderContact);
		driver.findElement(By.xpath("//input[@ng-model='contactInfo.lastName']")).sendKeys(LastNameContact);
		driver.findElement(By.xpath("//input[@ng-model='contactInfo.firstName']")).sendKeys(FirstNameContact);
		driver.findElement(By.xpath("//input[@ng-model='contactInfo.email']")).sendKeys(Email);
		driver.findElement(By.xpath("//input[@ng-model='contactInfo.phone1']")).sendKeys(phone);

		// //Select payment method - trip credit
		WebElement paymentmethod = driver.findElement(By.cssSelector("#payment-method-3"));
		paymentmethod.click();
		Thread.sleep(1000);

		WebElement paymentbtn = driver.findElement(By.cssSelector(".flight-payment-button"));
		paymentbtn.click();
		Thread.sleep(2000);

		WebElement confirmbtn = driver.findElement(By.cssSelector(".btn-success"));
		confirmbtn.click();
		for(int i=0; i< 10; i++) {
			String currentURL = driver.getCurrentUrl();
			if(currentURL.contains("booking")) {
				Thread.sleep(5000);
			}else {
				break;
			}
		}
		String currentURL = driver.getCurrentUrl();
		boolean redirectURL = currentURL.contains("https://pay.vnpay.vn/Transaction/PaymentMethod.html?token=");
		System.out.print("Đi tới cổng thanh toán thành công");
		Assert.assertEquals(redirectURL, true);
		
//
//		ArrayList<String> paymenttab = new ArrayList<String>(driver.getWindowHandles());
//		driver.switchTo().window(paymenttab.get(0));
//		Thread.sleep(10000);
		
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
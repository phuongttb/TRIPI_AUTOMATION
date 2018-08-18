package selenium_api;

import java.util.ArrayList;
import java.util.Date;
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

public class FlightTestScript {
	WebDriver driver;

	// TÌM KIẾM VÀ BOOK VÉ MÁY BAY
	@Test
	@Parameters({ "flightfromairport", "flighttoairport", "flightcheckindate", "flightcheckoutdate",
			"selectagencyoutbound", "selectagencyintbound", "passengerLastName1", "passengerFirstName1",
			"passengerLastName2", "passengerFirstName2", "gender", "gender1", "Passenpackage1", "contactInfolastName",
			"contactInfofirstName", "contactInfoemail", "contactInfophone1", "gendercontact" })
	public void TC_01_SearchAndtBookTicket_RoundTrip(String flightfromairport, String flighttoairport,
			String flightcheckindate, String flightcheckoutdate, String selectagencyoutbound,
			String selectagencyintbound, String passengerLastName1, String passengerFirstName1,
			String passengerLastName2, String passengerFirstName2, String gender, String gender1, String Passenpackage1,
			String contactInfolastName, String contactInfofirstName, String contactInfoemail, String contactInfophone1,
			String gendercontact) throws Exception {

		// Click on "vé máy bay" tab
		driver.findElement(By.xpath("//div[contains(text(),'Vé máy bay')]")).click();

		// Enter "From" station code eg.HAN
		WebElement eFromAirport = driver.findElement(By.id("flight-from-airport-value"));
		eFromAirport.sendKeys(flightfromairport);
		Thread.sleep(2000);
		eFromAirport.sendKeys(Keys.RETURN);

		// Enter "To" station code eg.SGN
		WebElement eToAirport = driver.findElement(By.id("flight-to-airport-value"));
		eToAirport.sendKeys(flighttoairport);
		Thread.sleep(2000);
		eToAirport.sendKeys(Keys.RETURN);

		// Click on RoundTrip tab
		driver.findElement(By.xpath("//span[contains(text(),'Khứ hồi')]")).click();

		// click on Departure date
		WebElement depaturedate = driver.findElement(By.xpath("//input[@id='flight-checkin-date']"));
		depaturedate.click();

		selectDate(flightcheckindate);

		WebElement returnDate = driver.findElement(By.xpath("//input[@id='flight-checkout-date']"));
		returnDate.click();

		WebElement dateWidget = driver
				.findElement(By.cssSelector("div.hthsf-item:nth-child(5) > div:nth-child(2) > div:nth-child(1)"));
		List<WebElement> columns = dateWidget.findElements(By.tagName("td"));
		for (WebElement cell : columns) {
			if (cell.getText().equals(flightcheckoutdate)) {
				cell.click();
				break;
			}
		}

		// select number of adults
		WebElement clickpassenger = driver.findElement(By.cssSelector(".ui-selectmenu-text"));
		clickpassenger.click();
		driver.findElement(By.xpath("//button[@class='btn btn-white btn-ts-up']")).click();

		// all fields filled in. Now click on search
		WebElement searchbutton = driver
				.findElement(By.xpath("//button[@class='flight-search-button btn btn-search']"));
		searchbutton.click();
		Thread.sleep(20000);

		// Chon ve chieu di
		WebElement outBoundTicketsDiv = driver.findElement(By.cssSelector("#outBoundTickets"));
		List<WebElement> outboundTickets = outBoundTicketsDiv.findElements(By.cssSelector(".ticket-info"));
		System.out.println("Tổng số vé chiều đi:" + outboundTickets.size());
		boolean isSelected = false;
		for (WebElement ticket : outboundTickets) {
			WebElement logo = ticket.findElement(By.cssSelector(".alogo"));
			String agency = logo.getAttribute("alt");

			if (agency.contains(selectagencyintbound)) {
				WebElement selectBtnob = ticket.findElement(By.cssSelector(".flight-select-single-ticket"));
				selectBtnob.click();
				isSelected = true;
				Thread.sleep(1000);
				System.out.println(agency);
				break;
			}
		}
		if (!isSelected) {
			Assert.fail("Dont have any Jetstar ticket");
		}
		// Chon ve chieu ves
		WebElement inBoundTicketstab = driver.findElement(By.cssSelector("div.menu-item:nth-child(3)"));
		inBoundTicketstab.click();
		Thread.sleep(5000);
		WebElement inBoundTicketsDiv = driver.findElement(By.cssSelector("#inboundTickets.tickets"));
		List<WebElement> inboundTickets = inBoundTicketsDiv.findElements(By.cssSelector(".ticket-info"));

		System.out.println("Tổng số vé chiều về :" + inboundTickets.size());
		for (WebElement ticketib : inboundTickets) {

			WebElement logoib = ticketib.findElement(By.cssSelector(".alogo"));
			String agencyib = logoib.getAttribute("alt");

			if (agencyib.contains(selectagencyintbound)) {
				WebElement selectBtnib = ticketib.findElement(By.cssSelector(".flight-select-return-ticket"));
				selectBtnib.click();
				Thread.sleep(1000);
				System.out.println(agencyib);
				break;

			}

		}
		// click vào nút confirm button
		WebElement comfirmbtn = driver.findElement(By.cssSelector(".flight-search-booking-ticket"));
		comfirmbtn.click();
		Thread.sleep(10000);
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(0));

		Thread.sleep(1000);
		// Khach hang 1

		driver.findElement(By.cssSelector("input[ng-model='adult.lastName']")).sendKeys(passengerLastName1);
		Thread.sleep(1000);
		driver.findElement(By.xpath("//input[contains(@class,'first-name')]")).sendKeys(passengerFirstName1);

		// CHỌN GIỚI TÍNH KHÁCH HÀNG THỨ NHẤT
		new Select(driver.findElement(By.xpath("(//select[@ng-model='adult.gender'])[1]"))).selectByVisibleText(gender);

		// NHẬP TÊN
		WebElement secondGuestDiv = driver.findElement(By.cssSelector("#adult-1"));
		// Nhap lastname
		WebElement lastNametext2 = secondGuestDiv.findElement(By.cssSelector(".form-control.last-name"));
		lastNametext2.clear();
		lastNametext2.sendKeys(passengerLastName2);

		// NHẬP HỌ TÊN
		WebElement firstNametext2 = secondGuestDiv.findElement(By.cssSelector(".form-control.first-name"));
		firstNametext2.clear();
		firstNametext2.sendKeys(passengerLastName2);

		// NHẬP GIỚI TÍNH KHÁCH HÀNG 2
		new Select(driver.findElement(By.xpath("(//select[@ng-model='adult.gender'])[2]")))
				.selectByVisibleText(gender1);

		// MUA HÀNH LÝ CHIỀU ĐI
		WebElement selectBox = driver.findElement(By.xpath("//select[@ng-model='adult.outboundBaggageId']"));
		Select X = new Select(selectBox);
		List<WebElement> options = selectBox.findElements(By.tagName("option"));
		int index = 0;
		for (WebElement option : options) {
			System.out.println(option.getText());
			String value = option.getText();
			if (value.contains(Passenpackage1)) {
				X.selectByIndex(index);

			}
			index++;
		}

		// MUA HÀNH LÝ CHIỀU VỀ

		WebElement baggagedrop = driver.findElement(By.xpath("//select[@ng-model='adult.inboundBaggageId']"));
		Select baggagevalue = new Select(baggagedrop);
		List<WebElement> valuedrop = baggagedrop.findElements(By.tagName("option"));
		int index3 = 0;
		for (WebElement listvalue : valuedrop) {
			System.out.println(listvalue.getText());
			String value = listvalue.getText();
			if (value.contains("20 kg")) {
				baggagevalue.selectByIndex(index3);
			}
			index3++;
		}

		// NHẬP THÔNG TIN LIÊN HỆ
		new Select(driver.findElement(By.id("ticket-booking-select-title"))).selectByVisibleText("Ông");
		driver.findElement(By.xpath("//input[@ng-model='contactInfo.lastName']")).sendKeys(contactInfolastName);
		driver.findElement(By.xpath("//input[@ng-model='contactInfo.firstName']")).sendKeys(contactInfofirstName);
		driver.findElement(By.xpath("//input[@ng-model='contactInfo.email']")).sendKeys(contactInfoemail);
		driver.findElement(By.xpath("//input[@ng-model='contactInfo.phone1']")).sendKeys(contactInfophone1);

		// //Select payment method - trip credit
				WebElement paymentmethod = driver.findElement(By.cssSelector("#payment-method-3"));
				paymentmethod.click();
				Thread.sleep(1000);

				WebElement paymentbtn = driver.findElement(By.cssSelector(".flight-payment-button"));
				paymentbtn.click();
				Thread.sleep(2000);

				WebElement confirmbtn = driver.findElement(By.cssSelector(".btn-success"));
				confirmbtn.click();

	}

	public void selectDate(String date) {
		WebElement dateWidget = driver.findElement(By.className("startHoliday"));
		List<WebElement> columns = dateWidget.findElements(By.tagName("td"));
		for (WebElement cell : columns) {
			if (cell.getText().equals(date)) {
				cell.click();
				break;
			}
		}
	}

	// SEARCH FLIGHT : SEARCH RESULTS NOT SATISFY SEARCH CONDITION

	@Test(enabled = false)
	@Parameters({ "flightfromairport", "flighttoairport", "flightcheckindate", "flightcheckoutdate",
			"selectagencyoutbound", "selectagencyintbound" })
	public void TC_03_SearchFligh_notsatisfysearchcondition(String flightfromairport, String flighttoairport,
			String flightcheckindate, String flightcheckoutdate) throws Exception {

		// Click on "vé máy bay" tab
		driver.navigate().to("https://www.tripi.vn/");

		// Enter "From" station code eg.HAN
		WebElement eFromAirport = driver.findElement(By.id("flight-from-airport-value"));
		eFromAirport.sendKeys(flightfromairport);
		Thread.sleep(2000);
		eFromAirport.sendKeys(Keys.RETURN);

		// Enter "To" station code eg.SGN
		WebElement eToAirport = driver.findElement(By.id("flight-to-airport-value"));
		eToAirport.sendKeys(flighttoairport);
		Thread.sleep(2000);
		eToAirport.sendKeys(Keys.RETURN);

		// Click on RoundTrip tab
		driver.findElement(By.xpath("//span[contains(text(),'Khứ hồi')]")).click();
		System.out.print(new Date());

		// click on Departure date
		WebElement depaturedate = driver.findElement(By.xpath("//input[@id='flight-checkin-date']"));
		depaturedate.click();

		selectDate(flightcheckindate);
		WebElement returnDate = driver.findElement(By.xpath("//input[@id='flight-checkout-date']"));
		returnDate.click();

		WebElement dateWidget = driver
				.findElement(By.cssSelector("div.hthsf-item:nth-child(5) > div:nth-child(2) > div:nth-child(1)"));
		List<WebElement> columns = dateWidget.findElements(By.tagName("td"));
		System.out.println("No elements found:  " + columns.size());
		for (WebElement cell : columns) {
			if (cell.getText().equals(flightcheckoutdate)) {
				cell.click();
				break;
			}
		}

		Thread.sleep(10000);
		// all fields filled in. Now click on search
		WebElement searchbutton = driver
				.findElement(By.xpath("//button[@class='flight-search-button btn btn-search']"));
		searchbutton.click();

		Thread.sleep(10000);

		String errormssage = driver.findElement(By.xpath("//span[contains(text(),'Không có chuyến bay')]")).getText();
		Assert.assertEquals("Không có chuyến bay", errormssage);
		System.out.println(errormssage);

		// Navigate to previous page
		driver.navigate().back();
	}

	public void selectDate3(String date) {
		WebElement dateWidget = driver.findElement(By.className("startHoliday"));
		List<WebElement> columns = dateWidget.findElements(By.tagName("td"));
		System.out.println("No elements found:  " + columns.size());
		for (WebElement cell : columns) {
			if (cell.getText().equals(date)) {
				cell.click();
				break;
			}
		}
	}

	// SEARCH TRIPI HOLIDAY_TESTSCRIPT

	@Test(enabled = false)
	@Parameters({ "flightfromairport", "flighttoairport", "flightcheckindate", "flightcheckoutdate",
			"selectagencyoutbound", "selectagencyintbound" })
	public void TC_02_SearchFlightholiday(String flightfromairport, String flighttoairport, String flightcheckindate,
			String flightcheckoutdate) throws Exception {

		driver.get("https://www.tripi.vn/");

		// Chọn chiều đi
		WebElement eFromAirport = driver.findElement(By.id("tripi-holiday-from-airport-value"));
		eFromAirport.sendKeys(flightfromairport);
		Thread.sleep(2000);
		eFromAirport.sendKeys(Keys.RETURN);
		System.out.println("Test: " + eFromAirport.getText());
		// Chọn chiều về
		WebElement eToAirport = driver.findElement(By.id("tripi-holiday-to-airport-value"));
		eToAirport.sendKeys(flighttoairport);
		Thread.sleep(2000);
		eToAirport.sendKeys(Keys.RETURN);
		System.out.println("Test: " + eToAirport.getText());

		// Chọn ngay di
		WebElement eToAirport1 = driver.findElement(By.xpath("//input[@id='flight-checkin-date']"));
		eToAirport1.click();

		selectDate("20");
		selectDate("23");

		WebElement searchbutton = driver.findElement(By.xpath("//div[contains(text(),'Tìm kiếm')]"));
		searchbutton.click();

		Thread.sleep(15000);
		List<WebElement> hotels = driver.findElements(By.className("hsr-item"));
		System.out.println(hotels.size());
		Assert.assertEquals(true, hotels.size() > 0);
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
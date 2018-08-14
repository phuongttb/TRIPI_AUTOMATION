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
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class BookerPartner {
	WebDriver driver;

	@Test(enabled = true)
	public void TC_01_SearchAndtBookTicket_RoundTrip() throws Exception {

		// Click on "vé máy bay" tab
		driver.findElement(By.xpath("//div[contains(text(),'Vé máy bay')]")).click();

		// Enter "From" station code eg.HAN
		WebElement eFromAirport = driver.findElement(By.id("flight-from-airport-value"));
		eFromAirport.sendKeys("HAN");
		Thread.sleep(2000);
		eFromAirport.sendKeys(Keys.RETURN);

		// Enter "To" station code eg.SGN
		WebElement eToAirport = driver.findElement(By.id("flight-to-airport-value"));
		eToAirport.sendKeys("SGN");
		Thread.sleep(2000);
		eToAirport.sendKeys(Keys.RETURN);

		// Click on RoundTrip tab
		driver.findElement(By.xpath("//span[contains(text(),'Khứ hồi')]")).click();

		// click on Departure date
		WebElement depaturedate = driver.findElement(By.xpath("//input[@id='flight-checkin-date']"));
		depaturedate.click();

		selectDate("17");

		WebElement returnDate = driver.findElement(By.xpath("//input[@id='flight-checkout-date']"));
		returnDate.click();

		WebElement dateWidget = driver
				.findElement(By.cssSelector("div.hthsf-item:nth-child(5) > div:nth-child(2) > div:nth-child(1)"));
		List<WebElement> columns = dateWidget.findElements(By.tagName("td"));
		for (WebElement cell : columns) {
			if (cell.getText().equals("20")) {
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

			if (agency.contains("Jetstar")) {
				WebElement selectBtnob = ticket.findElement(By.cssSelector(".flight-select-single-ticket"));
				selectBtnob.click();
				isSelected=true;
				Thread.sleep(1000);
				System.out.println(agency);
				break;
			}
		}
		if(!isSelected) {
			Assert.fail("Dont have any Jetstar ticket");
		}
		// Chon ve chieu ve
		WebElement inBoundTicketstab = driver.findElement(By.cssSelector("div.menu-item:nth-child(3)"));
		inBoundTicketstab.click();
		Thread.sleep(5000);
		WebElement inBoundTicketsDiv = driver.findElement(By.cssSelector("#inboundTickets.tickets"));
		List<WebElement> inboundTickets = inBoundTicketsDiv.findElements(By.cssSelector(".ticket-info"));

		System.out.println("Tổng số vé chiều về :" + inboundTickets.size());
		for (WebElement ticketib : inboundTickets) {

			WebElement logoib = ticketib.findElement(By.cssSelector(".alogo"));
			String agencyib = logoib.getAttribute("alt");

			if (agencyib.contains("VietJet")) {
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
     		
		Thread.sleep(3000);
		//Khach hang 1
//		driver.findElement(By.xpath("//input[contains(@class,'last-name')]")).sendKeys("Phuong");
		driver.findElement(By.cssSelector("input[ng-model='adult.lastName']")).sendKeys("Phuong");
		
		Thread.sleep(10000);
		driver.findElement(By.xpath("//input[contains(@class,'first-name')]")).sendKeys("Tran");
		WebElement selectsex = driver.findElement(By.cssSelector(".gender"));
		List <WebElement> options=selectsex.findElements(By.cssSelector(".gender > option:nth-child(2)"));
		for (WebElement option : options)
			if ("Nam".equals(option.getText()))
				option.click();
		
		//Khach hang thu 2
		driver.findElement(By.xpath("//input[contains(@class,'first-name')]")).sendKeys("Tran");
		WebElement selectsex1 = driver.findElement(By.cssSelector(".gender"));
		List <WebElement> options1=selectsex1.findElements(By.cssSelector(".#ticket-booking-select-title > option:nth-child(2)"));
		for (WebElement option : options1)
			if ("Nữ".equals(option.getText()))
				option.click();
		Thread.sleep(4000);
		driver.findElement(By.xpath("//input[@ng-model='adult.firstName']")).sendKeys("phuong");
		driver.findElement(By.xpath("//input[@placeholder='Số điện thoại']")).sendKeys("0942127129");
		driver.findElement(By.xpath("//input[@placehoder='Địa chỉ email']")).sendKeys("bichphuong@gmail.com");
		driver.findElement(By.xpath("//tetarea[@placeholder='Địa chỉ liên hệ']")).sendKeys("phuongttb");

		// //Select payment method - trip credit
		driver.findElement(By.xpath(" //input[@name='Trip Credits' and value='Trip Credits']")).click();
		assert driver.findElement(By.xpath("//input[@name='Trip Credits' and value='Trip Credits']")).isSelected();

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
	public void TC_03_SearchFligh_notsatisfysearchcondition() throws Exception {

		// Click on "vé máy bay" tab
		driver.navigate().to("https://www.tripi.vn/");
		// driver.findElement(By.xpath("//div[contains(text(),'Vé máy bay')]")).click();

		// Enter "From" station code eg.HAN
		WebElement eFromAirport = driver.findElement(By.id("flight-from-airport-value"));
		eFromAirport.sendKeys("CGK");
		Thread.sleep(2000);
		eFromAirport.sendKeys(Keys.RETURN);
		System.out.println("Test: " + eFromAirport.getText());

		// Enter "To" station code eg.SGN
		WebElement eToAirport = driver.findElement(By.id("flight-to-airport-value"));
		eToAirport.sendKeys("KUL");
		Thread.sleep(2000);
		eToAirport.sendKeys(Keys.RETURN);
		System.out.println("Test: " + eToAirport.getText());

		// Click on RoundTrip tab
		driver.findElement(By.xpath("//span[contains(text(),'Khứ hồi')]")).click();
		System.out.print(new Date());

		// click on Departure date
		WebElement depaturedate = driver.findElement(By.xpath("//input[@id='flight-checkin-date']"));
		depaturedate.click();

		selectDate("14");
		// selectDate("15");
		WebElement returnDate = driver.findElement(By.xpath("//input[@id='flight-checkout-date']"));
		returnDate.click();

		WebElement dateWidget = driver
				.findElement(By.cssSelector("div.hthsf-item:nth-child(5) > div:nth-child(2) > div:nth-child(1)"));
		List<WebElement> columns = dateWidget.findElements(By.tagName("td"));
		System.out.println("No elements found:  " + columns.size());
		for (WebElement cell : columns) {
			if (cell.getText().equals("15")) {
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
	public void TC_02_SearchFlightholiday() throws Exception {
		System.out.println("Check homepage title");
		String homePageTitle = driver.getTitle();
		Assert.assertEquals(homePageTitle, "Tripi.vn - Đặt vé máy bay và khách sạn thuận tiện với giá tốt nhất");

		System.out.println("Check homepage url");
		String homePageUrl = driver.getCurrentUrl();
		Assert.assertEquals(homePageUrl, "https://dev.tripi.vn/");

		// Chọn chiều đi
		WebElement eFromAirport = driver.findElement(By.id("tripi-holiday-from-airport-value"));
		eFromAirport.sendKeys("Hồ chí Minh");
		Thread.sleep(2000);
		eFromAirport.sendKeys(Keys.RETURN);
		System.out.println("Test: " + eFromAirport.getText());
		// Chọn chiều về
		WebElement eToAirport = driver.findElement(By.id("tripi-holiday-to-airport-value"));
		eToAirport.sendKeys("Đà Nẵng");
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
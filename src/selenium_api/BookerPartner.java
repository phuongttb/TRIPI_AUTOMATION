package selenium_api;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class BookerPartner {
	WebDriver driver;
	WebDriverWait wait;


	@Test()
	public void TC_01_SearchFlight_RoundTrip() throws Exception {

		String homePageUrl = driver.getCurrentUrl();
		Assert.assertEquals(homePageUrl, "https://dev.tripi.vn/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		
		// Click on "vé máy bay" tab
		driver.findElement(By.xpath("//div[contains(text(),'Vé máy bay')]")).click();

		// Enter "From" station code eg.HAN
		WebElement eFromAirport = driver.findElement(By.id("flight-from-airport-value"));
		eFromAirport.sendKeys("HAN");
		Thread.sleep(2000);
		eFromAirport.sendKeys(Keys.RETURN);
		System.out.println("Test: " + eFromAirport.getText());
		
		// Enter "To" station code eg.SGN
		WebElement eToAirport = driver.findElement(By.id("flight-to-airport-value"));
		eToAirport.sendKeys("SGN");
		Thread.sleep(2000);
		eToAirport.sendKeys(Keys.RETURN);
		System.out.println("Test: " + eToAirport.getText());

		// Click on RoundTrip tab
		driver.findElement(By.xpath("//span[contains(text(),'Khứ hồi')]")).click();

		// click on Departure date
		WebElement depaturedate = driver.findElement(By.xpath("//input[@id='flight-checkin-date']"));
		depaturedate.click();
		
		
		selectDate("14");
		//selectDate("15");
		WebElement returnDate = driver.findElement(By.xpath("//input[@id='flight-checkout-date']"));
		returnDate.click();
		
		WebElement dateWidget= driver.findElement(By.cssSelector("div.hthsf-item:nth-child(5) > div:nth-child(2) > div:nth-child(1)"));
		List<WebElement> columns = dateWidget.findElements(By.tagName("td"));
		System.out.println("LEN:  " + columns.size());
		for (WebElement cell : columns) {
			if (cell.getText().equals("15")) {
				cell.click();
				break;
			}
		}
	


		// all fields filled in. Now click on search
		WebElement searchbutton = driver.findElement(By.xpath("//button[@class='flight-search-button btn btn-search']"));
		searchbutton.click();

		
		
		// Check the system return search results
		WebElement outBoundTickets = driver.findElement(By.id("outBoundTickets"));
		List<WebElement> flight = outBoundTickets
				.findElements(By.xpath("//div[@class='panel panel-default ticket first-ticket']"));

		// verify that result appears for the provided journey search
		System.out.println(flight.size());
		Assert.assertEquals(true, flight.size() > 0);

		// Navigate to previous page
		driver.navigate().back();
		
		//sau khoảng 10k miliseconds sẽ chạy tiếp testcase thứ 2
		wait =new WebDriverWait(driver,30);
	}

	public void selectDate(String date) {
		WebElement dateWidget= driver.findElement(By.className("startHoliday"));
		List<WebElement> columns = dateWidget.findElements(By.tagName("td"));
		System.out.println("LEN:  " + columns.size());
		for (WebElement cell : columns) {
			if (cell.getText().equals(date)) {
				cell.click();
				break;
			}
		}
	}

	// SEARCH HOTEL_TESTSCRIPT

	@Test(enabled = true)
	public void TC_02_SearchHotel() throws Exception {

		// click on Hotel tab
		driver.findElement(By.xpath("//div[contains(text(),'Khách sạn')]")).click();

		// select destination hotel
		WebElement destination = driver.findElement(By.id("hotel-autocomplete-input-value"));
		destination.sendKeys("Đà Nẵng");
		Thread.sleep(2000);
		destination.sendKeys(Keys.RETURN);
		System.out.println("Test: " + destination.getText());
		
		
		//click on check-in date
		driver.findElement(By.xpath("//input[@id='hotel-check-in-value']")).click();

		selectDate("10");
	
		WebElement checkoutdate = driver.findElement(By.xpath("//input[@id='hotel-check-out-value']"));
		checkoutdate.click();
		
		WebElement dateWidget= driver.findElement(By.cssSelector("div.date-picker-wrapper:nth-child(3)"));
		List<WebElement> columns = dateWidget.findElements(By.tagName("td"));
		System.out.println("LEN:  " + columns.size());
		for (WebElement cell : columns) {
			if (cell.getText().equals("15")) {
				cell.click();
				break;
			}
		}
		

		// check search results's list
		WebElement hotelname = driver.findElement(By.id("hotel-1549"));
		List<WebElement> flight = hotelname
				.findElements(By.xpath("//div[@class='panel panel-default ticket first-ticket']"));
		System.out.println(flight.size());
		Assert.assertEquals(true, flight.size() > 0);
	}

	// select check-in and check-out date
	public void selectDatehotel(String date) {

		WebElement startHoliday = driver.findElement(By.className("ng-scope"));
		List<WebElement> columns = startHoliday.findElements(By.tagName("td"));
		System.out.println("LEN:  " + columns.size());
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
		driver.get("https://dev.tripi.vn/");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();

	}

	@AfterClass
	public void afterClass() {
		driver.quit();

	}

}
package selenium_api;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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

	@Test(enabled = true)
	public void TC_01_SearchFlight_RoundTrip() throws Exception {

//		String homePageUrl = driver.getCurrentUrl();
//		Assert.assertEquals(homePageUrl, "https://www.tripi.vn/");
//		driver.manage().window().maximize();
//		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

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
		System.out.println("LEN:  " + columns.size());
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
		
		
		String errormssage = driver.findElement(By.xpath("//span[contains(text(),'Chưa chọn vé chiều đi')]")).getText();
		 Assert.assertEquals("Chưa chọn vé chiều đi", errormssage);
		 System.out.println(errormssage);
		 
		//check title page and print page title
//		System.out.println("Check homepage title");
//		String homePageTitle = driver.getTitle();
//		System.out.println(homePageTitle);
//    	System.out.println(homePageTitle.length());
//		Assert.assertEquals(homePageTitle, "Vé máy bay Hà Nội - Tân Sơn Nhất (SGN) - Tripi.vn");
		Thread.sleep(10000);
		
		//click on "chọn vé này" button
//		driver.findElement(By.xpath("//span[contains(text(),'Chọn vé này')]")).click();
//		driver.findElement(By.xpath("//span[contains(text(),'Chọn vé này')]")).click();
		
	    //click on "Xác nhận chọn vé' button
//		driver.findElement(By.xpath("//span[contains(text(),'Xác nhận chọn vé')]")).click();
		
		// Navigate to previous page
		driver.navigate().back();

		// sau khoảng 10k miliseconds sẽ chạy tiếp testcase thứ 2
//		wait = new WebDriverWait(driver, 10);
	}

	public void selectDate(String date) {
		WebElement dateWidget = driver.findElement(By.className("startHoliday"));
		List<WebElement> columns = dateWidget.findElements(By.tagName("td"));
		System.out.println("LEN:  " + columns.size());
		for (WebElement cell : columns) {
			if (cell.getText().equals(date)) {
				cell.click();
				break;
			}
		}
	}
	
	

	// SEARCH HOTEL_TESTSCRIPT (Return search results)

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

		// click on check-in date
		driver.findElement(By.xpath("//input[@id='hotel-check-in-value']")).click();

		selectDate("10");

		
		// select check-out date
		driver.findElement(By.xpath("//input[@id='hotel-check-out-value']")).click();

		WebElement dateWidget = driver.findElement(By.cssSelector("div.date-picker-wrapper:nth-child(3)"));
		List<WebElement> columns = dateWidget.findElements(By.tagName("td"));
		System.out.println("LEN:  " + columns.size());
		for (WebElement cell : columns) {
			if (cell.getText().equals("15")) {
				cell.click();
				break;
			}
			
		}
		// click on search button
		driver.findElement(By.xpath("//button[@class='hotel-search-button btn btn-search']")).click();
		Thread.sleep(10000);
		
		driver.findElement(By.xpath("//button[contains(text(),'Thay đổi')]")).click();
		 driver.findElement(By.xpath("//input[@id='hotel-autocomplete-input-value']")).sendKeys("Bình Thuận");
		 driver.findElement(By.xpath("//span[contains(text(),'Tìm kiếm')]")).click();
		 
		 Thread.sleep(10000);
//		 String searchresults = driver.findElement(By.xpath("//div[contains(text(),'DOUBLE DELUXE')]")).getText();
//		 Assert.assertEquals("DOUBLE DELUXE", searchresults);
//		 System.out.println(searchresults);

		
		//kiểm tra xem ở page title ở màn hình kết quả hotel trả ra có đúng không
//		System.out.println("Check homepage title");
//		String homePageTitle = driver.getTitle();
//		System.out.println(homePageTitle);
//		System.out.println(homePageTitle.length());
//		Assert.assertEquals(homePageTitle, "Khách sạn ở Đà Nẵng - Trang 1");
//		
//		
		//click on book button on search results 
		driver.findElement(By.xpath("//span[contains(text(),'Đặt phòng')]")).click();
		
		//open new tab
		//Lưu trữ mã id của tất cả các tab đang mở Do lúc này browser có 2 tabs nên nó sẽ có 2 tab id được lưu vào array list
		//2 tabs id đó sẽ lần lượt có index là 0 và 1 trong array list muôn trỏ tabs thứ 2 để thao tác Vậy sẽ switch sang index 1 tương ứng tab thứ 2
		
		ArrayList<String> tabs1 = new ArrayList<String> (driver.getWindowHandles());
		driver.switchTo().window(tabs1.get(1));
//		
		 String findroom = driver.findElement(By.xpath("//div[contains(text(),'DOUBLE DELUXE')]")).getText();
		 Assert.assertEquals("DOUBLE DELUXE", findroom);
		 System.out.println(findroom);

	
		// Navigate to previous page
		driver.navigate().back();

		
		//Click on book now button on hotel details
//		driver.findElement(By.xpath("//div[@ng-if='filterByProvider']//div[@class='room-title ng-binding'][text()='DOUBLE DELUXE']/../../../div[2]//span[text()='Đặt ngay']")).click();
		 //driver.findElement(By.xpath("//div[@class='room-by-agency row item-provider ng-scope']")).click();
//		 ((JavascriptExecutor) driver).executeScript("document.getElementsByClassName('hotel-add-payment-info')[0].click()");
		//open new tab
//		ArrayList<String> tabs2 = new ArrayList<String> (driver.getWindowHandles());
//		driver.switchTo().window(tabs2.get(1));
//	  	Thread.sleep(10000);	
	
		//Fill customer's information
////		driver.findElement(By.xpath("//*[@data-ng-model='bookingInfo.customerInfo.name']")).sendKeys("Tran");
//		driver.findElement(By.xpath("//input[@name='name']")).sendKeys("Tran");
//		driver.findElement(By.xpath("//input[@placeholder='Số điện thoại']")).sendKeys("0942127129");
//		driver.findElement(By.xpath("//input[@placehoder='Địa chỉ email']")).sendKeys("bichphuong@gmail.com");
//		driver.findElement(By.xpath("//tetarea[@placeholder='Địa chỉ liên hệ']")).sendKeys("phuongttb");
//	    
//	    //Select payment method - trip credit
//		driver.findElement(By.xpath(" //input[@name='Trip Credits' and value='Trip Credits']")).click();
//		assert driver.findElement(By.xpath("//input[@name='Trip Credits' and value='Trip Credits']")).isSelected();
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

	@Test(enabled = true)
	public void TC_03_SearchFligh_notsatisfysearchcondition() throws Exception {

		// Click on "vé máy bay" tab
		driver.navigate().to("https://www.tripi.vn/");
	//	driver.findElement(By.xpath("//div[contains(text(),'Vé máy bay')]")).click();

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
		System.out.println("LEN:  " + columns.size());
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
		
		Thread.sleep(50000);
		
		String errormssage = driver.findElement(By.xpath("//span[contains(text(),'Không có chuyến bay')]")).getText();
		 Assert.assertEquals("Không có chuyến bay", errormssage);
		 System.out.println(errormssage);

		// Navigate to previous page
		driver.navigate().back();


	}


	public void selectDate3(String date) {
		WebElement dateWidget = driver.findElement(By.className("startHoliday"));
		List<WebElement> columns = dateWidget.findElements(By.tagName("td"));
		System.out.println("LEN:  " + columns.size());
		for (WebElement cell : columns) {
			if (cell.getText().equals(date)) {
				cell.click();
				break;
			}
		}
	}
	
	@Test(enabled = false)
	public void TC_04_HotelSearchResultsnotsatisfysearchcondition() throws Exception {
       
		driver.navigate().to("https://www.tripi.vn/");
		// click on Hotel tab
		driver.findElement(By.xpath("//div[contains(text(),'Khách sạn')]")).click();

		// select destination hotel
		WebElement destination = driver.findElement(By.id("hotel-autocomplete-input-value"));
		destination.sendKeys("Đà Nẵng");
		Thread.sleep(2000);
		destination.sendKeys(Keys.RETURN);
		System.out.println("Test: " + destination.getText());

		// click on check-in date
		driver.findElement(By.xpath("//input[@id='hotel-check-in-value']")).click();

		selectDate("10");

		
		// select check-out date
		driver.findElement(By.xpath("//input[@id='hotel-check-out-value']")).click();

		WebElement dateWidget = driver.findElement(By.cssSelector("div.date-picker-wrapper:nth-child(3)"));
		List<WebElement> columns = dateWidget.findElements(By.tagName("td"));
		System.out.println("LEN:  " + columns.size());
		for (WebElement cell : columns) {
			if (cell.getText().equals("15")) {
				cell.click();
				break;
			}
			
		}
		// click on search button
		driver.findElement(By.xpath("//button[@class='hotel-search-button btn btn-search']")).click();
		Thread.sleep(10000);
		
		// Navigate to previous page
		driver.navigate().back();

		}

	// select check-in and check-out date
	public void selectDatehotel3(String date) {

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
		driver.get("https://www.tripi.vn/");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();

	}

	@AfterClass
	public void afterClass() {
		driver.quit();

	}

}
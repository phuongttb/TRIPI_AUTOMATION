package selenium_api;

import java.util.ArrayList;
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

public class TestscriptHotel {
	WebDriver driver;

	// SEARCH HOTEL_TESTSCRIPT (Return search results)

	@Test(enabled = false)
	public void TC_02_SearchHotel() throws Exception {

		// click on Hotel tab
		driver.findElement(By.xpath("//div[contains(text(),'Khách sạn')]")).click();

		// select destination hotel
		WebElement destination = driver.findElement(By.id("hotel-autocomplete-input-value"));
		destination.sendKeys("Đà Nẵng");
		Thread.sleep(2000);
		destination.sendKeys(Keys.RETURN);

		// click on check-in date
		driver.findElement(By.xpath("//input[@id='hotel-check-in-value']")).click();

//		selectDatehotel("15");

		WebElement dateWidget2 = driver.findElement(By.cssSelector("div.date-picker-wrapper:nth-child(3)"));
		List<WebElement> columns = dateWidget2.findElements(By.tagName("td"));
		for (WebElement cell : columns) {
			if (cell.getText().equals("20")) {
				cell.click();
				break;
			}
		
		// select check-out date
		driver.findElement(By.xpath("//input[@id='hotel-check-out-value']")).click();

		WebElement dateWidget = driver.findElement(By.cssSelector("div.date-picker-wrapper:nth-child(3)"));
		List<WebElement> columns2 = dateWidget.findElements(By.tagName("td"));
		for (WebElement cell2 : columns2) {
			if (cell2.getText().equals("20")) {
				cell2.click();
				break;
			}

		}

		WebElement clickonperson = driver.findElement(By.cssSelector(".ui-selectmenu-text"));
		clickonperson.click();
		driver.findElement(By.xpath("//button[@class='btn btn-white btn-ts-up']")).click();

		// click on search button
		driver.findElement(By.xpath("//button[@class='hotel-search-button btn btn-search']")).click();
		Thread.sleep(10000);

		WebElement listhoteldiv = driver.findElement(By.cssSelector(".result-items"));
		List<WebElement> listhotel = listhoteldiv.findElements(By.cssSelector(".item-wrapper"));
		System.out.println("number of hotel record :" + listhotel.size());
		for (WebElement item : listhotel) {
			WebElement itemname = item.findElement(By.xpath("//a[@class='item-name in-hotel-search']"));
			String titleHotel = itemname.getAttribute("title");
			System.out.println(titleHotel);

			// click on book button on search results
			driver.findElement(By.xpath("//span[contains(text(),'Đặt phòng')]")).click();
			ArrayList<String> tabs1 = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(tabs1.get(1));

			// WebElement
			// lisroomparent=driver.findElement(By.cssSelector(".content-detail-hotel"));
			// List<WebElement>
			// listroom=lisroomparent.findElements(By.cssSelector(".agency-item-provider"))
			// ;
			// for (WebElement itemroom : listroom)
			// {
			//
			// System.out.println("number of room item" + listroom.size() );
			// WebElement room=itemroom.findElement(By.cssSelector(".item-name"));
			// String titleroom = room.getAttribute("title");
			// System.out.println(titleroom);
			// }

			// String findroom = driver.findElement(By.xpath("//div[contains(text(),'DOUBLE
			// DELUXE')]")).getText();
			// Assert.assertEquals("DOUBLE DELUXE", findroom);
			// System.out.println(findroom);
//			Thread.sleep(20000);

			// Fill customer's information
			//// driver.findElement(By.xpath("//*[@data-ng-model='bookingInfo.customerInfo.name']")).sendKeys("Tran");
			// driver.findElement(By.xpath("//input[@name='name']")).sendKeys("Tran");
			// driver.findElement(By.xpath("//input[@placeholder='Số điện
			//// thoại']")).sendKeys("0942127129");
			// driver.findElement(By.xpath("//input[@placehoder='Địa chỉ
			//// email']")).sendKeys("bichphuong@gmail.com");
			// driver.findElement(By.xpath("//tetarea[@placeholder='Địa chỉ liên
			//// hệ']")).sendKeys("phuongttb");
			//
			// //Select payment method - trip credit
			// driver.findElement(By.xpath(" //input[@name='Trip Credits' and value='Trip
			//// Credits']")).click();
			// assert driver.findElement(By.xpath("//input[@name='Trip Credits' and
			//// value='Trip Credits']")).isSelected();
		}

	}

	// select check-in and check-out date
//	public void selectDatehotel(String date) {
//
//		WebElement startHoliday = driver.findElement(By.className("ng-scope"));
//		List<WebElement> columns = startHoliday.findElements(By.tagName("td"));
//		for (WebElement cell : columns) {
//			if (cell.getText().equals(date)) {
//				cell.click();
//				break;
//			}
//
//		}

	}

	// SEARCH HOTEL_TESTSCRIPT Filter according to star

	@Test(enabled = true)
	public void TC_02_NoSearchResults() throws Exception {

		// click on Hotel tab
		driver.findElement(By.xpath("//div[contains(text(),'Khách sạn')]")).click();

		// select destination hotel
		WebElement destination = driver.findElement(By.id("hotel-autocomplete-input-value"));
		destination.sendKeys("Đà Nẵng");
		Thread.sleep(2000);
		destination.sendKeys(Keys.RETURN);

		// click on check-in date
		driver.findElement(By.xpath("//input[@id='hotel-check-in-value']")).click();

		selectDatehotel2("16");

		// select check-out date
		driver.findElement(By.xpath("//input[@id='hotel-check-out-value']")).click();

		WebElement dateWidget = driver.findElement(By.cssSelector("div.date-picker-wrapper:nth-child(3)"));
		List<WebElement> columns = dateWidget.findElements(By.tagName("td"));
		for (WebElement cell : columns) {
			if (cell.getText().equals("20")) {
				cell.click();
				break;
			}

		}

		WebElement clickonperson = driver.findElement(By.cssSelector(".ui-selectmenu-text"));
		clickonperson.click();
		driver.findElement(By.xpath("//button[@class='btn btn-white btn-ts-up']")).click();

		// click on search button
		driver.findElement(By.xpath("//button[@class='hotel-search-button btn btn-search']")).click();
		Thread.sleep(10000);

		WebElement star = driver.findElement(By.cssSelector("#stars-4"));
		if (!star.isSelected()) {
			star.click();
		}
       Thread.sleep(50000);
//		WebElement location = driver.findElement(By.cssSelector("#sublocation-0"));
//		if (!location.isSelected()) {
//			location.click();
//		}

		System.out.println("--------------------------------------------------");
		
		
		WebElement listhoteldiv = driver.findElement(By.cssSelector(".result-items"));
		List<WebElement> listhotel = listhoteldiv.findElements(By.cssSelector(".item-wrapper"));
		System.out.println("number of hotel record :" + listhotel.size());
		for (WebElement item : listhotel) {
			WebElement itemname = item.findElement(By.cssSelector(".item-name"));
			String titleHotel = itemname.getAttribute("title");
			System.out.println(titleHotel);
			
		}			
		
		System.out.println("--------------------------------------------------");
		 
		Thread.sleep(10000);
	// click on book button on search results
			driver.findElement(By.xpath("//span[contains(text(),'Đặt phòng')]")).click();
			ArrayList<String> tabs1 = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(tabs1.get(1));
		}


	// select check-in and check-out date
	public void selectDatehotel2(String date) {

		WebElement startHoliday = driver.findElement(By.className("ng-scope"));
		List<WebElement> columns = startHoliday.findElements(By.tagName("td"));
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

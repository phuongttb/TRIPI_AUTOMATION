package selenium_api;

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

	@Test(priority=1)
	public void TC_01_SearchFlight() throws Exception {
		
		String homePageUrl = driver.getCurrentUrl();
		Assert.assertEquals(homePageUrl, "https://dev.tripi.vn/");

		driver.findElement(By.xpath("//div[contains(text(),'Vé máy bay')]")).click();
		
		// Chọn điểm đi
		WebElement eFromAirport = driver.findElement(By.id("flight-from-airport-value"));
		eFromAirport.sendKeys("HAN");
		Thread.sleep(2000);
		eFromAirport.sendKeys(Keys.RETURN);
		System.out.println("Test: " + eFromAirport.getText());
		// Chọn chiều về
		WebElement eToAirport = driver.findElement(By.id("flight-to-airport-value"));
		eToAirport.sendKeys("SGN");
		Thread.sleep(2000);
		eToAirport.sendKeys(Keys.RETURN);
		System.out.println("Test: " + eToAirport.getText());
		//click vào text khứ hồi
		driver.findElement(By.xpath("//span[contains(text(),'Khứ hồi')]")).click();

		//		
		
		selectDate("06");
		selectDate("08");

		//		try {
		//			Thread.sleep(500);
		//		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//		}
		//		click search button
		WebElement searchbutton = driver.findElement(By.xpath("//button[@class='flight-search-button btn btn-search']"));
        searchbutton.click();

        Thread.sleep(5000);
 
        //Tim cac ket qua va check so luong > 0 hay khong chua check ket qua tra ra dung hay sai
        WebElement outBoundTickets = driver.findElement(By.id("outBoundTickets"));
        List<WebElement> flight = outBoundTickets.findElements(By.xpath("//div[@class='panel panel-default ticket first-ticket']"));
        
        System.out.println(flight.size());
        Assert.assertEquals(true, flight.size()>0);
	}

	public void selectDate(String date) {

		WebElement startHoliday = driver.findElement(By.className("startHoliday"));
		List<WebElement> columns = startHoliday.findElements(By.tagName("td"));
		System.out.println("LEN:  " + columns.size());
		for (WebElement cell : columns) {
			if (cell.getText().equals(date)) {
				cell.click();
				break;
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Test(enabled=false)
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

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		click search button
		WebElement searchbutton = driver.findElement(By.xpath("//div[contains(text(),'Tìm kiếm')]"));
        searchbutton.click();

        Thread.sleep(15000);
        List<WebElement> hotels = driver.findElements(By.className("hsr-item"));
        System.out.println(hotels.size());
        Assert.assertEquals(true, hotels.size()>0);
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
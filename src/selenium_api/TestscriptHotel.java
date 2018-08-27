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
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class TestscriptHotel {
	WebDriver driver;

	@Test
	@Parameters({ "autocompletesearch", "checkindate", "checkoutdate" })

	public void TC_02_SearchHotel2(String autocompletesearch, String checkindate, String checkoutdate)
			throws Exception {

		// click on Hotel tab
		driver.findElement(By.xpath("//div[contains(text(),'Khách sạn')]")).click();

		// chon diem den
		WebElement destination = driver.findElement(By.id("hotel-autocomplete-input-value"));
		destination.sendKeys(autocompletesearch);
		Thread.sleep(2000);
		destination.sendKeys(Keys.RETURN);

		// click on check-in date
		driver.findElement(By.xpath("//input[@id='hotel-check-in-value']")).click();

		// chon ngay checkin
		WebElement dateWidget2 = driver.findElement(By.cssSelector("div.date-picker-wrapper:nth-child(3)"));
		List<WebElement> columns = dateWidget2.findElements(By.tagName("td"));
		for (WebElement cell : columns) {
			if (cell.getText().equals(checkindate)) {
				cell.click();
				break;
			}
			Thread.sleep(1000);
			// select check-out date
			driver.findElement(By.xpath("//input[@id='hotel-check-out-value']")).click();

			WebElement dateWidget = driver.findElement(By.cssSelector("div.date-picker-wrapper:nth-child(3)"));
			List<WebElement> columns2 = dateWidget.findElements(By.tagName("td"));
			for (WebElement cell2 : columns2) {
				if (cell2.getText().equals(checkoutdate)) {
					cell2.click();
					break;
				}

			}
			// Chon so nguoi lon
			WebElement clickonperson = driver.findElement(By.cssSelector(".ui-selectmenu-text"));
			clickonperson.click();
			driver.findElement(By.xpath("//button[@class='btn btn-white btn-ts-up']")).click();

			driver.findElement(By.xpath("//button[@class='hotel-search-button btn btn-search']")).click();
			Thread.sleep(3000);

			// lọc theo sao của khách sạn
			WebElement star = driver.findElement(By.cssSelector("#stars-4"));
			if (!star.isSelected()) {
				star.click();
			}
			Thread.sleep(4000);

			System.out.println("--------------------------------------------------");

			WebElement listhoteldiv = driver.findElement(By.cssSelector(".result-items"));
			List<WebElement> listhotel = listhoteldiv.findElements(By.cssSelector(".item-wrapper"));
			System.out.println("Total hotel item :" + listhotel.size());
			for (WebElement item : listhotel) {
				WebElement itemname = item.findElement(By.cssSelector(".item-name"));
				String titleHotel = itemname.getAttribute("title");
				System.out.println(titleHotel);
				WebElement itemprice = item.findElement(By.cssSelector(".item-price"));
				String price = itemprice.getText();
				System.out.println(price);
			}
			int numhotel=listhotel.size();
			Assert.assertEquals(numhotel > 0, true);
		}
	
	
		
		System.out.println("--------------------------------------------------");

		Thread.sleep(2000);

		// Nhấn vào nút Đặt Phòng

//		 driver.findElement(By.xpath("//span[contains(text(),'Đặt phòng')]")).click();

		WebElement bookbtn = driver.findElement(By.cssSelector(".hotel-view-content-tripi"));
		bookbtn.click();
		ArrayList<String> tabs1 = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs1.get(1));

		
	}
	/** Test
	// PRINT ROOM'S INFORMATION AND CLICK VÀO NÚT ĐẶT PHÒNG
	@Test(enabled = false)
	public void TC_03_BookHotel() throws InterruptedException {

		WebElement listroomdiv = driver.findElement(By.cssSelector(".provider-list"));
		List<WebElement> listroom = listroomdiv
				.findElements(By.cssSelector(".content agency-item-provider displayed ng-scope"));
		System.out.println("Total room item :" + listroom.size());
		for (WebElement item : listroom) {
			WebElement itemname = item.findElement(By.cssSelector(".room-title"));
			System.out.println(itemname);
			WebElement roomprice = item.findElement(By.cssSelector(".fader ng-binding"));
			String price = roomprice.getText();
			System.out.println(price);
		}
		Thread.sleep(10000);

		// click on book button on search results
		driver.findElement(By.xpath("//span[@class='hotel-add-payment-info ng-binding']")).click();
		ArrayList<String> tabs1 = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs1.get(1));
		Thread.sleep(10000);
	}
*/
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

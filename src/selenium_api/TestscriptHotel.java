package selenium_api;

import static org.testng.AssertJUnit.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class TestscriptHotel {
	WebDriver driver;

	@Test
	@Parameters({ "autocompletesearch", "checkindate", "checkoutdate", "number_room", "number_adult", "number_infant" })

	public void TC_01_SearchHotel(String autocompletesearch, String checkindate, String checkoutdate, int number_room,
			int number_adult, int number_infant) throws Exception {

		// click on Hotel tab
		driver.findElement(By.xpath("//div[contains(text(),'Khách sạn')]")).click();

		// chon diem den
		WebElement destination = driver.findElement(By.id("hotel-autocomplete-input-value"));
		destination.sendKeys(autocompletesearch);
		Thread.sleep(2000);
		destination.sendKeys(Keys.RETURN);

		// click on check-in date
		driver.findElement(By.xpath("//input[@id='hotel-check-in-value']")).click();

		// chọn ngày check in
		WebElement fromDate = driver.findElement(By.xpath("(//div[normalize-space()='" + checkindate + "'])[1]"));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", fromDate);
		Thread.sleep(1000);
		// Chon ngay check-out date
		WebElement toDate = driver.findElement(By.xpath("(//div[normalize-space()='" + checkoutdate + "'])[2]"));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", toDate);
		Thread.sleep(1000);
		// click vào truong so khach
		WebElement clickonperson = driver.findElement(By.cssSelector(".ui-selectmenu-text"));
		clickonperson.click();

		// Chon so phong
		for (int i = 0; i < number_room; i++) {
			driver.findElement(By.xpath("(//button[@class='btn btn-white btn-ts-up'])[1]")).click();
		}
		Thread.sleep(1000);

		// Chon so adult
		for (int i = 0; i < number_adult; i++) {
			driver.findElement(By.xpath("(//button[@class='btn btn-white btn-ts-up'])[2]")).click();
		}
		Thread.sleep(1000);

		// Chon so infant
		for (int i = 0; i < number_infant; i++) {
			driver.findElement(By.xpath("(//button[@class='btn btn-white btn-ts-up'])[3]")).click();
		}
		Thread.sleep(1000);

		driver.findElement(By.xpath("//button[@class='btn btn-white btn-ts-up']")).click();

		// click vào nút tìm kiếm
		driver.findElement(By.xpath("//button[@class='hotel-search-button btn btn-search']")).click();
		Thread.sleep(3000);

		// lọc theo sao của khách sạn
		WebElement star = driver.findElement(By.cssSelector("#stars-4"));
		if (!star.isSelected()) {
			star.click();
		}
		Thread.sleep(3000);

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
		int numhotel = listhotel.size();
		assertTrue(numhotel > 0);

		System.out.println("--------------------------------------------------");

	}

	// PRINT ROOM'S INFORMATION AND CLICK VÀO NÚT ĐẶT PHÒNG
	@Test()
	@Parameters({ "room_name" })
	public void TC_02_ChooseRoom(String room_name) throws InterruptedException {

		// Nhấn vào nút Đặt Phòng
		driver.findElement(By.xpath("//span[contains(text(),'Đặt phòng')]")).click();
		ArrayList<String> tabs1 = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs1.get(1));
        Thread.sleep(5000);  

		List<WebElement> listroom = driver.findElements(By.cssSelector(".list-rooms-by-agency"));
		System.out.println("Total room item :" + listroom.size());
		int numroom = listroom.size();
		assertTrue(numroom > 0);

		Thread.sleep(4000);
		WebElement selectBtn = driver.findElement(
				By.xpath("//div[text()='" + room_name + "']/../../following-sibling::*//span[text()='Đặt ngay']"));
		selectBtn.click();

	}

	@Test()
	@Parameters({ "user_name", "phone_number", "email", "address" })
	public void TC_03_Payment(String user_name, String phone_number, String email, String address)
			throws InterruptedException {
		ArrayList<String> tabs1 = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs1.get(1));
		Thread.sleep(5000);
		driver.findElement(By.xpath("//input[@name='name']")).sendKeys(user_name);
		Thread.sleep(1000);
		driver.findElement(By.xpath("//input[@name='phoneNumber']")).sendKeys(phone_number);
		driver.findElement(By.xpath("//input[@name='email']")).sendKeys(email);
		driver.findElement(By.xpath("//textarea[@name='address']")).sendKeys(address);
		WebElement paymentmethod = driver.findElement(By.cssSelector("#payment-method-3"));
		paymentmethod.click();
		Thread.sleep(1000);
		WebElement paymentbtn = driver.findElement(By.cssSelector(".hotel-payment-button"));
		paymentbtn.click();
		Thread.sleep(2000);
		WebElement confirmbtn = driver.findElement(By.cssSelector(".btn-success"));
		confirmbtn.click();
		for (int i = 0; i < 10; i++) {
			String currentURL = driver.getCurrentUrl();
			if (currentURL.contains("booking")) {
				Thread.sleep(5000);
			} else {
				break;
			}
		}
		String currentURL = driver.getCurrentUrl();
		boolean redirectURL = currentURL.contains("https://pay.vnpay.vn/Transaction/PaymentMethod.html?token=");
		System.out.print("Đi tới cổng thanh toán thành công");
		AssertJUnit.assertEquals(redirectURL, true);

	}

	@BeforeClass
	public void beforeClass() {
		driver = new FirefoxDriver();
		driver.get("https://www.tripi.vn/");
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		driver.manage().window().maximize();

	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}
}

package selenium_api;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ExampleTest {
	WebDriver driver = new FirefoxDriver();

	@BeforeClass
	public void setUp() {
		// driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
	}

	@BeforeMethod
	public void beforeTest() {
		driver.get(
				"https://www.tripi.vn/flights/booking?requestIdOutBound=-583503496&fromAirport=HAN&toAirport=SGN&departureDate=27-08-2018&ticketClassId=1&numAdults=1&numChildren=1&numInfants=1&oneWay=1&ticketOutBoundId=1183030802&agencyIdOutBound=3");

	}

	@Test(enabled = false)

	public void TC_01_SearchTicket_RoundTrip() throws Exception {
		driver.get("https://www.tripi.vn/");

		driver.findElement(By.cssSelector(".header-menu")).click();
		Thread.sleep(1000);
		driver.findElement(By.id("username")).sendKeys("0988100942");
		Thread.sleep(1000);
		WebElement pass = driver.findElement(By.id("password"));
		pass.sendKeys("123456");
		driver.findElement(By.id("submit-btn")).click();
		Thread.sleep(1000);
		driver.findElement(By.cssSelector(".username")).click();
		driver.findElement(By.xpath("//a[@data-ng-if='user.moduleBooker']")).click();
		Thread.sleep(5000);
		driver.findElement(By.cssSelector("a[ui-sref='booker.bookingManage']")).click();
		driver.findElement(By.cssSelector(".ticket-info")).click();
		Thread.sleep(1000);


	}

	@Test()

	public void Booking() throws Exception {
		// Nguoi lon 1
		driver.findElement(By.cssSelector("input[ng-model='adult.lastName']")).sendKeys("phuong");
		Thread.sleep(1000);
		driver.findElement(By.xpath("//input[contains(@class,'first-name')]")).sendKeys("tran");
		new Select(driver.findElement(By.xpath("(//select[@ng-model='adult.gender'])[1]"))).selectByVisibleText("Nữ");

		// tre em 1
		driver.findElement(By.xpath("//input[@ng-model='child.lastName']")).sendKeys("phan");
		driver.findElement(By.xpath("//input[@ng-model='child.firstName']")).sendKeys("nguyet");
		new Select(driver.findElement(By.xpath("(//select[@ng-model='child.gender'])[1]"))).selectByVisibleText("Nam");
		Thread.sleep(2000);
		new Select(driver.findElement(By.xpath("//select[@ng-model='child.dayOfBirth']"))).selectByVisibleText("12");
		Thread.sleep(2000);
		new Select(driver.findElement(By.xpath("//select[@ng-model='child.monthOfBirth']"))).selectByVisibleText("03");
		Thread.sleep(2000);
		new Select(driver.findElement(By.xpath("//select[@ng-model='child.yearOfBirth']")))
				.selectByVisibleText("2016");
		Thread.sleep(2000);

		// em be
		driver.findElement(By.xpath("//input[@ng-model='infant.lastName']")).sendKeys("phan");
		driver.findElement(By.xpath("//input[@ng-model='infant.firstName']")).sendKeys("dat");
		new Select(driver.findElement(By.xpath("//select[@ng-model='infant.gender'][1]"))).selectByVisibleText("Nữ");
		Thread.sleep(2000);
		new Select(driver.findElement(By.xpath("//select[@ng-model='infant.dayOfBirth']"))).selectByVisibleText("12");
		Thread.sleep(2000);
		new Select(driver.findElement(By.xpath("//select[@ng-model='infant.monthOfBirth']"))).selectByVisibleText("03");
		Thread.sleep(2000);
		new Select(driver.findElement(By.xpath("//select[@ng-model='infant.yearOfBirth']")))
				.selectByVisibleText("2017");
		Thread.sleep(2016);

		// NHẬP THÔNG TIN LIÊN HỆ

		new Select(driver.findElement(By.id("ticket-booking-select-title"))).selectByVisibleText("Ông");
		driver.findElement(By.xpath("//input[@ng-model='contactInfo.lastName']")).sendKeys("Tran");
		driver.findElement(By.xpath("//input[@ng-model='contactInfo.firstName']")).sendKeys("Phuong");
		driver.findElement(By.xpath("//input[@ng-model='contactInfo.email']")).sendKeys("phuong@gmail.com");
		driver.findElement(By.xpath("//input[@ng-model='contactInfo.phone1']")).sendKeys("0942712719");

		// //Select payment method - trip credit
		WebElement paymentmethod = driver.findElement(By.cssSelector("#payment-method-3"));
		paymentmethod.click();
		Thread.sleep(1000);

		WebElement paymentbtn = driver.findElement(By.cssSelector(".flight-payment-button"));
		paymentbtn.click();
		Thread.sleep(2000);

		WebElement confirmbtn = driver.findElement(By.cssSelector(".btn-success"));
		confirmbtn.click();

		// Đi tới cổng thanh toán
		// WebElement confirmdialog =driver.findElement(By.cssSelector(".btn-success"));
		// confirmdialog.click();
		//
	}
}
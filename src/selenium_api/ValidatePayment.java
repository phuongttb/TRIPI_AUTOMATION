package selenium_api;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class ValidatePayment {
	WebDriver driver;

	private void commonTestProcedure(String lastname_adult, String firstname_adult, String gender_adult,
			String firstname_child, String lastname_child, String gender_child, String day_child, String month_child,
			String year_child, String lastname_infant, String firstname_infant, String gender_infant, String day_infant,
			String month_infant, String year_infant, String gender_contact, String lastName_contact,
			String firstname_contact, String email_contact, String phone_contact) throws InterruptedException {

		// NHAP THONG TIN NGUOI LON
		driver.findElement(By.xpath("//input[@ng-model='adult.lastName']")).sendKeys(lastname_adult);
		// Thread.sleep(1000);
		driver.findElement(By.xpath("//input[contains(@class,'first-name')]")).sendKeys(firstname_adult);
		new Select(driver.findElement(By.xpath("(//select[@ng-model='adult.gender'])[1]")))
				.selectByVisibleText(gender_adult);

		// NHAP THONG TIN TRE EM
		driver.findElement(By.xpath("//input[@ng-model='child.lastName']")).sendKeys(firstname_child);
		driver.findElement(By.xpath("//input[@ng-model='child.firstName']")).sendKeys(lastname_child);
		new Select(driver.findElement(By.xpath("(//select[@ng-model='child.gender'])[1]")))
				.selectByVisibleText(gender_child);
		Thread.sleep(2000);
		new Select(driver.findElement(By.xpath("//select[@ng-model='child.dayOfBirth']")))
				.selectByVisibleText(day_child);
		Thread.sleep(2000);
		new Select(driver.findElement(By.xpath("//select[@ng-model='child.monthOfBirth']")))
				.selectByVisibleText(month_child);
		Thread.sleep(2000);
		new Select(driver.findElement(By.xpath("//select[@ng-model='child.yearOfBirth']")))
				.selectByVisibleText(year_child);
		Thread.sleep(2000);

		// NHAP THONG TIN EM BE
		driver.findElement(By.xpath("//input[@ng-model='infant.lastName']")).sendKeys(lastname_infant);
		driver.findElement(By.xpath("//input[@ng-model='infant.firstName']")).sendKeys(firstname_infant);
		new Select(driver.findElement(By.xpath("//select[@ng-model='infant.gender'][1]")))
				.selectByVisibleText(gender_infant);
		Thread.sleep(2000);
		new Select(driver.findElement(By.xpath("//select[@ng-model='infant.dayOfBirth']")))
				.selectByVisibleText(day_infant);
		Thread.sleep(2000);
		new Select(driver.findElement(By.xpath("//select[@ng-model='infant.monthOfBirth']")))
				.selectByVisibleText(month_infant);
		Thread.sleep(2000);
		new Select(driver.findElement(By.xpath("//select[@ng-model='infant.yearOfBirth']")))
				.selectByVisibleText(year_infant);
		Thread.sleep(2016);

		// NHẬP THÔNG TIN LIÊN HỆ
		new Select(driver.findElement(By.id("ticket-booking-select-title"))).selectByVisibleText(gender_contact);
		driver.findElement(By.xpath("//input[@ng-model='contactInfo.lastName']")).sendKeys(lastName_contact);
		driver.findElement(By.xpath("//input[@ng-model='contactInfo.firstName']")).sendKeys(firstname_contact);
		driver.findElement(By.xpath("//input[@ng-model='contactInfo.email']")).sendKeys(email_contact);
		driver.findElement(By.xpath("//input[@ng-model='contactInfo.phone1']")).sendKeys(phone_contact);

	}

	@Test(priority = 0)
	public void TC_ValidatLetBlankCustomer() throws InterruptedException {

		WebElement paymentmethod = driver.findElement(By.cssSelector("#payment-method-3"));
		paymentmethod.click();
		Thread.sleep(1000);
		WebElement paymentbtn = driver.findElement(By.cssSelector(".flight-payment-button"));
		paymentbtn.click();

		String errormssage = driver.findElement(By.cssSelector(".message")).getText();
		Assert.assertEquals("Vui lòng kiểm tra lại thông tin hành khách!", errormssage);
		System.out.println(errormssage);
		Thread.sleep(3000);
	}

	@Test(priority = 1)
	@Parameters({ "vouchercode" })
	public void TC_TC_ValidateVoucherCode(String vouchercode) throws InterruptedException {
		driver.navigate().refresh();
		WebElement voucher = driver.findElement(By.cssSelector(".coupon-code"));
		voucher.sendKeys(vouchercode);
		WebElement voucherbtn = driver.findElement(By.cssSelector(".btn-submit-code"));
		voucherbtn.click();
		Thread.sleep(3000);
		String errormssage = driver.findElement(By.xpath("//span[contains(text(),'Mã khuyến mãi không hợp lệ!')]"))
				.getText();
		System.out.println("TC_TC_ValidateVoucherCode errormssage: " + errormssage);
		Assert.assertEquals(errormssage, "Mã khuyến mãi không hợp lệ!");
	}

	@Test(priority = 2)
	@Parameters({ "lastname_adult", "firstname_adult", "gender_adult", "firstname_child", "lastname_child",
			"gender_child", "day_child", "month_child", "year_child", "lastname_infant", "firstname_infant",
			"gender_infant", "day_infant", "month_infant", "year_infant", "gender_contact", "lastname_contact",
			"firstname_contact", "email_contact", "phone_contact" })
	public void TC_WithoutSelectPaymentMethod(String lastname_adult, String firstname_adult, String gender_adult,
			String firstname_child, String lastname_child, String gender_child, String day_child, String month_child,
			String year_child, String lastname_infant, String firstname_infant, String gender_infant, String day_infant,
			String month_infant, String year_infant, String gender_contact, String lastname_contact,
			String firstname_contact, String email_contact, String phone_contact) throws InterruptedException {
		driver.navigate().refresh();
		commonTestProcedure(lastname_adult, firstname_adult, gender_adult, firstname_child, lastname_child,
				gender_child, day_child, month_child, year_child, lastname_infant, firstname_infant, gender_infant,
				day_infant, month_infant, year_infant, gender_contact, lastname_contact, firstname_contact,
				email_contact, phone_contact);

		driver.navigate().refresh();
		WebElement paymentbtn = driver.findElement(By.cssSelector(".flight-payment-button"));
		paymentbtn.click();
		Thread.sleep(8000);		
		String errormssage = driver.findElement(By.cssSelector(".message")).getText();
//		if(!email_contact.isEmpty()){
//			 assertEquals(errormssage, "Vui lòng kiểm tra lại thông tin hành khách!");
//			}
//			if(!phone_contact.isEmpty()){
//			  assertEquals(errormssage, "Vui lòng kiểm tra lại thông tin hành khách!");
//			}
		System.out.println("WithoutSelectPaymentMethod: " + errormssage);
		Assert.assertEquals(errormssage, "Vui lòng lựa chọn phương thức thanh toán");

	}
	
	

	@BeforeClass
	public void beforeClass() {
		driver = new FirefoxDriver();
		driver.get(
				"https://www.tripi.vn/flights/booking?requestIdOutBound=-1959182868&fromAirport=HAN&toAirport=SGN&departureDate=07-09-2018&ticketClassId=1&numAdults=1&numChildren=1&numInfants=1&oneWay=1&ticketOutBoundId=-351062617&agencyIdOutBound=3");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@AfterClass
	public void afterClass() {
		driver.quit();

	}

}
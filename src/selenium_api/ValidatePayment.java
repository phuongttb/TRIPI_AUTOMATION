package selenium_api;

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

	private void commonTestProcedure(String LastName_payment, String FirstName_payment, String Gender_payment,
			String Baggage_payment, String GenderContact_pay, String LastNameContact_pay, String FirstNameContact_pay,
			String email_contact, String phone_contact) throws InterruptedException {

		driver.findElement(By.xpath("//input[@ng-model='adult.lastName']")).sendKeys(LastName_payment);
		// Thread.sleep(1000);
		driver.findElement(By.xpath("//input[contains(@class,'first-name')]")).sendKeys(FirstName_payment);
		new Select(driver.findElement(By.xpath("(//select[@ng-model='adult.gender'])[1]")))
				.selectByVisibleText(Gender_payment);

		// NHẬP THÔNG TIN LIÊN HỆ
		new Select(driver.findElement(By.id("ticket-booking-select-title"))).selectByVisibleText(GenderContact_pay);
		driver.findElement(By.xpath("//input[@ng-model='contactInfo.lastName']")).sendKeys(LastNameContact_pay);
		driver.findElement(By.xpath("//input[@ng-model='contactInfo.firstName']")).sendKeys(FirstNameContact_pay);
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
	@Parameters({ "LastName_payment", "FirstName_payment", "Gender_payment", "Baggage_payment", "GenderContact_pay",
			"LastNameContact_pay", "FirstNameContact_pay", "email_contact", "phone_contact" })
	public void TC_WithoutSelectPaymentMethod(String LastName_payment, String FirstName_payment, String Gender_payment,
			String Baggage_payment, String GenderContact_pay, String LastNameContact_pay, String FirstNameContact_pay,
			String email_contact, String phone_contact) throws InterruptedException {

		commonTestProcedure(LastName_payment, FirstName_payment, Gender_payment, Baggage_payment, GenderContact_pay,
				LastNameContact_pay, FirstNameContact_pay, email_contact, phone_contact);

		WebElement paymentbtn = driver.findElement(By.cssSelector(".flight-payment-button"));
		paymentbtn.click();
		String errormssage = driver.findElement(By.cssSelector(".message")).getText();
		Assert.assertEquals("Vui lòng lựa chọn phương thức thanh toán", errormssage);
		System.out.println(errormssage);

	}

	// Validate vouchercode
	@Test(priority = 2)
	@Parameters({ "vouchercode" })
	public void TC_TC_ValidateVoucherCode(String vouchercode) throws InterruptedException {

		WebElement voucher = driver.findElement(By.cssSelector(".coupon-code"));
		voucher.sendKeys(vouchercode);
		WebElement voucherbtn = driver.findElement(By.cssSelector(".btn-submit-code"));
		voucherbtn.click();
		String errormssage = driver.findElement(By.xpath("//span[contains(text(),'Mã khuyến mãi không hợp lệ!')]"))
				.getText();
		Assert.assertEquals("Mã khuyến mãi không hợp lệ!", errormssage);
		System.out.println(errormssage);

	}

	@BeforeClass
	public void beforeClass() {
		driver = new FirefoxDriver();
		driver.get(
				"https://www.tripi.vn/flights/booking?requestIdOutBound=-613056168&fromAirport=HAN&toAirport=SGN&departureDate=27-08-2018&ticketClassId=1&numAdults=1&numChildren=0&numInfants=0&oneWay=1&ticketOutBoundId=-971122508&agencyIdOutBound=3");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@AfterClass
	public void afterClass() {
		driver.quit();

	}

}
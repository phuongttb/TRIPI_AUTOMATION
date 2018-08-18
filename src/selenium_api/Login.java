package selenium_api;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class Login {
	WebDriver driver;
	@Test ()
	@Parameters({ "username", "password" ,"orderid"})
	public void TC_01_Filterorder(String username, String password, String orderid) throws InterruptedException {
		driver.get("https://www.tripi.vn/");

		driver.findElement(By.cssSelector(".header-menu")).click();
		Thread.sleep(1000);
		driver.findElement(By.id("username")).sendKeys(username);
		Thread.sleep(1000);
		WebElement pass = driver.findElement(By.id("password"));
		pass.sendKeys(password);
		driver.findElement(By.id("submit-btn")).click();
		Thread.sleep(3000);
		driver.findElement(By.cssSelector(".username")).click();
		driver.findElement(By.xpath("//a[@data-ng-if='user.moduleBooker']")).click();
		Thread.sleep(5000);
		driver.findElement(By.cssSelector("a[ui-sref='booker.bookingManage']")).click();
		Thread.sleep(2000);
		driver.findElement(By.id("btn-filter")).click();
		Thread.sleep(2000);
		driver.findElement(By.id("bookingIdInput")).sendKeys(orderid);
		Thread.sleep(5000);
		driver.findElement(By.cssSelector(".btn-confirm")).click();
		Thread.sleep(4000);
		WebElement bookinglisttable = driver.findElement(By.xpath("//div[@data-ng-controller='bookingManageController']"));
		List<WebElement> bookinglist = bookinglisttable.findElements(By.cssSelector(".list-booking"));
		System.out.println("Tổng số booking:" + bookinglist.size());	
	}	
 
	@BeforeClass
	public void beforeClass() {
		driver = new FirefoxDriver();
		driver.get("https://www.tripi.vn/");
//		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@AfterClass
	public void afterClass() {
		driver.quit();

	}

}
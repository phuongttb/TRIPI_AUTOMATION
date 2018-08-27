package selenium_api;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class ValidateLoginForm {
	WebDriver driver;
	
	
	private void commonProcedure(String username, String password,String expectedOutput) throws InterruptedException {
		driver.get("https://www.tripi.vn/");

		driver.findElement(By.cssSelector(".header-menu")).click();
		Thread.sleep(1000);
		driver.findElement(By.id("username")).sendKeys(username);
		WebElement pass = driver.findElement(By.id("password"));
		pass.sendKeys(password);
		driver.findElement(By.id("submit-btn")).click();
		Thread.sleep(3000);
		String errormssage = driver.findElement(By.xpath("//div[contains(h4,'Mật khẩu không hợp lệ')]")).getText();
		System.out.println("Validate username and Passoword: " + errormssage);
		Assert.assertEquals(errormssage,expectedOutput);
	}
	
	@Test
	@Parameters({ "username", "password","expectedOutput" })
	public void TC_01_Filterorder(String username, String password,String expectedOutput) throws InterruptedException {
		commonProcedure(username,password,expectedOutput);
	}	
 
	@BeforeClass
	public void beforeClass() {
		driver = new FirefoxDriver();
		driver.get("https://www.tripi.vn/");
		driver.manage().window().maximize();
	}

	@AfterClass
	public void afterClass() {
		driver.quit();

	}

}
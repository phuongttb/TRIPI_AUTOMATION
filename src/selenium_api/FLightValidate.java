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
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class FLightValidate {
	WebDriver driver;
	
	@Test()
	@Parameters({ "flightfromairportv", "flighttoairportv", "flightcheckindatev" })
	public void TC_01_Validatesamedestination(String flightfromairportv, String flighttoairportv,
			String flightcheckindatev) throws Exception {

		driver.findElement(By.xpath("//div[contains(text(),'Vé máy bay')]")).click();
		
		WebElement eFromAirport = driver.findElement(By.id("flight-from-airport-value"));
		eFromAirport.sendKeys(flightfromairportv);
		Thread.sleep(2000);
		eFromAirport.sendKeys(Keys.RETURN);


		WebElement eToAirport = driver.findElement(By.id("flight-to-airport-value"));
		eToAirport.sendKeys(flighttoairportv);
		Thread.sleep(2000);
		eToAirport.sendKeys(Keys.RETURN);
		

		WebElement depaturedate = driver.findElement(By.xpath("//input[@id='flight-checkin-date']"));
		depaturedate.click();
		WebElement dateWidget=driver.findElement(By.cssSelector(".dropdown-menu"));
		List<WebElement> columns = dateWidget.findElements(By.tagName("td"));
		for (WebElement cell : columns) {
			if (cell.getText().equals(flightcheckindatev)) {
				cell.click();
				break;
			}
		}

		WebElement searchbutton = driver
				.findElement(By.xpath("//button[@class='flight-search-button btn btn-search']"));
		searchbutton.click();
		Thread.sleep(2000);
		String errormssage = driver.findElement(By.xpath("//div[@data-ng-bind-html='message']")).getText();
		Assert.assertEquals("Điểm đến không được trùng với điểm khởi hành. Vui lòng lựa chọn lại.", errormssage);
		System.out.println(errormssage);

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
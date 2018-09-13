package selenium_api;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import vn.tripi.testing.commons.BaseClass;
import vn.tripi.testing.utils.PriceConvert;
import vn.tripi.testing.utils.TimeUtils;

public class SortTicketandComparePrice extends BaseClass {
	@Test()
	@Parameters({ "flight_fromairport", "flight_toairport", "flight_checkindate","selectagencyintbound" })
	public void TC_Flight_CheckAllInformation(String flight_fromairport, String flight_toairport,
			String flight_checkindate, String selectagencyintbound) throws Exception {

		driver.findElement(By.xpath("//div[contains(text(),'Vé máy bay')]")).click();

		WebElement eFromAirport = driver.findElement(By.id("flight-from-airport-value"));
		eFromAirport.sendKeys(flight_fromairport);
		Thread.sleep(2000);
		eFromAirport.sendKeys(Keys.RETURN);

		WebElement eToAirport = driver.findElement(By.id("flight-to-airport-value"));
		eToAirport.sendKeys(flight_toairport);
		Thread.sleep(2000);
		eToAirport.sendKeys(Keys.RETURN);
		
		// chọn ngày di
		WebElement depaturedate = driver.findElement(By.xpath("//input[@id='flight-checkin-date']"));
		depaturedate.click();
		selectdatepicker(flight_checkindate);

        
		WebElement searchbutton = driver
				.findElement(By.xpath("//button[@class='flight-search-button btn btn-search']"));
		searchbutton.click();
		Thread.sleep(3000);
		WebElement filterbtn=driver.findElement(By.cssSelector(".btn-link"));
		filterbtn.click();
		Thread.sleep(4000);
		
		WebElement outBoundTicketsDiv = driver.findElement(By.cssSelector("#outBoundTickets"));
		List<WebElement> outboundTickets = outBoundTicketsDiv.findElements(By.cssSelector(".ticket-info"));
		boolean Selected = false;
		
		String ob_from = new String();
		String ob_to = new String();
		String ob_timefrom = new String();
		String ob_timeto = new String();
		String ob_price = new String();
		int preFarePrice =-1;
	
		
		for (WebElement tickets : outboundTickets) {
			WebElement logo = tickets.findElement(By.cssSelector(".alogo"));
			String agency = logo.getAttribute("alt");

			
				List<WebElement> route = tickets.findElements(By.cssSelector(".ticket-info-text"));
				ob_from = (route.get(0)).getText();
				ob_to = (route.get(1)).getText();

				// thong tin gio di/gio den
				List<WebElement> time = tickets.findElements(By.cssSelector(".ticket-time"));
				ob_timefrom = (time.get(0)).getText();
				ob_timeto = (time.get(1)).getText();


				// thong tin gia
				WebElement price = tickets.findElement(By.cssSelector(".ticket-price"));
				ob_price = price.getText();

				WebElement selectBtnob = tickets.findElement(By.cssSelector(".flight-select-single-ticket"));
//				selectBtnob.click();
				TimeUtils.sleep(2);
				System.out.println(agency);
				System.out.println(ob_from + "-->" + ob_to);
				System.out.println(ob_timefrom + "-->" + ob_timeto);
				System.out.println("Gia ve : " + ob_price);
				int farePrice = PriceConvert.getPrice(ob_price);
				System.out.println("Compare ["+farePrice+"] vs ["+preFarePrice+"]");
				Assert.assertEquals(farePrice>=preFarePrice, true);
				preFarePrice=farePrice;
			}
						
	
		if (!Selected) {
			Assert.fail("Không có vé. Vui lòng chọn vé khác!");
		}
		
		
	}
	public void selectdatepicker(String date) {
		WebElement dateWidget = driver.findElement(By.className("startHoliday"));
		List<WebElement> columns = dateWidget.findElements(By.tagName("td"));
		for (WebElement cell : columns) {
			if (cell.getText().equals(date)) {
				cell.click();
				break;
			}
		}
	}
}
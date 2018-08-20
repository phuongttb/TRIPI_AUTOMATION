package selenium_api;

import java.util.Date;
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

public class SearchTicketDomestic {
	WebDriver driver;

	// TÌM KIẾM/FILTER RỒI COUNT SỐ VÉ MỘT CHIỀU RỒI IN THÔNG TIN SỐ VÉ ( MỘT CHIỀU)
	@Test
	@Parameters({ "flightfromairport1", "flighttoairport1", "flightcheckindate1",
			"selectagencyoutbound1", "selectagencyintbound1", "numadult", "children" })
	public void TC_01_SearchFilterPrintTicket_OneTrip(String flightfromairport1, String flighttoairport1,
			String flightcheckindate1, String selectagencyoutbound1,
			String selectagencyintbound1, int numadult, int children) throws Exception {

		// Click on "vé máy bay" tab
		driver.findElement(By.xpath("//div[contains(text(),'Vé máy bay')]")).click();

		// Enter "From" station code eg.HAN
		WebElement eFromAirport = driver.findElement(By.id("flight-from-airport-value"));
		eFromAirport.sendKeys(flightfromairport1);
		Thread.sleep(2000);
		eFromAirport.sendKeys(Keys.RETURN);

		// Enter "To" station code eg.SGN
		WebElement eToAirport = driver.findElement(By.id("flight-to-airport-value"));
		eToAirport.sendKeys(flighttoairport1);
		Thread.sleep(2000);
		eToAirport.sendKeys(Keys.RETURN);

		// click on Departure date
		WebElement depaturedate = driver.findElement(By.xpath("//input[@id='flight-checkin-date']"));
		depaturedate.click();

		selectdatepicker(flightcheckindate1);

		WebElement clickpassenger = driver.findElement(By.cssSelector(".ui-selectmenu-text"));
		clickpassenger.click();
		// Chọn số người lớn
		for (int i = 0; i < numadult; i++) {
			driver.findElement(By.xpath("(//button[@class='btn btn-white btn-ts-up'])[1]")).click();
		}
		Thread.sleep(1000);
		// chọn số trẻ em
		for (int i = 0; i < children; i++) {
			driver.findElement(By.xpath("(//button[@class='btn btn-white btn-ts-up'])[2]")).click();
		}
		Thread.sleep(1000);

		// all fields filled in. Now click on search
		WebElement searchbutton = driver
				.findElement(By.xpath("//button[@class='flight-search-button btn btn-search']"));
		searchbutton.click();
		Thread.sleep(3000);
		//Filter theo hãng hàng không 
		WebElement classticket = driver.findElement(By.xpath("//*[contains(text(),'Vietnam Airlines')][1]"));
		classticket.click();
		Thread.sleep(3000);
		//Filter theo hạng vé
		driver.findElement(By.cssSelector(".btn-default"));
		WebElement classticket2 = driver.findElement(By.xpath("//*[contains(text(),'Phổ Thông')]"));
		classticket2.click();

		// Đếm số vé một chiều và in thông tin vé
		WebElement outBoundTicketsDiv = driver.findElement(By.cssSelector("#outBoundTickets"));
		List<WebElement> outboundTickets = outBoundTicketsDiv.findElements(By.cssSelector(".ticket-info"));
		System.out.println("Tổng số vé một chiều:" + outboundTickets.size());
		for (WebElement ticket : outboundTickets) {
			WebElement logo = ticket.findElement(By.cssSelector(".alogo"));
			String agency = logo.getAttribute("alt");
			List<WebElement> times = ticket.findElements(By.className("departure-time"));
			String startDate = times.get(0).findElement(By.className("ticket-time")).getText();
			String endDate = times.get(1).findElement(By.className("ticket-time")).getText();
			System.out.println(agency);
			System.out.println(startDate + "->" + endDate);
			System.out.println("--------------------------------------------------------");

		}
		driver.navigate().back();
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

	// TÌM KIẾM VÀ COUNT SỐ VÉ MỘT CHIỀU RỒI IN THÔNG TIN SỐ VÉ ( MỘT CHIỀU)
	@Test
	@Parameters({ "flightfromairport3", "flighttoairport3", "flightcheckindate3", "flightcheckoutdate3",
			"selectagencyoutbound3", "selectagencyintbound3" })
	public void TC_02_SearchPrintTicketInformation_OneTrip(String flightfromairport3, String flighttoairport3,
			String flightcheckindate3, String flightcheckoutdate3, String selectagencyoutbound3,
			String selectagencyintbound3) throws Exception {

		// Click on "vé máy bay" tab
		driver.findElement(By.xpath("//div[contains(text(),'Vé máy bay')]")).click();

		// Enter "From" station code eg.HAN
		WebElement eFromAirport = driver.findElement(By.id("flight-from-airport-value"));
		eFromAirport.sendKeys(flightfromairport3);
		Thread.sleep(2000);
		eFromAirport.sendKeys(Keys.RETURN);

		// Enter "To" station code eg.SGN
		WebElement eToAirport = driver.findElement(By.id("flight-to-airport-value"));
		eToAirport.sendKeys(flighttoairport3);
		Thread.sleep(2000);
		eToAirport.sendKeys(Keys.RETURN);

		// click on Departure date
		WebElement depaturedate = driver.findElement(By.xpath("//input[@id='flight-checkin-date']"));
		depaturedate.click();

		selectdatepicker1(flightcheckoutdate3);


		// all fields filled in. Now click on search
		WebElement searchbutton = driver
				.findElement(By.xpath("//button[@class='flight-search-button btn btn-search']"));
		searchbutton.click();
		Thread.sleep(2000);

		// Chon ve chieu di
		WebElement outBoundTicketsDiv = driver.findElement(By.cssSelector("#outBoundTickets"));
		List<WebElement> outboundTickets = outBoundTicketsDiv.findElements(By.cssSelector(".ticket-info"));
		System.out.println("Tổng số vé một chiều:" + outboundTickets.size());
		for (WebElement ticket : outboundTickets) {
			WebElement logo = ticket.findElement(By.cssSelector(".alogo"));
			String agency = logo.getAttribute("alt");
			List<WebElement> times = ticket.findElements(By.className("departure-time"));
			String startDate = times.get(0).findElement(By.className("ticket-time")).getText();
			String endDate = times.get(1).findElement(By.className("ticket-time")).getText();
			System.out.println(agency);
			System.out.println(startDate + "->" + endDate);
			System.out.println("--------------------------------------------------------");

		}

	}
	
	public void selectdatepicker1(String date) {
		WebElement dateWidget = driver.findElement(By.className("startHoliday"));
		List<WebElement> columns = dateWidget.findElements(By.tagName("td"));
		for (WebElement cell : columns) {
			if (cell.getText().equals(date)) {
				cell.click();
				break;
			}
		}
	}

	// SEARCH FLIGHT : SEARCH RESULTS NOT SATISFY SEARCH CONDITION

	@Test()
	@Parameters({ "flightfromairport2", "flighttoairport2", "flightcheckindate2", "flightcheckoutdate2",
			"selectagencyoutbound2", "selectagencyintbound2" })
	public void TC_02_SearchFligh_notsatisfysearchcondition(String flightfromairport2, String flighttoairport2,
			String flightcheckindate2, String flightcheckoutdate2, String selectagencyoutbound2,
			String selectagencyintbound2) throws Exception {

		// Click on "vé máy bay" tab
		driver.navigate().to("https://www.tripi.vn/");

		// Enter "From" station code eg.HAN
		WebElement eFromAirport = driver.findElement(By.id("flight-from-airport-value"));
		eFromAirport.sendKeys(flightfromairport2);
		Thread.sleep(2000);
		eFromAirport.sendKeys(Keys.RETURN);

		// Enter "To" station code eg.SGN
		WebElement eToAirport = driver.findElement(By.id("flight-to-airport-value"));
		eToAirport.sendKeys(flighttoairport2);
		Thread.sleep(2000);
		eToAirport.sendKeys(Keys.RETURN);

		// Click on RoundTrip tab
		driver.findElement(By.xpath("//span[contains(text(),'Khứ hồi')]")).click();
		System.out.print(new Date());

		// click on Departure date
		WebElement depaturedate = driver.findElement(By.xpath("//input[@id='flight-checkin-date']"));
		depaturedate.click();

		// click on Departure date
		WebElement depaturedate2 = driver.findElement(By.xpath("//input[@id='flight-checkin-date']"));
		depaturedate2.click();

		selectDate3(flightcheckoutdate2);
		

		Thread.sleep(10000);
		// all fields filled in. Now click on search
		WebElement searchbutton = driver
				.findElement(By.xpath("//button[@class='flight-search-button btn btn-search']"));
		searchbutton.click();

		Thread.sleep(10000);

		String errormssage = driver.findElement(By.xpath("//span[contains(text(),'Không có chuyến bay')]")).getText();
		Assert.assertEquals("Không có chuyến bay", errormssage);
		System.out.println(errormssage);

		// Navigate to previous page
		driver.navigate().back();
	}

	public void selectDate3(String date) {
		WebElement dateWidget = driver.findElement(By.className("startHoliday"));
		List<WebElement> columns = dateWidget.findElements(By.tagName("td"));
		System.out.println("No elements found:  " + columns.size());
		for (WebElement cell : columns) {
			if (cell.getText().equals(date)) {
				cell.click();
				break;
			}
		}
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
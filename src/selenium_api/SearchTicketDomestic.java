package selenium_api;

import static org.junit.Assert.assertTrue;

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

import vn.tripi.testing.utils.TimeUtils;

public class SearchTicketDomestic {
	WebDriver driver;

	// TÌM KIẾM/FILTER RỒI COUNT SỐ VÉ MỘT CHIỀU RỒI IN THÔNG TIN SỐ VÉ ( MỘT CHIỀU)
	/**
	 * Test
	 * 
	 * @Test @Parameters({ "flightfromairport1", "flighttoairport1",
	 *       "flightcheckindate1", "selectagencyoutbound1", "selectagencyintbound1",
	 *       "numadult", "children" }) public void
	 *       TC_01_SearchFilterPrintTicket_OneTrip(String flightfromairport1, String
	 *       flighttoairport1, String flightcheckindate1, String
	 *       selectagencyoutbound1, String selectagencyintbound1, int numadult, int
	 *       children) throws Exception {
	 * 
	 *       // Click on "vé máy bay" tab
	 *       driver.findElement(By.xpath("//div[contains(text(),'Vé máy
	 *       bay')]")).click();
	 * 
	 *       // Enter "From" station code eg.HAN WebElement eFromAirport =
	 *       driver.findElement(By.id("flight-from-airport-value"));
	 *       eFromAirport.sendKeys(flightfromairport1); Thread.sleep(2000);
	 *       eFromAirport.sendKeys(Keys.RETURN);
	 * 
	 *       // Enter "To" station code eg.SGN WebElement eToAirport =
	 *       driver.findElement(By.id("flight-to-airport-value"));
	 *       eToAirport.sendKeys(flighttoairport1); Thread.sleep(2000);
	 *       eToAirport.sendKeys(Keys.RETURN);
	 * 
	 *       // click on Departure date WebElement depaturedate =
	 *       driver.findElement(By.xpath("//input[@id='flight-checkin-date']"));
	 *       depaturedate.click();
	 * 
	 *       selectdatepicker(flightcheckindate1);
	 * 
	 *       WebElement clickpassenger =
	 *       driver.findElement(By.cssSelector(".ui-selectmenu-text"));
	 *       clickpassenger.click(); // Chọn số người lớn for (int i = 0; i <
	 *       numadult; i++) { driver.findElement(By.xpath("(//button[@class='btn
	 *       btn-white btn-ts-up'])[1]")).click(); } Thread.sleep(1000); // chọn số
	 *       trẻ em for (int i = 0; i < children; i++) {
	 *       driver.findElement(By.xpath("(//button[@class='btn btn-white
	 *       btn-ts-up'])[2]")).click(); } Thread.sleep(1000);
	 * 
	 *       // all fields filled in. Now click on search WebElement searchbutton =
	 *       driver .findElement(By.xpath("//button[@class='flight-search-button btn
	 *       btn-search']")); searchbutton.click(); Thread.sleep(3000); //Filter
	 *       theo hãng hàng không WebElement classticket =
	 *       driver.findElement(By.xpath("//*[contains(text(),'Vietnam
	 *       Airlines')][1]")); classticket.click(); Thread.sleep(3000); //Filter
	 *       theo hạng vé driver.findElement(By.cssSelector(".btn-default"));
	 *       WebElement classticket2 =
	 *       driver.findElement(By.xpath("//*[contains(text(),'Phổ Thông')]"));
	 *       classticket2.click();
	 * 
	 *       // Đếm số vé một chiều và in thông tin vé WebElement outBoundTicketsDiv
	 *       = driver.findElement(By.cssSelector("#outBoundTickets"));
	 *       List<WebElement> outboundTickets =
	 *       outBoundTicketsDiv.findElements(By.cssSelector(".ticket-info"));
	 *       System.out.println("Tổng số vé một chiều:" + outboundTickets.size());
	 *       for (WebElement ticket : outboundTickets) { WebElement logo =
	 *       ticket.findElement(By.cssSelector(".alogo")); String agency =
	 *       logo.getAttribute("alt"); List<WebElement> times =
	 *       ticket.findElements(By.className("departure-time")); String startDate =
	 *       times.get(0).findElement(By.className("ticket-time")).getText(); String
	 *       endDate =
	 *       times.get(1).findElement(By.className("ticket-time")).getText();
	 * 
	 *       WebElement ticketclasscode =
	 *       ticket.findElement(By.cssSelector(".ticket-detail-class-code"));
	 *       System.out.println(ticketclasscode.getText());
	 *       System.out.println(agency); System.out.println(startDate + "->" +
	 *       endDate);
	 *       System.out.println("--------------------------------------------------------");
	 *       Assert.assertEquals(classticket.getText(), "Vietnam Airlines"); boolean
	 *       classcommon = ticketclasscode.getText().contains("Phổ Thông");
	 *       Assert.assertEquals(classcommon, true); } driver.navigate().back(); }
	 * 
	 *       public void selectdatepicker(String date) { WebElement dateWidget =
	 *       driver.findElement(By.className("startHoliday")); List<WebElement>
	 *       columns = dateWidget.findElements(By.tagName("td")); for (WebElement
	 *       cell : columns) { if (cell.getText().equals(date)) { cell.click();
	 *       break; } } }
	 * 
	 */

	// SEARCH FLIGHT : SEARCH FLIGHT ROUND TRIP

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

		selectDate3(flightcheckindate2);

		WebElement returnDate = driver.findElement(By.xpath("//input[@id='flight-checkout-date']"));
		returnDate.click();

		WebElement dateWidget = driver
				.findElement(By.cssSelector("div.hthsf-item:nth-child(5) > div:nth-child(2) > div:nth-child(1)"));
		List<WebElement> columns = dateWidget.findElements(By.tagName("td"));
		for (WebElement cell : columns) {
			if (cell.getText().equals(flightcheckoutdate2)) {
				cell.click();
				break;
				
			}
		}
		Thread.sleep(2000);
		// all fields filled in. Now click on search
		WebElement searchbutton = driver
				.findElement(By.xpath("//button[@class='flight-search-button btn btn-search']"));
		searchbutton.click();

		Thread.sleep(4000);
		System.out.println("Kiem tra danh sach ve tra ve co du ve cua 3 hang VNA, VJ, JS");
		WebElement outBoundTicketsDiv = driver.findElement(By.cssSelector("#outBoundTickets"));
		List<WebElement> outboundTickets = outBoundTicketsDiv.findElements(By.cssSelector(".ticket-info"));
		int jetstarNum = 0;
		int vietjetNum = 0;
		int vnairline = 0;
		System.out.println("Danh sach ve chieu di:" + outboundTickets.size());
		for (WebElement ticket : outboundTickets) {
			WebElement logo = ticket.findElement(By.cssSelector(".alogo"));
			String agency = logo.getAttribute("alt");
			System.out.println(agency);
			if (agency.contains("Jetstar")) {
				jetstarNum++;
			} else if (agency.contains("VietJet")) {
				vietjetNum++;
			} else if (agency.contains("Vietnam")) {
				vnairline++;
			}
		}

		System.out.println("Tổng số vé của hãng Jetstar Airway :" + jetstarNum);
		System.out.println("Tổng số vé của hãng Vietjet :" + vietjetNum);
		System.out.println("Tổng số vé của hãng VNAirline :" + vnairline);

		int numOfTickets = outboundTickets.size();

		assertTrue(numOfTickets > 0);
		assertTrue(jetstarNum > 0);

		assertTrue(vnairline > 0);
		assertTrue(vietjetNum > 0);

		Thread.sleep(2000);

		WebElement inBoundTicketstab = driver.findElement(By.cssSelector("div.menu-item:nth-child(3)"));
		inBoundTicketstab.click();

		Thread.sleep(3000);
		// kiểm tra danh sách vé chiều về có đủ vé của 3 hãng VJ, JS, VNA hay không?
		WebElement inBoundTicketsDiv = driver.findElement(By.cssSelector("#inboundTickets.tickets"));
		List<WebElement> inboundTickets = inBoundTicketsDiv.findElements(By.cssSelector(".ticket-info"));

		int jetstarNumib = 0;
		int vietjetNumib = 0;
		int vnairlineib = 0;
		System.out.println("Danh sách vé chiều về:");
		for (WebElement ticketib : inboundTickets) {

			WebElement logoib = ticketib.findElement(By.cssSelector(".alogo"));
			String agencyib = logoib.getAttribute("alt");
			System.out.println(agencyib);
			if (agencyib.contains("Jetstar")) {
				jetstarNumib++;
			} else if (agencyib.contains("VietJet")) {
				vietjetNumib++;
			} else if (agencyib.contains("Vietnam")) {
				vnairlineib++;
			}
		}

		System.out.println("Tổng số vé của hãng Jetstar Airway :" + jetstarNumib);
		System.out.println("Tổng số vé của hãng Vietjet :" + vietjetNumib);
		System.out.println("Tổng số vé của hãng VNAirline :" + vnairlineib);

		int numOfTicketsib = outboundTickets.size();

		assertTrue(numOfTicketsib > 0);
		assertTrue(jetstarNumib > 0);
		assertTrue(vietjetNumib > 0);
		assertTrue(vnairlineib > 0);

	}
	public void selectDate3(String date) {
		WebElement dateWidget = driver.findElement(By.className("startHoliday"));
		List<WebElement> columns = dateWidget.findElements(By.tagName("td"));
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
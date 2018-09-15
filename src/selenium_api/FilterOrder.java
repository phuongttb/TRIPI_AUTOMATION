package selenium_api;

import static org.testng.AssertJUnit.assertTrue;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import vn.tripi.testing.commons.BaseClass;

public class FilterOrder extends BaseClass {

	@Test()
	@Parameters({ "username", "password", "orderid" })
	public void TC_01_Filterorder(String username, String password, String orderid) throws InterruptedException {

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
		WebElement bookinglisttable = driver
				.findElement(By.xpath("//div[@data-ng-controller='bookingManageController']"));
		List<WebElement> bookinglist = bookinglisttable.findElements(By.cssSelector(".list-booking"));
		int numOfBooking = bookinglist.size();

		assertTrue(numOfBooking > 0);
		System.out.println("Tổng số booking:" + bookinglist.size());
	}

	@Test()
	@Parameters({ "booking_code" })
	public void TC_02_FilterBookingCode(String booking_code) throws InterruptedException {

		driver.navigate().refresh();
		driver.findElement(By.cssSelector("a[ui-sref='booker.bookingManage']")).click();
		Thread.sleep(2000);
		driver.findElement(By.id("btn-filter")).click();
		Thread.sleep(2000);

		driver.findElement(By.xpath("//input[@ng-model='filters.bookingCode']")).sendKeys(booking_code);
		Thread.sleep(5000);
		driver.findElement(By.cssSelector(".btn-confirm")).click();
		Thread.sleep(4000);
		WebElement bookinglisttable = driver
				.findElement(By.xpath("//div[@data-ng-controller='bookingManageController']"));
		List<WebElement> bookinglist = bookinglisttable.findElements(By.cssSelector(".list-booking"));
		int numOfBooking = bookinglist.size();

		assertTrue(numOfBooking > 0);
		System.out.println("Tổng số booking:" + bookinglist.size());
	}

	@Test()

	public void TC_03_FilterStatusSuccess() throws InterruptedException {

		driver.navigate().refresh();
		driver.findElement(By.cssSelector("a[ui-sref='booker.bookingManage']")).click();
		Thread.sleep(2000);
		driver.findElement(By.id("btn-filter")).click();
		Thread.sleep(2000);

		driver.findElement(By.xpath("//span[normalize-space()='Thành công']")).click();
		Thread.sleep(5000);
		driver.findElement(By.cssSelector(".btn-confirm")).click();
		Thread.sleep(4000);
		WebElement bookinglisttable = driver
				.findElement(By.xpath("//div[@data-ng-controller='bookingManageController']"));
		List<WebElement> bookinglist = bookinglisttable.findElements(By.cssSelector(".list-booking"));
		int numOfBooking = bookinglist.size();

		assertTrue(numOfBooking > 0);
		System.out.println("Tổng số booking:" + bookinglist.size());
		Thread.sleep(2000);
	}

	@Test()
	public void TC_04_FilterStatusHolding() throws InterruptedException {

		driver.navigate().refresh();
		driver.findElement(By.cssSelector("a[ui-sref='booker.bookingManage']")).click();
		Thread.sleep(2000);
		driver.findElement(By.id("btn-filter")).click();
		Thread.sleep(2000);

		driver.findElement(By.xpath("//span[normalize-space()='Đang giữ']")).click();
		Thread.sleep(5000);
		driver.findElement(By.cssSelector(".btn-confirm")).click();
		Thread.sleep(4000);
		WebElement bookinglisttable = driver
				.findElement(By.xpath("//div[@data-ng-controller='bookingManageController']"));
		List<WebElement> bookinglist = bookinglisttable.findElements(By.cssSelector(".list-booking"));
		int numOfBooking = bookinglist.size();

		assertTrue(numOfBooking > 0);
		System.out.println("Tổng số booking:" + bookinglist.size());
		Thread.sleep(2000);
	}

	@Test()
	public void TC_05_FilterStatusAll() throws InterruptedException {

		driver.navigate().refresh();
		driver.findElement(By.cssSelector("a[ui-sref='booker.bookingManage']")).click();
		Thread.sleep(2000);
		driver.findElement(By.id("btn-filter")).click();
		Thread.sleep(2000);

		driver.findElement(By.xpath("//span[normalize-space()='Tất cả']")).click();
		Thread.sleep(5000);
		driver.findElement(By.cssSelector(".btn-confirm")).click();
		Thread.sleep(4000);
		WebElement bookinglisttable = driver
				.findElement(By.xpath("//div[@data-ng-controller='bookingManageController']"));
		List<WebElement> bookinglist = bookinglisttable.findElements(By.cssSelector(".list-booking"));
		int numOfBooking = bookinglist.size();

		assertTrue(numOfBooking > 0);
		System.out.println("Tổng số booking:" + bookinglist.size());
		Thread.sleep(2000);
	}
	@Parameters({ "check_in" ,"check_out" })
	@Test()
	public void TC_06_FilterDate(String check_in, String check_out) throws InterruptedException {

		driver.navigate().refresh();
		driver.findElement(By.cssSelector("a[ui-sref='booker.bookingManage']")).click();
		Thread.sleep(2000);
		driver.findElement(By.id("btn-filter")).click();
		Thread.sleep(2000);

		// chọn ngày check in
		driver.findElement(By.id("profit-from-date")).click();
		WebElement fromDate = driver.findElement(By.xpath("(//div[normalize-space()='" + check_in + "'])"));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", fromDate);
		Thread.sleep(1000);
		// Chon ngay check-out date
//		driver.findElement(By.id("profit-from-date")).click();
		WebElement toDate = driver.findElement(By.xpath("(//div[normalize-space()='" + check_out + "'])"));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", toDate);
		Thread.sleep(1000);
		
		driver.findElement(By.cssSelector(".btn-confirm")).click();
		Thread.sleep(4000);
		WebElement bookinglisttable = driver
				.findElement(By.xpath("//div[@data-ng-controller='bookingManageController']"));
		List<WebElement> bookinglist = bookinglisttable.findElements(By.cssSelector(".list-booking"));
		int numOfBooking = bookinglist.size();

		assertTrue(numOfBooking > 0);
		System.out.println("Tổng số booking:" + bookinglist.size());
		Thread.sleep(2000);
	}
}

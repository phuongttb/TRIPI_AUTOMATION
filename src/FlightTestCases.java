package vn.tripi.testing.searching;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import vn.tripi.testing.commons.BaseClass;
import vn.tripi.testing.utils.TimeUtils;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import java.util.Calendar;

public class FlightTestCases extends BaseClass {
	// ------------------------------------------- Method 1
	@Parameters({ "from_airport", "to_airport", "from_date_add", "to_date_add", "adult_num", "child_num",
			"infant_num" })
	@Test
	public void SearchFlight(String from_airport, String to_airport, int from_date_add, int to_date_add, int adult_num,
			int child_num, int infant_num) {

		System.out.println("-----Thuc hien viec tim kiem ve may bay tu " + from_airport + " den " + to_airport
				+ ".Tong so hanh khach: " + (adult_num + child_num + infant_num));
		// Click vao button khu hoi
		WebElement returnButton = driver.findElement(By.cssSelector("span.fw-item:nth-child(2)"));
		returnButton.click();

		// Nhap departure station
		WebElement departureStation = driver.findElement(By.cssSelector("#flight-from-airport-value"));
		departureStation.sendKeys(from_airport);
		TimeUtils.sleep(2);
		departureStation.sendKeys(Keys.RETURN);

		// Nhap arrival station
		WebElement arrivalStation = driver.findElement(By.cssSelector("#flight-to-airport-value"));
		arrivalStation.sendKeys(to_airport);
		TimeUtils.sleep(2);
		arrivalStation.sendKeys(Keys.RETURN);

		Calendar now = Calendar.getInstance();
		// System.out.println("Ngay hien tai "+now.get(Calendar.DATE) + "-"+
		// (now.get(Calendar.MONTH) + 1) + "-" + now.get(Calendar.YEAR));
		now.add(Calendar.DATE, from_date_add);
		int from_date = now.get(Calendar.DATE);
		// System.out.println("Ngay sau ngay hien tai "+from_date_add+" ngay la ngay
		// "+from_date);
		// Nhap departure date
		WebElement checkinDate = driver.findElement(By.cssSelector("#flight-checkin-date"));
		checkinDate.click();
		WebElement tableCheckinDate = checkinDate.findElement(By.xpath(".."));
		List<WebElement> days = tableCheckinDate.findElements(By.tagName("td"));
		for (WebElement day : days) {
			int datef = Integer.parseInt(day.getText());
			if (from_date == datef) {
				day.click();
				break;
			}
		}
		System.out.println("Ngay di: " + from_date + "/" + (now.get(Calendar.MONTH) + 1));

		// Nhap ngay ve
		Calendar nowup = Calendar.getInstance();
		// System.out.println("Ngay hien tai "+nowup.get(Calendar.DATE) + "-"+
		// (nowup.get(Calendar.MONTH) + 1) + "-" + nowup.get(Calendar.YEAR));
		nowup.add(Calendar.DATE, to_date_add);
		int to_date = nowup.get(Calendar.DATE);
		// System.out.println("Ngay sau ngay hien tai "+to_date_add+"ngay la ngay
		// "+to_date);
		WebElement checkoutDate = driver.findElement(By.cssSelector("#flight-checkout-date"));
		checkoutDate.click();
		WebElement tableCheckoutDate = checkoutDate.findElement(By.xpath(".."));
		List<WebElement> checkoutDays = tableCheckoutDate.findElements(By.tagName("td"));
		for (WebElement day : checkoutDays) {
			int date = Integer.parseInt(day.getText());
			if (to_date == date) {
				day.click();
				break;
			}
		}
		System.out.println("Ngay ve: " + to_date + "/" + (nowup.get(Calendar.MONTH) + 1) + "-----------");
		/// Change passengers
		WebElement passenger = driver.findElement(By.cssSelector(".ui-selectmenu-text"));
		passenger.click();

		WebElement adultNum = driver.findElement(By.cssSelector(
				".centered > li:nth-child(1) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > span:nth-child(5) > button:nth-child(1)"));
		for (int i = 1; i < adult_num; i++) {
			adultNum.click();
		}
		TimeUtils.sleep(2);
		WebElement childNum = driver
				.findElement(By.cssSelector(".flights-touchspin-children > span:nth-child(5) > button:nth-child(1)"));
		for (int i = 0; i < child_num; i++) {
			childNum.click();
		}
		TimeUtils.sleep(2);
		WebElement infantNum = driver
				.findElement(By.cssSelector(".flights-touchspin-infants > span:nth-child(5) > button:nth-child(1)"));
		for (int i = 0; i < infant_num; i++) {
			infantNum.click();
		}
		TimeUtils.sleep(2);

		// Click tim kiem
		WebElement searchButton = driver.findElement(By.cssSelector(".flight-search-button"));
		searchButton.click();
		TimeUtils.sleep(15);
	}

	// ------------------------------------------- Method 2
	@Parameters({ "from_airport_checkairlines", "to_airport_checkairlines", "from_date_checkairlines_add",
			"to_date_checkairlines_add", "adult_num_checkairlines", "child_num_checkairlines",
			"infant_num_checkairlines" })
	@Test
	public void CheckEnoughThreeAirlines(String from_airport_checkairlines, String to_airport_checkairlines,
			int from_date_checkairlines_add, int to_date_checkairlines_add, int adult_num_checkairlines,
			int child_num_checkairlines, int infant_num_checkairlines) {
		SearchFlight(from_airport_checkairlines, to_airport_checkairlines, from_date_checkairlines_add,
				to_date_checkairlines_add, adult_num_checkairlines, child_num_checkairlines, infant_num_checkairlines);
		// Check ket qua
		// Check outBoundTickets
		// kiểm tra danh sách vé chiều đi có đủ vé của 3 hãng VJ, JS, VNA hay không?
		System.out.println("Kiem tra danh sach ve tra ve co du ve cua 3 hang VNA, VJ, JS hay khong");
		WebElement outBoundTicketsDiv = driver.findElement(By.cssSelector("#outBoundTickets"));
		List<WebElement> outboundTickets = outBoundTicketsDiv.findElements(By.cssSelector(".ticket-info"));
		int jetstarNum = 0;
		int vietjetNum = 0;
		int vnairline = 0;
		System.out.println("Danh sach ve chieu di:");
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
		int numOfTickets = outboundTickets.size();

		// Check xem so luong xem > 0 ?
		// System.out.println("Vietject number: " + vietjetNum);
		Assert.assertEquals(numOfTickets > 0, true);
		Assert.assertEquals(vietjetNum > 0, true);
		Assert.assertEquals(vnairline > 0, true);
		Assert.assertEquals(jetstarNum > 0, true);
		TimeUtils.sleep(5);

		// Check inBoundTickets
		// click vào tab chiều về
		WebElement inBoundTicketstab = driver.findElement(By.cssSelector("div.menu-item:nth-child(3)"));
		inBoundTicketstab.click();

		TimeUtils.sleep(5);
		// kiểm tra danh sách vé chiều về có đủ vé của 3 hãng VJ, JS, VNA hay không?
		WebElement inBoundTicketsDiv = driver.findElement(By.cssSelector("#inboundTickets.tickets"));
		List<WebElement> inboundTickets = inBoundTicketsDiv.findElements(By.cssSelector(".ticket-info"));

		int jetstarNumib = 0;
		int vietjetNumib = 0;
		int vnairlineib = 0;
		System.out.println("Danh sach ve chieu ve:");
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

		int numOfTicketsib = inboundTickets.size();

		// Check xem so luong xem > 0 ?
		Assert.assertEquals(numOfTicketsib > 0, true);
		Assert.assertEquals(vietjetNumib > 0, true);
		Assert.assertEquals(vnairlineib > 0, true);
		Assert.assertEquals(jetstarNumib > 0, true);
		System.out.println("==> PASSED");
		TimeUtils.sleep(5);
	}

	// ------------------------------------------- Method 3
	@Parameters({ "from_airport_book", "to_airport_book", "from_date_book_add", "to_date_book_add", "adult_num_book",
			"child_num_book", "infant_num_book" })
	@Test
	public void BookFlight(String from_airport_book, String to_airport_book, int from_date_book_add,
			int to_date_book_add, int adult_num_book, int child_num_book, int infant_num_book) {
		SearchFlight(from_airport_book, to_airport_book, from_date_book_add, to_date_book_add, adult_num_book,
				child_num_book, infant_num_book);
		// Chon ve chieu di
		System.out.println("Kiem tra book ve VJ va JS co den duoc cong thanh toan hay khong?");
		WebElement outBoundTicketsDiv = driver.findElement(By.cssSelector("#outBoundTickets"));
		List<WebElement> outboundTickets = outBoundTicketsDiv.findElements(By.cssSelector(".ticket-info"));
		System.out.println("Chon ve chieu di:");
		for (WebElement ticket : outboundTickets) {
			WebElement logo = ticket.findElement(By.cssSelector(".alogo"));
			String agency = logo.getAttribute("alt");

			if (agency.contains("Jetstar")) {
				WebElement selectBtnob = ticket.findElement(By.cssSelector(".flight-select-single-ticket"));
				selectBtnob.click();
				TimeUtils.sleep(2);
				System.out.println(agency);
				break;
			}
		}
		// Chon ve chieu ve
		WebElement inBoundTicketstab = driver.findElement(By.cssSelector("div.menu-item:nth-child(3)"));
		inBoundTicketstab.click();
		TimeUtils.sleep(5);
		WebElement inBoundTicketsDiv = driver.findElement(By.cssSelector("#inboundTickets.tickets"));
		List<WebElement> inboundTickets = inBoundTicketsDiv.findElements(By.cssSelector(".ticket-info"));

		System.out.println("Chon ve chieu ve:");
		for (WebElement ticketib : inboundTickets) {

			WebElement logoib = ticketib.findElement(By.cssSelector(".alogo"));
			String agencyib = logoib.getAttribute("alt");

			if (agencyib.contains("VietJet")) {
				WebElement selectBtnib = ticketib.findElement(By.cssSelector(".flight-select-return-ticket"));
				selectBtnib.click();
				TimeUtils.sleep(2);
				System.out.println(agencyib);
				break;

			}
		}
		// Xac nhan chon ve
		WebElement selectedFlightDiv = driver.findElement(By.cssSelector("#selected-flight"));
		WebElement confirmBookTicketbtn = selectedFlightDiv
				.findElement(By.cssSelector(".flight-search-booking-ticket"));
		confirmBookTicketbtn.click();
		TimeUtils.sleep(5);

		// Nhap tt hanh khach thu nhat
		WebElement firstGuestDiv = driver.findElement(By.cssSelector("#adult-0"));
		// Nhap lastname
		WebElement lastNametext = firstGuestDiv.findElement(By.cssSelector(".form-control.last-name"));
		lastNametext.clear();
		lastNametext.sendKeys("Nguyen");
		// Nhap firstname
		WebElement firstNametext = firstGuestDiv.findElement(By.cssSelector(".form-control.first-name"));
		firstNametext.clear();
		firstNametext.sendKeys("Van An");
		// Chon gioi tinh
		WebElement genderOfFirstGuestDiv = firstGuestDiv.findElement(By.cssSelector(".form-control.gender"));
		Select dropdown = new Select(genderOfFirstGuestDiv);
		dropdown.selectByVisibleText("Nam");
		TimeUtils.sleep(3);

		// Nhap tt hanh khach thu hai

		WebElement secondGuestDiv = driver.findElement(By.cssSelector("#adult-1"));
		// Nhap lastname
		WebElement lastNametext2 = secondGuestDiv.findElement(By.cssSelector(".form-control.last-name"));
		lastNametext2.clear();
		lastNametext2.sendKeys("Nguyen");
		// Nhap firstname
		WebElement firstNametext2 = secondGuestDiv.findElement(By.cssSelector(".form-control.first-name"));
		firstNametext2.clear();
		firstNametext2.sendKeys("Hong Ha");
		// Chon gioi tinh
		WebElement genderOfFirstGuestDiv2 = secondGuestDiv.findElement(By.cssSelector(".form-control.gender"));
		Select dropdown2 = new Select(genderOfFirstGuestDiv2);
		dropdown2.selectByVisibleText("Nữ");
		TimeUtils.sleep(5);

		// Chon mua them hanh ly
		// The chon hanh ly
		WebElement baggagesDiv = driver.findElement(By.cssSelector(".row"));
		// chon hanh ly hanh khach thu nhat - chon goi 15kg
		WebElement baggagesNum = baggagesDiv.findElement(By.cssSelector(".form-control.baggage"));
		Select dropdown3 = new Select(baggagesNum);
		dropdown3.selectByVisibleText("Gói (Bag) 15 kg - 160.000đ");
		TimeUtils.sleep(5);

		// Nhap thong tin lien he
		// tim the thong tin lien he
		WebElement contactDiv = driver.findElement(By.cssSelector("#contact-info"));
		// Chon gioi tinh
		WebElement contactGender = contactDiv.findElement(By.cssSelector("#ticket-booking-select-title"));
		Select dropdown4 = new Select(contactGender);
		dropdown4.selectByVisibleText("Bà");

		// Nhap Ho
		WebElement lastNametext3 = contactDiv.findElement(By.cssSelector(".form-control.last-name"));
		lastNametext3.clear();
		lastNametext3.sendKeys("Nguyen");
		// Nhap Ten
		WebElement firstNametext3 = contactDiv.findElement(By.cssSelector(".form-control.first-name"));
		firstNametext3.clear();
		firstNametext3.sendKeys("Van An");
		// Nhap email
		WebElement emailContact = contactDiv.findElement(By.cssSelector(".form-control.email"));
		emailContact.clear();
		emailContact.sendKeys("test@gmail.com");
		// Nhap so dien thoai
		WebElement phoneContact = contactDiv.findElement(By.cssSelector(".form-control.phone1"));
		phoneContact.clear();
		phoneContact.sendKeys("0986111111");
		// Chon phuong thuc thanh toan
		// Tim the pttt
		TimeUtils.sleep(2);
		WebElement paymentMethodDiv = driver.findElement(By.cssSelector(".row"));
		// WebElement paymentVisa =
		// paymentMethodDiv.findElement(By.cssSelector("#payment-method-1"));
		WebElement paymentVisa = paymentMethodDiv.findElement(By.cssSelector("#payment-method-1"));
		paymentVisa.click();
		// click nut thanh toan
		WebElement paymentButton = driver.findElement(By.cssSelector(".flight-initiate-checkout"));
		paymentButton.click();
		TimeUtils.sleep(5);

		// an nut Xac nhan dong y thanh toan
		WebElement confirmBtnDiv = driver.findElement(By.cssSelector(".modal-footer"));
		WebElement confirmBtn = confirmBtnDiv.findElement(By.cssSelector(".modal-footer > button:nth-child(2)"));
		confirmBtn.click();
		TimeUtils.sleep(40);
		// kiem tra cong thanh toan da chon
		WebElement gatewayDiv = driver.findElement(By.cssSelector("#gateway"));
		// kiem tra radio button duoc checked
		WebElement radioBtnDiv = gatewayDiv.findElement(By.tagName("td"));
		WebElement radioBtn = radioBtnDiv.findElement(By.cssSelector(".copy_radio"));
		Assert.assertEquals(radioBtn.isSelected(), true);
		WebElement methodLabel = radioBtnDiv.findElement(By.cssSelector(".copy"));
		Assert.assertEquals(methodLabel.getText(), "Thẻ quốc tế");
		System.out.println("==> PASSED");
		TimeUtils.sleep(5);
	}

	// ------------------------------------------- Method 4
	@Parameters({ "from_airport_noflight", "to_airport_noflight", "from_date_noflight_add", "to_date_noflight_add",
			"adult_num_noflight", "child_num_noflight", "infant_num_noflight" })
	@Test
	public void CheckHaveNoFlight(String from_airport_noflight, String to_airport_noflight, int from_date_noflight_add,
			int to_date_noflight_add, int adult_num_noflight, int child_num_noflight, int infant_num_noflight) {
		SearchFlight(from_airport_noflight, to_airport_noflight, from_date_noflight_add, to_date_noflight_add,
				adult_num_noflight, child_num_noflight, infant_num_noflight);
		// Check ket qua
		// Check outBoundTickets
		// Kiem tra ket qua doi voi chang bay khong ton tai
		System.out.println("Kiem tra ket qua doi voi chang bay khong ton tai");
		WebElement outBoundTicketsDiv = driver.findElement(By.cssSelector("#outBoundTickets"));
		WebElement outBoundTickets = outBoundTicketsDiv.findElement(By.cssSelector(".note-1"));
		String notice = outBoundTickets.getText();
		String a = new String("Không có chuyến bay");
		System.out.println("Chieu di hien thi thong bao: " + a);
		Assert.assertEquals(notice.equals(a), true);

		// Check inBoundTickets
		// click vào tab chiều về
		WebElement inBoundTicketstab = driver.findElement(By.cssSelector("div.menu-item:nth-child(3)"));
		inBoundTicketstab.click();
		TimeUtils.sleep(5);
		WebElement inBoundTicketsDiv = driver.findElement(By.cssSelector("#inboundTickets.tickets"));
		WebElement inBoundTickets = inBoundTicketsDiv.findElement(By.cssSelector(".note-1"));
		String noticeIb = inBoundTickets.getText();
		String b = new String("Không có chuyến bay");
		System.out.println("Chieu di hien thi thong bao: " + b);
		Assert.assertEquals(notice.equals(b), true);
		System.out.println("==> PASSED");

	}

	// ------------------------------------------- Method 5
	@Parameters({ "from_airport_flightinfo", "to_airport_flightinfo", "from_date_flightinfo_add",
			"to_date_flightinfo_add", "adult_num_flightinfo", "child_num_flightinfo", "infant_num_flightinfo" })
	@Test
	public void CheckFlightInformation(String from_airport_flightinfo, String to_airport_flightinfo,
			int from_date_flightinfo_add, int to_date_flightinfo_add, int adult_num_flightinfo,
			int child_num_flightinfo, int infant_num_flightinfo) {
		SearchFlight(from_airport_flightinfo, to_airport_flightinfo, from_date_flightinfo_add, to_date_flightinfo_add,
				adult_num_flightinfo, child_num_flightinfo, infant_num_flightinfo);
		// Chon ve chieu di
		System.out.println(
				"Kiem tra thong tin chang bay cua ve co chinh xac hay khong (kiem tra tren 10 ve cua trang ket qua dau tien)");
		WebElement outBoundTicketsDiv = driver.findElement(By.cssSelector("#outBoundTickets"));
		List<WebElement> outboundTickets = outBoundTicketsDiv.findElements(By.cssSelector(".ticket-info"));
		System.out.println("Chang bay chieu di xuat phat tu: ");
		for (WebElement ticket : outboundTickets) {
			List<WebElement> text_from = ticket.findElements(By.cssSelector(".ticket-info-text"));

			String a = (text_from.get(0)).getText();
			System.out.println("(" + a);
			Assert.assertEquals(from_airport_flightinfo.equals(a), true);

			System.out.println(" ---> ");

			String b = (text_from.get(1)).getText();
			System.out.println(b + ")");
			Assert.assertEquals(to_airport_flightinfo.equals(b), true);
		}

		// Chon ve chieu ve
		WebElement inBoundTicketstab = driver.findElement(By.cssSelector("div.menu-item:nth-child(3)"));
		inBoundTicketstab.click();
		TimeUtils.sleep(5);
		WebElement inBoundTicketsDiv = driver.findElement(By.cssSelector("#inboundTickets.tickets"));
		List<WebElement> inboundTickets = inBoundTicketsDiv.findElements(By.cssSelector(".ticket-info"));

		System.out.println("Chang bay chieu ve xuat phat tu: ");
		for (WebElement ticketib : inboundTickets) {
			List<WebElement> text_to = ticketib.findElements(By.cssSelector(".ticket-info-text"));

			String c = (text_to.get(0)).getText();
			System.out.println("(" + c);
			Assert.assertEquals(to_airport_flightinfo.equals(c), true);

			System.out.println(" ---> ");

			String d = (text_to.get(1)).getText();
			System.out.println(d + ")");
			Assert.assertEquals(from_airport_flightinfo.equals(d), true);
		}
		System.out.println("===> PASSED - Thong tin chang bay chinh xac");
		// Xac nhan chon ve
		// WebElement selectedFlightDiv =
		// driver.findElement(By.cssSelector("#selected-flight"));
		// WebElement confirmBookTicketbtn =
		// selectedFlightDiv.findElement(By.cssSelector(".flight-search-booking-ticket"));
		// confirmBookTicketbtn.click();
		// TimeUtils.sleep(5);
	}

	// ------------------------------------------- Method 6
	@Parameters({ "from_airport_allInfor", "to_airport_allInfor", "from_date_allInfor_add", "to_date_allInfor_add",
			"adult_num_allInfor", "child_num_allInfor", "infant_num_allInfor", "airlines_from_allInfor",
			"airlines_to_allInfor" })
	@Test
	// them bien hang bay se chon ve de book
	public void CheckAllInfor(String from_airport_allInfor, String to_airport_allInfor, int from_date_allInfor_add,
			int to_date_allInfor_add, int adult_num_allInfor, int child_num_allInfor, int infant_num_allInfor,
			String airlines_from_allInfor, String airlines_to_allInfor) {
		SearchFlight(from_airport_allInfor, to_airport_allInfor, from_date_allInfor_add, to_date_allInfor_add,
				adult_num_allInfor, child_num_allInfor, infant_num_allInfor);
		// Chon ve chieu di
		System.out.println(
				"Kiem tra thong tin ve may bay co chinh xac hay khong: Chang bay - Gio di/Gio den - Ngay bay - Gia - So nguoi - Hang bay sau khi chon ve");
		WebElement outBoundTicketsDiv = driver.findElement(By.cssSelector("#outBoundTickets"));
		List<WebElement> outboundTickets = outBoundTicketsDiv.findElements(By.cssSelector(".ticket-info"));
		System.out.println("Chon ve chieu di:");
		String ob_from = new String();
		String ob_to = new String();
		String ob_timefrom = new String();
		String ob_timeto = new String();
		String ob_price = new String();

		for (WebElement ticket : outboundTickets) {
			WebElement logo = ticket.findElement(By.cssSelector(".alogo"));
			String agency = logo.getAttribute("alt");

			if (agency.contains(airlines_from_allInfor)) {
				// truoc khi chon ve thi lay thong tin luu vao bien de so sanh
				// thong tin cua ve bao gom chang bay, gio di/gio den, ngay bay, gia, hang bay
				// thong tin chang bay

				List<WebElement> route = ticket.findElements(By.cssSelector(".ticket-info-text"));
				ob_from = (route.get(0)).getText();
				ob_to = (route.get(1)).getText();

				// thong tin gio di/gio den
				List<WebElement> time = ticket.findElements(By.cssSelector(".ticket-time"));
				ob_timefrom = (time.get(0)).getText();
				ob_timeto = (time.get(1)).getText();

				// thong tin ngay bay
				// ve khong co tt ngay bay

				// thong tin gia
				WebElement price = ticket.findElement(By.cssSelector(".ticket-price"));
				ob_price = price.getText();

				// thong tin hang bay
				// khong can lay vi lay o bien airlines_ob_allInfor

				WebElement selectBtnob = ticket.findElement(By.cssSelector(".flight-select-single-ticket"));
				selectBtnob.click();
				TimeUtils.sleep(2);
				System.out.println(agency);
				System.out.println(ob_from + "-->" + ob_to);
				System.out.println(ob_timefrom + "-->" + ob_timeto);
				System.out.println("Gia ve : " + ob_price);
				break;
			}
		}

		// Chon ve chieu ve
		WebElement inBoundTicketstab = driver.findElement(By.cssSelector("div.menu-item:nth-child(3)"));
		inBoundTicketstab.click();
		TimeUtils.sleep(5);
		WebElement inBoundTicketsDiv = driver.findElement(By.cssSelector("#inboundTickets.tickets"));
		List<WebElement> inboundTickets = inBoundTicketsDiv.findElements(By.cssSelector(".ticket-info"));

		System.out.println("Chon ve chieu ve:");
		String ib_from = new String();
		String ib_to = new String();
		String ib_timefrom = new String();
		String ib_timeto = new String();
		String ib_price = new String();
		for (WebElement ticketib : inboundTickets) {
			WebElement logoib = ticketib.findElement(By.cssSelector(".alogo"));
			String agencyib = logoib.getAttribute("alt");

			if (agencyib.contains(airlines_to_allInfor)) {
				// truoc khi chon ve thi lay thong tin luu vao bien de so sanh
				// thong tin cua ve bao gom chang bay, gio di/gio den, ngay bay, gia, hang bay
				// thong tin chang bay
				List<WebElement> route_ib = ticketib.findElements(By.cssSelector(".ticket-info-text"));
				ib_from = (route_ib.get(0)).getText();
				ib_to = (route_ib.get(1)).getText();

				// thong tin gio di/gio den
				List<WebElement> time_ib = ticketib.findElements(By.cssSelector(".ticket-time"));
				ib_timefrom = (time_ib.get(0)).getText();
				ib_timeto = (time_ib.get(1)).getText();

				// thong tin ngay bay
				// ve khong co tt ngay bay

				// thong tin gia
				WebElement price = ticketib.findElement(By.cssSelector(".ticket-price"));
				ib_price = price.getText();

				// thong tin hang bay
				// khong can lay vi lay o bien airlines_ob_allInfor
				WebElement selectBtnib = ticketib.findElement(By.cssSelector(".flight-select-return-ticket"));
				selectBtnib.click();
				TimeUtils.sleep(2);
				System.out.println(agencyib);
				System.out.println(ib_from + "-->" + ib_to);
				System.out.println(ib_timefrom + "-->" + ib_timeto);
				System.out.println("Gia ve : " + ib_price);
				break;

			}
		}

		// Chon xong ve thi kiem tra thong tin ve
		// xác định và lấy các thông tin của vé ở khung thông tin chuyến bay : giờ
		// đi/giờ đến, chặng bay, hãng bay, ngày bay, tính tiền ở khung thông tin chuyến
		// bay
		// get ve xong so sanh luon

		// so sanh ob_from , ob_to , ob_timefrom ,ob_timeto, String ob_price

		WebElement SelectedFlightInfordiv = driver.findElement(By.cssSelector("#selected-flight"));

		// lay theo the note (chang bay, hang bay) ob_from , ob_to ,
		// airlines_from_allInfor
		List<WebElement> note_class = SelectedFlightInfordiv.findElements(By.cssSelector(".note"));
		Assert.assertEquals((note_class.get(0).getText()).equals(ob_from), true);
		Assert.assertEquals((note_class.get(1).getText()).equals(ob_to), true);
		Assert.assertEquals((note_class.get(2).getText()).equals(airlines_from_allInfor), true);

		Assert.assertEquals((note_class.get(3).getText()).equals(ib_from), true);
		Assert.assertEquals((note_class.get(4).getText()).equals(ib_to), true);
		Assert.assertEquals((note_class.get(5).getText()).equals(airlines_to_allInfor), true);

		// lay theo the title (so sanh gio di/gio den + ngay bay cua ve chieu di va cua
		// ve chieu ve)
		// so sanh gio di/gio den cua ve di
		List<WebElement> title_class = SelectedFlightInfordiv.findElements(By.cssSelector(".title"));
		Assert.assertEquals((title_class.get(0).getText()).equals(ob_timefrom), true);
		Assert.assertEquals((title_class.get(1).getText()).equals(ob_timeto), true);

		// so sanh ngay bay cua ve chieu di
		// lay ngay bay va tach chuoi, doi sang int
		Calendar now = Calendar.getInstance();
		now.add(Calendar.DATE, from_date_allInfor_add);

		int a = now.get(Calendar.DATE);
		int b = now.get(Calendar.MONTH) + 1;
		int c = now.get(Calendar.YEAR);

		String from_date_actual = title_class.get(2).getText();
		String[] from_date_array = from_date_actual.split("/");
		int day = Integer.parseInt(from_date_array[0]);
		int month = Integer.parseInt(from_date_array[1]);
		int year = Integer.parseInt(from_date_array[2]);
		// so sanh
		Assert.assertEquals(day == a, true);
		Assert.assertEquals(month == b, true);
		Assert.assertEquals(year == c, true);

		// so sanh gio di/gio den cua ve ve
		Assert.assertEquals((title_class.get(3).getText()).equals(ib_timefrom), true);
		Assert.assertEquals((title_class.get(4).getText()).equals(ib_timeto), true);

		// so sanh ngay bay cua ve chieu ve
		// lay ngay bay va tach chuoi, doi sang int
		Calendar now2 = Calendar.getInstance();
		now2.add(Calendar.DATE, to_date_allInfor_add);

		int d = now2.get(Calendar.DATE);
		int e = now2.get(Calendar.MONTH) + 1;
		int f = now2.get(Calendar.YEAR);

		String return_date_actual = title_class.get(5).getText();
		String[] return_date_array = return_date_actual.split("/");
		int day2 = Integer.parseInt(return_date_array[0]);
		int month2 = Integer.parseInt(return_date_array[1]);
		int year2 = Integer.parseInt(return_date_array[2]);
		// so sanh
		Assert.assertEquals(day2 == d, true);
		Assert.assertEquals(month2 == e, true);
		Assert.assertEquals(year2 == f, true);

		// lay theo the press (don gia theo ve nguoi lon, tre em, em be)
		List<WebElement> press_class = SelectedFlightInfordiv.findElements(By.cssSelector(".press"));
		int Money = 0;
		String text = press_class.get(0).getText();
		text = text.replaceAll("\\D+", "");
		Money = Integer.parseInt(text);

		// so sanh voi ve chieu di va chieu ve, tuy bien theo so hanh khach
		// nguoi lon = (press_class.get(0) tinh tien nguoi lon
		// tre em >0 => (press_class.get(1) tinh tien tre em
		// em be >0 ==> (press_class.get(2) tinh tien em be
		/*
		 * int a = 1; if(tre em > 0 and em be > 0) if(tre em > 0 and em be = 0) of(tre
		 * em = 0 and em be >0) if(tre em = 0 and em be = 0)
		 */
		// khai bao va tinh tien so chieu di/chieu ve
		// tien = don gia (phai doi tu string sang int) * bien hanh khach
		// don gia lay theo tung nhanh

		int adult_money_ob = (TimeUtils.changeStringtoMoney(press_class, 0)) * adult_num_allInfor;
		int child_money_ob = 0;
		int infant_money_ob = 0;
		int adult_money_ib = 0;
		int child_money_ib = 0;
		int infant_money_ib = 0;
		int sum_ob_index = 0;
		int sum_ib_index = 0;

		if ((child_num_allInfor == 0) && (infant_num_allInfor == 0)) {
			Assert.assertEquals((press_class.get(0).getText()).equals(ob_price), true);
			Assert.assertEquals((press_class.get(1).getText()).equals(ib_price), true);
			adult_money_ib = (TimeUtils.changeStringtoMoney(press_class, 1)) * adult_num_allInfor;
			sum_ob_index = 1;
			sum_ib_index = 3;
		} else if ((child_num_allInfor > 0) && (infant_num_allInfor > 0)) {
			Assert.assertEquals((press_class.get(0).getText()).equals(ob_price), true);
			child_money_ob = (TimeUtils.changeStringtoMoney(press_class, 1)) * child_num_allInfor;
			infant_money_ob = (TimeUtils.changeStringtoMoney(press_class, 2)) * infant_num_allInfor;
			Assert.assertEquals((press_class.get(3).getText()).equals(ib_price), true);
			adult_money_ib = (TimeUtils.changeStringtoMoney(press_class, 3)) * adult_num_allInfor;
			child_money_ib = (TimeUtils.changeStringtoMoney(press_class, 4)) * child_num_allInfor;
			infant_money_ib = (TimeUtils.changeStringtoMoney(press_class, 5)) * infant_num_allInfor;
			sum_ob_index = 3;
			sum_ib_index = 7;
		} else if ((child_num_allInfor > 0) && (infant_num_allInfor == 0)) {

			Assert.assertEquals((press_class.get(0).getText()).equals(ob_price), true);
			child_money_ob = (TimeUtils.changeStringtoMoney(press_class, 1)) * child_num_allInfor;
			Assert.assertEquals((press_class.get(2).getText()).equals(ib_price), true);
			adult_money_ib = (TimeUtils.changeStringtoMoney(press_class, 2)) * adult_num_allInfor;
			child_money_ib = (TimeUtils.changeStringtoMoney(press_class, 3)) * child_num_allInfor;
			sum_ob_index = 2;
			sum_ib_index = 5;
		} else if ((child_num_allInfor == 0) && (infant_num_allInfor > 0)) {

			Assert.assertEquals((press_class.get(0).getText()).equals(ob_price), true);
			infant_money_ob = (TimeUtils.changeStringtoMoney(press_class, 1)) * infant_num_allInfor;
			Assert.assertEquals((press_class.get(2).getText()).equals(ib_price), true);
			adult_money_ib = (TimeUtils.changeStringtoMoney(press_class, 2)) * adult_num_allInfor;
			infant_money_ib = (TimeUtils.changeStringtoMoney(press_class, 3)) * infant_num_allInfor;
			sum_ob_index = 2;
			sum_ib_index = 5;
		}

		// lay theo the pull-right (tinh tien, tong tien)
		// so sanh tong tien chieu di + tong tien chieu ve co bang tong tien don hang
		// hay khong
		// muon tinh tong tien chieu di = don gia * so khach tuong ung
		// flight-way-item-info xong tro den pull-right
		// lay chi so cua mang

		List<WebElement> flight_way_class = SelectedFlightInfordiv
				.findElements(By.cssSelector(".flight-way-item-info"));
		WebElement press_class_ob = (flight_way_class.get(sum_ob_index)).findElement(By.cssSelector(".pull-right"));

		// tinh tong tien chieu di va so sanh
		System.out.println("Tien ve nguoi lon chieu di: " + adult_money_ob);
		System.out.println("Tien ve tre em chieu di: " + child_money_ob);
		System.out.println("Tien ve em be chieu di: " + infant_money_ob);

		int sum_money_ob = adult_money_ob + child_money_ob + infant_money_ob;
		System.out.println("Tong tien ve chieu di: " + sum_money_ob);
		String sum_money_text1 = press_class_ob.getText();
		sum_money_text1 = sum_money_text1.replaceAll("\\D+", "");
		int sum_money_int1 = Integer.parseInt(sum_money_text1);
		Assert.assertEquals(sum_money_int1 == sum_money_ob, true);

		// tinh tong tien chieu ve va so sanh
		WebElement press_class_return = (flight_way_class.get(sum_ib_index)).findElement(By.cssSelector(".pull-right"));
		System.out.println("Tien ve nguoi lon chieu ve: " + adult_money_ib);
		System.out.println("Tien ve tre em chieu ve: " + child_money_ib);
		System.out.println("Tien ve em be chieu ve: " + infant_money_ib);

		int sum_money_ib = adult_money_ib + child_money_ib + infant_money_ib;
		System.out.println("Tong tien ve chieu ve: " + sum_money_ib);
		String sum_money_text2 = press_class_return.getText();
		sum_money_text2 = sum_money_text2.replaceAll("\\D+", "");
		int sum_money_int2 = Integer.parseInt(sum_money_text2);
		Assert.assertEquals(sum_money_int2 == sum_money_ib, true);

		// tinh tong tien hai chieu va so sanh

		int sum_money = sum_money_ob + sum_money_ib;
		// WebElement sum_money_itemdiv =
		// driver.findElement(By.cssSelector("#selected-flight"));
		WebElement sum_money_item = SelectedFlightInfordiv.findElement(By.cssSelector(".toi-row"));
		WebElement sum_money_item1 = sum_money_item.findElement(By.cssSelector(".pull-right"));

		String sum_money_text3 = sum_money_item1.getText();
		sum_money_text3 = sum_money_text3.replaceAll("\\D+", "");
		int sum_money_int3 = Integer.parseInt(sum_money_text3);
		System.out.println("Tong tien ve hai chieu: " + sum_money_text3);
		Assert.assertEquals(sum_money_int3 == sum_money, true);

		System.out.println("1. Thong tin hien thi tren ve chinh xac voi thong tin hien thi sau khi an nut Chon ve");

		// am nut Xac nhan chon ve

		// WebElement selectedFlightDiv =
		// driver.findElement(By.cssSelector("#selected-flight"));
		WebElement confirmBookTicketbtn = SelectedFlightInfordiv
				.findElement(By.cssSelector(".flight-add-payment-info"));
		confirmBookTicketbtn.click();
		TimeUtils.sleep(5);
		// lay the khung Thong tin dat ve
		WebElement BookingInfoDiv = driver.findElement(By.cssSelector(".content-item-sidebar-payment"));

		// so sanh theo #note-1 va #note-2 va #total-price
		// theo #note-1 lay hai item dau tien cua mang

		// tao mang lay ve theo#note-1 (chieu di)
		List<WebElement> note_1_array = BookingInfoDiv.findElements(By.cssSelector(".note-1"));
		// the pull-left la chang bay A - B
		// chang bay chieu di
		WebElement route_ob = (note_1_array.get(0)).findElement(By.cssSelector(".pull-left"));
		String route_ob_text = route_ob.getText();
		String[] route_ob_text_splited = route_ob_text.split(" - ");
		Assert.assertEquals(from_airport_allInfor.equals(route_ob_text_splited[0]), true);
		Assert.assertEquals(to_airport_allInfor.equals(route_ob_text_splited[1]), true);
		// gio bay chieu di
		// khong lay dc ngay bay
		WebElement time_ob = (note_1_array.get(0)).findElement(By.cssSelector(".pull-right"));
		String time_ob_text = time_ob.getText();
		String[] time_ob_text_array = time_ob_text.split(" - ");
		// gio di
		Assert.assertEquals(ob_timefrom.equals(time_ob_text_array[0]), true);
		// gio den
		// tiep tuc phan tach gio den va ngay bay
		String[] time_ob_text_a1 = (time_ob_text_array[1]).split("[|]");
		// gio den
		Assert.assertEquals(ob_timeto.equals(time_ob_text_a1[0].trim()), true);
		// so sanh ngay bay DATE va MONTH

		String[] date_month_ob = (time_ob_text_a1[1].trim()).split("/");
		Assert.assertEquals(a == (Integer.parseInt(date_month_ob[0])), true);
		Assert.assertEquals(b == (Integer.parseInt(date_month_ob[1])), true);

		// chang bay chieu ve
		WebElement route_ib = (note_1_array.get(1)).findElement(By.cssSelector(".pull-left"));
		String route_ib_text = route_ib.getText();
		String[] route_ib_text_splited = route_ib_text.split(" - ");
		Assert.assertEquals(to_airport_allInfor.equals(route_ib_text_splited[0]), true);
		Assert.assertEquals(from_airport_allInfor.equals(route_ib_text_splited[1]), true);

		// gio bay chieu ve

		WebElement time_ib = (note_1_array.get(1)).findElement(By.cssSelector(".pull-right"));
		/// --------------

		String time_ib_text = time_ib.getText();
		String[] time_ib_text_array = time_ib_text.split(" - ");
		// gio di
		Assert.assertEquals(ib_timefrom.equals(time_ib_text_array[0]), true);
		// gio den
		// tiep tuc phan tach gio den va ngay bay
		String[] time_ib_text_a1 = (time_ib_text_array[1]).split("[|]");
		// gio den
		Assert.assertEquals(ib_timeto.equals(time_ib_text_a1[0].trim()), true);
		// so sanh ngay bay DATE va MONTH

		String[] date_month_ib = (time_ib_text_a1[1].trim()).split("/");
		Assert.assertEquals(d == (Integer.parseInt(date_month_ib[0])), true);
		Assert.assertEquals(e == (Integer.parseInt(date_month_ib[1])), true);

		// so sanh voi bien from_airport_allInfor va to_airport_allInfor
		// the pull-right la gio bay|ngay bay
		// get ve roi phan tach chuoi ra de lay gia tri
		// so sanh voi bien ob_timefrom va ob_timeto doi voi chieu di
		// so sanh voi bien ib_timefrom va ib_timeto doi voi chieu ve
		// ngay bay lay theo bien tinh lai ngay di và ngay tro ve

		// theo #note-2 lay hai item cua mang (hang bay) airlines_from_allInfor
		// airlines_to_allInfor
		List<WebElement> note_2_array = BookingInfoDiv.findElements(By.cssSelector(".note-2"));
		WebElement airlines_ob = (note_2_array.get(0)).findElement(By.cssSelector(".pull-left"));
		Assert.assertEquals(airlines_from_allInfor.equals(airlines_ob.getText()), true);

		WebElement airlines_ib = (note_2_array.get(1)).findElement(By.cssSelector(".pull-left"));
		Assert.assertEquals(airlines_to_allInfor.equals(airlines_ib.getText()), true);

		// #total-price la duy nhat
		WebElement total_pricediv = BookingInfoDiv.findElement(By.cssSelector(".total-price"));
		WebElement total_price = total_pricediv.findElement(By.tagName("b"));
		String total_price_text = total_price.getText();
		total_price_text = total_price_text.replaceAll("\\D+", "");
		int total_price_int = Integer.parseInt(total_price_text);
		Assert.assertEquals(total_price_int == sum_money, true);
		System.out.println("2. Sau khi an nut Xac nhan dat ve, Thong tin chuyen bay hien thi chinh xac");
		System.out.println("==> PASSED");
	}
}

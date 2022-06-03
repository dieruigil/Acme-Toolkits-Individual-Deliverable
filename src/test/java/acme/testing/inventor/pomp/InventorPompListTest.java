package acme.testing.inventor.pomp;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class InventorPompListTest extends TestHarness {
	
	// Lifecycle management ---------------------------------------------------

	// Test cases -------------------------------------------------------------
	
	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/pomp/list-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positiveTest(final int recordIndex, final String code, final String creationMoment, final String name, final String explanation, final String startDate,
		final String finishDate, final String expenditure, final String moreInfo) {
		
		super.signIn("inventor2", "inventor2");
		
		super.clickOnMenu("Inventor", "Pomps");
		super.checkListingExists();
		
		super.sortListing(0, "asc");
		super.checkColumnHasValue(recordIndex, 0, code);
		super.checkColumnHasValue(recordIndex, 1, creationMoment);
		super.checkColumnHasValue(recordIndex, 2, name);
		
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		
		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("creationMoment", creationMoment);
		super.checkInputBoxHasValue("name", name);
		super.checkInputBoxHasValue("explanation", explanation);
		super.checkInputBoxHasValue("startDate", startDate);
		super.checkInputBoxHasValue("finishDate", finishDate);
		super.checkInputBoxHasValue("expenditure", expenditure);
		super.checkInputBoxHasValue("moreInfo", moreInfo);
		
		super.signOut();
	}
	
	@Test
	@Order(20)
	public void hackingTest() {
		super.signIn("patron1", "patron1");
		super.navigate("/inventor/list/pomp");
		super.checkPanicExists();
		
		super.signOut();
		
		super.signIn("administrator", "administrator");
		super.navigate("/inventor/pomp/list");
		super.checkPanicExists();
	}
}

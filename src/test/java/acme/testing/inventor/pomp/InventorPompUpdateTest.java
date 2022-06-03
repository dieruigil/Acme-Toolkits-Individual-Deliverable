package acme.testing.inventor.pomp;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class InventorPompUpdateTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/pomp/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positiveTest(final int recordIndex, final String name, final String explanation,
		final String startDate, final String finishDate,final String expenditure, final String moreInfo) {
		
		super.signIn("inventor2", "inventor2");
		
		super.clickOnMenu("Inventor", "Pomps");
		super.checkListingExists();
		
		super.clickOnListingRecord(0);
		super.checkFormExists();
		
		super.fillInputBoxIn("name", name);
		super.fillInputBoxIn("explanation", explanation);
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("finishDate", finishDate);
		super.fillInputBoxIn("expenditure", expenditure);
		super.fillInputBoxIn("moreInfo", moreInfo);
		super.clickOnSubmit("Update");
		
		super.checkNotErrorsExist();
		super.signOut();
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/pomp/update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(20)
	public void negativeTest(final int recordIndex, final String name, final String explanation,
		final String startDate, final String finishDate,final String expenditure, final String moreInfo) {
		
		super.signIn("inventor2", "inventor2");
		
		super.clickOnMenu("Inventor", "Pomps");
		super.checkListingExists();
		
		super.clickOnListingRecord(0);
		super.checkFormExists();
		
		super.fillInputBoxIn("name", name);
		super.fillInputBoxIn("explanation", explanation);
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("finishDate", finishDate);
		super.fillInputBoxIn("expenditure", expenditure);
		super.fillInputBoxIn("moreInfo", moreInfo);
		super.clickOnSubmit("Update");
		
		super.checkErrorsExist();
		super.signOut();
	}
	
	@Test
	@Order(30)
	public void hackingTest() {
		// HINT: the framework doesn't currently provide enough support to hack
		// HINT+ this feature, so the hacking tests must be performed manually.
		// HINT+ Login as a patron and search for a pomp id in DBeaver.
		// HINT+ Navigate to the url /inventor/pomp/update?id=pompId
	}
}

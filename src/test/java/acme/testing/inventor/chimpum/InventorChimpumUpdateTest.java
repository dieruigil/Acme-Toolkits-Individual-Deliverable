package acme.testing.inventor.chimpum;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class InventorChimpumUpdateTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/chimpum/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positiveTest(final int recordIndex, final String title, final String description,
		final String startDate, final String finishDate,final String budget, final String link) {
		
		super.signIn("inventor2", "inventor2");
		
		super.clickOnMenu("Inventor", "Chimpums");
		super.checkListingExists();
		
		super.clickOnListingRecord(0);
		super.checkFormExists();
		
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("description", description);
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("finishDate", finishDate);
		super.fillInputBoxIn("budget", budget);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Update");
		
		super.checkNotErrorsExist();
		super.signOut();
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/chimpum/update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(20)
	public void negativeTest(final int recordIndex, final String title, final String description,
		final String startDate, final String finishDate,final String budget, final String link) {
		
		super.signIn("inventor2", "inventor2");
		
		super.clickOnMenu("Inventor", "Chimpums");
		super.checkListingExists();
		
		super.clickOnListingRecord(0);
		super.checkFormExists();
		
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("description", description);
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("finishDate", finishDate);
		super.fillInputBoxIn("budget", budget);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Update");
		
		super.checkErrorsExist();
		super.signOut();
	}
	
	@Test
	@Order(30)
	public void hackingTest() {
		// HINT: the framework doesn't currently provide enough support to hack
		// HINT+ this feature, so the hacking tests must be performed manually.
		// HINT+ Login as a patron and search for a chimpum id in DBeaver.
		// HINT+ Navigate to the url /inventor/chimpum/update?id=chimpumId
	}
}

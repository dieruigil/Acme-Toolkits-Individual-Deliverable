package acme.testing.inventor.pomp;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class InventorPompDeleteTest extends TestHarness {
	
	// Lifecycle management ---------------------------------------------------

	// Test cases -------------------------------------------------------------
	
	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/pomp/delete.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positiveTest(final int recordIndex) {
		super.signIn("inventor2", "inventor2");
		
		super.clickOnMenu("Inventor", "Pomps");
		super.checkListingExists();
		
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		
		super.clickOnSubmit("Delete");
		super.checkNotErrorsExist();
		super.signOut();
	}
	
	@Test
	@Order(30)
	public void hackingTest() {
		// HINT: the framework doesn't currently provide enough support to hack
		// HINT+ this feature, so the hacking tests must be performed manually.
		// HINT+ Login as a patron and search for a pomp id in DBeaver.
		// HINT+ Navigate to the url /inventor/pomp/delete?id=pompId
	}
}

package acme.testing.inventor.chimpum;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.chimpum.Chimpum;
import acme.features.inventor.chimpum.InventorChimpumRepository;
import acme.framework.datatypes.Money;
import acme.framework.helpers.FactoryHelper;
import acme.testing.TestHarness;

public class InventorChimpumCreateTest extends TestHarness {
	
	@Autowired
	protected InventorChimpumRepository repository;
	
	@Override
	@BeforeAll
	public void beforeAll() {
		super.beforeAll();
		FactoryHelper.autowire(this);
		Chimpum chimpum;
		Date creationMoment;
		Date startDate;
		Date finishDate;
		Calendar calendar;
		Money budget;
		
		chimpum = new Chimpum();
		creationMoment = new Date();
		calendar = new GregorianCalendar();
		budget = new Money();
		
		calendar.add(Calendar.YEAR, 1);
		startDate = calendar.getTime();
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		finishDate = calendar.getTime();
		budget.setCurrency("EUR");
		budget.setAmount(20.34);
		
		chimpum.setCode("FGH-189-B");
		chimpum.setTitle("Title");
		chimpum.setDescription("Description");
		chimpum.setCreationMoment(creationMoment);
		chimpum.setStartDate(startDate);
		chimpum.setFinishDate(finishDate);
		chimpum.setBudget(budget);
		
		this.repository.save(chimpum);
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/chimpum/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positiveTest(final int recordIndex, final String code, final String title, final String description,
		final String startDate, final String finishDate,final String budget, final String link) {
		
		super.signIn("inventor2", "inventor2");
		
		super.clickOnMenu("Inventor", "Chimpums");
		super.checkListingExists();
		
		super.clickOnButton("Create");
		super.checkFormExists();
		
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("description", description);
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("finishDate", finishDate);
		super.fillInputBoxIn("budget", budget);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Create");
		
		super.checkNotErrorsExist();
		super.signOut();
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/chimpum/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(20)
	public void negativeTest(final int recordIndex, final String code, final String title, final String description,
		final String startDate, final String finishDate,final String budget, final String link) {
		
		super.signIn("inventor2", "inventor2");
		
		super.clickOnMenu("Inventor", "Chimpums");
		super.checkListingExists();
		
		super.clickOnButton("Create");
		super.checkFormExists();
		
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("description", description);
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("finishDate", finishDate);
		super.fillInputBoxIn("budget", budget);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Create");
		
		super.checkErrorsExist();
		super.signOut();
	}
	
	@Test
	@Order(30)
	public void hackingTest() {
		super.signIn("patron1", "patron1");
		super.navigate("/inventor/chimpum/create");
		super.checkPanicExists();
		
		super.signOut();
		
		super.signIn("administrator", "administrator");
		super.navigate("/inventor/chimpum/create");
		super.checkPanicExists();
	}
}

package acme.testing.inventor.pomp;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.pomp.Pomp;
import acme.features.inventor.pomp.InventorPompRepository;
import acme.framework.datatypes.Money;
import acme.framework.helpers.FactoryHelper;
import acme.testing.TestHarness;

public class InventorPompCreateTest extends TestHarness {
	
	@Autowired
	protected InventorPompRepository repository;
	
	@Override
	@BeforeAll
	public void beforeAll() {
		super.beforeAll();
		FactoryHelper.autowire(this);
		Pomp pomp;
		Date creationMoment;
		Date startDate;
		Date finishDate;
		Calendar calendar;
		Money expenditure;
		
		pomp = new Pomp();
		creationMoment = new Date();
		calendar = new GregorianCalendar();
		expenditure = new Money();
		
		calendar.add(Calendar.YEAR, 1);
		startDate = calendar.getTime();
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		finishDate = calendar.getTime();
		expenditure.setCurrency("EUR");
		expenditure.setAmount(20.34);
		
		pomp.setCode("GH-18");
		pomp.setName("Name");
		pomp.setExplanation("Explanation");
		pomp.setCreationMoment(creationMoment);
		pomp.setStartDate(startDate);
		pomp.setFinishDate(finishDate);
		pomp.setExpenditure(expenditure);
		
		this.repository.save(pomp);
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/pomp/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positiveTest(final int recordIndex, final String code, final String name, final String explanation,
		final String startDate, final String finishDate,final String expenditure, final String moreInfo) {
		
		super.signIn("inventor2", "inventor2");
		
		super.clickOnMenu("Inventor", "Pomps");
		super.checkListingExists();
		
		super.clickOnButton("Create");
		super.checkFormExists();
		
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("name", name);
		super.fillInputBoxIn("explanation", explanation);
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("finishDate", finishDate);
		super.fillInputBoxIn("expenditure", expenditure);
		super.fillInputBoxIn("moreInfo", moreInfo);
		super.clickOnSubmit("Create");
		
		super.checkNotErrorsExist();
		super.signOut();
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/pomp/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(20)
	public void negativeTest(final int recordIndex, final String code, final String name, final String explanation,
		final String startDate, final String finishDate,final String expenditure, final String moreInfo) {
		
		super.signIn("inventor2", "inventor2");
		
		super.clickOnMenu("Inventor", "Pomps");
		super.checkListingExists();
		
		super.clickOnButton("Create");
		super.checkFormExists();
		
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("name", name);
		super.fillInputBoxIn("explanation", explanation);
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("finishDate", finishDate);
		super.fillInputBoxIn("expenditure", expenditure);
		super.fillInputBoxIn("moreInfo", moreInfo);
		super.clickOnSubmit("Create");
		
		super.checkErrorsExist();
		super.signOut();
	}
	
	@Test
	@Order(30)
	public void hackingTest() {
		super.signIn("patron1", "patron1");
		super.navigate("/inventor/pomp/create");
		super.checkPanicExists();
		
		super.signOut();
		
		super.signIn("administrator", "administrator");
		super.navigate("/inventor/pomp/create");
		super.checkPanicExists();
	}
}

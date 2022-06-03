package acme.features.inventor.pomp;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.pomp.Pomp;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractUpdateService;
import acme.roles.Inventor;
import features.SpamDetector;

@Service
public class InventorPompUpdateService implements AbstractUpdateService<Inventor, Pomp> {

	// Internal state ---------------------------------------------------------
	
	@Autowired
	protected InventorPompRepository repository;
	
	// AbstractUpdateService<Inventor, Pomp> interface ---------------------
	
	@Override
	public boolean authorise(final Request<Pomp> request) {
		assert request != null;
		
		return true;
	}

	@Override
	public void bind(final Request<Pomp> request, final Pomp entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		request.bind(entity, errors, "creationMoment", "name", "explanation", "startDate",
			"finishDate", "expenditure", "moreInfo");
	}

	@Override
	public void unbind(final Request<Pomp> request, final Pomp entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		request.unbind(entity, model, "creationMoment", "name", "explanation", "startDate",
			"finishDate", "expenditure", "moreInfo");
		model.setAttribute("code", entity.getPattern());
	}

	@Override
	public Pomp findOne(final Request<Pomp> request) {
		assert request != null;
		
		Pomp result;
		int pompId;
		
		pompId = request.getModel().getInteger("id");
		result = this.repository.findPompById(pompId);
		
		return result;
	}

	@Override
	public void validate(final Request<Pomp> request, final Pomp entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		SpamDetector spamDetector;
		String strongSpamTerms;
		String weakSpamTerms;
		int strongSpamThreshold;
		int weakSpamThreshold;
		
		spamDetector = new SpamDetector();
		strongSpamTerms = this.repository.findStrongSpamTerms();
		weakSpamTerms = this.repository.findWeakSpamTerms();
		strongSpamThreshold = this.repository.findStrongSpamTreshold();
		weakSpamThreshold = this.repository.findWeakSpamTreshold();
		
		if (!errors.hasErrors("expenditure")) {
			final String currency = entity.getExpenditure().getCurrency();
			final String currencyAvaliable = this.repository.acceptedCurrencies();
			boolean acceptedCurrency = false;
			
			for(final String cur: currencyAvaliable.split(",")) {
				acceptedCurrency = cur.trim().equalsIgnoreCase(currency);
				if(acceptedCurrency)
					break;
			}
			errors.state(request, entity.getExpenditure().getAmount() > 0 , "expenditure", "inventor.pomp.form.error.negative-expenditure");
			errors.state(request,acceptedCurrency, "expenditure", "inventor.pomp.form.error.negative-currency");
		}
		
		if(!errors.hasErrors("startDate")) {
			Calendar calendar;
			
			calendar = new GregorianCalendar();
			calendar.setTime(entity.getCreationMoment());
			calendar.add(Calendar.MONTH, 1);
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			
			errors.state(request, entity.getStartDate().after(calendar.getTime()), "startDate", "inventor.pomp.form.error.startDate");
		}
		
		if(!errors.hasErrors("finishDate")) {
			Calendar calendar;
			
			boolean errorState = true;
			
			if (entity.getStartDate() != null) {		
				calendar = new GregorianCalendar();
				calendar.setTime(entity.getStartDate());
				calendar.add(Calendar.WEEK_OF_MONTH, 1);
				calendar.add(Calendar.DAY_OF_MONTH, -1);
				errorState = entity.getFinishDate().after(calendar.getTime());
			}
			
			errors.state(request, errorState, "finishDate", "inventor.pomp.form.error.finishDate");
		}
		
		if (!errors.hasErrors("name")) {
			errors.state(request, !spamDetector.containsSpam(weakSpamTerms.split(","), weakSpamThreshold, entity.getName())
				&& !spamDetector.containsSpam(strongSpamTerms.split(","), strongSpamThreshold, entity.getName()),
				"name", "inventor.pomp.form.error.spam");
		}
		
		if (!errors.hasErrors("explanation")) {
			errors.state(request, !spamDetector.containsSpam(weakSpamTerms.split(","), weakSpamThreshold, entity.getExplanation())
				&& !spamDetector.containsSpam(strongSpamTerms.split(","), strongSpamThreshold, entity.getExplanation()),
				"explanation", "inventor.pomp.form.error.spam");
		}
	}

	@Override
	public void update(final Request<Pomp> request, final Pomp entity) {
		assert request != null;
		assert entity != null;
		
		this.repository.save(entity);
	}

}

package acme.features.inventor.pomp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.pomp.Pomp;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractShowService;
import acme.roles.Inventor;

@Service
public class InventorPompShowService implements AbstractShowService<Inventor, Pomp> {

	// Internal state ---------------------------------------------------------
	
	@Autowired
	protected InventorPompRepository repository;
	
	// AbstractShowService<Inventor, Pomp> interface -----------------------
	
	@Override
	public boolean authorise(final Request<Pomp> request) {
		assert request != null;
		
		return true;
	}

	@Override
	public Pomp findOne(final Request<Pomp> request) {
		assert request != null;
		
		int pompId;
		Pomp result;
		
		pompId = request.getModel().getInteger("id");
		result = this.repository.findPompById(pompId);
		
		return result;
	}

	@Override
	public void unbind(final Request<Pomp> request, final Pomp entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		request.unbind(entity, model, "creationMoment", "name", "explanation",
			"startDate", "finishDate", "expenditure", "moreInfo");
		model.setAttribute("pompId", entity.getId());
		model.setAttribute("code", entity.getPattern());
	}

}

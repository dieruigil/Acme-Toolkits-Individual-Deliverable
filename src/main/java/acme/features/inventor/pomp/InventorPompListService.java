package acme.features.inventor.pomp;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.pomp.Pomp;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractListService;
import acme.roles.Inventor;

@Service
public class InventorPompListService implements AbstractListService<Inventor, Pomp> {
	
	// Internal state ---------------------------------------------------------
	
	@Autowired
	protected InventorPompRepository repository;
	
	// AbstractListService<Inventor, Pomp> interface -----------------------

	@Override
	public boolean authorise(final Request<Pomp> request) {
		assert request != null;
		
		return true;
	}

	@Override
	public Collection<Pomp> findMany(final Request<Pomp> request) {
		assert request != null;
		
		Collection<Pomp> result;
		
		result = this.repository.findAllPomp();
		
		return result;
	}

	@Override
	public void unbind(final Request<Pomp> request, final Pomp entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		request.unbind(entity, model, "creationMoment", "name");
		model.setAttribute("code", entity.getPattern());
	}

}

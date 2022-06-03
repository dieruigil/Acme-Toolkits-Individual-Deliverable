package acme.features.inventor.pomp;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.artifacts.Artifact;
import acme.entities.pomp.Pomp;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractDeleteService;
import acme.roles.Inventor;

@Service
public class InventorPompDeleteService implements AbstractDeleteService<Inventor, Pomp> {

	// Internal state ---------------------------------------------------------
	
	@Autowired
	protected InventorPompRepository repository;
	
	// AbstractDeleteService<Inventor, Pomp> interface ---------------------
	
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
		
		request.bind(entity, errors, "creationMoment", "title", "description", "startDate",
			"finishDate", "budget", "link");
	}

	@Override
	public void unbind(final Request<Pomp> request, final Pomp entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		request.unbind(entity, model, "creationMoment", "title", "description", "startDate",
			"finishDate", "budget", "link");
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
		
	}

	@Override
	public void delete(final Request<Pomp> request, final Pomp entity) {
		assert request != null;
		assert entity != null;
		
		Collection<Artifact> artifacstWithPomp;
		
		artifacstWithPomp = this.repository.findArtifactsByPompId(entity.getId());
		
		for(final Artifact artifact: artifacstWithPomp) {
			artifact.setPomp(null);
		}
		
		this.repository.saveAll(artifacstWithPomp);
		this.repository.delete(entity);
	}

}

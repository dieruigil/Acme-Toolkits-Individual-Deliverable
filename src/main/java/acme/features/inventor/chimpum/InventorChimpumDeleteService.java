package acme.features.inventor.chimpum;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.artifacts.Artifact;
import acme.entities.chimpum.Chimpum;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractDeleteService;
import acme.roles.Inventor;

@Service
public class InventorChimpumDeleteService implements AbstractDeleteService<Inventor, Chimpum> {

	// Internal state ---------------------------------------------------------
	
	@Autowired
	protected InventorChimpumRepository repository;
	
	// AbstractDeleteService<Inventor, Chimpum> interface ---------------------
	
	@Override
	public boolean authorise(final Request<Chimpum> request) {
		assert request != null;
		
		return true;
	}

	@Override
	public void bind(final Request<Chimpum> request, final Chimpum entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		request.bind(entity, errors, "creationMoment", "title", "description", "startDate",
			"finishDate", "budget", "link");
	}

	@Override
	public void unbind(final Request<Chimpum> request, final Chimpum entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		request.unbind(entity, model, "creationMoment", "title", "description", "startDate",
			"finishDate", "budget", "link");
		model.setAttribute("code", entity.getCode());
	}

	@Override
	public Chimpum findOne(final Request<Chimpum> request) {
		assert request != null;
		
		Chimpum result;
		int chimpumId;
		
		chimpumId = request.getModel().getInteger("id");
		result = this.repository.findChimpumById(chimpumId);
		
		return result;
	}

	@Override
	public void validate(final Request<Chimpum> request, final Chimpum entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
	}

	@Override
	public void delete(final Request<Chimpum> request, final Chimpum entity) {
		assert request != null;
		assert entity != null;
		
		Collection<Artifact> artifacstWithChimpum;
		
		artifacstWithChimpum = this.repository.findArtifactsByChimpumId(entity.getId());
		
		for(final Artifact artifact: artifacstWithChimpum) {
			artifact.setChimpum(null);
		}
		
		this.repository.saveAll(artifacstWithChimpum);
		this.repository.delete(entity);
	}

}

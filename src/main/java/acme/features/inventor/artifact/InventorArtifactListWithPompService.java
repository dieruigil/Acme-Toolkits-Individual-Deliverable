package acme.features.inventor.artifact;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.artifacts.Artifact;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractListService;
import acme.roles.Inventor;

@Service
public class InventorArtifactListWithPompService implements AbstractListService<Inventor, Artifact> {

	// Internal state ---------------------------------------------------------
	
	@Autowired
	protected InventorArtifactRepository repository;
	
	// AbstractListWithChimpumService<Inventor, Artifact> interface -----------
	
	@Override
	public boolean authorise(final Request<Artifact> request) {
		assert request != null;
		
		return true;
	}

	@Override
	public Collection<Artifact> findMany(final Request<Artifact> request) {
		assert request != null;
		
		final int pompId;
		final Collection<Artifact> result;
		
		pompId = request.getModel().getInteger("pompId");
		result = this.repository.findPublishedArtifactsByPompId(pompId);
		
		return result;
	}

	@Override
	public void unbind(final Request<Artifact> request, final Artifact entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		request.unbind(entity, model, "name", "retailPrice", "artifactType");
	}

}

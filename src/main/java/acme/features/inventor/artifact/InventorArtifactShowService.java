package acme.features.inventor.artifact;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.artifacts.Artifact;
import acme.entities.artifacts.ArtifactType;
import acme.entities.pomp.Pomp;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractShowService;
import acme.roles.Inventor;

@Service
public class InventorArtifactShowService implements AbstractShowService<Inventor, Artifact>{
	
	// Internal state ---------------------------------------------------------

	@Autowired
	protected InventorArtifactRepository repository;

	// AbstractListService<Inventor, Artifact> interface ---------------------------
	
	@Override
	public boolean authorise(final Request<Artifact> request) {
		assert request != null;
		
		Integer id;
		Artifact artifact;
		boolean result;
		id = request.getModel().getInteger("id");
		artifact = this.repository.findArtifactById(id);
		
		result = request.getPrincipal().getActiveRoleId() == artifact.getInventor().getId();
		
		
		return result;
	}

	@Override
	public Artifact findOne(final Request<Artifact> request) {
		assert request != null;
	
		Integer id;
		Artifact artifact;
		id = request.getModel().getInteger("id");
		artifact = this.repository.findArtifactById(id);
		
		return artifact;
	}

	@Override
	public void unbind(final Request<Artifact> request, final Artifact entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		String pompCode;
		
		pompCode = entity.getPomp() == null ? null : entity.getPomp().getPattern();
		
		request.unbind(entity, model, "name", "code", "technology", "description", "retailPrice", "artifactType", "link");
		model.setAttribute("pomp", pompCode);
		
		List<String> types;
		List<Pomp> pomps;
		
		types = new ArrayList<String>();
		for(final ArtifactType type:ArtifactType.values()) {
			types.add(type.toString());
		}
		
		pomps = this.repository.findAllPomps();
		
		model.setAttribute("types", types);
		model.setAttribute("published", entity.isPublished());
		model.setAttribute("pomps", pomps);
	}
	
}

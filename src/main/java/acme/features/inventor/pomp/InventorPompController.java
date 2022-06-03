package acme.features.inventor.pomp;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.pomp.Pomp;
import acme.framework.controllers.AbstractController;
import acme.roles.Inventor;

@Controller
public class InventorPompController extends AbstractController<Inventor, Pomp> {
	
	// Internal state ---------------------------------------------------------
	
	@Autowired
	protected InventorPompListService listService;
	
	@Autowired
	protected InventorPompShowService showService;
	
	@Autowired
	protected InventorPompCreateService createService;
	
	@Autowired
	protected InventorPompUpdateService updateService;
	
	@Autowired
	protected InventorPompDeleteService deleteService;
	
	// Constructors -----------------------------------------------------------
	
	@PostConstruct
	protected void initialise() {
		super.addCommand("list", this.listService);
		super.addCommand("show", this.showService);
		super.addCommand("create", this.createService);
		super.addCommand("update", this.updateService);
		super.addCommand("delete", this.deleteService);
	}
}

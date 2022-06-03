package acme.entities.pomp;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.datatypes.Money;
import acme.framework.entities.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Pomp extends AbstractEntity {
	
	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------
	
	@NotBlank
	@Pattern(regexp = "\\w{2}-\\d{2}")
	protected String 				code;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Past
	@NotNull
	protected Date					creationMoment;
	
	@NotBlank
	@Length(max = 100)
	protected String 				name;
	
	@NotBlank
	@Length(max = 255)
	protected String				explanation;
	
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	protected Date					startDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	protected Date					finishDate;
	
	@Valid
	@NotNull
	protected Money					expenditure;
	
	@URL
	protected String				moreInfo;
	
	// Derived attributes -----------------------------------------------------
	
	public String getPattern() {
		String date;
		String[] splitedDate;
		String[] splitedCode;
		
		date = new SimpleDateFormat("dd/MM/yy").format(this.creationMoment);
		splitedDate = date.split("/");
		splitedCode = this.code.split("-");
		
		//dd-\w{2}\d{2}-mmyy
		return splitedDate[0] + "-" + splitedCode[0] + splitedCode[1] + "-" + splitedDate[1] + splitedDate[2];
	}
	
	// Relationships ----------------------------------------------------------
	
	
}
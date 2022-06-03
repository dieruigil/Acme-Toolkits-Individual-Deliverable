package acme.features.inventor.pomp;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.artifacts.Artifact;
import acme.entities.pomp.Pomp;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface InventorPompRepository extends AbstractRepository {
	
	@Query("select p from Pomp p")
	Collection<Pomp> findAllPomp();
	
	@Query("select p from Pomp p where p.id = :id")
	Pomp findPompById(int id);
	
	@Query("select cd.acceptedCurrencies from ConfigData cd")
	String acceptedCurrencies();
	
	@Query("select config.strongSpamTerms from ConfigData config")
	String findStrongSpamTerms();
	
	@Query("select config.weakSpamTerms from ConfigData config")
	String findWeakSpamTerms();
	
	@Query("select config.strongSpamTreshold from ConfigData config")
	int findStrongSpamTreshold();
	
	@Query("select config.weakSpamTreshold from ConfigData config")
	int findWeakSpamTreshold();
	
	@Query("select a from Artifact a where a.pomp.id = :id")
	Collection<Artifact> findArtifactsByPompId(int id);
	
	@Query("select p from Pomp p where p.code = :code")
	List<Pomp> pompsWithSameCode(String code);
}

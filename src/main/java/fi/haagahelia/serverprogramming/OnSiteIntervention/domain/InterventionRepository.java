package fi.haagahelia.serverprogramming.OnSiteIntervention.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel="interventions", path="interventions")
public interface InterventionRepository extends PagingAndSortingRepository<Intervention, Long>{
	public List<Intervention> findByCustomer(Long id);
	
	public List<Intervention> findByEmployee(Employee employee);
	
	public long count();
}
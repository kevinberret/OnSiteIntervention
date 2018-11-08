package fi.haagahelia.serverprogramming.OnSiteIntervention.domain;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel="employees", path="employees")
public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Long>{
	public Employee findByUsername(String username);
	
	public long count();
}
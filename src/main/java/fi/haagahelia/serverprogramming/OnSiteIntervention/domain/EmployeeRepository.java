package fi.haagahelia.serverprogramming.OnSiteIntervention.domain;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import fi.haagahelia.serverprogramming.OnSiteIntervention.service.InlineEmployee;

@RepositoryRestResource(collectionResourceRel="employees", path="employees", excerptProjection= InlineEmployee.class)
public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Long>{
	public Employee findByUsername(String username);
	
	public long count();
}
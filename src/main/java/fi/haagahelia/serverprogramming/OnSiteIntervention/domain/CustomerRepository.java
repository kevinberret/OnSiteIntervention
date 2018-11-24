package fi.haagahelia.serverprogramming.OnSiteIntervention.domain;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import fi.haagahelia.serverprogramming.OnSiteIntervention.service.InlineCustomer;

@RepositoryRestResource(collectionResourceRel="customers", path="customers", excerptProjection=InlineCustomer.class)
public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long>{
	public long count();
}
package fi.haagahelia.serverprogramming.OnSiteIntervention.domain;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel="addresses", path="addresses")
public interface AddressRepository extends PagingAndSortingRepository<Address, Long>{
	public long count();
}
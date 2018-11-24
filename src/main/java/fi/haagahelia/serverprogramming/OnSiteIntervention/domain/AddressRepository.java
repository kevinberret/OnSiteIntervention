package fi.haagahelia.serverprogramming.OnSiteIntervention.domain;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import fi.haagahelia.serverprogramming.OnSiteIntervention.service.InlineAddress;

@RepositoryRestResource(collectionResourceRel="addresses", path="addresses", excerptProjection=InlineAddress.class)
public interface AddressRepository extends PagingAndSortingRepository<Address, Long>{
	public long count();
}
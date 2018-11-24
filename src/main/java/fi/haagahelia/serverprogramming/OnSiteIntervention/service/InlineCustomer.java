package fi.haagahelia.serverprogramming.OnSiteIntervention.service;

import org.springframework.data.rest.core.config.Projection;

import fi.haagahelia.serverprogramming.OnSiteIntervention.domain.Address;
import fi.haagahelia.serverprogramming.OnSiteIntervention.domain.Customer;

@Projection(name="inlineCustomer", types = {Customer.class})
public interface InlineCustomer {
	String getFirstname();
	String getLastname();
	Address getAddress();
}
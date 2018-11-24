package fi.haagahelia.serverprogramming.OnSiteIntervention.service;

import org.springframework.data.rest.core.config.Projection;

import fi.haagahelia.serverprogramming.OnSiteIntervention.domain.Address;
import fi.haagahelia.serverprogramming.OnSiteIntervention.domain.Customer;

@Projection(name="inlineAddress", types = {Address.class})
public interface InlineAddress {
	String getStreet();
	String getNumber();
	String getCity();
	String getZip();
	double getLatitude();
	double getLongitude();
}
package fi.haagahelia.serverprogramming.OnSiteIntervention.service;

import org.springframework.data.rest.core.config.Projection;

import fi.haagahelia.serverprogramming.OnSiteIntervention.domain.Employee;

@Projection(name="inlineEmpoyee", types = {Employee.class})
public interface InlineEmployee {
	String getFirstname();
	String getLastname();
	String getWorkRate();
}
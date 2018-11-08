package fi.haagahelia.serverprogramming.OnSiteIntervention.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.haagahelia.serverprogramming.OnSiteIntervention.domain.Employee;
import fi.haagahelia.serverprogramming.OnSiteIntervention.domain.Intervention;
import fi.haagahelia.serverprogramming.OnSiteIntervention.domain.InterventionRepository;

/**
 * This class is used for all business code for Intervention objects.
 * @author kb
 *
 */
@Service
public class InterventionService {
	@Autowired
	private InterventionRepository interventionRepo;
	
	public List<Intervention> getAllInterventions() {
		return (List<Intervention>) interventionRepo.findAll();
	}
	
	public List<Intervention> getAllInterventionsByEmployee(Employee employee){
		return (List<Intervention>) interventionRepo.findByEmployee(employee);
	}
	
	public Optional<Intervention> getIntervention(Long id) {
		return interventionRepo.findById(id);
	}
	
	public Intervention addIntervention(Intervention intervention) {
		return interventionRepo.save(intervention);
	}
	
	public Intervention updateIntervention(Intervention intervention) {
		return interventionRepo.save(intervention);
	}
	
	public boolean deleteIntervention(Intervention intervention) {
		// Try to find the desired intervention
		Optional<Intervention> element = interventionRepo.findById(intervention.getId());
		
		if(element.isPresent()) {		
			interventionRepo.delete(intervention);
			return true;
		}
		
		return false;
	}
	
	public boolean deleteInterventionById(Long id) {
		// Try to find the desired intervention
		Optional<Intervention> element = interventionRepo.findById(id);
		
		if(element.isPresent()) {
			interventionRepo.delete(element.get());
			return true;
		}
		
		return false;
	}
}
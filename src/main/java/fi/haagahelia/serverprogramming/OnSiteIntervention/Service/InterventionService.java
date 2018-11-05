package fi.haagahelia.serverprogramming.OnSiteIntervention.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
	
	public Optional<Intervention> getIntervention(Long id) {
		return interventionRepo.findById(id);
	}
	
	public Intervention addIntervention(Intervention intervention) {
		return interventionRepo.save(intervention);
	}
	
	public Intervention updateIntervention(Intervention intervention) {
		return interventionRepo.save(intervention);
	}
	
	public boolean deleteIntervention(Long id) {
		// Try to find the desired intervention
		Optional<Intervention> element = interventionRepo.findById(id);
		
		if(element.isPresent()) {
			interventionRepo.delete(element.get());
			return true;
		}
		
		return false;
	}
}
package fi.haagahelia.serverprogramming.OnSiteIntervention.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DefaultController {
	@RequestMapping(value="/")
    public String defaultPage() {	
		// redirect to index
		return "redirect:/app/index";
    }
}
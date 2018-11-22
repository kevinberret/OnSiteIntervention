package fi.haagahelia.serverprogramming.OnSiteIntervention.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/app")
public class IndexController {
	@RequestMapping(value="/login")
    public String login() {	
        return "index/login";
    }
	
	@GetMapping("/index")
	public String index(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		model.addAttribute("username", auth.getName());
		return "index/index";
	}
	
	@GetMapping("/403")
	public String accessDenied() {
		return "index/403";
	}
}
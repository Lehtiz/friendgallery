package projekti.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import projekti.IAuthenticationFacade;

@Controller
public class DefaultController {
    
    @Autowired
    private IAuthenticationFacade authenticationFacade;
    
    @GetMapping("/")
    public String helloWorld(Model model) {
        
        Authentication authentication = authenticationFacade.getAuthentication();
        if (authentication.isAuthenticated()){
            //show wall
        }
        model.addAttribute("message", authentication.getName());
        return "index";
    }
    
}

package projekti.controller;

import projekti.model.Account;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import projekti.service.AccountService;

@Controller
public class AccountController {
    
    @Autowired
    private AccountService accountService;
    
    @ModelAttribute
    private Account getAccount() {
        return new Account();
    }
    
    /////////
    // GET //
    /////////
    // List persons
    @GetMapping("/accounts")
    public String list(Model model){
        model.addAttribute("accounts", accountService.findAll());
        return "accounts";
    }
    
    // show info on an account
    @GetMapping("account/{username}")
    public String getUser(Model model, @PathVariable String username){
        model.addAttribute("account", accountService.findByUsername(username));
        return "account";
    }
    
    // form to add a new person
    // needs @ModelAttribute Account person object or defined in controller
    // form uses this to save data
    @GetMapping("/accountform")
    public String add(){
        return "accountform";
    }
    
    // show search form
    @GetMapping("/searchform")
    public String searchform(Model model){
        return "search";
    }
    
    //show search result with search input and results as links list
    @GetMapping("/search")
    public String searchResult(
            Model model, 
            @RequestParam String username){//@RequestParam Map<String, String> param
        model.addAttribute(
                "accounts", 
                accountService.findByUsername(username));
        return "search";// return param.keySet().toString();
    }
    
    //////////
    // POST //
    //////////
    // Create a person, validate via model, and request data like ModelAttribute,
    // if not valid save errors to bindingResult 
    @PostMapping("/create")
    public String create(
            @Valid @ModelAttribute Account account, 
            BindingResult bindingResult) {
        
        // check that the account does not exist already
        Account accountExistsCheck = accountService.findByUsername(account.getUsername());//accountRepository.findByUsername(account.getUsername());
        
        if(accountExistsCheck != null){
            // add an rejection to bindingResult 
            bindingResult.rejectValue(
                    "username",
                    "Error_UserAlreadyExists", 
                    "That name is already taken");
        }
        
        // return to form with previous data if errors occur in validation
        if(bindingResult.hasErrors()) {
            return "accountform";
        }
        
        accountService.create(account);

        return "redirect:/accounts";
    }
    
}

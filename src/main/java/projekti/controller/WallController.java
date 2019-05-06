package projekti.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import projekti.service.AuthService;
import projekti.service.AccountService;
import projekti.service.WallService;
import projekti.model.Account;
import projekti.model.Wall;
import projekti.model.Comment;

@Controller
public class WallController {
    
    @Autowired
    private AccountService accountService;
    
    @Autowired
    private AuthService authService;
    
    //secure this method
    //show current users personal wall
    @GetMapping("/wall")
    public String currentLoggedInAccountWall(Model model){

        // get username from authentication
        String username = authService.getCurrentAuthedAccountUsername();
        
        return "redirect:/wall/" + username;
    }
    
    //show wall of user defined in pathvar
    @GetMapping("/wall/{username}")
    public String personalWall(Model model, @PathVariable String username){

        Account account = accountService.findByUsername(username);
        
        //show avatar if the user has set one
        if (account.getAvatar() != null){
            Long id = account.getAvatar().getId();
            model.addAttribute("avatarId", id);
        }
        String usernameAuth = authService.getCurrentAuthedAccountUsername();
        
        //set<Account> friends = account.getFriends()
        List<Comment> comments = account.getWall().getComments();
        
        model.addAttribute("pagetitle", username);
        model.addAttribute("name", username);
        model.addAttribute("authedusername", usernameAuth);
        model.addAttribute("comments", comments);
        model.addAttribute("accounts", accountService.getFriends(account));//account.getFriends()
        model.addAttribute("isFriend", authedUserFriendsWithWall(username));
        
        return "wall";
    }
    
    // check if authed user is friends with wall's owner
    private boolean authedUserFriendsWithWall(String wall){
        boolean result = false;
        Account currentAccount = authService.getCurrentAuthedAccount();
        Account wallAccount = accountService.findByUsername(wall);
        if(accountService.isFriendOf(currentAccount, wallAccount) || accountService.hasFriend(currentAccount, wallAccount)){
            result = true;
        }
        return result;
    }
    

}

package projekti.controller;

import projekti.service.AuthService;
import projekti.model.FriendRequest;
import projekti.service.AccountService;
import projekti.dao.AccountRepository;
import projekti.model.Account;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import projekti.dao.FriendRequestRepository;

@Controller
public class FriendRequestController {
    
    @Autowired
    private AuthService authService;
    
    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private AccountService accountService;
    
    @Autowired
    private FriendRequestRepository friendRequestRepository;
    
    
    @GetMapping("/friendrequest")
    public String listFriendRequests(Model model){
        Account currentUser = authService.getCurrentAuthedAccount();
        model.addAttribute("account", currentUser);
        model.addAttribute("friendrequests_out", friendRequestRepository.findRequestsBySender(currentUser)); //getAccountfromAuth().getFriendRequests()
        model.addAttribute("friendrequests_in", friendRequestRepository.findRequestsByRecipient(currentUser));
        //
        return "friendrequest";  
    }
    
    @PostMapping("/friendrequest/{username}")
    public String sendFriendRequest(@PathVariable String username){
        
        //check that user is not sending a friendmessage to themselves
        if(username.equalsIgnoreCase(authService.getCurrentAuthedAccountUsername())){
            return "redirect:/friendrequest";
        }
        
        //new request
        FriendRequest friendRequest = new FriendRequest();
        //get current users name to use in the request as a sender
        Account sender = authService.getCurrentAuthedAccount();
        Account recipient = accountRepository.findByUsername(username);
        
        friendRequest.setRecipient(recipient);
        friendRequest.setSender(sender);
        friendRequest.setFriendRequestStatus(false);
        friendRequest.setTimestamp(LocalDateTime.now());
        
        accountRepository.getOne(authService.getCurrentAuthedAccount().getId()).getFriendRequests().add(friendRequest);
        friendRequestRepository.save(friendRequest);
        return "redirect:/friendrequest";
    }
    
    
    // show friendrequest by id, 
    @GetMapping("/friendrequest/{id}/manage")
    public String showFriendRequest(Model model, @PathVariable Long id){
        FriendRequest fr = friendRequestRepository.getOne(id);
        model.addAttribute("user", fr.getRecipient().getUsername());
        model.addAttribute("friendrequest", fr);
        return "friendrequest"; 
    }
    
    @Transactional
    @PostMapping("/friendrequest/{id}/manage")
    public String modifyFriendRequest(@PathVariable Long id, @RequestParam boolean value){
        FriendRequest friendRequest = friendRequestRepository.findById(id).get();
        
        //only recipient should be able to accept
        if (authService.getCurrentAuthedAccount() != friendRequest.getRecipient()){
            return "redirect:/friendrequest"; 
        }
        
        //if value is false, delete request
        if(value == false){
            //friendRequestRepository.deleteById(id);
            friendRequest.setFriendRequestStatus(value);
            friendRequestRepository.delete(friendRequest);
        }
        // if true add friend relation
        else if(value = true){
            // once accepted add sender and receiver into the others friendlist
            Account sender = friendRequest.getSender();
            Account recipient = friendRequest.getRecipient();
            // create friend relations
            accountService.addFriend(sender, recipient);
            friendRequest.setFriendRequestStatus(value);
        }
        
        return "redirect:/friendrequest"; 
    }
}

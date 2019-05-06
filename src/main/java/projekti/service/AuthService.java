package projekti.service;

import projekti.dao.AccountRepository;
import projekti.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projekti.IAuthenticationFacade;

@Service
public class AuthService {
    
    @Autowired
    private IAuthenticationFacade authenticationFacade;
    
    @Autowired
    private AccountRepository accountRepository;
    
    private Account getAccount(){
        //get account with username from auth
        return accountRepository.findByUsername(authenticationFacade.getAuthentication().getName());
    }
    
    public Account getCurrentAuthedAccount(){
        
        return getAccount();
    }
    
    public String getCurrentAuthedAccountUsername(){
        //get account with username from auth
        return getAccount().getUsername();
    }
}

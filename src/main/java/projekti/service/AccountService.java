package projekti.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import projekti.model.Account;
import projekti.model.Wall;
import projekti.model.Album;
import projekti.dao.AlbumRepository;
import projekti.dao.WallRepository;
import projekti.dao.AccountRepository;

@Service
public class AccountService {
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private AlbumRepository albumRepository;
    
    @Autowired
    private WallRepository wallRepository;
    
    
    
    @Transactional(readOnly = true)
    public List<Account> findAll() {
        return accountRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    public Account findById(Long id) {
        return accountRepository.getOne(id);
    }
    
    public Account findByUsername(String username){
        return accountRepository.findByUsername(username);
    }
    
    public boolean hasFriend(Account account, Account friend){
        return account.hasFriend(friend);
        //return accountRepository.hasFriend(account);
    }
    
    public boolean isFriendOf(Account account, Account friend){
        return account.isFriendOf(friend);
        //return accountRepository.isFriendOf(friend);
    }
    
    //@Transactional(readOnly = true)
    public Set<Account> getFriends(Account account) {
        Set<Account> allFriends = new HashSet<>();
        allFriends.addAll(accountRepository.findFriends(account));
        allFriends.addAll(accountRepository.findFriendOf(account));
        return allFriends;
    }
    
    public Page<Account> findFriend(Account account, String searchTerm, Pageable pageRequest) {
          return accountRepository.findFriendsPagify(account, searchTerm, pageRequest);
    }
    
    //@Transactional
    public void addFriend(Account person, Account friend) {
        if (!person.hasFriend(friend)) {
            person.addFriend(friend);
        }
    }
    
    //@Transactional
    public void removeFriend(Account person, Account friend) {
        if (person.hasFriend(friend)) {
            person.removeFriend(friend);
        }
    }
    
    public Account create(Account account){
        
        // crypt password before saving account
        // is this weak for mim-attack as pw gets here in text in object
        String cryptedpw = passwordEncoder.encode(account.getPassword());
        account.setPassword(cryptedpw);
        
        // add user auth for every account
        List<String> authorities = new ArrayList<>();
        authorities.add("USER");
        account.setAuthorities(authorities);
        
        //save modified account data
        accountRepository.save(account);
        
        //Create album, wall etc here for account
        Album album = new Album();
        //album.setAccount(account);
        albumRepository.save(album);
        
        //create wall
        Wall wall = new Wall();
        wallRepository.save(wall);
        
        //link album
        account.setAlbum(album);
        account.setWall(wall);
        accountRepository.save(account);
        
        return accountRepository.save(account);
    }
}

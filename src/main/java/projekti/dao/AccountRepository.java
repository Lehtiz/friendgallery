package projekti.dao;

import java.util.List;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import projekti.model.FriendRequest;
import projekti.model.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
    
    Account findByUsername(String username);
    List<FriendRequest> getFriendRequestsByUsername(String username);
    List<Account> getFriendsByUsername(String username);
    
    @Query("SELECT p FROM Account p " +
			"WHERE ?1 MEMBER OF p.friendOf " +
			"   AND LOWER(p.username) LIKE LOWER(CONCAT('%', ?2, '%')) " +
			"ORDER BY p.username")
    Page<Account> findFriendsPagify(Account person, String searchTerm, Pageable pageRequest);
    
    
    @Query("SELECT p FROM Account p " +
			"WHERE ?1 MEMBER OF p.friends " +
			"ORDER BY p.username")
    Set<Account> findFriendOf(Account person);
    
    @Query("SELECT p FROM Account p " +
			"WHERE ?1 MEMBER OF p.friendOf " +
			"ORDER BY p.username")
    Set<Account> findFriends(Account person);
    
}

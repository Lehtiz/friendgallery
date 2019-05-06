package projekti.dao;

import projekti.model.FriendRequest;
import projekti.model.Account;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
    List<FriendRequest> findRequestsBySender(Account sender);
    List<FriendRequest> findRequestsByRecipient(Account recipient);
}

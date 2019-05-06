package projekti.dao;

import projekti.model.Vote;
import projekti.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    Vote findByAccount(Account account);
}

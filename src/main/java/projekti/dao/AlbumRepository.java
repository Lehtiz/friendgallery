package projekti.dao;

import projekti.model.Album;
import projekti.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<Album, Long> {
    //Album findByAccount(Account account);
}

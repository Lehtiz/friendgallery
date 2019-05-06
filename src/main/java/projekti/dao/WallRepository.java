package projekti.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import projekti.model.Wall;


public interface WallRepository extends JpaRepository<Wall, Long> {
    
    //Wall findWallByUsername(String username);
}

package projekti.dao;

import projekti.model.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    //List<Comment> findByPictureId(Long pictureId);
    
    @EntityGraph(attributePaths = {"account"})
    @Override
    List<Comment> findAll();
}

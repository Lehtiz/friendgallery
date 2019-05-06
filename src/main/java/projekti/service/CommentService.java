package projekti.service;

import projekti.dao.CommentRepository;
import projekti.model.Picture;
import projekti.model.Comment;
import projekti.dao.AccountRepository;
import projekti.model.Account;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import projekti.dao.PictureRepository;
import projekti.dao.WallRepository;

@Service
public class CommentService {
    
    @Autowired
    private CommentRepository commentRepository;
    
    @Autowired
    private PictureRepository pictureRepository;
    
    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private WallRepository wallRepository;
    
    
    public List<Comment> listCommentsforPicture(Long pictureId){
        //return commentRepository.findByPictureId(pictureId);
        return pictureRepository.getOne(pictureId).getComments();//pictureRepository.findById(pictureId).get().getComments();
    }
    
    public List<Comment> listCommentsforWall(String username){
        Long wallid = accountRepository.findByUsername(username).getWall().getId();
        return wallRepository.getOne(wallid).getComments();
        //return accountRepository.findByUsername(username).getWall().getComments();
    }
    
    public void addCommentToPicture(Long pictureId, Comment comment){
        
        comment.setTimestamp(LocalDateTime.now());
        commentRepository.save(comment);
        
        //add comment to pictures comment list
        Picture pic = pictureRepository.findById(pictureId).get();
        pic.getComments().add(comment);
        //pictureRepository.findById(pictureId).get().getComments().add(comment);
        pictureRepository.save(pic);
    }
 
    public void addCommentToWall(String username, Comment comment){
        
        comment.setTimestamp(LocalDateTime.now());
        commentRepository.save(comment);
        
        //add comment to wall's comment list
        Account acc = accountRepository.findByUsername(username);
        acc.getWall().getComments().add(comment);
        accountRepository.save(acc);
    }
    
    public int getVotesCount(Long id){
        return commentRepository.getOne(id).getVotes().size();
    }
}

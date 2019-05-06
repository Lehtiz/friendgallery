package projekti.service;

import projekti.dao.CommentRepository;
import projekti.service.AuthService;
import projekti.model.Vote;
import projekti.model.Picture;
import projekti.model.Comment;
import projekti.model.Account;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projekti.dao.PictureRepository;
import projekti.dao.VoteRepository;

@Service
public class VoteService {
    
    @Autowired
    private AuthService authService;
    
    @Autowired
    private VoteRepository voteRepository;
    
    @Autowired
    private PictureRepository pictureRepository;
    
    @Autowired
    private CommentRepository commentRepository;
    
    //transactional breaks temp solution
    //@Transactional 
    public void upvotePicture(Long pictureId){
        
        // temp solution to "user can only vote once" fix later
        // error calcels voting again
        try {
            
            Account acc = authService.getCurrentAuthedAccount();
            Picture pic = pictureRepository.getOne(pictureId);
            
            Vote vote = new Vote();

            vote.setTimestamp(LocalDateTime.now());
            vote.setAccount(acc);
            //if user has already voted ignore
            voteRepository.save(vote);

            // Add vote to Pictures votes list
            pic.getVotes().add(vote);
            pictureRepository.save(pic);
        }
        catch(Exception e) {
            //  Block of code to handle errors
        }
    }
    
    public void upvoteComment(Long commentId){
        
        // temp solution to "user can only vote once" fix later
        // error calcels voting again
        try {
            
            Account acc = authService.getCurrentAuthedAccount();
            Comment com = commentRepository.getOne(commentId);
            
            Vote vote = new Vote();

            vote.setTimestamp(LocalDateTime.now());
            vote.setAccount(acc);
            //if user has already voted ignore
            voteRepository.save(vote);

            // Add vote to Comments votes list
            com.getVotes().add(vote);
            commentRepository.save(com);
        }
        catch(Exception e) {
            //  Block of code to handle errors
        }
    }
    
}
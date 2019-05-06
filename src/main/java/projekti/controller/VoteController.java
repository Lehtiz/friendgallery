package projekti.controller;

import projekti.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class VoteController {
    
    @Autowired
    private VoteService voteService;
    
    @PostMapping("/picture/{pictureId}/upvote")
    public String upvotePicture(@PathVariable Long pictureId){
        voteService.upvotePicture(pictureId);
        return "redirect:/picture/" + pictureId;
    }
    
    
    //@PostMapping("/comment/{commentId}/upvote")
    @GetMapping("/comment/{commentId}/upvote")
    public String upvoteComment(@PathVariable Long commentId){
        voteService.upvoteComment(commentId);
        return "redirect:/";
    }
}

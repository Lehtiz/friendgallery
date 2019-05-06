package projekti.controller;

import projekti.service.AuthService;
import projekti.model.Comment;
import projekti.model.Account;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import projekti.service.CommentService;
import projekti.dao.PictureRepository;

@Controller
public class CommentController {
    
    @Autowired
    private AuthService authService;
    
    @Autowired
    private CommentService commentService;
    
    @Autowired
    private PictureRepository pictureRepository;
    
    @GetMapping(path="/picture/{pictureId}/comments", produces="application/json")
    @ResponseBody
    public List<Comment> getComments(@PathVariable Long pictureId){
        //return pictureRepository.getOne(pictureId).getComments();
        //model.addAttribute("comments", commentService.listComments(pictureId));
        return commentService.listCommentsforPicture(pictureId);
    }
    // Comment a picture
    @PostMapping("/pictures/{pictureId}/comment/add")
    public String addComment(
            @PathVariable Long pictureId, 
            @RequestParam String commentBody){
        Comment comment = new Comment();
        
        
        comment.setAccount(authService.getCurrentAuthedAccount());
        comment.setContent(commentBody);
        commentService.addCommentToPicture(pictureId, comment);
        return "redirect:/picture/" + pictureId;
    }
    
    ////
    // wall
    ////
    
    @GetMapping(path="/wall/{wall}/comments", produces="application/json")
    @ResponseBody
    public List<Comment> getWallComments(@PathVariable String wall){
        return commentService.listCommentsforWall(wall);
    }
    
    // Comment an accounts wall
    //@Transactional
    @PostMapping("/wall/{username}/comment/add")
    public String sendMessage(
            @PathVariable String username, 
            @RequestParam String commentBody){
        //new comment
        Comment comment = new Comment();
        //get poster account from auth
        Account account = authService.getCurrentAuthedAccount();
        
        // fill data and save
        comment.setAccount(account);
        comment.setContent(commentBody);
        commentService.addCommentToWall(username, comment);
        
        return "redirect:/wall/" + username;
    }
}

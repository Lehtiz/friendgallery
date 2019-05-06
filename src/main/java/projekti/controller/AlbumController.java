package projekti.controller;

import projekti.model.Album;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import projekti.service.AuthService;
import projekti.service.PictureService;

@Controller
public class AlbumController {
    
    @Autowired
    private AuthService authService;
    
    @Autowired
    private PictureService pictureService;
    
    // show album for current user
    @GetMapping("/album")
    public String getAlbumOfAuthedUser(Model model){
        return "redirect:/album/" + authService.getCurrentAuthedAccountUsername();
    }
    
    // show album of pictures
    @GetMapping("/album/{username}")
    public String getAlbumWithUsername(Model model, @PathVariable String username){
        
        model.addAttribute("owner", username);
        
        Album album = pictureService.getAlbum(username);
        
        long count = album.getPictureList().size();
        model.addAttribute("pagetitle", "Album of " + username);
        model.addAttribute("count", count);
        model.addAttribute("pictures", album.getPictureList());
        return "album";
    }
}

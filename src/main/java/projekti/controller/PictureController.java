package projekti.controller;

import projekti.dao.PictureRepository;
import projekti.service.PictureService;
import projekti.service.AuthService;
import projekti.model.Picture;
import projekti.dao.AccountRepository;
import projekti.model.Account;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class PictureController {
    
    @Autowired
    private AuthService authService;
    
    @Autowired
    private PictureRepository pictureRepository;
    
    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private PictureService pictureService;
    
    @GetMapping("/picture/add")
    public String showPictureForm(){
        return "pictureform";
    }
    
    //show a single picture
    @GetMapping("/picture/{id}")
    public String getPicture(Model model, @PathVariable Long id){
        Picture pic = pictureService.getPicture(id);//pictureRepository.findById(id).get();
        model.addAttribute("current", id);
        model.addAttribute("desc", pic.getDescription());
        model.addAttribute("title", pic.getTitle());
        model.addAttribute("comments", pic.getComments());
        model.addAttribute("votes", pictureService.getVotesCount(id));
        return "picture";
    }
    
    // save picture ref as accounts avatar
    @GetMapping("/picture/{id}/setavatar")
    public String setAvatar(@PathVariable Long id){
        Account account = authService.getCurrentAuthedAccount();
        
        account.setAvatar(pictureRepository.getOne(id));
        accountRepository.save(account);
        
        return "redirect:/wall";
    }
    
    // get raw picture data from db
    @GetMapping("/picture/{id}/content")
    @ResponseBody
    public byte[] getPictureContent(@PathVariable Long id){
        return pictureRepository.getOne(id).getContent();
    }
    
    // delete picture //and return deleted picture object
    @DeleteMapping(path="/picture/{id}", produces="application/json")
    @ResponseBody
    public String deletePicture(@PathVariable Long id){
        //Picture picture = pictureRepository.getOne(id);
        pictureRepository.deleteById(id);
        return "redirect:/album";
    }
    
    // TODO edit picture
    @GetMapping("/picture/{id}/edit")
    public String editPicture(Model model, @PathVariable Long id){
        //check if user is owner
        //set tag in model to show edit functions
        
        model.addAttribute("edit", true);
        Picture pic = pictureRepository.findById(id).get();
        model.addAttribute("current", id);
        model.addAttribute("desc", pic.getDescription());
        model.addAttribute("title", pic.getTitle());

        return "pictureform";
    }
    
    @PostMapping("/savepic")
    public String savePicture(
            @RequestParam("file") MultipartFile file,
            @RequestParam("description") String description,
            @RequestParam("title") String title) throws IOException {
        
        // allowed types set
        Set<String> types = new HashSet<>();
        types.add("image/png");
        types.add("image/jpg");
        types.add("image/gif");
        types.add("image/bmp");
        
        //save file to db if of an allowed type
        if(types.contains(file.getContentType())){ //file.getContentType().equals("image/png")
            Picture pic = new Picture();
            pic.setTitle(title.trim());
            pic.setDescription(description.trim());
            pic.setContent(file.getBytes());
            pictureService.savePicture(pic);
        }
        return "redirect:/album"; 
    }
}

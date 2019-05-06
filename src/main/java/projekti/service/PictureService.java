package projekti.service;

import projekti.service.AuthService;
import projekti.dao.AlbumRepository;
import projekti.model.Picture;
import projekti.model.Album;
import projekti.dao.AccountRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projekti.dao.PictureRepository;

@Service
public class PictureService {
    @Autowired
    private AuthService authService;
    
    @Autowired
    private PictureRepository pictureRepository;
    
    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private AlbumRepository albumRepository;
    
    //saves picture object and creates relations to album and 
    public void savePicture(Picture picture){
        picture.setTimestamp(LocalDateTime.now());
        pictureRepository.save(picture);
        
        //add picture owner via album
        Album album = authService.getCurrentAuthedAccount().getAlbum();

        //link picture with album and user
        List<Picture> picturesList = album.getPictureList();
        picturesList.add(picture);

        album.setPictureList(picturesList);
        albumRepository.save(album);
    }
    
    public Album getAlbum(String username){
        return accountRepository.findByUsername(username).getAlbum();
    } 
    
    public int getVotesCount(Long id){
        return pictureRepository.getOne(id).getVotes().size();
    }
    
    public Picture getPicture(Long id){
        return pictureRepository.findById(id).get();
    }
    
    
}

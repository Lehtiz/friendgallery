package projekti.service;

import org.springframework.beans.factory.annotation.Autowired;

import projekti.dao.WallRepository;
import projekti.model.Wall;

public class WallService {
    
    @Autowired
    private WallRepository wallRepository;
    
    //public Wall findWallByUsername(String username){
    //    return wallRepository.findWallByUsername(username);
    //}
}

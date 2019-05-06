package projekti.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@NoArgsConstructor @AllArgsConstructor @Data 
@Entity
public class Album extends AbstractPersistable<Long> {
    
    // one album holds many pictures
    @OneToMany(fetch = FetchType.LAZY)
    private List<Picture> pictureList = new ArrayList<>();

}

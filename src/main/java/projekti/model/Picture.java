package projekti.model;

// lombok constructor / getset helpers

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@NoArgsConstructor @AllArgsConstructor @Data 
@Entity
public class Picture extends AbstractPersistable<Long> {
    
    // picture data
    @Lob
    private byte[] content;
    
    // description for the image
    //
    @Size(max = 400)
    private String description;
    
    //@NotEmpty
    @Size(max = 50)
    private String title;
    
    private LocalDateTime timestamp;
    
    // comments attached to the picture
    // one picture can have many comments
    @OneToMany(fetch = FetchType.LAZY)
    private List<Comment> comments;
    
    @OneToMany(fetch = FetchType.LAZY)
    private List<Vote> votes = new ArrayList<>();
    //private Set<Vote> votes = new HashSet<>();

    
}

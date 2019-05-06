package projekti.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@NoArgsConstructor @AllArgsConstructor @Data 
@Entity
public class Comment extends AbstractPersistable<Long> {
    
    
    //user who commented
    // a poster can have many comments, one comment can have one owner who posted it
    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;
    
    // comment text
    @NotEmpty
    @Size(max=500)
    private String content;
    
    private LocalDateTime timestamp;
    
    @OneToMany(fetch = FetchType.LAZY)
    private List<Vote> votes = new ArrayList<>();

    
}

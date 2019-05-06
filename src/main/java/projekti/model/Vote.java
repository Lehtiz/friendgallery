package projekti.model;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@NoArgsConstructor @AllArgsConstructor @Data 
@Entity
public class Vote extends AbstractPersistable<Long> {

    
    @ManyToOne(fetch = FetchType.LAZY)
    // makes colunm unique cannot haveduplicate accounts, also gives error for now, test if this works with comment rating aswell
    // does not work as intended, once rated cannot rate anything else, find anothr way
    //@JoinColumn(unique = true)
    private Account account;
    
    //private Long likes;

    private LocalDateTime timestamp;
    
    
    
}

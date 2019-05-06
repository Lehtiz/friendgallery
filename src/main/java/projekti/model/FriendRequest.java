package projekti.model;

// lombok constructor / getset helpers

import projekti.model.Account;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@NoArgsConstructor @AllArgsConstructor @Data
//with no entity: Invocation of init method failed; nested exception is org.hibernate.AnnotationException: Use of @OneToMany or @ManyToMany targeting an unmapped class: projekti.Account.friendRequests[projekti.FriendRequest]
@Entity // lombok causes infiniloop here trying to call toString(), unless overriding toString() manually
// apparently lombok @Data should not be used with @Entity annotation for this reason
//better fix than override
@EqualsAndHashCode(exclude="recipient")
public class FriendRequest extends AbstractPersistable<Long> {
    
    private boolean friendRequestStatus;
    
    // request only has one sender
    @ManyToOne(fetch = FetchType.LAZY)
    private Account sender;
    
    // one request has one recipient
    @ManyToOne(fetch = FetchType.LAZY)
    private Account recipient;
    
    private LocalDateTime timestamp;
    
    //fix to bug mentioned above, should research more. p.sure material had @Entity with @Data all the time
    /*
    @Override
    public String toString(){
        return super.toString();
    }
    */
    
}

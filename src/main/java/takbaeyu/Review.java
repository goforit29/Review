package takbaeyu;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;

@Entity
@Table(name="Review_table")
public class Review {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private Long memberId;
    private Long qty;
    private String review;
    private Long requestId;

    @PostPersist
    public void onPostPersist(){
        Reviewed reviewed = new Reviewed();
        BeanUtils.copyProperties(this, reviewed);
        reviewed.publishAfterCommit();


    }

    @PostUpdate
    public void onPostUpdate(){
        Rerequested rerequested = new Rerequested();
        BeanUtils.copyProperties(this, rerequested);
        rerequested.publishAfterCommit();

        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

        if(this.getQty() != null && this.getQty() != 0 ) {
            takbaeyu.external.Request request = new takbaeyu.external.Request();
            request.setMemberId(this.getMemberId());
            request.setQty(this.getQty());
            request.setStatus("ReRequested");
            // mappings goes here
            ReviewApplication.applicationContext.getBean(takbaeyu.external.RequestService.class)
                    .request(request);
        }


    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }
    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }
    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }




}

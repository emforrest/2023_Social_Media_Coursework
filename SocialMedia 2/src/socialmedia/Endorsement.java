package socialmedia;

/**
 * This class extends the Post superclass, used for posts of type EndorsementPost. Has it's additional attrubte originalPostId.
 * 
 * @author Jack Skinner, Eleanor Forrest
 */
public class Endorsement extends Post{

    private int originalPostId; 

    /**
     * constructor, creates an endorsement, given a message and orignalPostID. 
     * @param message - String: Constructed and formatted in SocialMedia. the message this endorsement will have
     * @param originalPostId - int: the id of the post that this endorsement is endorsing.
     */
    public Endorsement(String message, int originalPostId) {
        //calls the superclass, to set all of it's Post attributes like it's id.
        super(message); 
        this.originalPostId = originalPostId;
        //it's postType is set to OriginalPost in the superclass, this corrects it. 
        this.postType = "EndorsementPost"; 
        
    }

    /**
     * getter, returns the attribute OriginalPostId
     * @return originalPostId - int: the ID of the post this endorsement has endorsed.
     */
    public int getOriginalPostId() {
        return originalPostId;
    }
 
}

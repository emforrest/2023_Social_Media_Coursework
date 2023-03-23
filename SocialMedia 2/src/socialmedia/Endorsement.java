package socialmedia;

/**
 * This class extends the Post superclass, used for endorsements. Has an additional attrubte originalPostId which refers to the post being endorsed
 * 
 * @author Jack Skinner, Eleanor Forrest
 */
public class Endorsement extends Post{

    private int originalPostId; 

    /**
     * constructor, creates an endorsement, given a message and orignalPostID 
     * @param message - String: the message this endorsement will have, formatted correctly in makeEndorsement()
     * @param originalPostId - int: the ID of the post that this endorsement is endorsing
     */
    public Endorsement(String message, int originalPostId) {
        super(message); 
        this.originalPostId = originalPostId;
        //the post type is set to OriginalPost in the superclass, this corrects it
        this.postType = "EndorsementPost"; 
        
    }

    /**
     * getter, returns the attribute originalPostId
     * @return originalPostId - int: the ID of the post this endorsement is endorsing
     */
    public int getOriginalPostId() {
        return originalPostId;
    }
 
}

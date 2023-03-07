package socialmedia;

public class Endorsement extends Post{

    private int originalPostId; 

    public Endorsement(String message, int originalPostId) {
        super(message); 
        this.originalPostId = originalPostId;
        this.postType = "EndorsementPost"; 
        
    }

    //object getters 
    public int getOriginalPostId() {
        return originalPostId;
    }
 
}

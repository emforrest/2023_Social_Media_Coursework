package socialmedia;

public class Endorsement extends Post{
    private static int NO_OF_ENDORSEMENTS = 0;
    private int originalPostId; 

    public Endorsement(String message, int originalPostId) {
        super(message); 
        NO_OF_ENDORSEMENTS++;
        this.originalPostId = originalPostId;
        this.postType = "EndorsementPost"; 
    }
 
}

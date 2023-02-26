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

    //static method to get the number of endorsements 
    public static int getNO_OF_ENDORSEMENTS() {
        return NO_OF_ENDORSEMENTS;
    }

    //object getters 
    public int getOriginalPostId() {
        return originalPostId;
    }
 
}

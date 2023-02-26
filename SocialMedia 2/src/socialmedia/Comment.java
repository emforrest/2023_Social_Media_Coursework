package socialmedia;

public class Comment extends Post{
    private static int NO_OF_COMMENTS = 0; 
    private int originalPostID; 

    public Comment(String message, int originalPostID) {
    super(message);
    NO_OF_COMMENTS++;
    this.originalPostID = originalPostID;
    this.postType = "CommentPost";
    }

    public static int getNO_OF_COMMENTS() {
        return NO_OF_COMMENTS;
    }
    //object getters
    public int getOriginalPostID() {
        return originalPostID;
    }
}


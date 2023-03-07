package socialmedia;

public class Comment extends Post{
    private int originalPostID; 

    public Comment(String message, int originalPostID) {
    super(message);
    this.originalPostID = originalPostID;
    this.postType = "CommentPost";
    }

    //object getters
    public int getOriginalPostID() {
        return originalPostID;
    }

}


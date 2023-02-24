package socialmedia;

public class Comment extends Post{
    public static int NO_OF_COMMENTS = 0; 
    public int originalPostID; 
}

public Comment(String message, int originalPostID) {
    super(message);
    NO_OF_COMMENTS++;
    this.originalPostID = originalPostID;
    this.postType = "CommentPost";
}


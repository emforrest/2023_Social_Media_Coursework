package socialmedia;

/** 
 * This class extends the Post superclass, used for posts of type CommentPost. Has it's additional attrubte originalPostId.
 * 
 * @author Jack Skinner, Eleanor Forrest
 */

public class Comment extends Post{
    private int originalPostID; 

    /**
     * constructor, creates an endorsement, given a message and orignalPostID. 
     * @param message - String: the message that the comment will have.
     * @param originalPostID int: The postid of the post this comment is commenting on. 
     */
    public Comment(String message, int originalPostID) {
    super(message);
    this.originalPostID = originalPostID;
    // as in endrosement, the posttype is set to OriginalPost in the superclass, this corrects it.
    this.postType = "CommentPost";
    }

    /**
     * getter, returns the attribute OriginalPostId
     * @return originalPostId - int: the ID of the post this comment has commented on.
     */
    public int getOriginalPostID() {
        return originalPostID;
    }

}


package socialmedia;

/** 
 * This class extends the Post superclass, used for comments. Has an additional attrubte originalPostId which refers to the post being commented on
 * 
 * @author Jack Skinner, Eleanor Forrest
 */

public class Comment extends Post{
    private int originalPostID; 

    /**
     * constructor, creates a comment, given a message and orignalPostID
     * @param message - String: the message that the comment will have
     * @param originalPostID int: The ID of the post this comment is commenting on
     */
    public Comment(String message, int originalPostID) {
    super(message);
    this.originalPostID = originalPostID;
    //as in Endorsement, the post type is set to OriginalPost in the superclass, this corrects it
    this.postType = "CommentPost";
    }

    /**
     * getter, returns the attribute originalPostId
     * @return originalPostId - int: the ID of the post this comment has commented on
     */
    public int getOriginalPostID() {
        return originalPostID;
    }

}


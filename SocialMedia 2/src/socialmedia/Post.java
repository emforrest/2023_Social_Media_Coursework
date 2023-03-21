package socialmedia;
import java.io.Serializable;
/**
 * The class that contains Post objects, which is a superclass for Endorsement and Comment objects. Objects have an ID, 
 * a message, a Type, their number of endorsements and number of comments. The class has the attribute NO_OF_POSTS, which is used to create IDs.
 * 
 * @author Jack Skinner, Eleanor Forrest
 */

 //attributes.
public class Post implements Serializable{
    //NO_OF_POSTS is not the number of posts in the platoform, it's the number of posts ever created. used for IDs
    private static int NO_OF_POSTS = 0; 
    protected int postId; 
    protected String message; 
    protected String postType = "OriginalPost"; 
    protected int numberOfEndorsements = 0;
    protected int numberOfComments = 0;

    /**
     * The constructor for the post class
     * @param message - String: The message that the post will contain
     */ 
    public Post(String message) {
        this.message = message; 
        // The ID of the post is set as how many posts were created before it. 
        postId = ++NO_OF_POSTS; 
    }

    /**
     * getter, returns the total number of posts ever created.
     * @return NO_OF_POSTS - int: number of posts ever created
     */
    public static int getNO_OF_POSTS() {
        return NO_OF_POSTS;
    }

    /**
     * getter, returns the message of a post
     * @return message - String: the message of the post
     */
    public String getMesssage() {
        return message;
    }

    /** 
     * getter, returns the id of the post
     * @return postId - int: the id of the post
     */
    public int getId(){
        return postId;
    }

    /**
     * getter, returns the type of the post (Original, Comment, Endorsement or Deleted)
     * @return postType - String: the type of the post
     */
    public String getPostType(){
        return postType;
    }

    /**
     * getter, returns the number of Endorsements a post has recived 
     * @return numberOfEndorsements - int: the number of endorsements recived by a post
     */
    public int getNumberOfEndorsements(){
        return numberOfEndorsements;
    }

    /**
     * getter, returns the number of comments a post has recived
     * @return numberOfComments - int: the number of comments a post has recived
     */
    public int getNumberOfComments(){
        return numberOfComments;
    }

    /**
     * setter, sets the message of a post to the parameter
     * @param message - String: the new message the post will have
     *
     */
    public void setMessage(String message){
        this.message = message;
    }

    /**
     * setter, sets the post type to the posts updated type - used when a comment is deleted but stored in deletedComments
     * @param postType - String: the updated postype of the Post
     */
    public void setPostType(String postType){
        this.postType = postType;
    }

    /**
     * void, when a post is endorsed, this is called to increment number of endorsements.
     */
    public void addEndorsement(){
        numberOfEndorsements += 1;
    }

    /**
     * void, used when an endorsement is deleted, decrements the number of endorsements.
     */
    public void removeEndorsement(){
        numberOfEndorsements -= 1;
    }

    /**
     * void, used when a post recives a comment, increments the number of comments.
     */
    public void addComment(){
        numberOfComments += 1;
    }

    /**
     * void, used when a post's comment is deleted, decrements the number of comments.
     */
    public void removeComment(){
        numberOfComments -= 1;
    }

    /**
     * void, called when the platofrm is erased. This resets the NO_OF_POSTS to 0 so ids start from 1.
     */
    public static void reset(){
        NO_OF_POSTS = 0;
    }

    /**
     * setter, when the platform is loaded from a file, the NO_OF_POSTS is saved so IDS are incremented correctly. 
     * This function sets the NO_OF_POSTS to that updated attribute.
     * @param i - Integer: the number of posts that the platform had when saved, stores it when loaded.
     */
    public static void setNO_OF_POSTS(Integer i){
        NO_OF_POSTS = i;
    }
}

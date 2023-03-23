package socialmedia;

//Imports
import java.io.Serializable; //allows the state of the platform to be saved as a byte stream

/**
 * The class that contains Post objects, which is a superclass for Endorsement and Comment objects. Objects have an ID, message, type, number of endorsements and number of comments. The class has the attribute NO_OF_POSTS, which is used to create IDs
 * 
 * @author Jack Skinner, Eleanor Forrest
 */

public class Post implements Serializable{
    //Attributes
    private static int NO_OF_POSTS = 0; //NO_OF_POSTS is the total number of posts created rather than the number of posts currently in the platform, used for generating unique IDs
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
        //The ID of the post is set to how many posts were created before it 
        postId = ++NO_OF_POSTS; 
    }

    /**
     * void method, used when a post is endorsed  to increment its number of endorsements
     */
    public void addEndorsement(){
        numberOfEndorsements += 1;
    }

    /**
     * void method, used when an endorsement is deleted to decrement its number of endorsements
     */
    public void removeEndorsement(){
        numberOfEndorsements -= 1;
    }

    /**
     * void method, used when a post receives a comment to increment its number of comments
     */
    public void addComment(){
        numberOfComments += 1;
    }

    /**
     * void method, used when a post's comment is deleted to decrement its number of comments
     */
    public void removeComment(){
        numberOfComments -= 1;
    }

    /**
     * void method, called when the platform is erased. Resets NO_OF_POSTS to 0 so IDs start from 1
     */
    public static void reset(){
        NO_OF_POSTS = 0;
    }

    /**
     * getter, returns the total number of posts created on the platform
     * @return NO_OF_POSTS - int: number of posts ever created
     */
    public static int getNO_OF_POSTS() {
        return NO_OF_POSTS;
    }

    /**
     * getter, returns a post's message
     * @return message - String: the message of the post
     */
    public String getMesssage() {
        return message;
    }

    /** 
     * getter, returns a post's ID
     * @return postId - int: the ID of the post
     */
    public int getId(){
        return postId;
    }

    /**
     * getter, returns a post's type (Original, Comment, Endorsement or Deleted)
     * @return postType - String: the type of the post
     */
    public String getPostType(){
        return postType;
    }

    /**
     * getter, returns the number of endorsements a post has received 
     * @return numberOfEndorsements - int: the number of endorsements received by a post
     */
    public int getNumberOfEndorsements(){
        return numberOfEndorsements;
    }

    /**
     * getter, returns the number of comments a post has received
     * @return numberOfComments - int: the number of comments received by a post
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
     * setter, sets the post type to a new type, used when a comment is deleted and stored in deletedComments
     * @param postType - String: the type the post should be updated to
     */
    public void setPostType(String postType){
        this.postType = postType;
    }

    /**
     * setter, when the platform is loaded from a file, NO_OF_POSTS is saved so IDS are incremented correctly
     * @param no - Integer: the number of posts that the platform had when saved
     */
    public static void setNO_OF_POSTS(Integer no){
        NO_OF_POSTS = no;
    }
}

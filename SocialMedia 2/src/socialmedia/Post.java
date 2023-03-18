package socialmedia;
import java.io.Serializable;
/**
 * The class that contains Post objects, which is a superclass for Endorsement and Comment objects, 
 * 
 */

public class Post implements Serializable{
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

    public void setMessage(String message){
        this.message = message;
    }

    public void setPostType(String postType){
        this.postType = postType;
    }

    public void addEndorsement(){
        numberOfEndorsements += 1;
    }

    public void removeEndorsement(){
        numberOfEndorsements -= 1;
    }

    public void addComment(){
        numberOfComments += 1;
    }

    public void removeComment(){
        numberOfComments -= 1;
    }

    public static void reset(){
        NO_OF_POSTS = 0;
    }

    public static void setNO_OF_POSTS(Integer i){
        NO_OF_POSTS = i;
    }
}

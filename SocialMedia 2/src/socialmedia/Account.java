package socialmedia;
import java.util.ArrayList;
import java.util.Iterator;
import java.io.Serializable;
/**
 * The class that contains all of the user account objects. These consist of a unique ID, a handle, a description, an arraylist of posts, comments
 * as well as endorsements 
 * 
 * @author Jack Skinner, Eleanor Forrest
 */

public class Account implements Serializable {
    // declare all the attributes
    private static int NO_OF_ACCOUNTS = 0; 
    private int id;
    private String handle; 
    private String description; 
    private int noOfEndorsements = 0;
    // We create an array list using the post, comment and endorsement classes for all of the posts linked to the account object. 
    private ArrayList<Post> posts = new ArrayList<>();

    /**
     * The first constructor for the account does not set a description and instead intitalises it to an empty string
     * 
     *  @param handle - String: the handle of the account that the user wishes to create
     * 
     * */
    public Account(String handle) {
        this.handle = handle;
        this.id = ++NO_OF_ACCOUNTS;
        this.description = ""; 
    }

    /**
     * The second constructor for the account takes the description as a parameter as well as the handle
     * 
     *  @param handle - String: the handle of the account that the user wishes to create
     *  @param description - String: the description for the account that the user wishes to create
     * 
     * */
    public Account(String handle, String description) {
        this.handle = handle; 
        this.id = ++NO_OF_ACCOUNTS;
        this.description = description;
    }
    
    /**
     * Create a Post object associated with this account, adding it to the list of posts
     * 
     * @param message - String: the message that the post should contain
     * 
     * */
    public int makePost(String message) {
        Post p = new Post(message);
        posts.add(p); 
        return p.getId();
    }

    public int makeComment(int originalId, String message) {
        Comment c = new Comment(message, originalId); 
        posts.add(c); 
        return c.getId();
    }

    public int makeEndorsement(int originalId, String originalHandle, String orignalMessage) {
        String message = ("EP@" + originalHandle + ": " + orignalMessage);
        Endorsement e = new Endorsement(message, originalId); 
        posts.add(e);
        return e.getId();
    }
    
    //define the static method to get the number of accoutns
    public static int getNO_OF_ACCOUNTS() {
        return NO_OF_ACCOUNTS;
    }
    
    //define the getters 
    public String getHandle() {
        return handle;
    }

    public int getId() {
        return id;
    }

    public void endorsed() {
        noOfEndorsements += 1;
    }

    public String getDescription() {
        return description;
    }

    //define the setters 
    public void setHandle(String handle) {
        this.handle = handle;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean hasPost(int id) {
        for (Post p : posts){
            if (p.getId() == id){
                return true;
            }
        }
        return false;
    }

    public Post getPost(int id) throws PostIDNotRecognisedException{
        for (Post p : posts){
            if (p.getId() == id){
                return p;
            }
        }
        throw new PostIDNotRecognisedException();
    }

    public void deletePost(int id) throws PostIDNotRecognisedException{
        Iterator<Post> itr = posts.iterator();
		while (itr.hasNext()) {
			Post p = (Post)itr.next();
            if (p.getId() == id){
                p.setMessage("The original content was removed from the system and is no longer available.");
                p.setPostType("DeletedPost");
                itr.remove();
                return;
            }
            
            }
        throw new PostIDNotRecognisedException();
    }
    
    public void deleteAllPosts() {
        Iterator<Post> itr = posts.iterator();
		while (itr.hasNext()) {
			Post p = (Post)itr.next();
            p.setMessage("The original content was removed from the system and is no longer available.");
            p.setPostType("DeletedPost");
            itr.remove();            
        }
    }

    public int getNoOfPosts() { 
        return posts.size();
    }

    public int getNoOfEndorsements() {
        return noOfEndorsements; 
    }

    public ArrayList<Comment> getComments() {
        ArrayList<Comment> comments = new ArrayList<>();
        for (Post p : posts) {
            if (p.getPostType().equals("CommentPost")) {
                Comment c = (Comment)p;
                comments.add(c);
            }
        }
        return comments;
    }

    public ArrayList<Post> getPosts(){
        return posts;
    }

    public static void reset(){
        NO_OF_ACCOUNTS = 0;
    }
    public static void setNO_OF_ACOUNTS(Integer i) {
        NO_OF_ACCOUNTS = i;
    }
}

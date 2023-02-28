package socialmedia;
import java.util.ArrayList;
/**
 * The class that contains all of the user account objects. These consist of a unique ID, a handle, a description, an arraylist of posts, comments
 * as well as endorsements 
 */
public class Account {
    // declare all the attributes
    private static int NO_OF_ACCOUNTS = 0; 
    private int id;
    private String handle; 
    private String description; 
    // We create an array list using the post, comment and endorsement classes for all of the posts linked to the account object. 
    private ArrayList<Post> posts = new ArrayList<>();

    //declare our constructors, overloading for accounting if a description is provided or not, defaulting description to a blank string. 
    public Account(String handle) {
        this.handle = handle;
        this.id = ++NO_OF_ACCOUNTS;
        this.description = ""; 
    }
    public Account(String handle, String description) {
        this.handle = handle; 
        this.id = ++NO_OF_ACCOUNTS;
        this.description = description;
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

    
    public Post accessPost(int id) throws PostIDNotRecognisedException{
        for (int i = 0; i <posts.size(); i++){
            if (posts.get(i).getId() == id){
                return posts.get(i);
            }
        }
        throw new PostIDNotRecognisedException();
    }

    public void addPost(String message){
        Post post = new Post(message);
        posts.add(post);
    }

    public void deletePost(int id) throws PostIDNotRecognisedException{
        for (int i = 0; i <posts.size(); i++){
            if (posts.get(i).getId() == id){
                posts.get(i).setMessage("The original content was removed from the system and is no longer available.");
                posts.get(i).setPostType("DeletedPost");
                posts.remove(i);

            }
        }
        throw new PostIDNotRecognisedException();
    }
    
    public void deleteAllPosts() throws PostIDNotRecognisedException{
        for(Post p :posts){
            deletePost(p.getId());
        }
    }

}

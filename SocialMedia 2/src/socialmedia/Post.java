package socialmedia;

public class Post {
    private static int NO_OF_POSTS = 0; 
    protected int postId; 
    protected String message; 
    protected String postType = "OriginalPost"; 

    //constructor to create a post 
    public Post(String message) {
        this.message = message; 
        postId = ++NO_OF_POSTS; 
    }


    
}

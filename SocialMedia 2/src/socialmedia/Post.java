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

    //static class getter for NO_OF_POSTS attribute
    public static int getNO_OF_POSTS() {
        return NO_OF_POSTS;
    }

    //define the getters 
    public String getMesssage() {
        return message;
    }


    
}

package socialmedia;

public class Post {
    private static int NO_OF_POSTS = 0; 
    protected int postId; 
    protected String message; 
    protected String postType = "OriginalPost"; 
    protected int numberOfEndorsements = 0;
    protected int numberOfComments = 0;

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

    public int getId(){
        return postId;
    }

    public String getPostType(){
        return postType;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public void setPostType(String postType){
        this.postType = postType;
    }

    public int getNumberOfEndorsements(){
        return numberOfEndorsements;
    }

    public void addEndorsement(){
        numberOfEndorsements += 1;
    }

    public void removeEndorsement(){
        numberOfEndorsements -= 1;
    }

    public int getNumberOfComments(){
        return numberOfComments;
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
}

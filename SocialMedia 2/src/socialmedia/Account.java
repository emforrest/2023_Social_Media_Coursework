package socialmedia;

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

    


}

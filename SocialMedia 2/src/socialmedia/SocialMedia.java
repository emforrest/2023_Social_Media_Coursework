package socialmedia;

//Imports
import java.io.IOException; //thrown if there is an issue saving or loading the file
import java.util.Scanner; //used when generating the string of posts for showPostChildrenDetails()
import java.util.ArrayList; //used to store a dynamic list of objects
import java.util.Iterator; //used to iterate through ArrayList objects 
//The following imports are used to handle saving and loading the platform as a byte stream
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;

/**
 * SocialMedia is a functioning implementation of the SocialMediaPlatform interface providing the backend for this project
 * 
 * @author Jack Skinner and Eleanor Forrest
 * 
 */
public class SocialMedia implements SocialMediaPlatform {
	private ArrayList<Account> accounts = new ArrayList<>(); //contains all the Account objects that exist in the platform
	private ArrayList<Comment> deletedComments = new ArrayList<>(); //contains any Comment objects that have been deleted, so that any successive comments can still refer to them, thus preventing them from being removed by the garbage collector


	/**
	 * returns an account given it's handle. Throws HandleNotRecognisedException if the handle isn't saved in accounts
	 * @param handle - String: The handle of the account that is being searched for
	 * @return a - Account: The account with said handle
	 * @throws HandleNotRecognisedException - Thrown if the handle is not found
	 */
	private Account returnAccount(String handle) throws HandleNotRecognisedException {
		//given an account handle, return the account object
		for(Account a : accounts) {
			if (a.getHandle().equals(handle)) {
				return a;
			}
		}
		throw new HandleNotRecognisedException(); //if the account with this handle doesn't exist
	}

	@Override
	public int createAccount(String handle) throws IllegalHandleException, InvalidHandleException {
		//check if the account handle is valid 
		if ((handle.isEmpty()) || (handle.length() > 30) || (handle.contains(" "))) {
			throw new InvalidHandleException();
		}
		//search the accounts ArrayList to see if the handle is already in use 
		for (Account a : accounts) {  
			if (a.getHandle().equals(handle)) {
				throw new IllegalHandleException();
			}
		}
		//if all checks are passed, create a new account with the verified handle
		Account newAccount = new Account(handle); 
		accounts.add(newAccount); 
		//return the ID of the new account
		return newAccount.getId();
	}	

	@Override
	public int createAccount(String handle, String description) throws IllegalHandleException, InvalidHandleException {
		//call the original createAccount() method with only the handle
		int id = createAccount(handle);
		//loop through each account in accounts, to find the account that was just created using the ID
		for (Account a : accounts) {
			if (a.getId() == id) {
				//set the description of this account to the description given in the input
				a.setDescription(description);
			}
		}
		return id;
	}

	@Override
	public void removeAccount(int id) throws AccountIDNotRecognisedException {
		//Iterator is used to iterate through the accounts ArrayList and delete items without index errors
		Iterator<Account> itr = accounts.iterator();
		while (itr.hasNext()) {
			//if the current account has the ID we are looking to delete, start deleting
			Account a = (Account)itr.next();
			if (a.getId() == id){
				//go through each post owned by this account and delete it
				ArrayList<Post> posts = a.getPosts();
				while(!posts.isEmpty()){
					Post p = posts.get(0);
					//deletePost() throws PostIDNotRecognisedException, this will never be raised however we need to handle it
					try{
						deletePost(p.getId());
					} catch (PostIDNotRecognisedException e){
		
					}
					
				}
				//set the account to null, and remove it from the iterator. It will be removed from the heap by the garbage collector
				a = null;
				itr.remove();
				return;
			}
		}
		//throw AccountIDNotRecognisedException if no account is found with the matching ID 
		throw new AccountIDNotRecognisedException();

	}

	@Override
	public void removeAccount(String handle) throws HandleNotRecognisedException {
		//get the account to be removed based on its handle. We already have a function to do this and so do not need to use an iterator
		Account account = returnAccount(handle);
		//remove the posts associated with the account similarly
		ArrayList<Post> posts = account.getPosts();
		while(!posts.isEmpty()){
			Post p = posts.get(0);
			try{
				deletePost(p.getId());
			} catch (PostIDNotRecognisedException e){

			}
			
		}
		//remove the account from accounts and set it to null
		accounts.remove(account);
		account = null;
	}

	@Override
	public void changeAccountHandle(String oldHandle, String newHandle)
			throws HandleNotRecognisedException, IllegalHandleException, InvalidHandleException {
		//find the account to change the handle of
		Account a = returnAccount((oldHandle));
		//check if the new handle is already in use by looping through each account in accounts
		for (Account b : accounts) {
			if (b.getHandle().equals(newHandle)) {
				throw new IllegalHandleException();
			}
		}
		//check that the new handle is valid
		if ((newHandle.isEmpty()) || (newHandle.length() > 30) || (newHandle.contains(" "))) {
			throw new InvalidHandleException();
		}
		//change the handle
		a.setHandle(newHandle);
	}

	@Override
	public void updateAccountDescription(String handle, String description) throws HandleNotRecognisedException {
		//find the account to be edited and set its description to the new description
		Account a = returnAccount(handle);
		a.setDescription(description);
	}

	@Override
	public String showAccount(String handle) throws HandleNotRecognisedException {
		//generate a string containing information about the requested account
		Account a = returnAccount(handle);
		String accountOut = "";
		accountOut += ("ID: " + Integer.toString(a.getId()) + " \n");
		accountOut += ("Handle: " + a.getHandle() + " \n");
		accountOut += ("Description: " + a.getDescription() + " \n"); 
		accountOut += ("Post count: " + Integer.toString(a.getNoOfPosts()) + " \n");
		accountOut += ("Endorse count: " + Integer.toString(a.getNoOfEndorsements()) + " \n"); 
		return accountOut;
	}

	@Override
	public int createPost(String handle, String message) throws HandleNotRecognisedException, InvalidPostException {
		Account a = returnAccount(handle);
		//check that the post is valid
		if ((message.isEmpty()) || (message.length() > 100)) {
			throw new InvalidPostException();
		}
		//create the post
		int postId = a.makePost(message);
		return postId;
	}

	@Override
	public int endorsePost(String handle, int id)
			throws HandleNotRecognisedException, PostIDNotRecognisedException, NotActionablePostException {
		Account endorsing = returnAccount(handle); //endorsing is the account which is endorsing a post
		//find the post to be endorsed, if it isn't found PostIDNotRecognisedException is thrown
		for (Account endorsed : accounts) { //endorsed is the account which owns the post that the endorsing account wishes to endorse
			if (endorsed.hasPost(id)) {
				//get the post to be endorsed
				Post originalPost = endorsed.getPost(id); 
				//check that this post is a post that can be endorsed, else throw NotActionablePostException
				if (originalPost.getPostType().equals("EndorsementPost") || originalPost.getPostType().equals("DeletedPost"))  {
					throw new NotActionablePostException();
				}
				//create the new endorsement post
				int newID = endorsing.makeEndorsement(id, endorsed.getHandle(), originalPost.getMesssage());
				//increment the number of endorsed posts the owner of this post has and the number of endorsements on the post
				endorsed.endorsed();
				originalPost.addEndorsement();
				return newID;
			}
		}
		throw new PostIDNotRecognisedException();
	}

	@Override
	public int commentPost(String handle, int id, String message) throws HandleNotRecognisedException,
			PostIDNotRecognisedException, NotActionablePostException, InvalidPostException {
		Account commenting = returnAccount(handle); //commenting is the account making a comment
		//find the post that this account wants to make a comment on
		for (Account commented : accounts) { //commented is the account which owns the post being commented on
			if (commented.hasPost(id)) {
				Post originalPost = commented.getPost(id); 
				//check that this post is a post that can be commented on
				if (originalPost.getPostType().equals("EndorsementPost") || originalPost.getPostType().equals("DeletedPost"))  {
					throw new NotActionablePostException();
				}
				//check that the comment is valid
				if ((message.isEmpty()) || (message.length() > 100)) {
					throw new InvalidPostException();
				}
				//create the new comment post
				int newId = commenting.makeComment(id, message);
				//increment the number of comments that the original post has
				originalPost.addComment();
				return newId;
			}
		}
		throw new PostIDNotRecognisedException();
	}

	@Override
	public void deletePost(int id) throws PostIDNotRecognisedException {
		//find the post to be deleted, throwing PostIDNotRecognisedException if it isn't found
		for (Account a : accounts) {
			if (a.hasPost(id)) {
				Post p = a.getPost(id);
				//deal with if the post is an endorsement post - decrement the number of endorsements the post and the account which was endorsed have
				if (p.getPostType().equals("EndorsementPost")){
					//to find the original post p must be downcasted into an endorsement object
                    Endorsement e = (Endorsement)p;
                    int originalPostId = e.getOriginalPostId();
					//find the account with the original post
                    for(Account a2 : accounts){
						if (a2.hasPost(originalPostId)){
							a2.getPost(originalPostId).removeEndorsement();
							a2.unendorsed();
						}
					}
				}
				//deal with if the post is a comment post - decrement the number of comments on the original post
				if (p.getPostType().equals("CommentPost")){
					//to find the original post p must be downcasted into a comment object
					Comment c = (Comment)p;
					int originalPostId2 = c.getOriginalPostID();
					for(Account a3 : accounts){
						if (a3.hasPost(originalPostId2)){
							a3.getPost(originalPostId2).removeComment();
						}
					}
				}
				//deal with any comments that refer to the post being deleted - if there are any, this post must be added to the deletedComments ArrayList so that when showPostChildrenDetails() is called the children comments refer to a post with a dummy message
				for (Account a4: accounts){
					for (Comment c2 : a4.getComments()){
						if (c2.getOriginalPostID() == id && p.getPostType().equals("CommentPost")){
							deletedComments.add((Comment)p);
						}
					}
				}
				//delete the post
				a.deletePost(id);
				return;
			}
		}
		throw new PostIDNotRecognisedException();

	}

	@Override
	public String showIndividualPost(int id) throws PostIDNotRecognisedException {
		//find the requested post
		for (Account a : accounts){
			if (a.hasPost(id)){
				//generate a string containing information about the post
				Post post = a.getPost(id);
				String postDetails = "";
				postDetails += "ID: "+Integer.toString(id)+" \n";
				postDetails += "Account: "+a.getHandle()+" \n";
				postDetails += "No. endorsements: " + Integer.toString(post.getNumberOfEndorsements()) +" | No. comments: " + Integer.toString(post.getNumberOfComments()) + " \n";
				postDetails += post.getMesssage() +"\n";
				//the string is now formatted appropriatley, and returned
				return postDetails;
			}
		}
		
		throw new PostIDNotRecognisedException();
	} 

	public StringBuilder showPostChildrenDetails(int id)
			throws PostIDNotRecognisedException, NotActionablePostException {
		// loop through all accounts
		for (Account a: accounts){
			//if the account has a post with the matching id, check if the post is and original post or a comment. If not, throw NotActionablePostException
			if (a.hasPost(id)){
				if (a.getPost(id).getPostType().equals("EndorsementPost") || a.getPost(id).getPostType().equals("DeletedPost")){
					throw new NotActionablePostException();
				}
				//create a StringBuilder to contain the eventual string to be returned, and append the string returned from calling showIndividualPost() on the parent post
				StringBuilder postChildrenDetails = new StringBuilder();
				postChildrenDetails.append(showIndividualPost(id));
				//enter recursivePost() to build the string, starting with a depth of 0
				recursivePost(a.getPost(id), 0, postChildrenDetails);
				return postChildrenDetails;
			}
		}
		throw new PostIDNotRecognisedException();
	}

	/**
	 * Recursive solution to building the children details. Displays the thread properly formatted with the |'s and indents
	 * Each child calls this method with all of their own children posts, until a post has no comments, where the base case is met
	 * @param post - Post: The parent post that the method is being called on. It will be added to the StringBuilder and then this method is called on each of its children
	 * @param depth - int: how many parents a post has, used to control the indenting
	 * @param postChildrenDetails - StringBuilder: The current string containing details of the post and its children, will be added to in this method
	 * @throws PostIDNotRecognisedException
	 */
	private void recursivePost(Post post, int depth, StringBuilder postChildrenDetails) throws PostIDNotRecognisedException{
		ArrayList<Comment> childrenPosts = new ArrayList<>();
		//if depth = 0, the post is the original post, and so does not need to be altered
		if (depth != 0){
			//adds the indent for the | > that is put before each post 
			for(int i =1; i<depth; i++){
				postChildrenDetails.append("\t");
			}
			//put in the | > that links a post to its reply
			postChildrenDetails.append("| >");
			//if the current post doesn't refer to a deleted post it is displayed as normal
			if (post.getPostType() != "DeletedPost") {
				//go through each line, and indent it before adding it to the StringBuilder
				Scanner scanner = new Scanner(showIndividualPost(post.getId()));
				postChildrenDetails.append("\t");
				postChildrenDetails.append(scanner.nextLine() + "\n");
				while(scanner.hasNextLine()) {
					for(int i =0; i<depth; i++){
						postChildrenDetails.append("\t");
					}
				//after indenting each line based on depth, add it to the StringBuilder
					postChildrenDetails.append(scanner.nextLine() + "\n");
				}
			//close the scanner
			scanner.close();
			}
			//if the post has been deleted, just display the dummy message given to deleted posts
			else {
				postChildrenDetails.append("\t");
				postChildrenDetails.append(post.getMesssage() + "\n");
			}
		}
		//base case, if this post's number of comments is 0, exit the recursion
		if (post.getNumberOfComments()==0){
			//check if there is a deleted comment; this may have comments under it which should stil be displayed
			boolean hasDeletedComment = false;
			for (Comment c: deletedComments){
				if (c.getOriginalPostID() == post.getId()){
					hasDeletedComment = true;
				}
			}
			if (!hasDeletedComment){
				return;
			}
		}
		//add the indent for the | that goes below a post
		for(int i =0; i<depth; i++){
			postChildrenDetails.append("\t");
		}
		//add the | 
		postChildrenDetails.append("| \n");
		//check all comments in the system to see if they link to the current post
		for (Account a2 : accounts) {
			ArrayList<Comment> Comments = a2.getComments();
			for(Comment c : Comments) {
				if (c.getOriginalPostID() == post.getId()) {
					childrenPosts.add(c);
				}
			}
		}
		//we must check the deleted comments too
		for (Comment deletedComment : deletedComments){
			if (deletedComment.getOriginalPostID() == post.getId()){
				childrenPosts.add(deletedComment);
			}
		}
		
		//sort the ArrayList into ascending order of post IDs
		childrenPosts.sort((o1, o2) -> (o1.getId()-o2.getId()));
		//recursive step, call the function on all of this post's children, increasing depth by 1 so they're properly indented
		for (Comment child : childrenPosts){
			recursivePost(child, depth + 1, postChildrenDetails);
		}
	}

	@Override
	public int getNumberOfAccounts() {
		//return the size of the accounts ArrayList which is the number of active accounts
		return accounts.size();
	}

	@Override
	public int getTotalOriginalPosts() {
		//NO_OF_POSTS is a recorded attribute, but it can't be used here as it doesn't account for deleted posts, and it also counts endorsements and comments so their IDs are also unique. Instead we loop through all the posts and check their type
		int totalOriginalPosts = 0;
		for (Account a: accounts){
			for (Post p: a.getPosts()){
				//if the post type is OriginalPost, add it to totalOriginalPosts
				if (p.getPostType().equals("OriginalPost")){
					totalOriginalPosts +=1;
				}
			}
		}
		//return totalOriginalPosts once all accounts and posts have been checked
		return totalOriginalPosts;
	}

	@Override
	public int getTotalEndorsmentPosts() {
		//works similarly to getTotalOriginalPosts(). The number of endorsements isn't kept track of, and so they are looped though and counted
		int totalEndorsementPosts = 0;
		for (Account a: accounts){
			for (Post p: a.getPosts()){
				//this time, check if post type is EndorsementPost
				if (p.getPostType().equals("EndorsementPost")){
					totalEndorsementPosts +=1;
				}
			}
		}
		return totalEndorsementPosts;
	}

	@Override
	public int getTotalCommentPosts() {
		//loop through each account and count their number of comments using the getComments() method
		int totalCommentPosts = 0;
		for (Account a: accounts){
			ArrayList<Comment> comments = a.getComments();
			totalCommentPosts+= comments.size();
		}
		return totalCommentPosts;
	}

	@Override
	public int getMostEndorsedPost() {
		//set mostPopularPostId to -1, which will be returned if there are no posts in the platform. Otherwise maxNumberOfEndorsements will be 0 or greater
		int mostPopularPostID = -1;
		int maxNumberOfEndorsements = -1;
		//loop though all accounts, and check each post
		for( Account a : accounts){
			ArrayList<Post> posts = a.getPosts();
			for (Post p : posts){
				//if the post has more endorsements than the current maximum, update the current maximum and set mostPopularPostID to this posts ID
				if (p.getNumberOfEndorsements() > maxNumberOfEndorsements){
					maxNumberOfEndorsements = p.getNumberOfEndorsements();
					mostPopularPostID = p.getId();
				}
			}
		}
		//return the post ID with the highest number of endorsements. If two posts have the same number, the first one will be returned
		return mostPopularPostID;
	}

	@Override
	public int getMostEndorsedAccount() {
		//works very similarly to getMostEndorsedPost()
		int maxNumberOfEndorsements = -1;
		int mostPopularAccountId = -1;
		for (Account a: accounts){
			//get the number of endorsements using the getNoOfEndorsements() method
			int sumOfEndorsements = a.getNoOfEndorsements();
			//if this number is bigger than the current maximum, update the current maximum and set the account ID to mostPopularAccountID
			if (sumOfEndorsements > maxNumberOfEndorsements){
				maxNumberOfEndorsements = sumOfEndorsements;
				mostPopularAccountId = a.getId();
			}
		}
		//once all accounts have been checked, return the ID of the account with the most endorsements
		return mostPopularAccountId;
	}

	@Override
	public void erasePlatform() {
		//handle the HandleNotRecognisedException, thrown by removeAccount()
		try{
			while (!accounts.isEmpty()) {
				//remove all accounts in the platform
				Account a = accounts.get(0);
				removeAccount(a.getHandle());
			}
		} catch (HandleNotRecognisedException e){
			//as we are only using handles already retrieved from accounts, this won't be an issue. This assertion validates this
			assert(accounts.isEmpty()) : "while loop has been exited with accounts still in the platform";
		}
		//use the reset methods to set NO_OF_ACCOUNTS and NO_OF_POSTS to 0
		Account.reset();
		Post.reset();
		//go through the deletedComments ArrayList and remove their reference, so they are removed from the heap by the garbage collector
		while (!deletedComments.isEmpty()){
			deletedComments.remove(0);
		}
	}

	@Override
	public void savePlatform(String filename) throws IOException {
		//create an ObjectOutputStream using the filename passed in. This will throw an IOException if there is a problem
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename));
		//add the accounts and deletedComments ArrayLists to this file
		out.writeObject(accounts);
		out.writeObject(deletedComments);
		//upcast the static values NO_OF_POSTS() and NO_OF_ACCOUNTS() to an Integer array, so that the ID's begin from the correct value when the platform is loaded
		Integer[] Numbers = {Post.getNO_OF_POSTS(), Account.getNO_OF_ACCOUNTS()};
		out.writeObject(Numbers);
		//close the output stream
		out.close();
	}

	@Override
	public void loadPlatform(String filename) throws IOException, ClassNotFoundException {
		//erase the current platform
		erasePlatform();
		//createa new input stream from the filename passed in
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename)); 
		//iterate over each line in the bytestream until it is empty (when the break statement is reached)
		while (true) {
			try {
				//use the general obj type to account for the 3 types of objects stored in the file
				Object obj = in.readObject();
				//if the object is an ArrayList, upcast it safley 
				if (obj instanceof ArrayList) {
					ArrayList lst = (ArrayList) obj;
					//if the list is empty, move to the next object
					if (lst.isEmpty()) {
						continue;
					}
					//if the first value in this ArrayList is an account, this is the accounts Arraylist. Upcast the whole ArrayList and save to accounts
					if (lst.get(0) instanceof Account) {
						accounts = (ArrayList<Account>) lst;
					}
					//if the first value is a comment, it is the deletedComments ArrayList. Upcast and save to deletedComments
					if (lst.get(0) instanceof Comment) {
						deletedComments = (ArrayList<Comment>) lst;
					}
				}
				//otherwise, it is the list containing NO_OF_POSTS and NO_OF_ACOUNTS
				if (obj instanceof Integer[]) {
					//upacst the object to a list of Integers
					Integer[] intlst = (Integer[]) obj;
					//the 0th index is NO_OF_POSTS and the 1st is NO_OF_ACCOUNTS, save these to the platform
					Post.setNO_OF_POSTS(intlst[0]);
					Account.setNO_OF_ACOUNTS(intlst[1]);
					}
			//if there are no more objects, exit the while loop
			} catch (IOException e) {
				break;
			}
		}
		//close the input stream
		in.close();
	}
}

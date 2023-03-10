package socialmedia;

import java.io.IOException;
import java.util.Scanner;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Collections;

/**
 * BadSocialMedia is a minimally compiling, but non-functioning implementor of
 * the SocialMediaPlatform interface.
 * 
 * @author Diogo Pacheco
 * @version 1.0
 */
public class SocialMedia implements SocialMediaPlatform {
	private ArrayList<Account> Accounts = new ArrayList<>();
	private ArrayList<Comment> deletedComments = new ArrayList<>();

	private Account returnAccount(String handle) throws HandleNotRecognisedException {
		//Given an account handle, return the account object
		for(Account a : Accounts) {
			if (a.getHandle().equals(handle)) {
				return a;
			}
		}
		throw new HandleNotRecognisedException(); //if the account with this handle doesn't exist
	}

	@Override
	/**
	 * This fuction creates an account given a handle but no description
	 * It checks that the handle is valid and legal (not in use), and then returns the ID of the created account
	 * 
	 * 
	 * @param handle - String: the handle of the account that the user wishes to create
	 * 
	 * @return id - int: the id of the created account
	 * 
	 */
	public int createAccount(String handle) throws IllegalHandleException, InvalidHandleException {
		//check if the account handle is valid 
		if ((handle.isEmpty()) || (handle.length() > 30) || (handle.contains(" "))) {
			throw new InvalidHandleException();
		}
		//search the Accounts ArrayList to see if the handle is already in use 
		for (Account a : Accounts) {  
			//using the String class, use .equals method.
			if (a.getHandle().equals(handle)) {
				throw new IllegalHandleException();
			}
		}
		//if all checks are passed, create a new account with the verified handle
		Account accountn = new Account(handle); 
		//add this account to the Accounts ArrayList
		Accounts.add(accountn); 
		//use the .getId method to return the id of the new account.
		return accountn.getId();
	}	

	@Override
	/**
	 * This fuction creates an account given a handle and a description
	 * It checks that the handle is valid and legal (not in use), and then returns the ID of the created account
	 * 
	 * 
	 * @param handle - String: the handle of the account that the user wishes to create
	 * @param description - String: the description that the Account will have
	 * 
	 * @return id - int: the id of the created account
	 * 
	 */
	public int createAccount(String handle, String description) throws IllegalHandleException, InvalidHandleException {
		//call the original createAccount class with only the handle, storing the id it returns
		int id = createAccount(handle);
		//loop through each account in the ArrayList, to find the account that was just created using the id.
		for (Account a : Accounts) {
			if (a.getId() == id) {
				//set the description of this account to the description given in the input
				a.setDescription(description);
			}
		}
		//return the id generated from the original createAccount call
		return id;



	}

	@Override
	public void removeAccount(int id) throws AccountIDNotRecognisedException {
		Iterator<Account> itr = Accounts.iterator();
		while (itr.hasNext()) {
			Account a = (Account)itr.next();
			if (a.getId() == id){
				
				Iterator<Post> posts = a.getPosts().iterator();
				while(posts.hasNext()){
					Post p = posts.next();
					try{
						deletePost(p.getId());
						posts.remove();
					} catch (PostIDNotRecognisedException e){
				
					}
				}

				a = null;
				itr.remove();

			}
		}

	}

	@Override
	public void removeAccount(String handle) throws HandleNotRecognisedException {
		Account account = returnAccount(handle);
		ArrayList<Post> posts = account.getPosts();
		while(!posts.isEmpty()){
			Post p = posts.get(0);
			try{
				deletePost(p.getId());
			} catch (PostIDNotRecognisedException e){

			}
			
		}
		Accounts.remove(account);
		account = null;

	}

	@Override
	public void changeAccountHandle(String oldHandle, String newHandle)
			throws HandleNotRecognisedException, IllegalHandleException, InvalidHandleException {
		Account a = returnAccount((oldHandle));
		for (Account b : Accounts) {
			if (b.getHandle().equals(newHandle)) {
				throw new IllegalHandleException();
			}
		}
		if ((newHandle.isEmpty()) || (newHandle.length() > 30) || (newHandle.contains(" "))) {
			throw new InvalidHandleException();
		}
		a.setHandle(newHandle);
	}

	@Override
	public void updateAccountDescription(String handle, String description) throws HandleNotRecognisedException {
		Account a = returnAccount(handle);
		a.setDescription(description);
	}

	@Override
	public String showAccount(String handle) throws HandleNotRecognisedException {
		String accountOut = "";
		Account a = returnAccount(handle);
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
		if ((message.isEmpty()) || (message.length() > 100)) {
			throw new InvalidPostException();
		}
		int PostId = a.makePost(message);
		return PostId;
	}

	@Override
	public int endorsePost(String handle, int id)
			throws HandleNotRecognisedException, PostIDNotRecognisedException, NotActionablePostException {
		Account endorsing = returnAccount(handle); 
		for (Account endorsed : Accounts) {
			if (endorsed.hasPost(id)) {
				Post originalPost = endorsed.getPost(id); 
				if (originalPost.getPostType().equals("EndorsementPost") || originalPost.getPostType().equals("DeletedPost"))  {
					throw new NotActionablePostException();
				}
				int newID = endorsing.makeEndorsement(id, endorsed.getHandle(), originalPost.getMesssage());
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
		Account commenting = returnAccount(handle); 
		for (Account commented : Accounts) {
			if (commented.hasPost(id)) {
				Post originalPost = commented.getPost(id); 
				if (originalPost.getPostType().equals("EndorsementPost") || originalPost.getPostType().equals("DeletedPost"))  {
					throw new NotActionablePostException();
				}
				if ((message.isEmpty()) || (message.length() > 100)) {
					throw new InvalidPostException();
				}
				int newId = commenting.makeComment(id, message);
				originalPost.addComment();
				return newId;
			}
		}
		throw new PostIDNotRecognisedException();
	}

	@Override
	public void deletePost(int id) throws PostIDNotRecognisedException {
		for (Account a : Accounts) {
			if (a.hasPost(id)) {
				Post p = a.getPost(id);
				if (p.getPostType().equals("EndorsementPost")){
                    Endorsement e = (Endorsement)p;
                    int originalPostId = e.getOriginalPostId();
                    for(Account a2 : Accounts){
						if (a2.hasPost(originalPostId)){
							a2.getPost(originalPostId).removeEndorsement();
						}
					}
				}
				if (p.getPostType().equals("CommentPost")){
					Comment c = (Comment)p;
					int originalPostId2 = c.getOriginalPostID();
					for(Account a3 : Accounts){
						if (a3.hasPost(originalPostId2)){
							a3.getPost(originalPostId2).removeComment();
						}
					}
				}
				for (Account a4: Accounts){
					for (Comment c2 : a4.getComments()){
						if (c2.getOriginalPostID() == id && p.getPostType().equals("CommentPost")){
							deletedComments.add((Comment)p);
						}
					}
				}
				a.deletePost(id);
				return;
			}
		}
		throw new PostIDNotRecognisedException();

	}

	@Override
	public String showIndividualPost(int id) throws PostIDNotRecognisedException {
		for ( Account a : Accounts){
			if (a.hasPost(id)){
				Post post = a.getPost(id);
				String postDetails = "";
				postDetails += "ID: "+Integer.toString(id)+" \n";
				postDetails += "Account: "+a.getHandle()+" \n";
				postDetails += "No. endorsements: " + Integer.toString(post.getNumberOfEndorsements()) +" | No. comments: " + Integer.toString(post.getNumberOfComments()) + " \n";
				postDetails += post.getMesssage() +"\n";
				return postDetails;
			}
		}
		for (Comment deletedComment : deletedComments){
			if (deletedComment.getId() == id){
				String postDetails = "";
				postDetails += "ID: "+Integer.toString(id)+" \n";
				postDetails += "Account: None \n";
				postDetails += "No. endorsements: " + Integer.toString(deletedComment.getNumberOfEndorsements()) +" | No. comments: " + Integer.toString(deletedComment.getNumberOfComments()) + " \n";
				postDetails += deletedComment.getMesssage() +"\n";
				return postDetails;

			}
		}
		throw new PostIDNotRecognisedException();
	}

	public StringBuilder showPostChildrenDetails(int id)
			throws PostIDNotRecognisedException, NotActionablePostException {
		for (Account a: Accounts){
			if (a.hasPost(id)){
				if (a.getPost(id).getPostType().equals("EndorsementPost") || a.getPost(id).getPostType().equals("DeletedPost")){
					throw new NotActionablePostException();
				}
				StringBuilder postChildrenDetails = new StringBuilder();
				postChildrenDetails.append(showIndividualPost(id));
				recursivePost(a.getPost(id), 0, postChildrenDetails);
				return postChildrenDetails;
			}
		}
		throw new PostIDNotRecognisedException();
	}

	private void recursivePost(Post post, int depth, StringBuilder postChildrenDetails) throws PostIDNotRecognisedException{
		ArrayList<Comment> childrenPosts = new ArrayList<>();
		// if depth = 0, the post is the original post, and so does not need to be altered
		if (depth != 0){
			// adds the indent for the | > that is put before each post 
			for(int i =1; i<depth; i++){
				postChildrenDetails.append("\t");
			}
			//put in the | > that links a post to it's reply
			postChildrenDetails.append("| >");
			// go through each line, and indent it before adding to the string builder 
			Scanner scanner = new Scanner(showIndividualPost(post.getId()));
			postChildrenDetails.append("\t");
			postChildrenDetails.append(scanner.nextLine() + "\n");
			while(scanner.hasNextLine()) {
				for(int i =0; i<depth; i++){
					postChildrenDetails.append("\t");
				}
				//after indenting each line, add it to the stringbuilder
				postChildrenDetails.append(scanner.nextLine() + "\n");
			}
			//close the scanner
			scanner.close();
		}
		//base case, if number of comments is 0, exit recusion
		if (post.getNumberOfComments()==0){
			return;
		}
		// loop add the indent for the | that goes below a post/comment
		for(int i =0; i<depth; i++){
			postChildrenDetails.append("\t");
		}
		// add the | that goes below
		postChildrenDetails.append("| \n");
		// acess all the posts of type "CommentPost for each account, and see if they are linked to the post stored in post"
		for (Account a2 : Accounts) {
			ArrayList<Comment> Comments = a2.getComments();
			for(Comment c : Comments) {
				if (c.getOriginalPostID() == post.getId()) {
					// if a account has a comment linked aboce, call recursivePost on it, increasing the depth by 1
					childrenPosts.add(c);
					////recursivePost(c, depth + 1, postChildrenDetails);
				}
			}
		}
		for (Comment deletedComment : deletedComments){
			if (deletedComment.getOriginalPostID() == post.getId()){
				childrenPosts.add(deletedComment);
			}

		}
		
		///ArrayList<Comment> childrenPostsSorted = Collections.sort(childrenPosts, new compareByPostId());
		childrenPosts.sort((o1, o2) -> (o1.getId()-o2.getId()));
		for (Comment child : childrenPosts){
			recursivePost(child, depth + 1, postChildrenDetails);
		}

	}

	@Override
	public int getNumberOfAccounts() {
		return Accounts.size();
	}

	@Override
	public int getTotalOriginalPosts() {
		int totalOriginalPosts = 0;
		for (Account a: Accounts){
			for (Post p: a.getPosts()){
				if (p.getPostType().equals("OriginalPost")){
					totalOriginalPosts +=1;
				}
			}
		}
		return totalOriginalPosts;
	}

	@Override
	public int getTotalEndorsmentPosts() {
		int totalEndorsementPosts = 0;
		for (Account a: Accounts){
			for (Post p: a.getPosts()){
				if (p.getPostType().equals("EndorsementPost")){
					totalEndorsementPosts +=1;
				}
			}
		}
		return totalEndorsementPosts;
	}

	@Override
	public int getTotalCommentPosts() {
		int totalCommentPosts = 0;
		for (Account a: Accounts){
			ArrayList<Comment> comments = a.getComments();
			totalCommentPosts+= comments.size();
		}
		return totalCommentPosts;
	}

	@Override
	public int getMostEndorsedPost() {
		int mostPopularPostID = -1;
		int maxNumberOfEndorsements = -1;
		for( Account a : Accounts){
			ArrayList<Post> posts = a.getPosts();
			for (Post p : posts){
				if (p.getNumberOfEndorsements() > maxNumberOfEndorsements){
					maxNumberOfEndorsements = p.getNumberOfEndorsements();
					mostPopularPostID = p.getId();
				}
			}
		}
		return mostPopularPostID;
	}

	@Override
	public int getMostEndorsedAccount() {
		int maxNumberOfEndorsements = -1;
		int mostPopularAccountId = -1;
		for (Account a: Accounts){
			int sumOfEndorsements = 0;
			ArrayList<Post> posts = a.getPosts();
			for (Post p : posts){
				sumOfEndorsements += p.getNumberOfEndorsements();
			}
			if (sumOfEndorsements > maxNumberOfEndorsements){
				maxNumberOfEndorsements = sumOfEndorsements;
				mostPopularAccountId = a.getId();
			}
		}
		return mostPopularAccountId;
	}

	@Override
	public void erasePlatform() {
		try{
			while (!Accounts.isEmpty()) {
				Account a = Accounts.get(0);
				removeAccount(a.getHandle());
			}
			System.out.println(Accounts.size());
		} catch (HandleNotRecognisedException e){
			System.out.println("problem");
		}
		Account.reset();
		Post.reset();
		Iterator<Comment> itr = deletedComments.iterator();
		while (itr.hasNext()){
			itr.remove();
		}
	}

	@Override
	public void savePlatform(String filename) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void loadPlatform(String filename) throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub

	}

}

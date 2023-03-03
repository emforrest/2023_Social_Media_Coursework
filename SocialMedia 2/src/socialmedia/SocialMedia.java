package socialmedia;

import java.io.IOException;
import java.util.ArrayList;

/**
 * BadSocialMedia is a minimally compiling, but non-functioning implementor of
 * the SocialMediaPlatform interface.
 * 
 * @author Diogo Pacheco
 * @version 1.0
 */
public class SocialMedia implements SocialMediaPlatform {
	private ArrayList<Account> Accounts = new ArrayList<>();

	public Account returnAccount(String handle) throws HandleNotRecognisedException {
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
		if ((handle.isEmpty()) || (handle.length() < 30) || (handle.contains(" "))) {
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
		for (Account a : Accounts) {
			if (a.getId() == id){
				a.deleteAllPosts();
				Accounts.remove(a);

			}
		}

	}

	@Override
	public void removeAccount(String handle) throws HandleNotRecognisedException {
		Account account = returnAccount(handle);
		account.deleteAllPosts();
		Accounts.remove(account);

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
		if ((newHandle.isEmpty()) || (newHandle.length() < 30) || (newHandle.contains(" "))) {
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
				a.deletePost(id);
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
				postChildrenDetails.append(recursivePost(id, 0, postChildrenDetails));
				return postChildrenDetails;
			}
		}
		throw new PostIDNotRecognisedException();
	}

	private StringBuilder recursivePost(int id, int depth, StringBuilder postChildrenDetails){
		postChildrenDetails.append("| ");
		return postChildrenDetails;
	}

	@Override
	public int getNumberOfAccounts() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalOriginalPosts() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalEndorsmentPosts() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalCommentPosts() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMostEndorsedPost() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMostEndorsedAccount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void erasePlatform() {
		// TODO Auto-generated method stub

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

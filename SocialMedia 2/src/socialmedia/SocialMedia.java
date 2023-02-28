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
		for(int i=0; i<Accounts.size(); i++) {
			if (Accounts.get(i).getHandle().equals(handle)) {
				return Accounts.get(i);
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
		for (int i = 0; i<Accounts.size(); i++) { 
			Account Accountn = Accounts.get(i); 
			//using the String class, use .equals method.
			if (Accountn.getHandle().equals(handle)) {
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
		for (int i=0; i<Accounts.size(); i++) {
			if (Accounts.get(i).getId() == id) {
				//set the description of this account to the description given in the input
				Accounts.get(i).setDescription(description);
			}
		}
		//return the id generated from the original createAccount call
		return id;



	}

	@Override
	public void removeAccount(int id) throws AccountIDNotRecognisedException {
		for (int i=0; i<Accounts.size(); i++) {
			if (Accounts.get(i).getId() == id){
				Accounts.get(i).deleteAllPosts();
				Accounts.remove(i);

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
		// TODO Auto-generated method stub

	}

	@Override
	public void updateAccountDescription(String handle, String description) throws HandleNotRecognisedException {
		// TODO Auto-generated method stub

	}

	@Override
	public String showAccount(String handle) throws HandleNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int createPost(String handle, String message) throws HandleNotRecognisedException, InvalidPostException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int endorsePost(String handle, int id)
			throws HandleNotRecognisedException, PostIDNotRecognisedException, NotActionablePostException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int commentPost(String handle, int id, String message) throws HandleNotRecognisedException,
			PostIDNotRecognisedException, NotActionablePostException, InvalidPostException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void deletePost(int id) throws PostIDNotRecognisedException {
		// TODO Auto-generated method stub

	}

	@Override
	public String showIndividualPost(int id) throws PostIDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StringBuilder showPostChildrenDetails(int id)
			throws PostIDNotRecognisedException, NotActionablePostException {
		// TODO Auto-generated method stub
		return null;
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

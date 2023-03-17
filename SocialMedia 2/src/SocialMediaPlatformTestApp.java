import java.io.IOException;

import socialmedia.AccountIDNotRecognisedException;
import socialmedia.HandleNotRecognisedException;
import socialmedia.SocialMedia;
import socialmedia.IllegalHandleException;
import socialmedia.InvalidHandleException;
import socialmedia.InvalidPostException;
import socialmedia.NotActionablePostException;
import socialmedia.PostIDNotRecognisedException;
import socialmedia.SocialMediaPlatform;

/**
 * A short program to illustrate an app testing some minimal functionality of a
 * concrete implementation of the SocialMediaPlatform interface -- note you will
 * want to increase these checks, and run it on your SocialMedia class (not the
 * BadSocialMedia class).
 *
 * 
 * @author Diogo Pacheco
 * @version 1.0
 */
public class SocialMediaPlatformTestApp {

	/**
	 * Test method.
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) {
		System.out.println("The system compiled and started the execution...");

		SocialMediaPlatform platform = new SocialMedia();

		assert (platform.getNumberOfAccounts() == 0) : "Innitial SocialMediaPlatform not empty as required.";
		assert (platform.getTotalOriginalPosts() == 0) : "Innitial SocialMediaPlatform not empty as required.";
		assert (platform.getTotalCommentPosts() == 0) : "Innitial SocialMediaPlatform not empty as required.";
		assert (platform.getTotalEndorsmentPosts() == 0) : "Innitial SocialMediaPlatform not empty as required.";


		int id1; 
		int id2;
		int id3;
		int postid1;
		int postid2;
		int postid3 = 12;
		int postid4;
		int mostendorsed;

		try{
			platform.loadPlatform("hello");
			

	/*	} catch (IllegalHandleException e){
			System.out.println("IllegalHandle");
		} catch (InvalidHandleException e2){
			System.out.println("InvalidHandle");
		} catch (HandleNotRecognisedException e3){
			System.out.println("HandleNotRecognised");
		//} catch (AccountIDNotRecognisedException e4){
			//System.out.println("AccountIDNotRecognised");
		} catch (InvalidPostException e5){
			//System.out.println("InvalidPost");
		} catch (NotActionablePostException e6){
			//System.out.println("NotActionablePost");
		} catch (PostIDNotRecognisedException e7){
			//System.out.println("PostIDNotRecognised");*/
		} catch (IOException e8) {
			System.out.println("IOexception");
		} catch (ClassNotFoundException e9) {
			System.out.println("ClassNotFound");
		}
	}

}

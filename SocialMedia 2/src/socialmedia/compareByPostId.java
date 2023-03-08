package socialmedia;

public class compareByPostId {
    public boolean compare(Post post1, Post post2){
        return post1.getId() < post2.getId();
    }
}

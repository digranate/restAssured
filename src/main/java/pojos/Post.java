package pojos;

/**
 * Created by Dinara.Trifanova on 8/14/2017.
 */
public class Post {
    Integer id;
    String title;
    String body;
    int userId;

    public Post() {
    }

    public Integer getId() {
        return id;
    }

    public Post setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Post setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getBody() {
        return body;
    }

    public Post setBody(String body) {
        this.body = body;
        return this;
    }

    public int getUserId() {
        return userId;
    }

    public Post setUserId(int userId) {
        this.userId = userId;
        return this;
    }
}

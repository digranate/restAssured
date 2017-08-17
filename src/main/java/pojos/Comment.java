package pojos;

/**
 * Created by Dinara.Trifanova on 8/15/2017.
 */
public class Comment {
    Integer postId;
    Integer id;
    String name;
    String email;
    String body;

    public Integer getPostId() {
        return postId;
    }

    public Comment setPostId(Integer postId) {
        this.postId = postId;
        return this;
    }

    public Integer getId() {
        return id;
    }

    public Comment setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Comment setName(String name) {
        this.name = name;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Comment setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getBody() {
        return body;
    }

    public Comment setBody(String body) {
        this.body = body;
        return this;
    }
}

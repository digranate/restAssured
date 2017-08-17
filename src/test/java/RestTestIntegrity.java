import com.jayway.restassured.RestAssured;
import com.jayway.restassured.filter.log.LogDetail;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Created by Dinara.Trifanova on 8/14/2017.
 */
public class RestTestIntegrity {
    @BeforeClass
    public static void setUp() {
        RestAssured.baseURI = "http://jsonplaceholder.typicode.com";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails(LogDetail.ALL);
    }

    @Test
    public void testPostsForEachUserIdThereIsRecordInUsers() {
        List<Integer> userIds = given().that()
                .when().get("/posts")
                .then()
                .extract().path("unique{it.userId}.collect{it.userId}");
        userIds.forEach(id ->
                given().
                        pathParam("userId", id).
                        when().
                        get("/users/{userId}").
                        then().
                        statusCode(200).
                        body("id", equalTo(id))
        );
    }

    //   7. check posts/1/comments is equal to /comments?postId=1
    @Test
    public void testCommentsFromPostsAreEqualToPostFromComments() {

        int postId = 5;

        String fromPosts = given().
                pathParam("postId", postId).
                when().
                get("/posts/{postId}/comments").
                then().
                statusCode(200).
                extract().response().asString();

        String fromComments = given().
                queryParam("postId", postId).
                when().
                get("/comments").
                then().
                statusCode(200).
                extract().response().asString();
        Assert.assertTrue("Responses from comments and posts are not equal", fromComments.equals(fromPosts));
    }

}

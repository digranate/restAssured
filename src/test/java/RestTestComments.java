import com.jayway.restassured.RestAssured;
import com.jayway.restassured.filter.log.LogDetail;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import pojos.Comment;
import pojos.User;

import static com.jcabi.matchers.RegexMatchers.matchesPattern;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;

/**
 * Created by Dinara.Trifanova on 8/14/2017.
 */
public class RestTestComments extends RestTest{
    @BeforeClass
    public static void setUp() {
        RestAssured.baseURI = "http://jsonplaceholder.typicode.com";
        RestAssured.basePath = "/comments";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails(LogDetail.ALL);
    }

    @Test
    public void testUsersCheckAllFields() {
        int commentId = 5;
        Comment comment = given().that()
                .when()
                .pathParam("commentId",commentId)
                .get("/{commentId}")
                .then()
                .extract().as(Comment.class);
        assertEquals(commentId, (int)comment.getId());
        assertEquals(1, (int)comment.getPostId());
    }


    @Test
    public void testEMailCorrespondsToPost() {
        given().that().when()
              //  .pathParam("commentId", 1)
                .get("")
                .then()
                .statusCode(200)
                .body("findAll{it.email.equals('Eliseo@gardner.biz')}.postId", equalTo(Collections.singletonList(1)));
    }


    @Test
    public void testEMailHasEmailRegex() {
        String email = given().that().when()
                .pathParam("commentId", 1)
                .get("/{commentId}")
                .then()
                .statusCode(200)
                .extract().path("email");
        Assert.assertThat(email, matchesPattern("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,7}$"));
    }

}

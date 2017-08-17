import com.jayway.restassured.RestAssured;
import com.jayway.restassured.filter.log.LogDetail;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Headers;
import com.jayway.restassured.response.Response;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import pojos.Post;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.jayway.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

/**
 * Created by Dinara.Trifanova on 7/17/2017.
 */
public class RestTestPosts extends RestTest {

    @BeforeClass
    public static void setUp() {
        RestAssured.baseURI = "http://jsonplaceholder.typicode.com";
        RestAssured.basePath = "/posts";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails(LogDetail.ALL);
    }

/*
    1. /photos check url != thumbnailUrl for all id's
    2. create /posts/1 post check title and author is from input
    3. update /posts/1 check the title and author is from input
    4. update /posts/1 with non-existing id
    5. delete /posts/1
  + 6. filtering posts?userId=1 check userId
  + 7. check posts/1/comments is equal to /comments?postId=1
    8. get /users/1/todos check count of true = ? count of false = ?
    9. check headers and cookies
  + 10. measure response times
    11. getting logging in request and response
    12 logging in case response code is smth
    13 logging only in case of failure
*/


    @Test
    public void testPostsValidationSchema() {
        given().that()
                .when()
                .then()
                .body(matchesJsonSchemaInClasspath("schema_posts.json"));
    }

    @Test
    public void testPostsCheckAllFields() {
        int postId = 5;
        Post post = given().that()
                .when()
                .pathParam("postId", postId)
                .get("/{postId}")
                .then()
                .extract().as(Post.class);
        assertEquals(postId, (int) post.getId());
        assertEquals(1, (int) post.getUserId());
        assertEquals("nesciunt quas odio", post.getTitle());
        assertEquals("repudiandae veniam quaerat sunt sed\n" +
                "alias aut fugiat sit autem sed est\n" +
                "voluptatem omnis possimus esse voluptatibus quis\n" +
                "est aut tenetur dolor neque", post.getBody());

    }

    @Test
    public void testPostsUserIdsCount() {
        given().that()
                .when()
                .then()
                .body("unique{it.userId}.size()", equalTo(10));
    }

    @Test
    public void testPostsUserIdMax() {
        given().that()
                .when()
                .then()
                .body("collect{it.userId}.max()", equalTo(10));

    }

    @Test
    public void testPostsTitleAreNotBlank() {
        given().that()
                .when()
                .then()
                .body("find {it.title == null && it.title.equals('')}", equalTo(null));
    }

    // filtering posts?userId=1 check userId
    @Test
    public void testFilterByUserIdgivesUserId() {
        int userId = 5;

        given().that()
                .queryParam("userId", userId)

                .when().
                get("")
                .then()
                .body("find{it.userId != 5}", equalTo(null));
    }

    @Test
    public void testAddNewPost() {
        Post post = new Post();
        post.setTitle("foo").setBody("bar").setUserId(1);
        int max = given().that()
                .when().get("")
                .then()
                .extract().path("max{it.id}.id");

        given().that()
                .body(post)
                .when().post("")
                .then().statusCode(201)
                .body("id", equalTo(max + 1));
    }


    @Test
    public void testUpdatePost() {
        Post post = new Post();
        post.setId(2).setTitle("foo").setBody("bar").setUserId(1);

        given().that()
                .body(post)
                .pathParam("postId", post.getId())
                .when().put("/{postId}")
                .then().statusCode(200)
                .body("id", equalTo(post.getId()));
    }

    @Test
    public void testUpdatePostWithNonExistId() {
        Post post = new Post();
        post.setId(100000).setTitle("foo").setBody("bar").setUserId(1);

        given().that()
                .body(post)
                .pathParam("postId", post.getId())
                .when().put("/{postId}")
                .then().statusCode(404);
    }

    //Bug! there is no integrity check with users when updating postId with non-existing userId
    @Ignore
    @Test
    public void testUpdatePostWithNonExistUserId() {
        Post post = new Post();
        post.setId(2).setTitle("foo").setBody("bar").setUserId(100000000);

        given().that()
                .body(post)
                .pathParam("postId", post.getId())
                .when().put("/{postId}")
                .then().statusCode(404);
    }

    @Test
    public void testDeletePost() {
        int postId = 1;
        given().that()
                .pathParam("postId", postId)
                .when().delete("/{postId}")
                .then().statusCode(200);
    }

    @Test
    public void testDeletePostWithNonexistId() {
        int postId = 100000;
        given().that()
                .pathParam("postId", postId)
                .when().delete("/{postId}")
                .then().statusCode(404);
    }


    @Test
    public void testGetAllRepos() {
        given().that().baseUri("https://api.github.com").port(8080).when().get("/users/digranate/repos").then()
                .statusCode(200)
                .body("name", hasItems("CRUD", "CRUDforJavaRush"));
    }

    @Test
    public void testGetGroovyValidation() {
        given().that().baseUri("https://api.github.com").port(8080).when().get("/users/digranate/repos").then()
                .body("findAll{it.has_wiki='true'}.collect{it.forks}.sum()", equalTo(0));
    }


    @Test
    public void testGetExtractValues() {
        int id = given().that().baseUri("https://api.github.com").port(8080).
                when().get("/users/digranate/repos").
                then()
                .extract().path("find{it.name='CRUD'}.id");
        assertEquals(id, 63797694);
    }

    @Test
    public void testGetExtractResponse() {
        Response response = given().that().baseUri("https://api.github.com").port(8080).
                when().get("/users/digranate/repos").
                then()
                .extract().response();
        assertEquals((int) response.path("find{it.name='CRUD'}.id"), 63797694);

        String json = response.asString();
        JsonPath jsonPath = new JsonPath(json).setRoot("find{it.name='CRUD'}");
        int id = jsonPath.getInt("id");
        String login = jsonPath.get("owner.login");

        assertEquals(63797694, id);
        assertEquals("digranate", login);
    }

    @Test
    public void testGetExtractResponseAdditionalDataCheck() {
        Response response = given().that().baseUri("https://api.github.com").port(8080).
                when().get("/users/digranate/repos").
                then()
                .extract().response();

        // Get all headers
        Headers allHeaders = response.getHeaders();
        // get a multi header value
        List<String> headers = response.getHeaders().getValues("Server");
        // Get a single header value:
        String headerName = response.getHeader("Server");

        // Get all cookies as simple name-value pairs
        Map<String, String> allCookies = response.getCookies();
        // Get a single cookie value:
        String cookieValue = response.getCookie("cookieName");

        // Get status line
        String statusLine = response.getStatusLine();
        // Get status code
        int statusCode = response.getStatusCode();

    }


    @Test
    public void testGetInputParameters() {
        given().when().baseUri("https://api.github.com").pathParam("owner", "digranate").pathParam("repo", "CRUD")
                .when().get("/repos/{owner}/{repo}")
                .then().statusCode(200);
    }


    @Test
    public void testGet2() {
        given().that().port(8080).when().get("/posts/1").then().body("userId", equalTo(1));
    }

    @Test
    public void testPut() {
        given().that().baseUri("http://localhost").port(8080).and().basePath("/presentation").when().put("/call/without/authorization").then().statusCode(200);
    }

    @Test
    public void testDelete() {
        given().that().baseUri("http://localhost").port(8080).and().basePath("/presentation").when().delete("/call/without/authorization").then().statusCode(200);
    }

    @Test
    public void testGetWithJsonInResponse() {
        given().that().baseUri("http://localhost").port(8080).and().basePath("/presentation").when().get("/call/without/authorization1").then().assertThat().body("lotto.lottoId", equalTo(5)).and().body("lotto.winning-numbers", hasItem(45)).and().body("lotto.winners[1].numbers[5]", equalTo(22)).and().body("lotto.winners[0].numbers", hasItems(2, 23, 3));
    }

    @Test
    public void testGetWithXmlInResponse() {
        given().that().baseUri("http://localhost").port(8080).and().basePath("/presentation").when().get("/call/without/authorization2").then().assertThat().body("persones.person[1].firstname", equalTo("Petja")).body("persones.person[1].lastname", equalTo("Chopikow")).and().statusCode(200);
    }

    @Test
    public void testGetWithBasicPreemptiveAuthorization() {
        given().that().baseUri("http://localhost").port(8080).and().basePath("/presentation").auth().preemptive().basic("thisDude", "canPass").when().get("/call/with/authorization").then().assertThat().statusCode(200);
    }

    @Test
    public void testGetWithBasicChallengeAuthorization() {
        given().that().auth().basic("thisDude", "canPass").baseUri("http://localhost").port(8080).and().basePath("/presentation").when().get("/call/with/authorization").then().assertThat().statusCode(200);
    }

    @Test
    public void testPostWithHeaders0() {
        given().that().baseUri("http://localhost").port(8080).basePath("/presentation").header("Content-Type", "text/xml").when().post("/call/with/headers").then().and().assertThat().statusCode(200);
    }

    @Test
    public void testPostWithHeaders1() {
        given().that().baseUri("http://localhost").port(8080).basePath("/presentation").header("Content-Type", "text/plain").header("Accept", "text/.sfsdfsf").when().post("/call/with/headers").then().and().assertThat().statusCode(201);
    }

    @Test
    public void testPostWithHeaders2() {
        given().that().baseUri("http://localhost").port(8080).basePath("/presentation").header("Content-Type", "application/json").header("Accept", "text/.rasdrsa").header("etag", "252452525").when().post("/call/with/headers").then().and().assertThat().statusCode(202);
    }

    @Test
    public void testPostWithHeaders3() {
        given().that().baseUri("http://localhost").port(8080).basePath("/presentation").header("Content-Type", "text/html").header("Accept", "text/.rasdrsa").header("etag", "252452525").header("X-Custom-Header", "2134_aDFahdrAJDFIYa").when().post("/call/with/headers").then().and().assertThat().statusCode(203);
    }

    @Test
    public void testPutWithQueryParameters() {
        given().that().baseUri("http://localhost").and().port(8080).and().basePath("/presentation").param("search", "Some_text").when().put("/call/with/query/parameters").then().and().assertThat().statusCode(223);
    }
}

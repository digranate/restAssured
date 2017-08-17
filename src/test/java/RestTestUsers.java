import com.jayway.restassured.RestAssured;
import com.jayway.restassured.filter.log.LogDetail;
import org.junit.BeforeClass;
import org.junit.Test;
import pojos.User;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

/**
 * Created by Dinara.Trifanova on 8/15/2017.
 */
public class RestTestUsers extends RestTest {
    @BeforeClass
    public static void setUp() {
        RestAssured.baseURI = "http://jsonplaceholder.typicode.com";
        RestAssured.basePath = "/users";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails(LogDetail.ALL);
    }

    @Test
    public void testUsersCheckAllFields() {
        int userId = 5;
        User user = given().that()
                .when()
                .pathParam("userId", userId)
                .get("/{userId}")
                .then()
                .extract().as(User.class);
        assertEquals(userId, (int) user.getId());
        assertEquals("Chelsey Dietrich", user.getName());
        assertEquals("Keebler LLC", user.getCompany().getName());
    }

    @Test
    public void testUsersNegativeNonExistingUser() {
        int userId = 100000;
        given().that()
                .when()
                .pathParam("userId", userId)
                .get("/{userId}")
                .then()
                .statusCode(404);
    }


}

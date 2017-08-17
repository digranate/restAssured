import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;

/**
 * Created by Dinara.Trifanova on 8/15/2017.
 */
public abstract class RestTest {
    @Test
    public void testGetSuccessStatus() {
        given().that().when().then()
                .statusCode(200);
    }

    @Test
    public void testExecutionTime() {
        given().that().when().then()
                .time(lessThan(2L), TimeUnit.SECONDS); // Milliseconds;
    }

}

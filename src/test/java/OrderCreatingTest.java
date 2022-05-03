import io.restassured.response.Response;
import model.Order;
import org.apache.http.HttpStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class OrderCreatingTest extends BaseTest{

    private final List<String> colors;

    public OrderCreatingTest(List<String> colors) {
        this.colors = colors;
    }

    @Parameters
    public static Object[][] colorParameters() {
        return new Object[][] {
                { List.of() },
                { List.of("BLACK") },
                { List.of("GREY") },
                { List.of("BLACK", "GREY") },
        };
    }

    @Test
    public void createOrder() {
        Order order = new Order("Wolf",
                "The Grey",
                "Miracle forest",
                "Under the Oak",
                "+7 800 755 25 65",
                6,
                "2022-06-06",
                "some comments",
                colors);

        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(order)
                .when()
                .post("/api/v1/orders");

        response.then().assertThat().body("track", notNullValue())
                .and()
                .statusCode(HttpStatus.SC_CREATED);
    }

}
import org.apache.http.HttpStatus;
import org.junit.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.isA;

public class OrdersListTest extends BaseTest{

    @Test
    public void getListOfOrder() {
        given()
                .get("/api/v1/orders")
                .then().assertThat().body("orders", isA(List.class))
                .and()
                .statusCode(HttpStatus.SC_OK);
    }
}

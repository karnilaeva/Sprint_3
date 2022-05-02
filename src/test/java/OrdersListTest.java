import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.isA;

public class OrdersListTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @Test
    public void getListOfOrder() {
        given()
                .get("/api/v1/orders")
                .then().assertThat().body("orders", isA(List.class))
                .and()
                .statusCode(200);
    }
}

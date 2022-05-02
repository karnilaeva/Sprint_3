import io.restassured.RestAssured;
import io.restassured.response.Response;
import json.Courier;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class CourierCreatingTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @Test
    public void successfulCreating() {
        Courier courier = new Courier(UUID.randomUUID().toString(), "12345", "John");

        Response response = Util.createCourier(courier);

        response.then().assertThat().body("ok", equalTo(true))
                .and()
                .statusCode(201);

        Util.deleteCourier(courier);
    }

    @Test
    public void doNotCreateCourierTwice() {
        Courier courier = new Courier(UUID.randomUUID().toString(), "12345", "John");


        Response firstResponse = Util.createCourier(courier);
        Response secondResponse = Util.createCourier(courier);

        firstResponse.then().assertThat().body("ok", equalTo(true))
                .and()
                .statusCode(201);

        secondResponse.then().assertThat().body("message", equalTo("Этот логин уже используется"))
                .and()
                .statusCode(409);

        Util.deleteCourier(courier);
    }

    @Test
    public void doNotCreateCourierWithoutLogin() {
        Courier courier = new Courier(null, "12345", "John");

        Response firstResponse = Util.createCourier(courier);

        firstResponse.then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400);
    }

    @Test
    public void doNotCreateCourierWithoutPassword() {
        Courier courier = new Courier("Moisha", null, "John");

        Response firstResponse = Util.createCourier(courier);

        firstResponse.then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400);
    }

    @Test
    public void doNotCreateEmptyCourier() {
        Courier courier = new Courier();

        Response firstResponse = Util.createCourier(courier);

        firstResponse.then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400);
    }

}

import io.restassured.RestAssured;
import io.restassured.response.Response;
import json.Courier;
import json.CourierLogin;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class CourierLoginTest {

    Courier courier;

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @Before
    public void createCourier() {
        this.courier = new Courier(UUID.randomUUID().toString(), "12345", "John");

        Response response = Util.createCourier(this.courier);
        response.then().assertThat().body("ok", equalTo(true));
    }

    @After
    public void deleteCourier() {
        Util.deleteCourier(courier);
    }

    @Test
    public void successfulLogin() {
        CourierLogin courierLogin = new CourierLogin(courier.getLogin(), courier.getPassword());

        Response response = courierAuth(courierLogin);

        response.then().assertThat().body("id", notNullValue())
                .and()
                .statusCode(200);
    }

    @Test
    public void doNotAuthCourierWithoutLogin() {
        CourierLogin courierLogin = new CourierLogin(null, courier.getPassword());

        Response response = courierAuth(courierLogin);

        response.then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
    }

    @Test
    public void doNotAuthCourierWithoutPassword() {
        CourierLogin courierLogin = new CourierLogin(courier.getLogin(), null);

        Response response = courierAuth(courierLogin);

        response.then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
    }

    @Test
    public void doNotAuthCourierWithWrongLoginAndPassword() {
        CourierLogin courierLogin = new CourierLogin(courier.getLogin(), "12345111");

        Response response = courierAuth(courierLogin);

        response.then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
    }

    @Test
    public void loginNonExistentCourier() {
        CourierLogin courierLogin = new CourierLogin("Makar", "1234598");

        Response response = courierAuth(courierLogin);

        response.then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
    }

    private Response courierAuth(CourierLogin courierLogin) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(courierLogin)
                .when()
                .post("/api/v1/courier/login");
    }

}

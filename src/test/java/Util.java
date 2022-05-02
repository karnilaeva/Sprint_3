import io.restassured.response.Response;
import json.Courier;
import json.CourierLogin;

import static io.restassured.RestAssured.given;

class Util {

    public static Response createCourier(Courier courier) {
        System.out.println("Trying to create a courier. Login: " + courier.getLogin() + ", Password: " + courier.getPassword());
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier");
    }

    public static void deleteCourier(Courier courier) {
        CourierLogin courierLogin = new CourierLogin(courier);

        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(courierLogin)
                .when()
                .post("/api/v1/courier/login");

        int id = response.jsonPath().getInt("id");

        given().delete("/api/v1/courier/" + id);
    }

}

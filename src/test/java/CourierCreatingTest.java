import io.restassured.response.Response;
import model.Courier;
import org.apache.http.HttpStatus;
import org.junit.Test;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.equalTo;

public class CourierCreatingTest extends BaseTest{

    @Test
    public void successfulCreating() {
        Courier courier = new Courier(UUID.randomUUID().toString(), "12345", "John");

        Response response = Util.createCourier(courier);

        response.then().assertThat().body("ok", equalTo(true))
                .and()
                .statusCode(HttpStatus.SC_CREATED);

        Util.deleteCourier(courier);
    }

    @Test
    public void doNotCreateCourierTwice() {
        Courier courier = new Courier(UUID.randomUUID().toString(), "12345", "John");


        Response firstResponse = Util.createCourier(courier);
        Response secondResponse = Util.createCourier(courier);

        firstResponse.then().assertThat().body("ok", equalTo(true))
                .and()
                .statusCode(HttpStatus.SC_CREATED);

        secondResponse.then().assertThat().body("message", equalTo("Этот логин уже используется"))
                .and()
                .statusCode(HttpStatus.SC_CONFLICT);

        Util.deleteCourier(courier);
    }

    @Test
    public void doNotCreateCourierWithoutLogin() {
        Courier courier = new Courier(null, "12345", "John");

        Response firstResponse = Util.createCourier(courier);

        firstResponse.then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    public void doNotCreateCourierWithoutPassword() {
        Courier courier = new Courier("Moisha", null, "John");

        Response firstResponse = Util.createCourier(courier);

        firstResponse.then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    public void doNotCreateEmptyCourier() {
        Courier courier = new Courier();

        Response firstResponse = Util.createCourier(courier);

        firstResponse.then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

}

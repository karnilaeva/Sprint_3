import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class OrderCreatingTest {

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

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }


    @Test
    public void createOrder() {
        Order order = new Order(colors);

        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(order)
                .when()
                .post("/api/v1/orders");

        response.then().assertThat().body("track", notNullValue())
                .and()
                .statusCode(201);
    }

    private static class Order {

        private String firstName = "Wolf";
        private String lastName = "The Grey";
        private String address = "Miracle forest";
        private String metroStation = "Under the Oak";
        private String phone = "+7 800 755 25 65";
        private Integer rentTime = 6;
        private String deliveryDate = "2022-06-06";
        private String comment = "some comment";
        private List<String> colors;

        public Order() {
        }

        private Order(List<String> colors) {
            this.colors = colors;
        }

        public List<String> getColors() {
            return colors;
        }

        public void setColors(List<String> colors) {
            this.colors = colors;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getMetroStation() {
            return metroStation;
        }

        public void setMetroStation(String metroStation) {
            this.metroStation = metroStation;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public Integer getRentTime() {
            return rentTime;
        }

        public void setRentTime(Integer rentTime) {
            this.rentTime = rentTime;
        }

        public String getDeliveryDate() {
            return deliveryDate;
        }

        public void setDeliveryDate(String deliveryDate) {
            this.deliveryDate = deliveryDate;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }
    }
}
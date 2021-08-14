
package factoryRequest;

import io.restassured.response.Response;
import util.ConfigEnv;

import static io.restassured.RestAssured.given;

public class RequestGET implements IRequest{
    @Override
    public Response send(RequestInformation information) {
        Response response=given()
                .header("Authorization", "Basic "+ ConfigEnv.AuthenticacionBasica)
                .log()
                .all()
                .when()
                .get(information.getUrl());
        response.then()
                .log()
                .all();
        return response;
    }
}
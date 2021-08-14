
package testClean;

import factoryRequest.FactoryRequest;
import factoryRequest.RequestInformation;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import util.ConfigAPI;
import util.ConfigEnv;
import util.GetProperties;

import static io.restassured.RestAssured.given;
import java.io.IOException;
import static org.hamcrest.CoreMatchers.equalTo;

public class CRUDUserItemTest {

    @BeforeEach
    public void before() throws IOException {
        new GetProperties().leerPropiedades();
    }

    @Test
    public void verifyCRUDCreateUser() throws IOException {
        new GetProperties().leerPropiedades();

        JSONObject body = new JSONObject();
        body.put("Email", ConfigEnv.user);
        body.put("Password", "12345");
        body.put("FullName", "4FinalEquipo4@gmail.com");

        RequestInformation request = new RequestInformation(ConfigAPI.CREATE_USER, body.toString());
        Response response = FactoryRequest.make(FactoryRequest.POST2).send(request);
        response.then()
                .statusCode(200)
                .body("Email", equalTo(ConfigEnv.user));


        request = new RequestInformation(ConfigAPI.GET_USER.replace("USER", "user"), "");
        response = FactoryRequest.make(FactoryRequest.GET).send(request);

        String id = response.then().extract().path("Id") + "";
//Actualizacion
        body = new JSONObject();
        body.put("Email", "UPDATE"+ConfigEnv.user);

        request = new RequestInformation(ConfigAPI.UPDATE_USER.replace("ID", id), body.toString());
        response = FactoryRequest.make(FactoryRequest.PUT).send(request);

        response.then()
                .statusCode(200)
                .body("Email", equalTo( "UPDATE"+ConfigEnv.user));
        //ConfigEnv.AuthenticacionBasica = ConfigEnv.AuthenticacionBasicaNuevo;

    }


    @Test
    public void verifyCRUDforItem() throws IOException {
        new GetProperties().leerPropiedades();
        ConfigEnv.AuthenticacionBasica = ConfigEnv.AuthenticacionBasicaNuevo;
        JSONObject body = new JSONObject();
        body.put("Content","Equipo4");

        RequestInformation request = new RequestInformation(ConfigAPI.CREATE_ITEM,body.toString());
        Response response = FactoryRequest.make(FactoryRequest.POST).send(request);
        response.then()
                .statusCode(200)
                .body("Content", equalTo("Equipo4"));
        String id = response.then().extract().path("Id")+"";

        body.put("Content","Equipo4");
        body.put("Checked",true);

        request = new RequestInformation(ConfigAPI.UPDATE_ITEM.replace("ID",id),body.toString());
        response = FactoryRequest.make(FactoryRequest.PUT).send(request);

        response.then()
                .statusCode(200)
                .body("Content", equalTo("Equipo4"))
                .body("Checked", equalTo(true));

        request = new RequestInformation(ConfigAPI.READ_ITEM.replace("ID",id),"");
        response = FactoryRequest.make(FactoryRequest.GET).send(request);

        response.then()
                .statusCode(200)
                .body("Content", equalTo("Equipo4"))
                .body("Checked", equalTo(true));

        request = new RequestInformation(ConfigAPI.DELETE_ITEM.replace("ID",id),"");
        response = FactoryRequest.make(FactoryRequest.DELETE).send(request);

        response.then()
                .statusCode(200)
                .body("Content", equalTo("Equipo4"))
                .body("Checked", equalTo(true))
                .body("Deleted", equalTo(true));

    }


}

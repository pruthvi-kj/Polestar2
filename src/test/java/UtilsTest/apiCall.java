package UtilsTest;

import Polestar.DataMembers.FuelPrices;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.sql.Timestamp;
import java.util.Date;

import static io.restassured.RestAssured.given;

public class apiCall {
    public static String baseURL = "https://pc-api-staging.polestar.com";
    private static Response response;

    public static FuelPrices getFuelPrice(String stateCode) throws FileNotFoundException {
        PrintStream stream = new PrintStream(new FileOutputStream("TestResults/log" + new Timestamp(new Date().getTime()) + ".txt"));
        RequestSpecification reqSpec = new RequestSpecBuilder().setBaseUri(baseURL)
                .addQueryParam("query", "query GetFuelById($fuelId: String!, $electricityId: String!) {  getFuelById(id: $fuelId) {    id    diesel {      date      price      __typename    }    gasoline {      date      price      __typename    }    __typename  }  getElectricityById(id: $electricityId) {    id    average    __typename  }}")
                .addQueryParam("operationName", "GetFuelById")
                .addFilter(RequestLoggingFilter.logRequestTo(stream))
                .addFilter(ResponseLoggingFilter.logResponseTo(stream))
                .build();

        ResponseSpecification responseSpecification = new ResponseSpecBuilder().expectStatusCode(200).build();

        response = given().queryParam("variables", "{\"fuelId\":\""+stateCode+"\",\"electricityId\":\"US\"}")
                .spec(reqSpec).when().get("eu-north-1/energy-prices").then().spec(responseSpecification).extract().response();

        JsonPath js= new JsonPath(response.asString());
        String date = js.get("ServicePoints.getFuelById.gasoline.date");
        String stateCodeResponse = js.get("ServicePoints.getFuelById.id");

        if(stateCodeResponse.equalsIgnoreCase(stateCode)){
            return new FuelPrices(js.get("ServicePoints.getElectricityById.average"),js.get("ServicePoints.getFuelById.gasoline.price"));
        }
        return null;
    }
}

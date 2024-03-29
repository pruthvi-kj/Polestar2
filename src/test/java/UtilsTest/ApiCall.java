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
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Timestamp;
import java.util.Date;

import static UtilsTest.Utils.getValue;
import static io.restassured.RestAssured.given;

public class ApiCall {

    private static Response response;

    public static FuelPrices getFuelPrice(String stateCode) throws IOException {
        PrintStream stream = new PrintStream(FileUtils.openOutputStream(new File("TestResults/log" + new Timestamp(new Date().getTime()) + ".txt")));
        RequestSpecification reqSpec = new RequestSpecBuilder().setBaseUri(getValue("RetrieveFuelPriceBaseUrl"))
                .addQueryParam("query", "query GetFuelById($fuelId: String!, $electricityId: String!) {  getFuelById(id: $fuelId) {    id    diesel {      date      price      __typename    }    gasoline {      date      price      __typename    }    __typename  }  getElectricityById(id: $electricityId) {    id    average    __typename  }}")
                .addQueryParam("operationName", "GetFuelById")
                .addFilter(RequestLoggingFilter.logRequestTo(stream))
                .addFilter(ResponseLoggingFilter.logResponseTo(stream))
                .build();

        ResponseSpecification responseSpecification = new ResponseSpecBuilder().expectStatusCode(200).build();

        response = given().queryParam("variables", "{\"fuelId\":\"" + stateCode + "\",\"electricityId\":\"US\"}")
                .spec(reqSpec).when().get("eu-north-1/energy-prices").then().spec(responseSpecification).extract().response();

        JsonPath js = new JsonPath(response.asString());
        String date = js.get("data.getFuelById.gasoline.date");
        String stateCodeResponse = js.get("data.getFuelById.id");

        if (stateCodeResponse.equalsIgnoreCase(stateCode)) {
            return new FuelPrices(js.get("data.getElectricityById.average"), js.get("data.getFuelById.gasoline.price"));
        }
        return null;
    }
}

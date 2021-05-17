package assessment.base;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import assessment.cucumber.cucumberHooks;

import java.util.HashMap;

public class BaseAPI {

    private static Logger logger = LoggerFactory.getLogger(BaseAPI.class);
    private static Integer statusCode = 0;
    private static String endpoint = null;
    public static Response response;
    public static Response responseGet;
    public static JSONObject request;

    public static void getRequest(String resource) {
        endpoint = resource;
        logger.info("\n URL: " + cucumberHooks.baseURL + endpoint);
        HashMap<String, String> headerMap = new HashMap<>();
        headerMap.put("Content-Type", "application/json");
        try {
            RestAssured.useRelaxedHTTPSValidation();
            RestAssured.baseURI = cucumberHooks.baseURL;
            responseGet = RestAssured.given()
                    .headers(headerMap)
                    .get(endpoint)
                    .then()
                    .extract()
                    .response();
            statusCode = responseGet.getStatusCode();

        } catch (Exception e) {
            logger.error("\n Error: " + e.getMessage());
        }
    }

    public static void assertResponseStatus(Integer statusCode) {
        Assert.assertEquals(endpoint + " has failed, expected: ", statusCode, BaseAPI.statusCode);
        logger.info("\n Api resource path " +endpoint + " status: " + BaseAPI.statusCode + "\n");
    }

    public static void assertJsonAttributeGetResponse(String attribute, String expectedValue) {
        JsonPath jsonPathEvaluator = responseGet.jsonPath();
        String attributeValue = jsonPathEvaluator.get(attribute);
        logger.info("\n Expected value: " + expectedValue + ", Actual value: " + attributeValue);
        Assert.assertEquals(attributeValue,expectedValue);
    }

    protected static void assertJsonIntegerAttributeGetResponse(String attribute, Integer expectedValue) {
        JsonPath jsonPathEvaluator = responseGet.jsonPath();
        Integer attributeValue = jsonPathEvaluator.get(attribute);
        logger.info("\n Expected value: " + expectedValue + ", Actual value: " + attributeValue);
        Assert.assertEquals(attributeValue,expectedValue);
    }
}


package assessment.steps;

import assessment.responses.UserIdResponse;
import assessment.utils.ReadData;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import assessment.base.BaseAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static com.jayway.jsonpath.JsonPath.parse;

public class TechnicalTestSteps {

    private static Logger logger = LoggerFactory.getLogger(TechnicalTestSteps.class);

    //Request using City
    @When("the request is performed using city \"([^\"]*)\"$")
    public void requestByCity(String city) throws IOException {
        String endpoint = ReadData.readDataFromPropertyFile( "getcity.endpoint")
               + city +"/users";
        BaseAPI.getRequest(endpoint);
    }

    @And ("verify that the response includes the data for \"([^\"]*)\"$")
    public void responseByCity(String city) throws IOException {
        // BaseAPI.responseGet.prettyPrint();
        UserIdResponse.assertSuccesfullListUsersbyCity(city);
    }

    //*************************************************************************
    //Request get all users
    @When("the request is performed to get all users")
    public void requestAllUsers() throws IOException {
        String endpoint = ReadData.readDataFromPropertyFile("getlistofusers.endpoint");
        BaseAPI.getRequest(endpoint);
    }

    @And("the response does not include null id field")
    public void assertAllUsersIdNotNull() throws IOException {
        UserIdResponse.assertSuccesfullListUsers();
    }

    @Then("^response should be (\\d+)$")
    public void responseShouldBe(int statusCode) {
        BaseAPI.assertResponseStatus(statusCode);
      //  logger.info("Response is successful");
      }

    @When("get all users within 50 miles radius of london")
    public void getUserswithin50milesoflondon() throws IOException {
//        UserIdResponse.parseLondonUsers();

    }


} //end of Class
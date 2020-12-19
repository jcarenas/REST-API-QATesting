package com.jcarenas.gherkinDefinitions;

import com.jcarenas.serenitySteps.ExampleSteps;
import cucumber.api.DataTable;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.Response;
import net.serenitybdd.core.Serenity;
import net.thucydides.core.annotations.Steps;

import java.util.Calendar;

public class ExampleDefinitions {

    @Steps
    private ExampleSteps exampleSteps;

    /**
     * Method executed before each scenario to start measuring execution times
     *
     * @param scenario Scenario object to check if the scenario contains the tag to write on InfluxDB
     */
    @Before
    public void startInfluxdb(Scenario scenario) {
        if (scenario.getSourceTagNames().contains("@influxdb"))
            Serenity.setSessionVariable("startTime").to(Calendar.getInstance());
    }


    @Then("I should get (.*) status code")
    public void iShouldGetStatusCode(int expectedStatusCode) {
        exampleSteps.verifyStatusCode(expectedStatusCode);
    }

    @And("^The value for the \"([^\"]*)\" after (get|post|update|delete|put) operation should be \"([^\"]*)\"$")
    public void theValueForTheAfterGetOperationShouldBe(String key, String operation, String expectedValue) {
        Response res = Serenity.sessionVariableCalled("response");
        exampleSteps.verifyValueFromKey(res, operation, key, expectedValue);
    }


    /**
     * Arquetype BE V2
     */

    @Given("^baseUri is (.*)$")
    public void baseUri(String uri) {
        exampleSteps.setTheUri(uri);
    }

    @When("^I set the uri to \"([^\"]*)\"$")
    public void iSetTheUriTo(String uri) {
        exampleSteps.setTheUri(uri);
    }

    @And("^I set the path to \"([^\"]*)\"$")
    public void iSetThePathTo(String path) {
        exampleSteps.setThePath(path);
    }

    @And("^I set method to (put|PUT|post|POST|delete|DELETE) a user by ID \"([^\"]*)\"$")
    public void iSetMethodToAUserByID(String operation, String user) {
        exampleSteps.setOperationToUser(operation, user);
    }

    @And("^I set method to (get|GET|put|PUT|post|POST|delete|DELETE)$")
    public void iSetMethodToAUserByID(String operation) {
        if (operation.toLowerCase().equals("delete")) {
            exampleSteps.setPathParameter();
        }
        exampleSteps.setOperation(operation);
    }

    /**
     * Methods to set headers
     */

    @And("^I set headers with$")
    public void iSetHeadersWith(DataTable dataTable) {
        exampleSteps.setHeaders(dataTable);
    }

    @Given("^I set (.*) header to (.*)$")
    public void header(String headerName, String headerValue) {
        exampleSteps.setHeader(headerName, headerValue);
    }

    /**
     * Methods to set body
     */

    @When("^I set (.*) to body$")
    public void setToBody(String body) {
        exampleSteps.setBody(body);
    }

    @When("^I set body \"([^\"]*)\"$")
    public void setBody(String body) {
        exampleSteps.setBody(body);
    }

    @When("^I set the body to \"([^\"]*)\"$")
    public void setBodyTo(String endpoint, String body) {
        exampleSteps.setBodyTo(endpoint, body);
    }

    @And("^I set body with this json$")
    public void iSetBodyWithThisJson(String jsonString) {
        exampleSteps.sendRequestWithJson(jsonString);
    }

    @And("^I set body with$")
    public void iSetBodyWithTable(DataTable dataTable) {
        exampleSteps.setBodyWithTable(dataTable);
    }

    @And("^I set query parameter (.*) to (.*)$")
    public void iSetQueryParameterTo(String value, String key) {
        exampleSteps.setQueryParameter(key, value);
    }

    @And("^I set query parameter$")
    public void iSetQueryParameter(DataTable dataTable) {
        exampleSteps.setQueryParameter(dataTable);
    }


    /**
     * Methods to validate response
     */

    @And("^I execute the request$")
    public void iExecuteTheRequest() {
        exampleSteps.executeRequest();
    }

    @And("^(?:the |)response header \"([^\"]*)\" should be \"([^\"]*)\"$")
    public void theResponseHeaderShouldBe(String headerName, String headerValue) {
        exampleSteps.responseHeaderShouldBe(headerName, headerValue);
    }

    @And("^(?:the |)response body should be valid json$")
    public void responseBodyShouldBeValidJson() {
        exampleSteps.checkJsonBody();
    }

    @And("^(?:the |)response should be json$")
    public void theResponseShouldBeJson(String jsonResponseString) {
        exampleSteps.responseShouldBeJson(jsonResponseString);
    }

    @And("^(?:the |)response content type should be \"([^\"]*)\"$")
    public void responseContentTypeShouldBe(String contentType) {
        exampleSteps.responseContentTypeShouldBe(contentType);
    }

    @And("^(?:the |)response json path element (.*) should be (.*)$")
    public void responseJsonPathElementShouldBe(String jsonPath, int value) {
        exampleSteps.responseJsonPathElementShouldBe(jsonPath, value);
    }

    @And("^(?:the |)response json path element (.*) must be positive$")
    public void responseJsonPathElementShouldBePositive(String jsonPath) {
        exampleSteps.responseJsonPathElementShouldBePositive(jsonPath);
    }

    @Then("^(?:the |)response content status is (equal|greater|less|size|greaterSize|lessSize)(?:to|than| )+(.*)$")
    public void theResponseContentStatusIs(String operationCompare, int codeStatus) {
        exampleSteps.responseContentStatusIs(operationCompare, codeStatus);
    }

    @And("^(?:the |)response should be json in file \"([^\"]*)\"$")
    public void responseShouldBeJsonInFile(String file) throws Throwable {
        exampleSteps.responseShouldBeJsonInFile(file);
    }

    @And("^(?:the |)response should be empty$")
    public void theResponseShouldBeEmpty() throws Throwable {
        exampleSteps.responseShouldBeEmpty();
    }

    @And("^(?:the |)response collection should be json$")
    public void theResponseCollectionShouldBeJson(String jsonResponseString) {
        exampleSteps.responseCollectionShouldBeJson(jsonResponseString);
    }

    @Then("^(?:the |)response code is (.*)$")
    public void theResponseCodeIs(int responseCode) {
        exampleSteps.verifyStatusCode(responseCode);
    }

    @And("^I clear request header$")
    public void iClearRequestHeader() {
        exampleSteps.clearRequestHeader();
    }

    @And("^I clear request body$")
    public void iClearRequestBody() {
        exampleSteps.clearRequestBody();
    }

    @And("^I save pet id in SerenityBDD session$")
    public void savePetIdInSerenityBDDSession() {
        exampleSteps.saveBodyValueInSession();
    }

    @And("^the response with json path element (.*) should be present in session variable (.*)$")
    public void theResponseWithJsonPathElementElementKeyShouldBePresentInSessionVariableSessionKey(String elementKey, String sessionKey) {
        exampleSteps.checkElementDeleted(elementKey, sessionKey);
    }
}

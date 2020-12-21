package com.jcarenas.gherkinDefinitions;

import com.jcarenas.serenitySteps.ExampleSteps;
import cucumber.api.Scenario;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import net.serenitybdd.core.Serenity;
import net.thucydides.core.annotations.Steps;

import java.util.Calendar;

public class ExampleDefinitions {

    @Steps
    private ExampleSteps exampleSteps;


    @Then("I should get {int} status code")
    public void iShouldGetStatusCode(int expectedStatusCode) {
        exampleSteps.verifyStatusCode(expectedStatusCode);
    }

    @And("The value for the \"([^\"]*)\" after (get|post|update|delete|put) operation should be \"([^\"]*)\"$")
    public void theValueForTheAfterGetOperationShouldBe(String key, String operation, String expectedValue) {
        Response res = Serenity.sessionVariableCalled("response");
        exampleSteps.verifyValueFromKey(res, operation, key, expectedValue);
    }

    @Given("^baseUri is (.*)$")
    public void baseUri(String uri) {
        exampleSteps.setTheUri(uri);
    }

    @When("^I set the uri to \"([^\"]*)\"$")
    public void iSetTheUriTo(String uri) {
        exampleSteps.setTheUri(uri);
    }

    @And("I set the path to {word}")
    public void iSetThePathTo(String path) {
        exampleSteps.setThePath(path);
    }

    @And("^I set method to (get|GET|put|PUT|post|POST)$")
    public void iSetRequestMethod(String operation) {
        exampleSteps.setOperation(operation);
    }

    @And("^I set method to (delete|DELETE) element with pathParameter (.*) and session id key (.*)$")
    public void iSetDeletedRequestMethod(String operation, String pathParameter, String sessionKey) {
        exampleSteps.setPathParameterWithSessionValue(pathParameter, sessionKey);
        exampleSteps.setOperation(operation);
    }

    /**
     * Methods to set headers
     */
    @Given("I set {word} header to {word}")
    public void header(String headerName, String headerValue) {
        exampleSteps.setHeader(headerName, headerValue);
    }

    /**
     * Methods to set body
     */

    @When("I set {} to body")
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

    @And("^I set query parameter (.*) to (.*)$")
    public void iSetQueryParameterTo(String value, String key) {
        exampleSteps.setQueryParameter(key, value);
    }


    /**
     * Methods to validate response
     */

    @And("^I execute the request$")
    public void iExecuteTheRequest() {
        exampleSteps.executeRequest();
    }

    @And("the response header {word} should be {word}")
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

    @And("response json path element {word} should be {word}")
    public void responseJsonPathElementShouldBe(String jsonPath, String value) {
        exampleSteps.responseJsonPathElementShouldBe(jsonPath, value);
    }

    @And("response json path element {word} must be positive")
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
    public void theResponseShouldBeEmpty() {
        exampleSteps.responseShouldBeEmpty();
    }

    @And("^(?:the |)response collection should be json$")
    public void theResponseCollectionShouldBeJson(String jsonResponseString) {
        exampleSteps.responseCollectionShouldBeJson(jsonResponseString);
    }

    @Then("the response code is {int}")
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

    @And("I save {word} in SerenityBDD {word} session")
    public void savePetIdInSerenityBDDSession(String bodyJsonPath, String sessionKey) {
        exampleSteps.saveBodyValueInSession(bodyJsonPath, sessionKey);
    }

    @And("^the response with json path element (.*) should be present in session variable (.*)$")
    public void theResponseWithJsonPathElementElementKeyShouldBePresentInSessionVariableSessionKey(String elementKey, String sessionKey) {
        exampleSteps.checkElementPresentInSession(elementKey, sessionKey);
    }
}

package com.jcarenas.serenitySteps;

import com.jcarenas.config.Configuration;
import com.jcarenas.support.ServicesSupport;
import cucumber.api.DataTable;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.serenitybdd.core.Serenity;
import net.thucydides.core.annotations.Step;
import org.json.JSONObject;
import org.junit.Assert;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.serenitybdd.rest.SerenityRest.rest;

public class ExampleSteps {

    private ServicesSupport servicesSupport = new ServicesSupport();

    private RequestSpecification spec = rest().contentType(ContentType.JSON).when();

    private RequestSpecification requestSpecification = rest();

    private String path;
    private String operation;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    /**
     * Method to verify an status code received from the scenario
     *
     * @param expectedStatusCode Expected status code in the response
     */
    @Step
    public void verifyStatusCode(int expectedStatusCode) {
        Response res = Serenity.sessionVariableCalled(Configuration.SESSION_RESPONSE);
        Assert.assertEquals("status code doesn't match", expectedStatusCode, res.getStatusCode());
    }

    /**
     * Method to verify an status code received from the scenario
     *
     * @param res           Response object from a previous operation
     * @param operation     The operation that was done in a previous step received from Cucumber
     * @param key           Attribute name received from the scenario as a parameter
     * @param expectedValue Expected value of the attribute received from the scenario as a parameter
     */
    @Step
    public void verifyValueFromKey(Response res, String operation, String key, String expectedValue) {

        String currentValue = "";

        switch (operation.toLowerCase()) {
            case "get":
                //currentValue = res.getBody().jsonPath().getString("data." + key);
                currentValue = res.getBody().jsonPath().getString(key);
                break;
            case "post":
                currentValue = res.getBody().jsonPath().getString(key);
                break;
            case "update":
            case "put":
                currentValue = res.getBody().jsonPath().getString(key);
                break;
            case "delete":
                currentValue = res.getBody().jsonPath().getString("data." + key);
                break;
            default:
                break;
        }

        Assert.assertEquals("Value for " + key + " doesn't match. \nResponse was \n" + res.jsonPath().prettyPrint(), expectedValue, currentValue);
    }

    /**
     * STEPS ADDED IN ARCHETYPE V2
     */

    /**
     * Set URI to ResquestSpecification
     *
     * @param uri Name URI
     */
    public void setTheUri(String uri) {
        this.requestSpecification.baseUri(uri);
    }

    /**
     * Set path o endpoint to ResquestSpecification
     *
     * @param path Path or endpoint
     */
    @Step
    public void setThePath(String path) {
        this.setPath(path);
    }

    /**
     * Set operation to ResquestSpecification
     *
     * @param operation Type operation
     * @param user      User to apply operation
     */
    @Step
    public void setOperationToUser(String operation, String user) {
        this.setOperation(operation);
        this.setPath(this.getPath() + "/" + user);
    }

    /**
     * Add http headers
     *
     * @param dataTable Datatable with values (header name and header value)
     */
    @Step
    public void setHeaders(DataTable dataTable) {
        List<List<String>> data = dataTable.raw();
        //Get values from datatable
        String headerName = data.get(0).get(1);
        String headerValue = data.get(0).get(1);
        this.headers(headerName, headerValue);
    }

    /**
     * Add an http header
     *
     * @param headerName  Header name
     * @param headerValue Header value
     */
    @Step
    public void setHeader(String headerName, String headerValue) {
        Assert.assertNotNull(headerName);
        Assert.assertNotNull(headerValue);
        this.headers(headerName, headerValue);
    }

    /**
     * Method to set contentType to ResquestSpecification
     *
     * @param headerName  Name of header
     * @param headerValue Value from header
     */
    private void headers(String headerName, String headerValue) {
        if (headerName.equalsIgnoreCase("Content-Type")) {
            switch (headerValue) {
                case "application/json":
                    this.requestSpecification.contentType(ContentType.JSON);
                    break;
                case "application/xml":
                    this.requestSpecification.contentType(ContentType.XML);
                    break;
                case "text/html":
                    this.requestSpecification.contentType(ContentType.HTML);
                    break;
                case "text/plain":
                    this.requestSpecification.contentType(ContentType.TEXT);
                    break;
                default:
                    break;
            }
        } else if (headerName.equalsIgnoreCase("Accept")) {
            switch (headerValue) {
                case "application/json":
                    this.requestSpecification.contentType(ContentType.JSON);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Set the request body
     * A json string structure is accepted
     * The body will be parse to be sure the json is valid
     *
     * @param body Body to send
     */
    @Step
    public void setBody(String body) {
        Assert.assertNotNull(body);
        Assert.assertTrue(!body.isEmpty());
        this.requestSpecification.body(body);
    }

    /**
     * @param endPoint
     * @param body
     */
    @Step
    public void setBodyTo(String endPoint, String body) {
        Assert.assertNotNull(body);
        Assert.assertTrue(!body.isEmpty());
        Assert.assertNotNull(endPoint);
        Assert.assertTrue(!endPoint.isEmpty());
        this.setPath(endPoint);
        this.requestSpecification.body(body);
    }

    /**
     * Set a request body with a Json
     *
     * @param json JSON in String format
     */
    @Step
    public void sendRequestWithJson(String json) {
        JSONObject body = new JSONObject(json);
        this.requestSpecification.body(body.toMap());
    }

    /**
     * Set a request body with Datatable
     *
     * @param dataTable Datatable with fields/values
     */
    @Step
    public void setBodyWithTable(DataTable dataTable) {
        List<List<String>> data = dataTable.raw();
        Map<String, Object> map = new HashMap<>();
        for (List<String> list : data) {
            map.put(list.get(0), list.get(1));
        }
        JSONObject body = new JSONObject(map);
        this.requestSpecification.body(body.toMap());
    }

    /**
     * Add a list of query parameter to the url
     *
     * @param key   Name of field
     * @param value Value from field
     */
    @Step
    public void setQueryParameter(String key, String value) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);
        JSONObject body = new JSONObject(map);
        this.requestSpecification.body(body.toMap());
    }

    /**
     * Add a list of query parameter to the url
     *
     * @param dataTable DataTable of parameters
     */
    @Step
    public void setQueryParameter(DataTable dataTable) {
        JSONObject body = new JSONObject();
        for (Map<String, String> data : dataTable.asMaps(String.class, String.class)) {
            body.put(data.get("param"), data.get("value"));
        }
        this.requestSpecification.body(body.toMap());
    }

    /**
     * Method to execute request
     */
    @Step
    public void executeRequest() {
        Response response = servicesSupport.executeRequest(this.requestSpecification, this.getOperation(), this.path);
        Serenity.setSessionVariable(Configuration.SESSION_RESPONSE).to(response);
    }

    /**
     * Test if a given header value is matching the expected value
     *
     * @param headerName  Name of the header to find
     * @param headerValue Expected value of the header
     */
    @Step
    public void responseHeaderShouldBe(String headerName, String headerValue) {
        Response response = Serenity.sessionVariableCalled(Configuration.SESSION_RESPONSE);
        Assert.assertTrue(response.getHeaders().get(headerName).getName().equalsIgnoreCase(headerName));
        Assert.assertTrue(response.getContentType().contains(headerValue));
    }

    /**
     * Test response body is json typed
     */
    @Step
    public void checkJsonBody() {
        Response response = Serenity.sessionVariableCalled(Configuration.SESSION_RESPONSE);
        String body = response.getBody().toString();
        Assert.assertNotNull(body);
        Assert.assertTrue(!body.isEmpty());
    }

    /**
     * Test response body
     *
     * @param jsonResponseString JSON response in String format to check
     */
    @Step
    public void responseShouldBeJson(String jsonResponseString) {
        Response response = Serenity.sessionVariableCalled(Configuration.SESSION_RESPONSE);
        String stringBody = response.getBody().asString();
        String cleanBody = stringBody.replaceAll("\\s", "");
        Assert.assertEquals("Response is " + cleanBody, jsonResponseString.replaceAll("\\s", ""), cleanBody);
    }

    /**
     * Test response content type
     *
     * @param contentType Content type to check
     */
    @Step
    public void responseContentTypeShouldBe(String contentType) {
        Response response = Serenity.sessionVariableCalled(Configuration.SESSION_RESPONSE);
        Assert.assertTrue("Response Content Type not is " + contentType, response.contentType().contains(contentType));
    }

    /**
     * Test the given json path in the response body match the given value
     *
     * @param key   Name of field
     * @param value Value from field
     */
    @Step
    public void responseJsonPathElementShouldBe(String key, String value) {
        try {
            Response response = Serenity.sessionVariableCalled(Configuration.SESSION_RESPONSE);
            //Get the JsonPath object instance from the Response interface
            JsonPath jsonPathEvaluator = response.jsonPath();
            String check = jsonPathEvaluator.get(key);

            Assert.assertEquals("Response was " + jsonPathEvaluator.prettyPrint(), value, check);
        } catch (Exception e) {
            Assert.fail("Field " + key + " not found " + e.toString());
        }

    }

    /**
     * Test the given json path in the response body is a positive number
     *
     * @param key Name of field
     */
    @Step
    public void responseJsonPathElementShouldBePositive(String key) {
        try {
            Response response = Serenity.sessionVariableCalled(Configuration.SESSION_RESPONSE);
            //Get the JsonPath object instance from the Response interface
            JsonPath jsonPathEvaluator = response.jsonPath();
            int check = jsonPathEvaluator.get(key);

            Assert.assertTrue("Response was " + jsonPathEvaluator.prettyPrint(), check > 0);
        } catch (Exception e) {
            Assert.fail("Field " + key + " not found " + e.toString());
        }

    }

    /**
     * Check response content status
     *
     * @param operationCompare Type of comparation
     * @param codeStatus       Code Status to check
     */
    @Step
    public void responseContentStatusIs(String operationCompare, int codeStatus) {
        Response response = Serenity.sessionVariableCalled(Configuration.SESSION_RESPONSE);
        switch (operationCompare.toLowerCase()) {
            case "equal":
                Assert.assertEquals(response.getStatusCode(), codeStatus);
                break;
            case "greater":
                if (response.getStatusCode() < codeStatus) {
                    Assert.fail("Response content status " + response.getStatusCode() + " is less than " + codeStatus);
                }
                break;
            case "less":
                if (response.getStatusCode() > codeStatus) {
                    Assert.fail("Response content status " + response.getStatusCode() + " is greater than " + codeStatus);
                }
                break;
            default:
                break;
        }
    }

    /**
     * Check if response body is equal to JSON file
     *
     * @param pathFile File path to check
     * @throws Exception
     */
    @Step
    public void responseShouldBeJsonInFile(String pathFile) throws Exception {
        Response response = Serenity.sessionVariableCalled(Configuration.SESSION_RESPONSE);
        InputStream out = this.getClass().getResourceAsStream(pathFile);
        JSONObject jsonObjectOut = servicesSupport.jsonInputStreamToJsonObject(out);
        JSONObject jsonObjectResponse = servicesSupport.jsonInputStreamToJsonObject(response.body().asInputStream());
        Assert.assertEquals(jsonObjectOut.toString(), jsonObjectResponse.toString());
    }

    /**
     * Check if response body is empty
     */
    @Step
    public void responseShouldBeEmpty() {
        Response response = Serenity.sessionVariableCalled(Configuration.SESSION_RESPONSE);
        Assert.assertTrue("Response should be empty", response.asString().equals("{}"));
    }

    /**
     * Test response body
     *
     * @param jsonResponseString JSON response in String format to check
     */
    @Step
    public void responseCollectionShouldBeJson(String jsonResponseString) {
        String stringResponseCollection = Serenity.sessionVariableCalled("responseCollection");
        Assert.assertEquals(jsonResponseString.replaceAll("\\s", ""), stringResponseCollection.replaceAll("\\s", ""));
    }

    /**
     * Method to clear request Header
     */
    @Step
    public void clearRequestHeader() {
        this.requestSpecification.header(Configuration.EMPTY, Configuration.EMPTY);
    }

    /**
     * Method to clear request Body
     */
    @Step
    public void clearRequestBody() {
        this.requestSpecification.body(Configuration.EMPTY);
    }


    /**
     * Method to save a body value in session
     *
     * @param bodyJsonPath element to save in session
     * @param sessionKey session key name
     */
    @Step
    public void saveBodyValueInSession(String bodyJsonPath, String sessionKey) {
        Response response = Serenity.sessionVariableCalled(Configuration.SESSION_RESPONSE);
        String bodyJsonValue = response.getBody().jsonPath().getString(bodyJsonPath);

        Serenity.getCurrentSession().put(sessionKey, bodyJsonValue);
        Serenity.setSessionVariable(sessionKey).to(bodyJsonValue);

        Assert.assertNotNull(bodyJsonValue);
        Assert.assertFalse(bodyJsonValue.isEmpty());
    }

    /**
     * Method to set the value of a pathParameter
     *
     * @param pathParameter key of the pathParameter
     */
    @Step
    public void setPathParameterWithSessionValue(String pathParameter) {
        requestSpecification.pathParam(pathParameter, Serenity.sessionVariableCalled("petId"));
    }

    /**
     * Method to check if a value is present in session
     *
     * @param elementKey element to validate against session
     * @param sessionKey session element to look for
     */
    @Step
    public void checkElementPresentInSession(String elementKey, String sessionKey) {
        Response response = Serenity.sessionVariableCalled(Configuration.SESSION_RESPONSE);
        String bodyValue = response.getBody().jsonPath().getString(elementKey);
        String sessionValue = Serenity.sessionVariableCalled(sessionKey).toString();
        Assert.assertEquals("\nResponse body: \n " + response.prettyPrint(), sessionValue, bodyValue);
    }
}

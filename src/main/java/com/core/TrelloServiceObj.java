package com.core;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

import static com.constants.ParamsName.KEY;
import static com.constants.ParamsName.TOKEN;
import static com.core.TrelloServiceObj.ApiRequestBuilder.requestSpecification;
import static com.epam.jdi.light.settings.JDISettings.COMMON;
import static com.jdiai.tools.PropertyReader.getProperties;
import static org.hamcrest.Matchers.lessThan;

public class TrelloServiceObj {

    private static String domain = getProperties(COMMON.testPropertiesPath).getProperty("domain");
    private static String apiKey = getProperties(COMMON.testPropertiesPath).getProperty("apiKey");
    private static String token = getProperties(COMMON.testPropertiesPath).getProperty("token");
    private Method requestMethod;

    private Map<String, String> params;

    private TrelloServiceObj(Map<String, String> params, Method method) {
        this.params = params;
        this.requestMethod = method;
    }

    public static ApiRequestBuilder requestBuilder() {
        return new ApiRequestBuilder();
    }

    public static class ApiRequestBuilder {
        private Map<String, String> params = new HashMap<>();
        private Method requestMethod = Method.GET;

        public ApiRequestBuilder setMethod(Method method) {
            requestMethod = method;
            return this;
        }

        public ApiRequestBuilder setParams(Map<String, String> params) {
            this.params.putAll(params);
            return this;
        }

        public TrelloServiceObj buildRequest() {
            params.put(KEY, apiKey);
            params.put(TOKEN, token);
            return new TrelloServiceObj(params, requestMethod);
        }


        public static RequestSpecification requestSpecification() {
            return new RequestSpecBuilder()
                    .setAccept(ContentType.JSON)
                    .setContentType(ContentType.JSON)
                    .build();
        }

        public static ResponseSpecification goodResponseSpecification() {
            return new ResponseSpecBuilder()
                    .expectContentType(ContentType.JSON)
                    .expectResponseTime(lessThan(10000L))
                    .expectStatusCode(HttpStatus.SC_OK)
                    .build();
        }

    }

    public Response sendRequest(String url) {
        return RestAssured
                .given(requestSpecification().log().all())
                .queryParams(params)
                .request(requestMethod, String.format("%s%s", domain, url))
                .prettyPeek();
    }

}

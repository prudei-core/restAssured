package com.api;

import com.dto.responses.TrelloCreateListOnDeskResponse.TrelloCreateListOnDeskResponse;
import com.dto.responses.TrelloUpdateAListResponse.TrelloUpdateAListResponse;
import io.restassured.http.Method;

import java.util.Map;

import static com.core.TrelloServiceObj.requestBuilder;
import static com.enums.URL.CREATE_LIST;
import static com.enums.URL.LISTS;
import static java.lang.String.format;

public class TrelloListApi {

    public static TrelloCreateListOnDeskResponse createList(Map<String, String> params) {
        return requestBuilder()
                .setParams(params)
                .setMethod(Method.POST)
                .buildRequest()
                .sendRequest(CREATE_LIST.getUrl())
                .as(TrelloCreateListOnDeskResponse.class);
    }

    public static TrelloUpdateAListResponse updateList(Map<String, String> params, String idOfList) {
        return requestBuilder()
                .setParams(params)
                .setMethod(Method.PUT)
                .buildRequest()
                .sendRequest(format("%s/%s", LISTS.getUrl(), idOfList))
                .as(TrelloUpdateAListResponse.class);
    }
}

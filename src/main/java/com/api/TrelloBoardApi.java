package com.api;

import com.dto.responses.TrelloCreateABoardResponse.TrelloCreateABoardResponse;
import com.dto.responses.TrelloDeleteABoard.TrelloDeleteABoard;
import com.dto.responses.TrelloUpdateABoardResponse.TrelloUpdateABoardResponse;
import io.restassured.http.Method;

import java.util.Map;

import static com.core.TrelloServiceObj.ApiRequestBuilder.goodResponseSpecification;
import static com.core.TrelloServiceObj.requestBuilder;
import static com.enums.URL.BOARDS;
import static java.lang.String.format;

public class TrelloBoardApi {
    public static TrelloCreateABoardResponse createBoard(Map<String, String> params) {
        return requestBuilder()
                .setParams(params)
                .setMethod(Method.POST)
                .buildRequest()
                .sendRequest(BOARDS.getUrl())
                .then().assertThat()
                .spec(goodResponseSpecification())
                .extract().as(TrelloCreateABoardResponse.class);
    }
    public static TrelloDeleteABoard deleteBoard(String idOfBoard) {
        return requestBuilder()
                .setMethod(Method.DELETE)
                .buildRequest()
                .sendRequest(format("%s/%s", BOARDS.getUrl(), idOfBoard))
                .then().assertThat()
                .spec(goodResponseSpecification())
                .extract().as(TrelloDeleteABoard.class);
    }

    public static TrelloUpdateABoardResponse updateBoard(Map<String, String> params, String idOfBoard) {
        return requestBuilder()
                .setParams(params)
                .setMethod(Method.PUT)
                .buildRequest()
                .sendRequest(String.format("%s/%s", BOARDS.getUrl(), idOfBoard))
                .then().assertThat()
                .spec(goodResponseSpecification())
                .extract().as(TrelloUpdateABoardResponse.class);
    }

}

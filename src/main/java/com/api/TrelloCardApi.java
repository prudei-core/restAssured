package com.api;

import com.dto.responses.TrelloCreateALabelOnACard.TrelloCreateALabelOnACard;
import com.dto.responses.TrelloCreateANewCardResponse.TrelloCreateANewCardResponse;
import com.dto.responses.TrelloCreateCheckLlistOnACardResponse.TrelloCreateCheckLlistOnACardResponse;
import com.dto.responses.TrelloDeleteCardResponse.TrelloDeleteCardResponse;
import com.dto.responses.TrelloUpdateACardResponse.TrelloUpdateACardResponse;
import com.dto.responses.TrelloUpdateFieldOnAChecklistResponse.TrelloUpdateFieldOnAChecklistResponse;
import io.restassured.http.Method;

import java.util.Map;

import static com.core.TrelloServiceObj.ApiRequestBuilder.goodResponseSpecification;
import static com.core.TrelloServiceObj.requestBuilder;
import static com.enums.URL.*;
import static com.enums.URL.CARDS;
import static java.lang.String.format;

public class TrelloCardApi {
    public static TrelloCreateANewCardResponse createCard(Map<String, String> params) {
        return requestBuilder()
                .setParams(params)
                .setMethod(Method.POST)
                .buildRequest()
                .sendRequest(CARDS.getUrl())
                .as(TrelloCreateANewCardResponse.class);
    }



    public static TrelloUpdateACardResponse updateCard(Map<String, String> params, String idOfCard) {
        return requestBuilder()
                .setParams(params)
                .setMethod(Method.PUT)
                .buildRequest()
                .sendRequest(format("%s/%s", CARDS.getUrl(), idOfCard))
                .as(TrelloUpdateACardResponse.class);
    }

    public static TrelloCreateALabelOnACard createLabelForCard(Map<String, String> params, String idOfCard) {
        return requestBuilder()
                .setParams(params)
                .setMethod(Method.POST)
                .buildRequest()
                .sendRequest(format("%s/%s/%s", CARDS.getUrl(), idOfCard, LABELS.getUrl()))
                .as(TrelloCreateALabelOnACard.class);
    }

    public static TrelloCreateCheckLlistOnACardResponse createCheckList(Map<String, String> params, String idOfCard) {
        return requestBuilder()
                .setParams(params)
                .setMethod(Method.POST)
                .buildRequest()
                .sendRequest(format("%s/%s/%s", CARDS.getUrl(), idOfCard, CHECKLISTS.getUrl()))
                .then().assertThat()
                .spec(goodResponseSpecification())
                .extract().as(TrelloCreateCheckLlistOnACardResponse.class);
    }

    public static TrelloUpdateFieldOnAChecklistResponse updateFieldOnCheckList(Map<String, String> params, String idOfCheckList) {
        return requestBuilder()
                .setParams(params)
                .setMethod(Method.POST)
                .buildRequest()
                .sendRequest(format("%s/%s/%s", CHECKLISTS.getUrl(), idOfCheckList, CHECKITEMS.getUrl()))
                .then().assertThat()
                .spec(goodResponseSpecification())
                .extract().as(TrelloUpdateFieldOnAChecklistResponse.class);
    }

    public static TrelloDeleteCardResponse deleteCard(String idOfCard) {
        return requestBuilder()
                .setMethod(Method.DELETE)
                .buildRequest()
                .sendRequest(format("%s/%s", CARDS.getUrl(), idOfCard))
                .then().assertThat()
                .spec(goodResponseSpecification())
                .extract().as(TrelloDeleteCardResponse.class);
    }
}

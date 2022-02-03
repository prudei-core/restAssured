import com.dto.responses.TrelloCreateALabelOnACard.TrelloCreateALabelOnACard;
import com.dto.responses.TrelloCreateCheckLlistOnACardResponse.TrelloCreateCheckLlistOnACardResponse;
import com.dto.responses.TrelloDeleteCardResponse.TrelloDeleteCardResponse;
import com.dto.responses.TrelloUpdateACardResponse.TrelloUpdateACardResponse;
import com.dto.responses.TrelloUpdateFieldOnAChecklistResponse.TrelloUpdateFieldOnAChecklistResponse;
import io.qameta.allure.Description;
import io.restassured.http.Method;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static com.core.TrelloServiceObj.ApiRequestBuilder.goodResponseSpecification;
import static com.core.TrelloServiceObj.requestBuilder;
import static java.lang.String.format;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CardTests implements CardTestInit {

    public static String idOfCard;
    public static String idOfCheckList;

    @Test
    @Description("Create a new card")
    public void createACard() {
    }

    @Test
    @Description("Rename the card")
    public void updateTitleOfCard() {
        String name = random.name().name();
        Map<String, String> params = new HashMap<>();
        params.put("name", name);
        TrelloUpdateACardResponse response = requestBuilder()
                .setParams(params)
                .setMethod(Method.PUT)
                .buildRequest()
                .sendRequest(format("%s/%s", ListTestsInit.CARDS, idOfCard))
                .as(TrelloUpdateACardResponse.class);
        assertThat(response.name, equalTo(name));
    }

    @Test
    @Description("Add description for the card")
    public void addDescriptionForTheCard() {
        String desc = random.lorem().paragraph();
        Map<String, String> params = new HashMap<>();
        params.put("desc", desc);
        TrelloUpdateACardResponse response = requestBuilder()
                .setParams(params)
                .setMethod(Method.PUT)
                .buildRequest()
                .sendRequest(format("%s/%s", ListTestsInit.CARDS, idOfCard))
                .as(TrelloUpdateACardResponse.class);
        assertThat(response.desc, equalTo(desc));
    }

    @Test
    @Description("Create a new label on a card")
    public void addLabeltoCard() {
        String color = "red";
        Map<String, String> params = new HashMap<>();
        params.put("color", color);
        TrelloCreateALabelOnACard response = requestBuilder()
                .setParams(params)
                .setMethod(Method.POST)
                .buildRequest()
                .sendRequest(format("%s/%s/%s", ListTests.CARDS, idOfCard, ListTests.LABELS))
                .as(TrelloCreateALabelOnACard.class);
        assertThat(response.color, equalTo(color));
    }

    @Test
    @Description("Create checklist on a card")
    public void createAChecklist() {
        String name = random.name().name();
        Map<String, String> params = new HashMap<>();
        params.put("name", name);

        TrelloCreateCheckLlistOnACardResponse response = requestBuilder()
                .setParams(params)
                .setMethod(Method.POST)
                .buildRequest()
                .sendRequest(format("%s/%s/%s", ListTestsInit.CARDS, idOfCard, ListTestsInit.CHECKLISTS))
                .then().assertThat()
                .spec(goodResponseSpecification())
                .extract().as(TrelloCreateCheckLlistOnACardResponse.class);

        idOfCheckList = response.id;
        assertThat(response.name, equalTo(name));
    }

    @Test
    @Description("Add new item to checklist")
    public void updateChecklist() {
        String title = random.address().country();
        Map<String, String> params = new HashMap<>();
        params.put("name", title);

        createAChecklist();
        TrelloUpdateFieldOnAChecklistResponse response = requestBuilder()
                .setParams(params)
                .setMethod(Method.POST)
                .buildRequest()
                .sendRequest(format("%s/%s/%s", ListTestsInit.CHECKLISTS, idOfCheckList, ListTestsInit.CHECKITEMS))
                .then().assertThat()
                .spec(goodResponseSpecification())
                .extract().as(TrelloUpdateFieldOnAChecklistResponse.class);

        assertThat(response.state, equalTo("incomplete"));
        assertThat(response.name, equalTo(title));
    }

    @Test
    @Description("Delete a card")
    public void deleteACard() {
        TrelloDeleteCardResponse response = requestBuilder()
                .setMethod(Method.DELETE)
                .buildRequest()
                .sendRequest(format("%s/%s", ListTestsInit.CARDS, idOfCard))
                .then().assertThat()
                .spec(goodResponseSpecification())
                .extract().as(TrelloDeleteCardResponse.class);
    }

}
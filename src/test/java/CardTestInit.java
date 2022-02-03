import com.dto.responses.TrelloCreateANewCardResponse.TrelloCreateANewCardResponse;
import com.dto.responses.TrelloCreateListOnDeskResponse.TrelloCreateListOnDeskResponse;
import com.dto.responses.TrelloUpdateAListResponse.TrelloUpdateAListResponse;
import com.github.javafaker.Faker;
import io.restassured.http.Method;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.util.HashMap;
import java.util.Map;

import static com.core.TrelloServiceObj.requestBuilder;
import static java.lang.String.format;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public interface CardTestInit {
    Faker random = new Faker();

    @BeforeMethod(alwaysRun = true)
    default void beforeMethodTestInit() {
        String listName = random.name().name();
        String cardName = random.name().name();

        Map<String, String> listParams = new HashMap<>();
        Map<String, String> cardParams = new HashMap<>();
        listParams.put("name", listName);
        cardParams.put("name", cardName);

        TrelloCreateListOnDeskResponse response = requestBuilder()
                .setParams(listParams)
                .setMethod(Method.POST)
                .buildRequest()
                .sendRequest(ListTestsInit.CREATE_LIST)
                .as(TrelloCreateListOnDeskResponse.class);
        assertThat(response.name, equalTo(listName));
        ListTests.idOfList = response.id;

        cardParams.put("idList", response.id);
        TrelloCreateANewCardResponse response2 = requestBuilder()
                .setParams(cardParams)
                .setMethod(Method.POST)
                .buildRequest()
                .sendRequest(ListTestsInit.CARDS)
                .as(TrelloCreateANewCardResponse.class);
        assertThat(response2.name, equalTo(cardName));
        CardTests.idOfCard = response2.id;
    }

    @AfterMethod(alwaysRun = true)
    default void afterMethodTestsInit() {
        Map<String, String> params = new HashMap<>();
        params.put("value", "true");
        TrelloUpdateAListResponse response = requestBuilder()
                .setParams(params)
                .setMethod(Method.PUT)
                .buildRequest()
                .sendRequest(format("%s/%s/%s", ListTestsInit.LISTS, ListTests.idOfList, ListTestsInit.CLOSED))
                .as(TrelloUpdateAListResponse.class);
    }

}
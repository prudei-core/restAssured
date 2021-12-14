import com.dto.responses.TrelloCreateABoardResponse.TrelloCreateABoardResponse;
import com.dto.responses.TrelloCreateANewCardResponse.TrelloCreateANewCardResponse;
import com.dto.responses.TrelloCreateAnAttachmentResponse.TrelloCreateAnAttachmentResponse;
import com.dto.responses.TrelloCreateCheckLlistOnACardResponse.TrelloCreateCheckLlistOnACardResponse;
import com.dto.responses.TrelloCreateListOnDeskResponse.TrelloCreateListOnDeskResponse;
import com.dto.responses.TrelloGetABoardResponse.TrelloGetABoardResponse;
import com.dto.responses.TrelloGetACardResponse.TrelloGetACardResponse;
import com.dto.responses.TrelloUpdateABoardResponse.TrelloUpdateABoardResponse;
import com.dto.responses.TrelloUpdateChecklistResponse.TrelloUpdateChecklistResponse;
import com.dto.responses.TrelloUpdateFieldOnAChecklistResponse.TrelloUpdateFieldOnAChecklistResponse;
import com.github.javafaker.Faker;
import io.restassured.http.Method;
import org.testng.annotations.Test;
import testdata.BoardFactory;

import java.util.HashMap;
import java.util.Map;

import static com.core.TrelloServiceObj.ApiRequestBuilder.goodResponseSpecification;
import static com.core.TrelloServiceObj.requestBuilder;
import static java.lang.String.format;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


public class TrelloApiTests {

    private final String CREATE_LIST = "boards/4filFDhN/lists";
    private final String GET_BOARD_BY_ID = "boards/";
    private final String CARDS = "cards";
    private final String CHECKLISTS = "checklists";
    private final String CHECKLITEMS = "checkitems";
    private final String ATTACHMENTS = "attachments";
    private final String BOARDS = "boards";

    private final String ID_OF_TEST_LIST = "61b3874f48ba9d7c98a689e3";
    private final String ID_OF_CARD = "61b764ab2302230d2b2986af";
    private final String ID_OF_CHECKLIST = "61b850f715a750141bc12744";

    Faker random = new Faker();

    @Test
    public void checkThatListIsCreated() {
        String name = random.name().name();
        Map<String, String> params = new HashMap<>();
        params.put("name", name);
        TrelloCreateListOnDeskResponse response = requestBuilder()
                .setParams(params)
                .setMethod(Method.POST)
                .buildRequest()
                .sendRequest(CREATE_LIST)
                .as(TrelloCreateListOnDeskResponse.class);
        assertThat(response.name, equalTo(name));
    }

    @Test(dataProviderClass = BoardFactory.class, dataProvider = "boardsData")
    public void checkTitlesOfBoards(String titleOfBoard, String idOfBoard) {
        TrelloGetABoardResponse response = requestBuilder()
                .setMethod(Method.GET)
                .buildRequest()
                .sendRequest(format("%s%s", GET_BOARD_BY_ID, idOfBoard))
                .as(TrelloGetABoardResponse.class);
        assertThat(response.name, equalTo(titleOfBoard));
    }

    @Test
    public void checkThatNewCardIdCreated() {
        String name = random.name().name();
        Map<String, String> params = new HashMap<>();
        params.put("name", name);
        params.put("idList", ID_OF_TEST_LIST);
        TrelloCreateANewCardResponse response = requestBuilder()
                .setParams(params)
                .setMethod(Method.POST)
                .buildRequest()
                .sendRequest(CARDS)
                .as(TrelloCreateANewCardResponse.class);
        assertThat(response.name, equalTo(name));
    }

    @Test
    public void checkThatLabelIsCreated() {
        TrelloGetACardResponse response = requestBuilder()
                .buildRequest()
                .sendRequest(format("%s/%s", CARDS, ID_OF_CARD))
                .as(TrelloGetACardResponse.class);
        assertThat(response.labels.get(0).color, equalTo("red"));
    }

    @Test
    public void checkThatAttachmentIsCreated() {
        String url = "https://8.allegroimg.com/s256/035f14/53b09a0f4f77901aee88e0e4e438/TDA3771-Philips";

        String name = random.name().name();
        Map<String, String> params = new HashMap<>();
        params.put("name", name);
        params.put("url", url);

        TrelloCreateAnAttachmentResponse response = requestBuilder()
                .setMethod(Method.POST)
                .setParams(params)
                .buildRequest()
                .sendRequest(format("%s/%s/%s", CARDS, ID_OF_CARD, ATTACHMENTS))
                .then().assertThat()
                .spec(goodResponseSpecification())
                .extract().as(TrelloCreateAnAttachmentResponse.class);

        assertThat(response.name, equalTo(name));
    }

    @Test
    public void checkThatChecklistIsUpdated() {
        String name = random.name().lastName();
        Map<String, String> params = new HashMap<>();
        params.put("name", name);

        TrelloUpdateChecklistResponse response = requestBuilder()
                .setMethod(Method.PUT)
                .setParams(params)
                .buildRequest()
                .sendRequest(format("%s/%s", CHECKLISTS, ID_OF_CHECKLIST))
                .then().assertThat()
                .spec(goodResponseSpecification())
                .extract().as(TrelloUpdateChecklistResponse.class);

        assertThat(response.id, equalTo(ID_OF_CHECKLIST));
    }

    @Test
    public void checkThatChecklistIsCreated() {
        String name = random.name().name();
        Map<String, String> params = new HashMap<>();
        params.put("name", name);

        TrelloCreateCheckLlistOnACardResponse response = requestBuilder()
                .setParams(params)
                .setMethod(Method.POST)
                .buildRequest()
                .sendRequest(format("%s/%s/%s", CARDS, ID_OF_CARD, CHECKLISTS))
                .then().assertThat()
                .spec(goodResponseSpecification())
                .extract().as(TrelloCreateCheckLlistOnACardResponse.class);

        assertThat(response.name, equalTo(name));
    }

    @Test
    public void checkThatCheckItemIsAdded() {
        String title = random.address().country();
        Map<String, String> params = new HashMap<>();
        params.put("name", title);

        TrelloUpdateFieldOnAChecklistResponse response = requestBuilder()
                .setParams(params)
                .setMethod(Method.POST)
                .buildRequest()
                .sendRequest(format("%s/%s/%s", CHECKLISTS, ID_OF_CHECKLIST, CHECKLITEMS))
                .then().assertThat()
                .spec(goodResponseSpecification())
                .extract().as(TrelloUpdateFieldOnAChecklistResponse.class);

        assertThat(response.state, equalTo("incomplete"));
        assertThat(response.name, equalTo(title));
    }

    @Test
    public void checkThatBoardIsCreated() {
        String name = random.name().name();
        Map<String, String> params = new HashMap<>();
        params.put("name", name);

        TrelloCreateABoardResponse response = requestBuilder()
                .setParams(params)
                .setMethod(Method.POST)
                .buildRequest()
                .sendRequest(BOARDS)
                .then().assertThat()
                .spec(goodResponseSpecification())
                .extract().as(TrelloCreateABoardResponse.class);

        assertThat(response.name, equalTo(name));
    }

    @Test
    public void checkThatBoardIsUpdated() {
        String idOfBoard = "k8NzfbCm";
        String name = random.name().name();
        String lorem = random.lorem().paragraph();
        Map<String, String> params = new HashMap<>();
        params.put("name", name);
        params.put("desc", lorem);

        TrelloUpdateABoardResponse response = requestBuilder()
                .setParams(params)
                .setMethod(Method.PUT)
                .buildRequest()
                .sendRequest(String.format("%s/%s", BOARDS, idOfBoard))
                .then().assertThat()
                .spec(goodResponseSpecification())
                .extract().as(TrelloUpdateABoardResponse.class);

        assertThat(response.desc, equalTo(lorem));
    }
}

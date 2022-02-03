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

public interface ListTestsInit {

    String CREATE_LIST = "boards/4filFDhN/lists";
    String CARDS = "cards";
    String LABELS = "labels";
    String LISTS = "lists";
    String CLOSED = "closed";
    String CHECKLISTS = "checklists";
    String CHECKITEMS = "checkitems";

    Faker random = new Faker();

    @BeforeMethod(alwaysRun = true)
    default void beforeMethodTestInit() {
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
        ListTests.idOfList = response.id;
    }

    @AfterMethod(alwaysRun = true)
    default void afterMethodTestsInit() {
        Map<String, String> params = new HashMap<>();
        params.put("value", "true");
        TrelloUpdateAListResponse response = requestBuilder()
                .setParams(params)
                .setMethod(Method.PUT)
                .buildRequest()
                .sendRequest(format("%s/%s/%s", LISTS, ListTests.idOfList, CLOSED))
                .as(TrelloUpdateAListResponse.class);
    }

}
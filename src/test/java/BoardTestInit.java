import com.dto.responses.TrelloCreateABoardResponse.TrelloCreateABoardResponse;
import com.dto.responses.TrelloDeleteABoard.TrelloDeleteABoard;
import com.github.javafaker.Faker;
import io.restassured.http.Method;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.util.HashMap;
import java.util.Map;

import static com.core.TrelloServiceObj.ApiRequestBuilder.goodResponseSpecification;
import static com.core.TrelloServiceObj.requestBuilder;
import static java.lang.String.format;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public interface BoardTestInit {

    String BOARDS = "boards";

    Faker random = new Faker();

    @BeforeMethod(alwaysRun = true)
    default void beforeMethodTestInit() {
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

        BoardTests.idOfBoard = response.id;
        assertThat(response.name, equalTo(name));
    }

    @AfterMethod(alwaysRun = true)
    default void afterMethodTestsInit() {
        TrelloDeleteABoard response = requestBuilder()
                .setMethod(Method.DELETE)
                .buildRequest()
                .sendRequest(format("%s/%s", BOARDS, BoardTests.idOfBoard))
                .then().assertThat()
                .spec(goodResponseSpecification())
                .extract().as(TrelloDeleteABoard.class);
    }

}
import com.dto.responses.TrelloUpdateABoardResponse.TrelloUpdateABoardResponse;
import io.qameta.allure.Description;
import io.restassured.http.Method;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static com.core.TrelloServiceObj.ApiRequestBuilder.goodResponseSpecification;
import static com.core.TrelloServiceObj.requestBuilder;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class BoardTests implements BoardTestInit {

    public static String idOfBoard;

    @Test
    @Description("Crate a new board")
    public void createANewBoard() {
    }

    @Test
    @Description("Update the title of the board. Add the description")
    public void updateTheBoard() {
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
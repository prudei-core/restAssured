import com.dto.responses.TrelloUpdateAListResponse.TrelloUpdateAListResponse;
import io.qameta.allure.Description;
import io.restassured.http.Method;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static com.core.TrelloServiceObj.requestBuilder;
import static java.lang.String.format;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ListTests implements ListTestsInit {
    public static String idOfList;

    @Test
    @Description("Create a new list")
    public void createNewList() {
    }

    @Test
    @Description("Rename the list")
    public void updateAList() {
        String name = random.name().name();
        Map<String, String> params = new HashMap<>();
        params.put("name", name);
        TrelloUpdateAListResponse response = requestBuilder()
                .setParams(params)
                .setMethod(Method.PUT)
                .buildRequest()
                .sendRequest(format("%s/%s", LISTS, idOfList))
                .as(TrelloUpdateAListResponse.class);
        assertThat(response.name, equalTo(name));
    }

}
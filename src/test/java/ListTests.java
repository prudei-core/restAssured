import com.dto.responses.TrelloCreateListOnDeskResponse.TrelloCreateListOnDeskResponse;
import com.dto.responses.TrelloUpdateAListResponse.TrelloUpdateAListResponse;
import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static com.api.TrelloListApi.createList;
import static com.api.TrelloListApi.updateList;

public class ListTests {
    public static String idOfList;
    Faker random = new Faker();
    TrelloCreateListOnDeskResponse response;
    String name;

    @BeforeMethod(alwaysRun = true)
    void beforeMethodTestInit() {
        name = random.name().name();
        Map<String, String> params = new HashMap<>();
        params.put("name", name);

        response = createList(params);
        ListTests.idOfList = response.id;
    }

    @Test
    @Description("Create a new list")
    public void createNewList() {
        Assert.assertEquals(response.name, name);
    }

    @Test
    @Description("Rename the list")
    public void updateAList() {
        String name = random.name().name();
        Map<String, String> params = new HashMap<>();
        params.put("name", name);

        TrelloUpdateAListResponse response = updateList(params, idOfList);
        Assert.assertEquals(response.name, name);
    }

    @AfterMethod(alwaysRun = true)
    void afterMethodTestsInit() {
        Map<String, String> params = new HashMap<>();
        params.put("value", "true");
        updateList(params, ListTests.idOfList);
    }

}
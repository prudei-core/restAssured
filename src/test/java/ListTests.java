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

import static com.api.TrelloApi.createList;
import static com.api.TrelloApi.updateList;

public class ListTests {
    public static String idOfList;
    Faker random = new Faker();

    @BeforeMethod(alwaysRun = true)
    void beforeMethodTestInit() {
        String name = random.name().name();
        Map<String, String> params = new HashMap<>();
        params.put("name", name);
        TrelloCreateListOnDeskResponse response = createList(params);
        Assert.assertEquals(response.name, name);
        ListTests.idOfList = response.id;
    }

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
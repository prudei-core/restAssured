import com.api.TrelloApi;
import com.dto.responses.TrelloCreateABoardResponse.TrelloCreateABoardResponse;
import com.dto.responses.TrelloUpdateABoardResponse.TrelloUpdateABoardResponse;
import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static com.api.TrelloApi.createBoard;
import static com.api.TrelloApi.deleteBoard;

public class BoardTests {
    public static String idOfBoard;
    public Faker random = new Faker();

    @BeforeMethod(alwaysRun = true)
    void beforeMethodTestInit() {
        String name = random.name().name();
        Map<String, String> params = new HashMap<>();
        params.put("name", name);

        TrelloCreateABoardResponse response = createBoard(params);

        idOfBoard = response.id;
        Assert.assertEquals(response.name, name);
    }

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

        TrelloUpdateABoardResponse response = TrelloApi.updateBoard(params, idOfBoard);

        Assert.assertEquals(response.desc, lorem);
    }

    @AfterMethod(alwaysRun = true)
    void afterMethodTestsInit() {
        deleteBoard(idOfBoard);
    }

}
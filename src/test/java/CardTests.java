import com.dto.responses.TrelloCreateALabelOnACard.TrelloCreateALabelOnACard;
import com.dto.responses.TrelloCreateANewCardResponse.TrelloCreateANewCardResponse;
import com.dto.responses.TrelloCreateCheckLlistOnACardResponse.TrelloCreateCheckLlistOnACardResponse;
import com.dto.responses.TrelloCreateListOnDeskResponse.TrelloCreateListOnDeskResponse;
import com.dto.responses.TrelloUpdateACardResponse.TrelloUpdateACardResponse;
import com.dto.responses.TrelloUpdateFieldOnAChecklistResponse.TrelloUpdateFieldOnAChecklistResponse;
import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static com.api.TrelloApi.*;

public class CardTests {
    Faker random = new Faker();

    public static String idOfCard;
    public static String idOfCheckList;

    @BeforeMethod(alwaysRun = true)
    void beforeMethodTestInit() {
        String listName = random.name().name();
        String cardName = random.name().name();

        Map<String, String> listParams = new HashMap<>();
        Map<String, String> cardParams = new HashMap<>();
        listParams.put("name", listName);
        cardParams.put("name", cardName);

        TrelloCreateListOnDeskResponse response = createList(listParams);
        Assert.assertEquals(response.name, listName);
        ListTests.idOfList = response.id;

        cardParams.put("idList", response.id);
        TrelloCreateANewCardResponse response2 = createCard(cardParams);
        Assert.assertEquals(response2.name, cardName);
        CardTests.idOfCard = response2.id;
    }

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
        TrelloUpdateACardResponse response = updateCard(params, idOfCard);
        Assert.assertEquals(response.name, name);
    }

    @Test
    @Description("Add description for the card")
    public void addDescriptionForTheCard() {
        String desc = random.lorem().paragraph();
        Map<String, String> params = new HashMap<>();
        params.put("desc", desc);
        TrelloUpdateACardResponse response = updateCard(params, idOfCard);
        Assert.assertEquals(response.desc, desc);
    }

    @Test
    @Description("Create a new label on a card")
    public void addLabeltoCard() {
        String color = "red";
        Map<String, String> params = new HashMap<>();
        params.put("color", color);
        TrelloCreateALabelOnACard response = createLabelForCard(params, idOfCard);
        Assert.assertEquals(response.color, color);
    }

    @Test
    @Description("Create checklist on a card")
    public void createAChecklist() {
        String name = random.name().name();
        Map<String, String> params = new HashMap<>();
        params.put("name", name);

        TrelloCreateCheckLlistOnACardResponse response = createCheckList(params, idOfCard);

        idOfCheckList = response.id;
        Assert.assertEquals(response.name, name);
    }

    @Test
    @Description("Add new item to checklist")
    public void updateChecklist() {
        String title = random.address().country();
        Map<String, String> params = new HashMap<>();
        params.put("name", title);

        createAChecklist();
        TrelloUpdateFieldOnAChecklistResponse response = updateFieldOnCheckList(params, idOfCheckList);

        Assert.assertEquals(response.state, "incomplete");
        Assert.assertEquals(response.name, title);
    }

    @Test
    @Description("Delete a card")
    public void deleteACard() {
        deleteCard(idOfCard);
    }

    @AfterMethod(alwaysRun = true)
    void afterMethodTestsInit() {
        Map<String, String> params = new HashMap<>();
        params.put("value", "true");
        updateList(params, ListTests.idOfList);
    }

}
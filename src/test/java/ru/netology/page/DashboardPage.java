package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;
import ru.netology.data.DataHelper;


import static com.codeborne.selenide.Selenide.*;

public class DashboardPage {
    private ElementsCollection cards = $$(".list__item div");
    private final SelenideElement heading = $x("//*[@data-test-id='dashboard']");
    private final String button = "[data-test-id='action-deposit']";
    private final String dataTestId = "data-test-id";
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";

    public DashboardPage() {
        heading.shouldBe(Condition.visible);
    }


    public int getCardBalance(DataHelper.Card selectCard) {
        String text = cards.find(com.codeborne.selenide.Condition.attribute(dataTestId, selectCard.getId())).getText();
        return extractBalance(text);
    }

    private int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

    public TransferPage fillCard(DataHelper.Card selectTo) {
        cards.findBy(Condition.attribute(dataTestId, selectTo.getId())).find(button).click();
        return new TransferPage();
    }


}

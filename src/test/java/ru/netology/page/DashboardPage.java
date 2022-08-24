package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.Data;
import lombok.experimental.UtilityClass;
import lombok.val;
import org.openqa.selenium.By;
import ru.netology.data.DataHelper;

import java.util.ArrayList;

import static com.codeborne.selenide.Selenide.*;

public class DashboardPage {
    // к сожалению, разработчики не дали нам удобного селектора, поэтому так
    private ElementsCollection cards = $$(".list__item div");
    private final SelenideElement heading = $x("//*[@data-test-id='dashboard']");
    private ElementsCollection buttons = $$x("//*[@data-test-id='action-deposit']");
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
        cards.findBy(Condition.attribute(dataTestId, selectTo.getId()));
        if (selectTo.equals(DataHelper.getFirstCardInfo())) {
        buttons.get(0).click();
        }
        if (selectTo.equals(DataHelper.getSecondCardInfo())) {
            buttons.get(1).click();
        }
        return new TransferPage();
    }


}

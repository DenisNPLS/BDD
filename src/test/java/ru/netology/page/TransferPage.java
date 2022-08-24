package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class TransferPage {


    private final SelenideElement heading = $x("//h1[contains(text(),'Пополнение карты')]");
    private final SelenideElement amount = $x("//input[@maxlength='14']");
    private final SelenideElement fromField = $x("//input[@type='tel']");
    private final SelenideElement button = $x("//button[@data-test-id='action-transfer']");
    private final SelenideElement notificationField = $x("//*[@data-test-id='error-notification']");

    public TransferPage() {
        heading.shouldBe(Condition.visible);
    }

    public DashboardPage transferMoney(String fromCard, int sum) {
        amount.setValue(String.valueOf(sum));
        fromField.setValue(String.valueOf(fromCard));
        button.click();
        return new DashboardPage();
    }

    public void errorNotification() {
        notificationField.shouldHave(text("Ошибка!")).shouldBe(visible);
    }

}

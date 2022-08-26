package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Selenide.$x;

public class LoginInPage {

    private SelenideElement heading = $x("//h2[contains(text(),'Интернет Банк')]");
    private SelenideElement loginField = $x("//input[@name='login']");
    private SelenideElement passwordField = $x("//input[@name='password']");
    private SelenideElement loginButton = $x("//button[@data-test-id='action-login']");

    public LoginInPage() {
        heading.shouldBe(Condition.visible);
    }

    public VerificationPage validLogin(DataHelper.AuthInfo info) {
        loginField.setValue(DataHelper.getAuthInfo().getLogin());
        passwordField.setValue(DataHelper.getAuthInfo().getPassword());
        loginButton.click();
        return new VerificationPage();
    }


}

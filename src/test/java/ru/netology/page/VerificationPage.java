package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import lombok.experimental.UtilityClass;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Selenide.$x;
import static java.awt.SystemColor.info;

public class VerificationPage {
    private SelenideElement codeField = $x("//input[@name='code']");
    private SelenideElement verifyButton = $x("//button[@data-test-id='action-verify']");

    public VerificationPage() {
        codeField.shouldBe(Condition.visible);
    }

    public DashboardPage validVerify(DataHelper.VerificationCode verificationCode) {
        codeField.setValue(verificationCode.getCode());
        verifyButton.click();
        return new DashboardPage();
    }
}

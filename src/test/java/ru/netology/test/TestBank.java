package ru.netology.test;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginInPage;

public class TestBank {

    @BeforeEach
    void setUpAll() {
        Selenide.open("http://localhost:9999");
    }

    @AfterEach
    void reset() {
        var authInfo = DataHelper.getAuthInfo();
        DashboardPage dashboardPage = new DashboardPage();
        int balanceFirstCard = dashboardPage.getCardBalance(DataHelper.getFirstCardInfo());
        int balanceSecondCard = dashboardPage.getCardBalance(DataHelper.getSecondCardInfo());
        if (balanceFirstCard > balanceSecondCard) {
            int diff = (balanceFirstCard - balanceSecondCard) / 2;
            var transferPage = dashboardPage.fillCard(DataHelper.getSecondCardInfo());
            transferPage.transferMoney(DataHelper.getFirstCardInfo().getNumberOfCard(), diff);
        } else {
            int diff = (balanceSecondCard - balanceFirstCard) / 2;
            var transferPage = dashboardPage.fillCard(DataHelper.getFirstCardInfo());
            transferPage.transferMoney(DataHelper.getSecondCardInfo().getNumberOfCard(), diff);
        }
    }

    @Test
    public void shouldTransferMoneyFromFirstCardToSecondCard() {
        var loginPage = new LoginInPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        int balanceFirstCard = dashboardPage.getCardBalance(DataHelper.getFirstCardInfo());
        int balanceSecondCard = dashboardPage.getCardBalance(DataHelper.getSecondCardInfo());
        var transferPage = dashboardPage.fillCard(DataHelper.getSecondCardInfo());
        transferPage.transferMoney(DataHelper.getFirstCardInfo().getNumberOfCard(), 1000);
        int balanceFirstCardAfterTransfer = dashboardPage.getCardBalance(DataHelper.getFirstCardInfo());
        int balanceSecondCardAfterTransfer = dashboardPage.getCardBalance(DataHelper.getSecondCardInfo());
        Assertions.assertEquals(balanceFirstCard - 1000, balanceFirstCardAfterTransfer);
        Assertions.assertEquals(balanceSecondCard + 1000, balanceSecondCardAfterTransfer);
    }

    @Test
    public void shouldTransferMoneyFromSecondCardToFirstCard() {
        var loginPage = new LoginInPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        int balanceFirstCard = dashboardPage.getCardBalance(DataHelper.getFirstCardInfo());
        int balanceSecondCard = dashboardPage.getCardBalance(DataHelper.getSecondCardInfo());
        var transferPage = dashboardPage.fillCard(DataHelper.getFirstCardInfo());
        transferPage.transferMoney(DataHelper.getSecondCardInfo().getNumberOfCard(), 1000);
        int balanceFirstCardAfterTransfer = dashboardPage.getCardBalance(DataHelper.getFirstCardInfo());
        int balanceSecondCardAfterTransfer = dashboardPage.getCardBalance(DataHelper.getSecondCardInfo());
        Assertions.assertEquals(balanceFirstCard + 1000, balanceFirstCardAfterTransfer);
        Assertions.assertEquals(balanceSecondCard - 1000, balanceSecondCardAfterTransfer);
    }


    @Test
    public void shouldTransferZeroMoney() {
        var loginPage = new LoginInPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        int balanceFirstCard = dashboardPage.getCardBalance(DataHelper.getFirstCardInfo());
        int balanceSecondCard = dashboardPage.getCardBalance(DataHelper.getSecondCardInfo());
        var transferPage = dashboardPage.fillCard(DataHelper.getSecondCardInfo());
        transferPage.transferMoney(DataHelper.getFirstCardInfo().getNumberOfCard(), 0);
        int balanceFirstCardAfterTransfer = dashboardPage.getCardBalance(DataHelper.getFirstCardInfo());
        int balanceSecondCardAfterTransfer = dashboardPage.getCardBalance(DataHelper.getSecondCardInfo());
        Assertions.assertEquals(balanceFirstCard, balanceFirstCardAfterTransfer);
        Assertions.assertEquals(balanceSecondCard, balanceSecondCardAfterTransfer);
    }

    @Test
    public void shouldTransferSumMoreThanBalanceOnCard() {
        var loginPage = new LoginInPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        int balanceFirstCard = dashboardPage.getCardBalance(DataHelper.getFirstCardInfo());
        int balanceSecondCard = dashboardPage.getCardBalance(DataHelper.getSecondCardInfo());
        var transferPage = dashboardPage.fillCard(DataHelper.getSecondCardInfo());
        transferPage.transferMoney(DataHelper.getFirstCardInfo().getNumberOfCard(), 20_000);
        int balanceFirstCardAfterTransfer = dashboardPage.getCardBalance(DataHelper.getFirstCardInfo());
        int balanceSecondCardAfterTransfer = dashboardPage.getCardBalance(DataHelper.getSecondCardInfo());
        Assertions.assertEquals(balanceFirstCard - 20_000, balanceFirstCardAfterTransfer);
        Assertions.assertEquals(balanceSecondCard + 20_000, balanceSecondCardAfterTransfer);
    }
}



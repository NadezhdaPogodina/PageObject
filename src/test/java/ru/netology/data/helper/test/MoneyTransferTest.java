package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.helper.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.helper.data.DataHelper.*;

public class MoneyTransferTest {
    //private class DashboardPage {
    LoginPage loginPage;
    DashboardPage dashboardPage;

    @BeforeEach
    void setup() {
        loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = getVerificationCode();
        dashboardPage = verificationPage.validVerify(verificationCode);

    }

    @Test
    void shouldTransferFromFirstToSecond() {
        var firstCardInfo = getFirstCardInfo();
        var secondCardInfo = getSecondCardInfo();
        var firstCardBalance = dashboardPage.getCardBalance(firstCardInfo);
        var secondCardBalance = dashboardPage.gatCardBalance(secondCardInfo);
        var amount = generateValidAmount(firstCardBalance);
        var expectedBalanceFirstCard = firstCardBalance - amount;
        var expectedBalanceSecondCard = secondCardBalance + amount;
        var transferPage = dashboardPage.selectCardtoTransfer(secondCardInfo);
        dashboardPage = transferPage.makeValidTransfer(String.valueOf(amount), firstCardInfo);
        var actualBalanceFirstCard = dashboardPage.getCardBalance(firstCardInfo);
        var actualBalanceSecondCard = dashboardPage.getCardBalance(secondCardInfo);
        assertEquals(expectedBalanceFirstCard, actualBalanceFirstCard);
        assertEquals(expectedBalanceSecondCard, actualBalanceSecondCard);

    }

    @Test
    void shouldGetErrorMessageIfAmountMoreBalance() {
        var firstCardInfo = getFirstCardInfo();
        var secondCardInfo = getSecondCardInfo();
        var firstCardBalance = dashboardPage.getCardBalance(firstCardInfo);
        var secondCardBalance = dashboardPage.gatCardBalance(secondCardInfo);
        var amount = generateInvalidAmount(secondCardBalance);
        var transferPage = dashboardPage.selectCardToTransfer(firstCardInfo);
        transferPage.makeTransfer(String.valueOf(amount), secondCardInfo);
        transferPage.findErrorMessage("Выпонена попытка перевода суммы, превышающей остаток на карте списания");
        dashboardPage = transferPage.makeValidTransfer(String.valueOf(amount), firstCardInfo);
        var actualBalanceFirstCard = dashboardPage.getCardBalance(firstCardInfo);
        var actualBalanceSecondCard = dashboardPage.getCardBalance(secondCardInfo);
        assertEquals(firstCardBalance, actualBalanceFirstCard);
        assertEquals(secondCardBalance, actualBalanceSecondCard);
    }


    //   @Test
    // void shouldTransferMoneyBetweenOwnCardsV1() {
    //    open("http://localhost:9999");
    //   var loginPage = new LoginPageV1();
//   var loginPage = open("http://localhost:9999", LoginPageV1.class);
    //   var authInfo = getAuthInfo();
    //   var verificationPage = loginPage.validLogin(authInfo);
    //   var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
    //   verificationPage.validVerify(verificationCode);
    //   }

    //  @Test
    //  void shouldTransferMoneyBetweenOwnCardsV2() {
    //     open("http://localhost:9999");
    //     var loginPage = new LoginPageV2();
//    var loginPage = open("http://localhost:9999", LoginPageV2.class);
    //     var authInfo = getAuthInfo();
    //      var verificationPage = loginPage.validLogin(authInfo);
    //    var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
    //    verificationPage.validVerify(verificationCode);
    // }

    //  @Test
    //  void shouldTransferMoneyBetweenOwnCardsV3() {
    ////  var loginPage = open("http://localhost:9999", LoginPage.class);
    ////   var authInfo = getAuthInfo();
    //    var verificationPage = loginPage.validLogin(authInfo);
    //   var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
    //   verificationPage.validVerify(verificationCode);
}



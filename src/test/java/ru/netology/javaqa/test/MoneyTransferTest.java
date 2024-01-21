package ru.netology.javaqa.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.javaqa.data.DataHelper;
import ru.netology.javaqa.page.DashboardPage;
import ru.netology.javaqa.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.javaqa.data.DataHelper.*;

public class MoneyTransferTest {
    DashboardPage dashboardPage;
    CardInfo firstCardInfo;
    CardInfo secondCardInfo;
    int firstCardBalance;
    int secondCardBalance;

    @BeforeEach
    void setup() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = getVerificationCode();
        dashboardPage = verificationPage.validVerify(verificationCode);
        firstCardInfo = getFirstCardInfo();
        secondCardInfo = getSecondCardInfo();
        firstCardBalance = dashboardPage.getCardBalance(firstCardInfo);
        secondCardBalance = dashboardPage.getCardBalance(secondCardInfo);


    }

    @Test
    void shouldTransferFromFirstToSecond() {

        var amount = generateValidAmount(firstCardBalance);
        var expectedBalanceOfFirstCard = firstCardBalance - amount;
        var expectedBalanceOfSecondCard = secondCardBalance + amount;
        var transferPage = dashboardPage.chooseCardToTransfer(secondCardInfo);
        dashboardPage = transferPage.makeValidTransfer(String.valueOf(amount), firstCardInfo);
        var actualBalanceOfFirstCard = dashboardPage.getCardBalance(firstCardInfo);
        var actualBalanceOfSecondCard = dashboardPage.getCardBalance(secondCardInfo);
        assertAll(() -> assertEquals(expectedBalanceOfFirstCard, actualBalanceOfFirstCard),
                () -> assertEquals(expectedBalanceOfSecondCard, actualBalanceOfSecondCard));
    }

    @Test
    void shouldGetErrorMessegIfAmountMoreBalance() {
        var amount = generateInvalidAmount(secondCardBalance);
        var transferPage = dashboardPage.chooseCardToTransfer(firstCardInfo);
        transferPage.makeTransfer(String.valueOf(amount), secondCardInfo);
        transferPage.findErrorMessage
                ("Выполнена попытка перевода суммы, превышающей остаток на карте списания");
        var actualBalanceOfFirstCard = dashboardPage.getCardBalance(firstCardInfo);
        var actualBalanceOfSecondCard = dashboardPage.getCardBalance(secondCardInfo);
        assertAll(() -> transferPage.findErrorMessage
                        ("Выполнена попытка перевода суммы, превышающей остаток на карте списания"),
                () -> assertEquals(firstCardBalance, actualBalanceOfFirstCard),
                () -> assertEquals(secondCardBalance, actualBalanceOfSecondCard));
    }

}


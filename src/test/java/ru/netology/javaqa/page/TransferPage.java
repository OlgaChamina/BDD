package ru.netology.javaqa.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.selector.ByText;
import ru.netology.javaqa.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private SelenideElement transferHeader =$(byText("Пополнение карты"));
    private SelenideElement transferAmount=$("[data-test-id=amount] input");
    private SelenideElement transferFrom=$("[data-test-id=from] input");

    private SelenideElement transferButton=$("[data-test-id=action-transfer]");
    private SelenideElement errorMessage=$("[data-test-id=error-notification] .notification__content");
    public TransferPage(){
        transferHeader.shouldBe(Condition.visible);
    }
    public DashboardPage makeValidTransfer(String amountToTransfer, DataHelper.CardInfo cardInfo){
        makeTransfer(amountToTransfer, cardInfo);
        return new DashboardPage();
    }
    public void makeTransfer(String amountToTransfer, DataHelper.CardInfo cardInfo){
        transferAmount.setValue(amountToTransfer);
        transferFrom.setValue(cardInfo.getCardNumber());
        transferButton.click();

    }
    public void findErrorMessage(String expectedText){
        errorMessage.shouldHave(Condition.exactText(expectedText), Duration.ofSeconds(15)).shouldBe(Condition.visible);
    }

}

package ru.netology.javaqa.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.netology.javaqa.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    // к сожалению, разработчики не дали нам удобного селектора, поэтому так
    private final ElementsCollection cards = $$(".list__item div");
    private final SelenideElement header = $("[data-test-id=dashboard");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";


    public DashboardPage() {
        header.shouldBe(Condition.visible);
    }

    public int getCardBalance(DataHelper.CardInfo cardInfo) {
        var text = cards.findBy(Condition.text("**** **** **** " + cardInfo.getCardNumber().substring(15)))
                .getText();
        return extractBalance(text);
    }

    public TransferPage chooseCardToTransfer(DataHelper.CardInfo cardInfo) {
        cards.findBy(Condition.text("**** **** **** " + cardInfo.getCardNumber().substring(15)))
                .$("button").click();
        return new TransferPage();
    }


    private int extractBalance(String text) {
        var start = text.indexOf(balanceStart);
        var finish = text.indexOf(balanceFinish);
        var value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }
}


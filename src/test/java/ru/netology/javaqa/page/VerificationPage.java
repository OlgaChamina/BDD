package ru.netology.javaqa.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.javaqa.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {
    private SelenideElement codeField=$("[data-test-id=code] input");
    private SelenideElement verifyButton=$("[data-test-id=action-verify]");
    private SelenideElement errorTransfer=$("[data-test-id=error-notification]");
    public VerificationPage()
    {
        codeField.shouldBe(Condition.visible);
    }
    public DashboardPage validVerify(DataHelper.VerificationCode verificationCode){
        codeField.setValue(verificationCode.getCode());
        verifyButton.click();
        return new DashboardPage();
    }
    public void invalidVerify(DataHelper.VerificationCode verificationCode){
        codeField.setValue(verificationCode.getCode());
        verifyButton.click();
        errorTransfer.shouldBe(Condition.visible);
    }
}

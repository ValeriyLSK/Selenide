package ru.netology;


import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryTest {

    String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    String planningDate = generateDate(3);

    @Test
    public void shouldSendForm() {
        open("http://localhost:9999");
        $("//*[@data-test-id='city']").setValue("Москва");
        $("//*[@data-test-id='date']").sendKeys(planningDate);
        $("//*[@data-test-id='name']").setValue("Райан Гослинг");
        $("//*[@data-test-id='phone']").setValue("+79999999999");
        $("//*[@class='checkbox__box']").click();
        $("//button[contains(span,'Забронировать')]").click();
        $("//*[@data-test-id='notification']").should(visible, Duration.ofSeconds(15));
        $("//*[@class='notification__content']").shouldHave(Condition.text("Встреча успешно запланирована на" + planningDate), Duration.ofSeconds(15));
    }
}

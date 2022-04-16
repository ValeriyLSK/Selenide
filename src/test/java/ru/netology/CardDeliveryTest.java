package ru.netology;


import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {

    String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    String planningDate = generateDate(3);

    @Test
    public void shouldSendForm() {
        open("http://localhost:9999");
        $x("//*[@data-test-id='city']/span/span/input").val("Москва");
        $x("//input[@placeholder='Дата встречи']").doubleClick().sendKeys(planningDate);
        $x("//input[@name='name']").val("Райан Гослинг");
        $x("//input[@name='phone']").val("+79999999999");
        $x("//*[@class='checkbox__box']").click();
        $x("//button[contains(span,'Забронировать')]").click();
        $x("//*[@data-test-id='notification']").should(visible, Duration.ofSeconds(15));
        $x("//*[@class='notification__content']").shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15));
    }
}

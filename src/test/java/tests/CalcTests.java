package tests;

import CalcSteps.CalcSteps;
import io.qameta.allure.Allure;
import io.qameta.allure.Issue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CalcTests {

    @Test
    @Issue("VIDEOTECH-213")
    public void sumTest() {
        CalcSteps calcSteps = new CalcSteps();
        int a = 1;
        int b = 2;
        Allure.step(String.format("Прибавляю %s к %s", a, b));
        int result = calcSteps.sum(a, b);
        //Allure.step(String.format("Проверяю что число %s - положительное", result));

        // Assertions.assertTrue(calcSteps.isPositive(result));
        Allure.step("Проверяю, что результат (число " + result + ") - положительное число",
                step -> {
                    Assertions.assertFalse(calcSteps.isPositive(result));
                });
    }
}

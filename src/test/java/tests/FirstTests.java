package tests;


import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junitpioneer.jupiter.RetryingTest;
import utils.RetryListener;
import utils.RetryListenerV2;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Tag("API")
@ExtendWith(RetryListenerV2.class)
public class FirstTests {

    @AfterAll
    public static void saveFailed(){
        RetryListenerV2.saveFailedTests();
    }

    //@Test
    @DisplayName("Check the weekday today green")
    @RetryingTest(maxAttempts = 3, name = "{displayName} [{index}]")
    void testIsWeekday1(){
        Instant now = Instant.now();
        LocalDateTime ldt = LocalDateTime.ofInstant(now, ZoneId.systemDefault());
        boolean isWeekday = ldt.getDayOfWeek() != DayOfWeek.SATURDAY && ldt.getDayOfWeek() != DayOfWeek.SUNDAY;
        Assertions.assertTrue(isWeekday, "it is weekend now");
    }

    //@Test
    @DisplayName("Check the weekday today red")
    @RetryingTest(maxAttempts = 3, name = "{displayName} [{index}]")
    void testIsWeekday2(){
        Instant now = Instant.now();
        LocalDateTime ldt = LocalDateTime.ofInstant(now, ZoneId.systemDefault());
        boolean isWeekday = ldt.getDayOfWeek() != DayOfWeek.SATURDAY && ldt.getDayOfWeek() != DayOfWeek.SUNDAY;
        Assertions.assertTrue(!isWeekday, "it is weekend now");

    }

    static int a = 1;

    //@Test
    @DisplayName("Check the repeating green")
    @RetryingTest(maxAttempts = 3, name = "{displayName} [{index}]")
    void testRepeater3rdPass(){
        a++;
        Assertions.assertEquals(3, a);
    }

    static int b = 1;

    //@Test
    @DisplayName("Check the repeating red")
    @RetryingTest(maxAttempts = 3, name = "{displayName} [{index}]")
    void testRepeater2ndPass(){
        b++;
        Assertions.assertEquals(2, b);
    }
}

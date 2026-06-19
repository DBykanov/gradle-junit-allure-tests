package utils;

import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junitpioneer.jupiter.RetryingTest;

import java.io.File;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

public class RetryListenerV2 implements AfterTestExecutionCallback {

    private static final Set<String> failedTests = new HashSet<>();

    @SneakyThrows
    public static void saveFailedTests() {
        String output = System.getProperty("user.dir") + "/src/test/resources/FailedTests.txt";
        String result = String.join(" ", failedTests);
        // Указываем кодировку UTF_8, чтобы компилятор Windows не ругался
        FileUtils.write(new File(output), result, StandardCharsets.UTF_8);
    }

/*    @Override
    public void afterTestExecution(ExtensionContext context) {
        String testClass = context.getRequiredTestClass().getName();
        String testName = context.getRequiredTestMethod().getName();
        String testToWrite = String.format("--tests %s.%s*", testClass, testName);

        if (context.getExecutionException().isPresent()) {
            // Если тест упал — фиксируем его в список
            failedTests.add(testToWrite);
        } else {
            // Если тест прошёл (например, успешно переигрался плагином Gradle) — убираем из списка
            failedTests.remove(testToWrite);
        }
        System.out.println("Текущие упавшие тесты в ране: " + failedTests);
    }*/

    @Override
    public void afterTestExecution(ExtensionContext context) {
        // Проверяем, есть ли вообще ошибка в тесте
        if (context.getExecutionException().isPresent()) {
            Method method = context.getRequiredTestMethod();
            RetryingTest annotation = method.getAnnotation(RetryingTest.class);

            boolean isNormalTest = (annotation == null);
            boolean isLastRetry = (annotation != null && context.getDisplayName().contains("[" + 3 + "]"));

            // Если это обычный тест ИЛИ последняя попытка ретрая — фиксируем фейл для скрипта
            if (isNormalTest || isLastRetry) {
                String testClass = context.getRequiredTestClass().getName();
                String testName = method.getName();
                String testToWrite = String.format("--tests %s.%s*", testClass, testName);

                failedTests.add(testToWrite);
            }
        }
    }

}
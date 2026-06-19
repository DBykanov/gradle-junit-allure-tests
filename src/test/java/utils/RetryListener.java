package utils;

import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;

import java.io.File;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class RetryListener implements TestExecutionExceptionHandler, AfterTestExecutionCallback { //первый обрабатывает ошибки, а второй выполняет логику после завершения теста

    private static final int MAX_RETRIES = 3;
    private static final Set<String> failedTests = new HashSet<>();

    @SneakyThrows
    public static void saveFailedTests() {
        String output = System.getProperty("user.dir") + "/src/test/resources/FailedTests.txt";
        String result = String.join(" ", failedTests);
        FileUtils.write(new File(output), result);
    }

    @Override
    //передаются вовнутрь тест который запускается и ошибка которая вылетела в случае падения теста
    public void handleTestExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
        for (int i = 0; i < MAX_RETRIES; i++) {
            try {
                context
                        .getRequiredTestMethod() //берем упавший тест
                        .invoke(context.getRequiredTestInstance()); //вызываем тест
                return; //если не упали то выходим просто из метода
            } catch (Throwable ex) {
                throwable = ex; //сетим в наш контекстный throwable нашу ошибку
            }
        }
        throw throwable; //и пробрасываем ее дальше по флоу
    }

    @Override
    public void afterTestExecution(ExtensionContext context) {

        Method method = context.getRequiredTestMethod();
        String testClass = context.getRequiredTestClass().getName();
        String testName = method.getName();
        String testToWrite = String.format("--tests %s.%s*", testClass, testName); //звездочка на всякий случай чтоб точно
        context.getExecutionException().ifPresent(x -> failedTests.add(testToWrite));
        System.out.println(failedTests);
    }

}

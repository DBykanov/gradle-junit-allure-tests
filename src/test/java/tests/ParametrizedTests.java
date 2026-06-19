package tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.Person;
import models.Props;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import utils.AppConfig;
import utils.JsonHelper;
import utils.RetryListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.stream.Stream;

@ExtendWith(RetryListener.class)
public class ParametrizedTests {

    @AfterAll
    public static void saveFailed(){
        RetryListener.saveFailedTests();
    }

    private static Stream<Arguments> testData(){
        return Stream.of(
                Arguments.of(new Person("sasha", "18", "female")),
                Arguments.of(new Person("misha", "18", "female"))
                //Arguments.of(new Person("katya", "18", "female"))
        );
    }

    @ParameterizedTest
    @MethodSource("testData")
    void paramTestFromClass(Person person){
        System.out.println(person.getName());
        System.out.printf(person.toString());
        Assertions.assertTrue(person.getName().contains("s"));
    }


    @ParameterizedTest
    @CsvFileSource(resources = "/people.csv", delimiter = ';')
    void paramTest(String name, String age, String sex){
        System.out.printf("%s, %s, %s%n", name, age, sex);
        System.out.println(name);
        Assertions.assertTrue(name.contains("s"));
    }

    @Test
    void jacksonTest1() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("src/test/resources/stas.json");
        Person stas = objectMapper.readValue(file, Person.class);
        System.out.println(stas.getName());
       // Assertions.fail();
    }

    @Test
    void jacksonTest2() {
        Person stas = JsonHelper.fromJson("src/test/resources/stas.json", Person.class);
        System.out.println(stas.getName());

        String stasStringified = JsonHelper.toJson(stas);
        System.out.println(stasStringified);
    }

    @Test
    void jacksonTest3() throws IOException {
        Properties properties = new Properties();
        FileInputStream fis = new FileInputStream("src/test/resources/app.properties");
        properties.load(fis);
        String json = JsonHelper.toJson(properties);
        System.out.println(json);

        Props props = JsonHelper.fromJsonString(json, Props.class);
        System.out.println(props.getThreads());
    }

    @Test
    void ownerReaderTest(){
        AppConfig appConfig = ConfigFactory.create(AppConfig.class);
        System.out.println(appConfig.isProd());
    }

}

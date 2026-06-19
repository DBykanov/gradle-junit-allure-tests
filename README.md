# gradle-junit-allure-tests
note: allure is configured to use via allure CLI (tested with allure v. 2.13.6)
commands :
1. To run tests:
   - to run all tests: ./gradlew clean test
   - To run particular tests: ./gradlew clean test --tests CalcTests
2. To generate allure report and open it : allure serve build/allure-results


Used technologies:
- java
- gradle
- junit (especially: @ParameterizedTest, 
- jackson
- lombok
- custom tags
- allure
- junit-pioneer (for retries: @RetryingTest)
- the owner lib (for props initing)
- appache commons (with jackson together to write failed tests to file)

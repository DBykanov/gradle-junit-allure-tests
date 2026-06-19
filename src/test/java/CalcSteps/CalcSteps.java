package CalcSteps;

import io.qameta.allure.Step;

public class CalcSteps {

    public int sum(int a, int b){
        return a + b;
    }

    public boolean isPositive(int result){
        return result > 0;
    }
}

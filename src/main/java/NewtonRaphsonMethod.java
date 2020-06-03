import com.udojava.evalex.Expression;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NewtonRaphsonMethod {

    private static final Double RAPHSON_PRECISION=0.00001;
    private static final int RAPHSON_ITERATIONS=30;
    private static final Double RAPHSON_X=5.0;
    private static final Double H=0.01;

    public double raphson(String function) {
        int k=1;
        Double x=RAPHSON_X;
        do {
            Double firstDerivative=partialDerivative(function, 0, Collections.singletonList(x));
            Double secondDerivative=getSecondDerivtive(function, x);

            if (secondDerivative != 0) {
                x=x - (firstDerivative / secondDerivative);
            }
//            System.out.println("inside raphson: "+x);
//            System.out.println("warunek: "+partialDerivative(function, 0, List.of(x)));
            k++;
        } while (Math.abs(partialDerivative(function, 0, List.of(x))) > RAPHSON_PRECISION && k <= RAPHSON_ITERATIONS);
        return x;
    }

    public Double partialDerivative(final String function, final int variableIndex, final List<Double> variables1) {
        ArrayList<Double> variables=new ArrayList<>(variables1);
        variables.set(variableIndex, variables.get(variableIndex) + H);
        SteepestDescentParams steepestDescentParams=new SteepestDescentParams(function, variables, null);
        Double firstExpression=Utils.evaluate(steepestDescentParams.getFunction(), steepestDescentParams.getVariablesName(), steepestDescentParams.getStartingPoint());
        variables.set(variableIndex, variables.get(variableIndex) - (2 * H));
        SteepestDescentParams steepestDescentParams1=new SteepestDescentParams(function, variables, null);
        Double secondExpression=Utils.evaluate(steepestDescentParams1.getFunction(), steepestDescentParams1.getVariablesName(), steepestDescentParams1.getStartingPoint());
        return (firstExpression - secondExpression) / (2 * H);
    }

    private Double getSecondDerivtive(String function, Double x) {
        SteepestDescentParams steepestDescentParams=new SteepestDescentParams(function, Collections.singletonList(x + H), null);
        Double one = Utils.evaluate(steepestDescentParams.getFunction(), steepestDescentParams.getVariablesName(), steepestDescentParams.getStartingPoint());

        SteepestDescentParams steepestDescentParams1=new SteepestDescentParams(function, Collections.singletonList(x), null);
        Double two=Utils.evaluate(steepestDescentParams1.getFunction(), steepestDescentParams1.getVariablesName(), steepestDescentParams1.getStartingPoint());

        SteepestDescentParams steepestDescentParams2=new SteepestDescentParams(function, Collections.singletonList(x - H), null);
        Double three=Utils.evaluate(steepestDescentParams2.getFunction(), steepestDescentParams2.getVariablesName(), steepestDescentParams2.getStartingPoint());
        return (one - (two * 2.0) + three) / Math.pow(H, 2);
    }
}

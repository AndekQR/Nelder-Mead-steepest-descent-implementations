import com.udojava.evalex.Expression;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NewtonRaphsonMethod {

    private static final Double RAPHSON_PRECISION=0.1;
    private static final int RAPHSON_ITERATIONS=20;
    private static final Double RAPHSON_X=10.0;
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
            k++;
        } while (Math.abs(partialDerivative(function, 0, List.of(x))) > RAPHSON_PRECISION && k <= RAPHSON_ITERATIONS);
        return x;
    }

    public Double partialDerivative(final String function, final int variableIndex, final List<Double> variables1) {
        ArrayList<Double> variables=new ArrayList<>(variables1);
        variables.set(variableIndex, variables.get(variableIndex) + H);
        Double firstExpression=this.evaluateExpression(new SteepestDescentParams(function, variables, null));
        variables.set(variableIndex, variables.get(variableIndex) - (2 * H));
        Double secondExpression=this.evaluateExpression(new SteepestDescentParams(function, variables, null));
        return (firstExpression - secondExpression) / (2 * H);
    }

    private Double getSecondDerivtive(String function, Double x) {
        Double one=this.evaluateExpression(new SteepestDescentParams(function, Collections.singletonList(x + H), null));
        Double two=this.evaluateExpression(new SteepestDescentParams(function, Collections.singletonList(x), null));
        Double three=this.evaluateExpression(new SteepestDescentParams(function, Collections.singletonList(x - H), null));
        return (one - (two * 2.0) + three) / Math.pow(H, 2);
    }

    private Double evaluateExpression(final SteepestDescentParams steepestDescentParams) throws IllegalArgumentException {
        Expression expression=new Expression(steepestDescentParams.getFunction());
        for (int i=0; i < steepestDescentParams.getStartingPoint().size(); i++) {
            expression.setVariable(steepestDescentParams.getVariablesName()[i].toString(), steepestDescentParams.getStartingPoint().get(i).toString());
        }
        return expression.eval().doubleValue();
    }
}

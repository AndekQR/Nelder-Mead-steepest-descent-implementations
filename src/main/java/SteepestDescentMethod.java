import com.udojava.evalex.Expression;

import java.util.ArrayList;
import java.util.List;

public class SteepestDescentMethod {

    private static final String LAMBDA="p";
    private NewtonRaphsonMethod newtonRaphsonMethod;

    public List<Double> start(SteepestDescentParams steepestDescentParams) {
        this.newtonRaphsonMethod=new NewtonRaphsonMethod();
        int k=1;
        List<Double> prevX=steepestDescentParams.getStartingPoint();
        List<Double> nextX=steepestDescentParams.getStartingPoint();
        List<Double> S=new ArrayList<>(2);
        double conditionToCheck=0.0;
        do {
            System.out.println("Iteracja " + k + "     ----------------------------------------------");
            S.clear();
            for (int i=0; i < steepestDescentParams.getVaraiblesNamesSize(); i++) {
                Double tmp=this.newtonRaphsonMethod.partialDerivative(steepestDescentParams.getFunction(), i, nextX) * -1;
                S.add(tmp);
            }

            System.out.println("S: " + S);

            prevX=nextX;
            nextX=this.getNextX(prevX, S, steepestDescentParams);

            System.out.println("New X: " + nextX);

            Double first=evaluateExpression(new SteepestDescentParams(steepestDescentParams.getFunction(), nextX, null));
            Double second=evaluateExpression(new SteepestDescentParams(steepestDescentParams.getFunction(), prevX, null));
            conditionToCheck=Math.abs((first - second));
            k++;
        } while (conditionToCheck > steepestDescentParams.getPrecision());
        return nextX;
    }

    private Double evaluateExpression(final SteepestDescentParams steepestDescentParams) throws IllegalArgumentException {
        Expression expression=new Expression(steepestDescentParams.getFunction());
        for (int i=0; i < steepestDescentParams.getStartingPoint().size(); i++) {
            expression.setVariable(steepestDescentParams.getVariablesName()[i].toString(), steepestDescentParams.getStartingPoint().get(i).toString());
        }
        return expression.eval().doubleValue();
    }

    private List<Double> getNextX(List<Double> prevX, List<Double> lastS, SteepestDescentParams steepestDescentParams) {
        List<Double> x=new ArrayList<>();
        List<String> arguments=this.getArguments(prevX, lastS);
        String expressionString=steepestDescentParams.getFunction();
        for (int i=0; i < arguments.size(); i++) {
            expressionString=expressionString.replace(steepestDescentParams.getVariablesName()[i].toString(), " ( " + arguments.get(i) + " ) ");
        }
        double raphson=this.newtonRaphsonMethod.raphson(expressionString);
        System.out.println("raphsonValue: " + raphson);
        for (int i=0; i < arguments.size(); i++) {
            double tmp=prevX.get(i) + (raphson * lastS.get(i));
            System.out.println("next x calculation: " + prevX.get(i) + " + ( " + raphson + " * " + lastS.get(i) + " = " + tmp);
            x.add(tmp);
        }
        return x;
    }

    private List<String> getArguments(List<Double> x, List<Double> s) {
        if (x.size() != s.size())
            throw new IllegalArgumentException("getFunctionArguments: x.size: " + x.size() + ", s.size: " + s.size());

        List<String> arguments=new ArrayList<>();
        for (int i=0; i < x.size(); i++) {
            arguments.add(x.get(i) + " + " + LAMBDA + " * " + s.get(i));
        }
        return arguments;
    }
}

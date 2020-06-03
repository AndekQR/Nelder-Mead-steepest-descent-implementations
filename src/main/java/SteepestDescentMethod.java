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

            SteepestDescentParams steepestDescentParams1=new SteepestDescentParams(steepestDescentParams.getFunction(), nextX, null);
            Double first = Utils.evaluate(steepestDescentParams1.getFunction(), steepestDescentParams1.getVariablesName(), steepestDescentParams1.getStartingPoint());
            SteepestDescentParams steepestDescentParams2=new SteepestDescentParams(steepestDescentParams.getFunction(), prevX, null);
            Double second=Utils.evaluate(steepestDescentParams2.getFunction(), steepestDescentParams2.getVariablesName(), steepestDescentParams2.getStartingPoint());
            conditionToCheck=Math.abs((first - second));
            k++;
        } while (conditionToCheck > steepestDescentParams.getPrecision());
        return nextX;
    }

    private List<Double> getNextX(List<Double> prevX, List<Double> lastS, SteepestDescentParams steepestDescentParams) {
        List<Double> x=new ArrayList<>();
        List<String> arguments=this.getArguments(prevX, lastS);
        String expressionString=steepestDescentParams.getFunction();
        for (int i=0; i < arguments.size(); i++) {
            expressionString=expressionString.replace(steepestDescentParams.getVariablesName().get(i), " ( " + arguments.get(i) + " ) ");
        }

        System.out.println(expressionString);
        double raphson=this.newtonRaphsonMethod.raphson(expressionString);
        System.out.println("raphsonValue: " + raphson);
        for (int i=0; i < arguments.size(); i++) {
            double tmp=prevX.get(i) + (raphson * lastS.get(i));
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

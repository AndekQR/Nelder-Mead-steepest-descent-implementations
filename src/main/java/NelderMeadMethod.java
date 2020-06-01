import com.udojava.evalex.Expression;

import java.util.ArrayList;
import java.util.List;

public class NelderMeadMethod {

    private static final String XR_FUNCTION="(1 + a) * x0 - a * xh";
    private static final String XE_FUNCTION="y * xr + (1 - y) * x0";
    private static final String XC_FUNCTION="b * xh + (1 - b) * x0";

    public void start(NelderMeadParams params) {
        this.setValuesToX(params);

        int k = 1;
        double q=0.0;
        Variable x0=null;
        do {

            System.out.println("iteracja nr. "+k+" -----------------------------");
            params.getX().forEach(System.out::println);

            int h =this.getHighestVarIndex(params.getX());
            System.out.println("h: "+h);
            int l =this.getLowest(params.getX());
            x0=this.getX0(h, params);
            Variable xR=getXR(params, x0, h);
            System.out.println("xR: " + xR);
            Variable xE=new Variable();
            Variable xC=new Variable();

            if ((params.getX().get(h).getValue() > xR.getValue()) && (xR.getValue() > params.getX().get(l).getValue())) { //4
                params.getX().get(h).setArguments(xR.getArguments());
                params.getX().get(h).setValue(this.evaluate(params.getFunction(), params.getVariablesName(), params.getX().get(h).getArguments()));
            } else {
                if (xR.getValue() < params.getX().get(l).getValue()) { //5
                    xE=this.getXE(params, xR, x0);
                    System.out.println("xE: " + xE);

                    if (xE.getValue() < params.getX().get(l).getValue()) { //6
                        params.getX().get(h).setArguments(xE.getArguments());
                        params.getX().get(h).setValue(this.evaluate(params.getFunction(), params.getVariablesName(), params.getX().get(h).getArguments()));

                    } else if (xE.getValue() > params.getX().get(l).getValue()) {
                        params.getX().get(h).setArguments(xR.getArguments());
                        params.getX().get(h).setValue(this.evaluate(params.getFunction(), params.getVariablesName(), params.getX().get(h).getArguments()));
                    }
                } else {
                    if ((this.isEachVarGreater(h, xR, params)) && (xR.getValue() < params.getX().get(h).getValue())) { //7
                        params.getX().get(h).setArguments(xR.getArguments());
                        params.getX().get(h).setValue(this.evaluate(params.getFunction(), params.getVariablesName(), params.getX().get(h).getArguments()));
                        xC=this.getXC(params, h, x0);
                        System.out.println("xC: " + xC);

                    } else if (xR.getValue() > params.getX().get(h).getValue()) {
                        xC=this.getXC(params, h, x0);
                    }

                    if ((xC.getValue() < params.getX().get(h).getValue()) && (xC.getValue() < xR.getValue())) { //8
                        params.getX().get(h).setArguments(xC.getArguments());
                        params.getX().get(h).setValue(this.evaluate(params.getFunction(), params.getVariablesName(), params.getX().get(h).getArguments()));
                    } else {
                        this.simplexReduction(params, l);
                    }
                }
            }
            k++;
            double numerator=Math.pow(params.getX().get(0).getValue() - x0.getValue(), 2) + Math.pow(params.getX().get(1).getValue() - x0.getValue(), 2) + Math.pow(params.getX().get(2).getValue() - x0.getValue(), 2);
            q=Math.sqrt(numerator / 3.0);
            System.out.println("Q: " + q);
        } while (q > params.getEpsilon());
        System.out.println("Result x0: " + x0);
    }


    private void simplexReduction(final NelderMeadParams params, final int l) {
        for (int i=0; i < params.getX().size(); i++) {
            if(i != l) {
                for (int i1=0; i1 < params.getX().get(i).getArguments().size(); i1++) {
                    Double tmp = (params.getX().get(i).getArguments().get(i1) - params.getX().get(l).getArguments().get(i1)) / 2.0;
                    params.getX().get(i).getArguments().set(i1, tmp);
                }
            }
        }
    }

    private Variable getXC(NelderMeadParams params, int h, Variable x0) {
        if (params.getX().get(h).getArguments().size() != x0.getArguments().size()) {
            throw new IllegalArgumentException("getXE: wrong number of arguments");
        }

        Variable result=new Variable();
        for (int i=0; i < params.getX().get(h).getArguments().size(); i++) {
            List<Double> args=List.of(params.getBeta(), params.getX().get(h).getArguments().get(i), x0.getArguments().get(i));
            List<String> argsNames=new ArrayList<>(Utils.parseNames(XC_FUNCTION));
            Double tmp=this.evaluate(XC_FUNCTION, argsNames, args);
            result.getArguments().add(tmp);
        }
        result.setValue(this.evaluate(params.getFunction(), params.getVariablesName(), result.getArguments()));
        return result;
    }

    private boolean isEachVarGreater(int h, Variable than, NelderMeadParams params) {
        for (int i=0; i < params.getX().size(); i++) {
            if(i != h){
                if(params.getX().get(i).getValue() < than.getValue()){
                    return false;
                }
            }
        }
        return true;
    }

    private void setValuesToX(NelderMeadParams params) {
        params.getX().forEach(x -> {
            x.setValue(this.evaluate(params.getFunction(), params.getVariablesName(), x.getArguments()));
        });
    }

    private Variable getXE(NelderMeadParams params, Variable xR, Variable x0) {
        if (xR.getArguments().size() != x0.getArguments().size()) {
            throw new IllegalArgumentException("getXE: wrong number of arguments");
        }

        Variable result=new Variable();
        for (int i=0; i < xR.getArguments().size(); i++) {
            List<Double> args=List.of(x0.getArguments().get(i), xR.getArguments().get(i), params.getGamma());
            List<String> argsNames=new ArrayList<>(Utils.parseNames(XE_FUNCTION));
            Double tmp=this.evaluate(XE_FUNCTION, argsNames, args);
            result.getArguments().add(tmp);
        }
        result.setValue(this.evaluate(params.getFunction(), params.getVariablesName(), result.getArguments()));
        return result;
    }

    private Variable getXR(final NelderMeadParams params, final Variable x0, final int h) {
        if (params.getX().isEmpty() || x0.getArguments().size() != params.getX().get(0).getArguments().size()) {
            throw new IllegalArgumentException("getXR: wrong number of arguments");
        }

        Variable result=new Variable();
        for (int i=0; i < x0.getArguments().size(); i++) {
            List<Double> args=List.of(params.getAlfa(), x0.getArguments().get(i), params.getX().get(h).getArguments().get(i));
            List<String> argsNames=new ArrayList<>(Utils.parseNames(XR_FUNCTION));
            Double tmp=this.evaluate(XR_FUNCTION, argsNames, args);
            result.getArguments().add(tmp);
        }
        result.setValue(this.evaluate(params.getFunction(), params.getVariablesName(), result.getArguments()));
        return result;
    }

    private Variable getX0(final int h, final NelderMeadParams params) {
        Variable result=new Variable();

        for (int i=0; i < params.getX().get(0).getArguments().size(); i++) { //arguments
            Double tmp=0.0;
            for (int j=0; j < params.getX().size(); j++) {
                if (j != h){
                    tmp+=params.getX().get(j).getArguments().get(i);
                }
            }
            result.getArguments().add(tmp / 2);
        }
        result.setValue(this.evaluate(params.getFunction(), params.getVariablesName(), result.getArguments()));
        return result;
    }


    private Double evaluate(final String function, final List<String> argsNames, final List<Double> args) {
        Expression expression=new Expression(function);
        for (int i=0; i < argsNames.size(); i++) {
            expression.setVariable(argsNames.get(i), args.get(i).toString());
        }
        return expression.eval().doubleValue();
    }


    private int getLowest(List<Variable> vars) {
        if (vars.isEmpty()) throw new IllegalArgumentException("getLowest: vars size equals zero");
        int lowest = 0;
        for (int i=1; i < vars.size(); i++) {
            if (vars.get(i).getValue() < vars.get(lowest).getValue())
                lowest=i;
        }
        return lowest;
    }

    private int getHighestVarIndex(List<Variable> vars) {
        if (vars.isEmpty()) throw new IllegalArgumentException("getHighest: vars size equals zero");
        int highest=0;
        for (int i=1; i < vars.size(); i++) {
            if (vars.get(i).getValue() > vars.get(highest).getValue())
                highest=i;
        }
        return highest;
    }
}

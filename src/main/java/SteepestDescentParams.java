import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class SteepestDescentParams {

    private SortedSet<String> variablesName;
    private String function;
    private List<Double> startingPoint;
    private Double precision;

    public SteepestDescentParams() {
        this.variablesName=new TreeSet<>();
        this.startingPoint=new ArrayList<>();
    }

    public SteepestDescentParams(String function, List<Double> startingPoint, Double precision) {
        this.function=function;
        this.startingPoint=startingPoint;
        this.precision=precision;
        this.variablesName=Utils.parseNames(this.getFunction());
    }

    public int getVaraiblesNamesSize() {
        return this.variablesName.size();
    }

    public ArrayList<String> getVariablesName() {
        return new ArrayList<>(this.variablesName);
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function=function;
        this.variablesName = Utils.parseNames(function);
    }


    public Double getPrecision() {
        return precision;
    }

    public void setPrecision(Double precision) {
        this.precision=precision;
    }


    public List<Double> getStartingPoint() {
        return startingPoint;
    }

    public void setStartingPoint(List<Double> startingPoint) {
        this.startingPoint=startingPoint;
    }

    @Override
    public String toString() {
        return "Params{" +
                "function='" + function + '\'' +
                ", startingPoint=" + startingPoint +
                ", precision=" + precision +
                ", variableNames=" + variablesName +
                '}';
    }
}

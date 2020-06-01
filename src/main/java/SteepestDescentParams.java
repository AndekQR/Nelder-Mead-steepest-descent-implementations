import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SteepestDescentParams {

    private final SortedSet<Character> variablesName;
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
        this.variablesName=new TreeSet<>();
        this.parseNames();
    }

    private void parseNames() {
        Pattern varaibleMatcher=Pattern.compile("[a-z]");
        this.variablesName.clear();
        Matcher matcher=varaibleMatcher.matcher(this.function);
        while (matcher.find()) {
            this.variablesName.add(matcher.group().charAt(0));
        }
    }

    public int getVaraiblesNamesSize() {
        return this.variablesName.size();
    }

    public Character[] getVariablesName() {
        return this.variablesName.toArray(new Character[this.variablesName.size()]);
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function=function;
        this.parseNames();
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

import java.util.*;

public class NelderMeadParams {

    private String function;
    private List<Variable> x;
    private Double alfa;
    private Double beta;
    private Double gamma;
    private Double epsilon;
    private SortedSet<String> variablesName;


    public NelderMeadParams() {
        this.x = new ArrayList<>();
        this.variablesName = new TreeSet<>();
    }

    public NelderMeadParams(String function, List<Variable> x,
                            Double alfa, Double beta,
                            Double gamma, Double epsilon) {
        this.function=function;
        this.alfa=alfa;
        this.x = x;
        this.beta=beta;
        this.gamma=gamma;
        this.epsilon=epsilon;
        this.variablesName = Utils.parseNames(this.function);

    }

    public int getVaraiblesNamesSize() {
        return this.variablesName.size();
    }

    public List<String> getVariablesName() {
        return new ArrayList<>(this.variablesName);
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function=function;
        this.variablesName = Utils.parseNames(function);
    }


    public List<Variable> getX() {
        return x;
    }

    public void setX(List<Variable> x) {
        this.x=x;
    }

    public Double getAlfa() {
        return alfa;
    }

    public void setAlfa(Double alfa) {
        this.alfa=alfa;
    }

    public Double getBeta() {
        return beta;
    }

    public void setBeta(Double beta) {
        this.beta=beta;
    }

    public Double getGamma() {
        return gamma;
    }
    public void setGamma(Double gamma) {
        this.gamma=gamma;
    }

    public Double getEpsilon() {
        return epsilon;
    }

    public void setEpsilon(Double epsilon) {
        this.epsilon=epsilon;
    }

    @Override
    public String toString() {
        return "NelderMeadParams{" +
                "function='" + function + '\'' +
                ", x=" + x +
                ", alfa=" + alfa +
                ", beta=" + beta +
                ", gamma=" + gamma +
                ", epsilon=" + epsilon +
                ", variablesName=" + variablesName +
                '}';
    }
}
